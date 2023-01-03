--------------------- Prenomina ---------------------------------------------
CREATE TABLE [dbo].[Prenominas](
	[PRENO_PrenominaId] [int] IDENTITY(1,1) NOT NULL,
	[PRENO_PROGRU_GrupoId] [int] NULL,
	[PRENO_EDP_EmpleadoDeduccionPercepcionId] [int] NULL,
	[PRENO_FechaInicioQuincena] [date] NOT NULL,
	[PRENO_FechaFinQuincena] [date] NOT NULL,
	[PRENO_Activo] [bit] NOT NULL,
	[PRENO_FechaCreacion] [datetime2](7) NOT NULL,
	[PRENO_USU_CreadoPorId] [int] NOT NULL,
	[PRENO_FechaUltimaModificacion] [datetime2](7) NULL,
	[PRENO_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[PRENO_PrenominaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---
ALTER TABLE [dbo].[Prenominas]  WITH CHECK ADD  CONSTRAINT [FK_PRENO_PROGRU_GrupoId] FOREIGN KEY([PRENO_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO

ALTER TABLE [dbo].[Prenominas] CHECK CONSTRAINT [FK_PRENO_PROGRU_GrupoId]
GO

---
ALTER TABLE [dbo].[Prenominas]  WITH CHECK ADD  CONSTRAINT [FK_PRENO_EDP_EmpleadoDeduccionPercepcionId] FOREIGN KEY([PRENO_EDP_EmpleadoDeduccionPercepcionId])
REFERENCES [dbo].[EmpleadosDeduccionesPercepciones] ([EDP_EmpleadoDeduccionPercepcionId])
GO

ALTER TABLE [dbo].[Prenominas] CHECK CONSTRAINT [FK_PRENO_EDP_EmpleadoDeduccionPercepcionId]
GO

---
ALTER TABLE [dbo].[Prenominas]  WITH CHECK ADD  CONSTRAINT [FK_PRENO_USU_CreadoPorId] FOREIGN KEY([PRENO_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Prenominas] CHECK CONSTRAINT [FK_PRENO_USU_CreadoPorId]
GO
---
ALTER TABLE [dbo].[Prenominas]  WITH CHECK ADD  CONSTRAINT [FK_PRENO_USU_ModificadoPorId] FOREIGN KEY([PRENO_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Prenominas] CHECK CONSTRAINT [FK_PRENO_USU_ModificadoPorId]
GO