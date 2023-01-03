--------------------- PAModalidad Horarios ---------------------------------------------
CREATE TABLE [dbo].[PAModalidadesHorarios](
	[PAMODH_PAModalidadHorarioId] [int] IDENTITY(1,1) NOT NULL,
	[PAMODH_PAMOD_ModalidadId] [int] NOT NULL,
	[PAMODH_Horario] [nvarchar](50) NOT NULL,
	[PAMODH_FechaUltimaModificacion] [datetime] NULL,
	[PAMODH_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[PAMODH_PAModalidadHorarioId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
---
ALTER TABLE [dbo].[PAModalidadesHorarios]  WITH CHECK ADD  CONSTRAINT [FK_PAMODH_PAMOD_ModalidadId] FOREIGN KEY([PAMODH_PAMOD_ModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO

ALTER TABLE [dbo].[PAModalidadesHorarios] CHECK CONSTRAINT [FK_PAMODH_PAMOD_ModalidadId]
GO

--

ALTER TABLE [dbo].[PAModalidadesHorarios]  WITH CHECK ADD  CONSTRAINT [FK_PAMODH_USU_ModificadoPorId] FOREIGN KEY([PAMODH_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[PAModalidadesHorarios] CHECK CONSTRAINT [FK_PAMODH_USU_ModificadoPorId]
GO

----------------------------------------- Grupos -------------------------------------------------------------
CREATE TABLE [dbo].[ProgramasGrupos](
	[PROGRU_GrupoId] [int] IDENTITY(1,1) NOT NULL,
	[PROGRU_SUC_SucursalId] [int] NOT NULL,
	[PROGRU_PROGI_ProgramaIdiomaId] [int] NOT NULL,
	[PROGRU_PAMOD_ModalidadId] [int] NOT NULL,
	[PROGRU_PAC_ProgramacionAcademicaComercialId] [int] NOT NULL,
	[PROGRU_FechaInicio] [date] NULL,
	[PROGRU_FechaFin] [date] NULL,
	[PROGRU_Nivel] [int] NOT NULL,
	[PROGRU_Grupo] [nvarchar](1) NOT NULL,
	[PROGRU_CMM_PlataformaId] [int] NOT NULL,
	[PROGRU_PAMODH_PAModalidadHorarioId] [int] NOT NULL,
	[PROGRU_CMM_TipoGrupoId] [int] NOT NULL,
	[PROGRU_Cupo] [int] NOT NULL,
	[PROGRU_EMP_EmpleadoId] [int] NULL,
	[PROGRU_Activo] [bit] NOT NULL,
	[PROGRU_FechaCreacion] [datetime2](7) NOT NULL,
	[PROGRU_USU_CreadoPorId] [int] NOT NULL,
	[PROGRU_FechaUltimaModificacion] [datetime2](7) NULL,
	[PROGRU_USU_ModificadoPorId] [int] NULL,
	CONSTRAINT [PK_ProgramasGrupos] PRIMARY KEY CLUSTERED 
(
	[PROGRU_GrupoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasGrupos] ADD  CONSTRAINT [DF_PROGRU_FechaCreacion]  DEFAULT (getdate()) FOR [PROGRU_FechaCreacion]
GO

---
ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_PROGI_ProgramaIdiomaId] FOREIGN KEY([PROGRU_PROGI_ProgramaIdiomaId])
REFERENCES [dbo].[ProgramasIdiomas] ([PROGI_ProgramaIdiomaId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_PROGI_ProgramaIdiomaId]
GO

---
ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_PAMOD_ModalidadId] FOREIGN KEY([PROGRU_PAMOD_ModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_PAMOD_ModalidadId]
GO

---
ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_PAC_ProgramacionAcademicaComercialId] FOREIGN KEY([PROGRU_PAC_ProgramacionAcademicaComercialId])
REFERENCES [dbo].[ProgramacionAcademicaComercial] ([PAC_ProgramacionAcademicaComercialId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_PAC_ProgramacionAcademicaComercialId]
GO

---
ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_CMM_PlataformaId] FOREIGN KEY([PROGRU_CMM_PlataformaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_CMM_PlataformaId]
GO

---
ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_PAMODH_PAModalidadHorarioId] FOREIGN KEY([PROGRU_PAMODH_PAModalidadHorarioId])
REFERENCES [dbo].[PAModalidadesHorarios] ([PAMODH_PAModalidadHorarioId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_PAMODH_PAModalidadHorarioId]
GO

---
ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_CMM_TipoGrupoId] FOREIGN KEY([PROGRU_CMM_TipoGrupoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_CMM_TipoGrupoId]
GO

---
ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_EMP_EmpleadoId] FOREIGN KEY([PROGRU_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_EMP_EmpleadoId]
GO

---
ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_USU_CreadoPorId] FOREIGN KEY([PROGRU_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_USU_CreadoPorId]
GO
---
ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_USU_ModificadoPorId] FOREIGN KEY([PROGRU_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_USU_ModificadoPorId]
GO