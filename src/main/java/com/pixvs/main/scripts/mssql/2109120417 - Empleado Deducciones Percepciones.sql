--------------------- EmpleadosDeduccionesPercepciones ---------------------------------------------
CREATE TABLE [dbo].[EmpleadosDeduccionesPercepciones](
	[EDP_EmpleadoDeduccionPercepcionId] [int] IDENTITY(1,1) NOT NULL,
	[EDP_Codigo] [nvarchar](15) NOT NULL,
	[EDP_EMP_EmpleadoId] [int] NOT NULL,
	[EDP_Fecha] [date] NOT NULL,
	[EDP_CMM_TipoMovimientoId] [int] NOT NULL,
	[EDP_DEDPER_DeduccionPercepcionId] [int] NOT NULL,
	[EDP_ValorFijo] [decimal](10,2) NOT NULL,
	[EDP_CantidadHoras] [int] NOT NULL,
	[EDP_Total] [decimal](10,2) NOT NULL,
	[EDP_Activo] [bit] NOT NULL,
	[EDP_FechaCreacion] [datetime2](7) NOT NULL,
	[EDP_USU_CreadoPorId] [int] NOT NULL,
	[EDP_FechaUltimaModificacion] [datetime2](7) NULL,
	[EDP_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[EDP_EmpleadoDeduccionPercepcionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---
ALTER TABLE [dbo].[EmpleadosDeduccionesPercepciones]  WITH CHECK ADD  CONSTRAINT [FK_EDP_EMP_EmpleadoId] FOREIGN KEY([EDP_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[EmpleadosDeduccionesPercepciones] CHECK CONSTRAINT [FK_EDP_EMP_EmpleadoId]
GO

---
ALTER TABLE [dbo].[EmpleadosDeduccionesPercepciones]  WITH CHECK ADD  CONSTRAINT [FK_EDP_CMM_TipoMovimientoId] FOREIGN KEY([EDP_CMM_TipoMovimientoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EmpleadosDeduccionesPercepciones] CHECK CONSTRAINT [FK_EDP_CMM_TipoMovimientoId]
GO

---
ALTER TABLE [dbo].[EmpleadosDeduccionesPercepciones]  WITH CHECK ADD  CONSTRAINT [FK_EDP_DEDPER_DeduccionPercepcionId] FOREIGN KEY([EDP_DEDPER_DeduccionPercepcionId])
REFERENCES [dbo].[DeduccionesPercepciones] ([DEDPER_DeduccionPercepcionId])
GO

ALTER TABLE [dbo].[EmpleadosDeduccionesPercepciones] CHECK CONSTRAINT [FK_EDP_DEDPER_DeduccionPercepcionId]
GO

---
ALTER TABLE [dbo].[EmpleadosDeduccionesPercepciones]  WITH CHECK ADD  CONSTRAINT [FK_EDP_USU_CreadoPorId] FOREIGN KEY([EDP_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[EmpleadosDeduccionesPercepciones] CHECK CONSTRAINT [FK_EDP_USU_CreadoPorId]
GO
---
ALTER TABLE [dbo].[EmpleadosDeduccionesPercepciones]  WITH CHECK ADD  CONSTRAINT [FK_EDP_USU_ModificadoPorId] FOREIGN KEY([EDP_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[EmpleadosDeduccionesPercepciones] CHECK CONSTRAINT [FK_EDP_USU_ModificadoPorId]
GO

--------------------- EmpleadosDeduccionesPercepcionesDocumentos ---------------------------------------------
CREATE TABLE [dbo].[EmpleadosDeduccionesPercepcionesDocumentos](
	[EDPD_EmpleadoDeduccionPercepcionDocumentoId] [int] IDENTITY(1,1) NOT NULL,
	[EDPD_EDP_EmpleadoDeduccionPercepcionId] [int] NOT NULL,
	[EDPD_ARC_ArchivoId] [int] NOT NULL,
	[EDPD_Activo] [bit] NOT NULL
PRIMARY KEY CLUSTERED 
(
	[EDPD_EmpleadoDeduccionPercepcionDocumentoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmpleadosDeduccionesPercepcionesDocumentos]  WITH CHECK ADD  CONSTRAINT [FK_EDPD_EDP_EmpleadoDeduccionPercepcionId] FOREIGN KEY([EDPD_EDP_EmpleadoDeduccionPercepcionId])
REFERENCES [dbo].[EmpleadosDeduccionesPercepciones] ([EDP_EmpleadoDeduccionPercepcionId])
GO

ALTER TABLE [dbo].[EmpleadosDeduccionesPercepcionesDocumentos] CHECK CONSTRAINT [FK_EDPD_EDP_EmpleadoDeduccionPercepcionId]
GO

---
ALTER TABLE [dbo].[EmpleadosDeduccionesPercepcionesDocumentos]  WITH CHECK ADD  CONSTRAINT [FK_EDPD_ARC_ArchivoId] FOREIGN KEY([EDPD_ARC_ArchivoId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[EmpleadosDeduccionesPercepcionesDocumentos] CHECK CONSTRAINT [FK_EDPD_ARC_ArchivoId]
GO