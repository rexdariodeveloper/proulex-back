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
( 1000060, 'CMM_Autofactura_TipoVigenciaFacturar', 1, NULL,'Tipo Fecha', 1, GETDATE() ),
( 1000061, 'CMM_Autofactura_TipoVigenciaFacturar', 1, NULL,'Fin de mes', 1, GETDATE() )
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO

DECLARE @reseed INT = (SELECT MAX(CMM_ControlId) FROM ControlesMaestrosMultiples WHERE CMM_ControlId <= 1000000)

DBCC checkident ('ControlesMaestrosMultiples', reseed, @reseed)
GO

INSERT INTO ControlesMaestros
VALUES
( 'CMA_Autofactura_TipoVigenciaFacturar', '1000061', 1, GETDATE(), NULL ),
( 'CMA_Autofactura_DiasFacturar', '', 1, GETDATE(), NULL ),
( 'CMA_Autofactura_DiasDescargar', '30', 1, GETDATE(), NULL ),
( 'CMA_Autofactura_URL', 'https://autofactura.com.mx', 1, GETDATE(), NULL )
GO