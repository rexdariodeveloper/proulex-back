/****** Object:  Table [dbo].[SolicitudesRH]    Script Date: 25/02/2021 10:34:41 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[CXPSolicitudesPagosRH](
	[CPXSPRH_CXPSolicitudPagoRhId] [int] IDENTITY(1,1) NOT NULL,
	[CPXSPRH_Codigo] [nvarchar](10) NULL,
	[CPXSPRH_SUC_SucursalId] [int] NOT NULL,
	[CPXSPRH_EMP_EmpleadoId] [int] NOT NULL,
	[CPXSPRH_CMM_TipoPagoId] [int] NOT NULL,
	[CPXSPRH_Monto] [decimal](10,2) NULL,
	[CPXSPRH_CXPF_CXPFacturaId] [int] NOT NULL,
	[CPXSPRH_CMM_EstatusId] [int] NOT NULL,
	[CPXSPRH_FechaCreacion] [datetime2](7) NOT NULL,
	[CPXSPRH_USU_CreadoPorId] [int] NOT NULL,
	[CPXSPRH_FechaModificacion] [datetime2](7) NULL,
	[CPXSPRH_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_CXPSolicitudesPagosRH] PRIMARY KEY CLUSTERED 
(
	[CPXSPRH_CXPSolicitudPagoRhId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH] ADD  CONSTRAINT [DF_SolicitudesRH_CPXSPRH_FechaCreacion]  DEFAULT (getdate()) FOR [CPXSPRH_FechaCreacion]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRH_EMP_EmpleadoId] FOREIGN KEY([CPXSPRH_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH] CHECK CONSTRAINT [FK_CPXSPRH_EMP_EmpleadoId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRH_SUC_SucursalId] FOREIGN KEY([CPXSPRH_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH] CHECK CONSTRAINT [FK_CPXSPRH_SUC_SucursalId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRH_CMM_TipoPagoId] FOREIGN KEY([CPXSPRH_CMM_TipoPagoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH] CHECK CONSTRAINT [FK_CPXSPRH_CMM_TipoPagoId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRH_CXPF_CXPFacturaId] FOREIGN KEY([CPXSPRH_CXPF_CXPFacturaId])
REFERENCES [dbo].[CXPFacturas] ([CXPF_CXPFacturaId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH] CHECK CONSTRAINT [FK_CPXSPRH_CXPF_CXPFacturaId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRH_CMM_EstatusId] FOREIGN KEY([CPXSPRH_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH] CHECK CONSTRAINT [FK_CPXSPRH_CMM_EstatusId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRH_USU_CreadoPorId] FOREIGN KEY([CPXSPRH_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH] CHECK CONSTRAINT [FK_CPXSPRH_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRH_USU_ModificadoPorId] FOREIGN KEY([CPXSPRH_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRH] CHECK CONSTRAINT [FK_CPXSPRH_USU_ModificadoPorId]
GO

------------------------------ CXPSolicitudesPagosRHBecariosDocumentos	(relación 1 - n) ------------------------------

CREATE TABLE [dbo].[CXPSolicitudesPagosRHBecariosDocumentos](
	[CPXSPRHBD_CXPSolicitudPagoRhBecarioDocumentoId] [int] IDENTITY(1,1) NOT NULL,
	[CPXSPRHBD_CPXSPRH_CXPSolicitudPagoRhId] [int] NOT NULL,
	[CPXSPRHBD_ARC_ArchivoId] [int] NOT NULL
	CONSTRAINT [PK_CXPSolicitudesPagosRHBecariosDocumentos] PRIMARY KEY CLUSTERED 
(
	[CPXSPRHBD_CXPSolicitudPagoRhBecarioDocumentoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRHBecariosDocumentos]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRHBD_CPXSPRH_SolicitudPagoRhId] FOREIGN KEY([CPXSPRHBD_CPXSPRH_CXPSolicitudPagoRhId])
REFERENCES [dbo].[CXPSolicitudesPagosRH] ([CPXSPRH_CXPSolicitudPagoRhId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRHBecariosDocumentos] CHECK CONSTRAINT [FK_CPXSPRHBD_CPXSPRH_SolicitudPagoRhId]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRHBecariosDocumentos]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRHBD_ARC_ArchivoId] FOREIGN KEY([CPXSPRHBD_ARC_ArchivoId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRHBecariosDocumentos] CHECK CONSTRAINT [FK_CPXSPRHBD_ARC_ArchivoId]
GO

------------------------------ CXPSolicitudesPagosRHPensionAlimenticia	(relación 1 - 1) ------------------------------


CREATE TABLE [dbo].[CXPSolicitudesPagosRHPensionesAlimenticias](
	[CPXSPRHPA_CXPSolicitudPagoRhPensionAlimenticiaId] [int] IDENTITY(1,1) NOT NULL,
	[CPXSPRHPA_CPXSPRH_CXPSolicitudPagoRhId] [int] NOT NULL,
	[CPXSPRHPA_NombreBeneficiario] [nvarchar](50) NOT NULL,
	[CPXSPRHPA_NumeroResolucion] [int] NOT NULL
	CONSTRAINT [PK_CXPSolicitudesPagosRHPensionesAlimenticias] PRIMARY KEY CLUSTERED 
(
	[CPXSPRHPA_CXPSolicitudPagoRhPensionAlimenticiaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRHPensionesAlimenticias]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRHPA_CPXSPRH_SolicitudPagoRhId] FOREIGN KEY([CPXSPRHPA_CPXSPRH_CXPSolicitudPagoRhId])
REFERENCES [dbo].[CXPSolicitudesPagosRH] ([CPXSPRH_CXPSolicitudPagoRhId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRHPensionesAlimenticias] CHECK CONSTRAINT [FK_CPXSPRHPA_CPXSPRH_SolicitudPagoRhId]
GO



------------------------------ CXPSolicitudesPagosRHIncapacidad	(relación 1 - 1) ------------------------------
CREATE TABLE [dbo].[CXPSolicitudesPagosRHIncapacidades](
	[CPXSPRHI_CXPSolicitudPagoRhIncapacidadId] [int] IDENTITY(1,1) NOT NULL,
	[CPXSPRHI_CPXSPRH_CXPSolicitudPagoRhId] [int] NOT NULL,
	[CPXSPRHI_FolioIncapacidad] [nvarchar](30) NOT NULL,
	[CPXSPRHI_FechaInicio] [date] NOT NULL,
	[CPXSPRHI_FechaFin] [date] NOT NULL
	CONSTRAINT [PK_CXPSolicitudesPagosRHIncapacidades] PRIMARY KEY CLUSTERED 
(
	[CPXSPRHI_CXPSolicitudPagoRhIncapacidadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRHIncapacidades]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRHI_CPXSPRH_SolicitudPagoRhId] FOREIGN KEY([CPXSPRHI_CPXSPRH_CXPSolicitudPagoRhId])
REFERENCES [dbo].[CXPSolicitudesPagosRH] ([CPXSPRH_CXPSolicitudPagoRhId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRHIncapacidades] CHECK CONSTRAINT [FK_CPXSPRHI_CPXSPRH_SolicitudPagoRhId]
GO

------------------------------ CXPSolicitudesPagosRHIncapacidadDetalle	(relación 1 - n) ------------------------------

CREATE TABLE [dbo].[CXPSolicitudesPagosRHIncapacidadesDetalles](
	[CPXSPRHID_CXPSolicitudPagoRhIncapacidadDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[CPXSPRHID_CPXSPRHI_IncapacidadId] [int] NOT NULL,
	[CPXSPRHID_CMM_TipoId] [int] NULL,
	[CPXSPRHID_CMM_TipoMovimientoId] [int] NULL,
	[CPXSPRHID_SalarioDiario] [decimal](10,2) NOT NULL,
	[CPXSPRHID_Porcentaje] [int] NULL,
	[CPXSPRHID_Dias] [int] NULL
	CONSTRAINT [PK_CXPSolicitudesPagosRHIncapacidadesDetalles] PRIMARY KEY CLUSTERED 
(
	[CPXSPRHID_CXPSolicitudPagoRhIncapacidadDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRHIncapacidadesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRHID_CPXSPRHI_IncapacidadId] FOREIGN KEY([CPXSPRHID_CPXSPRHI_IncapacidadId])
REFERENCES [dbo].[CXPSolicitudesPagosRHIncapacidades] ([CPXSPRHI_CXPSolicitudPagoRhIncapacidadId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRHIncapacidadesDetalles] CHECK CONSTRAINT [FK_CPXSPRHID_CPXSPRHI_IncapacidadId]
GO

------------------------------ CXPSolicitudesPagosRHRetiroCajaAhorro	(relación 1 - 1) ------------------------------

CREATE TABLE [dbo].[CXPSolicitudesPagosRHRetiroCajaAhorro](
	[CPXSPRHRCA_CXPSolicitudPagoRhRetiroCajaAhorroId] [int] IDENTITY(1,1) NOT NULL,
	[CPXSPRHRCA_CPXSPRH_CXPSolicitudPagoRhId] [int] NOT NULL,
	[CPXSPRHID_CMM_TipoRetiroId] [int] NOT NULL,
	[CPXSPRHID_AhorroAcumulado] [decimal](10,2) NOT NULL,
	[CPXSPRHID_CantidadRetirar] [decimal](10,2) NOT NULL,
	[CPXSPRHID_MotivoRetiro] [varchar](300) NOT NULL,
	CONSTRAINT [PK_CXPSolicitudesPagosRHRetiroCajaAhorro] PRIMARY KEY CLUSTERED 
(
	[CPXSPRHRCA_CXPSolicitudPagoRhRetiroCajaAhorroId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRHRetiroCajaAhorro]  WITH CHECK ADD  CONSTRAINT [FK_CPXSPRHRCA_CPXSPRH_SolicitudPagoRhId] FOREIGN KEY([CPXSPRHRCA_CPXSPRH_CXPSolicitudPagoRhId])
REFERENCES [dbo].[CXPSolicitudesPagosRH] ([CPXSPRH_CXPSolicitudPagoRhId])
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosRHRetiroCajaAhorro] CHECK CONSTRAINT [FK_CPXSPRHRCA_CPXSPRH_SolicitudPagoRhId]
GO
