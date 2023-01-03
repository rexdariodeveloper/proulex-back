SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_GRUPO_CAPTURA]
AS
	SELECT 
		[PROGRU_GrupoId] id,
		[PROGRU_Codigo] codigo,
		sede.[SUC_Nombre] sede,
		idioma.[CMM_Valor] idioma,
		[PAMOD_Nombre] modalidad,
		FORMAT([PROGRU_Nivel],'00') nivel,
		FORMAT([PROGRU_Grupo],'00') grupo,
		[PROGRU_FechaInicio] fechaInicio,
		[PROGRU_FechaFin] fechaFin,
		CONCAT_WS(' ',[EMP_CodigoEmpleado],'-',[EMP_Nombre],[EMP_PrimerApellido],[EMP_SegundoApellido]) profesor,
		vw.horario,
		[PROGRU_FaltasPermitidas] * 1.0 faltasPermitidas, -- porcentaje
		0.0 faltasDesertor,-- porcentaje
		CAST(CASE WHEN [PROGRU_PGINCG_ProgramaIncompanyId] IS NULL THEN 0 ELSE 1 END AS BIT) incompany,
		CAST(COALESCE([PROG_JOBS],0) AS BIT) jobs,
		CAST(COALESCE([PROG_JOBSSEMS],0) AS BIT) sems,
		CAST(COALESCE([PROG_PCP],0) AS BIT) pcp,
		PROGRU_CMM_EstatusId estatusId,
		DATEADD(DAY,(select COALESCE(CMA_Valor,0) from ControlesMaestros where CMA_Nombre = N'CM_SUMA_DIAS_FECHA_FIN'), PROGRU_FechaFin) fechaFinTolerancia
	FROM 
		(
			SELECT
				grupoId,
				(
					SELECT
						dia,
						horas,
						CONCAT_WS(' ',CAST(horaInicio as nvarchar(5)),'-',CAST(horaFin as nvarchar(5))) horario
					FROM
						[dbo].[VW_GRUPOS_HORARIOS] horarios
					WHERE
						horarios.grupoId = grupos.grupoId
					FOR JSON PATH
				) horario
			FROM
				[dbo].[VW_GRUPOS_HORARIOS] grupos
			GROUP BY
				grupoId
		) vw 
	INNER JOIN [ProgramasGrupos] ON vw.grupoId = [PROGRU_GrupoId]
	INNER JOIN [Sucursales] sede ON [PROGRU_SUC_SucursalId] = sede.[SUC_SucursalId]
	LEFT JOIN [SucursalesPlanteles] ON [PROGRU_SP_SucursalPlantelId] = [SP_SucursalPlantelId]
	INNER JOIN [ProgramasIdiomas] ON [PROGRU_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId]
	INNER JOIN [ControlesMaestrosMultiples] idioma ON [PROGI_CMM_Idioma] = idioma.[CMM_ControlId]
	INNER JOIN [PAModalidades] ON [PROGRU_PAMOD_ModalidadId] = [PAMOD_ModalidadId]
	LEFT JOIN [Empleados] ON [PROGRU_EMP_EmpleadoId] = [EMP_EmpleadoId]
	INNER JOIN [Programas] ON [PROGI_PROG_ProgramaId] = [PROG_ProgramaId]
GO