SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ================================================
-- Author:		Rene Carrillo
-- Create date: 19/07/2022
-- Modified by: Javier Elías
-- Modify date: 22/09/2022
-- Description:	Vista para obtener el reporte de facturas
-- ================================================
CREATE OR ALTER VIEW [dbo].[VW_RPT_ReporteFacturas]
AS
	SELECT CXCF_FacturaId AS Id,
		   CXCF_Fecha AS FechaFactura,
		   SUC_SucursalId AS SucursalId,
		   SUC_Nombre AS Sede,
		   tipoFactura.CMM_Valor AS TipoFactura,
		   ISNULL(CXCF_Serie + ' ', '') + CXCF_Folio AS FolioFactura,
		   CXCF_ReceptorRFC AS Receptor,
		   CXCF_ReceptorRFC+ISNULL(' - '+CXCF_ReceptorNombre, '') AS Cliente,
		   estatus.CMM_ControlId AS EstatusId,
		   estatus.CMM_Valor AS Estatus,
		   SUM(CXCFD_Importe - CXCFD_Descuento + CXCFDI_Importe) AS Total,
		   CASE WHEN ov.NotaVenta IS NOT NULL AND CHARINDEX(',', ISNULL(ov.NotaVenta, '')) != 0 THEN 'VARIAS' ELSE ov.NotaVenta END AS NotaVenta,
		   ov.NotaVenta AS TodasOV
	FROM CXCFacturas
		 INNER JOIN CXCFacturasDetalles ON CXCF_FacturaId = CXCFD_CXCF_FacturaId
		 INNER JOIN Sucursales ON CXCF_SUC_SucursalId = SUC_SucursalId
		 INNER JOIN ControlesMaestrosMultiples AS tipoFactura ON CXCF_CMM_TipoRegistroId = tipoFactura.CMM_ControlId
		 INNER JOIN ControlesMaestrosMultiples AS estatus ON CXCF_CMM_EstatusId = estatus.CMM_ControlId
		 CROSS APPLY
		 (
				SELECT SUM(CXCFDI_Importe) AS CXCFDI_Importe FROM CXCFacturasDetallesImpuestos WHERE CXCFDI_CXCFD_FacturaDetalleId = CXCFD_FacturaDetalleId
		 ) AS impuestos
		 INNER JOIN
		 (
			SELECT CXCF_FacturaId AS Id,
				   STUFF(
					(
						SELECT DISTINCT
							   ', '+OV_Codigo
						FROM OrdenesVenta
						WHERE CXCF_FacturaId = OV_CXCF_FacturaId FOR XML PATH('')
					), 1, 2, '') AS NotaVenta
			FROM CXCFacturas
		 ) AS ov ON CXCF_FacturaId = ov.Id
	WHERE CXCF_CMM_EstatusId IN(2000491, 2000493, 2000494, 2000495) -- Abierta, Cancelada, Pago Parcial, Pagada
	GROUP BY CXCF_FacturaId,
			 CXCF_Fecha,
			 SUC_SucursalId,
			 SUC_Nombre,
			 tipoFactura.CMM_Valor,
			 ISNULL(CXCF_Serie+' ', '')+CXCF_Folio,
			 CXCF_ReceptorRFC,
			 CXCF_ReceptorNombre,
			 estatus.CMM_ControlId,
			 estatus.CMM_Valor,
			 ov.NotaVenta
GO