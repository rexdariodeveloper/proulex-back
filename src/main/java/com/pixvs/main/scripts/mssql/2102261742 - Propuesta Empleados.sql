/****** Object:  Table [dbo].[Empleados]    Script Date: 29/01/2021 10:34:41 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Empleados](
	[EMP_EmpleadoId] [int] IDENTITY(1,1) NOT NULL,
	[EMP_Nombre] [nvarchar](50) NOT NULL,
	[EMP_PrimerApellido] [nvarchar](50) NOT NULL,
	[EMP_SegundoApellido] [nvarchar](50) NULL,
	[EMP_CMM_EstadoCivilId] [int] NOT NULL,
	[EMP_CMM_GeneroId] [int] NOT NULL,
	[EMP_FechaNacimiento] [date] NOT NULL,
	[EMP_EST_EstadoNacimientoId] [int] NOT NULL,
	[EMP_RFC] [nvarchar](20) NOT NULL,
	[EMP_CURP] [nvarchar](30) NOT NULL,
	[EMP_CorreoElectronico] [nvarchar](50) NOT NULL,
	[EMP_ARC_FotoId] [int] NULL,
	[EMP_CodigoEmpleado] [nvarchar](10) NOT NULL,
	[EMP_DEP_DepartamentoId] [int] NOT NULL,
	[EMP_FechaAlta] [date] NOT NULL,
	[EMP_CMM_TipoContratoId] [int] NOT NULL,
	[EMP_CMM_PuestoId] [int] NULL,
	[EMP_CMM_TipoEmpleadoId] [int] NOT NULL,
	[EMP_SUC_SucursalId] [int] NOT NULL,
	[EMP_SalarioDiario] [decimal](10,2) NOT NULL,
	[EMP_MON_MonedaId] [smallint] NOT NULL,
	[EMP_Domicilio] [nvarchar](200) NULL,
	[EMP_Colonia] [nvarchar](20) NOT NULL,
	[EMP_CP] [varchar]  (5) NOT NULL,
	[EMP_PAI_PaisId] [smallint]  NOT NULL,
	[EMP_EST_EstadoId] [int]  NOT NULL ,
	[EMP_Municipio] [nvarchar](50) NOT NULL,
	[EMP_TelefonoContacto] [nvarchar](50) NOT NULL,
	[EMP_USU_UsuarioId] [int] NULL,
	[EMP_Activo] [bit] NOT NULL,
	[EMP_FechaCreacion] [datetime2](7) NOT NULL,
	[EMP_USU_CreadoPorId] [int] NOT NULL,
	[EMP_FechaUltimaModificacion] [datetime2](7) NULL,
	[EMP_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_Empleados] PRIMARY KEY CLUSTERED 
(
	[EMP_EmpleadoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Empleados] ADD  CONSTRAINT [DF_Empleados_EMP_FechaCreacion]  DEFAULT (getdate()) FOR [EMP_FechaCreacion]
GO

ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_CMM_EstadoCivilId] FOREIGN KEY([EMP_CMM_EstadoCivilId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_CMM_EstadoCivilId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_EST_EstadoNacimientoId] FOREIGN KEY([EMP_EST_EstadoNacimientoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_EST_EstadoNacimientoId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_USU_UsuarioId] FOREIGN KEY([EMP_USU_UsuarioId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_USU_UsuarioId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_USU_CreadoPorId] FOREIGN KEY([EMP_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_USU_CreadoPorId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_USU_ModificadoPorId] FOREIGN KEY([EMP_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_USU_CreadoPorId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_CMM_GeneroId] FOREIGN KEY([EMP_CMM_GeneroId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_CMM_GeneroId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_PAI_PaisId] FOREIGN KEY([EMP_PAI_PaisId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_PAI_PaisId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_EST_EstadoId] FOREIGN KEY([EMP_EST_EstadoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_EST_EstadoId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_DEP_DepartamentoId] FOREIGN KEY([EMP_DEP_DepartamentoId])
REFERENCES [dbo].[Departamentos] ([DEP_DepartamentoId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_DEP_DepartamentoId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_CMM_PuestoId] FOREIGN KEY([EMP_CMM_PuestoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_CMM_PuestoId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_CMM_TipoContratoId] FOREIGN KEY([EMP_CMM_TipoContratoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_CMM_TipoContratoId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_CMM_TipoEmpleadoId] FOREIGN KEY([EMP_CMM_TipoEmpleadoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_CMM_TipoEmpleadoId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_SUC_SucursalId] FOREIGN KEY([EMP_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_SUC_SucursalId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_MON_MonedaId] FOREIGN KEY([EMP_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_MON_MonedaId]
GO
---
ALTER TABLE [dbo].[Empleados]  WITH CHECK ADD  CONSTRAINT [FK_EMP_ARC_FotoId] FOREIGN KEY([EMP_ARC_FotoId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[Empleados] CHECK CONSTRAINT [FK_EMP_ARC_FotoId]
GO