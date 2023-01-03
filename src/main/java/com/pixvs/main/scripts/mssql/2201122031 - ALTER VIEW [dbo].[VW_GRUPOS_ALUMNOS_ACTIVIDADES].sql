SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_GRUPOS_ALUMNOS_ACTIVIDADES]
AS
SELECT
	CONCAT( SUBSTRING(SUC_CodigoSucursal, 1, 3),
			SUBSTRING(PROG_Codigo, 1, 3),
			SUBSTRING(CMM_Referencia, 1, 3),
			SUBSTRING(PAMOD_Codigo, 1, 3),
			FORMAT(PROGRU_Nivel,'00'),
			PAMODH_Codigo,
			FORMAT(PROGRU_Grupo,'00')
	) codigoGrupo,
	SUC_CodigoSucursal sucursalCodigo,
	SUC_Nombre sucursalNombre,
	PROG_Codigo programaCodigo,
	PROG_Nombre programaNombre,
	FORMAT(PROGRU_Nivel,'00') nivel,
	FORMAT(PROGRU_Grupo,'00') grupo,
	PROGRU_Cupo cupo,
	-- PROGRU_EMP_EmpleadoId,
	-- PROGRULC_EMP_EmpleadoId,
	PROGRU_FechaInicio fechaInicio,
	PROGRU_FechaFin fechaFin,
	idioma.CMM_Valor idioma,
	PAMOD_Codigo modalidadCodigo,
	PAMOD_Nombre modalidadNombre,
	PAMOD_Color color,
	COALESCE(PAC_Codigo, PACIC_Codigo) programacionCodigo,
	COALESCE(PAC_Nombre, PACIC_Nombre) programacionNombre,
	PAMODH_Horario horario,
	PROGIE_Nombre evaluacionNombre,
	PROGIE_Porcentaje evaluacionPorcentaje,
	PROGIED_Puntaje actividadPuntaje,
	PAAE_Actividad actividadNombre,
	CONCAT_WS(' ',profesor.EMP_Nombre, profesor.EMP_PrimerApellido, profesor.EMP_SegundoApellido) profesor,
	CONCAT_WS(' ',suplente.EMP_Nombre, suplente.EMP_PrimerApellido, suplente.EMP_SegundoApellido) suplente,
	CONCAT_WS(' ',ALU_Nombre, ALU_PrimerApellido, ALU_SegundoApellido) alumno,

	SUC_SucursalId sucursalId,
	PROG_ProgramaId programaId,
	PROGRU_GrupoId grupoId,
	PAMOD_ModalidadId modalidadId,
	CMM_ControlId idiomaId,
	COALESCE(PROGRULC_EMP_EmpleadoId, PROGRU_EMP_EmpleadoId) profesorId,
	INS_ALU_AlumnoId alumnoId,
	PROGIE_ProgramaIdiomaExamenId evaluacionId,
	PROGIED_ProgramaIdiomaExamenDetalleId evaluacionDetalleId
FROM
	Programas
	INNER JOIN ProgramasIdiomas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ProgramasGrupos ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN ControlesMaestrosMultiples idioma ON PROGI_CMM_Idioma = idioma.CMM_ControlId
	
	INNER JOIN Sucursales ON PROGRU_SUC_SucursalId = SUC_SucursalId
	INNER JOIN PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
	-- No todos los grupos tienen PAComercial
	LEFT JOIN ProgramacionAcademicaComercial ON PROGRU_PAC_ProgramacionAcademicaComercialId = PAC_ProgramacionAcademicaComercialId
	LEFT JOIN PACiclos ON PROGRU_PACIC_CicloId = PACIC_CicloId
	INNER JOIN PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId

	INNER JOIN ProgramasIdiomasNiveles on PROGI_ProgramaIdiomaId = PROGIN_PROGI_ProgramaIdiomaId AND PROGRU_Nivel BETWEEN PROGIN_NivelInicial AND PROGIN_NivelFinal
	LEFT JOIN ProgramasIdiomasExamenes on PROGIN_ProgramaIdiomaNivelId = PROGIE_PROGIN_ProgramaIdiomaNivelId
	LEFT JOIN ProgramasIdiomasExamenesDetalles on PROGIE_ProgramaIdiomaExamenId = PROGIED_PROGIE_ProgramaIdiomaExamenId
	LEFT JOIN PAActividadesEvaluacion on PROGIED_PAAE_ActividadEvaluacionId = PAAE_ActividadEvaluacionId
	LEFT JOIN Inscripciones on PROGRU_GrupoId = INS_PROGRU_GrupoId
	LEFT JOIN ProgramasGruposListadoClases on PROGRULC_PROGRU_GrupoId = PROGRU_GrupoId AND CAST(PROGRULC_Fecha AS date) = GETDATE()
	LEFT JOIN Empleados profesor ON PROGRU_EMP_EmpleadoId = profesor.EMP_EmpleadoId
	LEFT JOIN Empleados suplente ON PROGRULC_EMP_EmpleadoId = suplente.EMP_EmpleadoId
	LEFT JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
	WHERE
		PROGIE_Activo = 1
GO