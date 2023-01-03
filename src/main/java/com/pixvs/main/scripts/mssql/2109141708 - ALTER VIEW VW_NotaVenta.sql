/**
* Created by Angel Daniel Hernández Silva on 13/09/2021.
* Object:  ALTER VIEW [dbo].[VW_NotaVenta]
*/

CREATE OR ALTER VIEW [dbo].[VW_NotaVenta] AS
	SELECT

		ordenVentaId,
		
		empresaRazonSocial,
		empresaRFC,
		empresaRegimenFiscal,
		
		ordenVentaCodigo,
		ordenVentaFecha,
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
		detalleMontoTotal
		
	FROM (
		SELECT

			ordenVentaId,
		
			empresaRazonSocial,
			empresaRFC,
			empresaRegimenFiscal,
		
			ordenVentaCodigo,
			ordenVentaFecha,
			ordenVentaSucursal,
			ordenVentaMedioPago,
			ordenVentaUsuario,
			(SELECT [dbo].[NumeroLetra]((SELECT Total FROM [dbo].[fn_getImpuestosArticulo](MAX(detalleCantidad),SUM(detallePrecio),MAX(detalleDescuento),MAX(detalleIVA),MAX(detalleIEPS),SUM(detalleIEPSCuotaFija))))) ordenVentaTotalLetra,

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
			MAX(detalleDescuento) AS detalleDescuento,
			(SELECT Subtotal FROM [dbo].[fn_getImpuestosArticulo](MAX(detalleCantidad),SUM(detallePrecio),MAX(detalleDescuento),MAX(detalleIVA),MAX(detalleIEPS),SUM(detalleIEPSCuotaFija))) AS detalleMontoSubtotal,
			(SELECT IVA FROM [dbo].[fn_getImpuestosArticulo](MAX(detalleCantidad),SUM(detallePrecio),MAX(detalleDescuento),MAX(detalleIVA),MAX(detalleIEPS),SUM(detalleIEPSCuotaFija))) AS detalleMontoIVA,
			(SELECT IEPS FROM [dbo].[fn_getImpuestosArticulo](MAX(detalleCantidad),SUM(detallePrecio),MAX(detalleDescuento),MAX(detalleIVA),MAX(detalleIEPS),SUM(detalleIEPSCuotaFija))) AS detalleMontoIEPS,
			(SELECT Descuento FROM [dbo].[fn_getImpuestosArticulo](MAX(detalleCantidad),SUM(detallePrecio),MAX(detalleDescuento),MAX(detalleIVA),MAX(detalleIEPS),SUM(detalleIEPSCuotaFija))) AS detalleMontoDescuento,
			(SELECT Total FROM [dbo].[fn_getImpuestosArticulo](MAX(detalleCantidad),SUM(detallePrecio),MAX(detalleDescuento),MAX(detalleIVA),MAX(detalleIEPS),SUM(detalleIEPSCuotaFija))) AS detalleMontoTotal
		
		FROM(
			SELECT

				OV_OrdenVentaId AS ordenVentaId,
		
				cmRazonSocial.CMA_Valor AS empresaRazonSocial,
				cmRFC.CMA_Valor AS empresaRFC,
				cmRegimenFiscal.CMA_Valor AS empresaRegimenFiscal,
		
				OV_Codigo AS ordenVentaCodigo,
				FORMAT(GETDATE(),'dd/MM/yy hh:mm:ss tt','es-mx') AS ordenVentaFecha,
				SUC_Nombre AS ordenVentaSucursal,
				MPPV_Nombre AS ordenVentaMedioPago,
				CONCAT(USU_Nombre, ' ' + USU_PrimerApellido, ' ' + USU_SegundoApellido) AS ordenVentaUsuario,
				'' AS ordenVentaTotalLetra,

				OVD_OrdenVentaDetalleId AS detalleId,
				COALESCE(ALU_Nombre,'') AS detalleAlumnoNombre,
				CONCAT(ALU_PrimerApellido, ' ' + ALU_SegundoApellido) AS detalleAlumnoApellidos,
				COALESCE(ALU_Codigo,'') AS detalleAlumnoCodigo,
				CASE
					WHEN OVD_OVD_DetallePadreId IS NOT NULL THEN 0
					ELSE CAST(OVD_Cantidad AS int)
				END AS detalleCantidad,
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
					WHEN INSSG_InscripcionId IS NOT NULL THEN 'Sin grupo'
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
					WHEN OVD_OVD_DetallePadreId IS NOT NULL THEN 0
					WHEN COALESCE(OVD_IVAExento,0) = 0 THEN COALESCE(OVD_IVA,0)
					ELSE 0
				END AS detalleIVA,
				CASE
					WHEN OVD_OVD_DetallePadreId IS NOT NULL THEN 0
					ELSE COALESCE(OVD_IEPS,0)
				END AS detalleIEPS,
				OVD_IEPSCuotaFija AS detalleIEPSCuotaFija,
				COALESCE(OVD_Descuento,0) AS detalleDescuento,
				COALESCE(OVD_OVD_DetallePadreId ,OVD_OrdenVentaDetalleId) AS detallePadreId
	
			FROM OrdenesVenta
			INNER JOIN ControlesMaestros AS cmRazonSocial ON cmRazonSocial.CMA_Nombre = 'CM_RAZON_SOCIAL'
			INNER JOIN ControlesMaestros AS cmRFC ON cmRFC.CMA_Nombre = 'CM_RFC_EMPRESA'
			INNER JOIN ControlesMaestros AS cmRegimenFiscal ON cmRegimenFiscal.CMA_Nombre = 'CM_REGIMEN_FISCAL'
			INNER JOIN Sucursales ON SUC_SucursalId = OV_SUC_SucursalId
			INNER JOIN MediosPagoPV ON MPPV_MedioPagoPVId = OV_MPPV_MedioPagoPVId
			INNER JOIN Usuarios ON USU_UsuarioId = COALESCE(OV_USU_ModificadoPorId,OV_USU_CreadoPorId)
			INNER JOIN OrdenesVentaDetalles ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
			LEFT JOIN Inscripciones ON INS_OVD_OrdenVentaId = OVD_OrdenVentaDetalleId
			LEFT JOIN InscripcionesSinGrupo ON INSSG_OVD_OrdenVentaId = (CASE WHEN INS_InscripcionId IS NULL THEN OVD_OrdenVentaDetalleId ELSE NULL END)
			LEFT JOIN Alumnos ON ALU_AlumnoId = COALESCE(INSSG_ALU_AlumnoId,INS_ALU_AlumnoId)
			LEFT JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
			LEFT JOIN [dbo].[VW_Codigo_ProgramasGrupos] ON grupoId = PROGRU_GrupoId
			LEFT JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			LEFT JOIN Programas ON PROG_ProgramaId = COALESCE(INSSG_PROG_ProgramaId,PROGI_PROG_ProgramaId)
			LEFT JOIN ControlesMaestrosMultiples AS Idioma ON CMM_ControlId = COALESCE(INSSG_CMM_IdiomaId,PROGI_CMM_Idioma)
			LEFT JOIN PAModalidades ON PAMOD_ModalidadId = COALESCE(INSSG_PAMOD_ModalidadId,PROGRU_PAMOD_ModalidadId)
			LEFT JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
			INNER JOIN Articulos ON ART_ArticuloId = OVD_ART_ArticuloId
		) OVDDesglosados
		GROUP BY
			ordenVentaId, empresaRazonSocial, empresaRFC, empresaRegimenFiscal, ordenVentaCodigo, ordenVentaFecha, ordenVentaSucursal, ordenVentaMedioPago,
			ordenVentaUsuario, ordenVentaTotalLetra, detallePadreId
	) OVDAcumulado
GO