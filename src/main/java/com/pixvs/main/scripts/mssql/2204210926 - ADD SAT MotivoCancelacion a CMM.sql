SET IDENTITY_INSERT ControlesMaestrosMultiples ON
GO
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
( 2000180, 'CMM_SAT_MotivoCancelacion', 1, '01', 'Comprobante emitido con errores con relación.', 1, GETDATE() ),
( 2000181, 'CMM_SAT_MotivoCancelacion', 1, '02', 'Comprobante emitido con errores sin relación.', 1, GETDATE() ),
( 2000182, 'CMM_SAT_MotivoCancelacion', 1, '03', 'No se llevó a cabo la operación.', 1, GETDATE() ),
( 2000183, 'CMM_SAT_MotivoCancelacion', 1, '04', 'Operación nominativa relacionada en una factura global.', 1, GETDATE() )
GO
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO

DECLARE @reseed INT = (SELECT MAX(CMM_ControlId) FROM ControlesMaestrosMultiples WHERE CMM_ControlId <= 1000000)

DBCC checkident ('ControlesMaestrosMultiples', reseed, @reseed)
GO