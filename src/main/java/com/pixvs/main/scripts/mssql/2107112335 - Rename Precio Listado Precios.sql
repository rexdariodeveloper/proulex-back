EXEC sp_RENAME 'ListadosPreciosDetalles.LIPRED_PrecioVenta' , 'LIPRED_Precio', 'COLUMN';
ALTER TABLE ListadosPreciosDetalles ALTER COLUMN LIPRED_Precio [decimal](10,6);