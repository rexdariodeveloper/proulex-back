----------------------------------------- Programas -------------------------------------------------------------

CREATE TABLE [dbo].[Programas](
	[PROG_ProgramaId] [int] IDENTITY(1,1) NOT NULL,
	[PROG_Codigo] [nvarchar](10) NOT NULL,
	[PROG_Nombre] [nvarchar](30) NOT NULL,
	[PROG_Activo] [bit] NOT NULL,
	[PROG_FechaCreacion] [datetime2](7) NOT NULL,
	[PROG_USU_CreadoPorId] [int] NOT NULL,
	[PROG_FechaModificacion] [datetime2](7) NULL,
	[PROG_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_Programas] PRIMARY KEY CLUSTERED 
(
	[PROG_ProgramaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
​
ALTER TABLE [dbo].[Programas] ADD  CONSTRAINT [DF_PROG_FechaCreacion]  DEFAULT (getdate()) FOR [PROG_FechaCreacion]
GO

---
ALTER TABLE [dbo].[Programas]  WITH CHECK ADD  CONSTRAINT [FK_PROG_USU_CreadoPorId] FOREIGN KEY([PROG_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Programas] CHECK CONSTRAINT [FK_PROG_USU_CreadoPorId]
GO
---
ALTER TABLE [dbo].[Programas]  WITH CHECK ADD  CONSTRAINT [FK_PROG_USU_ModificadoPorId] FOREIGN KEY([PROG_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Programas] CHECK CONSTRAINT [FK_PROG_USU_ModificadoPorId]
GO

----------------------------------------- Programas Idiomas -------------------------------------------------------------
CREATE TABLE [dbo].[ProgramasIdiomas](
	[PROGI_ProgramaIdiomaId] [int] IDENTITY(1,1) NOT NULL,
	[PROGI_PROG_ProgramaId] [int] NOT NULL,
	[PROGI_CMM_Idioma] [int] NOT NULL,
	[PROGI_HorasTotales] [int] NOT NULL,
	[PROGI_NumeroNiveles] [int] NOT NULL,
	[PROGI_CalificacionMinima] [decimal](10,2) NOT NULL,
	[PROGI_MCER] [nvarchar](50) NOT NULL,
	[PROGI_UM_UnidadMedidaId] [smallint] NOT NULL,
	[PROGI_CLAVE] [nvarchar](8) NOT NULL,
	[PROGI_ExamenEvaluacion] [bit] NULL,
	[PROGI_FechaModificacion] [datetime2](7) NULL,
	[PROGI_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_ProgramasIdiomas] PRIMARY KEY CLUSTERED 
(
	[PROGI_ProgramaIdiomaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


---
ALTER TABLE [dbo].[ProgramasIdiomas]  WITH CHECK ADD  CONSTRAINT [FK_PROGI_PROG_ProgramaId] FOREIGN KEY([PROGI_PROG_ProgramaId])
REFERENCES [dbo].[Programas] ([PROG_ProgramaId])
GO

ALTER TABLE [dbo].[ProgramasIdiomas] CHECK CONSTRAINT [FK_PROGI_PROG_ProgramaId]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomas]  WITH CHECK ADD  CONSTRAINT [FK_PROGI_UM_UnidadMedidaId] FOREIGN KEY([PROGI_UM_UnidadMedidaId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO

ALTER TABLE [dbo].[ProgramasIdiomas] CHECK CONSTRAINT [FK_PROGI_PROG_ProgramaId]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomas]  WITH CHECK ADD  CONSTRAINT [FK_PROGI_CMM_Idioma] FOREIGN KEY([PROGI_CMM_Idioma])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasIdiomas] CHECK CONSTRAINT [FK_PROGI_CMM_Idioma]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomas]  WITH CHECK ADD  CONSTRAINT [FK_PROGI_USU_ModificadoPorId] FOREIGN KEY([PROGI_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomas] CHECK CONSTRAINT [FK_PROGI_USU_ModificadoPorId]
GO


----------------------------------------- Programas Idiomas Modalidades -------------------------------------------------------------
CREATE TABLE [dbo].[ProgramasIdiomasModalidades](
	[PROGIM_ProgramaIdiomaModalidadId] [int] IDENTITY(1,1) NOT NULL,
	[PROGIM_PROGI_ProgramaIdiomaId] [int] NOT NULL,
	[PROGIM_PAMOD_ModalidadId] [int] NOT NULL,
	[PROGIM_FechaModificacion] [datetime2](7) NULL,
	[PROGIM_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_ProgramasIdiomasModalidades] PRIMARY KEY CLUSTERED 
(
	[PROGIM_ProgramaIdiomaModalidadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasModalidades]  WITH CHECK ADD  CONSTRAINT [FK_PROGIM_PROGI_ProgramaIdiomaId] FOREIGN KEY([PROGIM_PROGI_ProgramaIdiomaId])
REFERENCES [dbo].[ProgramasIdiomas] ([PROGI_ProgramaIdiomaId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasModalidades] CHECK CONSTRAINT [FK_PROGIM_PROGI_ProgramaIdiomaId]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasModalidades]  WITH CHECK ADD  CONSTRAINT [FK_PROGIM_PAMOD_ModalidadId] FOREIGN KEY([PROGIM_PAMOD_ModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasModalidades] CHECK CONSTRAINT [FK_PROGIM_PAMOD_ModalidadId]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasModalidades]  WITH CHECK ADD  CONSTRAINT [FK_PROGIM_USU_ModificadoPorId] FOREIGN KEY([PROGIM_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasModalidades] CHECK CONSTRAINT [FK_PROGIM_USU_ModificadoPorId]
GO

----------------------------------------- Programas Idiomas Sedes (Sucursales) -------------------------------------------------------------
CREATE TABLE [dbo].[ProgramasIdiomasSucursales](
	[PROGIS_ProgramaIdiomaSucursalId] [int] IDENTITY(1,1) NOT NULL,
	[PROGIS_PROGI_ProgramaIdiomaId] [int] NOT NULL,
	[PROGIS_SUC_SucursalId] [int] NOT NULL,
	[PROGIS_FechaModificacion] [datetime2](7) NULL,
	[PROGIS_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_ProgramasIdiomasSucursales] PRIMARY KEY CLUSTERED 
(
	[PROGIS_ProgramaIdiomaSucursalId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasSucursales]  WITH CHECK ADD  CONSTRAINT [FK_PROGIS_PROGI_ProgramaIdiomaId] FOREIGN KEY([PROGIS_PROGI_ProgramaIdiomaId])
REFERENCES [dbo].[ProgramasIdiomas] ([PROGI_ProgramaIdiomaId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasSucursales] CHECK CONSTRAINT [FK_PROGIS_PROGI_ProgramaIdiomaId]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasSucursales]  WITH CHECK ADD  CONSTRAINT [FK_PROGIS_SUC_SucursalId] FOREIGN KEY([PROGIS_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasSucursales] CHECK CONSTRAINT [FK_PROGIS_SUC_SucursalId]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasSucursales]  WITH CHECK ADD  CONSTRAINT [FK_PROGIS_USU_ModificadoPorId] FOREIGN KEY([PROGIS_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasSucursales] CHECK CONSTRAINT [FK_PROGIS_USU_ModificadoPorId]
GO

----------------------------------------- Programas Idiomas Certificación -------------------------------------------------------------
CREATE TABLE [dbo].[ProgramasIdiomasCertificacion](
	[PROGIC_ProgramaIdiomaCertificacionId] [int] IDENTITY(1,1) NOT NULL,
	[PROGIC_PROGI_ProgramaIdiomaId] [int] NOT NULL,
	[PROGIC_Nivel] [nvarchar](10) NOT NULL,
	[PROGIC_Certificacion] [nvarchar](20) NOT NULL,
	[PROGIC_Precio] [decimal](10,2) NOT NULL,
	[PROGIC_FechaModificacion] [datetime2](7) NULL,
	[PROGIC_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_ProgramasIdiomasCertificacion] PRIMARY KEY CLUSTERED 
(
	[PROGIC_ProgramaIdiomaCertificacionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasCertificacion]  WITH CHECK ADD  CONSTRAINT [FK_PROGIC_PROGI_ProgramaIdiomaId] FOREIGN KEY([PROGIC_PROGI_ProgramaIdiomaId])
REFERENCES [dbo].[ProgramasIdiomas] ([PROGI_ProgramaIdiomaId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacion] CHECK CONSTRAINT [FK_PROGIC_PROGI_ProgramaIdiomaId]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasCertificacion]  WITH CHECK ADD  CONSTRAINT [FK_PROGIC_USU_ModificadoPorId] FOREIGN KEY([PROGIC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacion] CHECK CONSTRAINT [FK_PROGIC_USU_ModificadoPorId]
GO

----------------------------------------- Programas Idiomas Libros Materiales -------------------------------------------------------------
CREATE TABLE [dbo].[ProgramasIdiomasLibrosMateriales](
	[PROGILM_ProgramaIdiomaLibroMaterialId] [int] IDENTITY(1,1) NOT NULL,
	[PROGILM_PROGI_ProgramaIdiomaId] [int] NOT NULL,
	[PROGILM_Nivel] [int] NOT NULL,
	[PROGILM_ART_ArticuloId] [int] NOT NULL,
	[PROGILM_FechaModificacion] [datetime2](7) NULL,
	[PROGILM_USU_ModificadoPorId] [int] NULL 
	CONSTRAINT [PK_ProgramasIdiomasLibrosMateriales] PRIMARY KEY CLUSTERED 
(
	[PROGILM_ProgramaIdiomaLibroMaterialId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasLibrosMateriales]  WITH CHECK ADD  CONSTRAINT [FK_PROGILM_PROGI_ProgramaIdiomaId] FOREIGN KEY([PROGILM_PROGI_ProgramaIdiomaId])
REFERENCES [dbo].[ProgramasIdiomas] ([PROGI_ProgramaIdiomaId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasLibrosMateriales] CHECK CONSTRAINT [FK_PROGILM_PROGI_ProgramaIdiomaId]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasLibrosMateriales]  WITH CHECK ADD  CONSTRAINT [FK_PROGILM_USU_ModificadoPorId] FOREIGN KEY([PROGILM_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasLibrosMateriales] CHECK CONSTRAINT [FK_PROGILM_USU_ModificadoPorId]
GO

---
ALTER TABLE [dbo].[ProgramasIdiomasLibrosMateriales]  WITH CHECK ADD  CONSTRAINT [FK_PROGILM_ART_ArticuloId] FOREIGN KEY([PROGILM_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasLibrosMateriales] CHECK CONSTRAINT [FK_PROGILM_ART_ArticuloId]
GO