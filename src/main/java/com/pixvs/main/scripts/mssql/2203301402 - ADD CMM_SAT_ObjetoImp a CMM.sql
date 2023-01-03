DECLARE @reseed INT = (SELECT MAX(CMM_ControlId) FROM ControlesMaestrosMultiples WHERE CMM_ControlId <= 1000000)

DBCC checkident ('ControlesMaestrosMultiples', reseed, @reseed)
GO

INSERT INTO ControlesMaestrosMultiples
(
    CMM_Control,
    CMM_Activo,
    CMM_Referencia,
	CMM_Valor,
    CMM_Sistema,
    CMM_FechaCreacion
)
VALUES
( 'CMM_SAT_ObjetoImp', 1, '01', 'No objeto de impuesto.', 0, GETDATE() ),
( 'CMM_SAT_ObjetoImp', 1, '02', 'Sí objeto de impuesto.', 0, GETDATE() ),
( 'CMM_SAT_ObjetoImp', 1, '03', 'Sí objeto del impuesto y no obligado al desglose.', 0, GETDATE() )
GO