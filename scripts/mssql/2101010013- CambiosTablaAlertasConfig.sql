ALTER TABLE AlertasConfig ADD ALC_CampoController nvarchar(100) null
GO
ALTER TABLE AlertasConfigEtapa ADD ACE_NotificarCreador bit null
GO
UPDATE AlertasConfig SET ALC_CampoController = 'CMM_OC_EstatusOC', ALC_CampoEstado = 'OC_CMM_EstatusId'
where ALC_CMM_TipoMovimiento = 1000163
GO
UPDATE AlertasConfig SET ALC_CampoController = 'CMM_REQ_EstatusRequisicion', ALC_CampoEstado = 'REQ_CMM_EstadoRequisicionId'
where ALC_CMM_TipoMovimiento = 1000162
GO
UPDATE AlertasConfig SET ALC_CampoController = 'CMM_CXPSPS_EstadoSolicitudPago', ALC_CampoEstado = 'CXPSPS_CMM_EstatusId'
where ALC_CMM_TipoMovimiento = 1000161
GO
UPDATE AlertasConfig SET ALC_CampoController = 'CMM_PED_EstatusPedido', ALC_CampoEstado = 'PED_CMM_EstatusId'
where ALC_CMM_TipoMovimiento = 1000164
GO
SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO [dbo].[ControlesMaestrosMultiples]
		   ([CMM_ControlId]
           ,[CMM_Control]
           ,[CMM_Valor]
           ,[CMM_Activo]
           ,[CMM_Sistema]
           ,[CMM_FechaCreacion]
           )
     VALUES
		   (1000166,'CMM_CALC_TipoMovimiento','Pagos',1,1,GETDATE())		   
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO
INSERT INTO [dbo].[AlertasConfig]
           ([ALC_Nombre]
           ,[ALC_Descripcion]
           ,[ALC_MP_NodoId]
           ,[ALC_CMM_TipoConfigAlertaId]
           ,[ALC_CMM_TipoMovimiento]
           ,[ALC_TablaReferencia]
           ,[ALC_CampoId]
           ,[ALC_CampoCodigo]
           ,[ALC_CampoEstado]
           ,[ALC_CampoEmpCreadoPor]
           ,[ALC_CampoEmpAutorizadoPor]
           ,[ALC_CampoFechaAutorizacion]
           ,[ALC_CMM_EstadoAutorizado]
           ,[ALC_CMM_EstadoEnProceso]
           ,[ALC_CMM_EstadoRechazado]
           ,[ALC_CMM_EstadoEnRevision]
           ,[ALC_AplicaSucursales]
           ,[ALC_FechaCreacion]
           ,[ALC_USU_CreadoPorId]
           ,[ALC_FechaUltimaModificacion]
           ,[ALC_USU_ModificadoPorId]
		   ,[ALC_CampoController])
     VALUES
           ('Validación de pagos','Alerta utilizada al validar una solicitud de pago',(select MP_NodoId from MenuPrincipal where  MP_Titulo = 'Programación de pagos' and MP_Orden =5),1000143,1000166,'CXPSolicitudesPagos','CXPS_CXPSolicitudPagoId','CXPS_CodigoSolicitud','CXPS_CMM_EstatusId','CXPS_USU_CreadoPorId',null,null,1000154,1000151,1000152,1000153,1,GETDATE(),1,null,null,'CMM_CXPS_EstadoSolicitudPago')
GO