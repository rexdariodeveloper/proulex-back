

ALTER TABLE [dbo].[Clientes] ADD [CLI_Consignacion] [bit] NULL
GO

UPDATE [dbo].[Clientes] SET [CLI_Consignacion] = 0
GO

ALTER TABLE [dbo].[Clientes] ALTER COLUMN [CLI_Consignacion] [bit] NOT NULL
GO

ALTER TABLE [dbo].[Almacenes] ADD [ALM_CLI_ClienteId] [int] NULL
GO

ALTER TABLE [dbo].[Almacenes]  WITH CHECK ADD  CONSTRAINT [FK_ALM_CLI_ClienteId] FOREIGN KEY([ALM_CLI_ClienteId])
REFERENCES [dbo].[Clientes] ([CLI_ClienteId])
GO

ALTER TABLE [dbo].[Almacenes] CHECK CONSTRAINT [FK_ALM_CLI_ClienteId]
GO

ALTER TABLE [dbo].[Almacenes] ADD [ALM_MismaDireccionCliente] [bit] NULL
GO

UPDATE [dbo].[Almacenes] SET [ALM_MismaDireccionCliente] = 0
GO

ALTER TABLE [dbo].[Almacenes] ALTER COLUMN [ALM_MismaDireccionCliente] [bit] NOT NULL
GO

ALTER TABLE [dbo].[Almacenes] WITH CHECK ADD CONSTRAINT [CHK_ALM_MismaDireccionSucursalCliente] CHECK (ALM_MismaDireccionSucursal = CAST(0 AS bit) OR ALM_MismaDireccionCliente = CAST(0 AS bit))
GO