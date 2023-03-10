SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ==============================================
-- Author:		--------
-- Modified author: Rene Carrillo
-- Create date: ------
-- Modified date: 04/11/2022
-- Description:	Trigger despues de creo un nuevo grupo
-- ==============================================

ALTER TRIGGER [dbo].[trg_migrarCriteriosVigentes] ON [dbo].[ProgramasGrupos]
AFTER INSERT
AS
	INSERT INTO [dbo].[ProgramasGruposExamenes]([PROGRUE_PROGRU_GrupoId],[PROGRUE_Nombre],[PROGRUE_Porcentaje])
	SELECT ProgramasGrupos.PROGRU_GrupoId, PROGIE_Nombre, PROGIE_Porcentaje FROM
		ProgramasGrupos
		INNER JOIN ProgramasIdiomasNiveles ON PROGRU_PROGI_ProgramaIdiomaId = PROGIN_PROGI_ProgramaIdiomaId AND PROGRU_Nivel BETWEEN PROGIN_NivelInicial AND PROGIN_NivelFinal
		INNER JOIN ProgramasIdiomasExamenes ON PROGIN_ProgramaIdiomaNivelId = PROGIE_PROGIN_ProgramaIdiomaNivelId
		INNER JOIN INSERTED ON INSERTED.PROGRU_GrupoId = ProgramasGrupos.PROGRU_GrupoId
	WHERE
		ProgramasGrupos.PROGRU_CMM_EstatusId = 2000620
		AND PROGIN_Activo = 1
		AND PROGIE_Activo = 1
	ORDER BY ProgramasGrupos.PROGRU_GrupoId, PROGIE_Orden

	INSERT INTO [dbo].[ProgramasGruposExamenesDetalles](PROGRUED_PROGRUE_ProgramaGrupoExamenId, PROGRUED_PAAE_ActividadEvaluacionId, PROGRUED_CMM_TestId, PROGRUED_Puntaje, PROGRUED_Time, PROGRUED_Continuos)
	SELECT
		PROGRUE_ProgramaGrupoExamenId, PROGIED_PAAE_ActividadEvaluacionId, PROGIED_CMM_TestId, PROGIED_Puntaje, PROGIED_Time, PROGIED_Continuos
	FROM
		ProgramasGrupos
		INNER JOIN ProgramasGruposExamenes ON PROGRU_GrupoId = PROGRUE_PROGRU_GrupoId
		LEFT JOIN ProgramasGruposExamenesDetalles ON PROGRUE_ProgramaGrupoExamenId = PROGRUED_PROGRUE_ProgramaGrupoExamenId
		INNER JOIN ProgramasIdiomasNiveles ON PROGRU_PROGI_ProgramaIdiomaId = PROGIN_PROGI_ProgramaIdiomaId AND PROGRU_Nivel BETWEEN PROGIN_NivelInicial AND PROGIN_NivelFinal
		INNER JOIN ProgramasIdiomasExamenes ON PROGIN_ProgramaIdiomaNivelId = PROGIE_PROGIN_ProgramaIdiomaNivelId AND PROGRUE_Nombre = PROGIE_Nombre
		INNER JOIN ProgramasIdiomasExamenesDetalles ON PROGIED_PROGIE_ProgramaIdiomaExamenId = PROGIE_ProgramaIdiomaExamenId
		INNER JOIN INSERTED ON INSERTED.PROGRU_GrupoId = ProgramasGrupos.PROGRU_GrupoId
		--INNER JOIN PAActividadesEvaluacion ON PROGIED_PAAE_ActividadEvaluacionId = PAAE_ActividadEvaluacionId
	WHERE
		PROGRUED_ProgramaGrupoExamenDetalleId IS NULL
		AND PROGIN_Activo = 1
		AND PROGIE_Activo = 1
		AND PROGIED_Activo = 1