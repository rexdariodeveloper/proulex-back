/**
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 * Object:  Table [dbo].[Articulos]
 */


CREATE TABLE [dbo].[Articulos](
	[ART_ArticuloId] [int] IDENTITY(1,1) NOT NULL ,
	[ART_CodigoArticulo] [varchar]  (30) NOT NULL ,
	[ART_NombreArticulo] [varchar]  (100) NOT NULL ,
	[ART_CodigoBarras] [varchar]  (50) NULL ,
	[ART_CodigoAlterno] [varchar]  (30) NULL ,
	[ART_NombreAlterno] [varchar]  (100) NULL ,
	[ART_Descripcion] [varchar]  (1000) NULL ,
	[ART_DescripcionCorta] [varchar]  (30) NOT NULL ,
	[ART_ClaveProductoSAT] [varchar]  (8) NULL ,
	[ART_IVA] [decimal] (3,2)  NULL ,
	[ART_IVAExento] [bit]  NULL ,
	[ART_IEPS] [decimal] (3,2)  NULL ,
	[ART_IEPSCuotaFija] [decimal] (28,2) NULL ,
	[ART_MultiploPedido] [decimal] (28,2) NULL ,
	[ART_PermitirCambioAlmacen] [bit]  NOT NULL ,
	[ART_MaximoAlmacen] [decimal] (28,2) NULL ,
	[ART_MinimoAlmacen] [decimal] (28,2) NULL ,
	[ART_PlaneacionTemporadas] [bit]  NOT NULL ,
	[ART_ARC_ImagenId] [int]  NULL ,
	[ART_AFAM_FamiliaId] [smallint]  NOT NULL ,
	[ART_ACAT_CategoriaId] [int]  NULL ,
	[ART_ASC_SubcategoriaId] [int]  NULL ,
	[ART_CMM_Clasificacion1Id] [int]  NULL ,
	[ART_CMM_Clasificacion2Id] [int]  NULL ,
	[ART_CMM_Clasificacion3Id] [int]  NULL ,
	[ART_CMM_Clasificacion4Id] [int]  NULL ,
	[ART_CMM_Clasificacion5Id] [int]  NULL ,
	[ART_CMM_Clasificacion6Id] [int]  NULL ,
	[ART_ARTT_TipoArticuloId] [int]  NOT NULL ,
	[ART_ARTST_ArticuloSubtipoId] [int]  NULL ,
	[ART_UM_UnidadMedidaInventarioId] [smallint]  NOT NULL ,
	[ART_UM_UnidadMedidaConversionVentasId] [smallint]  NULL ,
	[ART_FactorConversionVentas] [decimal] (28,6) NULL ,
	[ART_UM_UnidadMedidaConversionComprasId] [smallint]  NULL ,
	[ART_FactorConversionCompras] [decimal] (28,6) NULL ,
	[ART_CMM_TipoCostoId] [int]  NOT NULL ,
	[ART_CostoUltimo] [decimal] (28,2) NOT NULL ,
	[ART_CostoPromedio] [decimal] (28,2) NOT NULL ,
	[ART_CostoEstandar] [decimal] (28,2) NOT NULL ,
	[ART_Activo] [bit]  NOT NULL ,
	[ART_FechaCreacion] [datetime2](7) NOT NULL,
	[ART_FechaModificacion] [datetime2](7) NULL,
	[ART_USU_CreadoPorId] [int] NULL,
	[ART_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[ART_ArticuloId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

-- FKs

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_USU_ModificadoPorId] FOREIGN KEY([ART_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_USU_CreadoPorId] FOREIGN KEY([ART_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_ARC_ImagenId] FOREIGN KEY([ART_ARC_ImagenId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_ARC_ImagenId]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_AFAM_FamiliaId] FOREIGN KEY([ART_AFAM_FamiliaId])
REFERENCES [dbo].[ArticulosFamilias] ([AFAM_FamiliaId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_AFAM_FamiliaId]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_ACAT_CategoriaId] FOREIGN KEY([ART_ACAT_CategoriaId])
REFERENCES [dbo].[ArticulosCategorias] ([ACAT_CategoriaId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_ACAT_CategoriaId]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_ASC_SubcategoriaId] FOREIGN KEY([ART_ASC_SubcategoriaId])
REFERENCES [dbo].[ArticulosSubcategorias] ([ASC_SubcategoriaId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_ASC_SubcategoriaId]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_CMM_Clasificacion1Id] FOREIGN KEY([ART_CMM_Clasificacion1Id])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_CMM_Clasificacion1Id]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_CMM_Clasificacion2Id] FOREIGN KEY([ART_CMM_Clasificacion2Id])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_CMM_Clasificacion2Id]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_CMM_Clasificacion3Id] FOREIGN KEY([ART_CMM_Clasificacion3Id])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_CMM_Clasificacion3Id]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_CMM_Clasificacion4Id] FOREIGN KEY([ART_CMM_Clasificacion4Id])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_CMM_Clasificacion4Id]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_CMM_Clasificacion5Id] FOREIGN KEY([ART_CMM_Clasificacion5Id])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_CMM_Clasificacion5Id]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_CMM_Clasificacion6Id] FOREIGN KEY([ART_CMM_Clasificacion6Id])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_CMM_Clasificacion6Id]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_ARTT_TipoArticuloId] FOREIGN KEY([ART_ARTT_TipoArticuloId])
REFERENCES [dbo].[ArticulosTipos] ([ARTT_ArticuloTipoId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_ARTT_TipoArticuloId]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_ARTST_ArticuloSubtipoId] FOREIGN KEY([ART_ARTST_ArticuloSubtipoId])
REFERENCES [dbo].[ArticulosSubtipos] ([ARTST_ArticuloSubtipoId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_ARTST_ArticuloSubtipoId]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_UM_UnidadMedidaInventarioId] FOREIGN KEY([ART_UM_UnidadMedidaInventarioId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_UM_UnidadMedidaInventarioId]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_UM_UnidadMedidaConversionVentasId] FOREIGN KEY([ART_UM_UnidadMedidaConversionVentasId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_UM_UnidadMedidaConversionVentasId]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_UM_UnidadMedidaConversionComprasId] FOREIGN KEY([ART_UM_UnidadMedidaConversionComprasId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_UM_UnidadMedidaConversionComprasId]
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_CMM_TipoCostoId] FOREIGN KEY([ART_CMM_TipoCostoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_CMM_TipoCostoId]
GO

-- DEFAULTs

ALTER TABLE [dbo].[Articulos] WITH CHECK ADD CONSTRAINT [DF_Articulos_ART_IVAExento]  DEFAULT (0) FOR [ART_IVAExento]
GO

ALTER TABLE [dbo].[Articulos] WITH CHECK ADD CONSTRAINT [DF_Articulos_ART_Activo]  DEFAULT (1) FOR [ART_Activo]
GO

-- CHECKs

ALTER TABLE [dbo].[Articulos] WITH CHECK ADD CONSTRAINT [CHK_ART_IVA] CHECK ([ART_IVA] >= 0 AND [ART_IVA] <= 100)
GO

ALTER TABLE [dbo].[Articulos] WITH CHECK ADD CONSTRAINT [CHK_ART_IEPS] CHECK ([ART_IEPS] >= 0 AND [ART_IEPS] <= 100)
GO

ALTER TABLE [dbo].[Articulos] WITH CHECK ADD CONSTRAINT [CHK_ART_CostoUltimo] CHECK ([ART_CostoUltimo] >= 0)
GO

ALTER TABLE [dbo].[Articulos] WITH CHECK ADD CONSTRAINT [CHK_ART_CostoPromedio] CHECK ([ART_CostoPromedio] >= 0)
GO

ALTER TABLE [dbo].[Articulos] WITH CHECK ADD CONSTRAINT [CHK_ART_CostoEstandar] CHECK ([ART_CostoEstandar] >= 0)
GO

-- Descripciones

IF NOT EXISTS (
	SELECT NULL
	FROM SYS.EXTENDED_PROPERTIES
	WHERE
		[major_id] = OBJECT_ID('Articulos')
		AND [name] = N'MS_Description'
		AND [minor_id] = (SELECT [column_id] FROM SYS.COLUMNS WHERE [name] = 'ART_IVAExento' AND [object_id] = OBJECT_ID('Articulos'))
)
	EXECUTE sp_addextendedproperty
		N'MS_Description', N'Al ser true, el IVA debe ser 0',
		N'SCHEMA', N'dbo',
		N'TABLE', N'Articulos',
		N'COLUMN', N'ART_IVAExento'
ELSE
	EXECUTE sp_updateextendedproperty
		N'MS_Description', N'Al ser true, el IVA debe ser 0',
		N'SCHEMA', N'dbo',
		N'TABLE', N'Articulos',
		N'COLUMN', N'ART_IVAExento'
GO

IF NOT EXISTS (
	SELECT NULL
	FROM SYS.EXTENDED_PROPERTIES
	WHERE
		[major_id] = OBJECT_ID('Articulos')
		AND [name] = N'MS_Description'
		AND [minor_id] = (SELECT [column_id] FROM SYS.COLUMNS WHERE [name] = 'ART_IEPS' AND [object_id] = OBJECT_ID('Articulos'))
)
	EXECUTE sp_addextendedproperty
		N'MS_Description', N'Al tener porcentaje IEPS, ART_IEPSCuotaFija debe ser null o 0',
		N'SCHEMA', N'dbo',
		N'TABLE', N'Articulos',
		N'COLUMN', N'ART_IEPS'
ELSE
	EXECUTE sp_updateextendedproperty
		N'MS_Description', N'Al tener porcentaje IEPS, ART_IEPSCuotaFija debe ser null o 0',
		N'SCHEMA', N'dbo',
		N'TABLE', N'Articulos',
		N'COLUMN', N'ART_IEPS'
GO

IF NOT EXISTS (
	SELECT NULL
	FROM SYS.EXTENDED_PROPERTIES
	WHERE
		[major_id] = OBJECT_ID('Articulos')
		AND [name] = N'MS_Description'
		AND [minor_id] = (SELECT [column_id] FROM SYS.COLUMNS WHERE [name] = 'ART_IEPSCuotaFija' AND [object_id] = OBJECT_ID('Articulos'))
)
	EXECUTE sp_addextendedproperty
		N'MS_Description', N'Al tener IEPS fijo, ART_IEPS debe ser null o 0',
		N'SCHEMA', N'dbo',
		N'TABLE', N'Articulos',
		N'COLUMN', N'ART_IEPSCuotaFija'
ELSE
	EXECUTE sp_updateextendedproperty
		N'MS_Description', N'Al tener IEPS fijo, ART_IEPS debe ser null o 0',
		N'SCHEMA', N'dbo',
		N'TABLE', N'Articulos',
		N'COLUMN', N'ART_IEPSCuotaFija'
GO

IF NOT EXISTS (
	SELECT NULL
	FROM SYS.EXTENDED_PROPERTIES
	WHERE
		[major_id] = OBJECT_ID('Articulos')
		AND [name] = N'MS_Description'
		AND [minor_id] = (SELECT [column_id] FROM SYS.COLUMNS WHERE [name] = 'ART_CostoUltimo' AND [object_id] = OBJECT_ID('Articulos'))
)
	EXECUTE sp_addextendedproperty
		N'MS_Description', N'No editable desde ficha de Artículo',
		N'SCHEMA', N'dbo',
		N'TABLE', N'Articulos',
		N'COLUMN', N'ART_CostoUltimo'
ELSE
	EXECUTE sp_updateextendedproperty
		N'MS_Description', N'No editable desde ficha de Artículo',
		N'SCHEMA', N'dbo',
		N'TABLE', N'Articulos',
		N'COLUMN', N'ART_CostoUltimo'
GO

IF NOT EXISTS (
	SELECT NULL
	FROM SYS.EXTENDED_PROPERTIES
	WHERE
		[major_id] = OBJECT_ID('Articulos')
		AND [name] = N'MS_Description'
		AND [minor_id] = (SELECT [column_id] FROM SYS.COLUMNS WHERE [name] = 'ART_CostoPromedio' AND [object_id] = OBJECT_ID('Articulos'))
)
	EXECUTE sp_addextendedproperty
		N'MS_Description', N'No editable desde ficha de Artículo',
		N'SCHEMA', N'dbo',
		N'TABLE', N'Articulos',
		N'COLUMN', N'ART_CostoPromedio'
ELSE
	EXECUTE sp_updateextendedproperty
		N'MS_Description', N'No editable desde ficha de Artículo',
		N'SCHEMA', N'dbo',
		N'TABLE', N'Articulos',
		N'COLUMN', N'ART_CostoPromedio'
GO

-- Tablas pivote

CREATE TABLE [dbo].[ArticulosSucursales](
	[ASUC_ART_ArticuloId] [int] NOT NULL,
	[ASUC_SUC_SucursalId] [int] NOT NULL
PRIMARY KEY CLUSTERED 
(
	[ASUC_ART_ArticuloId] ASC,
	[ASUC_SUC_SucursalId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ArticulosSucursales]  WITH CHECK ADD  CONSTRAINT [FK_ASUC_ART_ArticuloId] FOREIGN KEY([ASUC_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[ArticulosSucursales] CHECK CONSTRAINT [FK_ASUC_ART_ArticuloId]
GO

ALTER TABLE [dbo].[ArticulosSucursales]  WITH CHECK ADD  CONSTRAINT [FK_ASUC_SUC_SucursalId] FOREIGN KEY([ASUC_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[ArticulosSucursales] CHECK CONSTRAINT [FK_ASUC_SUC_SucursalId]
GO

CREATE TABLE [dbo].[ArticulosSucursalesPV](
	[ASUCPV_ART_ArticuloId] [int] NOT NULL,
	[ASUCPV_SUC_SucursalId] [int] NOT NULL
PRIMARY KEY CLUSTERED 
(
	[ASUCPV_ART_ArticuloId] ASC,
	[ASUCPV_SUC_SucursalId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ArticulosSucursalesPV]  WITH CHECK ADD  CONSTRAINT [FK_ASUCPV_ART_ArticuloId] FOREIGN KEY([ASUCPV_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[ArticulosSucursalesPV] CHECK CONSTRAINT [FK_ASUCPV_ART_ArticuloId]
GO

ALTER TABLE [dbo].[ArticulosSucursalesPV]  WITH CHECK ADD  CONSTRAINT [FK_ASUCPV_SUC_SucursalId] FOREIGN KEY([ASUCPV_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[ArticulosSucursalesPV] CHECK CONSTRAINT [FK_ASUCPV_SUC_SucursalId]
GO


CREATE   VIEW [dbo].[VW_LISTADO_ARTICULOS] AS

SELECT ART_CodigoArticulo AS "Código Artículo", ART_NombreArticulo AS "Nombre Artículo", ART_CodigoAlterno AS "Código Alterno", ART_NombreAlterno AS "Nombre Alterno", ART_DescripcionCorta AS "Descripción Corta", ART_Activo AS "Activo", ART_FechaCreacion AS "Fecha Creación" 
FROM Articulos 

GO

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'free_breakfast', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Catálogos'), 2, 1000021, N'Articulos', N'Products', N'item', N'/app/catalogos/articulos')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Articulos' and MP_Icono = 'free_breakfast' and MP_Orden = 2)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO

