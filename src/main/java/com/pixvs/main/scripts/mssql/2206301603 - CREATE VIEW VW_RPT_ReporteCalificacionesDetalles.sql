SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Rene Carrillo
-- Create date: 30/06/2022
-- Modify date:
-- Description:	la vista para obtener el reporte de calificaciones con detalle
-- Version 1.0.0
-- =============================================

CREATE VIEW [dbo].[VW_RPT_ReporteCalificacionesDetalles]
AS
	SELECT a.ALU_CodigoAlumnoUDG AS codigoAlumno,
		a.ALU_PrimerApellido AS primerApellido,
		a.ALU_SegundoApellido AS segundoApellido,
		a.ALU_Nombre AS nombre,
		genero.CMM_Valor AS genero,
		a.ALU_Correoelectronico AS correo,
		COALESCE (grado.CMM_Valor, '') AS grado,
		COALESCE (a.ALU_Grupo, '') AS grupo,
		COALESCE (turno.CMM_Valor, '') AS turno,
		pg.PROGRU_Nivel AS nivel,
		sp.SP_Nombre AS plantel,
		CONCAT_WS(' ', p.PROG_Codigo, idioma.CMM_Valor, pam.PAMOD_Nombre, FORMAT(pg.PROGRU_Nivel, 'Nivel 00'), FORMAT(pg.PROGRU_Grupo, 'Grupo 00')) AS codigoGrupo,
		pg.PROGRU_Codigo AS codigoGrupo2,
		pamh.PAMODH_Horario AS horario,
		COALESCE (prepa.CMM_Valor, cu.CMM_Referencia) AS escuela,
		COALESCE (a.ALU_BachilleratoTecnologico, carrera.CMM_Valor) AS carrera,
		primeroCiclos.codigo AS cohorte,
		N'Si' AS regular,
		N'Si' AS carta,
		a.ALU_Codigo AS codigoProulex,
		COALESCE (estatus.CMM_Valor, 'Activo') AS estatus,
		COALESCE(ac.q1, 0) AS q1,
		COALESCE(ac.q2, 0) AS q2,
		COALESCE(ac.q3, 0) AS q3,
		COALESCE(ac.ca, 0) AS ca,
		COALESCE(ac.pf, 0) AS pf,
		COALESCE(ac.wa, 0) AS wa,
		COALESCE(ac.fe, 0) AS fe,
		COALESCE(ac.oo, 0) AS oo,
		COALESCE(ac.ee, 0) AS ee,
		COALESCE(ac.pd, 0) AS pd,
		COALESCE(ac.pz, 0) AS pz,
		COALESCE(ac.da, 0) AS da,
		COALESCE(ac.rl, 0) AS rl,
		COALESCE(ag.ALUG_CalificacionConvertida, 0) AS calificacionConvertida,
		COALESCE(ag.ALUG_CalificacionFinal, 0) AS calificacionFinal,
		pg.PROGRU_SUC_SucursalId AS sedeId,
		sp.SP_SucursalPlantelId AS plantelId,
		pg.PROGRU_PAC_ProgramacionAcademicaComercialId AS paId,
		pg.PROGRU_PACIC_CicloId AS cicloId,
		pg.PROGRU_PAMOD_ModalidadId AS modalidadId,
		pg.PROGRU_FechaInicio AS fechaInicio,
		a.ALU_CMM_PreparatoriaJOBSId AS prepaId,
		a.ALU_CMM_CentroUniversitarioJOBSId AS cuId,
		pg.PROGRU_Activo,
		a.ALU_Activo
	FROM AlumnosGrupos ag
		INNER JOIN ProgramasGrupos pg ON ag.ALUG_PROGRU_GrupoId = pg.PROGRU_GrupoId
		INNER JOIN Alumnos a ON ag.ALUG_ALU_AlumnoId = a.ALU_AlumnoId
		INNER JOIN ControlesMaestrosMultiples genero ON a.ALU_CMM_GeneroId = genero.CMM_ControlId
		INNER JOIN ControlesMaestrosMultiples estatus ON ALUG_CMM_EstatusId = estatus.CMM_ControlId
		INNER JOIN Inscripciones i ON ag.ALUG_ALU_AlumnoId = i.INS_ALU_AlumnoId AND ag.ALUG_PROGRU_GrupoId = i.INS_PROGRU_GrupoId AND INS_CMM_EstatusId != 2000512 /* Cancelada*/
		INNER JOIN ProgramasIdiomas pi ON pg.PROGRU_PROGI_ProgramaIdiomaId = pi.PROGI_ProgramaIdiomaId
		INNER JOIN ControlesMaestrosMultiples idioma ON pi.PROGI_CMM_Idioma = idioma.CMM_ControlId
		INNER JOIN Programas p ON pi.PROGI_PROG_ProgramaId = p.PROG_ProgramaId
		INNER JOIN PAModalidades pam ON pg.PROGRU_PAMOD_ModalidadId = pam.PAMOD_ModalidadId
		INNER JOIN PAModalidadesHorarios pamh ON pg.PROGRU_PAMODH_PAModalidadHorarioId = pamh.PAMODH_PAModalidadHorarioId
		LEFT JOIN ControlesMaestrosMultiples grado ON ALU_CMM_GradoId = grado.CMM_ControlId
		LEFT JOIN ControlesMaestrosMultiples turno ON ALU_CMM_TurnoId = turno.CMM_ControlId
		LEFT JOIN ControlesMaestrosMultiples prepa ON ALU_CMM_PreparatoriaJOBSId = prepa.CMM_ControlId
		LEFT JOIN ControlesMaestrosMultiples cu ON ALU_CMM_CentroUniversitarioJOBSId = cu.CMM_ControlId
		LEFT JOIN ControlesMaestrosMultiples carrera ON ALU_CMM_CarreraJOBSId = carrera.CMM_ControlId
		LEFT JOIN SucursalesPlanteles sp ON pg.PROGRU_SP_SucursalPlantelId = sp.SP_SucursalPlantelId
		LEFT JOIN Empleados e ON pg.PROGRU_EMP_EmpleadoId = e.EMP_EmpleadoId
		LEFT JOIN (SELECT ins.INS_ALU_AlumnoId alumnoId, p.PROGRU_GrupoId grupoId, pa.PACIC_Codigo codigo
				FROM (SELECT i.INS_ALU_AlumnoId, i.INS_PROGRU_GrupoId, ROW_NUMBER() OVER (PARTITION BY i.INS_ALU_AlumnoId
					  ORDER BY i.INS_FechaCreacion ASC) AS rank
					  FROM Inscripciones i) ins
					INNER JOIN ProgramasGrupos p ON ins.INS_PROGRU_GrupoId = p.PROGRU_GrupoId
					LEFT JOIN PACiclos pa ON p.PROGRU_PACIC_CicloId = pa.PACIC_CicloId
					WHERE ins.rank = 1
		) AS primeroCiclos ON alumnoId = ALU_AlumnoId
		INNER JOIN (
			SELECT *
			FROM (
				SELECT aec.AEC_ALU_AlumnoId, aec.AEC_PROGRU_GrupoId, paae.PAAE_Codigo, aec.AEC_Puntaje
				FROM AlumnosExamenesCalificaciones aec
				 INNER JOIN ProgramasIdiomasExamenesDetalles pied ON aec.AEC_PROGIED_ProgramaIdiomaExamenDetalleId = pied.PROGIED_ProgramaIdiomaExamenDetalleId
				 INNER JOIN PAActividadesEvaluacion paae ON pied.PROGIED_PAAE_ActividadEvaluacionId = paae.PAAE_ActividadEvaluacionId
			) AS AlumnosCalificaciones PIVOT(AVG(AEC_Puntaje) FOR PAAE_Codigo IN (q1, q2, q3, ca, pf, wa, fe, oo, ee, pd, pz, da, rl)) AS ac
		) AS ac ON ag.ALUG_ALU_AlumnoId = ac.AEC_ALU_AlumnoId AND pg.PROGRU_GrupoId = ac.AEC_PROGRU_GrupoId
GO
