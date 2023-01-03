ALTER TABLE [dbo].[CXPSolicitudesPagos] ADD CXPS_SUC_SucursalId int
GO

ALTER TABLE [dbo].[CXPSolicitudesPagos]  WITH CHECK ADD  CONSTRAINT [FK_CXPS_SUC_SucursalId] FOREIGN KEY([CXPS_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO