
UPDATE ALmacenes SET ALM_Extension = LEFT(COALESCE(ALM_Extension,''),3)
GO

UPDATE Sucursales SET SUC_Extension = LEFT(COALESCE(SUC_Extension,''),3)
GO


ALTER TABLE [dbo].[Almacenes] ALTER COLUMN [ALM_Extension] [varchar](3) NULL
GO

ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_Extension] [varchar] (3) NULL
GO