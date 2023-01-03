-- Nodo Facturación
DECLARE @nodoFacturacionId INT = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Facturación' AND MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'MÓDULOS'))

-- Nodos Facturas
UPDATE MenuPrincipal SET MP_NodoPadreId = @nodoFacturacionId WHERE MP_URL IN ('/app/facturacion/factura-nota-venta', '/app/facturacion/factura-global')

-- Nodo Facturación anterior a remisión
UPDATE MenuPrincipal SET
		MP_NodoPadreId = @nodoFacturacionId,
		MP_Titulo = 'Factura Remisión',
		MP_TituloEN = 'Remission invoice',
		MP_Icono = 'featured_play_list',
		MP_Orden = 3,
		MP_URL = '/app/facturacion/factura-remision'
WHERE MP_NodoId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'MÓDULOS') AND MP_Titulo = 'Ventas') AND MP_Titulo = 'Facturación')

UPDATE MenuPrincipal SET MP_Orden = 3, MP_Titulo = 'Factura Miscelánea', MP_NodoPadreId = @nodoFacturacionId WHERE MP_URL = '/app/facturacion/factura-miscelanea'

UPDATE MenuPrincipal SET MP_Orden = 4 WHERE MP_NodoPadreId = @nodoFacturacionId AND MP_Titulo = 'Factura Remisión'

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
( 2000475, 'CMM_CXCF_TipoRegistro', 1, NULL,'Factura Miscelánea', 1, GETDATE() )
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO

DECLARE @reseed INT = (SELECT MAX(CMM_ControlId) FROM ControlesMaestrosMultiples WHERE CMM_ControlId <= 1000000)

DBCC checkident ('ControlesMaestrosMultiples', reseed, @reseed)
GO