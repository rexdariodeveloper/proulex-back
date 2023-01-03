CREATE OR ALTER VIEW [dbo].[VW_Listado_CXCFacturasGlobales]
AS
	SELECT CXCF_FacturaId AS Id,
			CXCF_Fecha AS Fecha,
			CXCF_Folio AS Folio,
			FP_Codigo + ' - ' + FP_Nombre AS FormaPago,
			SUM(CXCFD_Importe - CXCFD_Descuento + ISNULL(CXCFDI_Importe, 0)) AS Monto,
			CMM_Valor AS Estatus,
			(SELECT dbo.getNombreCompletoUsuario(CXCF_USU_CreadoPorId)) AS Usuario
	FROM CXCFacturas
			INNER JOIN CXCFacturasDetalles ON CXCF_FacturaId = CXCFD_CXCF_FacturaId
			CROSS APPLY
			(
					SELECT SUM(CXCFDI_Importe) AS CXCFDI_Importe FROM CXCFacturasDetallesImpuestos WHERE CXCFDI_CXCFD_FacturaDetalleId = CXCFD_FacturaDetalleId
			) AS impuestos
			INNER JOIN ControlesMaestrosMultiples ON CXCF_CMM_EstatusId = CMM_ControlId
			INNER JOIN FormasPago ON CXCF_FP_FormaPagoId = FP_FormaPagoId
	WHERE CXCF_CMM_EstatusId != 2000490 -- No Borradas 
			AND CXCFacturas.CXCF_CMM_TipoRegistroId = 2000474 -- Factura Global
	GROUP BY CXCF_FacturaId,
				CXCF_Fecha,
				CXCF_Folio,
				FP_Codigo,
				FP_Nombre,
				CMM_Valor,
				CXCF_USU_CreadoPorId
GO