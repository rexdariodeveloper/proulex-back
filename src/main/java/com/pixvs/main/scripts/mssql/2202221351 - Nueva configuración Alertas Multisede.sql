/* Remover la configuración anterior */
DELETE FROM AlertasConfig WHERE ALC_AlertaCId = 12
GO
/* Remover los CMMs antiguos */
DELETE FROM ControlesMaestrosMultiples WHERE CMM_ControlId IN (2000601,2000602,2000603,2000604)
GO

/* Insertar el tipo de movimiento */
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
	/* [CMM_ControlId] */ 1000168,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CALC_TipoMovimiento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Solicitud alumno multisede'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

/* Insertar la nueva configuración */
SET IDENTITY_INSERT [dbo].[AlertasConfig] ON
GO

INSERT INTO AlertasConfig(
	[ALC_AlertaCId],
	[ALC_Nombre],
	[ALC_Descripcion],
	[ALC_MP_NodoId],
	[ALC_CMM_TipoConfigAlertaId],
	[ALC_CMM_TipoMovimiento],
	[ALC_TablaReferencia],
	[ALC_CampoId],
	[ALC_CampoCodigo],
	[ALC_CampoEstado],
	[ALC_CampoEmpCreadoPor],
	[ALC_CMM_EstadoAutorizado],
	[ALC_CMM_EstadoEnProceso],
	[ALC_CMM_EstadoRechazado],
	[ALC_CMM_EstadoEnRevision],
	[ALC_AplicaSucursales], 
	[ALC_FechaCreacion],
	[ALC_USU_CreadoPorId],
	[ALC_CampoController],
	[ALC_LOGP_LogProcesoId],
	[ALC_SPFinal]
)
VALUES
(
	/*[ALC_AlertaCId]*/ 12,
	/*[ALC_Nombre]*/ N'Inscripciones multisede',
	/*[ALC_Descripcion]*/ N'Notifica un proceso multisede desde el punto de venta o cambio de grupo',
	/*[ALC_MP_NodoId]*/ (SELECT mp.MP_NodoId FROM MenuPrincipal mp WHERE mp.MP_Titulo = N'Grupos'),
	/*[ALC_CMM_TipoConfigAlertaId]*/ 1000143,
	/*[ALC_CMM_TipoMovimiento]*/ 1000168,
	/*[ALC_TablaReferencia]*/ N'Inscripciones',
	/*[ALC_CampoId]*/ N'INS_InscripcionId',
	/*[ALC_CampoCodigo]*/ N'INS_Codigo',
	/*[ALC_CampoEstado]*/ N'INS_CMM_EstatusId',
	/*[ALC_CampoEmpCreadoPor]*/ N'INS_USU_CreadoPorId',
	/*[ALC_CMM_EstadoAutorizado]*/ 2000510,
	/*[ALC_CMM_EstadoEnProceso]*/ 2000510,
	/*[ALC_CMM_EstadoRechazado]*/ 2000512,
	/*[ALC_CMM_EstadoEnRevision]*/ 2000512,
	/*[ALC_AplicaSucursales]*/ 1, 
	/*[ALC_FechaCreacion]*/ GETDATE(),
	/*[ALC_USU_CreadoPorId]*/ 1,
	/*[ALC_CampoController]*/ 'CMM_INS_Estatus',
	/*[ALC_LOGP_LogProcesoId]*/ NULL,
	/*[ALC_SPFinal]*/ N'sp_InscripcionesMultisedePostAlerta'
)
GO

SET IDENTITY_INSERT [dbo].[AlertasConfig] OFF
GO