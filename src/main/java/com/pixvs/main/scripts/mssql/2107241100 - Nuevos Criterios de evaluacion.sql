--- Niveles ------------------------------------
CREATE TABLE [dbo].[ProgramasIdiomasNiveles](
	[PROGIN_ProgramaIdiomaNivelId] [int] IDENTITY(1,1) NOT NULL ,
	[PROGIN_PROGI_ProgramaIdiomaId] [int] NOT NULL,
	[PROGIN_NivelInicial] [int] NOT NULL,
	[PROGIN_NivelFinal] [int] NOT NULL,
	[PROGIN_Activo] [bit]  NOT NULL DEFAULT(1),
PRIMARY KEY CLUSTERED 
(
	[PROGIN_ProgramaIdiomaNivelId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

---ProgramasIdiomas
ALTER TABLE [dbo].[ProgramasIdiomasNiveles]  WITH CHECK ADD  CONSTRAINT [FK_PROGIN_PROGI_ProgramaIdiomaId] FOREIGN KEY([PROGIN_PROGI_ProgramaIdiomaId])
REFERENCES [dbo].[ProgramasIdiomas] ([PROGI_ProgramaIdiomaId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasNiveles] CHECK CONSTRAINT [FK_PROGIN_PROGI_ProgramaIdiomaId]
GO

------------------------Examenes--------------------------------------------

CREATE TABLE [dbo].[ProgramasIdiomasExamenes](
	[PROGIE_ProgramaIdiomaExamenId] [int] IDENTITY(1,1) NOT NULL ,
	[PROGIE_PROGIN_ProgramaIdiomaNivelId] [int] NOT NULL,
	[PROGIE_Nombre] [varchar](100) NOT NULL,
	[PROGIE_Porcentaje] [int] NOT NULL,
	[PROGIE_Activo] [bit]  NOT NULL DEFAULT(1),
	[PROGIE_FechaModificacion] [datetime2](7) NULL,
	[PROGIE_USU_ModificadoPorId] [int] NULL 
PRIMARY KEY CLUSTERED 
(
	[PROGIE_ProgramaIdiomaExamenId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

---ProgramasIdiomasNiveles
ALTER TABLE [dbo].[ProgramasIdiomasExamenes]  WITH CHECK ADD  CONSTRAINT [FK_PROGIE_PROGIN_ProgramaIdiomaNivelId] FOREIGN KEY([PROGIE_PROGIN_ProgramaIdiomaNivelId])
REFERENCES [dbo].[ProgramasIdiomasNiveles] ([PROGIN_ProgramaIdiomaNivelId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasExamenes] CHECK CONSTRAINT [FK_PROGIE_PROGIN_ProgramaIdiomaNivelId]
GO

---Usuario Modificador
ALTER TABLE [dbo].[ProgramasIdiomasExamenes]  WITH CHECK ADD  CONSTRAINT [FK_PROGIE_USU_ModificadoPorId] FOREIGN KEY([PROGIE_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasExamenes] CHECK CONSTRAINT [FK_PROGIE_USU_ModificadoPorId]
GO

-------------------- Examenes detalles ------------------------

CREATE TABLE [dbo].[ProgramasIdiomasExamenesDetalles](
	[PROGIED_ProgramaIdiomaExamenDetalleId] [int] IDENTITY(1,1) NOT NULL ,
	[PROGIED_PROGIE_ProgramaIdiomaExamenId] [int] NOT NULL,
	[PROGIED_PAAE_ActividadEvaluacionId] [int] NOT NULL,
	[PROGIED_CMM_TestId] [int] NOT NULL,
	[PROGIED_Puntaje] [int] NOT NULL,
	[PROGIED_Time] [int] NULL,
	[PROGIED_Continuos] [bit] NULL,
	[PROGIED_Activo] [bit]  NOT NULL DEFAULT(1) 
PRIMARY KEY CLUSTERED 
(
	[PROGIED_ProgramaIdiomaExamenDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

---Examen Cabecera
ALTER TABLE [dbo].[ProgramasIdiomasExamenesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PROGIED_PROGIE_ProgramaIdiomaExamenId] FOREIGN KEY([PROGIED_PROGIE_ProgramaIdiomaExamenId])
REFERENCES [dbo].[ProgramasIdiomasExamenes] ([PROGIE_ProgramaIdiomaExamenId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasExamenesDetalles] CHECK CONSTRAINT [FK_PROGIED_PROGIE_ProgramaIdiomaExamenId]
GO

---Actividades Evaluacion
ALTER TABLE [dbo].[ProgramasIdiomasExamenesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PROGIED_PAAE_ActividadEvaluacionId] FOREIGN KEY([PROGIED_PAAE_ActividadEvaluacionId])
REFERENCES [dbo].[PAActividadesEvaluacion] ([PAAE_ActividadEvaluacionId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasExamenesDetalles] CHECK CONSTRAINT [FK_PROGIED_PAAE_ActividadEvaluacionId]
GO

---Tests
ALTER TABLE [dbo].[ProgramasIdiomasExamenesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PROGIED_CMM_TestId] FOREIGN KEY([PROGIED_CMM_TestId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasExamenesDetalles] CHECK CONSTRAINT [FK_PROGIED_CMM_TestId]
GO

----------------------------------------- Programas Idiomas Tests Modalidades -------------------------------------------------------------
CREATE TABLE [dbo].[ProgramasIdiomasExamenesModalidades](
	[PROGIEM_ProgramaIdiomaExamenDiaId] [int] IDENTITY(1,1) NOT NULL,
	[PROGIEM_PROGIED_ProgramaIdiomaExamenDetalleId] [int] NOT NULL,
	[PROGIEM_PAMOD_ModalidadId] [int] NOT NULL,
	[PROGIEM_Dias] [varchar](500) NOT NULL,
	[PROGIEM_FechaModificacion] [datetime2](7) NULL,
	[PROGIEM_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_ProgramasIdiomasExamenesModalidades] PRIMARY KEY CLUSTERED 
(
	[PROGIEM_ProgramaIdiomaExamenDiaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasExamenesModalidades]  WITH CHECK ADD  CONSTRAINT [FK_PROGIEM_PROGIED_ProgramaIdiomaExamenDetalleId] FOREIGN KEY([PROGIEM_PROGIED_ProgramaIdiomaExamenDetalleId])
REFERENCES [dbo].[ProgramasIdiomasExamenesDetalles] ([PROGIED_ProgramaIdiomaExamenDetalleId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasExamenesModalidades] CHECK CONSTRAINT [FK_PROGIEM_PROGIED_ProgramaIdiomaExamenDetalleId]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasExamenesModalidades]  WITH CHECK ADD  CONSTRAINT [FK_PROGIEM_USU_ModificadoPorId] FOREIGN KEY([PROGIEM_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasExamenesModalidades] CHECK CONSTRAINT [FK_PROGIEM_USU_ModificadoPorId]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasExamenesModalidades]  WITH CHECK ADD  CONSTRAINT [FK_PROGIEM_PAMOD_ModalidadId] FOREIGN KEY([PROGIEM_PAMOD_ModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasExamenesModalidades] CHECK CONSTRAINT [FK_PROGIEM_PAMOD_ModalidadId]
GO

----------------------------------------- Programas Idiomas Tests Dias -------------------------------------------------------------
CREATE TABLE [dbo].[ProgramasIdiomasExamenesUnidades](
	[PROGIEU_ProgramaIdiomaExamenUnidadId] [int] IDENTITY(1,1) NOT NULL,
	[PROGIEU_PROGIED_ProgramaIdiomaExamenDetalleId] [int] NOT NULL,
	[PROGIEU_PROGILM_ProgramaIdiomaLibroMaterialId] [int] NOT NULL,
	[PROGIEU_Descripcion] [varchar](500) NOT NULL
	CONSTRAINT [PK_ProgramasIdiomasExamenesUnidades] PRIMARY KEY CLUSTERED 
(
	[PROGIEU_ProgramaIdiomaExamenUnidadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasExamenesUnidades]  WITH CHECK ADD  CONSTRAINT [FK_PROGIEU_PROGIED_ProgramaIdiomaExamenDetalleId] FOREIGN KEY([PROGIEU_PROGIED_ProgramaIdiomaExamenDetalleId])
REFERENCES [dbo].[ProgramasIdiomasExamenesDetalles] ([PROGIED_ProgramaIdiomaExamenDetalleId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasExamenesUnidades] CHECK CONSTRAINT [FK_PROGIEU_PROGIED_ProgramaIdiomaExamenDetalleId]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasExamenesUnidades]  WITH CHECK ADD  CONSTRAINT [FK_PROGIEU_PROGILM_ProgramaIdiomaLibroMaterialId] FOREIGN KEY([PROGIEU_PROGILM_ProgramaIdiomaLibroMaterialId])
REFERENCES [dbo].[ProgramasIdiomasLibrosMateriales] ([PROGILM_ProgramaIdiomaLibroMaterialId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasExamenesUnidades] CHECK CONSTRAINT [FK_PROGIEU_PROGILM_ProgramaIdiomaLibroMaterialId]
GO