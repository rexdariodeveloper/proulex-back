SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_LISTADO_PROJECTION_OC] AS

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
	SUC_Nombre as sedeNombre,
	cantidad,
	COALESCE(evidencia,'[]') AS evidenciaStr,
	COALESCE(facturas,'[]') AS facturasStr
FROM OrdenesCompra
LEFT JOIN Proveedores ON PRO_ProveedorId = OC_PRO_ProveedorId
LEFT JOIN ControlesMaestrosMultiples ON CMM_ControlId = OC_CMM_EstatusId
LEFT JOIN Almacenes on ALM_AlmacenId = OC_ALM_RecepcionArticulosAlmacenId
LEFT JOIN Sucursales on ALM_SUC_SucursalId = SUC_SucursalId
INNER JOIN(
	SELECT
		OC_OrdenCompraId AS id,
		SUM(OCD_Cantidad) AS cantidad
	FROM OrdenesCompra
	INNER JOIN OrdenesCompraDetalles ON OCD_OC_OrdenCompraId = OC_OrdenCompraId
	GROUP BY OC_OrdenCompraId
) OCCantidad ON OC_OrdenCompraId = OCCantidad.id
INNER JOIN(
	SELECT
		id,
		'[' + STRING_AGG(facturas,',') + ']' AS facturas,
		evidencia
	FROM(
		SELECT
			id,
			facturas,
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
			LEFT JOIN CXPFacturasDetalles ON CXPFD_OCR_OrdenCompraReciboId = OCR_OrdenCompraReciboId
			LEFT JOIN OrdenesCompraRecibosEvidencia ON OCRE_OCR_OrdenCompraReciboId = OCR_OrdenCompraReciboId
			LEFT JOIN Archivos FacturaPDF ON FacturaPDF.ARC_ArchivoId = OCR_ARC_FacturaPDF AND FacturaPDF.ARC_Activo = 1
			LEFT JOIN Archivos FacturaXML ON FacturaXML.ARC_ArchivoId = OCR_ARC_FacturaXML AND FacturaXML.ARC_Activo = 1
			LEFT JOIN Archivos Evidencia ON Evidencia.ARC_ArchivoId = OCRE_ARC_ArchivoId AND Evidencia.ARC_Activo = 1
			WHERE
				CXPFD_CXPF_CXPFacturaId IS NULL
			GROUP BY OC_OrdenCompraId,
				FacturaPDF.ARC_ArchivoId, FacturaPDF.ARC_NombreOriginal, FacturaPDF.ARC_FechaCreacion,
				FacturaXML.ARC_ArchivoId, FacturaXML.ARC_NombreOriginal, FacturaXML.ARC_FechaCreacion,
				Evidencia.ARC_ArchivoId, Evidencia.ARC_NombreOriginal, Evidencia.ARC_FechaCreacion
		) OCSinAgrupar
		GROUP BY id, facturas
	) OCSinAgrupar
	GROUP BY id, evidencia
) OCArchivos ON OC_OrdenCompraId = OCArchivos.id

GO