SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/**
    Autor: Benjamin
    Descripcion: Agregamos la funcionalidad de Existencias en transito
*/
CREATE OR ALTER   FUNCTION [dbo].[fn_reporteExistencias] ( @fechaFin DATETIME, @articulos NVARCHAR(MAX), @localidades NVARCHAR(MAX), @ceros BIT, @idUsuario INT)
RETURNS TABLE
AS
RETURN
(
	SELECT
		ART_CodigoArticulo codigo,
		ART_CodigoBarras isbn,
		editorial.CMM_Valor editorial,
		ART_NombreArticulo nombre,
		UM_Nombre um,
		CONCAT(ALM_CodigoAlmacen,' - ',ALM_Nombre) almacen,
		CONCAT(LOC_CodigoLocalidad,' - ',LOC_Nombre) localidad,
		COALESCE(existencias.cantidad, 0) existencia,
		COALESCE(transito.cantidadtransito, 0) transito,
		( (COALESCE(existencias.cantidad, 0) + COALESCE(transito.cantidadtransito, 0)) ) totalExistencia,
		ART_CostoPromedio costo,
		((COALESCE(existencias.cantidad, 0) + COALESCE(transito.cantidadtransito, 0)) * ART_CostoPromedio) total
		-- COALESCE(existencias.cantidad, 0) * ART_CostoPromedio total
		-- ids
		/**
			ART_ArticuloId articuloId,
			ALM_AlmacenId almacenId,
			LOC_LocalidadId localidadId,
		**/
	FROM
		Almacenes
		/* Filter by user-warehouse assignment */
		INNER JOIN UsuariosAlmacenes ON ALM_AlmacenId = USUA_ALM_AlmacenId AND USUA_USU_UsuarioId = @idUsuario
		INNER JOIN Localidades ON ALM_AlmacenId = LOC_ALM_AlmacenId
		INNER JOIN Articulos ON ART_Inventariable = 1
		INNER JOIN ControlesMaestrosMultiples editorial ON ART_CMM_EditorialId = CMM_ControlId
		INNER JOIN UnidadesMedidas ON ART_UM_UnidadMedidaInventarioId = UM_UnidadMedidaId
		LEFT JOIN
		(
			SELECT
				IM_ART_ArticuloId articuloId,
				IM_LOC_LocalidadId localidadId,
				SUM(IM_Cantidad) cantidad
			FROM
				InventariosMovimientos
			WHERE
				IM_FechaCreacion < ISNULL(DATEADD(DAY, 1, @fechaFin), GETDATE())
			GROUP BY
				IM_ART_ArticuloId, IM_LOC_LocalidadId
		) existencias ON existencias.articuloId = ART_ArticuloId AND existencias.localidadId = LOC_LocalidadId
		LEFT JOIN(
			SELECT
				idArticulo,
				idLocalidad,
				SUM(cantidad) AS cantidadtransito
			FROM
			(
			SELECT
				IM_ART_ArticuloId AS idArticulo,
				TRA_LOC_LocalidadDestinoId AS idLocalidad,
				SUM(IM_Cantidad) AS cantidad
			FROM Transferencias
			INNER JOIN TransferenciasDetalles ON TRA_TransferenciaId = TRAD_TRA_TransferenciaId
			INNER JOIN InventariosMovimientos ON IM_LOC_LocalidadId = 0
					AND IM_Referencia LIKE 'TRA%'
					AND TRAD_ART_ArticuloId = IM_ART_ArticuloId AND TRAD_TransferenciaDetalleId = IM_ReferenciaMovtoId --TRA_Codigo = IM_Referencia
			WHERE IM_FechaCreacion < ISNULL(DATEADD(DAY, 1,  @fechaFin), GETDATE())
				-- AND IM_ART_ArticuloId
			GROUP BY IM_ART_ArticuloId, TRA_LOC_LocalidadDestinoId

			UNION ALL

			SELECT
				IM_ART_ArticuloId AS idArticulo,
				PED_LOC_LocalidadCEDISId AS idLocalidad,
				SUM(IM_Cantidad) AS cantidad
			FROM Pedidos
			INNER JOIN PedidosDetalles ON PED_PedidoId = PEDD_PED_PedidoId
			INNER JOIN InventariosMovimientos ON IM_LOC_LocalidadId = 0
					AND IM_Referencia LIKE 'PDD%' AND PEDD_ART_ArticuloId = IM_ART_ArticuloId
					-- AND PEDD_PedidoDetalleId = IM_ReferenciaMovtoId
					AND PED_Codigo = IM_Referencia
			WHERE IM_FechaCreacion < ISNULL(DATEADD(DAY, 1,  @fechaFin), GETDATE())
				-- AND IM_ART_ArticuloId
			GROUP BY IM_ART_ArticuloId, PED_LOC_LocalidadCEDISId
			) AS transito
			GROUP BY idArticulo, idLocalidad
		) AS transito ON  existencias.articuloId = transito.idArticulo AND existencias.localidadId = transito.idLocalidad
	WHERE
		ART_Activo = 1
		AND ART_Inventariable = 1
		AND ALM_Activo = 1
		AND LOC_Activo = 1
		/* Custom filters */
		AND ISNULL(@localidades, '|'+CAST(LOC_LocalidadId AS NVARCHAR(10))+'|') LIKE '%|'+CAST(LOC_LocalidadId AS NVARCHAR(10))+'|%'
		AND ISNULL(@articulos, '|'+CAST(ART_ArticuloId AS NVARCHAR(10))+'|') LIKE '%|'+CAST(ART_ArticuloId AS NVARCHAR(10))+'|%'
		AND (@ceros = 1 OR existencias.cantidad > 0)
)