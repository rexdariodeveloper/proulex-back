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
    14, -- MP_NodoId - int
    11, -- MP_NodoPadreId - int
    'Transferencias', -- MP_Titulo - varchar
    'Transfers', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'redo', -- MP_Icono - varchar
    2, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/inventario/transferencias', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE() -- MP_FechaCreacion - datetime2
)
SET IDENTITY_INSERT MenuPrincipal OFF
GO

DECLARE @ident INT = ( SELECT MAX(MP_NodoId ) FROM MenuPrincipal )
DBCC CHECKIDENT( MenuPrincipal, RESEED, @ident )
GO

INSERT INTO Autonumericos
(
    --AUT_AutonumericoId - column value is auto-generated
    AUT_Nombre,
    AUT_Prefijo,
    AUT_Siguiente,
    AUT_Digitos,
    AUT_Activo
)
VALUES
(
    -- AUT_AutonumericoId - int
    'Transferencias', -- AUT_Nombre - varchar
    'TRA', -- AUT_Prefijo - varchar
    1, -- AUT_Siguiente - int
    6, -- AUT_Digitos - int
    1 -- AUT_Activo - bit
)
GO