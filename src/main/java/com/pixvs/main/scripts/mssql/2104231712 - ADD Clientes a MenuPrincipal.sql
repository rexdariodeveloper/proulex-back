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
    MP_Repetible
)
VALUES
(
    -- MP_NodoId - int
    3, -- MP_NodoPadreId - int
    'Clientes', -- MP_Titulo - varchar
    'Customers', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'person_pin', -- MP_Icono - varchar
    10, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/catalogos/clientes', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE(), -- MP_FechaCreacion - datetime2
    0 -- MP_Repetible - bit
)
GO

INSERT INTO Autonumericos
(
    --AUT_AutonumericoId - this column value is auto-generated
    AUT_Nombre,
    AUT_Prefijo,
    AUT_Siguiente,
    AUT_Digitos,
    AUT_Activo
)
VALUES
(
    -- AUT_AutonumericoId - int
    'Clientes', -- AUT_Nombre - varchar
    'CLI', -- AUT_Prefijo - varchar
    1, -- AUT_Siguiente - int
    6, -- AUT_Digitos - int
    1 -- AUT_Activo - bit
)
GO