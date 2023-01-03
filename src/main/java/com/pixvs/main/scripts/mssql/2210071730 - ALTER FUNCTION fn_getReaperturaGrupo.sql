SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Rene Carrillo
-- Create date: 13/09/2022
-- Modify date: 07/10/2022
-- Description: la funcion para obtener la Reapertura de Grupo con Codigo
-- Version 1.0.1
-- =============================================

ALTER FUNCTION [dbo].[fn_getReaperturaGrupo] (@codigo VARCHAR(MAX))
RETURNS TABLE
AS
RETURN
(
	SELECT pg.PROGRU_GrupoId AS Id,
		s.SUC_Nombre AS Sede,
		p.PROG_Codigo AS Programa,
		idioma.CMM_Valor AS Idioma,
		pg.PROGRU_Nivel AS Nivel,
		pamh.PAMODH_Horario AS Horario,
		pg.PROGRU_FechaInicio AS FechaInicio,
		pg.PROGRU_FechaFinInscripciones AS FechaFinInscripcion,
		pg.PROGRU_FechaFinInscripcionesBecas AS FechaFinInscripcionBeca
	FROM ProgramasGrupos pg
		INNER JOIN Sucursales s ON pg.PROGRU_SUC_SucursalId = s.SUC_SucursalId
		INNER JOIN ProgramasIdiomas pi ON pg.PROGRU_PROGI_ProgramaIdiomaId = pi.PROGI_ProgramaIdiomaId
		INNER JOIN Programas p ON pi.PROGI_PROG_ProgramaId = p.PROG_ProgramaId
		INNER JOIN ControlesMaestrosMultiples idioma ON pi.PROGI_CMM_Idioma = idioma.CMM_ControlId
		INNER JOIN PAModalidadesHorarios pamh ON pg.PROGRU_PAMODH_PAModalidadHorarioId = pamh.PAMODH_PAModalidadHorarioId
	WHERE pg.PROGRU_CMM_EstatusId = 2000620
		AND pg.PROGRU_Codigo = @codigo
)