SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO

INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_USU_CreadoPorId],
	[CMM_FechaCreacion],
	[CMM_FechaModificacion],
	[CMM_USU_ModificadoPorId],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 2000601,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CALC_TipoMovimiento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Solicitud alumno multisede'
), (
	/* [CMM_ControlId] */ 2000602,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_INS_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Autorizado'
), (
	/* [CMM_ControlId] */ 2000603,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_INS_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Proceso'
), (
	/* [CMM_ControlId] */ 2000604,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_INS_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Rechazado'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

INSERT INTO AlertasConfig
	(ALC_Nombre,ALC_Descripcion,ALC_MP_NodoId,ALC_CMM_TipoConfigAlertaId,ALC_CMM_TipoMovimiento,
	ALC_TablaReferencia,ALC_CampoId,ALC_CampoCodigo,ALC_CampoEstado,ALC_CampoEmpCreadoPor,
	ALC_CMM_EstadoAutorizado,ALC_CMM_EstadoEnProceso,ALC_CMM_EstadoRechazado,ALC_CMM_EstadoEnRevision,
	ALC_AplicaSucursales,ALC_FechaCreacion,ALC_USU_CreadoPorId,ALC_CampoController,ALC_LOGP_LogProcesoId)
VALUES
    ('Notificación de cambio de alumno a multisede',
	'Alerta utilizada para notificar que un alumno fue cambiado a un grupo multisede',
	(Select Men.MP_NodoId from MenuPrincipal Men where Men.MP_Titulo='Grupos' ),
	1000143,
	2000601, --Falta de agregar
	'Inscripciones',
	'INS_InscripcionId',
	'INS_Codigo',
	'INS_CMM_EstatusId',
	'INS_USU_CreadoPorId',
	2000602,
	2000603,
	2000604,
	1000153,
	1,
	'2021-09-06 17:40:00.633',
	1,
	'CMM_INS_Estatus',
	10)
GO