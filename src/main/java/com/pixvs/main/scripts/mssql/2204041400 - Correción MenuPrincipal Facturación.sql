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

-- Nuevo nodo Factura Miscelanea
INSERT INTO MenuPrincipal
VALUES
(
    @nodoFacturacionId, -- MP_NodoPadreId - int
    'Factura Miscelánea', -- MP_Titulo - varchar
    'Miscellaneous invoice', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'view_list', -- MP_Icono - varchar
    4, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/facturacion/factura-miscelanea', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0, -- MP_Repetible - bit
    0 -- MP_Personalizado - bit
)