ALTER TABLE InventariosMovimientos ALTER COLUMN IM_Cantidad DECIMAL(28, 6) NOT NULL
GO

ALTER TABLE InventariosMovimientos ALTER COLUMN IM_CostoUnitario DECIMAL(28, 6) NOT NULL
GO

ALTER TABLE InventariosMovimientos ALTER COLUMN IM_PrecioUnitario DECIMAL(28, 6) NULL
GO

ALTER TABLE LocalidadesArticulosAcumulados ALTER COLUMN LOCAA_Cantidad DECIMAL(28, 6) NULL
GO