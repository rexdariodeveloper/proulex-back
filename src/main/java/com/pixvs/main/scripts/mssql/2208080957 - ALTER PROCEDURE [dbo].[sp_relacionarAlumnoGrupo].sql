SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[sp_relacionarAlumnoGrupo]
@alumnoId INT, @grupoId INT, @origenId INT, @creadoPorId INT, @esApertura BIT, @feedback NVARCHAR(250) OUTPUT
AS
	BEGIN
		BEGIN TRANSACTION;
		BEGIN TRY
			/* CMM_OV_Estatus */
			DECLARE @CMM_OV_Estatus_Abierta		INT = 2000502;
			DECLARE @CMM_OV_Estatus_Cancelada	INT = 2000504;
			DECLARE @CMM_OV_Estatus_Facturada	INT = 2000507;
			DECLARE @CMM_OV_Estatus_Pagada		INT = 2000508;
			/* CMM_INS_Estatus */
			DECLARE @CMM_INS_Estatus_Pagada		INT = 2000510;
			DECLARE @CMM_INS_Estatus_Pendiente	INT = 2000511;
			DECLARE @CMM_INS_Estatus_Cancelada	INT = 2000512;
			DECLARE @CMM_INS_Estatus_Baja		INT = 2000513;
			/* CMM_INSSG_Estatus */
			DECLARE @CMM_INSSG_Estatus_Pagada	INT = 2000540;
			DECLARE @CMM_INSSG_Estatus_Asignada	INT = 2000542;
			/* CMM_PROGRU_Estatus */
			DECLARE @CMM_PROGRU_Estatus_Activo     INT = 2000620;
			DECLARE @CMM_PROGRU_Estatus_Finalizado INT = 2000621;
			DECLARE @CMM_PROGRU_Estatus_Cancelado  INT = 2000622;
			/* CMM_ALUEC_Estatus */
			DECLARE @CMM_ALUEC_Estatus_En_proceso	INT = 2000640;
			DECLARE @CMM_ALUEC_Estatus_Finalizado	INT = 2000641;
			DECLARE @CMM_ALUEC_Estatus_Cancelado	INT = 2000642;
			DECLARE @CMM_ALUEC_Estatus_Relacionado	INT = 2000643;
			/* CMM_ALUEC_Tipo */
			DECLARE @CMM_ALUEC_Tipo_Examen			INT = 2000650;
			DECLARE @CMM_ALUEC_Tipo_Certificacion	INT = 2000651;
			/* CMM_ALUG_Estatus */
			DECLARE @CMM_ALUG_Estatus_Registrado	INT = 2000670;
			DECLARE @CMM_ALUG_Estatus_Aprobado		INT = 2000675;
			DECLARE @CMM_ALUG_Estatus_Reprobado		INT = 2000676;
			/* CMM_OV_MetodoPago */
			DECLARE @CMM_OV_MetodoPago_PUE	INT = 2000710;
			DECLARE @CMM_OV_MetodoPago_PPD	INT = 2000711;
			/* CMM_INO_InscripcionOrigen */
			DECLARE @CMM_INO_InscripcionOrigen_Proyeccion INT = 2000980;
			DECLARE @CMM_INO_InscripcionOrigen_Plantilla  INT = 2000981;
			DECLARE @CMM_INO_InscripcionOrigen_PuntoVenta INT = 2000982;

			DECLARE @msg NVARCHAR(250) = NULL;
			DECLARE @codigoGrupo NVARCHAR(150) = NULL;
			DECLARE @estatusGrupo INT = NULL;
			DECLARE @articuloId INT = NULL;
			DECLARE @umId INT = NULL;
			DECLARE @nivel INT = NULL;
			DECLARE @programaId INT = NULL;
			DECLARE @idiomaId INT = NULL;
			DECLARE @fechaFin DATE = NULL;
			DECLARE @horarioId INT = NULL;
			DECLARE @sedeId INT = NULL;
			DECLARE @referenciaId INT = NULL;

			SELECT 
				@codigoGrupo = [PROGRU_Codigo], 
				@estatusGrupo = [PROGRU_CMM_EstatusId],
				@articuloId = [ART_ArticuloId],
				@umId = [ART_UM_UnidadMedidaInventarioId],
				@nivel = [PROGRU_Nivel],
				@programaId = [PROGI_PROG_ProgramaId],
				@idiomaId = [PROGI_CMM_Idioma],
				@fechaFin = [PROGRU_FechaFinInscripcionesBecas],
				@horarioId = [PROGRU_PAMODH_PAModalidadHorarioId],
				@sedeId = [PROGRU_SUC_SucursalId],
				@referenciaId = [PROGRU_GrupoReferenciaId]
			FROM [dbo].[ProgramasGrupos] 
				LEFT JOIN [dbo].[Articulos] ON [PROGRU_PROGI_ProgramaIdiomaId] = [ART_PROGI_ProgramaIdiomaId] AND [PROGRU_PAMOD_ModalidadId] = [ART_PAMOD_ModalidadId] AND [ART_Activo] = 1
				LEFT JOIN [dbo].[ProgramasIdiomas] ON [PROGRU_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId]
			WHERE PROGRU_GrupoId = @grupoId;
			/* Validar si el grupo existe y esta activo */
			IF @estatusGrupo IS NULL OR @estatusGrupo <> @CMM_PROGRU_Estatus_Activo
			BEGIN
				SET @msg = (SELECT FORMATMESSAGE(N'El grupo %s no se encuentra activo', @codigoGrupo));
				THROW 50000, @msg, 1
			END
			/* Validar si el grupo cuenta con un articulo relacionado */
			IF @articuloId IS NULL
			BEGIN
				SET @msg = (SELECT FORMATMESSAGE(N'El grupo %s no cuenta con un artículo relacionado', @codigoGrupo));
				THROW 50000, @msg, 1
			END
			/* Si es una proyeccion, cambiar la sede por la sede de la nota de venta anterior */
			IF @origenId = @CMM_INO_InscripcionOrigen_Proyeccion
			BEGIN
				DECLARE @sedeAnteriorId INT = NULL;
				SELECT 
					@sedeAnteriorId = OV_SUC_SucursalId
				FROM
				[dbo].[Inscripciones]
				INNER JOIN [dbo].[OrdenesVentaDetalles] ON [INS_OVD_OrdenVentaDetalleId] = [OVD_OrdenVentaDetalleId]
				INNER JOIN [dbo].[OrdenesVenta] ON [OVD_OV_OrdenVentaId] = [OV_OrdenVentaId]
				WHERE
					[INS_CMM_EstatusId] = @CMM_INS_Estatus_Pagada AND [INS_ALU_AlumnoId] = @alumnoId AND [INS_PROGRU_GrupoId] = @referenciaId;
				SET @sedeId = COALESCE(@sedeAnteriorId, @sedeId);
			END
			DECLARE @lp INT = NULL;
			DECLARE @lpv INT = NULL;
			DECLARE @precio DECIMAL(28,10) = NULL;
			SELECT 
				@lp = [LIPRE_ListadoPrecioId],
				@lpv = [LIPRED_ListadoPrecioDetalleId],
				@precio = [LIPRED_Precio]
			FROM 
			[dbo].[Sucursales] 
			INNER JOIN [dbo].[ListadosPrecios] ON [SUC_LIPRE_ListadoPrecioId] = [LIPRE_ListadoPrecioId]
			INNER JOIN [dbo].[ListadosPreciosDetalles] ON [LIPRE_ListadoPrecioId] = [LIPRED_LIPRE_ListadoPrecioId]
			WHERE
				[SUC_SucursalId] = @sedeId
				AND [LIPRED_ART_ArticuloId] = @articuloId
				AND [SUC_Activo] = 1
				AND [LIPRE_Activo] = 1;
			/* Validar si el grupo tiene una lista de precios relacionada */
			IF @lpv IS NULL
			BEGIN
				SET @msg = (SELECT FORMATMESSAGE(N'El grupo %s no cuenta con una relación en la lista de precios', @codigoGrupo));
				THROW 50000, @msg, 1
			END
			/* Revisar si el alumno tiene otras inscripciones */
			DECLARE @alumnoNuevo BIT = 0;
			SELECT 
				@alumnoNuevo = CAST(CASE WHEN COUNT(*) > 0 THEN 0 ELSE 1 END AS BIT) 
			FROM 
				[dbo].[Inscripciones]
			WHERE 
				INS_CMM_EstatusId <> @CMM_INS_Estatus_Cancelada AND INS_ALU_AlumnoId = @alumnoId;
			/* Si no es de plantilla, omitir la validación */
			IF @origenId <> @CMM_INO_InscripcionOrigen_Plantilla
				SET @fechaFin = CAST(GETDATE() AS date);
			/* Validar fecha fin de inscripciones */
			IF @fechaFin < CAST(GETDATE() AS date)
			BEGIN
				SET @msg = (SELECT FORMATMESSAGE(N'El periodo de inscripciones ha finalizado para el grupo %s', @codigoGrupo));
				THROW 50000, @msg, 1
			END
			/* Validar si el grupo tiene cupo disponible */
			IF [dbo].[fn_getCupoDisponible](@grupoId) =  0
			BEGIN
				SET @msg = (SELECT FORMATMESSAGE(N'El grupo %s no cuenta con cupo disponible', @codigoGrupo));
				THROW 50000, @msg, 1
			END
			/* Recuperar los datos del alumno */
			DECLARE @codigoAlumno NVARCHAR(20) = NULL;
			DECLARE @referencia NVARCHAR(20) = NULL;
			DECLARE @centroId INT = NULL;
			DECLARE @carreraId INT = NULL;
			DECLARE @preparatoriaId INT = NULL;
			DECLARE @bachillerato NVARCHAR(512) = NULL;
			DECLARE @turnoId INT = NULL;
			DECLARE @gradoId INT = NULL;
			DECLARE @grupo NVARCHAR(10) = NULL;
			SELECT 
				@codigoAlumno = ALU_Codigo, 
				@referencia = [dbo].[fn_getDigitoVerificador](ALU_Codigo, 0), 
				@centroId = ALU_CMM_CentroUniversitarioJOBSId,  
				@carreraId = ALU_CMM_CarreraJOBSId, 
				@preparatoriaId = ALU_CMM_PreparatoriaJOBSId, 
				@bachillerato = ALU_BachilleratoTecnologico,
				@turnoId = ALU_CMM_TurnoId,
				@gradoId = ALU_CMM_GradoId,
				@grupo = ALU_Grupo
			FROM 
				Alumnos WHERE ALU_AlumnoId = @alumnoId;
			/* Validar si el nivel del nuevo grupo es congruente con la inscripcion anterior */
			IF @origenId <> @CMM_INO_InscripcionOrigen_Proyeccion AND @alumnoNuevo = 0 AND [dbo].[fn_getNivelAlumnoPermiteInscripcion] (@alumnoId,@idiomaId,@programaId) < @nivel
			BEGIN
				SET @msg = (SELECT FORMATMESSAGE(N'El alumno %s no es candidato al grupo %s', @codigoAlumno, @codigoGrupo));
				THROW 50000, @msg, 1
			END
			DECLARE @monedaId INT = (SELECT [MON_MonedaId] FROM [dbo].[Monedas] WHERE [MON_Predeterminada] = 1);
			/* Validar horarios duplicados */
			IF @origenId <> @CMM_INO_InscripcionOrigen_Proyeccion AND [dbo].[fn_getAlumnoInterferenciaHorario](@alumnoId, @horarioId) = 1
			BEGIN
				SET @msg = (SELECT FORMATMESSAGE(N'El alumno %s ya cuenta con una inscripción para el mismo horario', @codigoAlumno));
				THROW 50000, @msg, 1
			END
			/* Validar duplicados por programa o idioma */
			IF @origenId <> @CMM_INO_InscripcionOrigen_Proyeccion AND [dbo].[fn_getAlumnoCursandoIdioma] (@alumnoId, @idiomaId, CASE @origenId WHEN @CMM_INO_InscripcionOrigen_Plantilla THEN @programaId ELSE NULL END,0) = 1
			BEGIN
				SET @msg = (SELECT FORMATMESSAGE(N'El alumno %s ya cuenta con una inscripción para el mismo programa', @codigoAlumno));
				THROW 50000, @msg, 1
			END
			/* TODO: Diseñar validación para duplicados en proyección de grupos */
			/* Validar examen de ubicacion */
			DECLARE @nivelExamen INT = NULL;
			SELECT 
				@nivelExamen = [ALUEC_Nivel] 
			FROM 
				[dbo].[AlumnosExamenesCertificaciones] 
			WHERE 
				[ALUEC_CMM_TipoId] = @CMM_ALUEC_Tipo_Examen AND [ALUEC_CMM_EstatusId] = @CMM_ALUEC_Estatus_Finalizado AND [ALUEC_ALU_AlumnoId] = @alumnoId;
			IF @nivel > COALESCE(@nivelExamen, @nivel)
			BEGIN
				SET @msg = (SELECT FORMATMESSAGE(N'El alumno %s no es candidato para el nivel actual', @codigoAlumno));
				THROW 50000, @msg, 1
			END
			UPDATE [dbo].[Alumnos] SET [ALU_Referencia] = [dbo].[fn_getDigitoVerificador]([ALU_Codigo], 0) WHERE [ALU_AlumnoId] = @alumnoId;
			/* Insertar la cabecera de la OV */
			DECLARE @inscripcionEstatusId INT = @CMM_INS_Estatus_Pendiente;
			DECLARE @notaVentaEstatusId INT = @CMM_OV_Estatus_Abierta;
			DECLARE @medioPago INT = NULL;
			DECLARE @metodoPago INT = NULL;
			DECLARE @alumnoGrupoEstatusId INT = @CMM_ALUG_Estatus_Registrado;
			DECLARE @calificacion DECIMAL(28,10) = NULL;
			IF @origenId = @CMM_INO_InscripcionOrigen_Proyeccion
			BEGIN
				SELECT TOP 1
					@medioPago = [OV_MPPV_MedioPagoPVId],
					@metodoPago = [OV_CMM_MetodoPago],
					@notaVentaEstatusId = [OV_CMM_EstatusId],
					@inscripcionEstatusId = [INS_CMM_EstatusId]
				FROM 
					[dbo].[Inscripciones] 
					INNER JOIN [dbo].[OrdenesVentaDetalles] ON [INS_OVD_OrdenVentaDetalleId] = [OVD_OrdenVentaDetalleId]
					INNER JOIN [dbo].[OrdenesVenta] ON [OVD_OV_OrdenVentaId] = [OV_OrdenVentaId]
				WHERE 
					[INS_ALU_AlumnoId] = @alumnoId AND [INS_PROGRU_GrupoId] = @grupoId
				ORDER BY
					[INS_FechaCreacion] DESC;
			END
			IF @esApertura = 1
			BEGIN
				SET @medioPago = 1;
				SET @metodoPago = @CMM_OV_MetodoPago_PUE;
				SET @notaVentaEstatusId = @CMM_OV_Estatus_Pagada;
				SET @inscripcionEstatusId = @CMM_INS_Estatus_Pagada;
				SET @alumnoGrupoEstatusId = @CMM_ALUG_Estatus_Aprobado;
				SET @calificacion = 90.00;
			END
			INSERT INTO [dbo].[OrdenesVenta]
				([OV_Codigo],[OV_SUC_SucursalId],[OV_FechaOV],[OV_FechaRequerida],[OV_MON_MonedaId],[OV_DiazCredito],[OV_MPPV_MedioPagoPVId],[OV_CMM_EstatusId],[OV_FechaCreacion],[OV_USU_CreadoPorId],[OV_CMM_MetodoPago])
			VALUES
				(NEWID(),@sedeId,GETDATE(),GETDATE(),@monedaId,0,@medioPago,@notaVentaEstatusId,GETDATE(),@creadoPorId,@metodoPago);
			/* Recuperar el id de la OV */
			DECLARE @ovId INT = (SELECT SCOPE_IDENTITY());
			/* Insertar el detalle relacionado al curso */
			INSERT INTO [dbo].[OrdenesVentaDetalles]
				([OVD_OV_OrdenVentaId],[OVD_ART_ArticuloId],[OVD_UM_UnidadMedidaId],[OVD_FactorConversion],[OVD_Cantidad],[OVD_Precio],[OVD_FechaCreacion],[OVD_USU_CreadoPorId])
			VALUES
				(@ovId,@articuloId,@umId,1.00,1.00,CASE @esApertura WHEN 1 THEN 0.00 ELSE [dbo].[getPrecioVenta](@articuloId, @articuloId, @lp) END,GETDATE(),@creadoPorId);
			/* Recuperar el id del detalle padre */
			DECLARE @ovdId INT = (SELECT SCOPE_IDENTITY());
			/* Insertar los detalles 'libro' */
			INSERT INTO [dbo].[OrdenesVentaDetalles]
				([OVD_OV_OrdenVentaId],[OVD_ART_ArticuloId],[OVD_UM_UnidadMedidaId],[OVD_FactorConversion],[OVD_Cantidad],[OVD_Precio],[OVD_IVA],[OVD_IEPS],[OVD_IVAExento],[OVD_IEPSCuotaFija],[OVD_OVD_DetallePadreId],[OVD_FechaCreacion],[OVD_USU_CreadoPorId])
			SELECT 
				@ovId,
				ART_ArticuloId,
				COALESCE(ART_UM_UnidadMedidaConversionVentasId, ART_UM_UnidadMedidaInventarioId),
				COALESCE(ART_FactorConversionVentas, 1.00),
				1.00,
				CASE @esApertura WHEN 1 THEN 0.00 ELSE [dbo].[getPrecioVenta](ART_ArticuloId, @articuloId, @lp) END,
				CASE ART_IVAExento WHEN 1 THEN 0.00 ELSE ART_IVA END,
				CASE WHEN COALESCE(ART_IEPSCuotaFija, 0.00) > 0.00 THEN 0.00 ELSE COALESCE(ART_IEPS, 0.00) END,
				COALESCE(ART_IVAExento, 0),
				COALESCE(ART_IEPSCuotaFija, 0.00),
				@ovdId,
				GETDATE(),
				@creadoPorId
			FROM [dbo].[Articulos]
			INNER JOIN [dbo].[ProgramasIdiomasLibrosMateriales] ON [ART_ArticuloId] = [PROGILM_ART_ArticuloId]
			INNER JOIN [dbo].[ProgramasIdiomas] ON [PROGILM_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId]
			LEFT JOIN [dbo].[ProgramasIdiomasLibrosMaterialesReglas] ON [PROGILMR_PROGILM_ProgramaIdiomaLibroMaterialId] = [PROGILM_ProgramaIdiomaLibroMaterialId]
			WHERE
				[ART_Activo] = 1
				AND [PROGILM_Borrado] = 0
				AND [PROGI_PROG_ProgramaId] = @programaId
				AND [PROGI_CMM_Idioma] = @idiomaId
				AND [PROGILM_Nivel] = @nivel
				AND ([PROGILMR_ProgramaIdiomaLibroMaterialReglaId] IS NULL OR ([PROGILMR_CMM_CarreraId] = COALESCE(@carreraId, [PROGILMR_CMM_CarreraId])));
			/* Insertar los detalles 'certificacion' */
			INSERT INTO [dbo].[OrdenesVentaDetalles]
				([OVD_OV_OrdenVentaId],[OVD_ART_ArticuloId],[OVD_UM_UnidadMedidaId],[OVD_FactorConversion],[OVD_Cantidad],[OVD_Precio],[OVD_IVA],[OVD_IEPS],[OVD_IVAExento],[OVD_IEPSCuotaFija],[OVD_OVD_DetallePadreId],[OVD_FechaCreacion],[OVD_USU_CreadoPorId])
			SELECT 
				@ovId,
				ART_ArticuloId,
				COALESCE(ART_UM_UnidadMedidaConversionVentasId, ART_UM_UnidadMedidaInventarioId),
				COALESCE(ART_FactorConversionVentas, 1.00),
				1.00,
				CASE @esApertura WHEN 1 THEN 0.00 ELSE [dbo].[getPrecioVenta](ART_ArticuloId, @articuloId, @lp) END,
				CASE ART_IVAExento WHEN 1 THEN 0.00 ELSE ART_IVA END,
				CASE WHEN COALESCE(ART_IEPSCuotaFija, 0.00) > 0.00 THEN 0.00 ELSE COALESCE(ART_IEPS, 0.00) END,
				COALESCE(ART_IVAExento, 0),
				COALESCE(ART_IEPSCuotaFija, 0.00),
				@ovdId,
				GETDATE(),
				@creadoPorId
			FROM [dbo].[Articulos]
			INNER JOIN [dbo].[ProgramasIdiomasCertificacion] ON [ART_ArticuloId] = [PROGIC_ART_ArticuloId]
			INNER JOIN [dbo].[ProgramasIdiomas] ON [PROGIC_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId]
			WHERE
				[ART_Activo] = 1
				AND [PROGIC_Borrado] = 0
				AND [PROGI_PROG_ProgramaId] = @programaId
				AND [PROGI_CMM_Idioma] = @idiomaId;
			/* Insertar la inscripcion */
			DECLARE @codigoInscripcion NVARCHAR(15) = NULL;
			DECLARE @siguiente INT = NULL;
			SELECT 
				@codigoInscripcion = CONCAT([AUT_Prefijo], RIGHT('0000' + CAST([AUT_Siguiente] AS nvarchar(10)), [AUT_Digitos])), 
				@siguiente = [AUT_Siguiente] 
			FROM [dbo].[Autonumericos] WHERE [AUT_Prefijo] = 'INS';
			IF @centroId IS NULL
			BEGIN
				INSERT INTO [dbo].[Inscripciones]
					   ([INS_Codigo],[INS_OVD_OrdenVentaDetalleId],[INS_ALU_AlumnoId],[INS_PROGRU_GrupoId],[INS_CMM_InscripcionOrigenId],[INS_CMM_EstatusId],[INS_EntregaLibrosPendiente],[INS_CMM_InstitucionAcademicaId],[INS_Carrera],[INS_CMM_TurnoId],[INS_CMM_GradoId],[INS_Grupo],[INS_FechaCreacion],[INS_USU_CreadoPorId])
				 VALUES
					   (@codigoInscripcion, @ovdId, @alumnoId, @grupoId, @origenId, @inscripcionEstatusId, 0, @preparatoriaId, @bachillerato, @turnoId, @gradoId, @grupo, GETDATE(), @creadoPorId);
			END
			ELSE
			BEGIN
				INSERT INTO [dbo].[Inscripciones]
					   ([INS_Codigo],[INS_OVD_OrdenVentaDetalleId],[INS_ALU_AlumnoId],[INS_PROGRU_GrupoId],[INS_CMM_InscripcionOrigenId],[INS_CMM_EstatusId],[INS_EntregaLibrosPendiente],[INS_CMM_InstitucionAcademicaId],[INS_CMM_CarreraId],[INS_FechaCreacion],[INS_USU_CreadoPorId])
				 VALUES
					   (@codigoInscripcion, @ovdId, @alumnoId, @grupoId, @origenId, @inscripcionEstatusId, 0, @centroId, @carreraId, GETDATE(), @creadoPorId);
			END
			DECLARE @inscripcionId INT = (SELECT SCOPE_IDENTITY());
			UPDATE [dbo].[Autonumericos] SET [AUT_Siguiente] = @siguiente + 1 WHERE [AUT_Prefijo] = 'INS';
			/* Insertar la relacion alumno-grupo */
			INSERT INTO [dbo].[AlumnosGrupos]
				([ALUG_ALU_AlumnoId],[ALUG_PROGRU_GrupoId],[ALUG_Asistencias],[ALUG_Faltas],[ALUG_MinutosRetardo],[ALUG_CalificacionFinal],[ALUG_CMM_EstatusId],[ALUG_FechaCreacion],[ALUG_USU_CreadoPorId],[ALUG_INS_InscripcionId])
			VALUES
				(@alumnoId,@grupoId,0,0,0,@calificacion,@alumnoGrupoEstatusId,GETDATE(),@creadoPorId,@inscripcionId);
			/*
			IF 1 = 1
			BEGIN
				SET @msg = (SELECT FORMATMESSAGE(N'Candado para prevenir el guardado (%s, %s)', @codigoAlumno, @codigoGrupo));
				THROW 50000, @msg, 1
			END
			*/
			IF @@TRANCOUNT > 0
				COMMIT TRANSACTION;
		END TRY
		BEGIN CATCH
			IF @@TRANCOUNT > 0
				ROLLBACK TRANSACTION;
			SET @feedback = ERROR_MESSAGE();
		END CATCH
	END
