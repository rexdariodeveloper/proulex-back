SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
(
    CMM_ControlId,
	CMM_Control,
    CMM_Activo,
    CMM_Referencia,
	CMM_Valor,
    CMM_Sistema,
    CMM_FechaCreacion
)
VALUES
( 1000030, 'CMM_EMPC_TipoPermiso', 1, NULL,'Ocultar', 1, GETDATE() ),
( 1000031, 'CMM_EMPC_TipoPermiso', 1, NULL,'Solo lectura', 1, GETDATE() )
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO

DECLARE @reseed INT = (SELECT MAX(CMM_ControlId) FROM ControlesMaestrosMultiples WHERE CMM_ControlId <= 1000000)

DBCC checkident ('ControlesMaestrosMultiples', reseed, @reseed)
GO