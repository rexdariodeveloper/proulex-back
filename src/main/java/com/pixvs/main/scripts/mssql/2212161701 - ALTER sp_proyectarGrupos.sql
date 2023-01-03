SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[sp_proyectarGrupos] @gruposIds NVARCHAR(MAX), @creadoPorId INT
AS
BEGIN
		DECLARE @feedBack TABLE (estudiante varchar(100), grupo varchar(100), mensaje varchar(2000))

		IF OBJECT_ID('tempdb..#tmpProgramasGrupos') IS NOT NULL
			DROP TABLE #tmpProgramasGrupos

		IF OBJECT_ID('tempdb..#tmpOV') IS NOT NULL
			DROP TABLE #tmpOV

         /* Constantes */
		 DECLARE @PROGRU_ACTIVO INT= 2000620;
         SELECT PROGRU_SUC_SucursalId,
                PROGRU_PROGI_ProgramaIdiomaId,
                PROGRU_PAMOD_ModalidadId,
                PROGRU_PAMODH_PAModalidadHorarioId,
                PROGRU_PAC_ProgramacionAcademicaComercialId,
                inicio fechaInicio,
                fin fechaFin,
                dbo.fn_getProyeccionGrupoFechaFin(inicio, PAMOD_DiasFinPeriodoInscripcion, PAMOD_ModalidadId) AS fechaFinInscripciones,
                dbo.fn_getProyeccionGrupoFechaFin(inicio, PAMOD_DiasFinPeriodoInscripcionBeca, PAMOD_ModalidadId) AS fechaFinInscripcionesBecas,
                PROGRU_Nivel + 1 AS nivel,
                PROGRU_Grupo + COALESCE(dbo.fn_getProyeccionUltimoGrupoCreado(PROGRU_SUC_SucursalId, PROGI_ProgramaIdiomaId, PAMOD_ModalidadId, PROGRU_Nivel, inicio), 0) AS maxgrupo,
                PROGRU_CMM_PlataformaId,
                PROGRU_CMM_TipoGrupoId,
                NULL AS empleadoId,
                PROGRU_Multisede,
                PROGRU_CalificacionMinima,
                PROGRU_FaltasPermitidas,
                PROGRU_Cupo,
                NULL AS categoriaProfesor,
                NULL AS sueldoProfesor,
                dbo.fn_getProyeccionGrupoCodigo(PROGRU_GrupoId, (PROGRU_Grupo - 1) + COALESCE(dbo.fn_getProyeccionUltimoGrupoCreado(PROGRU_SUC_SucursalId, PROGI_ProgramaIdiomaId, PAMOD_ModalidadId, PROGRU_Nivel, inicio), 0), PROGRU_Nivel) AS codigo,
                PROGRU_Aula,
                PROGRU_GrupoId AS grupoReferenciaId,
                @PROGRU_ACTIVO AS estatusId,
                @creadoPorId AS creadoPorId,
                CASE(PROGRU_Nivel + 1) WHEN(PROGI_NumeroNiveles + 1) THEN 'No se puede proyectar un grupo de nivel final' ELSE NULL END AS error
         INTO #tmpProgramasGrupos
         FROM dbo.ProgramasGrupos
              INNER JOIN dbo.ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
			  CROSS APPLY
			  (
					SELECT TOP 1
							PACD_FechaInicio AS inicio,
							PACD_FechaFin AS fin
					FROM dbo.ProgramacionAcademicaComercialDetalles
					WHERE PACD_PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
						  AND PACD_CMM_IdiomaId = PROGI_CMM_Idioma
						  AND PACD_FechaInicio > PROGRU_FechaFin
					ORDER BY PACD_FechaInicio
			  ) AS fechas
              INNER JOIN dbo.PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
              LEFT JOIN
			  (
					SELECT PROGRU_GrupoReferenciaId referenciaId
					FROM dbo.ProgramasGrupos
					WHERE PROGRU_GrupoReferenciaId IS NOT NULL
			  ) proyectados ON referenciaId = PROGRU_GrupoId
         WHERE referenciaId IS NULL
               AND PROGRU_GrupoId IN (SELECT VALUE FROM STRING_SPLIT(@gruposIds, ','))
		 OPTION(RECOMPILE);

         DECLARE @idsProgramasGrupos TABLE (id INT UNIQUE, codigo varchar(50), fechaInicio DATE, grupoReferenciaId INT);
         INSERT INTO ProgramasGrupos (
				PROGRU_SUC_SucursalId,
				PROGRU_PROGI_ProgramaIdiomaId,
				PROGRU_PAMOD_ModalidadId,
				PROGRU_PAMODH_PAModalidadHorarioId,
				PROGRU_PAC_ProgramacionAcademicaComercialId,
				PROGRU_FechaInicio,
				PROGRU_FechaFin,
				PROGRU_FechaFinInscripciones,
				PROGRU_FechaFinInscripcionesBecas,
				PROGRU_Nivel,
				PROGRU_Grupo,
				PROGRU_CMM_PlataformaId,
				PROGRU_CMM_TipoGrupoId,
				PROGRU_EMP_EmpleadoId,
				PROGRU_Multisede,
				PROGRU_CalificacionMinima,
				PROGRU_FaltasPermitidas,
				PROGRU_Cupo,
				PROGRU_CategoriaProfesor,
				PROGRU_SueldoProfesor,
				PROGRU_Codigo,
				PROGRU_Aula,
				PROGRU_GrupoReferenciaId,
				PROGRU_CMM_EstatusId,
				PROGRU_USU_CreadoPorId
		 )
         OUTPUT Inserted.PROGRU_GrupoId , Inserted.PROGRU_Codigo, inserted.PROGRU_FechaInicio, inserted.PROGRU_GrupoReferenciaId INTO @idsProgramasGrupos(id, codigo, fechaInicio, grupoReferenciaId)
		 SELECT [PROGRU_SUC_SucursalId], [PROGRU_PROGI_ProgramaIdiomaId], [PROGRU_PAMOD_ModalidadId], [PROGRU_PAMODH_PAModalidadHorarioId], [PROGRU_PAC_ProgramacionAcademicaComercialId], fechaInicio, fechaFin, fechaFinInscripciones, fechaFinInscripcionesBecas, nivel, maxgrupo, [PROGRU_CMM_PlataformaId], [PROGRU_CMM_TipoGrupoId], empleadoId, PROGRU_Multisede, PROGRU_CalificacionMinima, PROGRU_FaltasPermitidas, PROGRU_Cupo, categoriaProfesor, sueldoProfesor, codigo, PROGRU_Aula, grupoReferenciaId, estatusId, creadoPorId 
		 FROM #tmpProgramasGrupos t
		 WHERE t.error IS NULL;

         INSERT INTO @feedBack
		 SELECT NULL AS estudiante, t.codigo AS grupo , t.error FROM #tmpProgramasGrupos t WHERE t.error IS NOT NULL;
		 
		 DECLARE @monedaId INT = (SELECT [MON_MonedaId] FROM [dbo].[Monedas] WHERE [MON_Predeterminada] = 1);
		 DECLARE @medioPago INT = NULL;
		 DECLARE @notaVentaEstatusId INT = 2000502 /*Abierta*/
		 DECLARE @metodoPago INT = NULL;

         SELECT CONVERT(VARCHAR(50), NEWID()) AS codigoOV,
                OV_SUC_SucursalId AS sucursalId,
                GETDATE() AS fechaOV,
                GETDATE() AS fechaRequerida,
                @monedaId AS monedaId,
                0 AS diasCredito,
                @medioPago AS medioPago,
                @notaVentaEstatusId AS estatusOvId,
                GETDATE() AS fechaCreacion,
                @creadoPorId AS creadoporId,
                @metodoPago AS metodoPago,
                INS_InscripcionId AS inscripcionId
         INTO #tmpOV
         FROM dbo.Inscripciones
              INNER JOIN dbo.Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
              LEFT JOIN dbo.ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
			  LEFT JOIN dbo.AlumnosGrupos ON INS_InscripcionId = ALUG_INS_InscripcionId
              INNER JOIN @idsProgramasGrupos ng ON ng.grupoReferenciaId = PROGRU_GrupoId
              INNER JOIN dbo.OrdenesVentaDetalles ON INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
              INNER JOIN dbo.OrdenesVenta ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
              INNER JOIN dbo.ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
              LEFT JOIN
			  (
					SELECT INSSG_PROG_ProgramaId programaId,
						   INSSG_PAMOD_ModalidadId modalidadId,
						   INSSG_CMM_IdiomaId idiomaId,
						   INSSG_Nivel nivel,
						   INSSG_ALU_AlumnoId alumnoId
					FROM dbo.InscripcionesSinGrupo
					WHERE INSSG_CMM_EstatusId IN(2000540, 2000541)
			  ) inssg ON inssg.programaId = PROGI_PROG_ProgramaId
					   AND inssg.modalidadId = PROGRU_PAMOD_ModalidadId
					   AND inssg.nivel = (PROGRU_Nivel + 1)
					   AND inssg.idiomaId = PROGI_CMM_Idioma
					   AND inssg.alumnoId = ALU_AlumnoId
         WHERE INS_CMM_EstatusId NOT IN (2000512 /*CANCELADAS*/)
               AND inssg.programaId IS NULL /*Sin inscripcion sin grupo*/
			   AND INS_PROGRU_GrupoId IN (SELECT VALUE FROM STRING_SPLIT(@gruposIds, ','))
			   AND 1 = CASE WHEN PROGRU_GrupoId IS NULL OR ALUG_AlumnoGrupoId IS NULL OR PROGRU_CMM_EstatusId = 2000620 /*Activo*/ THEN 1 ELSE 
							  CASE WHEN PROGRU_CMM_EstatusId = 2000621 /*Finalizado*/ AND ALUG_CMM_EstatusId IN (2000670, 2000671, 2000675) /*Registrado, Activo, Aprobado*/ THEN 1 ELSE 0 END END
		 OPTION(RECOMPILE);
		
		/* Ordenes de Venta */
		INSERT INTO dbo.OrdenesVenta (
			 OV_Codigo,
			 OV_SUC_SucursalId,
			 OV_FechaOV,
			 OV_FechaRequerida,
			 OV_MON_MonedaId,
			 OV_DiazCredito,
			 OV_MPPV_MedioPagoPVId,
			 OV_CMM_EstatusId,
			 OV_FechaCreacion,
			 OV_USU_CreadoPorId,
			 OV_CMM_MetodoPago,
			 OV_TipoCambio,
			 OV_MON_MonedaSinConvertirId
		)
        SELECT codigoOV,
                sucursalId,
                fechaOV,
                fechaRequerida,
                monedaId,
                diasCredito,
                medioPago,
                estatusOvId,
                fechaCreacion,
                creadoporId,
                metodoPago,
                1,
                monedaId
        FROM #tmpOV;
		
		/*Orden de Venta Detalles*/
		DECLARE @tmpOVDetalles TABLE (id INT UNIQUE, idOV INT, articuloId INT)
		INSERT INTO dbo.OrdenesVentaDetalles (
			 OVD_OV_OrdenVentaId,
			 OVD_ART_ArticuloId,
			 OVD_UM_UnidadMedidaId,
			 OVD_FactorConversion,
			 OVD_Cantidad,
			 OVD_Precio,
			 OVD_PrecioSinConvertir,
			 OVD_FechaCreacion,
			 OVD_USU_CreadoPorId
		)
		OUTPUT Inserted.OVD_OrdenVentaDetalleId, inserted.OVD_OV_OrdenVentaId, inserted.OVD_ART_ArticuloId INTO @tmpOVDetalles(id, idOV, articuloId)
		/* Detalle relacionado al curso */
        SELECT OV_OrdenVentaId,
                ART_ArticuloId,
                ART_UM_UnidadMedidaInventarioId,
                1.00,
                1.00,
                dbo.getPrecioVenta(ART_ArticuloId, ART_ArticuloId, lp.listadoPrecioId),
                dbo.getPrecioVenta(ART_ArticuloId, ART_ArticuloId, lp.listadoPrecioId),
                GETDATE(),
                @creadoPorId
        FROM dbo.Inscripciones
                INNER JOIN #tmpOV ov ON ov.inscripcionId = INS_InscripcionId
                INNER JOIN dbo.OrdenesVenta ON OV_Codigo = ov.codigoOV
                INNER JOIN dbo.Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
                LEFT JOIN dbo.ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
                LEFT JOIN dbo.ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
                LEFT JOIN dbo.Articulos ON PROGRU_PROGI_ProgramaIdiomaId = ART_PROGI_ProgramaIdiomaId
                                        AND PROGRU_PAMOD_ModalidadId = ART_PAMOD_ModalidadId
                                        AND ART_Activo = 1
                                        AND ((PROGI_AgruparListadosPreciosPorTipoGrupo = 0 AND ART_CMM_TipoGrupoId IS NULL)
                                                OR (PROGI_AgruparListadosPreciosPorTipoGrupo = 1 AND ART_CMM_TipoGrupoId = PROGRU_CMM_TipoGrupoId))
                LEFT JOIN
				(
					SELECT SUC_SucursalId AS sucursalId,
							LIPRED_ART_ArticuloId AS articuloId,
							LIPRE_ListadoPrecioId AS listadoPrecioId,
							LIPRED_ListadoPrecioDetalleId AS listadoPrecioDetalleId,
							precio = LIPRED_Precio
					FROM dbo.Sucursales
							INNER JOIN dbo.ListadosPrecios ON SUC_LIPRE_ListadoPrecioId = LIPRE_ListadoPrecioId
							INNER JOIN dbo.ListadosPreciosDetalles ON LIPRE_ListadoPrecioId = LIPRED_LIPRE_ListadoPrecioId
					WHERE SUC_Activo = 1
							AND LIPRE_Activo = 1
				) lp ON lp.sucursalId = PROGRU_SUC_SucursalId
					AND lp.articuloId = ART_ArticuloId
        WHERE INS_PROGRU_GrupoId IN (SELECT VALUE FROM STRING_SPLIT(@gruposIds, ','))
		OPTION(RECOMPILE)
		
		/*Orden de Venta Detalles*/
		INSERT INTO dbo.OrdenesVentaDetalles (
			 OVD_OV_OrdenVentaId,
			 OVD_ART_ArticuloId,
			 OVD_UM_UnidadMedidaId,
			 OVD_FactorConversion,
			 OVD_Cantidad,
			 OVD_Precio,
			 OVD_PrecioSinConvertir,
			 OVD_IVA,
			 OVD_IEPS,
			 OVD_IVAExento,
			 OVD_IEPSCuotaFija,
			 OVD_OVD_DetallePadreId,
			 OVD_FechaCreacion,
			 OVD_USU_CreadoPorId
		)
        SELECT OV_OrdenVentaId,
                ART_ArticuloId,
                unidadMedidaInventarioId,
                factorConversionVentas,
                1.00,
                dbo.getPrecioVenta(padreId, ART_ArticuloId, lp.listadoPrecioId),
                dbo.getPrecioVenta(padreId, ART_ArticuloId, lp.listadoPrecioId),
                iva,
                ieps,
                ivaExento,
                iepsCuotaFija,
                idOvDetalle,
                GETDATE(),
                @creadoPorId
        FROM
		(
			/* Insertar los detalles 'libro' */
			SELECT OV_OrdenVentaId,
					ART_ArticuloId,
					COALESCE(ART_UM_UnidadMedidaConversionVentasId, ART_UM_UnidadMedidaInventarioId) AS unidadMedidaInventarioId,
					COALESCE(ART_FactorConversionVentas, 1.00) AS factorConversionVentas,
					CASE ART_IVAExento WHEN 1 THEN 0.00 ELSE ART_IVA END AS iva,
					CASE WHEN COALESCE(ART_IEPSCuotaFija, 0.00) > 0.00 THEN 0.00 ELSE COALESCE(ART_IEPS, 0.00) END AS ieps,
					COALESCE(ART_IVAExento, 0) AS ivaExento,
					COALESCE(ART_IEPSCuotaFija, 0.00) AS iepsCuotaFija,
					ovd.id AS idOvDetalle,
					PROGRU_SUC_SucursalId AS sucursalId,
					ovd.articuloId AS padreId
			FROM @tmpOVDetalles ovd
					INNER JOIN dbo.OrdenesVenta ON ovd.idOV = OV_OrdenVentaId
					INNER JOIN #tmpOV ov ON ov.codigoOV = OV_Codigo
					INNER JOIN dbo.Inscripciones ON ov.inscripcionId = INS_InscripcionId
					INNER JOIN dbo.Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
					INNER JOIN dbo.ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
					INNER JOIN dbo.ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
					INNER JOIN dbo.ProgramasIdiomasLibrosMateriales ON PROGILM_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
					INNER JOIN dbo.Articulos ON ART_ArticuloId = PROGILM_ART_ArticuloId
					LEFT JOIN dbo.ProgramasIdiomasLibrosMaterialesReglas ON PROGILMR_PROGILM_ProgramaIdiomaLibroMaterialId = PROGILM_ProgramaIdiomaLibroMaterialId
			WHERE ART_Activo = 1
					AND PROGILM_Borrado = 0
					AND PROGILM_Nivel = (PROGRU_Nivel + 1)
					AND (PROGILMR_ProgramaIdiomaLibroMaterialReglaId IS NULL OR (PROGILMR_CMM_CarreraId = COALESCE(ALU_CMM_CarreraJOBSId, PROGILMR_CMM_CarreraId)))
					
			UNION ALL
					
			/* Insertar los detalles 'certificados' */
			SELECT OV_OrdenVentaId,
					ART_ArticuloId,
					COALESCE(ART_UM_UnidadMedidaConversionVentasId, ART_UM_UnidadMedidaInventarioId) AS unidadMedidaInventarioId,
					COALESCE(ART_FactorConversionVentas, 1.00) AS factorConversionVentas,
					CASE ART_IVAExento WHEN 1 THEN 0.00 ELSE ART_IVA END AS iva,
					CASE WHEN COALESCE(ART_IEPSCuotaFija, 0.00) > 0.00 THEN 0.00 ELSE COALESCE(ART_IEPS, 0.00) END AS ieps,
					COALESCE(ART_IVAExento, 0) AS ivaExento,
					COALESCE(ART_IEPSCuotaFija, 0.00) AS iepsCuotaFija,
					ovd.id AS idOvDetalle,
					PROGRU_SUC_SucursalId AS sucursalId,
					ovd.articuloId AS padreId
			FROM @tmpOVDetalles ovd
					INNER JOIN dbo.OrdenesVenta ON ovd.idOV = OV_OrdenVentaId
					INNER JOIN #tmpOV ov ON ov.codigoOV = OV_Codigo
					INNER JOIN dbo.Inscripciones ON ov.inscripcionId = INS_InscripcionId
					INNER JOIN dbo.Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
					INNER JOIN dbo.ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
					INNER JOIN dbo.ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
					INNER JOIN dbo.ProgramasIdiomasCertificacion ON PROGI_ProgramaIdiomaId = PROGIC_PROGI_ProgramaIdiomaId
					INNER JOIN dbo.Articulos ON ART_ArticuloId = PROGIC_ART_ArticuloId
			WHERE ART_Activo = 1
					AND PROGIC_Borrado = 0
		) AS art
		LEFT JOIN
		(
			SELECT SUC_SucursalId AS sucursalId,
					LIPRED_ART_ArticuloId AS articuloId,
					LIPRE_ListadoPrecioId AS listadoPrecioId,
					LIPRED_ListadoPrecioDetalleId AS listadoPrecioDetalleId,
					precio = LIPRED_Precio
			FROM dbo.Sucursales
					INNER JOIN dbo.ListadosPrecios ON SUC_LIPRE_ListadoPrecioId = LIPRE_ListadoPrecioId
					INNER JOIN dbo.ListadosPreciosDetalles ON LIPRE_ListadoPrecioId = LIPRED_LIPRE_ListadoPrecioId
			WHERE SUC_Activo = 1
					AND LIPRE_Activo = 1
		) AS lp ON lp.sucursalId = art.sucursalId AND lp.articuloId = art.ART_ArticuloId
				
		/* Insertar la inscripcion */
		DECLARE @codigoInscripcion NVARCHAR(15)= NULL;
		DECLARE @siguiente INT= NULL;
		DECLARE @prefijo VARCHAR(10)= NULL;
		DECLARE @digitos INT= NULL;

		SELECT @siguiente = AUT_Siguiente,
			@prefijo = AUT_Prefijo,
			@digitos = AUT_Digitos
		FROM dbo.Autonumericos
		WHERE AUT_Prefijo = 'INS';
				 
		DECLARE @tmpInscripciones TABLE (id INT UNIQUE)
		INSERT INTO dbo.Inscripciones (
			INS_Codigo,
			INS_OVD_OrdenVentaDetalleId,
			INS_ALU_AlumnoId,
			INS_PROGRU_GrupoId,
			INS_CMM_InscripcionOrigenId,
			INS_CMM_EstatusId,
			INS_EntregaLibrosPendiente,
			INS_CMM_InstitucionAcademicaId,
			INS_Carrera,
			INS_CMM_TurnoId,
			INS_CMM_GradoId,
			INS_Grupo,
			INS_FechaCreacion,
			INS_USU_CreadoPorId
		)
		OUTPUT Inserted.INS_InscripcionId INTO @tmpInscripciones(id)
        SELECT CONCAT(@prefijo, RIGHT('0000'+CAST((@siguiente + d.num) AS NVARCHAR(10)), @digitos)),
                idOvDetalle,
                ALU_AlumnoId,
                idGrupo,
                2000980,
                2000511,
                0,
                ALU_CMM_PreparatoriaJOBSId,
                ALU_BachilleratoTecnologico,
                ALU_CMM_TurnoId,
                ALU_CMM_GradoId,
                ALU_Grupo,
                GETDATE(),
                @creadoPorId
        FROM
		(
			SELECT INS_InscripcionId,
					ROW_NUMBER() OVER(ORDER BY ng.id, ALU_AlumnoId) AS num,
					ovd.id AS idOvDetalle,
					ALU_AlumnoId,
					ng.id AS idGrupo,
					ALU_CMM_PreparatoriaJOBSId,
					ALU_BachilleratoTecnologico,
					ALU_CMM_TurnoId,
					ALU_CMM_GradoId,
					ALU_Grupo
			FROM @tmpOVDetalles ovd
					INNER JOIN dbo.OrdenesVenta ON ovd.idOV = OV_OrdenVentaId
					INNER JOIN #tmpOV ov ON ov.codigoOV = OV_Codigo
					INNER JOIN dbo.Inscripciones ON ov.inscripcionId = INS_InscripcionId
					INNER JOIN dbo.ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
					INNER JOIN #tmpProgramasGrupos tmpng ON tmpng.grupoReferenciaId = PROGRU_GrupoId
					INNER JOIN @idsProgramasGrupos ng ON ng.grupoReferenciaId = tmpng.grupoReferenciaId
					INNER JOIN dbo.Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
			WHERE tmpng.error IS NULL
		) AS d
				
		/* Actualizar los autonumericos al ultimo insertado */
		UPDATE dbo.Autonumericos SET AUT_Siguiente = @siguiente + (SELECT COUNT(*) FROM #tmpOV) WHERE AUT_Prefijo = 'INS';
				
		/* Insertar la relacion alumno-grupo */
		INSERT INTO dbo.AlumnosGrupos (
				ALUG_ALU_AlumnoId,
				ALUG_PROGRU_GrupoId,
				ALUG_Asistencias,
				ALUG_Faltas,
				ALUG_MinutosRetardo,
				ALUG_CalificacionFinal,
				ALUG_CMM_EstatusId,
				ALUG_FechaCreacion,
				ALUG_USU_CreadoPorId,
				ALUG_INS_InscripcionId
		)
        SELECT INS_ALU_AlumnoId,
                INS_PROGRU_GrupoId,
                0,
                0,
                0,
                NULL,
                2000670,
                GETDATE(),
                @creadoPorId,
                INS_InscripcionId
        FROM @tmpInscripciones ins
                INNER JOIN dbo.Inscripciones ON ins.id = INS_InscripcionId;
				
		/* Verificar si hay mensajes de feedback */
		IF (SELECT COUNT(*) FROM @feedBack) = 0
		INSERT INTO @feedBack VALUES (NULL, NULL, N'Ok');
		
		SELECT * FROM @feedBack;
END