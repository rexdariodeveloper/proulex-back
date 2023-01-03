CREATE OR ALTER VIEW [dbo].[VW_PROGRAMACION_PAGO_PROJECTION_CXPFactura] AS

SELECT
   CXPF_CXPFacturaId AS id,
   CXPF_PRO_ProveedorId AS proveedorId,
   CXPF_CodigoRegistro AS codigoRegistro,
   CXPF_MontoRegistro AS montoRegistro,
   CXPF_MontoRegistro - COALESCE(montoPagado,0) - COALESCE(montoProgramado,0) AS saldo,
   COALESCE(montoProgramado,0) AS montoProgramado,
   CXPF_FechaRegistro AS fechaRegistro,
   COALESCE(ordenCompraTexto,'-') AS ordenCompraTexto,
   COALESCE(folioSolicitudPagoServicio,PagosRh.codigoRh,'-') AS folioSolicitudPagoServicio,

   COALESCE(evidencia,'[]') AS evidenciaStr,
   COALESCE(evidenciaRh,'[]') AS evidenciaRh,
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
   MON_Codigo as codigoMoneda,
   COALESCE(DatosOC.sucursal, DatosSolicitudPago.sucursal, PagosRh.sucursal) sucursal
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
		CXPF_CXPFacturaId AS cxpFacturaId,
		SUM(CXPSD_MontoProgramado) AS montoProgramado
	FROM CXPFacturas
	INNER JOIN CXPSolicitudesPagosDetalles ON CXPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId AND CXPSD_CMM_EstatusId IN (2000161,2000165)
	GROUP BY CXPF_CXPFacturaId
) PagosProgramados ON PagosProgramados.cxpFacturaId = CXPF_CXPFacturaId

LEFT JOIN (
	SELECT
		CXPF_CXPFacturaId as cxpFacturaId,
		SUC_Nombre as sucursal,
		CPXSPRH_Codigo as codigoRh
	FROM CXPFacturas
	INNER JOIN CXPSolicitudesPagosRH ON CPXSPRH_CXPF_CXPFacturaId = CXPF_CXPFacturaId
	INNER JOIN Sucursales on SUC_SucursalId = CPXSPRH_SUC_SucursalId
) PagosRh ON PagosRh.cxpFacturaId = CXPF_CXPFacturaId

LEFT JOIN(
	SELECT
		id AS cxpFacturaId,
		'[' + STRING_AGG(evidencia,',') + ']' AS evidenciaRh
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
		INNER JOIN CXPSolicitudesPagosRH ON CPXSPRH_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		LEFT JOIN CXPSolicitudesPagosRHBecariosDocumentos ON CPXSPRHBD_CPXSPRH_CXPSolicitudPagoRhId = CPXSPRH_CXPSolicitudPagoRhId
		LEFT JOIN Archivos Evidencia ON Evidencia.ARC_ArchivoId = CPXSPRHBD_ARC_ArchivoId AND Evidencia.ARC_Activo = 1
	) OCSinAgrupar
	GROUP BY id
) DocumentosRh ON DocumentosRh.cxpFacturaId = CXPF_CXPFacturaId

LEFT JOIN Monedas ON CXPF_MON_MonedaId = MON_MonedaId
LEFT JOIN (
	SELECT
		cxpFacturaId,
		CASE
			WHEN COUNT(*) = 1 THEN MAX(OC_Codigo)
			ELSE 'M�ltiple'
		END AS ordenCompraTexto,
		sucursal
	FROM(
		SELECT
			CXPF_CXPFacturaId AS cxpFacturaId,
			OC_Codigo,
			SUC_Nombre sucursal
		FROM CXPFacturas
		INNER JOIN CXPFacturasDetalles ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		INNER JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId
		INNER JOIN OrdenesCompra ON OCR_OC_OrdenCompraId = OC_OrdenCompraId
		INNER JOIN Almacenes ON OC_ALM_RecepcionArticulosAlmacenId = ALM_AlmacenId	
		INNER JOIN Sucursales ON ALM_SUC_SucursalId = SUC_SucursalId
		GROUP BY CXPF_CXPFacturaId, OC_OrdenCompraId, OC_Codigo, SUC_Nombre
	) DatosOC
	GROUP BY cxpFacturaId, sucursal
) DatosOC ON DatosOC.cxpFacturaId = CXPF_CXPFacturaId
LEFT JOIN (
	SELECT
		cxpFacturaId,
		CASE
			WHEN COUNT(*) = 1 THEN MAX(CXPSPS_CodigoSolicitudPagoServicio)
			ELSE 'M�ltiple'
		END AS folioSolicitudPagoServicio,
		sucursal
	FROM(
		SELECT
			CXPF_CXPFacturaId AS cxpFacturaId,
			CXPSPS_CodigoSolicitudPagoServicio,
			SUC_Nombre sucursal
		FROM CXPFacturas
		INNER JOIN CXPSolicitudesPagosServiciosDetalles ON CXPSPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		INNER JOIN CXPSolicitudesPagosServicios ON CXPSPS_CXPSolicitudPagoServicioId = CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId
		INNER JOIN Sucursales ON CXPSPS_SUC_SucursalId = SUC_SucursalId
		GROUP BY CXPF_CXPFacturaId, CXPSPS_CodigoSolicitudPagoServicio, SUC_Nombre
	) DatosSolicitudPago
	GROUP BY cxpFacturaId, sucursal
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
			CXPSPS_FechaModificacion,
			'{
				"id": ' + CAST(CXPSPS_CXPSolicitudPagoServicioId AS varchar) + ',
				"fechaModificacion": ' + CASE WHEN CXPSPS_FechaModificacion IS NULL THEN 'null' ELSE CAST(CXPSPS_FechaModificacion AS varchar) END + '
			}' AS solicitudJson
		FROM CXPFacturas
		INNER JOIN CXPSolicitudesPagosServiciosDetalles ON CXPSPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		INNER JOIN CXPSolicitudesPagosServicios ON CXPSPS_CXPSolicitudPagoServicioId = CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId
		GROUP BY CXPF_CXPFacturaId, CXPSPS_CXPSolicitudPagoServicioId, CXPSPS_FechaModificacion
	) SolicitudesPagoServicio
	GROUP BY cxpFacturaId
) SolicitudesPagoServicio ON SolicitudesPagoServicio.cxpFacturaId = CXPF_CXPFacturaId
WHERE CXPF_CMM_EstatusId IN (2000116,2000119)

GO