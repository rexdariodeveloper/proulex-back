/**
 * Created by Angel Daniel Hernández Silva on 14/07/2021.
 * Object:  Table [dbo].[Inscripciones]
 */

/*************************/
/***** Inscripciones *****/
/*************************/

CREATE TABLE [dbo].[Inscripciones](
	[INS_InscripcionId] [int] IDENTITY(1,1) NOT NULL,
	[INS_Codigo] [varchar](30) NOT NULL,
	[INS_OV_OrdenVentaId] [int] NOT NULL,
	[INS_ALU_AlumnoId] [int] NOT NULL,
	[INS_PROGRU_GrupoId] [int] NOT NULL,
    [INS_CMM_EstatusId] [int] NOT NULL,
	[INS_FechaCreacion] [datetime2](7) NOT NULL,
	[INS_FechaModificacion] [datetime2](7) NULL,
	[INS_USU_CreadoPorId] [int] NULL,
	[INS_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[INS_InscripcionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_OV_OrdenVentaId] FOREIGN KEY([INS_OV_OrdenVentaId])
REFERENCES [dbo].[OrdenesVenta] ([OV_OrdenVentaId])
GO

ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_OV_OrdenVentaId]
GO

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_ALU_AlumnoId] FOREIGN KEY([INS_ALU_AlumnoId])
REFERENCES [dbo].[Alumnos] ([ALU_AlumnoId])
GO

ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_ALU_AlumnoId]
GO

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_PROGRU_GrupoId] FOREIGN KEY([INS_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO

ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_PROGRU_GrupoId]
GO

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_USU_CreadoPorId] FOREIGN KEY([INS_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_USU_ModificadoPorId] FOREIGN KEY([INS_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_USU_ModificadoPorId]
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
	/* [CMM_ControlId] */ 2000510,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_INS_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pagada'
),(
	/* [CMM_ControlId] */ 2000511,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_INS_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Pendiente de pago'
),(
	/* [CMM_ControlId] */ 2000512,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_INS_Estatus',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ GETDATE(),
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Cancelada'
)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

/*************************/
/***** Autonumericos *****/
/*************************/

INSERT INTO [dbo].[Autonumericos]
           ([AUT_Nombre]
           ,[AUT_Prefijo]
           ,[AUT_Siguiente]
           ,[AUT_Digitos]
           ,[AUT_Activo])
     VALUES
           ('Inscripciones'
           ,'INS'
           ,1
           ,6
           ,1)
GO