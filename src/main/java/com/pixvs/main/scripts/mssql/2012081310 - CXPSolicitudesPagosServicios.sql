/**
 * Created by Angel Daniel Hernández Silva on 03/12/2020.
 * Object:  Table [dbo].[CXPSolicitudesPagosServicios]
 */


CREATE TABLE [dbo].[CXPSolicitudesPagosServicios](
	[CXPSPS_CXPSolicitudPagoServicioId] [int] IDENTITY(1,1) NOT NULL ,
	[CXPSPS_CodigoSolicitudPagoServicio] [varchar]  (255) NOT NULL ,
	[CXPSPS_SUC_SucursalId] int NULL,
	[CXPSPS_CMM_EstatusId] [int]  NOT NULL ,
	[CXPSPS_FechaCreacion] [datetime2](7) NOT NULL,
	[CXPSPS_FechaModificacion] [datetime2](7) NULL,
	[CXPSPS_USU_CreadoPorId] [int] NULL,
	[CXPSPS_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[CXPSPS_CXPSolicitudPagoServicioId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServicios]  WITH CHECK ADD  CONSTRAINT [FK_CXPSPS_SUC_SucursalId] FOREIGN KEY([CXPSPS_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServicios]  WITH CHECK ADD  CONSTRAINT [FK_CXPSPS_CMM_EstatusId] FOREIGN KEY([CXPSPS_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServicios] CHECK CONSTRAINT [FK_CXPSPS_CMM_EstatusId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServicios]  WITH CHECK ADD  CONSTRAINT [FK_CXPSPS_USU_ModificadoPorId] FOREIGN KEY([CXPSPS_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServicios] CHECK CONSTRAINT [FK_CXPSPS_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServicios]  WITH CHECK ADD  CONSTRAINT [FK_CXPSPS_USU_CreadoPorId] FOREIGN KEY([CXPSPS_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServicios] CHECK CONSTRAINT [FK_CXPSPS_USU_CreadoPorId]
GO


















CREATE TABLE [dbo].[CXPSolicitudesPagosServiciosDetalles](
	[CXPSPSD_CXPSolicitudPagoServicioDetalleId] [int] IDENTITY(1,1) NOT NULL ,
	[CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId] [int]  NOT NULL ,
	[CXPSPSD_CXPF_CXPFacturaId] [int]  NOT NULL ,
	[CXPSPSD_CMM_EstatusId] [int]  NOT NULL,
	[CXPSPSD_FechaModificacion] [datetime2](7) NULL,
	[CXPSPSD_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[CXPSPSD_CXPSolicitudPagoServicioDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO


ALTER TABLE [dbo].[CXPSolicitudesPagosServiciosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId] FOREIGN KEY([CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId])
REFERENCES [dbo].[CXPSolicitudesPagosServicios] ([CXPSPS_CXPSolicitudPagoServicioId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServiciosDetalles] CHECK CONSTRAINT [FK_CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServiciosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPSPSD_CXPF_CXPFacturaId] FOREIGN KEY([CXPSPSD_CXPF_CXPFacturaId])
REFERENCES [dbo].[CXPFacturas] ([CXPF_CXPFacturaId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServiciosDetalles] CHECK CONSTRAINT [FK_CXPSPSD_CXPF_CXPFacturaId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServiciosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPSPSD_CMM_EstatusId] FOREIGN KEY([CXPSPSD_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServiciosDetalles] CHECK CONSTRAINT [FK_CXPSPSD_CMM_EstatusId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServiciosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPSPSD_USU_ModificadoPorId] FOREIGN KEY([CXPSPSD_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosServiciosDetalles] CHECK CONSTRAINT [FK_CXPSPSD_USU_ModificadoPorId]
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
	/* [CMM_ControlId] */ 2000281,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPSPS_EstadoSolicitudPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'En proceso'
),(
	/* [CMM_ControlId] */ 2000282,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPSPS_EstadoSolicitudPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Finalizada'
),(
	/* [CMM_ControlId] */ 2000283,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPSPS_EstadoSolicitudPago',
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cancelada'
),(
	/* [CMM_ControlId] */ 2000284,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_CXPSPS_EstadoSolicitudPago',
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
		   'CXP Solicitudes Pagos Servicios' -- <AUT_Nombre, varchar(50),>
           ,'SP' -- <AUT_Prefijo, varchar(255),>
           ,1 -- <AUT_Siguiente, int,>
           ,6 -- <AUT_Digitos, int,>
           ,1 -- <AUT_Activo, bit,>
	 )
GO