SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE IF EXISTS [dbo].[FormasPagoPV]
GO

CREATE TABLE [dbo].[FormasPagoPV](
	[FPPV_FormaPagoId] [smallint] IDENTITY(1,1) NOT NULL,
	[FPPV_Codigo] [varchar](10) NOT NULL,
	[FPPV_Nombre] [varchar](255) NOT NULL,
	[FPPV_ARC_ImagenId] [int] NULL,
	[FPPV_Activo] [bit] NOT NULL,
	[FPPV_USU_CreadoPorId] [int] NOT NULL,
	[FPPV_FechaCreacion] [datetime2](7) NOT NULL,
	[FPPV_USU_ModificadoPorId] [int] NULL,
	[FPPV_FechaModificacion] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[FPPV_FormaPagoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[FormasPagoPV]  WITH CHECK ADD  CONSTRAINT [FK_FPPV_ARC_ImagenId] FOREIGN KEY([FPPV_ARC_ImagenId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[FormasPagoPV] CHECK CONSTRAINT [FK_FPPV_ARC_ImagenId]
GO

ALTER TABLE [dbo].[FormasPagoPV]  WITH CHECK ADD  CONSTRAINT [FK_FPPV_USU_CreadoPorId] FOREIGN KEY([FPPV_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[FormasPagoPV] CHECK CONSTRAINT [FK_FPPV_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[FormasPagoPV]  WITH CHECK ADD  CONSTRAINT [FK_FPPV_USU_ModificadoPorId] FOREIGN KEY([FPPV_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[FormasPagoPV] CHECK CONSTRAINT [FK_FPPV_USU_ModificadoPorId]
GO