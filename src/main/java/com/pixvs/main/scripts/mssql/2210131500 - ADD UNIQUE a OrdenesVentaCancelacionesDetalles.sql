DELETE FROM OrdenesVentaCancelacionesDetalles
WHERE OVCD_OrdenVentaCancelacionDetalleId IN
(
	SELECT borrarId
	FROM
	(
		SELECT OVCD_OrdenVentaCancelacionDetalleId AS borrarId,
			   CONVERT(BIT, CASE WHEN ROW_NUMBER() OVER(ORDER BY cancelacionId, ovDetalleId, OVCD_OrdenVentaCancelacionDetalleId) % 2 = 0 THEN 1 ELSE 0 END) AS borrar
		FROM OrdenesVentaCancelacionesDetalles
			 INNER JOIN
			 (
				SELECT OVCD_OVC_OrdenVentaCancelacionId AS cancelacionId,
					   OVCD_OVD_OrdenVentaDetalleId AS ovDetalleId
				FROM OrdenesVentaCancelacionesDetalles
				GROUP BY OVCD_OVC_OrdenVentaCancelacionId,
						 OVCD_OVD_OrdenVentaDetalleId
				HAVING COUNT(OVCD_OrdenVentaCancelacionDetalleId) > 1
			 ) AS duplicados ON OVCD_OVC_OrdenVentaCancelacionId = cancelacionId
							   AND OVCD_OVD_OrdenVentaDetalleId = ovDetalleId
	) AS todo
	WHERE borrar = 1
)
GO

ALTER TABLE OrdenesVentaCancelacionesDetalles
ADD CONSTRAINT UC_OVC_OrdenVentaCancelacionId_OVD_OrdenVentaDetalleId UNIQUE(OVCD_OVC_OrdenVentaCancelacionId, OVCD_OVD_OrdenVentaDetalleId)
GO