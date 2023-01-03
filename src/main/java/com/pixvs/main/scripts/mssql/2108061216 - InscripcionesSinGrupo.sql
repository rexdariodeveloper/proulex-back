/**
 * Created by Angel Daniel Hernández Silva on 10/08/2021.
 * Object:  Table [dbo].[InscripcionesSinGrupo]
 */

/*********************************/
/***** InscripcionesSinGrupo *****/
/*********************************/

CREATE TABLE [dbo].[InscripcionesSinGrupo](
	[INSSG_InscripcionId] [int] IDENTITY(1,1) NOT NULL,
	[INSSG_OV_OrdenVentaId] [int] NOT NULL,
	[INSSG_ALU_AlumnoId] [int] NOT NULL,
    [INSSG_SUC_SucursalId] [int] NOT NULL,
    [INSSG_PROG_ProgramaId] [int] NOT NULL,
    [INSSG_CMM_IdiomaId] [int] NOT NULL,
    [INSSG_PAMOD_ModalidadId] [int] NOT NULL,
    [INSSG_PAMODH_PAModalidadHorarioId] [int] NOT NULL,
    [INSSG_Nivel] [int] NOT NULL,
    [INSSG_Grupo] [int] NULL,
    [INSSG_Comentario] [varchar](255) NULL,
    [INSSG_CMM_EstatusId] [int] NOT NULL,
	[INSSG_FechaCreacion] [datetime2](7) NOT NULL,
	[INSSG_FechaModificacion] [datetime2](7) NULL,
	[INSSG_USU_CreadoPorId] [int] NULL,
	[INSSG_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[INSSG_InscripcionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK

ALTER TABLE [dbo].[InscripcionesSinGrupo]  WITH CHECK ADD  CONSTRAINT [FK_INSSG_OV_OrdenVentaId] FOREIGN KEY([INSSG_OV_OrdenVentaId])
REFERENCES [dbo].[OrdenesVenta] ([OV_OrdenVentaId])
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] CHECK CONSTRAINT [FK_INSSG_OV_OrdenVentaId]
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo]  WITH CHECK ADD  CONSTRAINT [FK_INSSG_ALU_AlumnoId] FOREIGN KEY([INSSG_ALU_AlumnoId])
REFERENCES [dbo].[Alumnos] ([ALU_AlumnoId])
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] CHECK CONSTRAINT [FK_INSSG_ALU_AlumnoId]
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo]  WITH CHECK ADD  CONSTRAINT [FK_INSSG_SUC_SucursalId] FOREIGN KEY([INSSG_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] CHECK CONSTRAINT [FK_INSSG_SUC_SucursalId]
GO
ALTER TABLE [dbo].[InscripcionesSinGrupo]  WITH CHECK ADD  CONSTRAINT [FK_INSSG_PROG_ProgramaId] FOREIGN KEY([INSSG_PROG_ProgramaId])
REFERENCES [dbo].[Programas] ([PROG_ProgramaId])
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] CHECK CONSTRAINT [FK_INSSG_PROG_ProgramaId]
GO
ALTER TABLE [dbo].[InscripcionesSinGrupo]  WITH CHECK ADD  CONSTRAINT [FK_INSSG_CMM_IdiomaId] FOREIGN KEY([INSSG_CMM_IdiomaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] CHECK CONSTRAINT [FK_INSSG_CMM_IdiomaId]
GO
ALTER TABLE [dbo].[InscripcionesSinGrupo]  WITH CHECK ADD  CONSTRAINT [FK_INSSG_PAMOD_ModalidadId] FOREIGN KEY([INSSG_PAMOD_ModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] CHECK CONSTRAINT [FK_INSSG_PAMOD_ModalidadId]
GO
ALTER TABLE [dbo].[InscripcionesSinGrupo]  WITH CHECK ADD  CONSTRAINT [FK_INSSG_PAMODH_PAModalidadHorarioId] FOREIGN KEY([INSSG_PAMODH_PAModalidadHorarioId])
REFERENCES [dbo].[PAModalidadesHorarios] ([PAMODH_PAModalidadHorarioId])
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] CHECK CONSTRAINT [FK_INSSG_PAMODH_PAModalidadHorarioId]
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo]  WITH CHECK ADD  CONSTRAINT [FK_INSSG_USU_CreadoPorId] FOREIGN KEY([INSSG_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] CHECK CONSTRAINT [FK_INSSG_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo]  WITH CHECK ADD  CONSTRAINT [FK_INSSG_USU_ModificadoPorId] FOREIGN KEY([INSSG_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] CHECK CONSTRAINT [FK_INSSG_USU_ModificadoPorId]
GO

/***************************/
/***** CMM - Estatus *****/
/***************************/

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON
GO

INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_USU_CreadoPorId],
	[CMM_FechaCreacion],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 2000540,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_INSSG_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pagada'
),(
	/* [CMM_ControlId] */ 2000541,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_INSSG_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pendiente de pago'
),(
	/* [CMM_ControlId] */ 2000542,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_INSSG_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Asignada'
),(
	/* [CMM_ControlId] */ 2000543,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_INSSG_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cancelada'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO