SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER VIEW [dbo].[VW_LISTADO_AJUSTE_INVENTARIO]
AS
     SELECT IM_Referencia AS "Referencia",
		   IM_FechaCreacion AS "Fecha",
		   ALM_CodigoAlmacen+' - '+ALM_Nombre AS "Almacén",
		   LOC_CodigoLocalidad+' - '+LOC_Nombre AS "Localidad",
		   ART_CodigoArticulo+' - '+ART_NombreArticulo AS "Artículo",
		   UM_Nombre AS "UM",
		   IM_Razon AS "Motivo de ajuste",
		   IM_Cantidad AS "Cantidad",
		   '$' + CAST(IM_CostoUnitario AS NVARCHAR(20)) AS "Costo"       
	FROM InventariosMovimientos
		 INNER JOIN Articulos ON IM_ART_ArticuloId = ART_ArticuloId
		 INNER JOIN UnidadesMedidas ON IM_UM_UnidadMedidaId = UM_UnidadMedidaId
		 INNER JOIN Localidades ON IM_LOC_LocalidadId = LOC_LocalidadId
		 INNER JOIN Almacenes ON LOC_ALM_AlmacenId = ALM_AlmacenId
	WHERE IM_CMM_TipoMovimientoId = 2000011
GO