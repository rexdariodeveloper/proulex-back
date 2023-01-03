SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER FUNCTION [dbo].[fn_getSaldoPagosFacturacion] ()
RETURNS TABLE
AS
RETURN
(
	SELECT factura.CXCF_FacturaId AS FacturaId,
		   monto.Saldo AS Monto,
		   CASE WHEN saldo.FacturaId IS NULL THEN monto.Saldo ELSE saldo.Saldo END AS Saldo,
		   ISNULL(saldo.NoParcialidad, 0) + 1 AS NoParcialidadSiguiente,
		   CXCF_DF_DatosFacturacionId AS DatosFacturacionId
	FROM CXCFacturas AS factura
		 INNER JOIN
		 (
			SELECT CXCF_FacturaId AS FacturaId,
					SUM(CXCFD_Importe - CXCFD_Descuento + CXCFDI_Importe) - 0 AS Saldo
			FROM CXCFacturas
					INNER JOIN CXCFacturasDetalles ON CXCF_FacturaId = CXCFD_CXCF_FacturaId
					CROSS APPLY
					(
						SELECT SUM(CXCFDI_Importe) AS CXCFDI_Importe FROM CXCFacturasDetallesImpuestos WHERE CXCFDI_CXCFD_FacturaDetalleId = CXCFD_FacturaDetalleId
					) AS impuestos
			GROUP BY CXCF_FacturaId
		 ) AS monto ON CXCF_FacturaId = FacturaId
		 OUTER APPLY
		 (
			SELECT TOP 1 CXCF_FacturaId AS FacturaId,
						CXCPD_ImporteSaldoInsoluto AS Saldo,
						CXCPD_NoParcialidad AS NoParcialidad
			FROM CXCPagosDetalles
					INNER JOIN CXCPagos ON CXCPD_CXCP_PagoId = CXCP_PagoId
					INNER JOIN CXCFacturas AS facturaPago ON CXCP_CXCF_FacturaId = facturaPago.CXCF_FacturaId AND facturaPago.CXCF_CMM_EstatusId != 2000493 -- CMM_CXCF_EstatusFactura Cancelada
			WHERE CXCPD_CXCF_DoctoRelacionadoId = factura.CXCF_FacturaId
			ORDER BY facturaPago.CXCF_FechaCreacion DESC
		 ) AS saldo
	WHERE factura.CXCF_CMM_EstatusId IN(2000491, 2000494) -- Abierta o Pago Parcial
)