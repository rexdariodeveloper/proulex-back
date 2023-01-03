UPDATE OrdenesVentaDetalles set OVD_Precio = 350.00 where OVD_OVD_DetallePadreId IS NOT NULL
GO
UPDATE OrdenesVentaDetalles set OVD_Precio = 0.00 where OVD_OVD_DetallePadreId IS NULL
GO