ALTER TABLE [dbo].[OrdenesCompra] ADD [OC_USU_AutorizadoPorId] int NULL
GO

ALTER TABLE [dbo].[OrdenesCompra]  WITH CHECK ADD  CONSTRAINT [FK_OC_USU_AutorizadoPorId] FOREIGN KEY([OC_USU_AutorizadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[OrdenesCompra] CHECK CONSTRAINT [FK_OC_USU_AutorizadoPorId]
GO