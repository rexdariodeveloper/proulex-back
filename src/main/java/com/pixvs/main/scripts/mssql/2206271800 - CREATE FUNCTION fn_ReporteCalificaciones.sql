SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Rene Carrillo
-- Create date: 27/06/2022
-- Modify date:
-- Description:	Funcion para obtener el reporte de calificaciones
-- Version 1.0.0
-- =============================================
CREATE OR ALTER FUNCTION [dbo].[fn_ReporteCalificaciones]
(
	@GrupoId INT
)
RETURNS TABLE
AS
RETURN
(
	SELECT ROW_NUMBER() OVER(ORDER BY a.ALU_PrimerApellido ASC) AS Numero,
		a.ALU_Codigo AS CodigoProulex,
		COALESCE(a.ALU_Folio, '') AS Folio,
		COALESCE(a.ALU_PrimerApellido, '') + ' ' + COALESCE(a.ALU_SegundoApellido, '') + ' ' + COALESCE(a.ALU_Nombre, '') AS Nombre,
		COALESCE (cmm.CMM_Valor, 'Activo') AS Estatus,
		COALESCE(ag.ALUG_CalificacionConvertida, 0) As CalificacionFinal
	FROM ProgramasGrupos pg
		INNER JOIN Inscripciones i ON pg.PROGRU_GrupoId = i.INS_PROGRU_GrupoId AND i.INS_CMM_EstatusId != 2000512
		INNER JOIN AlumnosGrupos ag ON i.INS_InscripcionId = ag.ALUG_INS_InscripcionId
		INNER JOIN ControlesMaestrosMultiples cmm ON ag.ALUG_CMM_EstatusId = cmm.CMM_ControlId
		INNER JOIN Alumnos a ON ag.ALUG_ALU_AlumnoId = a.ALU_AlumnoId
	WHERE pg.PROGRU_GrupoId = @GrupoId
)