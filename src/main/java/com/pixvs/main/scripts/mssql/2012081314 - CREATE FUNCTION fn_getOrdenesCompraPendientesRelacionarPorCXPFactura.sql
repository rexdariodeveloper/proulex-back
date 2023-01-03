/**
 * Created by Angel Daniel Hernández Silva on 08/12/2020.
 * Object:  FUNCTION [dbo].[fn_getOrdenesCompraPendientesRelacionarPorCXPFactura]
 */


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fn_getOrdenesCompraPendientesRelacionarPorCXPFactura](
	@cxpFacturaId int
)RETURNS @tbl TABLE(
	id int,
	codigo varchar(150),
	fechaOC date,
	montoOC decimal(28,6),
	MontoPendienteRelacionar decimal(28,6)
) AS BEGIN

	INSERT INTO @tbl
	SELECT
		id,
		codigo,
		fechaOC,

		SUM(totalDetalle) AS montoOC,
		SUM(totalPendienteDetalle) AS montoPendienteRelacionar
	FROM(
		SELECT
			id,
			codigo,
			fechaOC,

			(SELECT Total FROM [dbo].[fn_getImpuestosArticulo](cantidadDetalle,OCD_Precio,OCD_Descuento,CASE OCD_IVAExento WHEN 1 THEN 0 ELSE OCD_IVA END,CASE WHEN OCD_IEPSCuotaFija IS NOT NULL THEN 0 ELSE OCD_IEPS END, OCD_IEPSCuotaFija)) AS totalDetalle,
			(SELECT Total FROM [dbo].[fn_getImpuestosArticulo](cantidadRecibo - cantidadRelacionada,OCD_Precio,OCD_Descuento,CASE OCD_IVAExento WHEN 1 THEN 0 ELSE OCD_IVA END,CASE WHEN OCD_IEPSCuotaFija IS NOT NULL THEN 0 ELSE OCD_IEPS END, OCD_IEPSCuotaFija)) AS totalPendienteDetalle
		FROM(
			SELECT
				id,
				codigo,
				fechaOC,

				detalleId,
				cantidadDetalle,
				OCD_Precio,
				OCD_Descuento,
				OCD_IVAExento,
				OCD_IVA,
				OCD_IEPSCuotaFija,
				OCD_IEPS,

				SUM(cantidadRecibo) AS cantidadRecibo,

				SUM(cantidadRelacionada) AS cantidadRelacionada
			FROM(
				SELECT
					id,
					codigo,
					fechaOC,

					OCD_OrdenCompraDetalleId AS detalleId,
					OCD_Cantidad AS cantidadDetalle,
					OCD_Precio,
					OCD_Descuento,
					OCD_IVAExento,
					OCD_IVA,
					OCD_IEPSCuotaFija,
					OCD_IEPS,

					OCR_OrdenCompraReciboId AS reciboId,
					OCR_CantidadRecibo AS cantidadRecibo,

					SUM(CXPFD_Cantidad) AS cantidadRelacionada
				FROM(
					SELECT
						OC_OrdenCompraId AS id,
						OC_Codigo AS codigo,
						OC_FechaOC AS fechaOC
					FROM OrdenesCompra
					INNER JOIN OrdenesCompraRecibos ON OCR_OC_OrdenCompraId = OC_OrdenCompraId
					INNER JOIN CXPFacturasDetalles ON CXPFD_OCR_OrdenCompraReciboId = OCR_OrdenCompraReciboId
					INNER JOIN CXPFacturas ON CXPF_CXPFacturaId = CXPFD_CXPF_CXPFacturaId AND CXPF_CXPFacturaId = @cxpFacturaId
					GROUP BY OC_OrdenCompraId, OC_Codigo, OC_FechaOC
				) OCs
				INNER JOIN OrdenesCompraDetalles ON OCD_OC_OrdenCompraId = id
				LEFT JOIN OrdenesCompraRecibos ON OCR_OCD_OrdenCompraDetalleId = OCD_OrdenCompraDetalleId
				LEFT JOIN CXPFacturasDetalles ON CXPFD_OCR_OrdenCompraReciboId = OCR_OrdenCompraReciboId
				GROUP BY id, codigo, fechaOC, OCD_OrdenCompraDetalleId, OCD_Cantidad, OCR_OrdenCompraReciboId, OCR_CantidadRecibo, OCD_Precio, OCD_Descuento, OCD_IVAExento, OCD_IVA, OCD_IEPSCuotaFija, OCD_IEPS
			) OCsAgrupadoRecibos
			GROUP BY id, codigo, fechaOC, detalleId, cantidadDetalle, OCD_Precio, OCD_Descuento, OCD_IVAExento, OCD_IVA, OCD_IEPSCuotaFija, OCD_IEPS
		) OCsAgrupadoDetalles
	) OCMontos
	GROUP BY id, codigo, fechaOC
	RETURN
END