/**
 * Created by David Arroyo Sánchez on 05/11/2020.
 * Object:  Table [dbo].[ProveedoresContactos]
 */


CREATE TABLE [dbo].[ProveedoresContactos](
	[PROC_ProveedorContactoId] [int] IDENTITY(1,1) NOT NULL,
	[PROC_PRO_ProveedorId] [int] NOT NULL,
	[PROC_Nombre] [varchar](100) NOT NULL,
	[PROC_PrimerApellido] [varchar](50) NOT NULL,
	[PROC_SegundoApellido] [varchar](50) NOT NULL,
	[PROC_DEP_DepartamentoId] [int] NOT NULL,
	[PROC_Telefono] [varchar](25) NOT NULL,
	[PROC_Extension] [varchar](3) NULL,
	[PROC_CorreoElectronico] [varchar](50) NOT NULL,
	[PROC_HorarioAtencion] [varchar](250) NOT NULL,
	[PROC_CMM_TipoContactoId] [int] NOT NULL,
	[PROC_Predeterminado] [bit] NOT NULL,
	[PROC_Activo] [bit] NOT NULL,
	[PROC_FechaCreacion] [datetime2](7) NOT NULL,
	[PROC_FechaModificacion] [datetime2](7) NULL,
	[PROC_USU_CreadoPorId] [int] NULL,
	[PROC_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[PROC_ProveedorContactoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProveedoresContactos]  WITH CHECK ADD  CONSTRAINT [FK_PROC_USU_CreadoPorId] FOREIGN KEY([PROC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProveedoresContactos] CHECK CONSTRAINT [FK_PROC_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ProveedoresContactos]  WITH CHECK ADD  CONSTRAINT [FK_PROC_USU_ModificadoPorId] FOREIGN KEY([PROC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProveedoresContactos] CHECK CONSTRAINT [FK_PROC_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[ProveedoresContactos]  WITH CHECK ADD  CONSTRAINT [FK_ProveedoresContactos_ControlesMaestrosMultiples] FOREIGN KEY([PROC_CMM_TipoContactoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProveedoresContactos] CHECK CONSTRAINT [FK_ProveedoresContactos_ControlesMaestrosMultiples]
GO

ALTER TABLE [dbo].[ProveedoresContactos]  WITH CHECK ADD  CONSTRAINT [FK_ProveedoresContactos_Departamentos] FOREIGN KEY([PROC_DEP_DepartamentoId])
REFERENCES [dbo].[Departamentos] ([DEP_DepartamentoId])
GO

ALTER TABLE [dbo].[ProveedoresContactos] CHECK CONSTRAINT [FK_ProveedoresContactos_Departamentos]
GO

ALTER TABLE [dbo].[ProveedoresContactos]  WITH CHECK ADD  CONSTRAINT [FK_ProveedoresContactos_Proveedores] FOREIGN KEY([PROC_PRO_ProveedorId])
REFERENCES [dbo].[Proveedores] ([PRO_ProveedorId])
GO

ALTER TABLE [dbo].[ProveedoresContactos] CHECK CONSTRAINT [FK_ProveedoresContactos_Proveedores]
GO

