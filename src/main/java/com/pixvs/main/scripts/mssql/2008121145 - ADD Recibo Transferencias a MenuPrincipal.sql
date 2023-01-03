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
    16, -- MP_NodoId - int
    11, -- MP_NodoPadreId - int
    'Recibir transferencia', -- MP_Titulo - varchar
    'Receive transfer', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'compare_arrows', -- MP_Icono - varchar
    3, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/inventario/recibir-transferencias', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE() -- MP_FechaCreacion - datetime2
)
SET IDENTITY_INSERT MenuPrincipal OFF
GO

DECLARE @ident INT = ( SELECT MAX(MP_NodoId ) FROM MenuPrincipal )
DBCC CHECKIDENT( MenuPrincipal, RESEED, @ident )
GO