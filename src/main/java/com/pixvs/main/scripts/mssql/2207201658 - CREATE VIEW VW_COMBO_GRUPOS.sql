/**
* Created by Angel Daniel Hernández Silva on 20/07/2022.
*/

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_COMBO_GRUPOS]
AS

	SELECT

		-- Campos proyección
		PROGRU_GrupoId AS id,
		PROGRU_Codigo AS codigo,
		CONCAT(PROG_Codigo,' ',CMM_Valor,' ',PAMOD_Nombre,' Nivel ',FORMAT(PROGRU_Nivel,'00'),' Grupo ',FORMAT(PROGRU_Grupo,'00')) AS nombreGrupo,
		SUC_Nombre AS nombreSucursal,
		PROGRU_GrupoReferenciaId AS grupoReferenciaId,

		-- Campos extra
		PROGRU_FechaFinInscripcionesBecas AS fechaFinInscripcionesBecas

	FROM ProgramasGrupos
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
	INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
	WHERE PROGRU_FechaFinInscripcionesBecas >= CAST(GETDATE() AS date)

GO