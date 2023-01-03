/*****************************/
/***** ArticulosFamilias *****/
/*****************************/

CREATE TABLE [dbo].[ArticulosFamilias](
	[AFAM_FamiliaId] [smallint] IDENTITY(1,1) NOT NULL ,
	[AFAM_Nombre] [varchar] (50) NOT NULL ,
	[AFAM_Descripcion] [varchar] (255) NULL ,
	[AFAM_Activo] [bit] NOT NULL ,
	[AFAM_FechaCreacion] [datetime2](7) NOT NULL,
	[AFAM_FechaModificacion] [datetime2](7) NULL,
	[AFAM_USU_CreadoPorId] [int] NULL,
	[AFAM_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED
(
	[AFAM_FamiliaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ArticulosFamilias]  WITH CHECK ADD  CONSTRAINT [FK_AFAM_USU_ModificadoPorId] FOREIGN KEY([AFAM_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ArticulosFamilias] CHECK CONSTRAINT [FK_AFAM_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[ArticulosFamilias]  WITH CHECK ADD  CONSTRAINT [FK_AFAM_USU_CreadoPorId] FOREIGN KEY([AFAM_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ArticulosFamilias] CHECK CONSTRAINT [FK_AFAM_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ArticulosFamilias] WITH CHECK ADD CONSTRAINT [DF_ArticulosFamilias_AFAM_Activo]  DEFAULT (1) FOR [AFAM_Activo]
GO





/*******************************/
/***** ArticulosCategorias *****/
/*******************************/

CREATE TABLE [dbo].[ArticulosCategorias](
	[ACAT_CategoriaId] [int] IDENTITY(1,1) NOT NULL ,
	[ACAT_AFAM_FamiliaId] [smallint] NOT NULL ,
	[ACAT_Nombre] [varchar] (50) NOT NULL ,
	[ACAT_Descripcion] [varchar] (255) NULL ,
	[ACAT_Activo] [bit] NOT NULL ,
	[ACAT_FechaCreacion] [datetime2](7) NOT NULL,
	[ACAT_FechaModificacion] [datetime2](7) NULL,
	[ACAT_USU_CreadoPorId] [int] NULL,
	[ACAT_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED
(
	[ACAT_CategoriaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ArticulosCategorias]  WITH CHECK ADD  CONSTRAINT [FK_ACAT_AFAM_FamiliaId] FOREIGN KEY([ACAT_AFAM_FamiliaId])
REFERENCES [dbo].[ArticulosFamilias] ([AFAM_FamiliaId])
GO

ALTER TABLE [dbo].[ArticulosCategorias] CHECK CONSTRAINT [FK_ACAT_AFAM_FamiliaId]
GO

ALTER TABLE [dbo].[ArticulosCategorias]  WITH CHECK ADD  CONSTRAINT [FK_ACAT_USU_ModificadoPorId] FOREIGN KEY([ACAT_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ArticulosCategorias] CHECK CONSTRAINT [FK_ACAT_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[ArticulosCategorias]  WITH CHECK ADD  CONSTRAINT [FK_ACAT_USU_CreadoPorId] FOREIGN KEY([ACAT_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ArticulosCategorias] CHECK CONSTRAINT [FK_ACAT_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ArticulosCategorias] WITH CHECK ADD CONSTRAINT [DF_ArticulosCategorias_ACAT_Activo]  DEFAULT (1) FOR [ACAT_Activo]
GO





/**********************************/
/***** ArticulosSubcategorias *****/
/**********************************/

CREATE TABLE [dbo].[ArticulosSubcategorias](
	[ASC_SubcategoriaId] [int] IDENTITY(1,1) NOT NULL ,
	[ASC_ACAT_CategoriaId] [int] NOT NULL ,
	[ASC_Nombre] [varchar] (50) NOT NULL ,
	[ASC_Descripcion] [varchar] (255) NULL ,
	[ASC_Activo] [bit] NOT NULL ,
	[ASC_FechaCreacion] [datetime2](7) NOT NULL,
	[ASC_FechaModificacion] [datetime2](7) NULL,
	[ASC_USU_CreadoPorId] [int] NULL,
	[ASC_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED
(
	[ASC_SubcategoriaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ArticulosSubcategorias]  WITH CHECK ADD  CONSTRAINT [FK_ASC_ACAT_CategoriaId] FOREIGN KEY([ASC_ACAT_CategoriaId])
REFERENCES [dbo].[ArticulosCategorias] ([ACAT_CategoriaId])
GO

ALTER TABLE [dbo].[ArticulosSubcategorias] CHECK CONSTRAINT [FK_ASC_ACAT_CategoriaId]
GO

ALTER TABLE [dbo].[ArticulosSubcategorias]  WITH CHECK ADD  CONSTRAINT [FK_ASC_USU_ModificadoPorId] FOREIGN KEY([ASC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ArticulosSubcategorias] CHECK CONSTRAINT [FK_ASC_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[ArticulosSubcategorias]  WITH CHECK ADD  CONSTRAINT [FK_ASC_USU_CreadoPorId] FOREIGN KEY([ASC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ArticulosSubcategorias] CHECK CONSTRAINT [FK_ASC_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ArticulosSubcategorias] WITH CHECK ADD CONSTRAINT [DF_ArticulosSubcategorias_ASC_Activo]  DEFAULT (1) FOR [ASC_Activo]
GO





/***************************/
/***** UnidadesMedidas *****/
/***************************/

CREATE TABLE [dbo].[UnidadesMedidas](
	[UM_UnidadMedidaId] [smallint] IDENTITY(1,1) NOT NULL ,
	[UM_Nombre] [varchar] (50) NOT NULL ,
	[UM_Clave] [varchar] (5) NULL ,
	[UM_ClaveSAT] [varchar] (5) NULL ,
	[UM_Activo] [bit] NOT NULL ,
	[UM_FechaCreacion] [datetime2](7) NOT NULL,
	[UM_FechaModificacion] [datetime2](7) NULL,
	[UM_USU_CreadoPorId] [int] NULL,
	[UM_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED
(
	[UM_UnidadMedidaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[UnidadesMedidas]  WITH CHECK ADD  CONSTRAINT [FK_UM_USU_ModificadoPorId] FOREIGN KEY([UM_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[UnidadesMedidas] CHECK CONSTRAINT [FK_UM_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[UnidadesMedidas]  WITH CHECK ADD  CONSTRAINT [FK_UM_USU_CreadoPorId] FOREIGN KEY([UM_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[UnidadesMedidas] CHECK CONSTRAINT [FK_UM_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[UnidadesMedidas] WITH CHECK ADD CONSTRAINT [DF_UnidadesMedidas_UM_Activo]  DEFAULT (1) FOR [UM_Activo]
GO





/*******************/
/***** Monedas *****/
/*******************/

CREATE TABLE [dbo].[Monedas](
	[MON_MonedaId] [smallint] IDENTITY(1,1) NOT NULL ,
	[MON_Codigo] [varchar] (10) NOT NULL ,
	[MON_Nombre] [varchar] (50) NOT NULL ,
	[MON_Simbolo] [varchar] (4) NOT NULL ,
	[MON_Predeterminada] [bit] NOT NULL ,
	[MON_Activo] [bit] NOT NULL ,
	[MON_FechaCreacion] [datetime2](7) NOT NULL,
	[MON_FechaModificacion] [datetime2](7) NULL,
	[MON_USU_CreadoPorId] [int] NULL,
	[MON_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED
(
	[MON_MonedaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Monedas]  WITH CHECK ADD  CONSTRAINT [FK_MON_USU_ModificadoPorId] FOREIGN KEY([MON_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Monedas] CHECK CONSTRAINT [FK_MON_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[Monedas]  WITH CHECK ADD  CONSTRAINT [FK_MON_USU_CreadoPorId] FOREIGN KEY([MON_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Monedas] CHECK CONSTRAINT [FK_MON_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[Monedas] WITH CHECK ADD CONSTRAINT [DF_Monedas_MON_Activo]  DEFAULT (1) FOR [MON_Activo]
GO