ALTER TABLE ProveedoresFormasPagos ALTER COLUMN PROFP_MON_MonedaId smallint NULL
GO

ALTER TABLE ProveedoresFormasPagos ADD PROFP_NombreTitularTarjeta varchar(250) NULL
GO