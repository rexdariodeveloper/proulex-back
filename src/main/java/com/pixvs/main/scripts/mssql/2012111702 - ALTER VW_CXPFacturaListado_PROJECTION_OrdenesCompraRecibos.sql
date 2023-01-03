CREATE OR ALTER VIEW [dbo].[VW_CXPFacturaListado_PROJECTION_OrdenesCompraRecibos] AS

SELECT
	MAX(id) AS id,
	proveedorId,
	proveedorNombre,
	proveedorRfc,
	NULL AS codigoRegistro,
	NULL AS montoRegistro,
	NULL AS fechaRegistro,
	NULL AS fechaVencimiento,
	ordenCompraTexto,
	codigoRecibo,
	MAX(fechaReciboRegistro) AS fechaReciboRegistro,
	CAST(0 AS bit) AS relacionada,
	'[]' AS evidenciaStr
FROM(
	SELECT
		id,
		proveedorId,
		proveedorNombre,
		proveedorRfc,
		ordenCompraTexto,
		codigoRecibo,
		fechaReciboRegistro
	FROM(
		SELECT
			Recibos.OCR_OrdenCompraReciboId AS id,
			PRO_ProveedorId AS proveedorId,
			PRO_Nombre AS proveedorNombre,
			PRO_RFC AS proveedorRfc,
			OC_Codigo AS ordenCompraTexto,
			Recibos.OCR_CodigoRecibo AS codigoRecibo,
			Recibos.OCR_FechaRecibo AS fechaReciboRegistro,
			Recibos.OCR_CantidadRecibo + COALESCE(SUM(Devoluciones.OCR_CantidadRecibo),0) AS cantidadRecibida
		FROM OrdenesCompraRecibos Recibos
		INNER JOIN OrdenesCompra ON OC_OrdenCompraId = Recibos.OCR_OC_OrdenCompraId
		INNER JOIN Proveedores ON PRO_ProveedorId = OC_PRO_ProveedorId
		LEFT JOIN OrdenesCompraRecibos Devoluciones ON Devoluciones.OCR_OCR_ReciboReferenciaId = Recibos.OCR_OrdenCompraReciboId
		WHERE Recibos.OCR_OCR_ReciboReferenciaId IS NULL AND Recibos.OCR_CantidadRecibo > 0
		GROUP BY Recibos.OCR_OrdenCompraReciboId, PRO_ProveedorId, PRO_Nombre, PRO_RFC, OC_Codigo, Recibos.OCR_CodigoRecibo, Recibos.OCR_FechaRecibo, Recibos.OCR_CantidadRecibo
	) Recibos
	LEFT JOIN CXPFacturasDetalles ON CXPFD_OCR_OrdenCompraReciboId = id
	GROUP BY id, proveedorId, proveedorNombre, proveedorRfc, ordenCompraTexto, codigoRecibo, fechaReciboRegistro, cantidadRecibida
	HAVING (cantidadRecibida - COALESCE(SUM(CXPFD_Cantidad),0)) > 0
) Recibos
GROUP BY proveedorId, proveedorNombre, proveedorRfc, ordenCompraTexto, codigoRecibo

GO