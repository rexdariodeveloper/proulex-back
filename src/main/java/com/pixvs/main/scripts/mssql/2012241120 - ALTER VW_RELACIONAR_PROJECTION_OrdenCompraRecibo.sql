
CREATE OR ALTER VIEW [dbo].[VW_RELACIONAR_PROJECTION_OrdenCompraRecibo] AS

SELECT
	OCR_OrdenCompraReciboId AS id,
	cantidadRecibo,
	totalRecibido - COALESCE(SUM(CXPFD_Cantidad),0) AS cantidadPendienteRelacionar,

	ordenCompraDetalleId
FROM(
	SELECT
		Recibos.OCR_OrdenCompraReciboId,
		Recibos.OCR_CantidadRecibo AS cantidadRecibo,
		Recibos.OCR_CantidadRecibo - COALESCE(SUM(Devoluciones.OCR_CantidadRecibo),0) AS totalRecibido,

		Recibos.OCR_OCD_OrdenCompraDetalleId AS ordenCompraDetalleId
	FROM OrdenesCompraRecibos Recibos
	LEFT JOIN OrdenesCompraRecibos Devoluciones ON Devoluciones.OCR_OCR_ReciboReferenciaId = Recibos.OCR_OrdenCompraReciboId
	WHERE Recibos.OCR_OCR_ReciboReferenciaId IS NULL AND Recibos.OCR_CantidadRecibo > 0
	GROUP BY Recibos.OCR_OrdenCompraReciboId, Recibos.OCR_CantidadRecibo, Recibos.OCR_OCD_OrdenCompraDetalleId
) TotalesRecibos
LEFT JOIN CXPFacturasDetalles ON CXPFD_OCR_OrdenCompraReciboId = OCR_OrdenCompraReciboId
GROUP BY OCR_OrdenCompraReciboId, cantidadRecibo, totalRecibido, ordenCompraDetalleId
HAVING totalRecibido - COALESCE(SUM(CXPFD_Cantidad),0) > 0

GO