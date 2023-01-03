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
( 2000130, 'CMM_SAT_TipoRelacion', 1, '01', 'Nota de crédito de los documentos relacionados.', 1, GETDATE() ),
( 2000131, 'CMM_SAT_TipoRelacion', 1, '02', 'Nota de débito de los documentos relacionados.', 1, GETDATE() ),
( 2000132, 'CMM_SAT_TipoRelacion', 1, '03', 'Devolución de mercancía sobre facturas o traslados previos.', 1, GETDATE() ),
( 2000133, 'CMM_SAT_TipoRelacion', 1, '04', 'Sustitución de los CFDI previos.', 1, GETDATE() ),
( 2000134, 'CMM_SAT_TipoRelacion', 1, '05', 'Traslados de mercancías facturados previamente.', 1, GETDATE() ),
( 2000135, 'CMM_SAT_TipoRelacion', 1, '06', 'Factura generada por los traslados previos.', 1, GETDATE() ),
( 2000136, 'CMM_SAT_TipoRelacion', 1, '07', 'CFDI por aplicación de anticipo.', 1, GETDATE() )
GO
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO

DECLARE @reseed INT = (SELECT MAX(CMM_ControlId) FROM ControlesMaestrosMultiples WHERE CMM_ControlId <= 1000000)

DBCC checkident ('ControlesMaestrosMultiples', reseed, @reseed)
GO