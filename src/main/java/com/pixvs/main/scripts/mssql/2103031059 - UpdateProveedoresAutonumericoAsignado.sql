/*
Update Codigo de proveedores existentes que se generaron de manera incorrecta
Ya que en el anterior script no contenia el prefijo
*/

UPDATE Proveedores SET PRO_Codigo=CONCAT('PRO', codigo)
                       FROM (
                       SELECT PRO_ProveedorId AS id, PRO_Codigo AS codigo FROM Proveedores WHERE PRO_Codigo LIKE '[0-9]%'
  ) AS c
WHERE id = PRO_ProveedorId