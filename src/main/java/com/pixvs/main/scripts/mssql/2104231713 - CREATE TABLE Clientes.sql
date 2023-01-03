SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Clientes](
	[CLI_ClienteId] [int] IDENTITY(1,1) NOT NULL,
	[CLI_Codigo] [varchar](15) NOT NULL,
	[CLI_Nombre] [varchar](100) NOT NULL,
	[CLI_RFC] [varchar](20) NOT NULL,
	[CLI_RazonSocial] [varchar](100) NOT NULL,
	[CLI_Domicilio] [varchar](200) NOT NULL,
	[CLI_Colonia] [varchar](100) NOT NULL,
	[CLI_PAI_PaisId] [smallint] NOT NULL,
	[CLI_EST_EstadoId] [int] NOT NULL,
	[CLI_Ciudad] [varchar](100) NOT NULL,
	[CLI_CP] [varchar](5) NOT NULL,
	[CLI_Telefono] [varchar](25) NOT NULL,
	[CLI_Extension] [varchar](30) NULL,
	[CLI_CorreoElectronico] [varchar](50) NOT NULL,
	[CLI_PaginaWeb] [varchar](200) NULL,
	[CLI_FP_FormaPagoId] [smallint] NOT NULL,
	[CLI_Comentarios] [varchar](3000) NULL,
	[CLI_MON_MonedaId] [smallint] NULL,
	[CLI_CuentaCXC] [varchar](50) NULL,
	[CLI_MontoCredito] [numeric](10, 2) NULL,
	[CLI_DiasCobro] [varchar](150) NULL,	
	[CLI_Activo] [bit] NOT NULL,
	[CLI_FechaCreacion] [datetime2](7) NULL,
	[CLI_FechaModificacion] [datetime2](7) NULL,
	[CLI_USU_CreadoPorId] [int] NULL,
	[CLI_USU_ModificadoPorId] [int] NULL
 CONSTRAINT [PK_Cliente] PRIMARY KEY CLUSTERED 
(
	[CLI_ClienteId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UC_CodigoCliente] UNIQUE NONCLUSTERED 
(
	[CLI_Codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Clientes]  WITH CHECK ADD  CONSTRAINT [FK_CLI_USU_CreadoPorId] FOREIGN KEY([CLI_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Clientes] CHECK CONSTRAINT [FK_CLI_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[Clientes]  WITH CHECK ADD  CONSTRAINT [FK_CLI_USU_ModificadoPorId] FOREIGN KEY([CLI_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Clientes] CHECK CONSTRAINT [FK_CLI_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[Clientes]  WITH CHECK ADD  CONSTRAINT [FK_Clientes_Estados] FOREIGN KEY([CLI_EST_EstadoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO

ALTER TABLE [dbo].[Clientes] CHECK CONSTRAINT [FK_Clientes_Estados]
GO

ALTER TABLE [dbo].[Clientes]  WITH CHECK ADD  CONSTRAINT [FK_Clientes_Monedas] FOREIGN KEY([CLI_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[Clientes] CHECK CONSTRAINT [FK_Clientes_Monedas]
GO

ALTER TABLE [dbo].[Clientes]  WITH CHECK ADD  CONSTRAINT [FK_Clientes_Paises] FOREIGN KEY([CLI_PAI_PaisId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO

ALTER TABLE [dbo].[Clientes] CHECK CONSTRAINT [FK_Clientes_Paises]
GO

ALTER TABLE [dbo].[Clientes]  WITH CHECK ADD  CONSTRAINT [FK_Clientes_FormasPago] FOREIGN KEY([CLI_FP_FormaPagoId])
REFERENCES [dbo].[FormasPago] ([FP_FormaPagoId])
GO

ALTER TABLE [dbo].[Clientes] CHECK CONSTRAINT [FK_Clientes_FormasPago]
GO