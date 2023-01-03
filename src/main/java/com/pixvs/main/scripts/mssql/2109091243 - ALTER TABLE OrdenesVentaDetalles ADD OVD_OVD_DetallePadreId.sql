/**
* Created by Angel Daniel Hernández Silva on 09/09/2021.
* Object:  ALTER TABLE [dbo].[OrdenesVentaDetalles] ADD [OVD_OVD_DetallePadreId]
*/

ALTER TABLE [dbo].[OrdenesVentaDetalles] ADD [OVD_OVD_DetallePadreId] int
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles]  WITH CHECK ADD  CONSTRAINT [FK_OVD_OVD_DetallePadreId] FOREIGN KEY([OVD_OVD_DetallePadreId])
REFERENCES [dbo].[OrdenesVentaDetalles] (OVD_OrdenVentaDetalleId)
GO

ALTER TABLE [dbo].[OrdenesVentaDetalles] CHECK CONSTRAINT [FK_OVD_OVD_DetallePadreId]
GO