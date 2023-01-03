SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_ARTICULOS]
AS
     SELECT
     	ART_CodigoArticulo AS "Código Artículo",
        ART_NombreArticulo AS "Nombre Artículo",
        ART_NombreAlterno AS "Nombre Alterno",
     	ART_CodigoBarras AS "ISBN",
     	ART_Descripcion AS "Descripción Larga",
        ART_DescripcionCorta AS "Descripción Corta",
     	ART_ClaveProductoSAT AS "Clave SAT",
     	ARTT_Descripcion AS "Tipo",
     	ARTST_Descripcion AS "Subtipo",
     	AFAM_Descripcion AS "Familia",
     	serie.CMM_Valor AS "Serie",
     	um_inventario.UM_Nombre AS "Unidad de Medida Inventario",
        um_venta.UM_Nombre AS "Unidad de Medida Venta",
        ART_FactorConversionVentas AS "Factor de Conversion Venta",
        um_compra.UM_Nombre AS "Unidad de Medida Compra",
        ART_FactorConversionCompras AS "Factor de Conversion Compra",
     	ACAT_Nombre AS "Categoria",
     	ASC_Nombre AS "Subcategoria",
     	idioma.CMM_Valor AS "Idioma",
     	programa.CMM_Valor AS "Programa",
     	editorial.CMM_Valor AS "Editorial",
     	ART_MarcoCertificacion AS "Marco Certificación",
     	(CASE WHEN ART_IVA IS NULL THEN NULL ELSE (CONCAT(CAST(ART_IVA * 100 AS INT), '%') ) END) AS "IVA",
		(CASE WHEN ART_IVAExento = 1 THEN 'Sí' ELSE '' END) AS "IVA Exento",
		(CASE WHEN ART_IEPS IS NULL THEN NULL ELSE (CONCAT(CAST(ART_IEPS * 100 AS INT), '%') ) END) AS "IEPS",
     	ART_IEPSCuotaFija AS "IEPS Cuota Fija",
     	(CASE WHEN ART_PlaneacionTemporadas = 1 THEN 'Sí' ELSE NULL END) AS "Planeación por Temporada",
        ART_MaximoAlmacen AS "Maximo por Almacén",
        ART_MinimoAlmacen AS "Minimo por Almacén",
        ART_MultiploPedido AS "Multiplo para pedido",
        (CASE WHEN ART_PermitirCambioAlmacen = 1 THEN 'Sí' ELSE NULL END) AS "Permitir Cambio de Almacén",
        tipocosto.CMM_Valor AS "Tipo Costo",
        ART_CostoPromedio AS "Costo Promedio",
        ART_CostoEstandar AS "Costo Estandar",
        ART_CostoUltimo AS "Costo Último",
        (CASE WHEN ART_Activo = 1 THEN 'Sí' ELSE 'No' END) AS "Activo",
        (CASE WHEN ART_Inventariable = 1 THEN 'Sí' ELSE 'No' END) AS "Inventariable",
        (CASE WHEN ART_ArticuloParaVenta = 1 THEN 'Sí' ELSE 'No' END) AS "Para Venta",
        ART_FechaCreacion AS "Fecha Creación"
     FROM Articulos
     	INNER JOIN ArticulosFamilias ON ART_AFAM_FamiliaId = AFAM_FamiliaId
     	INNER JOIN ArticulosTipos ON ART_ARTT_TipoArticuloId = ARTT_ArticuloTipoId AND ARTT_CMM_TipoId != 2000033
     	LEFT JOIN ArticulosSubtipos ON ART_ARTST_ArticuloSubtipoId = ARTST_ArticuloSubtipoId
     	LEFT JOIN UnidadesMedidas AS um_inventario ON ART_UM_UnidadMedidaInventarioId = um_inventario.UM_UnidadMedidaId
        LEFT JOIN UnidadesMedidas AS um_venta ON ART_UM_UnidadMedidaConversionVentasId = um_venta.UM_UnidadMedidaId
        LEFT JOIN UnidadesMedidas AS um_compra ON ART_UM_UnidadMedidaConversionComprasId = um_compra.UM_UnidadMedidaId
     	LEFT JOIN ArticulosCategorias ON ART_ACAT_CategoriaId = ACAT_CategoriaId
     	LEFT JOIN ArticulosSubcategorias ON ART_ASC_SubcategoriaId = ASC_SubcategoriaId
     	LEFT JOIN ControlesMaestrosMultiples AS programa ON ART_CMM_ProgramaId = programa.CMM_ControlId
     	LEFT JOIN ControlesMaestrosMultiples AS serie  ON ART_CMM_Clasificacion1Id = serie.CMM_ControlId
     	LEFT JOIN ControlesMaestrosMultiples AS idioma ON ART_CMM_IdiomaId = idioma.CMM_ControlId
     	LEFT JOIN ControlesMaestrosMultiples AS editorial ON ART_CMM_EditorialId = editorial.CMM_ControlId
     	LEFT JOIN ControlesMaestrosMultiples AS tipocosto ON ART_CMM_TipoCostoId = tipocosto.CMM_ControlId
GO