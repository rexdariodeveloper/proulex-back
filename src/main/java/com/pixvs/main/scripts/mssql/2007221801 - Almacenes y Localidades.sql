
/****** Object:  Table [dbo].[Almacenes]    Script Date: 16/07/2020 05:21:56 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Almacenes](
	[ALM_AlmacenId] [int] IDENTITY(1,1) NOT NULL,
	[ALM_CodigoAlmacen] [nvarchar](12) NOT NULL,
	[ALM_Nombre] [nvarchar](250) NOT NULL,
	[ALM_SUC_SucursalId] [int] NOT NULL,
	[ALM_USU_ResponsableId] [int] NOT NULL,
	[ALM_MismaDireccionSucursal] [bit] NOT NULL,
	[ALM_Domicilio] [nvarchar](250) NULL,
	[ALM_Colonia] [nvarchar](250) NULL,
	[ALM_PAI_PaisId] [smallint] NULL,
	[ALM_EST_EstadoId] [int] NULL,
	[ALM_Ciudad] [nvarchar](100) NULL,
	[ALM_CP] [nvarchar](10) NULL,
	[ALM_Telefono] [nvarchar](25) NULL,
	[ALM_Extension] [nvarchar](10) NULL,
	[ALM_Predeterminado] [bit] NOT NULL,
	[ALM_EsCEDI] [bit] NULL,
	[ALM_Activo] [bit] NOT NULL,
	[ALM_FechaCreacion] [datetime] NOT NULL,
	[ALM_USU_CreadoPorId] [int] NOT NULL,
	[ALM_FechaModificacion] [datetime] NULL,
	[ALM_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_Almacenes] PRIMARY KEY CLUSTERED 
(
	[ALM_AlmacenId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE FUNCTION existeAlmacenPredeterminado ( @sucursalId int, @almacenValidarId int )
RETURNS bit
AS
BEGIN
	
	IF (@almacenValidarId IS NOT NULL) AND EXISTS (
		SELECT *
		FROM Almacenes
		WHERE
			ALM_SUC_SucursalId = @sucursalId
			AND ALM_AlmacenId != @almacenValidarId
			AND ALM_Activo = CAST(1 AS bit)
			AND ALM_Predeterminado = CAST(1 AS bit)
	) return CAST(1 as bit)

	IF (@almacenValidarId IS NULL) AND EXISTS (
		SELECT *
		FROM Almacenes
		WHERE
			ALM_SUC_SucursalId = @sucursalId
			AND ALM_Activo = CAST(1 AS bit)
			AND ALM_Predeterminado = CAST(1 AS bit)
	) return CAST(1 as bit)
    
	return CAST(0 AS bit)
END
GO

-- FKs

ALTER TABLE [dbo].[Almacenes]  WITH CHECK ADD  CONSTRAINT [FK_Almacenes_Usuarios] FOREIGN KEY([ALM_USU_ResponsableId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[Almacenes] CHECK CONSTRAINT [FK_Almacenes_Usuarios]
GO
ALTER TABLE [dbo].[Almacenes]  WITH CHECK ADD  CONSTRAINT [FK_Almacenes_Estados] FOREIGN KEY([ALM_EST_EstadoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO
ALTER TABLE [dbo].[Almacenes] CHECK CONSTRAINT [FK_Almacenes_Estados]
GO
ALTER TABLE [dbo].[Almacenes]  WITH CHECK ADD  CONSTRAINT [FK_Almacenes_Paises] FOREIGN KEY([ALM_PAI_PaisId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO
ALTER TABLE [dbo].[Almacenes] CHECK CONSTRAINT [FK_Almacenes_Paises]
GO
ALTER TABLE [dbo].[Almacenes]  WITH CHECK ADD  CONSTRAINT [FK_Almacenes_Sucursales] FOREIGN KEY([ALM_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO
ALTER TABLE [dbo].[Almacenes] CHECK CONSTRAINT [FK_Almacenes_Sucursales]
GO
ALTER TABLE [dbo].[Almacenes]  WITH CHECK ADD  CONSTRAINT [FK_Almacenes_CreadoPorId] FOREIGN KEY([ALM_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[Almacenes] CHECK CONSTRAINT [FK_Almacenes_CreadoPorId]
GO
ALTER TABLE [dbo].[Almacenes]  WITH CHECK ADD  CONSTRAINT [FK_Almacenes_ModificadoPorId] FOREIGN KEY([ALM_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[Almacenes] CHECK CONSTRAINT [FK_Almacenes_ModificadoPorId]
GO

-- DFs

ALTER TABLE [dbo].[Almacenes] ADD  CONSTRAINT [DF_ALM_AlmacenPredeterminado]  DEFAULT ((0)) FOR [ALM_Predeterminado]
GO
ALTER TABLE [dbo].[Almacenes] ADD  CONSTRAINT [DF_ALM_Activo]  DEFAULT ((1)) FOR [ALM_Activo]
GO

-- CHECKs

ALTER TABLE [dbo].[Almacenes] WITH CHECK ADD CONSTRAINT [CHK_ALM_Predeterminado] CHECK (ALM_Predeterminado = CAST(0 AS bit) OR [dbo].[existeAlmacenPredeterminado](ALM_SUC_SucursalId,ALM_AlmacenId) = CAST(0 AS bit))
GO

-- UNIQUE

ALTER TABLE [dbo].[Almacenes] WITH CHECK ADD CONSTRAINT [UNQ_ALM_CodigoAlmacen] UNIQUE ([ALM_CodigoAlmacen])
GO



CREATE   VIEW [dbo].[VW_LISTADO_ALMACENES] AS

SELECT
	ALM_CodigoAlmacen AS "Código Almacén",
	ALM_Nombre AS "Nombre Almacén",
	Responsable.USU_Nombre + ' ' + Responsable.USU_PrimerApellido + COALESCE(' ' + Responsable.USU_SegundoApellido,'') AS "Responsable",
	SUC_Nombre AS "Sucursal",
	ALM_Telefono AS "Teléfono",
	ALM_Activo AS "Activo"
FROM Almacenes 
INNER JOIN Usuarios Responsable ON USU_UsuarioId = ALM_USU_ResponsableId
INNER JOIN Sucursales ON SUC_SucursalId = ALM_SUC_SucursalId

GO




/****** Object:  Table [dbo].[Localidades]    Script Date: 16/07/2020 05:21:56 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Localidades](
	[LOC_LocalidadId] [int] IDENTITY(1,1) NOT NULL,
	[LOC_CodigoLocalidad] [nvarchar](15) NOT NULL,
	[LOC_Nombre] [nvarchar](200) NOT NULL,
	[LOC_ALM_AlmacenId] [int] NOT NULL,
	[LOC_LocalidadGeneral] [bit] NOT NULL,
	[LOC_Activo] [int] NOT NULL,
	[LOC_FechaCreacion] [datetime] NOT NULL,
	[LOC_USU_CreadoPorId] [int] NOT NULL,
	[LOC_FechaModificacion] [datetime] NULL,
	[LOC_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_Localidades] PRIMARY KEY CLUSTERED 
(
	[LOC_LocalidadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

-- FKs

ALTER TABLE [dbo].[Localidades]  WITH CHECK ADD  CONSTRAINT [FK_Localidades_Almacenes] FOREIGN KEY([LOC_ALM_AlmacenId])
REFERENCES [dbo].[Almacenes] ([ALM_AlmacenId])
GO
ALTER TABLE [dbo].[Localidades] CHECK CONSTRAINT [FK_Localidades_Almacenes]
GO
ALTER TABLE [dbo].[Localidades]  WITH CHECK ADD  CONSTRAINT [FK_Localidades_Usuarios] FOREIGN KEY([LOC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[Localidades] CHECK CONSTRAINT [FK_Localidades_Usuarios]
GO
ALTER TABLE [dbo].[Localidades]  WITH CHECK ADD  CONSTRAINT [FK_Localidades_Usuarios1] FOREIGN KEY([LOC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[Localidades] CHECK CONSTRAINT [FK_Localidades_Usuarios1]
GO

-- DFs

ALTER TABLE [dbo].[Localidades] ADD  CONSTRAINT [DF_LOC_LocalidadGeneral]  DEFAULT ((0)) FOR [LOC_LocalidadGeneral]
GO
ALTER TABLE [dbo].[Localidades] ADD  CONSTRAINT [DF_LOC_Activo]  DEFAULT ((1)) FOR [LOC_Activo]
GO

-- UNIQUE

ALTER TABLE [dbo].[Localidades] WITH CHECK ADD CONSTRAINT [UNQ_LOC_CodigoLocalidad] UNIQUE ([LOC_CodigoLocalidad])
GO







/****** Object:  Table [dbo].[LocalidadesArticulos]    Script Date: 16/07/2020 05:21:56 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LocalidadesArticulos](
	[LOCA_ART_ArticuloId] [int] NOT NULL,
	[LOCA_LOC_LocalidadId] [int] NOT NULL,
 CONSTRAINT [PK_LocalidadesArticulos] PRIMARY KEY CLUSTERED 
(
	[LOCA_ART_ArticuloId] ASC,
	[LOCA_LOC_LocalidadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[LocalidadesArticulos]  WITH CHECK ADD  CONSTRAINT [FK_LocalidadesArticulos_Articulos] FOREIGN KEY([LOCA_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO
ALTER TABLE [dbo].[LocalidadesArticulos] CHECK CONSTRAINT [FK_LocalidadesArticulos_Articulos]
GO
ALTER TABLE [dbo].[LocalidadesArticulos]  WITH CHECK ADD  CONSTRAINT [FK_LocalidadesArticulos_Localidades] FOREIGN KEY([LOCA_LOC_LocalidadId])
REFERENCES [dbo].[Localidades] ([LOC_LocalidadId])
GO
ALTER TABLE [dbo].[LocalidadesArticulos] CHECK CONSTRAINT [FK_LocalidadesArticulos_Localidades]
GO



INSERT [dbo].[MenuPrincipal] (
	[MP_Activo],
	[MP_FechaCreacion],
	[MP_Icono],
	[MP_NodoPadreId],
	[MP_Orden],
	[MP_CMM_SistemaAccesoId],
	[MP_Titulo],
	[MP_TituloEN],
	[MP_Tipo],
	[MP_URL]
) 
VALUES (
	1,
	GETDATE(),
	N'store',
	(select MP_NodoId from MenuPrincipal where MP_Titulo = 'Catálogos'),
	3,
	1000021,
	N'Almacenes',
	N'Warehouses',
	N'item',
	N'/app/catalogos/almacenes'
)
GO

INSERT INTO [dbo].[RolesMenus](
	[ROLMP_FechaModificacion],
	[ROLMP_MP_NodoId],
	[ROLMP_ROL_RolId],
	ROLMP_Crear,
	ROLMP_Modificar,
	ROLMP_Eliminar,
	ROLMP_FechaCreacion
)
VALUES(
	null
	,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Almacenes' and MP_Icono = 'store' and MP_Orden = 3)
	,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
	, 1, 1, 1
	, GETDATE()
)
GO

