ALTER TABLE Sucursales
ADD SUC_LIPRE_ListadoPrecioId [INT] null;
--

ALTER TABLE [dbo].[Sucursales]  WITH CHECK ADD  CONSTRAINT [FK_SUC_LIPRE_ListadoPrecioId] FOREIGN KEY([SUC_LIPRE_ListadoPrecioId])
REFERENCES [dbo].[ListadosPrecios] ([LIPRE_ListadoPrecioId])
GO

ALTER TABLE [dbo].[Sucursales] CHECK CONSTRAINT [FK_SUC_LIPRE_ListadoPrecioId]
GO