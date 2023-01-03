/*
  Actualizamos los datos del proveedor
*/

UPDATE Proveedores
SET PRO_Codigo = nuevoCodigo
    FROM(
    SELECT
    RIGHT('0000000'+CAST(numeroCodigo AS VARCHAR(3)),6) AS nuevoCodigo, PRO_ProveedorId AS id, PRO_Nombre, PRO_Codigo
    FROM(
    SELECT ROW_NUMBER() OVER(ORDER BY PRO_ProveedorId) AS numeroCodigo, PRO_ProveedorId, PRO_Nombre, PRO_Codigo FROM Proveedores
  )AS datosActualizar
  ) AS actualizar
WHERE PRO_ProveedorId = id
GO


INSERT INTO Autonumericos(AUT_Nombre, AUT_Prefijo, AUT_Siguiente, AUT_Digitos, AUT_Activo)
SELECT 'Proveedores' AS AUT_Nombre, 'PRO' AS AUT_Prefijo, (COUNT(*)+1) AS AUT_Siguiente, 6 AS AUT_Digitos, 1 AS AUT_Activo
FROM(
      SELECT ROW_NUMBER() OVER(ORDER BY PRO_ProveedorId) AS RowNum,  Proveedores.* FROM Proveedores
)AS c
GO

