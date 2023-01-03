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
    19, -- MP_NodoId - int
    11, -- MP_NodoPadreId - int
    'Reporte de Existencias', -- MP_Titulo - varchar
    'Stock report', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'toc', -- MP_Icono - varchar
    5, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/inventario/existencias', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE() -- MP_FechaCreacion - datetime2
)
SET IDENTITY_INSERT MenuPrincipal OFF
GO