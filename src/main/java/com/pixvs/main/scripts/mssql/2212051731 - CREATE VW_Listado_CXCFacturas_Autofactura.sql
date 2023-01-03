CREATE OR ALTER VIEW [dbo].[VW_Listado_CXCFacturas_Autofactura]
AS
	SELECT CXCF_FacturaId AS Id,
		   CXCF_Fecha AS Fecha,
		   ISNULL(CXCF_Serie+' ', '')+CXCF_Folio AS Folio,
		   SUC_Nombre AS Sede,
		   SUM(CXCFD_Importe - CXCFD_Descuento + CXCFDI_Importe) AS Monto,
		   OV_OrdenVentaId AS OrdenVentaId,
		   CXCF_ReceptorRFC AS Receptor
	FROM CXCFacturas
		 INNER JOIN CXCFacturasDetalles ON CXCF_FacturaId = CXCFD_CXCF_FacturaId
		 CROSS APPLY
		 (
				SELECT SUM(CXCFDI_Importe) AS CXCFDI_Importe FROM CXCFacturasDetallesImpuestos WHERE CXCFDI_CXCFD_FacturaDetalleId = CXCFD_FacturaDetalleId
		 ) AS impuestos
		 INNER JOIN OrdenesVenta ON CXCF_FacturaId = OV_CXCF_FacturaId
		 INNER JOIN Sucursales ON CXCF_SUC_SucursalId = SUC_SucursalId
		 LEFT JOIN ControlesMaestros AS diasDescargar ON diasDescargar.CMA_Nombre = 'CMA_Autofactura_DiasDescargar'
	WHERE CXCF_CMM_TipoRegistroId = 2000473 -- Factura Nota de Venta
		  AND CXCF_CMM_EstatusId = 2000495 -- Pagada
		  AND CXCF_USU_CreadoPorId IS NULL
		  AND DATEDIFF(DAY, CXCF_Fecha, GETDATE()) <= CASE WHEN TRIM(ISNULL(diasDescargar.CMA_Valor, '')) = '' THEN 30 ELSE CONVERT(INT, TRIM(diasDescargar.CMA_Valor)) END
	GROUP BY CXCF_FacturaId,
			 CXCF_Fecha,
			 ISNULL(CXCF_Serie+' ', '')+CXCF_Folio,
			 SUC_Nombre,
			 OV_OrdenVentaId,
			 CXCF_ReceptorRFC
GO