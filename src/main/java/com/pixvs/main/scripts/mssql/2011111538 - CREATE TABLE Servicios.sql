SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Servicios](
	[SRV_ServicioId] [int] IDENTITY(1,1) NOT NULL,
	[SRV_Concepto] [nvarchar](50) NOT NULL,
	[SRV_Descripcion] [nvarchar](250) NULL,
	[SRV_CMM_TipoServicioId] [int] NOT NULL,
	[SRV_ART_ArticuloId] [int] NOT NULL,
	[SRV_RequiereXML] [bit] NOT NULL,
	[SRV_RequierePDF] [bit] NOT NULL,
	[SRV_Activo] [bit] NOT NULL,
	[SRV_FechaCreacion] [datetime2](7) NOT NULL,
	[SRV_USU_CreadoPorId] [int] NOT NULL,
	[SRV_FechaModificacion] [datetime2](7) NULL,
	[SRV_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[SRV_ServicioId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Servicios]  WITH CHECK ADD  CONSTRAINT [FK_SRV_CMM_TipoServicioId] FOREIGN KEY([SRV_CMM_TipoServicioId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Servicios] CHECK CONSTRAINT [FK_SRV_CMM_TipoServicioId]
GO

ALTER TABLE [dbo].[Servicios]  WITH CHECK ADD  CONSTRAINT [FK_SRV_ART_ArticuloId] FOREIGN KEY([SRV_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[Servicios] CHECK CONSTRAINT [FK_SRV_ART_ArticuloId]
GO

ALTER TABLE [dbo].[Servicios]  WITH CHECK ADD  CONSTRAINT [FK_SRV_USU_CreadoPorId] FOREIGN KEY([SRV_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Servicios] CHECK CONSTRAINT [FK_SRV_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[Servicios]  WITH CHECK ADD  CONSTRAINT [FK_SRV_USU_ModificadoPorId] FOREIGN KEY([SRV_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Servicios] CHECK CONSTRAINT [FK_SRV_USU_ModificadoPorId]
GO