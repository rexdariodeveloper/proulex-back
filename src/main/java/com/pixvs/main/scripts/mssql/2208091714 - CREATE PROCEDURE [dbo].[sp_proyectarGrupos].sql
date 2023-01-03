SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER PROCEDURE [dbo].[sp_proyectarGrupos] @gruposIds NVARCHAR(MAX), @creadoPorId INT
AS
BEGIN
	DECLARE @feedBack TABLE (estudiante varchar(100), grupo varchar(100), mensaje varchar(2000))
	IF OBJECT_ID('tempdb..#tmpProgramasGrupos') IS NOT NULL
		DROP TABLE #tmpProgramasGrupos
	IF OBJECT_ID('tempdb..#tmpOV') IS NOT NULL
		DROP TABLE #tmpOV

	SELECT
		[PROGRU_SUC_SucursalId],
		[PROGRU_PROGI_ProgramaIdiomaId],
		[PROGRU_PAMOD_ModalidadId],
		[PROGRU_PAMODH_PAModalidadHorarioId],
		[PROGRU_PAC_ProgramacionAcademicaComercialId],
		fi.fechaInicio,
		fi.fechaFin,
		/* fecha fin inscripciones */
		[dbo].[fn_getProyeccionGrupoFechaFin](fi.fechaInicio, g2.diasFinInscripciones, g.modalidadId) as fechaFinInscripciones,
		[dbo].[fn_getProyeccionGrupoFechaFin](fi.fechaInicio, g2.diasFinInscripcionesBecas, g.modalidadId) as fechaFinInscripcionesBecas,
		g.nivel + 1 as nivel,
		[PROGRU_Grupo] + COALESCE([dbo].[fn_getProyeccionUltimoGrupoCreado](g.sedeId, g.programaIdiomaId, g.modalidadId, nivel, fi.fechaInicio), 0) as maxgrupo,
		[PROGRU_CMM_PlataformaId],
		[PROGRU_CMM_TipoGrupoId],
		NULL as empleadoId,
		[PROGRU_Multisede],
		[PROGRU_CalificacionMinima],
		[PROGRU_FaltasPermitidas],
		[PROGRU_Cupo],
		NULL as categoriaProfesor,
		NULL as sueldoProfesor,
		[dbo].[fn_getProyeccionGrupoCodigo]([PROGRU_GrupoId],  [PROGRU_Grupo] + COALESCE([dbo].[fn_getProyeccionUltimoGrupoCreado](g.sedeId, g.programaIdiomaId, g.modalidadId, nivel, fi.fechaInicio),0),  g.nivel) as codigo,
		[PROGRU_Aula],
		[PROGRU_GrupoId] as grupoReferenciaId,
		2000620 as estatusId,
		@creadoPorId as creadoPorId,
		CASE WHEN g.nivel = g.nivelFinal THEN 'No se puede proyectar un grupo de nivel final' ELSE NULL END as error
	INTO #tmpProgramasGrupos
	FROM ProgramasGrupos 
	LEFT JOIN (
		SELECT grupoId = [PROGRU_GrupoId],
			sedeId = [PROGRU_SUC_SucursalId],
			programaIdiomaId = [PROGRU_PROGI_ProgramaIdiomaId],
			idiomaId = [PROGI_CMM_Idioma],
			modalidadId = [PROGRU_PAMOD_ModalidadId],
			nivel = [PROGRU_Nivel],
			fecha = [PROGRU_FechaInicio],
			nivelFinal = [PROGI_NumeroNiveles]
		FROM
			[dbo].[ProgramasGrupos]
			INNER JOIN [dbo].[ProgramasIdiomas] ON [PROGRU_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId] 
	) g on g.grupoId = [PROGRU_GrupoId]
	LEFT JOIN (
		SELECT programacionDetalleId = siguiente , [PACD_CMM_IdiomaId] idiomaId,  [PACD_PAMOD_ModalidadId] modalidadId, fechas.[PACD_FechaInicio] fecha
		FROM
		(
			SELECT 
				*, LEAD([PACD_ProgramacionAcademicaComercialDetalleId]) OVER (ORDER BY [PACD_FechaInicio]) siguiente 
			FROM 
				[dbo].[ProgramacionAcademicaComercialDetalles]
		) fechas 
	)p ON  g.idiomaId = p.idiomaId AND g.modalidadId = p.modalidadId and g.fecha = p.fecha
	/* Obtener las siguientes fechas de inicio */
	LEFT JOIN (
	SELECT
		fechaInicio = [PACD_FechaInicio],
		fechaFin = [PACD_FechaFin],
		[PACD_ProgramacionAcademicaComercialDetalleId] as programacionDetalleId
	FROM
		[dbo].[ProgramacionAcademicaComercialDetalles]
	)fi ON fi.programacionDetalleId = p.programacionDetalleId
	LEFT JOIN (
		SELECT
		grupoId = [PROGRU_GrupoId],
		diasFinInscripciones = [PAMOD_DiasFinPeriodoInscripcion],
		diasFinInscripcionesBecas = [PAMOD_DiasFinPeriodoInscripcionBeca]
		FROM [dbo].[ProgramasGrupos] 
		INNER JOIN [dbo].[Sucursales] ON [PROGRU_SUC_SucursalId] = [SUC_SucursalId]
		LEFT JOIN [dbo].[SucursalesPlanteles] ON [PROGRU_SP_SucursalPlantelId] = [SP_SucursalPlantelId]
		INNER JOIN [dbo].[ProgramasIdiomas] ON [PROGRU_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId]
		INNER JOIN [dbo].[Programas] ON [PROGI_PROG_ProgramaId] = [PROG_ProgramaId]
		INNER JOIN [dbo].[ControlesMaestrosMultiples] ON [PROGI_CMM_Idioma] = [CMM_ControlId]
		INNER JOIN [dbo].[PAModalidades] ON [PROGRU_PAMOD_ModalidadId] = [PAMOD_ModalidadId]
		INNER JOIN [dbo].[PAModalidadesHorarios] ON [PROGRU_PAMODH_PAModalidadHorarioId] = [PAMODH_PAModalidadHorarioId]
	)g2 on g2.grupoId = [PROGRU_GrupoId]
	LEFT JOIN (
		SELECT PROGRU_GrupoReferenciaId referenciaId FROM ProgramasGrupos WHERE PROGRU_GrupoReferenciaId IS NOT NULL
	) proyectados ON referenciaId = [PROGRU_GrupoId]
	WHERE referenciaId IS NULL AND PROGRU_GrupoId in ( SELECT VALUE FROM STRING_SPLIT(@gruposIds, ',')  ) OPTION(RECOMPILE)
	
	DECLARE @idsProgramasGrupos TABLE (id INT UNIQUE, codigo varchar(50), fechaInicio DATE, grupoReferenciaId INT);

	INSERT INTO ProgramasGrupos
		([PROGRU_SUC_SucursalId],
			[PROGRU_PROGI_ProgramaIdiomaId],
			[PROGRU_PAMOD_ModalidadId],
			[PROGRU_PAMODH_PAModalidadHorarioId],
			[PROGRU_PAC_ProgramacionAcademicaComercialId],
			[PROGRU_FechaInicio],
			[PROGRU_FechaFin],
			[PROGRU_FechaFinInscripciones],
			[PROGRU_FechaFinInscripcionesBecas],
			[PROGRU_Nivel],
			[PROGRU_Grupo],
			[PROGRU_CMM_PlataformaId],
			[PROGRU_CMM_TipoGrupoId],
			[PROGRU_EMP_EmpleadoId],
			[PROGRU_Multisede],
			[PROGRU_CalificacionMinima],
			[PROGRU_FaltasPermitidas],
			[PROGRU_Cupo],
			[PROGRU_CategoriaProfesor],
			[PROGRU_SueldoProfesor],
			[PROGRU_Codigo],
			[PROGRU_Aula],
			[PROGRU_GrupoReferenciaId],
			[PROGRU_CMM_EstatusId],
			[PROGRU_USU_CreadoPorId])
	OUTPUT Inserted.PROGRU_GrupoId , Inserted.PROGRU_Codigo, inserted.PROGRU_FechaInicio, inserted.PROGRU_GrupoReferenciaId INTO @idsProgramasGrupos(id, codigo, fechaInicio, grupoReferenciaId)
	SELECT PROGRU_SUC_SucursalId, PROGRU_PROGI_ProgramaIdiomaId, PROGRU_PAMOD_ModalidadId, PROGRU_PAMODH_PAModalidadHorarioId, PROGRU_PAC_ProgramacionAcademicaComercialId, fechaInicio, fechaFin, fechaFinInscripciones, fechaFinInscripcionesBecas, nivel, maxgrupo, PROGRU_CMM_PlataformaId, PROGRU_CMM_TipoGrupoId, empleadoId, PROGRU_Multisede, PROGRU_CalificacionMinima, PROGRU_FaltasPermitidas, PROGRU_Cupo, categoriaProfesor, sueldoProfesor, codigo, PROGRU_Aula, grupoReferenciaId, estatusId, creadoPorId 
	FROM #tmpProgramasGrupos t
	WHERE t.error IS NULL;

	INSERT INTO @feedBack
	SELECT NULL AS estudiante, t.codigo AS grupo , t.error FROM #tmpProgramasGrupos t WHERE t.error IS NOT NULL;

	DECLARE @monedaId INT = (SELECT [MON_MonedaId] FROM [dbo].[Monedas] WHERE [MON_Predeterminada] = 1);
	DECLARE @medioPago INT = 1;
	DECLARE @notaVentaEstatusId INT = 2000502 /*Abierta*/
	DECLARE @metodoPago INT = NULL;

	SELECT CONVERT(varchar(50),NEWID()) as codigoOV,PROGRU_SUC_SucursalId as sucursalId,GETDATE() as fechaOV,GETDATE() as fechaRequerida,@monedaId as monedaId,0 as diasCredito,@medioPago as medioPago,@notaVentaEstatusId as estatusOvId,GETDATE() as fechaCreacion ,@creadoPorId as creadoporId,@metodoPago as metodoPago,
		INS_InscripcionId as inscripcionId
	INTO #tmpOV
	FROM Inscripciones 
	INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
	LEFT JOIN ProgramasGrupos on INS_PROGRU_GrupoId = PROGRU_GrupoId
	INNER JOIN @idsProgramasGrupos ng on ng.grupoReferenciaId = PROGRU_GrupoId
	WHERE INS_CMM_EstatusId NOT IN(2000512 /*CANCELADAS*/) AND INS_PROGRU_GrupoId IN ( SELECT VALUE FROM STRING_SPLIT(@gruposIds, ',')  ) OPTION(RECOMPILE);

	/* Ordenes de Venta */
	INSERT INTO [dbo].[OrdenesVenta]
	([OV_Codigo],[OV_SUC_SucursalId],[OV_FechaOV],[OV_FechaRequerida],[OV_MON_MonedaId],[OV_DiazCredito],[OV_MPPV_MedioPagoPVId],[OV_CMM_EstatusId],[OV_FechaCreacion],[OV_USU_CreadoPorId],[OV_CMM_MetodoPago])
	SELECT codigoOV, sucursalId, fechaOV, fechaRequerida, monedaId, diasCredito, medioPago, estatusOvId, fechaCreacion, creadoporId, metodoPago 
	FROM #tmpOV;

	DECLARE @tmpOVDetalles TABLE (id INT UNIQUE, idOV INT)

	/*Orden de Venta Detalles*/
	INSERT INTO [dbo].[OrdenesVentaDetalles]
	([OVD_OV_OrdenVentaId],[OVD_ART_ArticuloId],[OVD_UM_UnidadMedidaId],[OVD_FactorConversion],[OVD_Cantidad],[OVD_Precio],[OVD_FechaCreacion],[OVD_USU_CreadoPorId])
	OUTPUT Inserted.OVD_OrdenVentaDetalleId, inserted.OVD_OV_OrdenVentaId INTO @tmpOVDetalles(id, idOV)
	/* Detalle relacionado al curso */
	SELECT OV_OrdenVentaId, ART_ArticuloId, ART_UM_UnidadMedidaInventarioId, 1.00,1.00,[dbo].[getPrecioVenta](ART_ArticuloId, ART_ArticuloId, lp.listadoPrecioId), GETDATE(), @creadoPorId
	FROM Inscripciones 
	inner join #tmpOV ov on ov.inscripcionId = INS_InscripcionId
	inner join OrdenesVenta on OV_Codigo = ov.codigoOV
	INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
	LEFT JOIN ProgramasGrupos on INS_PROGRU_GrupoId = PROGRU_GrupoId
	LEFT JOIN [dbo].[Articulos] ON [PROGRU_PROGI_ProgramaIdiomaId] = [ART_PROGI_ProgramaIdiomaId] AND [PROGRU_PAMOD_ModalidadId] = [ART_PAMOD_ModalidadId] AND [ART_Activo] = 1
	LEFT JOIN (
				SELECT [SUC_SucursalId] as sucursalId, [LIPRED_ART_ArticuloId] as articuloId,
				[LIPRE_ListadoPrecioId] as listadoPrecioId,
				[LIPRED_ListadoPrecioDetalleId] as listadoPrecioDetalleId,
				precio = [LIPRED_Precio]
			FROM 
			[dbo].[Sucursales] 
			INNER JOIN [dbo].[ListadosPrecios] ON [SUC_LIPRE_ListadoPrecioId] = [LIPRE_ListadoPrecioId]
			INNER JOIN [dbo].[ListadosPreciosDetalles] ON [LIPRE_ListadoPrecioId] = [LIPRED_LIPRE_ListadoPrecioId]
			WHERE [SUC_Activo] = 1 and [LIPRE_Activo] = 1
	)lp on lp.sucursalId = PROGRU_SUC_SucursalId and lp.articuloId = ART_ArticuloId

	WHERE INS_PROGRU_GrupoId in ( SELECT VALUE FROM STRING_SPLIT(@gruposIds, ',')  ) OPTION(RECOMPILE) 

	/*Orden de Venta Detalles*/
	INSERT INTO [dbo].[OrdenesVentaDetalles]
	([OVD_OV_OrdenVentaId],[OVD_ART_ArticuloId],[OVD_UM_UnidadMedidaId],[OVD_FactorConversion],[OVD_Cantidad],[OVD_Precio],
	 [OVD_IVA],[OVD_IEPS],[OVD_IVAExento],[OVD_IEPSCuotaFija],[OVD_OVD_DetallePadreId],[OVD_FechaCreacion],[OVD_USU_CreadoPorId])
	SELECT OV_OrdenVentaId, ART_ArticuloId, unidadMedidaInventarioId, factorConversionVentas ,1.00, [dbo].[getPrecioVenta](ART_ArticuloId, ART_ArticuloId, lp.listadoPrecioId), 
			iva, ieps, ivaExento, iepsCuotaFija, idOvDetalle, GETDATE(), @creadoPorId
	FROM(

		/* Insertar los detalles 'libro' */
		SELECT OV_OrdenVentaId, ART_ArticuloId, 
				COALESCE(ART_UM_UnidadMedidaConversionVentasId, ART_UM_UnidadMedidaInventarioId) as unidadMedidaInventarioId,COALESCE(ART_FactorConversionVentas, 1.00) as factorConversionVentas,
				CASE ART_IVAExento WHEN 1 THEN 0.00 ELSE ART_IVA END as iva,
				CASE WHEN COALESCE(ART_IEPSCuotaFija, 0.00) > 0.00 THEN 0.00 ELSE COALESCE(ART_IEPS, 0.00) END as ieps,
				COALESCE(ART_IVAExento, 0) as ivaExento,
				COALESCE(ART_IEPSCuotaFija, 0.00) as iepsCuotaFija,
				ovd.id as  idOvDetalle,
				PROGRU_SUC_SucursalId as sucursalId
		FROM @tmpOVDetalles ovd 
		inner join OrdenesVenta on ovd.idOV = OV_OrdenVentaId
		inner join #tmpOV ov on ov.codigoOV = OV_Codigo
		inner join Inscripciones on ov.inscripcionId = INS_InscripcionId
		inner join Alumnos on INS_ALU_AlumnoId = ALU_AlumnoId
		inner join ProgramasGrupos on INS_PROGRU_GrupoId = PROGRU_GrupoId
		inner join ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		INNER JOIN [dbo].[ProgramasIdiomasLibrosMateriales] ON PROGILM_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		INNER JOIN [Articulos] ON [ART_ArticuloId] = [PROGILM_ART_ArticuloId]
		LEFT JOIN [dbo].[ProgramasIdiomasLibrosMaterialesReglas] ON [PROGILMR_PROGILM_ProgramaIdiomaLibroMaterialId] = [PROGILM_ProgramaIdiomaLibroMaterialId]

		WHERE
			[ART_Activo] = 1
			AND [PROGILM_Borrado] = 0
			AND [PROGILM_Nivel] = (PROGRU_Nivel+1)
			AND ([PROGILMR_ProgramaIdiomaLibroMaterialReglaId] IS NULL OR ([PROGILMR_CMM_CarreraId] = COALESCE(ALU_CMM_CarreraJOBSId, [PROGILMR_CMM_CarreraId])))

		UNION ALL

		/* Insertar los detalles 'certificados' */
		SELECT OV_OrdenVentaId, ART_ArticuloId, 
				COALESCE(ART_UM_UnidadMedidaConversionVentasId, ART_UM_UnidadMedidaInventarioId) as unidadMedidaInventarioId,COALESCE(ART_FactorConversionVentas, 1.00) as factorConversionVentas,
				CASE ART_IVAExento WHEN 1 THEN 0.00 ELSE ART_IVA END as iva,
				CASE WHEN COALESCE(ART_IEPSCuotaFija, 0.00) > 0.00 THEN 0.00 ELSE COALESCE(ART_IEPS, 0.00) END as ieps,
				COALESCE(ART_IVAExento, 0) as ivaExento,
				COALESCE(ART_IEPSCuotaFija, 0.00) as iepsCuotaFija,
				ovd.id as  idOvDetalle,
				PROGRU_SUC_SucursalId as sucursalId
		FROM @tmpOVDetalles ovd 
		inner join OrdenesVenta on ovd.idOV = OV_OrdenVentaId
		inner join #tmpOV ov on ov.codigoOV = OV_Codigo
		inner join Inscripciones on ov.inscripcionId = INS_InscripcionId
		inner join Alumnos on INS_ALU_AlumnoId = ALU_AlumnoId
		inner join ProgramasGrupos on INS_PROGRU_GrupoId = PROGRU_GrupoId
		inner join ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		INNER JOIN [ProgramasIdiomasCertificacion] ON PROGI_ProgramaIdiomaId = [PROGIC_PROGI_ProgramaIdiomaId]
		INNER JOIN [Articulos] ON [ART_ArticuloId] = [PROGIC_ART_ArticuloId]

		WHERE
			[ART_Activo] = 1
			AND [PROGIC_Borrado] = 0

	)art

	LEFT JOIN (
				SELECT [SUC_SucursalId] as sucursalId, [LIPRED_ART_ArticuloId] as articuloId,
				[LIPRE_ListadoPrecioId] as listadoPrecioId,
				[LIPRED_ListadoPrecioDetalleId] as listadoPrecioDetalleId,
				precio = [LIPRED_Precio]
			FROM 
			[dbo].[Sucursales] 
			INNER JOIN [dbo].[ListadosPrecios] ON [SUC_LIPRE_ListadoPrecioId] = [LIPRE_ListadoPrecioId]
			INNER JOIN [dbo].[ListadosPreciosDetalles] ON [LIPRE_ListadoPrecioId] = [LIPRED_LIPRE_ListadoPrecioId]
			WHERE [SUC_Activo] = 1 and [LIPRE_Activo] = 1
	)lp on lp.sucursalId = art.sucursalId and lp.articuloId = art.ART_ArticuloId

	/* Insertar la inscripcion */
	DECLARE @codigoInscripcion NVARCHAR(15) = NULL;
	DECLARE @siguiente INT = NULL;
	DECLARE @prefijo varchar(10) = NULL;
	DECLARE @digitos INT = NULL;
	SELECT @siguiente = [AUT_Siguiente], @prefijo = [AUT_Prefijo], @digitos= [AUT_Digitos]
	FROM [dbo].[Autonumericos] WHERE [AUT_Prefijo] = 'INS';
		
	DECLARE @tmpInscripciones TABLE (id INT UNIQUE)

	INSERT INTO [dbo].[Inscripciones]
		([INS_Codigo],[INS_OVD_OrdenVentaDetalleId],[INS_ALU_AlumnoId],[INS_PROGRU_GrupoId],[INS_CMM_InscripcionOrigenId],[INS_CMM_EstatusId],[INS_EntregaLibrosPendiente],[INS_CMM_InstitucionAcademicaId],[INS_Carrera],[INS_CMM_TurnoId],[INS_CMM_GradoId],[INS_Grupo],[INS_FechaCreacion],[INS_USU_CreadoPorId])
	OUTPUT Inserted.INS_InscripcionId INTO @tmpInscripciones(id)
	SELECT CONCAT(@prefijo, RIGHT('0000' + CAST((@siguiente + d.num) AS nvarchar(10)), @digitos)), idOvDetalle, ALU_AlumnoId, idGrupo, 2000980, 2000511, 0, ALU_CMM_PreparatoriaJOBSId, ALU_BachilleratoTecnologico, ALU_CMM_TurnoId, ALU_CMM_GradoId, ALU_Grupo, GETDATE(), @creadoPorId	
	FROM(
		SELECT INS_InscripcionId, ROW_NUMBER() OVER(order by ng.id, ALU_AlumnoId) as num, 
			ovd.id as idOvDetalle, ALU_AlumnoId, ng.id as idGrupo, ALU_CMM_PreparatoriaJOBSId, ALU_BachilleratoTecnologico, ALU_CMM_TurnoId, ALU_CMM_GradoId, ALU_Grupo
		FROM @tmpOVDetalles ovd 	
		inner join OrdenesVenta on ovd.idOV = OV_OrdenVentaId
		inner join #tmpOV ov on ov.codigoOV = OV_Codigo
		inner join Inscripciones on ov.inscripcionId = INS_InscripcionId
		inner join ProgramasGrupos on INS_PROGRU_GrupoId = PROGRU_GrupoId
		inner join #tmpProgramasGrupos tmpng on tmpng.grupoReferenciaId = PROGRU_GrupoId
		inner join @idsProgramasGrupos ng on ng.grupoReferenciaId = tmpng.grupoReferenciaId
		
		inner join Alumnos on INS_ALU_AlumnoId = ALU_AlumnoId

		where tmpng.error is null
	)d

	UPDATE [dbo].[Autonumericos] SET [AUT_Siguiente] = @siguiente + (SELECT COUNT(*) from #tmpOV) WHERE [AUT_Prefijo] = 'INS';

	/* Insertar la relacion alumno-grupo */
	INSERT INTO [dbo].[AlumnosGrupos]
		([ALUG_ALU_AlumnoId],[ALUG_PROGRU_GrupoId],[ALUG_Asistencias],[ALUG_Faltas],[ALUG_MinutosRetardo],[ALUG_CalificacionFinal],[ALUG_CMM_EstatusId],[ALUG_FechaCreacion],[ALUG_USU_CreadoPorId],[ALUG_INS_InscripcionId])
	SELECT INS_ALU_AlumnoId, INS_PROGRU_GrupoId, 0,0,0, NULL, 2000670, GETDATE(), @creadoPorId, INS_InscripcionId
	FROM @tmpInscripciones ins
	inner join Inscripciones on ins.id = INS_InscripcionId

	IF (SELECT COUNT(*) FROM @feedBack) = 0
		INSERT INTO @feedBack VALUES (NULL,NULL,N'Ok');
	
	SELECT * FROM @feedBack;
		
END