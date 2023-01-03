ALTER TABLE [dbo].[Sucursales]
ADD [SUC_USU_CoordinadorId] [int] null
GO

ALTER TABLE [dbo].[Sucursales]  WITH CHECK ADD  CONSTRAINT [FK_SUC_USU_CoordinadorId] FOREIGN KEY([SUC_USU_CoordinadorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Sucursales] CHECK CONSTRAINT [FK_SUC_USU_CoordinadorId]
GO