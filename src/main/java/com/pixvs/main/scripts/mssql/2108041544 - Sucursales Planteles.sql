/**
 * Created by César Hernández Soto on 03/08/2021.
 * Object:  Table [dbo].[SucursalesPlanteles]
 */

/********************************/
/***** Sucursales Planteles *****/
/********************************/

CREATE TABLE [dbo].[SucursalesPlanteles](
	[SP_SucursalPlantelId] [int] IDENTITY(1,1) NOT NULL,
	[SP_SUC_SucursalId] [int] NOT NULL,
	[SP_Codigo] [varchar](3) NOT NULL,
	[SP_Nombre] [varchar](100) NOT NULL,
	[SP_USU_ResponsableId] [int] NOT NULL,
	[SP_ALM_AlmacenId] [int] NOT NULL,
	[SP_LOC_LocalidadId] [int] NOT NULL,
	[SP_Direccion] [varchar](100) NOT NULL,
	[SP_CP] [varchar](5) NOT NULL,
	[SP_Colonia] [varchar](50) NOT NULL,
	[SP_PAI_PaisId] [smallint] NOT NULL,
	[SP_EST_EstadoId] [int] NOT NULL,
	[SP_Municipio] [varchar](50) NOT NULL,
	[SP_CorreoElectronico] [varchar](50) NOT NULL,
	[SP_TelefonoFijo] [varchar](50) NULL,
	[SP_TelefonoMovil] [varchar](50) NULL,
	[SP_TelefonoTrabajo] [varchar](50) NULL,
	[SP_TelefonoTrabajoExtension] [varchar](10) NULL,
	[SP_TelefonoMensajeriaInstantanea] [varchar](50) NULL,
	[SP_Activo] [bit] NOT NULL,
	[SP_FechaCreacion] [datetime2](7) NOT NULL,
	[SP_FechaModificacion] [datetime2](7) NULL,
	[SP_USU_CreadoPorId] [int] NULL,
	[SP_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[SP_SucursalPlantelId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK
ALTER TABLE [dbo].[SucursalesPlanteles]  WITH CHECK ADD  CONSTRAINT [FK_SP_SUC_SucursalId] FOREIGN KEY([SP_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[SucursalesPlanteles] CHECK CONSTRAINT [FK_SP_SUC_SucursalId]
GO

ALTER TABLE [dbo].[SucursalesPlanteles]  WITH CHECK ADD  CONSTRAINT [FK_SP_USU_ResponsableId] FOREIGN KEY([SP_USU_ResponsableId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[SucursalesPlanteles] CHECK CONSTRAINT [FK_SP_USU_ResponsableId]
GO

ALTER TABLE [dbo].[SucursalesPlanteles]  WITH CHECK ADD  CONSTRAINT [FK_SP_ALM_AlmacenId] FOREIGN KEY([SP_ALM_AlmacenId])
REFERENCES [dbo].[Almacenes] ([ALM_AlmacenId])
GO

ALTER TABLE [dbo].[SucursalesPlanteles] CHECK CONSTRAINT [FK_SP_ALM_AlmacenId]
GO

ALTER TABLE [dbo].[SucursalesPlanteles]  WITH CHECK ADD  CONSTRAINT [FK_SP_LOC_LocalidadId] FOREIGN KEY([SP_LOC_LocalidadId])
REFERENCES [dbo].[Localidades] ([LOC_LocalidadId])
GO

ALTER TABLE [dbo].[SucursalesPlanteles] CHECK CONSTRAINT [FK_SP_LOC_LocalidadId]
GO

ALTER TABLE [dbo].[SucursalesPlanteles]  WITH CHECK ADD  CONSTRAINT [FK_SP_PAI_PaisId] FOREIGN KEY([SP_PAI_PaisId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO

ALTER TABLE [dbo].[SucursalesPlanteles] CHECK CONSTRAINT [FK_SP_PAI_PaisId]
GO

ALTER TABLE [dbo].[SucursalesPlanteles]  WITH CHECK ADD  CONSTRAINT [FK_SP_EST_EstadoId] FOREIGN KEY([SP_EST_EstadoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO

ALTER TABLE [dbo].[SucursalesPlanteles] CHECK CONSTRAINT [FK_SP_EST_EstadoId]
GO

ALTER TABLE [dbo].[SucursalesPlanteles]  WITH CHECK ADD  CONSTRAINT [FK_SP_USU_CreadoPorId] FOREIGN KEY([SP_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[SucursalesPlanteles] CHECK CONSTRAINT [FK_SP_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[SucursalesPlanteles]  WITH CHECK ADD  CONSTRAINT [FK_SP_USU_ModificadoPorId] FOREIGN KEY([SP_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[SucursalesPlanteles] CHECK CONSTRAINT [FK_SP_USU_ModificadoPorId]
GO