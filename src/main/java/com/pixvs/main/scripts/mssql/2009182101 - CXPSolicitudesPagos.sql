/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 * Object:  Table [dbo].[CXPSolicitudesPagos]
 */


CREATE TABLE [dbo].[CXPSolicitudesPagos](
	[CXPS_CXPSolicitudPagoId] [int] IDENTITY(1,1) NOT NULL ,
	[CXPS_CodigoSolicitud] [varchar]  (255) NOT NULL ,
	[CXPS_CMM_EstatusId] [int]  NOT NULL ,
	[CXPS_FechaCreacion] [datetime2](7) NOT NULL,
	[CXPS_FechaModificacion] [datetime2](7) NULL,
	[CXPS_USU_CreadoPorId] [int] NULL,
	[CXPS_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[CXPS_CXPSolicitudPagoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagos]  WITH CHECK ADD  CONSTRAINT [FK_CXPS_CMM_EstatusId] FOREIGN KEY([CXPS_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagos] CHECK CONSTRAINT [FK_CXPS_CMM_EstatusId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagos]  WITH CHECK ADD  CONSTRAINT [FK_CXPS_USU_ModificadoPorId] FOREIGN KEY([CXPS_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagos] CHECK CONSTRAINT [FK_CXPS_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagos]  WITH CHECK ADD  CONSTRAINT [FK_CXPS_USU_CreadoPorId] FOREIGN KEY([CXPS_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagos] CHECK CONSTRAINT [FK_CXPS_USU_CreadoPorId]
GO


















CREATE TABLE [dbo].[CXPSolicitudesPagosDetalles](
	[CXPSD_CXPSSolicitudPagoDetalleId] [int] IDENTITY(1,1) NOT NULL ,
	[CXPSD_CXPS_CXPSolicitudPagoId] [int]  NOT NULL ,
	[CXPSD_CXPF_CXPFacturaId] [int]  NOT NULL ,
	[CXPSD_CMM_EstatusId] [int]  NOT NULL,
	[CXPSD_FechaModificacion] [datetime2](7) NULL,
	[CXPSD_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[CXPSD_CXPSSolicitudPagoDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO


ALTER TABLE [dbo].[CXPSolicitudesPagosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPSD_CXPS_CXPSolicitudPagoId] FOREIGN KEY([CXPSD_CXPS_CXPSolicitudPagoId])
REFERENCES [dbo].[CXPSolicitudesPagos] ([CXPS_CXPSolicitudPagoId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosDetalles] CHECK CONSTRAINT [FK_CXPSD_CXPS_CXPSolicitudPagoId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPSD_CXPF_CXPFacturaId] FOREIGN KEY([CXPSD_CXPF_CXPFacturaId])
REFERENCES [dbo].[CXPFacturas] ([CXPF_CXPFacturaId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosDetalles] CHECK CONSTRAINT [FK_CXPSD_CXPF_CXPFacturaId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPSD_CMM_EstatusId] FOREIGN KEY([CXPSD_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosDetalles] CHECK CONSTRAINT [FK_CXPSD_CMM_EstatusId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPSD_USU_ModificadoPorId] FOREIGN KEY([CXPSD_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosDetalles] CHECK CONSTRAINT [FK_CXPSD_USU_ModificadoPorId]
GO

















SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO

INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_FechaCreacion],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 2000161,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPS_EstadoSolicitudPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'En proceso'
),(
	/* [CMM_ControlId] */ 2000162,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPS_EstadoSolicitudPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Finalizada'
),(
	/* [CMM_ControlId] */ 2000163,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPS_EstadoSolicitudPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cancelada'
),(
	/* [CMM_ControlId] */ 2000164,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPS_EstadoSolicitudPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Borrada'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO
















INSERT INTO [dbo].[Autonumericos]
           ([AUT_Nombre]
           ,[AUT_Prefijo]
           ,[AUT_Siguiente]
           ,[AUT_Digitos]
           ,[AUT_Activo])
     VALUES (
		   'CXP Solicitudes Pagos' -- <AUT_Nombre, varchar(50),>
           ,'CXPS' -- <AUT_Prefijo, varchar(255),>
           ,1 -- <AUT_Siguiente, int,>
           ,6 -- <AUT_Digitos, int,>
           ,1 -- <AUT_Activo, bit,>
	 )
GO











INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'calendar_today', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Compras'), 5, 1000021, N'Programación de pagos', N'Schedule payment', N'item', N'/app/compras/programacion-pagos')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Programación de pagos' and MP_Icono = 'calendar_today' and MP_Orden = 5)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO