-- Orden de venta
INSERT INTO Autonumericos(AUT_Nombre, AUT_Prefijo, AUT_Siguiente, AUT_Digitos, AUT_Activo)
SELECT CONCAT('Orden venta ',SUC_Nombre) AUT_Nombre, CONCAT('OV', SUC_Prefijo) AS AUT_Prefijo, 1 AS AUT_Siguiente, 6 AS AUT_Digitos, 1 AS AUT_Activo FROM sucursales
;
-- Orden de devolución
INSERT INTO Autonumericos(AUT_Nombre, AUT_Prefijo, AUT_Siguiente, AUT_Digitos, AUT_Activo)
SELECT CONCAT('Orden devolución ',SUC_Nombre) AUT_Nombre, CONCAT('DV', SUC_Prefijo) AS AUT_Prefijo, 1 AS AUT_Siguiente, 6 AS AUT_Digitos, 1 AS AUT_Activo FROM sucursales
;
-- Orden de degustación
INSERT INTO Autonumericos(AUT_Nombre, AUT_Prefijo, AUT_Siguiente, AUT_Digitos, AUT_Activo)
SELECT CONCAT('Orden degustación ',SUC_Nombre) AUT_Nombre, CONCAT('DEG', SUC_Prefijo) AS AUT_Prefijo, 1 AS AUT_Siguiente, 6 AS AUT_Digitos, 1 AS AUT_Activo FROM sucursales
;
