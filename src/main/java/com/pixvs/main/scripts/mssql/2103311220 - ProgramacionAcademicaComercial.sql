/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 * Object:  Table [dbo].[ProgramacionAcademicaComercial]
 */


/******************************************/
/***** ProgramacionAcademicaComercial *****/
/******************************************/

CREATE TABLE [dbo].[ProgramacionAcademicaComercial](
	[PAC_ProgramacionAcademicaComercialId] [int] IDENTITY(1,1) NOT NULL ,
	[PAC_Activo] [bit]  NOT NULL ,
	[PAC_Codigo] [varchar]  (150) NOT NULL ,
	[PAC_Nombre] [varchar]  (150) NOT NULL ,
	[PAC_PACIC_CicloId] [int]  NOT NULL ,
	[PAC_FechaCreacion] [datetime2](7) NOT NULL,
	[PAC_FechaModificacion] [datetime2](7) NULL,
	[PAC_USU_CreadoPorId] [int] NULL,
	[PAC_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[PAC_ProgramacionAcademicaComercialId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercial]  WITH CHECK ADD  CONSTRAINT [FK_PAC_PACIC_CicloId] FOREIGN KEY([PAC_PACIC_CicloId])
REFERENCES [dbo].[PACiclos] ([PACIC_CicloId])
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercial] CHECK CONSTRAINT [FK_PAC_PACIC_CicloId]
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercial]  WITH CHECK ADD  CONSTRAINT [FK_PAC_USU_ModificadoPorId] FOREIGN KEY([PAC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercial] CHECK CONSTRAINT [FK_PAC_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercial]  WITH CHECK ADD  CONSTRAINT [FK_PAC_USU_CreadoPorId] FOREIGN KEY([PAC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercial] CHECK CONSTRAINT [FK_PAC_USU_CreadoPorId]
GO

/**************************************************/
/***** ProgramacionAcademicaComercialDetalles *****/
/**************************************************/

CREATE TABLE [dbo].[ProgramacionAcademicaComercialDetalles](
	[PACD_ProgramacionAcademicaComercialDetalleId] [int] IDENTITY(1,1) NOT NULL ,
	[PACD_PAC_ProgramacionAcademicaComercialId] [int]  NOT NULL ,
	[PACD_PAMOD_ModalidadId] [int]  NOT NULL ,
	[PACD_FechaInicio] [date]  NOT NULL ,
	[PACD_FechaFin] [date]  NOT NULL ,
	[PACD_Comentarios] [varchar]  (255) NULL
PRIMARY KEY CLUSTERED 
(
	[PACD_ProgramacionAcademicaComercialDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercialDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PACD_PAC_ProgramacionAcademicaComercialId] FOREIGN KEY([PACD_PAC_ProgramacionAcademicaComercialId])
REFERENCES [dbo].[ProgramacionAcademicaComercial] ([PAC_ProgramacionAcademicaComercialId])
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercialDetalles] CHECK CONSTRAINT [FK_PACD_PAC_ProgramacionAcademicaComercialId]
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercialDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PACD_PAMOD_ModalidadId] FOREIGN KEY([PACD_PAMOD_ModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercialDetalles] CHECK CONSTRAINT [FK_PACD_PAMOD_ModalidadId]
GO

/**************************************************/
/***** ProgramacionAcademicaComercialDetallesProgramas *****/
/**************************************************/

CREATE TABLE [dbo].[ProgramacionAcademicaComercialDetallesProgramas](
	[PACDP_PACD_ProgramacionAcademicaComercialDetalleId] [int] NOT NULL ,
	[PACD_PROG_ProgramaId] [int]  NOT NULL
PRIMARY KEY CLUSTERED 
(
	[PACDP_PACD_ProgramacionAcademicaComercialDetalleId] ASC, [PACD_PROG_ProgramaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercialDetallesProgramas]  WITH CHECK ADD  CONSTRAINT [FK_PACDP_PACD_ProgramacionAcademicaComercialDetalleId] FOREIGN KEY([PACDP_PACD_ProgramacionAcademicaComercialDetalleId])
REFERENCES [dbo].[ProgramacionAcademicaComercialDetalles] ([PACD_ProgramacionAcademicaComercialDetalleId])
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercialDetallesProgramas] CHECK CONSTRAINT [FK_PACDP_PACD_ProgramacionAcademicaComercialDetalleId]
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercialDetallesProgramas]  WITH CHECK ADD  CONSTRAINT [FK_PACD_PROG_ProgramaId] FOREIGN KEY([PACD_PROG_ProgramaId])
REFERENCES [dbo].[Programas] ([PROG_ProgramaId])
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercialDetallesProgramas] CHECK CONSTRAINT [FK_PACD_PROG_ProgramaId]
GO

/***********************/
/***** Vista Excel *****/
/***********************/

CREATE   VIEW [dbo].[VW_LISTADO_PROGRAMACION_ACADEMICA_COMERCIAL] AS

SELECT PAC_Codigo AS "Código", PAC_Nombre AS "Nombre", MIN(PACD_FechaInicio) AS "Fecha Inicio", MAX(PACD_FechaInicio) AS "Fecha Fin", PACIC_Codigo AS "Ciclo", PAC_Activo AS "Estatus"
FROM ProgramacionAcademicaComercial
INNER JOIN ProgramacionAcademicaComercialDetalles ON PACD_PAC_ProgramacionAcademicaComercialId = PAC_ProgramacionAcademicaComercialId
INNER JOIN PACiclos ON PACIC_CicloId = PAC_PACIC_CicloId
GROUP BY PAC_ProgramacionAcademicaComercialId, PAC_Codigo, PAC_Nombre, PACIC_Codigo, PAC_Activo

GO

