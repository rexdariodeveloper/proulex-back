SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples(CMM_ControlId, CMM_Control, CMM_Valor, CMM_Activo, CMM_Referencia, CMM_Sistema, CMM_USU_CreadoPorId, CMM_FechaCreacion, CMM_USU_ModificadoPorId, CMM_FechaModificacion)
VALUES(2000123, 'CMM_CXPF_TipoRegistro', 'Factura Servicio CXP', 1, NULL, 1, NULL, GETDATE(), NULL, NULL)
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO