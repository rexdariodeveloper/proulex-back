INSERT INTO MenuPrincipal
VALUES
(
    1, -- MP_NodoPadreId - int
    'Cuentas por cobrar', -- MP_Titulo - varchar
    'Accounts receivable', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'payment', -- MP_Icono - varchar
    (SELECT MAX(MP_Orden) + 1 FROM MenuPrincipal WHERE MP_NodoPadreId = 1), -- MP_Orden - int
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
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1 AND MP_Titulo = 'Cuentas por cobrar'), -- MP_NodoPadreId - int
    'Pago de clientes', -- MP_Titulo - varchar
    'Customer payment', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'payment', -- MP_Icono - varchar
    1, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/cxc/pago-clientes', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0, -- MP_Repetible - bit
    0 -- MP_Personalizado - bit
),
(
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1 AND MP_Titulo = 'Cuentas por cobrar'), -- MP_NodoPadreId - int
    'Reportes', -- MP_Titulo - varchar
    'Reports', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'folder_open', -- MP_Icono - varchar
    2, -- MP_Orden - int
    'collapsable', -- MP_Tipo - varchar
    NULL, -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0, -- MP_Repetible - bit
    0 -- MP_Personalizado - bit
)
GO