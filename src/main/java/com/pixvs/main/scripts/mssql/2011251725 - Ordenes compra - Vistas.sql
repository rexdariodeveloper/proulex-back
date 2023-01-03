
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_PROJECTION_OC] AS

SELECT
	OC_OrdenCompraId AS id,
	OC_Codigo AS codigo,
	PRO_Nombre AS proveedorNombre,
	PRO_RFC AS proveedorRfc,
	OC_FechaOC AS fechaOC,
	OC_FechaRequerida AS fechaRequerida,
	OC_FechaModificacion AS fechaModificacion,
	OC_CMM_EstatusId AS estatusId,
	CMM_Valor AS estatusValor,
	OC_ALM_RecepcionArticulosAlmacenId AS recepcionArticulosAlmacenId,
	
	cantidad,
	COALESCE(facturas,'[]') AS evidenciaStr,
	COALESCE(evidencia,'[]') AS facturasStr
FROM OrdenesCompra
INNER JOIN Proveedores ON PRO_ProveedorId = OC_PRO_ProveedorId
INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = OC_CMM_EstatusId
INNER JOIN(
	SELECT
		OC_OrdenCompraId AS id,
		SUM(OCD_Cantidad) AS cantidad
	FROM OrdenesCompra
	INNER JOIN OrdenesCompraDetalles ON OCD_OC_OrdenCompraId = OC_OrdenCompraId
	GROUP BY OC_OrdenCompraId
) OCCantidad ON OC_OrdenCompraId = OCCantidad.id
LEFT JOIN(
	SELECT
		id,
		'[' + STRING_AGG(facturas,',') + ']' AS facturas,
		'[' + STRING_AGG(evidencia,',') + ']' AS evidencia
	FROM(
		SELECT
			OC_OrdenCompraId AS id,
			COALESCE('{
				"id": ' + CAST(FacturaPDF.ARC_ArchivoId AS varchar) + ',
				"nombreOriginal": "' + FacturaPDF.ARC_NombreOriginal + '",
				"tipo": null,
				"nombreFisico": null,
				"rutaFisica": null,
				"publico": null,
				"activo": true,
				"creadoPor": null,
				"fechaCreacion": ' + CAST(FacturaPDF.ARC_FechaCreacion AS varchar) + '
			}' + COALESCE(',{
				"id": ' + CAST(FacturaXML.ARC_ArchivoId AS varchar) + ',
				"nombreOriginal": "' + FacturaXML.ARC_NombreOriginal + '",
				"tipo": null,
				"nombreFisico": null,
				"rutaFisica": null,
				"publico": null,
				"activo": true,
				"creadoPor": null,
				"fechaCreacion": ' + CAST(FacturaXML.ARC_FechaCreacion AS varchar) + '
			}',''),'') AS facturas,
			'{
				"id": ' + CAST(Evidencia.ARC_ArchivoId AS varchar) + ',
				"nombreOriginal": "' + Evidencia.ARC_NombreOriginal + '",
				"tipo": null,
				"nombreFisico": null,
				"rutaFisica": null,
				"publico": null,
				"activo": true,
				"creadoPor": null,
				"fechaCreacion": ' + CAST(Evidencia.ARC_FechaCreacion AS varchar) + '
			}' AS evidencia
		FROM OrdenesCompra
		INNER JOIN OrdenesCompraDetalles ON OCD_OC_OrdenCompraId = OC_OrdenCompraId
		LEFT JOIN OrdenesCompraRecibos ON OCR_OCD_OrdenCompraDetalleId = OCD_OrdenCompraDetalleId
		LEFT JOIN OrdenesCompraRecibosEvidencia ON OCRE_OCR_OrdenCompraReciboId = OCR_OrdenCompraReciboId
		LEFT JOIN Archivos FacturaPDF ON FacturaPDF.ARC_ArchivoId = OCR_ARC_FacturaPDF AND FacturaPDF.ARC_Activo = 1
		LEFT JOIN Archivos FacturaXML ON FacturaXML.ARC_ArchivoId = OCR_ARC_FacturaXML AND FacturaXML.ARC_Activo = 1
		LEFT JOIN Archivos Evidencia ON Evidencia.ARC_ArchivoId = OCRE_ARC_ArchivoId AND Evidencia.ARC_Activo = 1
	) OCSinAgrupar
	GROUP BY id
) OCArchivos ON OC_OrdenCompraId = OCArchivos.id

GO
















CREATE OR ALTER VIEW [dbo].[VW_LISTADO_PROJECTION_CXPFactura] AS

SELECT
	CXPF_CXPFacturaId AS id,
	PRO_Nombre AS proveedorNombre,
	PRO_RFC AS proveedorRfc,
	CXPF_CodigoRegistro AS codigoRegistro,
	CXPF_MontoRegistro AS montoRegistro,
	CXPF_FechaRegistro AS fechaRegistro,
	NULL AS fechaVencimiento,
	CASE COALESCE(totalOCs,0) WHEN 1 THEN codigoOC WHEN 0 THEN '-' ELSE 'Múltiple' END AS ordenCompraTexto,
	CXPF_FechaReciboRegistro AS fechaReciboRegistro,
	CAST(1 AS bit) AS relacionada,
	COALESCE(evidencia,'[]') AS evidenciaStr,

	CXPF_CMM_EstatusId AS estatusId,
	PRO_ProveedorId AS proveedorId
FROM CXPFacturas
INNER JOIN Proveedores ON PRO_ProveedorId = CXPF_PRO_ProveedorId
LEFT JOIN (
	SELECT
		id,
		COUNT(*) AS totalOCs,
		STRING_AGG(codigoOC,',') AS codigoOC
	FROM(
		SELECT
			CXPF_CXPFacturaId AS id,
			OC_Codigo AS codigoOC
		FROM CXPFacturas
		INNER JOIN CXPFacturasDetalles ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		INNER JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId
		INNER JOIN OrdenesCompra ON OC_OrdenCompraId = OCR_OC_OrdenCompraId
		GROUP BY CXPF_CXPFacturaId, OC_Codigo
	) CXPFOCCodigo
	GROUP BY id
) CXPFOC ON CXPFOC.id = CXPF_CXPFacturaId
LEFT JOIN(

	SELECT
		id,
		'[' + STRING_AGG(evidencia,',') + ']' AS evidencia
	FROM(
		SELECT
			CXPF_CXPFacturaId AS id,
			'{
				"id": ' + CAST(Evidencia.ARC_ArchivoId AS varchar) + ',
				"nombreOriginal": "' + Evidencia.ARC_NombreOriginal + '",
				"tipo": null,
				"nombreFisico": null,
				"rutaFisica": null,
				"publico": null,
				"activo": true,
				"creadoPor": null,
				"fechaCreacion": ' + CAST(Evidencia.ARC_FechaCreacion AS varchar) + '
			}' AS evidencia
		FROM CXPFacturas
		INNER JOIN CXPFacturasDetalles ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		INNER JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId
		LEFT JOIN OrdenesCompraRecibosEvidencia ON OCRE_OCR_OrdenCompraReciboId = OCR_OrdenCompraReciboId
		LEFT JOIN Archivos Evidencia ON Evidencia.ARC_ArchivoId = OCRE_ARC_ArchivoId AND Evidencia.ARC_Activo = 1
	) OCSinAgrupar
	GROUP BY id
) CXPFEvidencia ON CXPF_CXPFacturaId = CXPFEvidencia.id

GO




















CREATE OR ALTER VIEW [dbo].[VW_CXPFacturaListado_PROJECTION_OrdenesCompraRecibos] AS

SELECT
	OCR_OrdenCompraReciboId AS id,
	PRO_Nombre AS proveedorNombre,
	PRO_RFC AS proveedorRfc,
	NULL AS codigoRegistro,
	NULL AS montoRegistro,
	NULL AS fechaRegistro,
	NULL AS fechaVencimiento,
	OC_Codigo AS ordenCompraTexto,
	OCR_FechaRecibo AS fechaReciboRegistro,
	CAST(0 AS bit) AS relacionada,
	'[]' AS evidenciaStr
FROM OrdenesCompraRecibos
INNER JOIN OrdenesCompra ON OC_OrdenCompraId = OCR_OC_OrdenCompraId
INNER JOIN Proveedores ON PRO_ProveedorId = OC_PRO_ProveedorId

GO