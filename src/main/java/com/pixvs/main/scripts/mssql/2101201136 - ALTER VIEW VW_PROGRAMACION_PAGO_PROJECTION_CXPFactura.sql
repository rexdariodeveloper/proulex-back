
CREATE OR ALTER VIEW [dbo].[VW_PROGRAMACION_PAGO_PROJECTION_CXPFactura] AS

SELECT
   CXPF_CXPFacturaId AS id,
   CXPF_PRO_ProveedorId AS proveedorId,
   CXPF_CodigoRegistro AS codigoRegistro,
   CXPF_MontoRegistro AS montoRegistro,
   CXPF_MontoRegistro - COALESCE(montoPagado,0) AS saldo,
   CXPF_FechaRegistro AS fechaRegistro,
   COALESCE(ordenCompraTexto,'-') AS ordenCompraTexto,
   COALESCE(folioSolicitudPagoServicio,'-') AS folioSolicitudPagoServicio,

   COALESCE(evidencia,'[]') AS evidenciaStr,
   '{
		"id": ' + CAST(FacturaPDF.ARC_ArchivoId AS varchar) + ',
		"nombreOriginal": "' + FacturaPDF.ARC_NombreOriginal + '",
		"activo": true,
		"fechaCreacion": ' + CAST(FacturaPDF.ARC_FechaCreacion AS varchar) + '
	}' AS facturaPDFStr,
	'{
		"id": ' + CAST(FacturaXML.ARC_ArchivoId AS varchar) + ',
		"nombreOriginal": "' + FacturaXML.ARC_NombreOriginal + '",
		"activo": true,
		"fechaCreacion": ' + CAST(FacturaXML.ARC_FechaCreacion AS varchar) + '
	}' AS facturaXMLStr,

	COALESCE(cxpSolicitudesPagosServiciosStr,'[]') AS cxpSolicitudesPagosServiciosStr,
   
   CXPF_DiasCredito AS diasCredito,
   CXPF_FechaModificacion AS fechaModificacion,
   codigoMoneda as codigoMoneda
FROM CXPFacturas
LEFT JOIN (
	SELECT
		CXPF_CXPFacturaId AS cxpFacturaId,
		SUM(CXPPD_MontoAplicado) AS montoPagado
	FROM CXPFacturas
	INNER JOIN CXPPagosDetalles ON CXPPD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
	INNER JOIN CXPPagos ON CXPP_CXPPagoId = CXPPD_CXPP_CXPPagoId AND CXPP_CMM_EstatusId != 2000173
	GROUP BY CXPF_CXPFacturaId
) Pagos ON Pagos.cxpFacturaId = CXPF_CXPFacturaId

LEFT JOIN (
	SELECT 
		CXPF_CXPFacturaId as cxpFacturaId,
		MON_Codigo as codigoMoneda
	FROM CXPFacturas
	INNER JOIN Monedas ON MON_MonedaId = CXPF_MON_MonedaId
) Moneda ON Moneda.cxpFacturaId = CXPF_CXPFacturaId
LEFT JOIN (
	SELECT
		cxpFacturaId,
		CASE
			WHEN COUNT(*) = 1 THEN MAX(OC_Codigo)
			ELSE 'Múltiple'
		END AS ordenCompraTexto
	FROM(
		SELECT
			CXPF_CXPFacturaId AS cxpFacturaId,
			OC_Codigo
		FROM CXPFacturas
		INNER JOIN CXPFacturasDetalles ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		INNER JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId
		INNER JOIN OrdenesCompra ON OCR_OC_OrdenCompraId = OC_OrdenCompraId
		GROUP BY CXPF_CXPFacturaId, OC_OrdenCompraId, OC_Codigo
	) DatosOC
	GROUP BY cxpFacturaId
) DatosOC ON DatosOC.cxpFacturaId = CXPF_CXPFacturaId
LEFT JOIN (
	SELECT
		cxpFacturaId,
		CASE
			WHEN COUNT(*) = 1 THEN MAX(CXPSPS_CodigoSolicitudPagoServicio)
			ELSE 'Múltiple'
		END AS folioSolicitudPagoServicio
	FROM(
		SELECT
			CXPF_CXPFacturaId AS cxpFacturaId,
			CXPSPS_CodigoSolicitudPagoServicio
		FROM CXPFacturas
		INNER JOIN CXPSolicitudesPagosServiciosDetalles ON CXPSPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		INNER JOIN CXPSolicitudesPagosServicios ON CXPSPS_CXPSolicitudPagoServicioId = CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId
		GROUP BY CXPF_CXPFacturaId, CXPSPS_CodigoSolicitudPagoServicio
	) DatosSolicitudPago
	GROUP BY cxpFacturaId
) DatosSolicitudPago ON DatosSolicitudPago.cxpFacturaId = CXPF_CXPFacturaId
LEFT JOIN(

	SELECT
		id AS cxpFacturaId,
		'[' + STRING_AGG(evidencia,',') + ']' AS evidencia
	FROM(
		SELECT
			CXPF_CXPFacturaId AS id,
			'{
				"id": ' + CAST(Evidencia.ARC_ArchivoId AS varchar) + ',
				"nombreOriginal": "' + Evidencia.ARC_NombreOriginal + '",
				"activo": true,
				"fechaCreacion": ' + CAST(Evidencia.ARC_FechaCreacion AS varchar) + '
			}' AS evidencia
		FROM CXPFacturas
		INNER JOIN CXPFacturasDetalles ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		INNER JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId
		LEFT JOIN OrdenesCompraRecibosEvidencia ON OCRE_OCR_OrdenCompraReciboId = OCR_OrdenCompraReciboId
		LEFT JOIN Archivos Evidencia ON Evidencia.ARC_ArchivoId = OCRE_ARC_ArchivoId AND Evidencia.ARC_Activo = 1
	) OCSinAgrupar
	GROUP BY id
) CXPFEvidencia ON CXPFEvidencia.cxpFacturaId = CXPF_CXPFacturaId
LEFT JOIN Archivos FacturaPDF ON FacturaPDF.ARC_ArchivoId = CXPF_ARC_FacturaPDFId AND FacturaPDF.ARC_Activo = 1
LEFT JOIN Archivos FacturaXML ON FacturaXML.ARC_ArchivoId = CXPF_ARC_FacturaXMLId AND FacturaXML.ARC_Activo = 1
LEFT JOIN (
	SELECT
		cxpFacturaId,
		'[' + STRING_AGG(solicitudJson,',') + ']' AS cxpSolicitudesPagosServiciosStr
	FROM(
		SELECT
			CXPF_CXPFacturaId AS cxpFacturaId,
			CXPSPS_CXPSolicitudPagoServicioId,
			CXPF_FechaModificacion,
			'{
				"id": ' + CAST(CXPSPS_CXPSolicitudPagoServicioId AS varchar) + ',
				"fechaModificacion": ' + CASE WHEN CXPF_FechaModificacion IS NULL THEN 'null' ELSE CAST(CXPF_FechaModificacion AS varchar) END + '
			}' AS solicitudJson
		FROM CXPFacturas
		INNER JOIN CXPSolicitudesPagosServiciosDetalles ON CXPSPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		INNER JOIN CXPSolicitudesPagosServicios ON CXPSPS_CXPSolicitudPagoServicioId = CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId
		GROUP BY CXPF_CXPFacturaId, CXPSPS_CXPSolicitudPagoServicioId, CXPF_FechaModificacion
	) SolicitudesPagoServicio
	GROUP BY cxpFacturaId
) SolicitudesPagoServicio ON SolicitudesPagoServicio.cxpFacturaId = CXPF_CXPFacturaId
WHERE CXPF_CMM_EstatusId = 2000116

GO