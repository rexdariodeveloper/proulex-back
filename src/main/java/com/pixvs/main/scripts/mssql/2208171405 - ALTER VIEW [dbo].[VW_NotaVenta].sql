/**
* Created by Angel Daniel Hernández Silva on 17/08/2022.
*/

CREATE OR ALTER VIEW [dbo].[VW_NotaVenta] AS

	SELECT

		ordenVentaId,
		
		empresaRazonSocial,
		empresaRFC,
		empresaRegimenFiscal,
		
		ordenVentaCodigo,
		ordenVentaFechaCompleta,
		ordenVentaFecha,
		ordenVentaHora,
		ordenVentaSucursal,
		ordenVentaMedioPago,
		ordenVentaUsuario,
		(SELECT [dbo].[NumeroLetra](ordenVentaMontoTotal)) AS ordenVentaTotalLetra,
		ordenVentaMontoSubtotal,
		ordenVentaMontoIVA,
		ordenVentaMontoIEPS,
		ordenVentaMontoDescuento,
		ordenVentaMontoTotal,

		detalleId,
		detalleAlumnoNombre,
		detalleAlumnoApellidos,
		detalleAlumnoCodigo,
		detalleCantidad,
		detalleConceptoLinea1,
		detalleConceptoLinea2,
		detallePrecio,
		detalleIVA,
		detalleIEPS,
		detalleIEPSCuotaFija,
		detalleDescuento,
		detalleMontoSubtotal,
		detalleMontoIVA,
		detalleMontoIEPS,
		detalleMontoDescuento,
		detalleMontoTotal,
		COALESCE(SP_Nombre, '') plantel,

		grupoId,
		grupoFechaInicio,
		grupoFechaFin,
		entregaLibrosPendiente,

		becaId,
		becaCodigo,
		becaPorcentaje,
		becaEntidad
		
	FROM (
		SELECT

			ordenVentaId,
			
			empresaRazonSocial,
			empresaRFC,
			empresaRegimenFiscal,
			
			ordenVentaCodigo,
			ordenVentaFechaCompleta,
			ordenVentaFecha,
			ordenVentaHora,
			ordenVentaSucursal,
			ordenVentaMedioPago,
			ordenVentaUsuario,
			ordenVentaTotalLetra,
			SUM(detalleMontoSubtotal) OVER(PARTITION BY ordenVentaId) AS ordenVentaMontoSubtotal,
			SUM(detalleMontoIVA) OVER(PARTITION BY ordenVentaId) AS ordenVentaMontoIVA,
			SUM(detalleMontoIEPS) OVER(PARTITION BY ordenVentaId) AS ordenVentaMontoIEPS,
			SUM(detalleMontoDescuento) OVER(PARTITION BY ordenVentaId) AS ordenVentaMontoDescuento,
			SUM(detalleMontoTotal) OVER(PARTITION BY ordenVentaId) AS ordenVentaMontoTotal,

			detalleId,
			detalleAlumnoNombre,
			detalleAlumnoApellidos,
			detalleAlumnoCodigo,
			detalleCantidad,
			detalleConceptoLinea1,
			detalleConceptoLinea2,
			detallePrecio,
			detalleIVA,
			detalleIEPS,
			detalleIEPSCuotaFija,
			detalleDescuento,
			detalleMontoSubtotal,
			detalleMontoIVA,
			detalleMontoIEPS,
			detalleMontoDescuento,
			detalleMontoTotal,
			plantelId,

			grupoId,
			grupoFechaInicio,
			grupoFechaFin,
			entregaLibrosPendiente,

			becaId,
			becaCodigo,
			becaPorcentaje,
			becaEntidad
			
		FROM (
			SELECT

				ordenVentaId,
			
				empresaRazonSocial,
				empresaRFC,
				empresaRegimenFiscal,
			
				ordenVentaCodigo,
				ordenVentaFechaCompleta,
				ordenVentaFecha,
				ordenVentaHora,
				ordenVentaSucursal,
				ordenVentaMedioPago,
				ordenVentaUsuario,
				'' AS ordenVentaTotalLetra,

				detallePadreId AS detalleId,
				MAX(detalleAlumnoNombre) AS detalleAlumnoNombre,
				MAX(detalleAlumnoApellidos) AS detalleAlumnoApellidos,
				MAX(detalleAlumnoCodigo) AS detalleAlumnoCodigo,
				MAX(detalleCantidad) AS detalleCantidad,
				MAX(detalleConceptoLinea1) AS detalleConceptoLinea1,
				MAX(detalleConceptoLinea2) AS detalleConceptoLinea2,
				SUM(detallePrecio) AS detallePrecio,
				MAX(detalleIVA) AS detalleIVA,
				MAX(detalleIEPS) AS detalleIEPS,
				SUM(detalleIEPSCuotaFija) AS detalleIEPSCuotaFija,
				SUM(detalleDescuento) AS detalleDescuento,
				SUM(detalleMontoSubtotal) AS detalleMontoSubtotal,
				SUM(detalleMontoIVA) AS detalleMontoIVA,
				SUM(detalleMontoIEPS) AS detalleMontoIEPS,
				SUM(detalleMontoDescuento) AS detalleMontoDescuento,
				SUM(detalleMontoTotal) AS detalleMontoTotal,
				MAX(plantelId) plantelId,

				MAX(grupoId) AS grupoId,
				MAX(grupoFechaInicio) AS grupoFechaInicio,
				MAX(grupoFechaFin) AS grupoFechaFin,
				MAX(entregaLibrosPendiente) AS entregaLibrosPendiente,

				MAX(becaId) AS becaId,
				MAX(becaCodigo) AS becaCodigo,
				MAX(becaPorcentaje) AS becaPorcentaje,
				MAX(becaEntidad) AS becaEntidad
			FROM(
				SELECT

					ordenVentaId,
			
					empresaRazonSocial,
					empresaRFC,
					empresaRegimenFiscal,
			
					ordenVentaCodigo,
					ordenVentaFechaCompleta,
					ordenVentaFecha,
					ordenVentaHora,
					ordenVentaSucursal,
					ordenVentaMedioPago,
					ordenVentaUsuario,
					'' AS ordenVentaTotalLetra,

					detalleId AS detalleId,
					detallePadreId AS detallePadreId,
					detalleAlumnoNombre AS detalleAlumnoNombre,
					detalleAlumnoApellidos AS detalleAlumnoApellidos,
					detalleAlumnoCodigo AS detalleAlumnoCodigo,
					CASE
						WHEN detallePadreId = detalleId THEN detalleCantidad
						ELSE 0
					END AS detalleCantidad,
					detalleConceptoLinea1 AS detalleConceptoLinea1,
					detalleConceptoLinea2 AS detalleConceptoLinea2,
					detallePrecio AS detallePrecio,
					CASE
						WHEN detallePadreId = detalleId THEN detalleIVA
						ELSE 0
					END AS detalleIVA,
					CASE
						WHEN detallePadreId = detalleId THEN detalleIEPS
						ELSE 0
					END AS detalleIEPS,
					detalleIEPSCuotaFija AS detalleIEPSCuotaFija,
					detalleDescuento AS detalleDescuento,
					(SELECT Subtotal FROM [dbo].[fn_getImpuestosArticulo](detalleCantidad,detallePrecio,detalleDescuento,detalleIVA,detalleIEPS,detalleIEPSCuotaFija)) AS detalleMontoSubtotal,
					(SELECT IVA FROM [dbo].[fn_getImpuestosArticulo](detalleCantidad,detallePrecio,detalleDescuento,detalleIVA,detalleIEPS,detalleIEPSCuotaFija)) AS detalleMontoIVA,
					(SELECT IEPS FROM [dbo].[fn_getImpuestosArticulo](detalleCantidad,detallePrecio,detalleDescuento,detalleIVA,detalleIEPS,detalleIEPSCuotaFija)) AS detalleMontoIEPS,
					(SELECT Descuento FROM [dbo].[fn_getImpuestosArticulo](detalleCantidad,detallePrecio,detalleDescuento,detalleIVA,detalleIEPS,detalleIEPSCuotaFija)) AS detalleMontoDescuento,
					(SELECT Total FROM [dbo].[fn_getImpuestosArticulo](detalleCantidad,detallePrecio,detalleDescuento,detalleIVA,detalleIEPS,detalleIEPSCuotaFija)) AS detalleMontoTotal,
					CASE
						WHEN detallePadreId = detalleId THEN PROGRU_SP_SucursalPlantelId
						ELSE 0
					END AS plantelId,

					grupoId,
					grupoFechaInicio,
					grupoFechaFin,
					CASE
						WHEN entregaLibrosPendiente = 1 THEN 'MATERIAL PENDIENTE DE ENTREGA'
						WHEN entregaLibrosPendiente = 0 THEN 'MATERIAL ENTREGADO'
						ELSE ''
					END AS entregaLibrosPendiente,

					becaId,
					becaCodigo,
					becaPorcentaje,
					becaEntidad
				FROM(
					SELECT

						id AS ordenVentaId,
			
						cmRazonSocial.CMA_Valor AS empresaRazonSocial,
						cmRFC.CMA_Valor AS empresaRFC,
						cmRegimenFiscal.CMA_Valor AS empresaRegimenFiscal,
			
						codigo AS ordenVentaCodigo,
						FORMAT(fechaCreacion,'dd/MM/yy hh:mm:ss tt','es-mx') AS ordenVentaFechaCompleta,
						FORMAT(fechaCreacion,'dd/MM/yy','es-mx') AS ordenVentaFecha,
						FORMAT(fechaCreacion,'hh:mm:ss tt','es-mx') AS ordenVentaHora,
						s_ov.SUC_Nombre AS ordenVentaSucursal,
						CONCAT(MPPV_Nombre,' (' + referenciaPago + ')') AS ordenVentaMedioPago,
						CONCAT(USU_Nombre, ' ' + USU_PrimerApellido, ' ' + USU_SegundoApellido) AS ordenVentaUsuario,
						'' AS ordenVentaTotalLetra,

						OVD_OrdenVentaDetalleId AS detalleId,
						COALESCE(ALU_Nombre,'') AS detalleAlumnoNombre,
						CONCAT(ALU_PrimerApellido, ' ' + ALU_SegundoApellido) AS detalleAlumnoApellidos,
						COALESCE(ALU_Codigo,'') AS detalleAlumnoCodigo,
						CAST(OVD_Cantidad AS int) detalleCantidad,
						CASE
							WHEN INS_InscripcionId IS NOT NULL THEN CONCAT(PROG_Codigo,' ',Idioma.CMM_Valor,' ',PAMOD_Nombre,' Nivel ',PROGRU_Nivel,' ',PAMODH_Horario,' ',RIGHT(('00'+CAST(PROGRU_Grupo AS varchar)),2))
								+ CASE WHEN COALESCE(PROG_JOBSSEMS,0) = 1
									THEN COALESCE(
										', ' + CASE
											WHEN PROGRU_Nivel = 1 THEN 'Libro Interac 1, Parcialidad (1 de 2)'
											WHEN PROGRU_Nivel = 2 THEN 'Libro Interac 1, Parcialidad (2 de 2)'
											WHEN PROGRU_Nivel = 3 THEN 'Libro Interac 2, Parcialidad (1 de 2)'
											WHEN PROGRU_Nivel = 4 THEN 'Libro Interac 2, Parcialidad (2 de 2)'
											WHEN PROGRU_Nivel = 5 THEN 'Libro Interac 3, Parcialidad (1 de 2)'
											WHEN PROGRU_Nivel = 6 THEN 'Libro Interac 3, Parcialidad (2 de 2)'
											ELSE NULL
										END
									,'')
									ELSE ''
								END
							WHEN INSSG_InscripcionId IS NOT NULL THEN CONCAT(PROG_Codigo,' ',Idioma.CMM_Valor,' ',PAMOD_Nombre,' Nivel ',INSSG_Nivel)
							WHEN OVD_OVD_DetallePadreId IS NOT NULL THEN ''
							ELSE ART_NombreArticulo
						END AS detalleConceptoLinea1,
						CASE
							WHEN INS_InscripcionId IS NOT NULL THEN grupoCodigo
							WHEN OVD_OVD_DetallePadreId IS NOT NULL THEN ''
							ELSE ''
						END AS detalleConceptoLinea2,
						OVD_Precio AS detallePrecio,
						CASE
							WHEN COALESCE(OVD_IVAExento,0) = 0 THEN COALESCE(OVD_IVA,0)
							ELSE 0
						END AS detalleIVA,
						COALESCE(OVD_IEPS,0) detalleIEPS,
						OVD_IEPSCuotaFija AS detalleIEPSCuotaFija,
						COALESCE(OVD_Descuento,0) AS detalleDescuento,
						COALESCE(OVD_OVD_DetallePadreId ,OVD_OrdenVentaDetalleId) AS detallePadreId,
						PROGRU_SP_SucursalPlantelId,

						PROGRU_GrupoId AS grupoId,
						FORMAT(PROGRU_FechaInicio,'dd/MM/yy','es-mx') AS grupoFechaInicio,
						FORMAT(PROGRU_FechaFin,'dd/MM/yy','es-mx') AS grupoFechaFin,
						CASE WHEN INSSG_InscripcionId IS NOT NULL THEN INSSG_EntregaLibrosPendiente ELSE INS_EntregaLibrosPendiente END AS entregaLibrosPendiente,

						BECU_BecaId AS becaId,
						BECU_CodigoBeca AS becaCodigo,
						CAST(CAST(BECU_Descuento*100 AS int) AS varchar(3)) + ' %' AS becaPorcentaje,
						ENBE_Nombre AS becaEntidad
					FROM VW_OrdenesVenta
					INNER JOIN ControlesMaestros AS cmRazonSocial ON cmRazonSocial.CMA_Nombre = 'CM_RAZON_SOCIAL'
					INNER JOIN ControlesMaestros AS cmRFC ON cmRFC.CMA_Nombre = 'CM_RFC_EMPRESA'
					INNER JOIN ControlesMaestros AS cmRegimenFiscal ON cmRegimenFiscal.CMA_Nombre = 'CM_REGIMEN_FISCAL'
					INNER JOIN Sucursales s_ov ON SUC_SucursalId = sucursalId
					INNER JOIN MediosPagoPV ON MPPV_MedioPagoPVId = medioPagoPVId
					INNER JOIN Usuarios ON USU_UsuarioId = creadoPorId
					INNER JOIN OrdenesVentaDetalles ON OVD_OV_OrdenVentaId = id
					LEFT JOIN Inscripciones ON INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
					LEFT JOIN InscripcionesSinGrupo ON INSSG_OVD_OrdenVentaDetalleId = (CASE WHEN INS_InscripcionId IS NULL THEN OVD_OrdenVentaDetalleId ELSE NULL END)
					LEFT JOIN AlumnosExamenesCertificaciones ON ALUEC_OVD_OrdenVentaDetalleId = (CASE WHEN INS_InscripcionId IS NULL AND INSSG_InscripcionId IS NULL THEN OVD_OrdenVentaDetalleId ELSE NULL END)
					LEFT JOIN AlumnosConstanciasTutorias ON ALUCT_OVD_OrdenVentaDetalleId = (CASE WHEN INS_InscripcionId IS NULL AND INSSG_InscripcionId IS NULL AND ALUEC_AlumnoExamenCertificacionId IS NULL THEN OVD_OrdenVentaDetalleId ELSE NULL END)
					LEFT JOIN Alumnos ON ALU_AlumnoId = COALESCE(ALUCT_ALU_AlumnoId,ALUEC_ALU_AlumnoId,INSSG_ALU_AlumnoId,INS_ALU_AlumnoId)
					LEFT JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
					LEFT JOIN Sucursales s ON PROGRU_SUC_SucursalId = s.SUC_SucursalId
					LEFT JOIN [dbo].[VW_Codigo_ProgramasGrupos] ON grupoId = PROGRU_GrupoId
					LEFT JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
					LEFT JOIN Programas ON PROG_ProgramaId = COALESCE(INSSG_PROG_ProgramaId,PROGI_PROG_ProgramaId)
					LEFT JOIN ControlesMaestrosMultiples AS Idioma ON CMM_ControlId = COALESCE(INSSG_CMM_IdiomaId,PROGI_CMM_Idioma)
					LEFT JOIN PAModalidades ON PAMOD_ModalidadId = COALESCE(INSSG_PAMOD_ModalidadId,PROGRU_PAMOD_ModalidadId)
					LEFT JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
					INNER JOIN Articulos ON ART_ArticuloId = OVD_ART_ArticuloId
					LEFT JOIN BecasUDG ON BECU_BecaId = COALESCE(INSSG_BECU_BecaId,INS_BECU_BecaId)
					LEFT JOIN EntidadesBecas ON ENBE_EntidadBecaId = BECU_ENBE_EntidadBecaId
				) OVDDesglosados
			) OVDDesglosados
			GROUP BY
				ordenVentaId, empresaRazonSocial, empresaRFC, empresaRegimenFiscal, ordenVentaCodigo, ordenVentaFechaCompleta, ordenVentaFecha, ordenVentaHora, ordenVentaSucursal, ordenVentaMedioPago,
				ordenVentaUsuario, ordenVentaTotalLetra, detallePadreId/*, grupoId, grupoFechaInicio, grupoFechaFin, becaId, becaCodigo, becaPorcentaje, becaEntidad*/
		) OVDAcumulado
	) OVDAcumulado
	LEFT JOIN SucursalesPlanteles ON SP_SucursalPlantelId = plantelId

GO