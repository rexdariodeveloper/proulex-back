/**
 * Created by Angel Daniel Hernández Silva on 09/03/2021.
 * Object:  FUNCTION [dbo].[fn_getOrdenesCompraPendientesRelacionar]
 */

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER FUNCTION [dbo].[fn_getOrdenesCompraPendientesRelacionar](
	@proveedorId int
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
		montoOC,
		SUM(totalPendienteDetalle) AS MontoPendienteRelacionar
	FROM(
		SELECT
			id,
			codigo,
			fechaOC,
			montoOC,
			(SELECT Total FROM [dbo].[fn_getImpuestosArticulo](cantidadRecibida - SUM(COALESCE(CASE WHEN COALESCE(CXPF_CMM_EstatusId,2000111) IN (2000111,2000114) THEN NULL ELSE CXPFD_Cantidad END,0)),OCD_Precio,OCD_Descuento,CASE OCD_IVAExento WHEN 1 THEN 0 ELSE OCD_IVA END,CASE WHEN OCD_IEPSCuotaFija IS NOT NULL THEN 0 ELSE OCD_IEPS END, OCD_IEPSCuotaFija)) AS totalPendienteDetalle
		FROM(
			SELECT
				id,
				codigo,
				fechaOC,
				montoOC,
				OCD_OrdenCompraDetalleId AS detalleId,
				SUM(OCR_CantidadRecibo) cantidadRecibida
			FROM(
				SELECT
					id,
					codigo,
					fechaOC,
					SUM(totalDetalle) AS montoOC
				FROM(
					SELECT
						OC_OrdenCompraId AS id,
						OC_Codigo AS codigo,
						OC_FechaOC AS fechaOC,
						(SELECT Total FROM [dbo].[fn_getImpuestosArticulo](OCD_Cantidad,OCD_Precio,OCD_Descuento,CASE OCD_IVAExento WHEN 1 THEN 0 ELSE OCD_IVA END,CASE WHEN OCD_IEPSCuotaFija IS NOT NULL THEN 0 ELSE OCD_IEPS END, OCD_IEPSCuotaFija)) AS totalDetalle
					FROM OrdenesCompra
					INNER JOIN OrdenesCompraDetalles ON OCD_OC_OrdenCompraId = OC_OrdenCompraId
					WHERE
						(@proveedorId IS NULL OR OC_PRO_ProveedorId = @proveedorId)
						AND OC_CMM_EstatusId IN (2000066,2000067)
				) OrdenesCompraTotalesDetalles
				GROUP BY id, codigo, fechaOC
			) OrdenesCompraMontoOC
			INNER JOIN OrdenesCompraDetalles ON OCD_OC_OrdenCompraId = id
			INNER JOIN OrdenesCompraRecibos ON OCR_OCD_OrdenCompraDetalleId = OCD_OrdenCompraDetalleId
			GROUP BY id, codigo, fechaOC, montoOC, OCD_OrdenCompraDetalleId
		) OrdenesCompraDetallesCantidadRecibida
		INNER JOIN OrdenesCompraDetalles ON OCD_OrdenCompraDetalleId = detalleId
		INNER JOIN OrdenesCompraRecibos ON OCR_OCD_OrdenCompraDetalleId = OCD_OrdenCompraDetalleId
		LEFT JOIN CXPFacturasDetalles ON CXPFD_OCR_OrdenCompraReciboId = OCR_OrdenCompraReciboId
		LEFT JOIN CXPFacturas ON CXPF_CXPFacturaId = CXPFD_CXPF_CXPFacturaId
		GROUP BY id, codigo, fechaOC, montoOC, OCD_OrdenCompraDetalleId, cantidadRecibida, OCD_Precio, OCD_Descuento, OCD_IVAExento, OCD_IVA, OCD_IEPSCuotaFija, OCD_IEPS
	) OrdenesCompraRelacionar
	GROUP BY id, codigo, fechaOC, montoOC
	RETURN
END