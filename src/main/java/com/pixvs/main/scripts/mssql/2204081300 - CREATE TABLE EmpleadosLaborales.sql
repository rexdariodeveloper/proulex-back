SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- ==========================================================
-- Author: Rene Carrillo
-- Create date: 04/04/2022
-- Modified date:
-- Description:	Creamos la tabla para Empleados Laborales
-- ==========================================================

CREATE TABLE [dbo].[EmpleadosLaborales](
	[EMPL_EmpleadoLaboralId] [int] IDENTITY(1,1) NOT NULL,
	[EMPL_EMP_EmpleadoId] [int] NOT NULL,
	[EMPL_CMM_JustificacionId] [int] NOT NULL,
	[EMPL_CMM_TipoContratoId] [int] NOT NULL,
	[EMPL_DEP_DepartamentoId] [int] NOT NULL,
	[EMPL_IngresosAdicionales] [decimal](10, 2) NULL,
	[EMPL_SueldoMensual] [decimal](10, 2) NOT NULL,
	[EMPL_FechaInicio] [date] NOT NULL,
	[EMPL_FechaFin] [date] NOT NULL,
	[EMPL_CMM_TipoHorarioId] [int] NOT NULL,
	[EMPL_CantidadHoraSemana] [int] NULL,
	[EMPL_Domicilio] [varchar](255) NOT NULL,
	[EMPL_CP] [varchar](10) NOT NULL,
	[EMPL_Colonia] [varchar](100) NOT NULL,
	[EMPL_PAI_PaisId] [smallint] NOT NULL,
	[EMPL_EST_EstadoId] [int] NOT NULL,
	[EMPL_MUN_MunicipioId] [int] NOT NULL,
	[EMPL_FechaCreacion] [datetime2](7) NOT NULL,
	[EMPL_USU_CreadoPorId] [int] NOT NULL,
	[EMPL_FechaUltimaModificacion] [datetime2](7) NULL,
	[EMPL_USU_ModificadoPorId] [int] NULL
 CONSTRAINT [PK_EmpleadosLaborales] PRIMARY KEY CLUSTERED
(
	[EMPL_EmpleadoLaboralId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmpleadosLaborales] ADD  CONSTRAINT [DF_EmpleadosLaborales_EMPL_FechaCreacion]  DEFAULT (getdate()) FOR [EMPL_FechaCreacion]
GO

ALTER TABLE [dbo].[EmpleadosLaborales]  WITH CHECK ADD  CONSTRAINT [FK_EMPL_EMP_EmpleadoId] FOREIGN KEY([EMPL_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[EmpleadosLaborales] CHECK CONSTRAINT [FK_EMPL_EMP_EmpleadoId]
GO

ALTER TABLE [dbo].[EmpleadosLaborales]  WITH CHECK ADD  CONSTRAINT [FK_EMPL_CMM_JustificacionId] FOREIGN KEY([EMPL_CMM_JustificacionId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EmpleadosLaborales] CHECK CONSTRAINT [FK_EMPL_CMM_JustificacionId]
GO

ALTER TABLE [dbo].[EmpleadosLaborales]  WITH CHECK ADD  CONSTRAINT [FK_EMPL_CMM_TipoContratoId] FOREIGN KEY([EMPL_CMM_TipoContratoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EmpleadosLaborales] CHECK CONSTRAINT [FK_EMPL_CMM_TipoContratoId]
GO

ALTER TABLE [dbo].[EmpleadosLaborales]  WITH CHECK ADD  CONSTRAINT [FK_EMPL_DEP_DepartamentoId] FOREIGN KEY([EMPL_DEP_DepartamentoId])
REFERENCES [dbo].[Departamentos] ([DEP_DepartamentoId])
GO

ALTER TABLE [dbo].[EmpleadosLaborales] CHECK CONSTRAINT [FK_EMPL_DEP_DepartamentoId]
GO

ALTER TABLE [dbo].[EmpleadosLaborales]  WITH CHECK ADD  CONSTRAINT [FK_EMPL_CMM_TipoHorarioId] FOREIGN KEY([EMPL_CMM_TipoHorarioId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EmpleadosLaborales] CHECK CONSTRAINT [FK_EMPL_CMM_TipoHorarioId]
GO

ALTER TABLE [dbo].[EmpleadosLaborales]  WITH CHECK ADD  CONSTRAINT [FK_EMPL_PAI_PaisId] FOREIGN KEY([EMPL_PAI_PaisId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO

ALTER TABLE [dbo].[EmpleadosLaborales] CHECK CONSTRAINT [FK_EMPL_PAI_PaisId]
GO

ALTER TABLE [dbo].[EmpleadosLaborales]  WITH CHECK ADD  CONSTRAINT [FK_EMPL_EST_EstadoId] FOREIGN KEY([EMPL_EST_EstadoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO

ALTER TABLE [dbo].[EmpleadosLaborales] CHECK CONSTRAINT [FK_EMPL_EST_EstadoId]
GO

ALTER TABLE [dbo].[EmpleadosLaborales]  WITH CHECK ADD  CONSTRAINT [FK_EMPL_MUN_MunicipioId] FOREIGN KEY([EMPL_MUN_MunicipioId])
REFERENCES [dbo].[Municipios] ([MUN_MunicipioId])
GO

ALTER TABLE [dbo].[EmpleadosLaborales] CHECK CONSTRAINT [FK_EMPL_MUN_MunicipioId]
GO

ALTER TABLE [dbo].[EmpleadosLaborales]  WITH CHECK ADD  CONSTRAINT [FK_EMPL_USU_CreadoPorId] FOREIGN KEY([EMPL_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[EmpleadosLaborales] CHECK CONSTRAINT [FK_EMPL_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[EmpleadosLaborales]  WITH CHECK ADD  CONSTRAINT [FK_EMPL_USU_ModificadoPorId] FOREIGN KEY([EMPL_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[EmpleadosLaborales] CHECK CONSTRAINT [FK_EMPL_USU_ModificadoPorId]
GO