UPDATE Autonumericos SET AUT_Nombre = 'Devolución de orden de venta', AUT_Prefijo = 'ND' WHERE AUT_Prefijo = 'NC' AND AUT_Nombre LIKE '%orden de venta%'
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
    'Cancelación de orden de venta', -- AUT_Nombre - varchar
    'NC', -- AUT_Prefijo - varchar
    1, -- AUT_Siguiente - int
    6, -- AUT_Digitos - int
    1 -- AUT_Activo - bit
)
GO

UPDATE OrdenesVentaCancelaciones SET OVC_Codigo = REPLACE(OVC_Codigo, 'NC', 'ND')
GO