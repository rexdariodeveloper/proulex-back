DELETE PADescuentosUsuariosAutorizados
WHERE PADESUA_DescuentoArticuloId IS NOT NULL
GO

ALTER TABLE [dbo].[PADescuentosUsuariosAutorizados]
ADD [PADESUA_SUC_SucursalId] [INT] NOT NULL
GO

ALTER TABLE [dbo].[PADescuentosUsuariosAutorizados]  WITH CHECK ADD  CONSTRAINT [FK_PADESUA_SUC_SucursalId] FOREIGN KEY([PADESUA_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[PADescuentosUsuariosAutorizados] CHECK CONSTRAINT [FK_PADESUA_SUC_SucursalId]
GO


