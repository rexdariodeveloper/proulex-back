UPDATE ControlesMaestrosMultiples SET CMM_Valor = 'Aceptada' WHERE CMM_ControlId = 2000281
GO

UPDATE ControlesMaestrosMultiples SET CMM_Valor = 'Pagada' WHERE CMM_ControlId = 2000282
GO

SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
	(CMM_ControlId, CMM_Control, CMM_Valor, CMM_Activo, CMM_Referencia, CMM_Sistema, CMM_USU_CreadoPorId, CMM_FechaCreacion, CMM_USU_ModificadoPorId, CMM_FechaModificacion)
VALUES
	(2000285, 'CMM_CXPSPS_EstadoSolicitudPago', 'Por autorizar', 1, NULL, 1, NULL, GETDATE(), NULL, NULL),
	(2000286, 'CMM_CXPSPS_EstadoSolicitudPago', 'Rechazada', 1, NULL, 1, NULL, GETDATE(), NULL, NULL)
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO


UPDATE AlertasConfig SET
	ALC_CMM_EstadoAutorizado = 2000281,
	ALC_CMM_EstadoEnProceso = 2000285,
	ALC_CMM_EstadoRechazado = 2000286
WHERE ALC_AlertaCId = 7
GO


UPDATE CXPSolicitudesPagosServicios SET
	CXPSPS_CMM_EstatusId = 2000281
WHERE CXPSPS_CMM_EstatusId = 2000161
GO

UPDATE CXPSolicitudesPagosServicios SET
	CXPSPS_CMM_EstatusId = 2000282
WHERE CXPSPS_CMM_EstatusId = 2000162
GO

UPDATE CXPSolicitudesPagosServicios SET
	CXPSPS_CMM_EstatusId = 2000283
WHERE CXPSPS_CMM_EstatusId = 2000163
GO

UPDATE CXPSolicitudesPagosServicios SET
	CXPSPS_CMM_EstatusId = 2000284
WHERE CXPSPS_CMM_EstatusId = 2000164
GO

UPDATE CXPSolicitudesPagosServicios SET
	CXPSPS_CMM_EstatusId = 2000285
WHERE CXPSPS_CMM_EstatusId = 2000165
GO

UPDATE CXPSolicitudesPagosServicios SET
	CXPSPS_CMM_EstatusId = 2000286
WHERE CXPSPS_CMM_EstatusId = 2000166
GO 



UPDATE CXPSolicitudesPagosServiciosDetalles SET
	CXPSPSD_CMM_EstatusId = 2000281
WHERE CXPSPSD_CMM_EstatusId = 2000161
GO

UPDATE CXPSolicitudesPagosServiciosDetalles SET
	CXPSPSD_CMM_EstatusId = 2000281
WHERE CXPSPSD_CMM_EstatusId = 2000181
GO

UPDATE CXPSolicitudesPagosServiciosDetalles SET
	CXPSPSD_CMM_EstatusId = 2000282
WHERE CXPSPSD_CMM_EstatusId = 2000162
GO

UPDATE CXPSolicitudesPagosServiciosDetalles SET
	CXPSPSD_CMM_EstatusId = 2000283
WHERE CXPSPSD_CMM_EstatusId = 2000163
GO

UPDATE CXPSolicitudesPagosServiciosDetalles SET
	CXPSPSD_CMM_EstatusId = 2000284
WHERE CXPSPSD_CMM_EstatusId = 2000164
GO

UPDATE CXPSolicitudesPagosServiciosDetalles SET
	CXPSPSD_CMM_EstatusId = 2000285
WHERE CXPSPSD_CMM_EstatusId = 2000165
GO

UPDATE CXPSolicitudesPagosServiciosDetalles SET
	CXPSPSD_CMM_EstatusId = 2000286
WHERE CXPSPSD_CMM_EstatusId = 2000166
GO 