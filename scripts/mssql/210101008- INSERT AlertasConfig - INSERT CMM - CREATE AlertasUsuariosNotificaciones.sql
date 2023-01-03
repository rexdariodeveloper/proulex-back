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
           ,[ALC_USU_ModificadoPorId])
     VALUES
           ('Validación de solicitudes de pago','Alerta utilizada al validar una solicitud de pago',(select MP_NodoId from MenuPrincipal where  MP_Titulo = 'Solicitudes de pago' and MP_Orden =7),1000143,1000161,'CXPSolicitudesPagos','CXPS_CXPSolicitudPagoId','CXPS_CodigoSolicitud','CXPS_CMM_EstatusId','CXPS_USU_CreadoPorId',null,null,1000154,1000151,1000152,1000153,1,GETDATE(),1,null,null),
		   ('Validación de Pedidos','Alerta utilizada al validar un pedido',(select MP_NodoId from MenuPrincipal where  MP_Titulo = 'Pedidos' and MP_Orden = 6),1000143,1000164,'Pedidos','PED_PedidoId','PED_Codigo','PED_CMM_EstatusId','PED_USU_CreadoPorId',null,null,1000154,1000151,1000152,1000153,1,GETDATE(),1,null,null),
		   ('Validación de surtir pedido','Alerta utilizada al validar surtir pedido',(select MP_NodoId from MenuPrincipal where  MP_Titulo = 'Surtir pedido' and MP_Orden = 7),1000143,1000165,'Pedidos','PED_PedidoId','PED_Codigo','PED_CMM_EstatusId','PED_USU_CreadoPorId',null,null,1000154,1000151,1000152,1000153,1,GETDATE(),1,null,null)

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
		   (1000164,'CMM_CALC_TipoMovimiento','Pedidos',1,1,GETDATE()),
		   (1000165,'CMM_CALC_TipoMovimiento','Surtir pedido',1,1,GETDATE()),
		   (1000181,'CMM_ACEN_TipoNotificacion','Notificacion Inicial',1,1,GETDATE()),
		   (1000182,'CMM_ACEN_TipoNotificacion','Notificacion Final',1,1,GETDATE())
		   
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[AlertasConfigEtapaNotificadores](
	[ACEN_AlertaEtapaNotificadorId] [int] IDENTITY(1,1) NOT NULL,
	[ACEN_USU_NotificadorId] [int] NULL,
	[ACEN_CMM_TipoNotificacionAlertaId] [int] NULL,
	[ACEN_ACE_AlertaConfiguracionEtapaId] [int] NOT NULL,
	[ACEN_Activo] [bit] NULL,
 CONSTRAINT [PK_AlertasConfigEtapaNotificadores] PRIMARY KEY CLUSTERED 
(
	[ACEN_AlertaEtapaNotificadorId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[AlertasConfigEtapaNotificadores]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfigEtapaNotificadores_AlertasConfigEtapa] FOREIGN KEY([ACEN_ACE_AlertaConfiguracionEtapaId])
REFERENCES [dbo].[AlertasConfigEtapa] ([ACE_AlertaConfiguracionEtapaId])
GO

ALTER TABLE [dbo].[AlertasConfigEtapaNotificadores] CHECK CONSTRAINT [FK_AlertasConfigEtapaNotificadores_AlertasConfigEtapa]
GO

ALTER TABLE [dbo].[AlertasConfigEtapaNotificadores]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfigEtapaNotificadores_ControlMaestroTipoNotificacion] FOREIGN KEY([ACEN_CMM_TipoNotificacionAlertaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[AlertasConfigEtapaNotificadores] CHECK CONSTRAINT [FK_AlertasConfigEtapaNotificadores_ControlMaestroTipoNotificacion]
GO



