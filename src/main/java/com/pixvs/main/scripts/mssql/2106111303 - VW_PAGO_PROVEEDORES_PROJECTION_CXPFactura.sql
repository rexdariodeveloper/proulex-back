CREATE OR ALTER VIEW [dbo].[VW_PAGO_PROVEEDORES_PROJECTION_CXPFactura] AS

SELECT
   CXPF_CXPFacturaId AS id,
   CXPF_CodigoRegistro AS codigoRegistro,
   CXPF_MontoRegistro AS montoRegistro,
   CXPF_MontoRegistro - COALESCE(montoPagado,0) - COALESCE(montoProgramado,0) AS saldo,
   CXPF_FechaRegistro AS fechaRegistro,
   COALESCE(ordenCompraTexto,'-') AS ordenCompraTexto,
   COALESCE(folioSolicitudPagoServicio,PagosRh.codigoRh,'-') AS folioSolicitudPagoServicio,

   COALESCE(evidencia,'[]') AS evidenciaStr,
   COALESCE(evidenciaRh,'[]') AS evidenciaStrRh,
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
   
   CXPF_DiasCredito AS diasCredito
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
		CPXSPRH_Codigo as codigoRh,
		COALESCE(CPXSPRHPA_NombreBeneficiario, CONCAT(EMP_Nombre, EMP_PrimerApellido, EMP_SegundoApellido)) AS beneficiario
	FROM CXPFacturas
	INNER JOIN CXPSolicitudesPagosRH ON CPXSPRH_CXPF_CXPFacturaId = CXPF_CXPFacturaId
	INNER JOIN Empleados ON CPXSPRH_EMP_EmpleadoId = EMP_EmpleadoId
	LEFT JOIN CXPSolicitudesPagosRHPensionesAlimenticias ON CPXSPRH_CXPSolicitudPagoRhId = CPXSPRHPA_CPXSPRH_CXPSolicitudPagoRhId
	INNER JOIN Sucursales on SUC_SucursalId = CPXSPRH_SUC_SucursalId
) PagosRh ON PagosRh.cxpFacturaId = CXPF_CXPFacturaId

LEFT JOIN(
	SELECT
		id AS cxpFacturaId,
		'[' + STRING_AGG(evidencia,',') + ']' AS evidenciaRh
	FROM(
		SELECT
			CXPF_CXPFacturaId AS id,
			'{"id": ' + CAST(Evidencia.ARC_ArchivoId AS varchar) + 
			',"nombreOriginal": "' + Evidencia.ARC_NombreOriginal + 
			'","activo": true,"fechaCreacion": ' + CAST(Evidencia.ARC_FechaCreacion AS varchar) + '
			}' AS evidencia
		FROM CXPFacturas
		INNER JOIN CXPSolicitudesPagosRH ON CPXSPRH_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		LEFT JOIN CXPSolicitudesPagosRHBecariosDocumentos ON CPXSPRHBD_CPXSPRH_CXPSolicitudPagoRhId = CPXSPRH_CXPSolicitudPagoRhId
		LEFT JOIN Archivos Evidencia ON Evidencia.ARC_ArchivoId = CPXSPRHBD_ARC_ArchivoId AND Evidencia.ARC_Activo = 1
	) OCSinAgrupar
	GROUP BY id
) DocumentosRh ON DocumentosRh.cxpFacturaId = CXPF_CXPFacturaId

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

GO