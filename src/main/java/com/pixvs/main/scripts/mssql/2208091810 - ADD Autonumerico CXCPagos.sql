INSERT INTO Autonumericos
SELECT 'CXCPago '+ SUC_Nombre,
       'CP' + SUC_Prefijo,
       1,
       6,
       1,
	   SUC_SucursalId
FROM Sucursales
WHERE SUC_Activo = 1
GO