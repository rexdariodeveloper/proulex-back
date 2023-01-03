/**
* Created by Angel Daniel Hernández Silva on 05/10/2021.
* Object: CREATE OR ALTER VIEW [dbo].[VW_HistorialPVResumen_OrdenesVentaDetalles]
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
			WHEN INS_InscripcionId IS NOT NULL THEN CONCAT('Alumno: ',ALU_Nombre,' ' + ALU_PrimerApellido,' ' + ALU_SegundoApellido)
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
	LEFT JOIN Alumnos ON ALU_AlumnoId = COALESCE(INS_ALU_AlumnoId,INSSG_ALU_AlumnoId)
	INNER JOIN (
		SELECT
			padreId AS id,
			MAX(cantidad) AS cantidad,
			SUM(precio) AS precio,
			MAX(descuento) AS descuento,
			(SELECT Total FROM [dbo].[fn_getImpuestosArticulo](MAX(cantidad),SUM(precio),MAX(descuento),MAX(iva),MAX(ieps),SUM(iepsCuotaFija))) AS montoTotal
		FROM (
			SELECT
				OVD_OrdenVentaDetalleId AS id,
				COALESCE(OVD_OVD_DetallePadreId,OVD_OrdenVentaDetalleId) AS padreId,
				CASE
					WHEN OVD_OVD_DetallePadreId IS NOT NULL THEN 0
					ELSE CAST(OVD_Cantidad AS int)
				END AS cantidad,
				OVD_Precio AS precio,
				CASE
					WHEN OVD_OVD_DetallePadreId IS NOT NULL THEN 0
					WHEN COALESCE(OVD_IVAExento,0) = 0 THEN COALESCE(OVD_IVA,0)
					ELSE 0
				END AS iva,
				CASE
					WHEN OVD_OVD_DetallePadreId IS NOT NULL THEN 0
					ELSE COALESCE(OVD_IEPS,0)
				END AS ieps,
				OVD_IEPSCuotaFija AS iepsCuotaFija,
				COALESCE(OVD_Descuento,0) AS descuento
			FROM OrdenesVentaDetalles
		) OVD
		GROUP BY padreId
	) OVDMontosAcumulado ON id = OVD_OrdenVentaDetalleId
GO