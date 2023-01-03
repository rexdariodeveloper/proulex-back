/**
* Created by Angel Daniel Hernández Silva on 22/10/2021.
* Object: CREATE VIEW [dbo].[VW_ProgramasGrupos]
*/

/*******************************/
/***** CMM - Estatus grupo *****/
/*******************************/

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO

INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_USU_CreadoPorId],
	[CMM_FechaCreacion],
	[CMM_FechaModificacion],
	[CMM_USU_ModificadoPorId],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 2000620,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PROGRU_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Activo'
),(
	/* [CMM_ControlId] */ 2000621,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PROGRU_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Finalizado'
),(
	/* [CMM_ControlId] */ 2000622,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_PROGRU_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cancelado'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

/********************************/
/***** PROGRU_CMM_EstatusId *****/
/********************************/

ALTER TABLE [dbo].[ProgramasGrupos] ADD [PROGRU_CMM_EstatusId] [int] NULL
GO

UPDATE ProgramasGrupos SET PROGRU_CMM_EstatusId = 2000620
FROM ProgramasGrupos
LEFT JOIN ControlesMaestros AS CMDiasTolerancia ON CMDiasTolerancia.CMA_Nombre = 'CM_SUMA_DIAS_FECHA_FIN'
WHERE
	PROGRU_Activo = 1
	AND DATEADD(DAY,CAST(COALESCE(CMDiasTolerancia.CMA_Valor,'0') AS int),PROGRU_FechaFin) >= GETDATE()
GO

UPDATE ProgramasGrupos SET PROGRU_CMM_EstatusId = 2000621
FROM ProgramasGrupos
LEFT JOIN ControlesMaestros AS CMDiasTolerancia ON CMDiasTolerancia.CMA_Nombre = 'CM_SUMA_DIAS_FECHA_FIN'
WHERE
	PROGRU_Activo = 1
	AND DATEADD(DAY,CAST(COALESCE(CMDiasTolerancia.CMA_Valor,'0') AS int),PROGRU_FechaFin) < GETDATE()
GO

UPDATE ProgramasGrupos SET PROGRU_CMM_EstatusId = 2000622 WHERE PROGRU_Activo = 0
GO

ALTER TABLE [dbo].[ProgramasGrupos] ALTER COLUMN [PROGRU_CMM_EstatusId] [int] NOT NULL
GO

ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_CMM_EstatusId] FOREIGN KEY([PROGRU_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_CMM_EstatusId]
GO

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