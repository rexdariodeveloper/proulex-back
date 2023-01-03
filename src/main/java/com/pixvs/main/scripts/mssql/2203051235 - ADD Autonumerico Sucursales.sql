ALTER TABLE Autonumericos ADD AUT_ReferenciaId INT NULL
GO

INSERT INTO Autonumericos
SELECT 'CXCFactura '+ SUC_Nombre,
       SUC_Prefijo,
       1,
       6,
       1,
	   SUC_SucursalId
FROM Sucursales
WHERE SUC_Activo = 1
GO