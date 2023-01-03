SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


-- =============================================
-- Author:		Rene Carrillo
-- Create date: 01/01/2022
-- Modify date:
-- Description:	la vista para obtener el reporte de factura con Nota de Venta
-- Version 1.0.0
-- =============================================

CREATE VIEW [dbo].[VW_RPT_ReporteFacturasNotaVenta]
AS
	SELECT f.CXCF_FacturaId AS Id,
		f.CXCF_Fecha AS FechaFactura,
		s.SUC_SucursalId AS SucursalId,
		s.SUC_Nombre AS Sede,
		tf.CMM_Valor AS TipoFactura,
		ISNULL(f.CXCF_Serie + ' ', '') + f.CXCF_Folio AS FolioFactura,
		f.CXCF_ReceptorRFC AS Receptor,
		f.CXCF_ReceptorRFC + ISNULL(' - ' + f.CXCF_ReceptorNombre, '') AS Cliente,
		--fd.CXCFD_Importe AS MontoFactura,
		e.CMM_ControlId AS EstatusId,
		e.CMM_Valor AS Estatus,
		SUM((fd.CXCFD_Importe - fd.CXCFD_Descuento) + fdi.CXCFDI_Importe) AS Total,
		ov.NotaVenta
	FROM CXCFacturas f
		INNER JOIN CXCFacturasDetalles fd ON f.CXCF_FacturaId = fd.CXCFD_CXCF_FacturaId
		INNER JOIN Sucursales s ON f.CXCF_SUC_SucursalId = s.SUC_SucursalId
		INNER JOIN ControlesMaestrosMultiples tf ON f.CXCF_CMM_TipoRegistroId = tf.CMM_ControlId
		INNER JOIN (SELECT fdi.CXCFDI_CXCFD_FacturaDetalleId, SUM(fdi.CXCFDI_Importe) AS CXCFDI_Importe
					FROM CXCFacturasDetallesImpuestos fdi
					GROUP BY fdi.CXCFDI_CXCFD_FacturaDetalleId) AS fdi ON fd.CXCFD_FacturaDetalleId = fdi.CXCFDI_CXCFD_FacturaDetalleId
		INNER JOIN ControlesMaestrosMultiples e ON f.CXCF_CMM_EstatusId = e.CMM_ControlId
		LEFT JOIN (SELECT ov.OV_CXCF_FacturaId,
						ov.OV_Codigo AS NotaVenta
					FROM OrdenesVenta ov
					WHERE NOT ov.OV_CXCF_FacturaId IS NULL
					GROUP BY ov.OV_CXCF_FacturaId, ov.OV_Codigo) AS ov ON f.CXCF_FacturaId = ov.OV_CXCF_FacturaId
	WHERE f.CXCF_CMM_EstatusId IN (2000491, 2000493, 2000495)
	GROUP BY f.CXCF_FacturaId, f.CXCF_Fecha, s.SUC_SucursalId, s.SUC_Nombre, tf.CMM_Valor, ISNULL(f.CXCF_Serie + ' ', '') + f.CXCF_Folio, f.CXCF_ReceptorRFC, f.CXCF_ReceptorNombre, e.CMM_ControlId, e.CMM_Valor, ov.NotaVenta
GO