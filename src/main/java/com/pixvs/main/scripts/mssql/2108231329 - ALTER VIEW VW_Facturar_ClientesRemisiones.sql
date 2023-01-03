/**
 * Created by Angel Daniel Hernández Silva on 23/08/2021.
 * Object: ALTER VIEW [dbo].[VW_Facturar_ClientesRemisiones]
 */

/************************************************/
/***** Vista VW_Facturar_ClientesRemisiones *****/
/************************************************/

CREATE OR ALTER VIEW [dbo].[VW_Facturar_ClientesRemisiones] AS

    SELECT
		id,
		clienteId,
		fecha,
		codigo,
		SUM(detalleMonto) AS monto,
		SUM(detalleMontoPendiente) AS montoPorRelacionar
	FROM(
		SELECT
			CLIR_ClienteRemisionId AS id,
			CLIR_CLI_ClienteId AS clienteId,
			CLIR_Fecha AS fecha,
			CLIR_Codigo AS codigo,
			(select Total from fn_getImpuestosArticulo(CLIRD_Cantidad,LIPRED_Precio,0,ART_IVA,ART_IEPS,ART_IEPSCuotaFija)) AS detalleMonto,
			(select Total from fn_getImpuestosArticulo(CLIRD_Cantidad-COALESCE(SUM(CXCFD_Cantidad),0),LIPRED_Precio,0,ART_IVA,ART_IEPS,ART_IEPSCuotaFija)) AS detalleMontoPendiente
		FROM ClientesRemisiones
		INNER JOIN ClientesRemisionesDetalles ON CLIRD_CLIR_ClienteRemisionId = CLIR_ClienteRemisionId
		INNER JOIN Clientes ON CLI_ClienteId = CLIR_CLI_ClienteId
		INNER JOIN ListadosPreciosDetalles ON LIPRED_ART_ArticuloId = CLIRD_ART_ArticuloId AND LIPRED_LIPRE_ListadoPrecioId = CLI_LIPRE_ListadoPrecioId
		INNER JOIN Articulos ON ART_ArticuloId = CLIRD_ART_ArticuloId
		LEFT JOIN CXCFacturasDetalles ON CXCFD_CLIRD_ClienteRemisionDetalleId = CLIRD_ClienteRemisionDetalleId
		GROUP BY CLIR_ClienteRemisionId, CLIR_CLI_ClienteId, CLIR_Fecha, CLIR_Codigo, CLIRD_ClienteRemisionDetalleId, CLIRD_Cantidad, LIPRED_Precio, ART_IVA, ART_IEPS, ART_IEPSCuotaFija
	) CLIRD
	GROUP BY id, clienteId, fecha, codigo

GO

/********************************************************/
/***** Vista VW_Facturar_ClientesRemisionesDetalles *****/
/********************************************************/

CREATE OR ALTER VIEW [dbo].[VW_Facturar_ClientesRemisionesDetalles] AS

    SELECT
		CLIRD_ClienteRemisionDetalleId AS id,
		CLIR_ClienteRemisionId AS clienteRemisionId,
		CLIR_Codigo AS codigoRemision,
		ART_CodigoArticulo AS articuloCodigo,
		ART_NombreArticulo AS articuloNombre,
		UM_Nombre AS unidadMedidaNombre,
		CLIRD_Cantidad-COALESCE(SUM(CXCFD_Cantidad),0) AS cantidad,
		LIPRED_Precio AS precioUnitario
	FROM ClientesRemisiones
	INNER JOIN ClientesRemisionesDetalles ON CLIRD_CLIR_ClienteRemisionId = CLIR_ClienteRemisionId
	INNER JOIN Clientes ON CLI_ClienteId = CLIR_CLI_ClienteId
	INNER JOIN ListadosPreciosDetalles ON LIPRED_ART_ArticuloId = CLIRD_ART_ArticuloId AND LIPRED_LIPRE_ListadoPrecioId = CLI_LIPRE_ListadoPrecioId
	INNER JOIN Articulos ON ART_ArticuloId = CLIRD_ART_ArticuloId
	LEFT JOIN CXCFacturasDetalles ON CXCFD_CLIRD_ClienteRemisionDetalleId = CLIRD_ClienteRemisionDetalleId
	INNER JOIN UnidadesMedidas ON UM_UnidadMedidaId = ART_UM_UnidadMedidaInventarioId
	GROUP BY CLIRD_ClienteRemisionDetalleId, CLIR_ClienteRemisionId, CLIR_Codigo, ART_CodigoArticulo, ART_NombreArticulo, UM_Nombre, CLIRD_Cantidad, LIPRED_Precio

GO