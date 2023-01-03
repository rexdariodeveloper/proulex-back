SET IDENTITY_INSERT MenuPrincipal ON
INSERT INTO MenuPrincipal
(
    MP_NodoId, -- column value is auto-generated
    MP_NodoPadreId,
    MP_Titulo,
    MP_TituloEN,
    MP_Activo,
    MP_Icono,
    MP_Orden,
    MP_Tipo,
    MP_URL,
    MP_CMM_SistemaAccesoId,
    MP_FechaCreacion
)
VALUES
(
    18, -- MP_NodoId - int
    11, -- MP_NodoPadreId - int
    'Kardex de Artículos', -- MP_Titulo - varchar
    'Kardex of products', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'assignment', -- MP_Icono - varchar
    5, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/inventario/kardex-articulos', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE() -- MP_FechaCreacion - datetime2
)
SET IDENTITY_INSERT MenuPrincipal OFF
GO