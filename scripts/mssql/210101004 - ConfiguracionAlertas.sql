INSERT INTO MenuPrincipal
(
    MP_NodoPadreId,MP_Titulo,MP_TituloEN,MP_Activo,MP_Icono,MP_Orden,MP_Tipo,MP_URL,MP_CMM_SistemaAccesoId,MP_FechaCreacion
)
VALUES
(
    (select MP_NodoId from MenuPrincipal where MP_Titulo = 'CONFIGURACIÓN'),'Alertas','Alerts',1,'notifications',7,'item','/config/alertas',1000021,GETDATE()
)
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AlertasConfig](
	[ALC_AlertaCId] [int] IDENTITY(1,1) NOT NULL,
	[ALC_Nombre] [nvarchar](100) NOT NULL,
	[ALC_Descripcion] [nvarchar](255) NOT NULL,
	[ALC_MP_NodoId] [int] NOT NULL,
	[ALC_CMM_TipoConfigAlertaId] [int] NOT NULL,
	[ALC_CMM_TipoMovimiento] [int] NULL,
	[ALC_TablaReferencia] [nvarchar](150) NOT NULL,
	[ALC_CampoId] [nvarchar](50) NOT NULL,
	[ALC_CampoCodigo] [nvarchar](40) NULL,
	[ALC_CampoEstado] [nvarchar](100) NOT NULL,
	[ALC_CampoEmpCreadoPor] [nvarchar](50) NULL,
	[ALC_CampoEmpAutorizadoPor] [nvarchar](50) NULL,
	[ALC_CampoFechaAutorizacion] [nvarchar](50) NULL,
	[ALC_CMM_EstadoAutorizado] [int] NULL,
	[ALC_CMM_EstadoEnProceso] [int] NULL,
	[ALC_CMM_EstadoRechazado] [int] NULL,
	[ALC_CMM_EstadoEnRevision] [int] NULL,
	[ALC_AplicaSucursales] [bit] DEFAULT 0,
	[ALC_FechaCreacion] [datetime] NOT NULL,
	[ALC_USU_CreadoPorId] [int] NOT NULL,
	[ALC_FechaUltimaModificacion] [datetime] NULL,
	[ALC_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_AlertasConfig] PRIMARY KEY CLUSTERED 
(
	[ALC_AlertaCId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[AlertasConfig] ADD  CONSTRAINT [DF_AlertasConfig_ALC_FechaCreacion]  DEFAULT (getdate()) FOR [ALC_FechaCreacion]
GO
ALTER TABLE [dbo].[AlertasConfig]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfig_ControlesMaestrosMultiples] FOREIGN KEY([ALC_CMM_TipoConfigAlertaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[AlertasConfig] CHECK CONSTRAINT [FK_AlertasConfig_ControlesMaestrosMultiples]
GO
ALTER TABLE [dbo].[AlertasConfig]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfig_ControlesMaestrosMultiples1] FOREIGN KEY([ALC_CMM_TipoMovimiento])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[AlertasConfig] CHECK CONSTRAINT [FK_AlertasConfig_ControlesMaestrosMultiples1]
GO
ALTER TABLE [dbo].[AlertasConfig]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfig_ControlesMaestrosMultiples2] FOREIGN KEY([ALC_CMM_EstadoAutorizado])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[AlertasConfig] CHECK CONSTRAINT [FK_AlertasConfig_ControlesMaestrosMultiples2]
GO
ALTER TABLE [dbo].[AlertasConfig]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfig_ControlesMaestrosMultiples3] FOREIGN KEY([ALC_CMM_EstadoEnProceso])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[AlertasConfig] CHECK CONSTRAINT [FK_AlertasConfig_ControlesMaestrosMultiples3]
GO
ALTER TABLE [dbo].[AlertasConfig]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfig_ControlesMaestrosMultiples4] FOREIGN KEY([ALC_CMM_EstadoRechazado])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[AlertasConfig] CHECK CONSTRAINT [FK_AlertasConfig_ControlesMaestrosMultiples4]
GO
ALTER TABLE [dbo].[AlertasConfig]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfig_ControlesMaestrosMultiples5] FOREIGN KEY([ALC_CMM_EstadoEnRevision])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[AlertasConfig] CHECK CONSTRAINT [FK_AlertasConfig_ControlesMaestrosMultiples5]
GO
ALTER TABLE [dbo].[AlertasConfig]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfig_MenuPrincipal] FOREIGN KEY([ALC_MP_NodoId])
REFERENCES [dbo].[MenuPrincipal] ([MP_NodoId])
GO
ALTER TABLE [dbo].[AlertasConfig] CHECK CONSTRAINT [FK_AlertasConfig_MenuPrincipal]
GO
ALTER TABLE [dbo].[AlertasConfig]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfig_Usuarios] FOREIGN KEY([ALC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[AlertasConfig] CHECK CONSTRAINT [FK_AlertasConfig_Usuarios]
GO
ALTER TABLE [dbo].[AlertasConfig]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfig_Usuarios1] FOREIGN KEY([ALC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[AlertasConfig] CHECK CONSTRAINT [FK_AlertasConfig_Usuarios1]
GO


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AlertasConfigEtapa](
	[ACE_AlertaConfiguracionEtapaId] [int] IDENTITY(1,1) NOT NULL,
	[ACE_ALC_AlertaCId] [int] NOT NULL,
	[ACE_Orden] [tinyint] NOT NULL,
	[ACE_FechaCreacion] [datetime] NOT NULL,
	[ACE_USU_CreadoPorId] [int] NOT NULL,
	[ACE_FechaUltimaModificacion] [datetime] NULL,
	[ACE_USU_ModificadoPorId] [int] NULL,
	[ACE_CMM_TipoAprobacion] [int] NULL,
	[ACE_CMM_TipoOrden] [int] NULL,
	[ACE_CMM_CondicionAprobacion] [int] NULL,
	[ACE_CriteriosEconomicos] [bit] NULL,
	[ACE_MontoMinimo] [money] NULL,
	[ACE_MontoMaximo] [money] NULL,
	[ACE_CMM_TipoMonto] [int] NULL,
	[ACE_AutorizacionDirecta] [bit] NOT NULL,
	[ACE_CMM_EstatusReferenciaId] [int] NULL,
	[ACE_CMM_TipoAlertaId] [int] NOT NULL,
	[ACE_SUC_SucursalId] [int] NULL,
 CONSTRAINT [PK_AlertasConfigEtapa] PRIMARY KEY CLUSTERED 
(
	[ACE_AlertaConfiguracionEtapaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[AlertasConfigEtapa] ADD  CONSTRAINT [DF_AlertasConfigEtapa_ACE_FechaCreacion]  DEFAULT (getdate()) FOR [ACE_FechaCreacion]
GO
ALTER TABLE [dbo].[AlertasConfigEtapa] ADD  DEFAULT ((1000172)) FOR [ACE_CMM_TipoAlertaId]
GO
ALTER TABLE [dbo].[AlertasConfigEtapa]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfigEtapa_AlertasConfig] FOREIGN KEY([ACE_ALC_AlertaCId])
REFERENCES [dbo].[AlertasConfig] ([ALC_AlertaCId])
GO
ALTER TABLE [dbo].[AlertasConfigEtapa] CHECK CONSTRAINT [FK_AlertasConfigEtapa_AlertasConfig]
GO
ALTER TABLE [dbo].[AlertasConfigEtapa]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfigEtapa_Usuarios] FOREIGN KEY([ACE_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[AlertasConfigEtapa] CHECK CONSTRAINT [FK_AlertasConfigEtapa_Usuarios]
GO
ALTER TABLE [dbo].[AlertasConfigEtapa]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfigEtapa_Usuarios1] FOREIGN KEY([ACE_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[AlertasConfigEtapa] CHECK CONSTRAINT [FK_AlertasConfigEtapa_Usuarios1]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[AlertasConfigEtapaAprobadores](
	[ACEA_AlertaEtapaAprobadorId] [int] IDENTITY(1,1) NOT NULL,
	[ACEA_USU_AprobadorId] [int] NULL,
	[ACEA_Orden] [tinyint] NOT NULL,
	[ACEA_ACE_AlertaConfiguracionEtapaId] [int] NOT NULL,
	[ACEA_Activo] [bit] NULL,
 CONSTRAINT [PK_AlertasConfigEtapaAprobadores] PRIMARY KEY CLUSTERED 
(
	[ACEA_AlertaEtapaAprobadorId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[AlertasConfigEtapaAprobadores]  WITH CHECK ADD  CONSTRAINT [FK_AlertasConfigEtapaAprobadores_AlertasConfigEtapa] FOREIGN KEY([ACEA_ACE_AlertaConfiguracionEtapaId])
REFERENCES [dbo].[AlertasConfigEtapa] ([ACE_AlertaConfiguracionEtapaId])
GO

ALTER TABLE [dbo].[AlertasConfigEtapaAprobadores] CHECK CONSTRAINT [FK_AlertasConfigEtapaAprobadores_AlertasConfigEtapa]
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
           (1000101,'CMM_ACE_TipoAprobacion','Usuario',1,1,GETDATE()),
		   (1000102,'CMM_ACE_TipoAprobacion','Departamentos',1,1,GETDATE()),
		   (1000111,'CMM_ACE_TipoOrden','Paralela',1,1,GETDATE()),
		   (1000112,'CMM_ACE_TipoOrden','Secuencial',1,1,GETDATE()),
		   (1000121,'CMM_ACE_TipoCondicionAprobacion','Una aprobación',1,1,GETDATE()),
		   (1000122,'CMM_ACE_TipoCondicionAprobacion','Todas las aprobaciones',1,1,GETDATE()),
		   (1000123,'CMM_ACE_TipoCondicionAprobacion','Notificar al creador',1,1,GETDATE()),
		   (1000131,'CMM_ACE_TipoMonto','Diaro',1,1,GETDATE()),
		   (1000132,'CMM_ACE_TipoMonto','Mensual',1,1,GETDATE()),
		   (1000133,'CMM_ACE_TipoMonto','Por exhibición',1,1,GETDATE()),
		   (1000141,'CMM_CALC_TipoConfigAlerta','Notificación',1,1,GETDATE()),
		   (1000142,'CMM_CALC_TipoConfigAlerta','Notificación y Autorización',1,1,GETDATE()),
		   (1000143,'CMM_CALC_TipoConfigAlerta','Autorización',1,1,GETDATE()),
		   (1000151,'CMM_CALE_EstatusAlerta','En Proceso',1,1,GETDATE()),
		   (1000152,'CMM_CALE_EstatusAlerta','Rechazada',1,1,GETDATE()),
		   (1000153,'CMM_CALE_EstatusAlerta','En Revisión',1,1,GETDATE()),	
		   (1000154,'CMM_CALE_EstatusAlerta','Autorizada',1,1,GETDATE()),
		   (1000155,'CMM_CALE_EstatusAlerta','Cancelada',1,1,GETDATE()),
		   (1000161,'CMM_CALC_TipoMovimiento','Solicitud de pago',1,1,GETDATE()),
		   (1000162,'CMM_CALC_TipoMovimiento','Requisiciones',1,1,GETDATE()),
		   (1000163,'CMM_CALC_TipoMovimiento','Órdenes de Compra',1,1,GETDATE()),
		   (1000171,'CMM_CALRD_TipoAlerta','Notificación',1,1,GETDATE()),
		   (1000172,'CMM_CALRD_TipoAlerta','Autorización',1,1,GETDATE())
		   
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
           ,[ALC_USU_ModificadoPorId])
     VALUES
           ('Validación de solicitudes de pago','Alerta utilizada al validar una solicitud de pago',34,1000143,1000161,'CXPSolicitudesPagos','CXPS_CXPSolicitudPagoId','CXPS_CodigoSolicitud','CXPS_CMM_EstatusId','CXPS_USU_CreadoPorId',null,null,1000154,1000151,1000152,1000153,1,GETDATE(),1,null,null),
           ('Validación de órdenes de compras','Alerta utilizada al validar una órden de compra',(select MP_NodoId from MenuPrincipal where  MP_Titulo = 'Órdenes de Compra'and MP_Orden =1),1000143,1000163,'OrdenesCompra','OC_OrdenCompraId','OC_Codigo','OC_CMM_EstatusId','OC_USU_CreadoPorId',null,null,1000154,1000151,1000152,1000153,1,GETDATE(),1,null,null),
		   ('Validación de requisiciones','Alerta utilizada al validar una requisicion',(select MP_NodoId from MenuPrincipal where  MP_Titulo = 'Requisiciones' and MP_Orden = 5),1000143,1000162,'Requisiciones','REQ_RequisicionId','REQ_Codigo','REQ_CMM_EstadoRequisicionId','REQ_USU_CreadoPorId',null,null,1000154,1000151,1000152,1000153,1,GETDATE(),1,null,null)
GO


