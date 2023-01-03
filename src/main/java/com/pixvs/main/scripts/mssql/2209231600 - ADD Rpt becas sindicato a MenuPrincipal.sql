INSERT INTO MenuPrincipal
VALUES
(
    1057, -- MP_NodoPadreId - int
    'Reportes', -- MP_Titulo - varchar
    'Reports', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'folder_open', -- MP_Icono - varchar
    99, -- MP_Orden - int
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
    (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_Titulo = 'Reportes'), -- MP_NodoPadreId - int
    'Reporte becas sindicato', -- MP_Titulo - varchar
    'Union scholarship report', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'supervisor_account', -- MP_Icono - varchar
    1, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/programacion-academica/reportes/becas-sindicato', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0, -- MP_Repetible - bit
    0 -- MP_Personalizado - bit
)
GO