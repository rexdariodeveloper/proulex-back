--------

CREATE OR ALTER VIEW [dbo].[VW_ALUMNOS_GRUPOS_FALTAS] WITH SCHEMABINDING
AS
(
	SELECT
		alumnoId,
		PROGRU_GrupoId grupoId,
		COALESCE(CAST(minutos AS FLOAT) / 60, 0) + (COALESCE(dias, 0) * PAMOD_HorasPorDia) horas,
		(COALESCE(diasTotal,0) * PAMOD_HorasPorDia ) limite,
		(COALESCE(diasTotal,0) * PAMOD_HorasPorDia) horasTotal,
		CASE 
			WHEN 
				COALESCE(CAST(minutos AS FLOAT) / 60, 0) + (COALESCE(dias, 0) * PAMOD_HorasPorDia) >
				(COALESCE(diasTotal,0) * PAMOD_HorasPorDia )
			THEN
				CAST(1 AS BIT)
			ELSE
				CAST(0 AS BIT)
		END limiteFaltasExcedido
	FROM
		[dbo].[ProgramasGrupos]
		INNER JOIN [dbo].[PAModalidades] ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
		LEFT JOIN (
		SELECT
			AA_ALU_AlumnoId alumnoId, 
			AA_PROGRU_GrupoId grupoId, 
			SUM(COALESCE(AA_MinutosRetardo,0)) minutos,
			SUM(CASE WHEN AA_CMM_TipoAsistenciaId = 2000551 THEN 1 ELSE 0 END) dias,
			COUNT(*) diasTotal
		FROM 
			[dbo].[AlumnosAsistencias]
		GROUP BY
			AA_ALU_AlumnoId, AA_PROGRU_GrupoId
	)aa ON PROGRU_GrupoId = aa.grupoId
)
GO


--------

CREATE OR ALTER VIEW [dbo].[VW_ProgramasGrupos] WITH SCHEMABINDING AS

	SELECT
		-- Datos del grupo
		PROGRU_GrupoId AS id,
		PROGRU_Codigo AS codigo,
		PROGRU_FechaInicio AS fechaInicio,
		PROGRU_FechaFin AS fechaFin,
		PROGRU_Nivel AS nivel,
		PROGRU_Grupo AS grupo,
		PROGRU_Cupo AS cupo,
		PROGRU_Multisede AS multisede,
		PROGRU_CalificacionMinima AS calificacionMinima,
		--PROGRU_FaltasPermitidas AS faltasPermitidas,
		PROGRU_Aula AS aula,
		PROGRU_Comentarios AS comentarios,
		CAST(DATEADD(DAY,CAST(CMDiasTolerancia.CMA_Valor AS int),PROGRU_FechaFin) AS DATE) AS fechaFinTolerancia,
		CAST(DATEADD(DAY,CAST(CMPlazoDiasReinscripcion.CMA_Valor AS int),PROGRU_FechaFin) AS DATE) AS fechaLimiteReinscripcion,
		
		-- Datos del profesor
		PROGRU_EMP_EmpleadoId AS empleadoId,
		PROGRU_CategoriaProfesor AS categoriaProfesor,
		PROGRU_SueldoProfesor AS sueldoProfesor,
		
		-- Relaciones
		PROGRU_SUC_SucursalId AS sucursalId,
		PROGRU_SP_SucursalPlantelId AS sucursalPlantelId,
		PROGRU_PAC_ProgramacionAcademicaComercialId AS programacionAcademicaComercialId,
		PROGRU_PROGI_ProgramaIdiomaId AS programaIdiomaId,
		PROGRU_CMM_PlataformaId AS plataformaId,
		PROGRU_CMM_TipoGrupoId AS tipoGrupoId,
		PROGRU_PAMOD_ModalidadId AS paModalidadId,
		PROGRU_PAMODH_PAModalidadHorarioId AS paModalidadHorarioId,
		PROGRU_PACIC_CicloId AS paCicloId,

		-- Datos de control
		PROGRU_CMM_EstatusId AS estatusId,
		CMMEstatus.CMM_Valor AS estatusValor,
		PROGRU_FechaCreacion AS fechaCreaccion,
		PROGRU_USU_CreadoPorId AS creadoPorId,
		PROGRU_FechaUltimaModificacion AS fechaUltimaModificacion,
		PROGRU_USU_ModificadoPorId AS modificadoPorId
	FROM [dbo].[ProgramasGrupos]
	LEFT JOIN [dbo].[ControlesMaestros] AS CMDiasTolerancia ON CMDiasTolerancia.CMA_Nombre = 'CM_SUMA_DIAS_FECHA_FIN'
	LEFT JOIN [dbo].[ControlesMaestros] AS CMPlazoDiasReinscripcion ON CMPlazoDiasReinscripcion.CMA_Nombre = 'CMA_Inscripciones_PlazoDiasReinscripcion'
	INNER JOIN [dbo].[ControlesMaestrosMultiples] AS CMMEstatus ON CMMEstatus.CMM_ControlId = PROGRU_CMM_EstatusId

GO


--------

ALTER TABLE [ProgramasGrupos]
ALTER COLUMN [PROGRU_FaltasPermitidas][decimal](10,2) NULL
GO

---------
CREATE OR ALTER VIEW [dbo].[VW_ALUMNOS_GRUPOS_FALTAS] WITH SCHEMABINDING
AS
(
	SELECT
		alumnoId,
		PROGRU_GrupoId grupoId,
		COALESCE(CAST(minutos AS FLOAT) / 60, 0) + (COALESCE(dias, 0) * PAMOD_HorasPorDia) horas,
		(COALESCE(diasTotal,0) * PAMOD_HorasPorDia * (CAST(PROGRU_FaltasPermitidas AS FLOAT)/ 100)) limite,
		(COALESCE(diasTotal,0) * PAMOD_HorasPorDia) horasTotal,
		CASE 
			WHEN 
				COALESCE(CAST(minutos AS FLOAT) / 60, 0) + (COALESCE(dias, 0) * PAMOD_HorasPorDia) >
				(COALESCE(diasTotal,0) * PAMOD_HorasPorDia * (CAST(PROGRU_FaltasPermitidas AS FLOAT)/ 100))
			THEN
				CAST(1 AS BIT)
			ELSE
				CAST(0 AS BIT)
		END limiteFaltasExcedido
	FROM
		[dbo].[ProgramasGrupos]
		INNER JOIN [dbo].[PAModalidades] ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
		LEFT JOIN (
		SELECT
			AA_ALU_AlumnoId alumnoId, 
			AA_PROGRU_GrupoId grupoId, 
			SUM(COALESCE(AA_MinutosRetardo,0)) minutos,
			SUM(CASE WHEN AA_CMM_TipoAsistenciaId = 2000551 THEN 1 ELSE 0 END) dias,
			COUNT(*) diasTotal
		FROM 
			[dbo].[AlumnosAsistencias]
		GROUP BY
			AA_ALU_AlumnoId, AA_PROGRU_GrupoId
	)aa ON PROGRU_GrupoId = aa.grupoId
)
GO

----
/******************************/
/***** VW_ProgramasGrupos *****/
/******************************/

CREATE OR ALTER VIEW [dbo].[VW_ProgramasGrupos] WITH SCHEMABINDING AS

	SELECT
		-- Datos del grupo
		PROGRU_GrupoId AS id,
		PROGRU_Codigo AS codigo,
		PROGRU_FechaInicio AS fechaInicio,
		PROGRU_FechaFin AS fechaFin,
		PROGRU_Nivel AS nivel,
		PROGRU_Grupo AS grupo,
		PROGRU_Cupo AS cupo,
		PROGRU_Multisede AS multisede,
		PROGRU_CalificacionMinima AS calificacionMinima,
		PROGRU_FaltasPermitidas AS faltasPermitidas,
		PROGRU_Aula AS aula,
		PROGRU_Comentarios AS comentarios,
		CAST(DATEADD(DAY,CAST(CMDiasTolerancia.CMA_Valor AS int),PROGRU_FechaFin) AS DATE) AS fechaFinTolerancia,
		CAST(DATEADD(DAY,CAST(CMPlazoDiasReinscripcion.CMA_Valor AS int),PROGRU_FechaFin) AS DATE) AS fechaLimiteReinscripcion,
		
		-- Datos del profesor
		PROGRU_EMP_EmpleadoId AS empleadoId,
		PROGRU_CategoriaProfesor AS categoriaProfesor,
		PROGRU_SueldoProfesor AS sueldoProfesor,
		
		-- Relaciones
		PROGRU_SUC_SucursalId AS sucursalId,
		PROGRU_SP_SucursalPlantelId AS sucursalPlantelId,
		PROGRU_PAC_ProgramacionAcademicaComercialId AS programacionAcademicaComercialId,
		PROGRU_PROGI_ProgramaIdiomaId AS programaIdiomaId,
		PROGRU_CMM_PlataformaId AS plataformaId,
		PROGRU_CMM_TipoGrupoId AS tipoGrupoId,
		PROGRU_PAMOD_ModalidadId AS paModalidadId,
		PROGRU_PAMODH_PAModalidadHorarioId AS paModalidadHorarioId,
		PROGRU_PACIC_CicloId AS paCicloId,

		-- Datos de control
		PROGRU_CMM_EstatusId AS estatusId,
		CMMEstatus.CMM_Valor AS estatusValor,
		PROGRU_FechaCreacion AS fechaCreaccion,
		PROGRU_USU_CreadoPorId AS creadoPorId,
		PROGRU_FechaUltimaModificacion AS fechaUltimaModificacion,
		PROGRU_USU_ModificadoPorId AS modificadoPorId
	FROM [dbo].[ProgramasGrupos]
	LEFT JOIN [dbo].[ControlesMaestros] AS CMDiasTolerancia ON CMDiasTolerancia.CMA_Nombre = 'CM_SUMA_DIAS_FECHA_FIN'
	LEFT JOIN [dbo].[ControlesMaestros] AS CMPlazoDiasReinscripcion ON CMPlazoDiasReinscripcion.CMA_Nombre = 'CMA_Inscripciones_PlazoDiasReinscripcion'
	INNER JOIN [dbo].[ControlesMaestrosMultiples] AS CMMEstatus ON CMMEstatus.CMM_ControlId = PROGRU_CMM_EstatusId

GO

----------------------------------
UPDATE ProgramasGrupos
SET PROGRU_FaltasPermitidas=15.79
WHERE PROGRU_CMM_EstatusId=2000620
GO