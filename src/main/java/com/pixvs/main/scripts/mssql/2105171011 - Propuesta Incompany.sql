--------------------- Programas Grupos Incompany ---------------------------------------------
CREATE TABLE [dbo].[ProgramasGruposIncompany](
	[PGINC_ProgramaIncompanyId] [int] IDENTITY(1,1) NOT NULL,
	[PGINC_Codigo] [nvarchar](30) NOT NULL,
	[PGINC_SUC_SucursalId] [int] NOT NULL,
	[PGINC_CLI_ClienteId] [int] NOT NULL,
	[PGINC_Activo] [bit] NOT NULL,
	[PGINC_FechaCreacion] [datetime2](7) NOT NULL,
	[PGINC_USU_CreadoPorId] [int] NOT NULL,
	[PGINC_FechaUltimaModificacion] [datetime2](7) NULL,
	[PGINC_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[PGINC_ProgramaIncompanyId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasGruposIncompany] ADD  CONSTRAINT [DF_PGINC_FechaCreacion]  DEFAULT (getdate()) FOR [PGINC_FechaCreacion]
GO

ALTER TABLE [dbo].[ProgramasGruposIncompany]  WITH CHECK ADD  CONSTRAINT [FK_PGINC_SUC_SucursalId] FOREIGN KEY([PGINC_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompany] CHECK CONSTRAINT [FK_PGINC_SUC_SucursalId]
GO

ALTER TABLE [dbo].[ProgramasGruposIncompany]  WITH CHECK ADD  CONSTRAINT [FK_PGINC_CLI_ClienteId] FOREIGN KEY([PGINC_CLI_ClienteId])
REFERENCES [dbo].[Clientes] ([CLI_ClienteId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompany] CHECK CONSTRAINT [FK_PGINC_CLI_ClienteId]
GO

ALTER TABLE [dbo].[ProgramasGruposIncompany]  WITH CHECK ADD  CONSTRAINT [FK_PGINC_USU_CreadoPorId] FOREIGN KEY([PGINC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompany] CHECK CONSTRAINT [FK_PGINC_USU_CreadoPorId]
GO
---
ALTER TABLE [dbo].[ProgramasGruposIncompany]  WITH CHECK ADD  CONSTRAINT [FK_PGINC_USU_ModificadoPorId] FOREIGN KEY([PGINC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompany] CHECK CONSTRAINT [FK_PGINC_USU_ModificadoPorId]
GO

--------------------- Programas Grupos Incompany Archivos ---------------------------------------------
CREATE TABLE [dbo].[ProgramasGruposIncompanyArchivos](
	[PGINCA_ProgramaIncompanyArchivoId] [int] IDENTITY(1,1) NOT NULL,
	[PGINCA_PGINC_ProgramaIncompanyId] [int] NOT NULL,
	[PGINCA_ARC_ArchivoId] [int] NOT NULL
PRIMARY KEY CLUSTERED 
(
	[PGINCA_ProgramaIncompanyArchivoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
---
ALTER TABLE [dbo].[ProgramasGruposIncompanyArchivos]  WITH CHECK ADD  CONSTRAINT [FK_PGINCA_PGINC_ProgramaIncompanyId] FOREIGN KEY([PGINCA_PGINC_ProgramaIncompanyId])
REFERENCES [dbo].[ProgramasGruposIncompany] ([PGINC_ProgramaIncompanyId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyArchivos] CHECK CONSTRAINT [FK_PGINCA_PGINC_ProgramaIncompanyId]
GO
---
ALTER TABLE [dbo].[ProgramasGruposIncompanyArchivos]  WITH CHECK ADD  CONSTRAINT [FK_PGINCA_ARC_ArchivoId] FOREIGN KEY([PGINCA_ARC_ArchivoId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyArchivos] CHECK CONSTRAINT [FK_PGINCA_ARC_ArchivoId]
GO

--------------------- Programas Grupos Incompany Grupo ---------------------------------------------
CREATE TABLE [dbo].[ProgramasGruposIncompanyGrupos](
	[PGINCG_ProgramaIncompanyGrupoId] [int] IDENTITY(1,1) NOT NULL,
	[PGINCG_PGINC_ProgramaIncompanyId] [int] NOT NULL,
	[PGINCG_Codigo] [nvarchar](100) NOT NULL,
	[PGINCG_Nombre] [nvarchar](100) NOT NULL,
	[PGINCG_PROGI_ProgramaIdiomaId][int] NOT NULL,
	[PGINCG_Nivel][int] NOT NULL,
	[PGINCG_Alias] [nvarchar](100) NOT NULL,
	[PGINCG_PAMOD_ModalidadId][int] NULL,
	[PGINCG_CMM_TipoGrupoId] [int] NOT NULL,
	[PGINCG_FechaInicio] [date] NOT NULL,
	[PGINCG_FechaFin] [date] NOT NULL,
	[PGINCG_CalificacionMinima] [decimal](10,2) NOT NULL,
	[PGINCG_FaltasPermitida][int] NOT NULL,
	[PGINCG_Cupo][int] NOT NULL,
	[PGINCG_CMM_PlataformaId] [int] NOT NULL,
	[PGINCG_EMP_EmpleadoId] [int] NOT NULL,
	[PGINCG_FechaUltimaModificacion] [datetime2](7) NULL,
	[PGINCG_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[PGINCG_ProgramaIncompanyGrupoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
---Grupos Incompany
ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PGINCG_PGINC_ProgramaIncompanyId] FOREIGN KEY([PGINCG_PGINC_ProgramaIncompanyId])
REFERENCES [dbo].[ProgramasGruposIncompany] ([PGINC_ProgramaIncompanyId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos] CHECK CONSTRAINT [FK_PGINCG_PGINC_ProgramaIncompanyId]
GO
---Programa Idioma
ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PGINCG_PROGI_ProgramaIdiomaId] FOREIGN KEY([PGINCG_PROGI_ProgramaIdiomaId])
REFERENCES [dbo].[ProgramasIdiomas] ([PROGI_ProgramaIdiomaId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos] CHECK CONSTRAINT [FK_PGINCG_PROGI_ProgramaIdiomaId]
GO
---Modalidad
ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PGINCG_PAMOD_ModalidadId] FOREIGN KEY([PGINCG_PAMOD_ModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos] CHECK CONSTRAINT [FK_PGINCG_PAMOD_ModalidadId]
GO
---Tipo grupo
ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PGINCG_CMM_TipoGrupoId] FOREIGN KEY([PGINCG_CMM_TipoGrupoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos] CHECK CONSTRAINT [FK_PGINCG_CMM_TipoGrupoId]
GO
---Plataforma
ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PGINCG_CMM_PlataformaId] FOREIGN KEY([PGINCG_CMM_PlataformaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos] CHECK CONSTRAINT [FK_PGINCG_CMM_PlataformaId]
GO
---Empleado
ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PGINCG_EMP_EmpleadoId] FOREIGN KEY([PGINCG_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos] CHECK CONSTRAINT [FK_PGINCG_EMP_EmpleadoId]
GO
---Usuario modificador
ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PGINCG_USU_ModificadoPorId] FOREIGN KEY([PGINCG_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyGrupos] CHECK CONSTRAINT [FK_PGINCG_USU_ModificadoPorId]
GO

--------------------- Programas Grupos Incompany Horario ---------------------------------------------
CREATE TABLE [dbo].[ProgramasGruposIncompanyHorarios](
	[PGINCH_ProgramaIncompanyHorarioId] [int] IDENTITY(1,1) NOT NULL,
	[PGINCH_PGINCG_ProgramaIncompanyGrupoId] [int] NOT NULL,
	[PGINCH_Dia] [varchar](20) NOT NULL, 
	[PGINCH_HoraInicio] [time](0) NULL, 
	[PGINCH_HoraFin] [time](0) NULL,
	[PGINCH_FechaUltimaModificacion] [datetime2](7) NULL,
	[PGINCH_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[PGINCH_ProgramaIncompanyHorarioId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
---Grupos Incompany Horario
ALTER TABLE [dbo].[ProgramasGruposIncompanyHorarios]  WITH CHECK ADD  CONSTRAINT [FK_PGINCH_PGINCG_ProgramaIncompanyGrupoId] FOREIGN KEY([PGINCH_PGINCG_ProgramaIncompanyGrupoId])
REFERENCES [dbo].[ProgramasGruposIncompanyGrupos] ([PGINCG_ProgramaIncompanyGrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyHorarios] CHECK CONSTRAINT [FK_PGINCH_PGINCG_ProgramaIncompanyGrupoId]
GO
---Usuario modificador
ALTER TABLE [dbo].[ProgramasGruposIncompanyHorarios]  WITH CHECK ADD  CONSTRAINT [FK_PGINCH_USU_ModificadoPorId] FOREIGN KEY([PGINCH_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyHorarios] CHECK CONSTRAINT [FK_PGINCH_USU_ModificadoPorId]
GO

--------------------- Programas Grupos Incompany Criterios Evaluacion ---------------------------------------------
CREATE TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion](
	[PGINCCE_ProgramaIncompanyHorarioId] [int] IDENTITY(1,1) NOT NULL,
	[PGINCCE_PGINCG_ProgramaIncompanyGrupoId] [int] NOT NULL,
	[PGINCCE_PAAE_ActividadEvaluacionId] [int] NOT NULL,
	[PGINCCE_PAMOD_ModalidadId] [int] NOT NULL,
	[PGINCCE_CMM_TestFormatId] [int] NOT NULL,
	[PGINCCE_FechaAplica] [date] NOT NULL,
	[PGINCCE_Score] [int] NOT NULL,
	[PGINCCE_Time] [int] NOT NULL,
	[PGINCCE_Activo] [bit] default(1) NOT NULL,
	[PGINCCE_FechaUltimaModificacion] [datetime2](7) NULL,
	[PGINCCE_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[PGINCCE_ProgramaIncompanyHorarioId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
---Grupos Incompany Grupo
ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion]  WITH CHECK ADD  CONSTRAINT [FK_PGINCCE_PGINCG_ProgramaIncompanyGrupoId] FOREIGN KEY([PGINCCE_PGINCG_ProgramaIncompanyGrupoId])
REFERENCES [dbo].[ProgramasGruposIncompanyGrupos] ([PGINCG_ProgramaIncompanyGrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion] CHECK CONSTRAINT [FK_PGINCCE_PGINCG_ProgramaIncompanyGrupoId]
GO
---Test
ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion]  WITH CHECK ADD  CONSTRAINT [FK_PGINCCE_PAAE_ActividadEvaluacionId] FOREIGN KEY([PGINCCE_PAAE_ActividadEvaluacionId])
REFERENCES [dbo].[PAActividadesEvaluacion] ([PAAE_ActividadEvaluacionId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion] CHECK CONSTRAINT [FK_PGINCCE_PAAE_ActividadEvaluacionId]
GO
---Test Format
ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion]  WITH CHECK ADD  CONSTRAINT [FK_PGINCCE_CMM_TestFormatId] FOREIGN KEY([PGINCCE_CMM_TestFormatId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion] CHECK CONSTRAINT [FK_PGINCCE_CMM_TestFormatId]
GO
---Usuario modificador
ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion]  WITH CHECK ADD  CONSTRAINT [FK_PGINCCE_USU_ModificadoPorId] FOREIGN KEY([PGINCCE_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion] CHECK CONSTRAINT [FK_PGINCCE_USU_ModificadoPorId]
GO

--------------------- Programas Grupos Incompany Clases Canceladas ---------------------------------------------
CREATE TABLE [dbo].[ProgramasGruposIncompanyClasesCanceladas](
	[PGINCCL_ProgramaIncompanyClaseCanceladaId] [int] IDENTITY(1,1) NOT NULL,
	[PGINCCL_PGINCG_ProgramaIncompanyGrupoId] [int] NOT NULL,
	[PGINCCL_FechaCancelar] [date] NOT NULL
PRIMARY KEY CLUSTERED 
(
	[PGINCCL_ProgramaIncompanyClaseCanceladaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---Grupos Incompany Grupo
ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesCanceladas]  WITH CHECK ADD  CONSTRAINT [FK_PGINCCL_PGINCG_ProgramaIncompanyGrupoId] FOREIGN KEY([PGINCCL_PGINCG_ProgramaIncompanyGrupoId])
REFERENCES [dbo].[ProgramasGruposIncompanyGrupos] ([PGINCG_ProgramaIncompanyGrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesCanceladas] CHECK CONSTRAINT [FK_PGINCCL_PGINCG_ProgramaIncompanyGrupoId]
GO
--------------------- Programas Grupos Incompany Clases Reprogramadas ---------------------------------------------
CREATE TABLE [dbo].[ProgramasGruposIncompanyClasesReprogramadas](
	[PGINCCR_ProgramaIncompanyHorarioId] [int] IDENTITY(1,1) NOT NULL,
	[PGINCCR_PGINCG_ProgramaIncompanyGrupoId] [int] NOT NULL,
	[PGINCCR_FechaReprogramar] [date] NOT NULL,
	[PGINCCR_FechaNueva] [date] NOT NULL,
	[PGINCCR_HoraInicio] [time](0) NULL,
	[PGINCCR_HoraFin] [time](0) NULL,
PRIMARY KEY CLUSTERED 
(
	[PGINCCR_ProgramaIncompanyHorarioId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
---Grupos Incompany Grupo
ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesReprogramadas]  WITH CHECK ADD  CONSTRAINT [FK_PGINCCR_PGINCG_ProgramaIncompanyGrupoId] FOREIGN KEY([PGINCCR_PGINCG_ProgramaIncompanyGrupoId])
REFERENCES [dbo].[ProgramasGruposIncompanyGrupos] ([PGINCG_ProgramaIncompanyGrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesReprogramadas] CHECK CONSTRAINT [FK_PGINCCR_PGINCG_ProgramaIncompanyGrupoId]
GO

--------------------- Programas Grupos Incompany Materiales ---------------------------------------------
CREATE TABLE [dbo].[ProgramasGruposIncompanyMateriales](
	[PGINCM_ProgramaIncompanyMaterialId] [int] IDENTITY(1,1) NOT NULL,
	[PGINCM_PGINCG_ProgramaIncompanyGrupoId] [int] NOT NULL,
	[PGINCM_ART_ArticuloId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[PGINCM_ProgramaIncompanyMaterialId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---Articulos
ALTER TABLE [dbo].[ProgramasGruposIncompanyMateriales]  WITH CHECK ADD  CONSTRAINT [FK_PGINCM_ART_ArticuloId] FOREIGN KEY([PGINCM_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyMateriales] CHECK CONSTRAINT [FK_PGINCM_ART_ArticuloId]
GO