/**
* Created by Angel Daniel Hernández Silva on 30/08/2021.
* Object:  ALTER VIEW [dbo].[VW_Codigo_ProgramasGrupos]
*/

CREATE OR ALTER VIEW [dbo].[VW_Codigo_ProgramasGrupos] AS

	SELECT
		PROGRU_GrupoId,
		CONCAT(SUBSTRING(SUC_CodigoSucursal, 1, 3),COALESCE(SP_Codigo,''),SUBSTRING(PROG_Codigo, 1, 3),SUBSTRING(Idioma.CMM_Referencia, 1, 3),SUBSTRING(PAMOD_Codigo, 1, 3),FORMAT(PROGRU_Nivel,'00'),PAMODH_Codigo,FORMAT(PROGRU_Grupo,'00')) AS grupo
	FROM ProgramasGrupos
	INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
	LEFT JOIN SucursalesPlanteles on SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples AS Idioma ON Idioma.CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
	INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId

GO