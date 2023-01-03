/**
 * Created by Angel Daniel Hernández Silva on 13/09/2021.
 * Object: ALTER TABLE [dbo].[OrdenesVenta]
 */

ALTER TABLE [dbo].[OrdenesVenta] ADD [OV_SCC_SucursalCorteCajaId] [int] NULL
GO

ALTER TABLE [dbo].[OrdenesVenta]  WITH CHECK ADD  CONSTRAINT [FK_OV_SCC_SucursalCorteCajaId] FOREIGN KEY([OV_SCC_SucursalCorteCajaId])
REFERENCES [dbo].[SucursalesCortesCajas] ([SCC_SucursalCorteCajaId])
GO

ALTER TABLE [dbo].[OrdenesVenta] CHECK CONSTRAINT [FK_OV_SCC_SucursalCorteCajaId]
GO


UPDATE SucursalesCortesCajas SET SCC_MontoCerrarCaja = 0
GO