SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[BancosCuentas](
	[BAC_CuentaId] [int] IDENTITY(1,1) NOT NULL,
	[BAC_Codigo] [nvarchar](150) NOT NULL,
	[BAC_Descripcion] [nvarchar](150) NOT NULL,
	[BAC_MON_MonedaId] [smallint] NOT NULL,
	[BAC_BAN_BancoId] [int] NOT NULL,
	[BAC_Activo] [bit] NOT NULL,
	[BAC_USU_CreadoPorId] [int] NOT NULL,
	[BAC_USU_ModificadoPorId] [int] NULL,
	[BAC_FechaCreacion] [datetime2](7) NOT NULL,
	[BAC_FechaModificacion] [datetime2](7) NULL,
 CONSTRAINT [PK_BancosCuentas] PRIMARY KEY CLUSTERED 
(
	[BAC_CuentaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[BancosCuentas]  WITH CHECK ADD  CONSTRAINT [FK_BAC_MON_MonedaId] FOREIGN KEY([BAC_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[BancosCuentas] CHECK CONSTRAINT [FK_BAC_MON_MonedaId]
GO

ALTER TABLE [dbo].[BancosCuentas]  WITH CHECK ADD  CONSTRAINT [FK_BAC_BAN_BancoId] FOREIGN KEY([BAC_BAN_BancoId])
REFERENCES [dbo].[Bancos] ([BAN_BancoId])
GO

ALTER TABLE [dbo].[BancosCuentas] CHECK CONSTRAINT [FK_BAC_BAN_BancoId]
GO