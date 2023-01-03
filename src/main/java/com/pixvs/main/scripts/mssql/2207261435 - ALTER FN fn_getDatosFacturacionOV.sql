SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER FUNCTION [dbo].[fn_getDatosFacturacionOV](@sucursalId INT, @codigoOV VARCHAR(150))
RETURNS TABLE
AS
RETURN
(
	SELECT OV_OrdenVentaId AS Id,
			OV_FechaCreacion AS Fecha,
			OV_Codigo AS Codigo,
			SUM(Subtotal) AS Subtotal,
			SUM(Descuento) AS Descuento,
			SUM(ImporteIVA + ISNULL(ImporteIEPS, 0)) AS Impuestos,
			SUM(Total) AS Total,
			OV_SUC_SucursalId AS SucursalId,
			OV_CMM_EstatusId AS EstatusId,
			OV_CXCF_FacturaId AS FacturaId
	FROM OrdenesVenta
			INNER JOIN OrdenesVentaDetalles ON OV_OrdenVentaId = OVD_OV_OrdenVentaId AND OVD_OVD_DetallePadreId IS NULL
			CROSS APPLY dbo.fn_getMontosFacturacionDetalleOV(OVD_OrdenVentaDetalleId) AS montos
			LEFT JOIN OrdenesVentaCancelacionesDetalles ON OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
	WHERE OVCD_OrdenVentaCancelacionDetalleId IS NULL
			AND OV_SUC_SucursalId = @sucursalId
			AND OV_Codigo = @codigoOV
	GROUP BY OV_OrdenVentaId,
				OV_Codigo,
				OV_FechaCreacion,
				OV_SUC_SucursalId,
				OV_CMM_EstatusId,
				OV_CXCF_FacturaId
)