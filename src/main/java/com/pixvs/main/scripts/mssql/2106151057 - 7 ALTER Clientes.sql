ALTER TABLE Clientes
ADD CLI_LIPRE_ListadoPrecioId [INT] null;
--

ALTER TABLE [dbo].[Clientes]  WITH CHECK ADD  CONSTRAINT [FK_CLI_LIPRE_ListadoPrecioId] FOREIGN KEY([CLI_LIPRE_ListadoPrecioId])
REFERENCES [dbo].[ListadosPrecios] ([LIPRE_ListadoPrecioId])
GO

ALTER TABLE [dbo].[Clientes] CHECK CONSTRAINT [FK_CLI_LIPRE_ListadoPrecioId]
GO