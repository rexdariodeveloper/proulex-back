UPDATE cancelacion
  SET
      OVC_ImporteReembolsar = monto
FROM OrdenesVentaCancelaciones AS cancelacion
     INNER JOIN
	 (
		SELECT OVCD_OVC_OrdenVentaCancelacionId,
			   SUM(montos.Total) AS monto
		FROM OrdenesVentaCancelacionesDetalles
			 CROSS APPLY fn_getMontosDetalleOV(OVCD_OVD_OrdenVentaDetalleId) AS montos
		GROUP BY OVCD_OVC_OrdenVentaCancelacionId
	 ) AS detalles ON cancelacion.OVC_OrdenVentaCancelacionId = detalles.OVCD_OVC_OrdenVentaCancelacionId
WHERE cancelacion.OVC_CMM_TipoMovimientoId = 2000081 -- CMM_OVC_TipoMovimiento Cancelación
      AND OVC_ImporteReembolsar = 0