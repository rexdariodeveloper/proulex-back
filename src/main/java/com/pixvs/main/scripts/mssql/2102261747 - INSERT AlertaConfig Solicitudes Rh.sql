INSERT INTO AlertasConfig
	(ALC_Nombre,ALC_Descripcion,ALC_MP_NodoId,ALC_CMM_TipoConfigAlertaId,ALC_CMM_TipoMovimiento,
	ALC_TablaReferencia,ALC_CampoId,ALC_CampoCodigo,ALC_CampoEstado,ALC_CampoEmpCreadoPor,
	ALC_CMM_EstadoAutorizado,ALC_CMM_EstadoEnProceso,ALC_CMM_EstadoRechazado,ALC_CMM_EstadoEnRevision,
	ALC_AplicaSucursales,ALC_FechaCreacion,ALC_USU_CreadoPorId,ALC_CampoController,ALC_LOGP_LogProcesoId)
VALUES
    ('Validación de solicitudes de pago rh',
	'Alerta utilizada al validar una solicitud de pago rh',
	(Select Men.MP_NodoId from MenuPrincipal Men where Men.MP_Titulo='Solicitudes de pago rh' ),
	1000143,
	1000167,
	'CXPSolicitudesPagosRH',
	'CPXSPRH_CXPSolicitudPagoRhId',
	'CPXSPRH_Codigo',
	'CPXSPRH_CMM_EstatusId',
	'CPXSPRH_USU_CreadoPorId',
	2000350,
	2000354,
	2000355,
	1000153,
	1,
	'2021-02-17 17:40:00.633',
	1,
	'CMM_CPXSPRH_EstatusId',
	4)
GO


