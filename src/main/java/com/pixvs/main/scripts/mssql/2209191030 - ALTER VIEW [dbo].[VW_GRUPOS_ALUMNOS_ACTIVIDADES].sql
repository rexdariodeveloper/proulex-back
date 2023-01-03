SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_GRUPOS_ALUMNOS_ACTIVIDADES]
WITH SCHEMABINDING
AS
SELECT
	PROGRU_Codigo codigoGrupo,
	SUC_CodigoSucursal sucursalCodigo,
	SUC_Nombre sucursalNombre,
	PROG_Codigo programaCodigo,
	PROG_Nombre programaNombre,
	FORMAT(PROGRU_Nivel,'00') nivel,
	FORMAT(PROGRU_Grupo,'00') grupo,
	PROGRU_Cupo cupo,
	PROGRU_FechaInicio fechaInicio,
	PROGRU_FechaFin fechaFin,
	idioma.CMM_Valor idioma,
	PAMOD_Codigo modalidadCodigo,
	PAMOD_Nombre modalidadNombre,
	PAMOD_Color color,
	COALESCE(PAC_Codigo, PACIC_Codigo) programacionCodigo,
	COALESCE(PAC_Nombre, PACIC_Nombre) programacionNombre,
	PAMODH_Horario horario,
	PROGRUE_Nombre evaluacionNombre, 
	PROGRUE_Porcentaje evaluacionPorcentaje,
	PROGRUED_Puntaje actividadPuntaje,
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
	PROGRUE_ProgramaGrupoExamenId evaluacionId,
	PROGRUED_ProgramaGrupoExamenDetalleId evaluacionDetalleId
FROM
	[dbo].[Programas]
	INNER JOIN [dbo].[ProgramasIdiomas] ON [PROG_ProgramaId] = [PROGI_PROG_ProgramaId]
	INNER JOIN [dbo].[ProgramasGrupos] ON [PROGI_ProgramaIdiomaId] = [PROGRU_PROGI_ProgramaIdiomaId]
	INNER JOIN [dbo].[ControlesMaestrosMultiples] idioma ON [PROGI_CMM_Idioma] = idioma.[CMM_ControlId]
	INNER JOIN [dbo].[Sucursales] ON [PROGRU_SUC_SucursalId] = [SUC_SucursalId]
	INNER JOIN [dbo].[PAModalidades] ON [PROGRU_PAMOD_ModalidadId] = [PAMOD_ModalidadId]
	-- No todos los grupos tienen PAComercial
	LEFT JOIN [dbo].[ProgramacionAcademicaComercial] ON [PROGRU_PAC_ProgramacionAcademicaComercialId] = [PAC_ProgramacionAcademicaComercialId]
	LEFT JOIN [dbo].[PACiclos] ON [PROGRU_PACIC_CicloId] = [PACIC_CicloId]
	INNER JOIN [dbo].[PAModalidadesHorarios] ON [PROGRU_PAMODH_PAModalidadHorarioId] = [PAMODH_PAModalidadHorarioId]
	LEFT JOIN [dbo].[ProgramasGruposExamenes] ON [PROGRU_GrupoId] = [PROGRUE_PROGRU_GrupoId]
	LEFT JOIN [dbo].[ProgramasGruposExamenesDetalles] ON [PROGRUED_PROGRUE_ProgramaGrupoExamenId] = [PROGRUE_ProgramaGrupoExamenId]
	LEFT JOIN [dbo].[PAActividadesEvaluacion] ON [PROGRUED_PAAE_ActividadEvaluacionId] = [PAAE_ActividadEvaluacionId]
	LEFT JOIN [dbo].[Inscripciones] ON [PROGRU_GrupoId] = [INS_PROGRU_GrupoId]
	LEFT JOIN [dbo].[ProgramasGruposListadoClases] ON [PROGRULC_PROGRU_GrupoId] = [PROGRU_GrupoId] AND CAST([PROGRULC_Fecha] AS DATE) = GETDATE()
	LEFT JOIN [dbo].[Empleados] profesor ON [PROGRU_EMP_EmpleadoId] = profesor.[EMP_EmpleadoId]
	LEFT JOIN [dbo].[Empleados] suplente ON [PROGRULC_EMP_EmpleadoId] = suplente.[EMP_EmpleadoId]
	LEFT JOIN [dbo].[Alumnos] ON [INS_ALU_AlumnoId] = [ALU_AlumnoId]
GO