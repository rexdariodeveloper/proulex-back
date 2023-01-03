--------------------- Listados de Precios ---------------------------------------------
CREATE TABLE [dbo].[ListadosPrecios](
	[LIPRE_ListadoPrecioId] [int] IDENTITY(1,1) NOT NULL,
	[LIPRE_Codigo] [nvarchar](30) NOT NULL,
	[LIPRE_Nombre] [nvarchar](50) NOT NULL,
	[LIPRE_FechaInicio] [date] NULL,
	[LIPRE_FechaFin] [date] NULL,
	[LIPRE_Indeterminado] [bit] NOT NULL,
	[LIPRE_Activo] [bit] NOT NULL,
	[LIPRE_FechaCreacion] [datetime2](7) NOT NULL,
	[LIPRE_USU_CreadoPorId] [int] NOT NULL,
	[LIPRE_FechaUltimaModificacion] [datetime2](7) NULL,
	[LIPRE_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[LIPRE_ListadoPrecioId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

--

ALTER TABLE [dbo].[ListadosPrecios]  WITH CHECK ADD  CONSTRAINT [FK_LIPRE_USU_CreadoPorId] FOREIGN KEY([LIPRE_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ListadosPrecios] CHECK CONSTRAINT [FK_LIPRE_USU_CreadoPorId]
GO

--

ALTER TABLE [dbo].[ListadosPrecios]  WITH CHECK ADD  CONSTRAINT [FK_LIPRE_USU_ModificadoPorId] FOREIGN KEY([LIPRE_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ListadosPrecios] CHECK CONSTRAINT [FK_LIPRE_USU_ModificadoPorId]
GO

--------------------- Listados de Precios Detalles ---------------------------------------------
CREATE TABLE [dbo].[ListadosPreciosDetalles](
	[LIPRED_ListadoPrecioDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[LIPRED_LIPRE_ListadoPrecioId] [int] NULL,
	[LIPRED_ListadoPrecioPadreId] [int] NULL,
	[LIPRED_PrecioVenta] [decimal](10,2) NOT NULL,
	[LIPRED_ART_ArticuloId] [int] NOT NULL
PRIMARY KEY CLUSTERED 
(
	[LIPRED_ListadoPrecioDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

--

ALTER TABLE [dbo].[ListadosPreciosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_LIPRED_LIPRE_ListadoPrecioId] FOREIGN KEY([LIPRED_LIPRE_ListadoPrecioId])
REFERENCES [dbo].[ListadosPrecios] ([LIPRE_ListadoPrecioId])
GO

ALTER TABLE [dbo].[ListadosPreciosDetalles] CHECK CONSTRAINT [FK_LIPRED_LIPRE_ListadoPrecioId]
GO

--

ALTER TABLE [dbo].[ListadosPreciosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_LIPRED_ART_ArticuloId] FOREIGN KEY([LIPRED_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[ListadosPreciosDetalles] CHECK CONSTRAINT [FK_LIPRED_ART_ArticuloId]
GO