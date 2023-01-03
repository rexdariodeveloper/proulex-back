UPDATE AlertasConfig SET ALC_CMM_EstadoAutorizado = 2000261, ALC_CMM_EstadoRechazado= 2000269
where ALC_CMM_TipoMovimiento = 1000164
GO
UPDATE AlertasConfig SET ALC_TablaReferencia='CXPSolicitudesPagosServicios', ALC_CampoId='CXPSPS_CXPSolicitudPagoServicioId',ALC_CampoCodigo='CXPSPS_CodigoSolicitudPagoServicio', ALC_CampoController = 'CMM_CXPSPS_EstadoSolicitudPago', ALC_CampoEstado = 'CXPSPS_CMM_EstatusId'
where ALC_CMM_TipoMovimiento = 1000161
GO