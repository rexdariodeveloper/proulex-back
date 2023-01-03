delete
from ControlesMaestrosMultiples
where CMM_ControlId = 140

GO

SET IDENTITY_INSERT ControlesMaestrosMultiples ON
GO
INSERT INTO ControlesMaestrosMultiples
	(CMM_ControlId, CMM_Control, CMM_Valor, CMM_Activo, CMM_Referencia, CMM_Sistema, CMM_USU_CreadoPorId, CMM_FechaCreacion, CMM_USU_ModificadoPorId, CMM_FechaModificacion, CMM_Orden, CMM_ARC_ImagenId)
VALUES
	(1000191, N'CMM_USU_TipoId', N'Super Admin', 1, null, 1, null, GETDATE(), null, null, null, null),
	(1000192, N'CMM_USU_TipoId', N'Estudiante SIIAU', 1, null, 1, null, GETDATE(), null, null, null, null)
GO
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO