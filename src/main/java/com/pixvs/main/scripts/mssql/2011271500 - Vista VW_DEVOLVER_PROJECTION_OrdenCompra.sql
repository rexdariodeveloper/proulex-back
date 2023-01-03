-- ============================================= 
-- Author:		Angel Daniel Hernández Silva
-- Create date: 2020/11/24
-- Description:	Vista VW_DEVOLVER_PROJECTION_OrdenCompra
-- --------------------------------------------- 

CREATE OR ALTER VIEW [dbo].[VW_DEVOLVER_PROJECTION_OrdenCompra] AS

SELECT
	id,
	codigo,
	proveedorStr,
	fechaRequerida,
	fechaModificacion,
	COALESCE('[' + STRING_AGG('{
		"id": ' + CAST(OCD_OrdenCompraDetalleId AS varchar) + ',
		"articulo": {
			"id": ' + CAST(ART_ArticuloId AS varchar) + ',
			"codigoArticulo": "' + ART_CodigoArticulo + '",
			"nombreArticulo": "' + ART_NombreArticulo + '"
		},
		"unidadMedida": {
			"id": ' + CAST(UM_UnidadMedidaId AS varchar) + ',
			"nombre": "' + UM_Nombre + '",
			"decimales": ' + CAST(COALESCE(UM_Decimales,0) AS varchar) + '
		},
		"": ' + CAST(OCD_Cantidad AS varchar) + ',
		"recibosPendientes": ' + recibosPendientes + '
	}',',') + ']','[]') AS detallesStr
FROM(
	SELECT
		id,
		codigo,
		proveedorStr,
		fechaRequerida,
		fechaModificacion,
		detalleId,
		COALESCE('[' + STRING_AGG('{
			"id": ' + CAST(OCR_OrdenCompraReciboId AS varchar) + ',
			"ordenCompraDetalleId": ' + CAST(OCR_OCD_OrdenCompraDetalleId AS varchar) + ',
			"cantidadRecibo": ' + CAST(OCR_CantidadRecibo AS varchar) + ',
			"fechaRecibo": "' + CAST(OCR_FechaRecibo AS varchar) + '",
			"localidadId": ' + CAST(OCR_LOC_LocalidadId AS varchar) + ',
			"creadoPor": {
				"id": ' + CAST(USU_UsuarioId AS varchar) + ',
				"nombre": "' + USU_Nombre + '",
				"primerApellido": "' + USU_PrimerApellido + '",
				"segundoApellido": "' + COALESCE(USU_SegundoApellido,'') + '",
				"estatusId": ' + CAST(USU_CMM_EstatusId AS varchar) + ',
				"nombreCompleto": "' + USU_Nombre + ' ' + USU_PrimerApellido + COALESCE(' ' + USU_SegundoApellido,'') + '"
			},
			"articulo": {
				"id": ' + CAST(ART_ArticuloId AS varchar) + ',
				"codigoArticulo": "' + ART_CodigoArticulo + '",
				"nombreArticulo": "' + ART_NombreArticulo + '"
			},
			"unidadMedida": {
				"id": ' + CAST(UM_UnidadMedidaId AS varchar) + ',
				"nombre": "' + UM_Nombre + '",
				"decimales": ' + CAST(COALESCE(UM_Decimales,0) AS varchar) + '
			},
			"cantidadRequerida": ' + CAST(OCD_Cantidad AS varchar) + ',
			"almacen": {
				"id": ' + CAST(ALM_AlmacenId AS varchar) + ',
				"codigoAlmacen": "' + ALM_CodigoAlmacen + '",
				"nombre": "' + ALM_Nombre + '",
				"sucursal": {
					"id": ' + CAST(SUC_SucursalId AS varchar) + ',
					"nombre": "' + SUC_Nombre + '",
				}
			},
			"cantidadPendienteDevolver": ' + CAST((OCR_CantidadRecibo - totalDevolver - totalCxpFacturasDetalles) AS varchar) + ',
			"devoluciones": ' + devoluciones + ',
			"cxpFacturasDetalles": ' + cxpFacturasDetalles + ',
			"ordenCompra": null,
			"evidencia": ' +  evidencia + '
		}',',') + ']','[]') AS recibosPendientes
	FROM(
		SELECT
			id,
			codigo,
			proveedorStr,
			fechaRequerida,
			fechaModificacion,
			detalleId,
			reciboId,
			devoluciones,
			COALESCE(totalDevolver,0) AS totalDevolver,
			cxpFacturasDetalles,
			COALESCE(totalCxpFacturasDetalles,0) AS totalCxpFacturasDetalles,
			COALESCE('[' + STRING_AGG('{
				"id": ' + CAST(ARC_ArchivoId AS varchar) + ',
				"nombreOriginal": "' + ARC_NombreOriginal + '",
				"tipo": null,
				"nombreFisico": null,
				"rutaFisica": null,
				"publico": null,
				"activo": true,
				"creadoPor": null,
				"fechaCreacion": ' + CAST(ARC_FechaCreacion AS varchar) + '
			}',',') + ']','[]') AS evidencia
		FROM(
			SELECT
				id,
				codigo,
				proveedorStr,
				fechaRequerida,
				fechaModificacion,
				detalleId,
				reciboId,
				devoluciones,
				totalDevolver,
				COALESCE('[' + STRING_AGG('{
					"id": ' + CAST(CXPFD_CXPFacturadetalleId AS varchar) + ',
					"cantidad": ' + CAST(CXPFD_Cantidad AS varchar) + '
				}',',') + ']','[]') AS cxpFacturasDetalles,
				SUM(CXPFD_Cantidad) AS totalCxpFacturasDetalles
			FROM(
				SELECT
					OC_OrdenCompraId AS id,
					OC_Codigo AS codigo,
					'{
						"id": ' + CAST(PRO_ProveedorId AS varchar) + ',
						"codigo": "' + PRO_Codigo + '",
						"nombre": "' + PRO_Nombre + '",
						"rfc": "' + PRO_RFC + '",
						"formasPago": []
					}' AS proveedorStr,
					OC_FechaRequerida AS fechaRequerida,
					OC_FechaModificacion AS fechaModificacion,
					OCD_OrdenCompraDetalleId AS detalleId,
					Recibos.OCR_OrdenCompraReciboId AS reciboId,
					COALESCE('[' + STRING_AGG('{
						"id": ' + CAST(Devoluciones.OCR_OrdenCompraReciboId AS varchar) + ',
						"cantidadRecibo": ' + CAST(Devoluciones.OCR_CantidadRecibo AS varchar) + '
					}',',') + ']','[]') AS devoluciones,
					SUM(Devoluciones.OCR_CantidadRecibo) AS totalDevolver
				FROM OrdenesCompra
				INNER JOIN Proveedores ON PRO_ProveedorId = OC_PRO_ProveedorId
				INNER JOIN OrdenesCompraDetalles ON OCD_OC_OrdenCompraId = OC_OrdenCompraId
				LEFT JOIN OrdenesCompraRecibos Recibos ON Recibos.OCR_OCD_OrdenCompraDetalleId = OCD_OrdenCompraDetalleId AND Recibos.OCR_CantidadRecibo >= 0 AND Recibos.OCR_OCR_ReciboReferenciaId IS NULL
				LEFT JOIN OrdenesCompraRecibos Devoluciones ON Devoluciones.OCR_OCR_ReciboReferenciaId = Recibos.OCR_OrdenCompraReciboId
				GROUP BY OC_OrdenCompraId, OC_Codigo, PRO_ProveedorId, PRO_Codigo, PRO_Nombre, PRO_RFC, OC_FechaRequerida, OC_FechaModificacion, OCD_OrdenCompraDetalleId, Recibos.OCR_OrdenCompraReciboId
			) OCDevolucionesAgrupado
			LEFT JOIN CXPFacturasDetalles ON CXPFD_OCR_OrdenCompraReciboId = reciboId
			GROUP BY id, codigo, proveedorStr, fechaRequerida, fechaModificacion, detalleId, reciboId, devoluciones, totalDevolver
		) OCCXPFacturaDetallesAgrupado
		LEFT JOIN OrdenesCompraRecibosEvidencia ON OCRE_OCR_OrdenCompraReciboId = reciboId
		LEFT JOIN Archivos ON OCRE_ARC_ArchivoId = ARC_ArchivoId
		GROUP BY id, codigo, proveedorStr, fechaRequerida, fechaModificacion, detalleId, reciboId, devoluciones, totalDevolver, cxpFacturasDetalles, totalCxpFacturasDetalles
	) OCEvidenciaAgrupado
	LEFT JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = reciboId
	LEFT JOIN Usuarios ON USU_UsuarioId = OCR_USU_CreadoPorId
	LEFT JOIN OrdenesCompraDetalles ON OCD_OrdenCompraDetalleId = OCR_OCD_OrdenCompraDetalleId
	LEFT JOIN Articulos ON ART_ArticuloId = OCD_ART_ArticuloId
	LEFT JOIN UnidadesMedidas ON UM_UnidadMedidaId = OCD_UM_UnidadMedidaId
	LEFT JOIN Localidades ON LOC_LocalidadId = OCR_LOC_LocalidadId
	LEFT JOIN Almacenes ON ALM_AlmacenId = LOC_ALM_AlmacenId
	LEFT JOIN Sucursales ON SUC_SucursalId = ALM_SUC_SucursalId
	GROUP BY id, codigo, proveedorStr, fechaRequerida, fechaModificacion, detalleId
) AS OCRecibosAgrupado
INNER JOIN OrdenesCompraDetalles ON OCD_OrdenCompraDetalleId = detalleId
INNER JOIN Articulos ON ART_ArticuloId = OCD_ART_ArticuloId
INNER JOIN UnidadesMedidas ON UM_UnidadMedidaId = OCD_UM_UnidadMedidaId
GROUP BY id, codigo, proveedorStr, fechaRequerida, fechaModificacion

GO