Delete from ListadosPreciosDetalles where LIPRED_ListadoPrecioDetalleId is not null;
UPDATE Clientes set CLI_LIPRE_ListadoPrecioId = null where CLI_LIPRE_ListadoPrecioId is not null;
UPDATE Sucursales set SUC_LIPRE_ListadoPrecioId = null where SUC_LIPRE_ListadoPrecioId is not null;
Delete from ListadosPrecios where LIPRE_Codigo is not null;
Delete from Articulos where ART_ARTT_TipoArticuloId=3 AND ART_ARTST_ArticuloSubtipoId=5 AND ART_ArticuloParaVenta=1;

ALTER TABLE Articulos
ADD ART_PROGI_ProgramaIdiomaId int null;

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_PROGI_ProgramaIdiomaId] FOREIGN KEY([ART_PROGI_ProgramaIdiomaId])
REFERENCES [dbo].[ProgramasIdiomas] ([PROGI_ProgramaIdiomaId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_PROGI_ProgramaIdiomaId]
GO