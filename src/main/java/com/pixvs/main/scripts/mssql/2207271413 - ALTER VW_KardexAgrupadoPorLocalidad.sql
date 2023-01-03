SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER VIEW [dbo].[VW_KardexAgrupadoPorLocalidad]
AS
     SELECT id,
            fechaMovimiento,
            tm.CMM_ControlId AS idTipoMoviento,
            tm.CMM_Valor AS tipoMovimiento,
            IM_PrecioUnitario AS precioUnitario,
            IM_Razon AS razon,
            IM_ReferenciaMovtoId AS idReferencia,
			CASE WHEN LEN(IM_Referencia) != 36 THEN IM_Referencia ELSE ISNULL(OV_Codigo, '') END AS referencia,
            idArticulo,
            ART_CodigoArticulo AS codArticulo,
            ART_NombreArticulo AS articulo,
            IM_UM_UnidadMedidaId AS idUnidadMedida,
            UM_Nombre AS UM,
            ALM_AlmacenId AS idAlmacen,
            ALM_CodigoAlmacen AS codAlmacen,
            ALM_Nombre AS almacen,
            idLocalidad,
            LOC_CodigoLocalidad AS codLocalidad,
            LOC_Nombre AS localidad,
            existenciaAnterior,
            cantidad,
            existenciaAcumulado,
            tc.CMM_ControlId AS idTipoCosto,
            tc.CMM_Valor AS tipoCosto,
            ART_CostoUltimo AS costoUnitario,
            ART_CostoUltimo * cantidad AS costoMovimiento,
            IM_USU_CreadoPorId AS creadorId,
            COALESCE(dbo.getNombreCompletoUsuario(IM_USU_CreadoPorId), '-') AS creador
     FROM
	 (
		SELECT IM_InventarioMovimientoId AS id,
			   IM_FechaCreacion AS fechaMovimiento,
			   IM_ART_ArticuloId AS idArticulo,
			   IM_LOC_LocalidadId AS idLocalidad,
			   COALESCE(SUM(IM_Cantidad) OVER(PARTITION BY IM_ART_ArticuloId,
														   IM_LOC_LocalidadId ORDER BY IM_ART_ArticuloId,
																					   IM_LOC_LocalidadId,
																					   IM_FechaCreacion,
																					   IM_InventarioMovimientoId ROWS BETWEEN UNBOUNDED PRECEDING AND 1 PRECEDING), 0) AS existenciaAnterior,
			   IM_Cantidad AS cantidad,
			   COALESCE(SUM(IM_Cantidad) OVER(PARTITION BY IM_ART_ArticuloId,
														   IM_LOC_LocalidadId ORDER BY IM_ART_ArticuloId,
																					   IM_LOC_LocalidadId,
																					   IM_FechaCreacion,
																					   IM_InventarioMovimientoId ROWS BETWEEN UNBOUNDED PRECEDING AND 1 PRECEDING), 0) + IM_Cantidad AS existenciaAcumulado
		FROM InventariosMovimientos
	 ) K1
	 LEFT JOIN InventariosMovimientos ON IM_InventarioMovimientoId = id
	 LEFT JOIN Localidades ON IM_LOC_LocalidadId = LOC_LocalidadId
	 INNER JOIN Almacenes ON LOC_ALM_AlmacenId = ALM_AlmacenId
	 LEFT JOIN Articulos ON IM_ART_ArticuloId = ART_ArticuloId
	 LEFT JOIN ControlesMaestrosMultiples AS tm ON IM_CMM_TipoMovimientoId = tm.CMM_ControlId
	 LEFT JOIN ControlesMaestrosMultiples AS tc ON IM_CMM_TipoCostoId = tc.CMM_ControlId
	 LEFT JOIN UnidadesMedidas ON IM_UM_UnidadMedidaId = UM_UnidadMedidaId
	 LEFT JOIN OrdenesVentaDetalles ON IM_ReferenciaMovtoId = OVD_OrdenVentaDetalleId
     LEFT JOIN OrdenesVenta ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
GO