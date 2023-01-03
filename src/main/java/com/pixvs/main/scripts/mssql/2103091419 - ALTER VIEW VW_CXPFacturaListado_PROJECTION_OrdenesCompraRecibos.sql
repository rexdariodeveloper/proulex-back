/*
 * Agregar campo de SEDE a listado
 */
CREATE OR ALTER VIEW [dbo].[VW_CXPFacturaListado_PROJECTION_OrdenesCompraRecibos] AS

	SELECT
		MAX(id) AS id,
		proveedorId,
		proveedorNombre,
		proveedorRfc,
		NULL AS codigoRegistro,
		NULL AS montoRegistro,
		NULL AS fechaRegistro,
		NULL AS fechaVencimiento,
		ordenCompraTexto,
		sedeNombre,
		codigoRecibo,
		MAX(fechaReciboRegistro) AS fechaReciboRegistro,
		CAST(0 AS bit) AS relacionada,
		'[]' AS evidenciaStr
	FROM(
		SELECT
		id,
		proveedorId,
		proveedorNombre,
		proveedorRfc,
		ordenCompraTexto,
		sedeNombre,
		codigoRecibo,
		fechaReciboRegistro
		FROM(
			SELECT
				Recibos.OCR_OrdenCompraReciboId AS id,
				PRO_ProveedorId AS proveedorId,
				PRO_Nombre AS proveedorNombre,
				PRO_RFC AS proveedorRfc,
				OC_Codigo AS ordenCompraTexto,
				SUC_Nombre AS sedeNombre,
				Recibos.OCR_CodigoRecibo AS codigoRecibo,
				Recibos.OCR_FechaRecibo AS fechaReciboRegistro,
				Recibos.OCR_CantidadRecibo + COALESCE(SUM(Devoluciones.OCR_CantidadRecibo),0) AS cantidadRecibida
			FROM OrdenesCompraRecibos Recibos
				INNER JOIN OrdenesCompra ON OC_OrdenCompraId = Recibos.OCR_OC_OrdenCompraId
				INNER JOIN Almacenes ON ALM_AlmacenId = OC_ALM_RecepcionArticulosAlmacenId
				INNER JOIN Sucursales ON ALM_SUC_SucursalId = SUC_SucursalId
				INNER JOIN Proveedores ON PRO_ProveedorId = OC_PRO_ProveedorId
				LEFT JOIN OrdenesCompraRecibos Devoluciones ON Devoluciones.OCR_OCR_ReciboReferenciaId = Recibos.OCR_OrdenCompraReciboId
			WHERE Recibos.OCR_OCR_ReciboReferenciaId IS NULL AND Recibos.OCR_CantidadRecibo > 0
			GROUP BY Recibos.OCR_OrdenCompraReciboId, PRO_ProveedorId, PRO_Nombre, PRO_RFC, OC_Codigo, SUC_Nombre, Recibos.OCR_CodigoRecibo, Recibos.OCR_FechaRecibo, Recibos.OCR_CantidadRecibo
		) Recibos
		LEFT JOIN CXPFacturasDetalles ON CXPFD_OCR_OrdenCompraReciboId = id
		LEFT JOIN CXPFacturas ON CXPF_CXPFacturaId = CXPFD_CXPF_CXPFacturaId
		GROUP BY id, proveedorId, proveedorNombre, proveedorRfc, ordenCompraTexto, SedeNombre, codigoRecibo, fechaReciboRegistro, cantidadRecibida
		HAVING (cantidadRecibida - COALESCE(SUM(CASE WHEN COALESCE(CXPF_CMM_EstatusId,2000111) = 2000111 THEN NULL ELSE CXPFD_Cantidad END),0)) > 0
	) Recibos
	GROUP BY proveedorId, proveedorNombre, proveedorRfc, ordenCompraTexto, SedeNombre, codigoRecibo

GO