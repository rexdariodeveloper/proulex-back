DROP TABLE IF EXISTS ClientesCuentasBancarias
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ClientesCuentasBancarias](
	[CLICB_ClienteCuentaBancariaId] [int] IDENTITY(1,1) NOT NULL,	
	[CLICB_CLI_ClienteId] [int] NOT NULL,
	[CLICB_BAN_BancoId] [int] NOT NULL,
	[CLICB_Cuenta] [varchar](40) NOT NULL,
	[CLICB_Activo] [bit] NOT NULL,
 CONSTRAINT [PK_ClienteCuentaBancaria] PRIMARY KEY CLUSTERED 
(
	[CLICB_ClienteCuentaBancariaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ClientesCuentasBancarias]  WITH CHECK ADD  CONSTRAINT [FK_CLICB_CLI_ClienteId] FOREIGN KEY([CLICB_CLI_ClienteId])
REFERENCES [dbo].[Clientes] ([CLI_ClienteId])
GO

ALTER TABLE [dbo].[ClientesCuentasBancarias] CHECK CONSTRAINT [FK_CLICB_CLI_ClienteId]
GO

ALTER TABLE [dbo].[ClientesCuentasBancarias]  WITH CHECK ADD  CONSTRAINT [FK_CLICB_BAN_BancoId] FOREIGN KEY([CLICB_BAN_BancoId])
REFERENCES [dbo].[Bancos] ([BAN_BancoId])
GO

ALTER TABLE [dbo].[ClientesCuentasBancarias] CHECK CONSTRAINT [FK_CLICB_BAN_BancoId]
GO