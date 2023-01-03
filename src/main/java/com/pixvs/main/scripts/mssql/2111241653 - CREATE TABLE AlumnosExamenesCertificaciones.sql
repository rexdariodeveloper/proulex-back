/**
 * Created by Angel Daniel Hernández Silva on 22/11/2021.
 * Object:  Table [dbo].[AlumnosExamenesCertificaciones]
 */

CREATE TABLE [dbo].[AlumnosExamenesCertificaciones](
    [ALUEC_AlumnoExamenCertificacionId] [int] IDENTITY(1,1) NOT NULL,
    [ALUEC_ALU_AlumnoId] [int] NOT NULL,
    [ALUEC_ART_ArticuloId] [int] NOT NULL,
    [ALUEC_OVD_OrdenVentaDetalleId] [int] NOT NULL,
    [ALUEC_CMM_TipoId] [int] NOT NULL,
    [ALUEC_CMM_EstatusId] [int] NOT NULL,
    [ALUEC_Calificacion] [decimal](5,2) NULL,
    [ALUEC_FechaCreacion] [datetime2](7) NOT NULL,
    [ALUEC_FechaModificacion] [datetime2](7) NULL,
    CONSTRAINT [PK_AlumnosExamenesCertificaciones] PRIMARY KEY CLUSTERED (
        [ALUEC_AlumnoExamenCertificacionId] ASC
    ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones]  WITH CHECK ADD  CONSTRAINT [FK_ALUEC_ALU_AlumnoId] FOREIGN KEY([ALUEC_ALU_AlumnoId])
REFERENCES [dbo].[Alumnos] ([ALU_AlumnoId])
GO

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones] CHECK CONSTRAINT [FK_ALUEC_ALU_AlumnoId]
GO

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones]  WITH CHECK ADD  CONSTRAINT [FK_ALUEC_ART_ArticuloId] FOREIGN KEY([ALUEC_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones] CHECK CONSTRAINT [FK_ALUEC_ART_ArticuloId]
GO

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones]  WITH CHECK ADD  CONSTRAINT [FK_ALUEC_OVD_OrdenVentaDetalleId] FOREIGN KEY([ALUEC_OVD_OrdenVentaDetalleId])
REFERENCES [dbo].[OrdenesVentaDetalles] ([OVD_OrdenVentaDetalleId])
GO

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones] CHECK CONSTRAINT [FK_ALUEC_OVD_OrdenVentaDetalleId]
GO

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones]  WITH CHECK ADD  CONSTRAINT [FK_ALUEC_CMM_TipoId] FOREIGN KEY([ALUEC_CMM_TipoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones] CHECK CONSTRAINT [FK_ALUEC_CMM_TipoId]
GO

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones]  WITH CHECK ADD  CONSTRAINT [FK_ALUEC_CMM_EstatusId] FOREIGN KEY([ALUEC_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[AlumnosExamenesCertificaciones] CHECK CONSTRAINT [FK_ALUEC_CMM_EstatusId]
GO

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
	/* [CMM_ControlId] */ 2000640,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ALUEC_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'En proceso'
),(
	/* [CMM_ControlId] */ 2000641,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ALUEC_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Finalizado'
),(
	/* [CMM_ControlId] */ 2000642,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ALUEC_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cancelado'
)
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
	/* [CMM_ControlId] */ 2000650,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ALUEC_Tipo',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Examen'
),(
	/* [CMM_ControlId] */ 2000651,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_ALUEC_Tipo',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Certificación'
)
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
	/* [CMM_ControlId] */ 2000436,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_IM_TipoMovimiento',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Venta de examen o certificación'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO