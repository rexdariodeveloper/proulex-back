CREATE OR ALTER VIEW [dbo].[VW_KardexAgrupadoPorLocalidad] AS
SELECT id , fechaMovimiento, 
	tm.CMM_ControlId as idTipoMoviento, tm.CMM_Valor as tipoMovimiento,	
	IM_PrecioUnitario as precioUnitario, IM_Razon as razon, IM_ReferenciaMovtoId as idReferencia, IM_Referencia as referencia,
	idArticulo, ART_CodigoArticulo as codArticulo, ART_NombreArticulo as articulo, IM_UM_UnidadMedidaId as idUnidadMedida, UM_Nombre as UM, 
	ALM_AlmacenId as idAlmacen, ALM_CodigoAlmacen AS codAlmacen, ALM_Nombre AS almacen,
	idLocalidad, LOC_CodigoLocalidad AS codLocalidad, LOC_Nombre AS localidad,
	existenciaAnterior, cantidad, existenciaAcumulado,

	tc.CMM_ControlId as idTipoCosto, tc.CMM_Valor as tipoCosto,
	IM_CostoUnitario as costoUnitario,
	IM_CostoUnitario * cantidad as costoMovimiento,
	IM_USU_CreadoPorId as creadorId, COALESCE(dbo.getNombreCompletoUsuario( IM_USU_CreadoPorId ), '-') as creador
	from (


		SELECT IM_InventarioMovimientoId as id,  IM_FechaCreacion as fechaMovimiento, IM_ART_ArticuloId as idArticulo, IM_LOC_LocalidadId as idLocalidad,
				COALESCE( SUM(IM_Cantidad) OVER (PARTITION BY  IM_ART_ArticuloId , IM_LOC_LocalidadId
									ORDER BY IM_ART_ArticuloId, IM_LOC_LocalidadId, IM_FechaCreacion, IM_InventarioMovimientoId
									ROWS BETWEEN UNBOUNDED PRECEDING 
											 AND 1 PRECEDING) , 0 ) AS existenciaAnterior,
				IM_Cantidad as cantidad, 
				COALESCE( SUM(IM_Cantidad) OVER (PARTITION BY  IM_ART_ArticuloId , IM_LOC_LocalidadId
									ORDER BY IM_ART_ArticuloId, IM_LOC_LocalidadId, IM_FechaCreacion, IM_InventarioMovimientoId
									ROWS BETWEEN UNBOUNDED PRECEDING 
											 AND 1 PRECEDING) , 0 ) + IM_Cantidad AS existenciaAcumulado
		FROM InventariosMovimientos
		
)K1

LEFT JOIN InventariosMovimientos on IM_InventarioMovimientoId = id
LEFT JOIN Localidades ON IM_LOC_LocalidadId = LOC_LocalidadId
INNER JOIN Almacenes ON LOC_ALM_AlmacenId = ALM_AlmacenId
LEFT JOIN Articulos ON IM_ART_ArticuloId = ART_ArticuloId
LEFT JOIN ControlesMaestrosMultiples tm on IM_CMM_TipoMovimientoId = tm.CMM_ControlId
LEFT JOIN ControlesMaestrosMultiples tc on IM_CMM_TipoCostoId = tc.CMM_ControlId
LEFT JOIN UnidadesMedidas on IM_UM_UnidadMedidaId = UM_UnidadMedidaId

--WHERE idArticulo = 1096
--AND fechaMovimiento BETWEEN '2021-02-12 11:35:01.7710000' and getdate()
--ORDER BY idArticulo, idAlmacen, idLocalidad, fechaMovimiento, IM_InventarioMovimientoId