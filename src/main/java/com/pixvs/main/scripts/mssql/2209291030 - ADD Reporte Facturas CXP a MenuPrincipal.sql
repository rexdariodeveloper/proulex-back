INSERT INTO MenuPrincipal
(
    --MP_NodoId - this column value is auto-generated
    MP_NodoPadreId,
    MP_Titulo,
    MP_TituloEN,
    MP_Activo,
    MP_Icono,
    MP_Orden,
    MP_Tipo,
    MP_URL,
    MP_CMM_SistemaAccesoId,
    MP_FechaCreacion,
    MP_Repetible,
    MP_Personalizado
)
VALUES
(
    -- MP_NodoId - INT 
    1034, -- MP_NodoPadreId - int
    'Reporte Facturas CXP', -- MP_Titulo - varchar
    'CXP Invoices Report', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'toc', -- MP_Icono - varchar
    6, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/compras/reportes/cxp-facturas', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0, -- MP_Repetible - bit
    NULL -- MP_Personalizado - bit
)
GO

SET IDENTITY_INSERT MenuPrincipalPermisos ON
GO
INSERT INTO MenuPrincipalPermisos
(
    MPP_MenuPrincipalPermisoId, -- this column value is auto-generated
    MPP_MP_NodoId,
    MPP_Nombre,
    MPP_Activo
)
VALUES
(
    68, -- MPP_MenuPrincipalPermisoId - int
    ( SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/compras/reportes/cxp-facturas' ), -- MPP_MP_NodoId - int
    'Descarga masiva de facturas', -- MPP_Nombre - nvarchar
    1 -- MPP_Activo - bit
),
(
    69, -- MPP_MenuPrincipalPermisoId - int
    ( SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = '/app/compras/reportes/cxp-facturas' ), -- MPP_MP_NodoId - int
    'Exportar a excel', -- MPP_Nombre - nvarchar
    1 -- MPP_Activo - bit
)
GO
SET IDENTITY_INSERT MenuPrincipalPermisos OFF
GO