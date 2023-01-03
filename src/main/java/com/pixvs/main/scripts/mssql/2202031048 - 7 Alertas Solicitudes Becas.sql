INSERT INTO AlertasConfig
	(ALC_Nombre,ALC_Descripcion,ALC_MP_NodoId,ALC_CMM_TipoConfigAlertaId,ALC_CMM_TipoMovimiento,
	ALC_TablaReferencia,ALC_CampoId,ALC_CampoCodigo,ALC_CampoEstado,ALC_CampoEmpCreadoPor,
	ALC_CMM_EstadoAutorizado,ALC_CMM_EstadoEnProceso,ALC_CMM_EstadoRechazado,ALC_CMM_EstadoEnRevision,
	ALC_AplicaSucursales,ALC_FechaCreacion,ALC_USU_CreadoPorId,ALC_CampoController,ALC_LOGP_LogProcesoId)
VALUES
    ('Notificación de solicitudes de becas',
	'Alerta utilizada para notificar que se solicitó una beca',
	(Select Men.MP_NodoId from MenuPrincipal Men where Men.MP_Titulo='Becas' ),
	1000143,
	2000806, --Becas Solicitudes
	'BecasSolicitudes',
	'BECS_BecaSolicitudId',
	'BECS_Codigo',
	'BECS_CMM_EstatusId',
	'BECS_USU_CreadoPorId',
	2000801,
	2000800,
	2000802,
	1000153,
	0,
	'2022-02-03 02:00:00.633',
	1,
	'CMM_BECS_Estatus',
	11)
GO