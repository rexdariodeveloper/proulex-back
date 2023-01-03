/**
* Created by Angel Daniel Hernández Silva on 13/12/2021.
* Object: ALTER VIEW [dbo].[VW_HistorialPVResumen_OrdenesVentaDetalles]
*/

CREATE OR ALTER VIEW [dbo].[VW_HistorialPVResumen_OrdenesVentaDetalles] AS
	SELECT
		OVD_OrdenVentaDetalleId AS id,
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
		END AS conceptoLinea1,
		CASE
			WHEN COALESCE(INS_InscripcionId,ALUEC_AlumnoExamenCertificacionId) IS NOT NULL THEN CONCAT('Alumno: ',ALU_Nombre,' ' + ALU_PrimerApellido,' ' + ALU_SegundoApellido)
			WHEN OVD_OVD_DetallePadreId IS NOT NULL THEN ''
			ELSE ''
		END AS conceptoLinea2,
		cantidad,
		precio,
		descuento,
		montoTotal,

		OVD_OV_OrdenVentaId AS ordenVentaId
	FROM OrdenesVentaDetalles
	LEFT JOIN Inscripciones ON INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
	LEFT JOIN InscripcionesSinGrupo ON INSSG_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
	LEFT JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
	LEFT JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	LEFT JOIN Programas ON PROG_ProgramaId = COALESCE(INSSG_PROG_ProgramaId,PROGI_PROG_ProgramaId)
	LEFT JOIN ControlesMaestrosMultiples AS Idioma ON CMM_ControlId = COALESCE(INSSG_CMM_IdiomaId,PROGI_CMM_Idioma)
	LEFT JOIN PAModalidades ON PAMOD_ModalidadId = COALESCE(INSSG_PAMOD_ModalidadId,PROGRU_PAMOD_ModalidadId)
	LEFT JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
	INNER JOIN Articulos ON ART_ArticuloId = OVD_ART_ArticuloId
	LEFT JOIN AlumnosExamenesCertificaciones ON ALUEC_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
	LEFT JOIN Alumnos ON ALU_AlumnoId = COALESCE(ALUEC_ALU_AlumnoId,INS_ALU_AlumnoId,INSSG_ALU_AlumnoId)
	INNER JOIN (
		SELECT
			padreId AS id,
			OVD_Cantidad AS cantidad,
			SUM(precio) AS precio,
			SUM(descuentoDetalle) AS descuento,
			SUM(totalDetalle) AS montoTotal
		FROM (
			SELECT
				COALESCE(OVD_OVD_DetallePadreId,OVD_OrdenVentaDetalleId) AS padreId,
				OVD_Precio AS precio,
				(SELECT Subtotal FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS subtotalDetalle,
				(SELECT IVA FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS ivaDetalle,
				(SELECT IEPS FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS iepsDetalle,
				(SELECT Descuento FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS descuentoDetalle,
				(SELECT Total FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS totalDetalle
			FROM OrdenesVenta
			INNER JOIN OrdenesVentaDetalles ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
		) MontosOVD
		INNER JOIN OrdenesVentaDetalles ON OVD_OrdenVentaDetalleId = padreId
		GROUP BY padreId,OVD_Cantidad
	) OVDMontosAcumulado ON id = OVD_OrdenVentaDetalleId
GO