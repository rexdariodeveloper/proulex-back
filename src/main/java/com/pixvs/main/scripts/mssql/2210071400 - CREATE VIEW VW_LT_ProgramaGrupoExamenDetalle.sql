SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Rene Carrillo
-- Modify author:
-- Create date: 07/10/2022
-- Modify date:
-- Description:	La vista de listado de detalle de examen en programa grupo
-- Version 1.0.0
-- =============================================
CREATE VIEW [dbo].[VW_LT_ProgramaGrupoExamenDetalle]
AS
	SELECT DISTINCT pged.PROGRUED_ProgramaGrupoExamenDetalleId AS Id,
		ppae.PAAE_Actividad AS Actividad,
		formato.CMM_Valor AS Formato,
		pged.PROGRUED_Puntaje AS Score,
		pged.PROGRUED_Time AS Tiempo,
		pge.PROGRUE_PROGRU_GrupoId AS GrupoId,
		ROW_NUMBER() OVER (ORDER BY ppae.PAAE_Actividad ASC) AS Orden
	FROM ProgramasGruposExamenesDetalles pged
		INNER JOIN ProgramasGruposExamenes pge ON pged.PROGRUED_PROGRUE_ProgramaGrupoExamenId = pge.PROGRUE_ProgramaGrupoExamenId
		INNER JOIN PAActividadesEvaluacion ppae ON pged.PROGRUED_PAAE_ActividadEvaluacionId = ppae.PAAE_ActividadEvaluacionId
		INNER JOIN ControlesMaestrosMultiples formato ON pged.PROGRUED_CMM_TestId = formato.CMM_ControlId