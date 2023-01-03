INSERT INTO [dbo].[Autonumericos]
           ([AUT_Nombre]
           ,[AUT_Prefijo]
           ,[AUT_Siguiente]
           ,[AUT_Digitos]
           ,[AUT_Activo])
     VALUES (
		   'Ordenes Compras Recibos' -- <AUT_Nombre, varchar(50),>
           ,'OCR' -- <AUT_Prefijo, varchar(255),>
           ,1 -- <AUT_Siguiente, int,>
           ,6 -- <AUT_Digitos, int,>
           ,1 -- <AUT_Activo, bit,>
	 )
GO


ALTER TABLE [dbo].[OrdenesCompraRecibos] ADD [OCR_CodigoRecibo] varchar(150) NULL
GO

UPDATE OrdenesCompraRecibos SET OCR_CodigoRecibo = 'OCR_' + CAST(OCR_OrdenCompraReciboId AS varchar)
GO

ALTER TABLE [dbo].[OrdenesCompraRecibos] ALTER COLUMN [OCR_CodigoRecibo] varchar(150) NOT NULL
GO