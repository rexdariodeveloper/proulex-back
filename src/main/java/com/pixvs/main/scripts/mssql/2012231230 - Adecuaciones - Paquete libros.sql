SET IDENTITY_INSERT [dbo].[ArticulosSubtipos] ON 
GO

INSERT [dbo].[ArticulosSubtipos] (
	[ARTST_ArticuloSubtipoId],
	[ARTST_ARTT_ArticuloTipoId],
	[ARTST_Descripcion]
) VALUES (
	/* [ARTST_ArticuloSubtipoId] */ 3,
	/* [ARTST_ARTT_ArticuloTipoId] */ 4,
	/* [ARTST_Descripcion] */ 'Paquete de libros'
)
GO

SET IDENTITY_INSERT [dbo].[ArticulosSubtipos] OFF
GO



ALTER TABLE [dbo].[Articulos] ADD [ART_Inventariable] bit NULL
GO

UPDATE Articulos SET ART_Inventariable = (CASE WHEN ART_ARTT_TipoArticuloId = 3 THEN 0 ELSE 1 END)
GO

ALTER TABLE [dbo].[Articulos] ALTER COLUMN [ART_Inventariable] bit NOT NULL
GO

ALTER TABLE [dbo].[Articulos] WITH CHECK ADD CONSTRAINT [DF_Articulos_ART_Inventariable]  DEFAULT (1) FOR [ART_Inventariable]
GO









CREATE TABLE [dbo].[ArticulosComponentes](
	[ARTC_ArticuloComponenteId] [int] IDENTITY(1,1) NOT NULL ,
	[ARTC_ART_ArticuloId] [int]  NOT NULL ,
	[ARTC_ART_ComponenteId] [int]  NOT NULL ,
	[ARTC_Cantidad] [decimal]  (28,6) NOT NULL,
	[ARTC_FechaCreacion] [datetime2](7) NOT NULL,
	[ARTC_FechaModificacion] [datetime2](7) NULL,
	[ARTC_USU_CreadoPorId] [int] NULL,
	[ARTC_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[ARTC_ArticuloComponenteId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ArticulosComponentes]  WITH CHECK ADD  CONSTRAINT [FK_ARTC_ART_ArticuloId] FOREIGN KEY([ARTC_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[ArticulosComponentes] CHECK CONSTRAINT [FK_ARTC_ART_ArticuloId]
GO

ALTER TABLE [dbo].[ArticulosComponentes]  WITH CHECK ADD  CONSTRAINT [FK_ARTC_ART_ComponenteId] FOREIGN KEY([ARTC_ART_ComponenteId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[ArticulosComponentes] CHECK CONSTRAINT [FK_ARTC_ART_ComponenteId]
GO

ALTER TABLE [dbo].[ArticulosComponentes]  WITH CHECK ADD  CONSTRAINT [FK_ARTC_USU_CreadoPorId] FOREIGN KEY([ARTC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ArticulosComponentes] CHECK CONSTRAINT [FK_ARTC_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ArticulosComponentes]  WITH CHECK ADD  CONSTRAINT [FK_ARTC_USU_ModificadoPorId] FOREIGN KEY([ARTC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ArticulosComponentes] CHECK CONSTRAINT [FK_ARTC_USU_ModificadoPorId]
GO