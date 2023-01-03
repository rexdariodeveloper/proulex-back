INSERT INTO MenuPrincipal
VALUES
(
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'MÓDULOS'), -- MP_NodoPadreId - int
    'Facturación', -- MP_Titulo - varchar
    'Invoicing', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'receipt', -- MP_Icono - varchar
    (SELECT MAX(MP_Orden) + 1 FROM MenuPrincipal WHERE MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'MÓDULOS')), -- MP_Orden - int
    'collapsable', -- MP_Tipo - varchar
    NULL, -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0, -- MP_Repetible - bit
    0 -- MP_Personalizado - bit
)
GO

INSERT INTO MenuPrincipal
VALUES
(
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'MÓDULOS') AND MP_Titulo = 'Facturación'), -- MP_NodoPadreId - int
    'Factura Nota de Venta', -- MP_Titulo - varchar
    'Sales note invoice', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'note', -- MP_Icono - varchar
    1, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/facturacion/factura-nota-venta', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0, -- MP_Repetible - bit
    0 -- MP_Personalizado - bit
),
(
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'MÓDULOS') AND MP_Titulo = 'Facturación'), -- MP_NodoPadreId - int
    'Factura Global', -- MP_Titulo - varchar
    'Global invoice', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'event_note', -- MP_Icono - varchar
    2, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/facturacion/factura-global', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0, -- MP_Repetible - bit
    0 -- MP_Personalizado - bit
)
GO

UPDATE MenuPrincipal SET
		MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'MÓDULOS') AND MP_Titulo = 'Facturación'),
		MP_Titulo = 'Factura Remisión',
		MP_TituloEN = 'Remission invoice',
		MP_Icono = 'featured_play_list',
		MP_Orden = 3,
		MP_URL = '/app/facturacion/factura-remision'
WHERE MP_NodoId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId IS NULL AND MP_Titulo = 'MÓDULOS') AND MP_Titulo = 'Ventas') AND MP_Titulo = 'Facturación')
GO