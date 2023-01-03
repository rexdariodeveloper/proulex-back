INSERT INTO [dbo].[Autonumericos]
           ([AUT_Nombre]
           ,[AUT_Prefijo]
           ,[AUT_Siguiente]
           ,[AUT_Digitos]
           ,[AUT_Activo])
     VALUES
           ('Sucursales Cortes Caja'
           ,'SCC'
           ,1
           ,6
           ,1)
GO


ALTER TABLE [SucursalesCortesCajas] ADD [SCC_Codigo] NVARCHAR(150)
GO

UPDATE [SucursalesCortesCajas] SET [SCC_Codigo] = CONCAT('SCC',RIGHT( '000000' + CAST( [SCC_SucursalCorteCajaId] as VARCHAR(6)), 6))
GO

UPDATE [Autonumericos] SET [AUT_Siguiente] = (SELECT MAX([SCC_SucursalCorteCajaId]) + 1 FROM [SucursalesCortesCajas]) WHERE [AUT_Prefijo] = N'SCC'
GO