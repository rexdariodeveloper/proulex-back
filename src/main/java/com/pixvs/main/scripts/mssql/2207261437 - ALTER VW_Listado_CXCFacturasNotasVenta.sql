CREATE OR ALTER VIEW [dbo].[VW_Listado_CXCFacturasNotasVenta]
AS
	SELECT CXCF_FacturaId AS Id,
			CXCF_Fecha AS Fecha,
			CXCF_Folio AS Folio,
			CXCF_ReceptorRFC+ISNULL(' - '+CXCF_ReceptorNombre, '') AS Cliente,
			SUM(CXCFD_Importe - CXCFD_Descuento + CXCFDI_Importe) AS Monto,
			CMM_Valor AS Estatus,
			(SELECT dbo.getNombreCompletoUsuario(CXCF_USU_CreadoPorId)) AS Usuario
	FROM CXCFacturas
			INNER JOIN CXCFacturasDetalles ON CXCF_FacturaId = CXCFD_CXCF_FacturaId
			CROSS APPLY
			(
					SELECT SUM(CXCFDI_Importe) AS CXCFDI_Importe FROM CXCFacturasDetallesImpuestos WHERE CXCFDI_CXCFD_FacturaDetalleId = CXCFD_FacturaDetalleId
			) AS impuestos
			INNER JOIN ControlesMaestrosMultiples ON CXCF_CMM_EstatusId = CMM_ControlId
	WHERE CXCF_CMM_EstatusId != 2000490 -- No Borradas 
			AND CXCFacturas.CXCF_CMM_TipoRegistroId = 2000473 -- Factura Nota de Venta
	GROUP BY CXCF_FacturaId,
				CXCF_Fecha,
				CXCF_Folio,
				CXCF_ReceptorRFC,
				CXCF_ReceptorNombre,
				CMM_Valor,
				CXCF_USU_CreadoPorId
GO