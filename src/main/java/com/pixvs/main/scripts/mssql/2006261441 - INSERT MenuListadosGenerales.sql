
SET IDENTITY_INSERT [dbo].[MenuListadosGenerales] ON
GO

INSERT [dbo].[MenuListadosGenerales] (
	[MLG_ListadoGeneralNodoId],
	[MLG_NodoPadreId],
	[MLG_Titulo],
	[MLG_TituloEN],
	[MLG_Activo],
	[MLG_Icono],
	[MLG_Orden],
	[MLG_CMM_TipoNodoId],
	[MLG_NombreTablaCatalogo],
	[MLG_CMM_ControlCatalogo],
	[MLG_PermiteBorrar],
	[MLG_UrlAPI],
	[MLG_FechaCreacion]
) VALUES (
	/* [MLG_ListadoGeneralNodoId] */ 1,
	/* [MLG_NodoPadreId] */ NULL,
	/* [MLG_Titulo] */ 'INVENTARIOS',
	/* [MLG_TituloEN] */ 'INVENTORY',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ NULL,
	/* [MLG_Orden] */ 1,
	/* [MLG_CMM_TipoNodoId] */ 1000081,
	/* [MLG_NombreTablaCatalogo] */ NULL,
	/* [MLG_CMM_ControlCatalogo] */ NULL,
	/* [MLG_PermiteBorrar] */ 0,
	/* [MLG_UrlAPI] */ NULL,
	/* [MLG_FechaCreacion] */ GETDATE()
),(
	/* [MLG_ListadoGeneralNodoId] */ 2,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Familias',
	/* [MLG_TituloEN] */ 'Families',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 1,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ArticulosFamilias',
	/* [MLG_CMM_ControlCatalogo] */ NULL,
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/articulos-familias',
	/* [MLG_FechaCreacion] */ GETDATE()
),(
	/* [MLG_ListadoGeneralNodoId] */ 3,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Categorías',
	/* [MLG_TituloEN] */ 'Categories',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 2,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ArticulosCategorias',
	/* [MLG_CMM_ControlCatalogo] */ NULL,
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/articulos-categorias',
	/* [MLG_FechaCreacion] */ GETDATE()
),(
	/* [MLG_ListadoGeneralNodoId] */ 4,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Subcategorías',
	/* [MLG_TituloEN] */ 'Subcategories',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 3,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ArticulosSubcategorias',
	/* [MLG_CMM_ControlCatalogo] */ NULL,
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/articulos-subcategorias',
	/* [MLG_FechaCreacion] */ GETDATE()
),(
	/* [MLG_ListadoGeneralNodoId] */ 5,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Unidades de medida',
	/* [MLG_TituloEN] */ 'Measurement units',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 4,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'UnidadesMedidas',
	/* [MLG_CMM_ControlCatalogo] */ NULL,
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/unidades-medidas',
	/* [MLG_FechaCreacion] */ GETDATE()
)
GO



INSERT [dbo].[MenuListadosGenerales] (
	[MLG_ListadoGeneralNodoId],
	[MLG_NodoPadreId],
	[MLG_Titulo],
	[MLG_TituloEN],
	[MLG_Activo],
	[MLG_Icono],
	[MLG_Orden],
	[MLG_CMM_TipoNodoId],
	[MLG_NombreTablaCatalogo],
	[MLG_CMM_ControlCatalogo],
	[MLG_PermiteBorrar],
	[MLG_UrlAPI],
	[MLG_FechaCreacion]
) VALUES (
	/* [MLG_ListadoGeneralNodoId] */ 6,
	/* [MLG_NodoPadreId] */ NULL,
	/* [MLG_Titulo] */ 'FINANZAS',
	/* [MLG_TituloEN] */ 'FINANCES',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ NULL,
	/* [MLG_Orden] */ 2,
	/* [MLG_CMM_TipoNodoId] */ 1000081,
	/* [MLG_NombreTablaCatalogo] */ NULL,
	/* [MLG_CMM_ControlCatalogo] */ NULL,
	/* [MLG_PermiteBorrar] */ 0,
	/* [MLG_UrlAPI] */ NULL,
	/* [MLG_FechaCreacion] */ GETDATE()
),(
	/* [MLG_ListadoGeneralNodoId] */ 7,
	/* [MLG_NodoPadreId] */ 6,
	/* [MLG_Titulo] */ 'Monedas',
	/* [MLG_TituloEN] */ 'Currency',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 1,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'Monedas',
	/* [MLG_CMM_ControlCatalogo] */ NULL,
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/monedas',
	/* [MLG_FechaCreacion] */ GETDATE()
)
GO

SET IDENTITY_INSERT [dbo].[MenuListadosGenerales] OFF
GO