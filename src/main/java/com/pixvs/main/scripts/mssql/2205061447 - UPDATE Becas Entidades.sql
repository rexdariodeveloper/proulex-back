UPDATE BEC
SET BEC.BECU_ENBE_EntidadBecaId=ENBE_EntidadBecaId
FROM BecasUDG BEC
INNER JOIN ControlesMaestrosMultiples ON BECU_CMM_EntidadId = CMM_ControlId
INNER JOIN EntidadesBecas ON ENBE_Codigo = CMM_Referencia
GO