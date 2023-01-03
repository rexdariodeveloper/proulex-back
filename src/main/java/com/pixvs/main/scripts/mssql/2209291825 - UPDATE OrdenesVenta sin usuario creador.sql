UPDATE OrdenesVenta
SET OV_USU_CreadoPorId = OV_USU_ModificadoPorId
WHERE
	OV_USU_CreadoPorId IS NULL
	AND OV_CMM_EstatusId IN (2000508,2000507)
GO

UPDATE Inscripciones
	SET INS_CMM_InscripcionOrigenId = 2000981
FROM Inscripciones
INNER JOIN OrdenesVentaDetalles ON OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
INNER JOIN OrdenesVenta ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
WHERE
	OV_USU_CreadoPorId IS NULL
	AND OV_CMM_EstatusId IN (2000502)
	AND INS_CMM_InscripcionOrigenId IS NULL
GO