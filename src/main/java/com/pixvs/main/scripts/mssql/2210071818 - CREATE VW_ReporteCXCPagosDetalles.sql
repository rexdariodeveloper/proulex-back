SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =========================================
-- Author:		Javier Elías
-- Create date: 06/10/2022
-- Description:	Vista para obtener el reporte de 
--						Pago de Clientes detalles
-- =========================================
CREATE OR ALTER VIEW [dbo].[VW_ReporteCXCPagosDetalles]
AS
	SELECT CXCPD_PagoDetalleId AS id,
		   CXCPD_CXCP_PagoId AS pagoId,
		   CXCF_FacturaId AS facturaId,
		   SUC_SucursalId AS sedeId,
		   SUC_Nombre AS sede,
		   ISNULL(CXCF_Serie + ' ', '') + CXCF_Folio AS numeroFactura,
		   CXCF_Fecha AS fechaFactura,
		   SUM(CXCFD_Importe - CXCFD_Descuento  +  ISNULL(CXCFDI_Importe, 0)) AS montoFactura,
		   CXCPD_ImportePagado AS importePagado,
		   CXCPD_ImporteSaldoInsoluto AS saldo,
		   CMM_Referencia AS metodoPago
	FROM CXCPagosDetalles
		 INNER JOIN CXCFacturas ON CXCPD_CXCF_DoctoRelacionadoId = CXCF_FacturaId
		 INNER JOIN Sucursales ON CXCF_SUC_SucursalId = SUC_SucursalId
		 INNER JOIN CXCFacturasDetalles ON CXCF_FacturaId = CXCFD_CXCF_FacturaId
		 CROSS APPLY
		 (
				SELECT SUM(CXCFDI_Importe) AS CXCFDI_Importe FROM CXCFacturasDetallesImpuestos WHERE CXCFDI_CXCFD_FacturaDetalleId = CXCFD_FacturaDetalleId
		 ) AS impuestos
		 INNER JOIN ControlesMaestrosMultiples ON CXCF_CMM_MetodoPagoId = CMM_ControlId
	GROUP BY CXCPD_PagoDetalleId,
			 CXCPD_CXCP_PagoId,
			 CXCF_FacturaId,
			 SUC_SucursalId,
			 SUC_Nombre,
			 ISNULL(CXCF_Serie + ' ', '') + CXCF_Folio,
			 CXCF_Fecha,
			 CXCPD_ImportePagado,
			 CXCPD_ImporteSaldoInsoluto,
			 CMM_Referencia
GO