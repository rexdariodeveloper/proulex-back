/**
* Created by Angel Daniel Hernández Silva on 02/08/2022.
* Modified by Javier Elías on 02/09/2022.
*/
CREATE OR ALTER VIEW [dbo].[VW_HistorialPVResumen_OrdenesVentaDetalles]
AS
SELECT OVD_OrdenVentaDetalleId AS id,
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
		END AS conceptoLinea1,
		CASE
			WHEN COALESCE(INS_InscripcionId,INSSG_InscripcionId,ALUEC_AlumnoExamenCertificacionId,ALUCT_AlumnoConstanciaTutoriaId) IS NOT NULL THEN CONCAT('Alumno: ',ALU_Nombre,' ' + ALU_PrimerApellido,' ' + ALU_SegundoApellido)
			WHEN OVD_OVD_DetallePadreId IS NOT NULL THEN ''
			ELSE ''
		END AS conceptoLinea2,
       cantidad,
       OVD_Precio AS precio,
       descuento,
       total AS montoTotal,
       CAST((CASE WHEN ART_ARTST_ArticuloSubtipoId = 6 THEN 1 ELSE 0 END) AS BIT) AS esExamenUbicacion,
       CAST((CASE WHEN INS_InscripcionId IS NOT NULL OR INSSG_InscripcionId IS NOT NULL THEN 1 ELSE 0 END) AS BIT) AS esInscripcion,
       OVD_OV_OrdenVentaId AS ordenVentaId
FROM OrdenesVentaDetalles
     INNER JOIN Articulos ON ART_ArticuloId = OVD_ART_ArticuloId
	 LEFT JOIN Inscripciones ON INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId AND INS_CMM_EstatusId != 2000512 -- Cancelada
     LEFT JOIN InscripcionesSinGrupo ON INSSG_OVD_OrdenVentaDetalleId = (CASE WHEN INS_OVD_OrdenVentaDetalleId IS NULL THEN OVD_OrdenVentaDetalleId ELSE NULL END)
     LEFT JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
     LEFT JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
     LEFT JOIN Programas ON PROG_ProgramaId = COALESCE(INSSG_PROG_ProgramaId, PROGI_PROG_ProgramaId)
     LEFT JOIN ControlesMaestrosMultiples AS Idioma ON CMM_ControlId = COALESCE(INSSG_CMM_IdiomaId, PROGI_CMM_Idioma)
     LEFT JOIN PAModalidades ON PAMOD_ModalidadId = COALESCE(INSSG_PAMOD_ModalidadId, PROGRU_PAMOD_ModalidadId)
     LEFT JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
     LEFT JOIN AlumnosExamenesCertificaciones ON ALUEC_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
     LEFT JOIN AlumnosConstanciasTutorias ON ALUCT_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
     LEFT JOIN Alumnos ON ALU_AlumnoId = COALESCE(ALUCT_ALU_AlumnoId, ALUEC_ALU_AlumnoId, INS_ALU_AlumnoId, INSSG_ALU_AlumnoId)
	 CROSS APPLY dbo.fn_getMontosDetalleOV(OVD_OrdenVentaDetalleId) AS OVDMontosAcumulado
WHERE OrdenesVentaDetalles.OVD_OVD_DetallePadreId IS NULL
GO