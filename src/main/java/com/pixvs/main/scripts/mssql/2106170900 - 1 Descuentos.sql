/****** Object:  Table [dbo].[Descuentos]    Script Date: 15/06/2021 13:34:41 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[PADescuentos](
	[PADESC_DescuentoId] [int] IDENTITY(1,1) NOT NULL,
	[PADESC_Codigo] [nvarchar](100) NOT NULL,
	[PADESC_Concepto] [nvarchar](100) NOT NULL,
	[PADESC_PorcentajeDescuento] [int] NOT NULL,
	[PADESC_FechaInicio] [date] NOT NULL,
	[PADESC_FechaFin] [date] NOT NULL,
	[PADESC_DescuentoRelacionadoCliente] [bit] NOT NULL,
	[PADESC_Activo] [bit] NOT NULL,
	[PADESC_FechaCreacion] [datetime2](7) NOT NULL,
	[PADESC_USU_CreadoPorId] [int] NOT NULL,
	[PADESC_FechaUltimaModificacion] [datetime2](7) NULL,
	[PADESC_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_Descuentos] PRIMARY KEY CLUSTERED 
(
	[PADESC_DescuentoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PADescuentos] ADD  CONSTRAINT [DF_PADESC_FechaCreacion]  DEFAULT (getdate()) FOR [PADESC_FechaCreacion]
GO

---
ALTER TABLE [dbo].[PADescuentos]  WITH CHECK ADD  CONSTRAINT [FK_PADESC_USU_CreadoPorId] FOREIGN KEY([PADESC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[PADescuentos] CHECK CONSTRAINT [FK_PADESC_USU_CreadoPorId]
GO
---
ALTER TABLE [dbo].[PADescuentos]  WITH CHECK ADD  CONSTRAINT [FK_PADESC_USU_ModificadoPorId] FOREIGN KEY([PADESC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[PADescuentos] CHECK CONSTRAINT [FK_PADESC_USU_ModificadoPorId]
GO

--------------------------------- Descuentos Detalles ----------------------------------------------------------
CREATE TABLE [dbo].[PADescuentosDetalles](
	[PADESCD_DescuentoDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[PADESCD_PADESC_DescuentoId] [int] NOT NULL,
	[PADESCD_PROG_ProgramaId] [int] NOT NULL,
	[PADESCD_PAMOD_ModalidadId] [int] NOT NULL,
	[PADESCD_PAMODH_PAModalidadHorarioId] [int] NOT NULL,
 CONSTRAINT [PK_DescuentosDetalles] PRIMARY KEY CLUSTERED 
(
	[PADESCD_DescuentoDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---Descuentos
ALTER TABLE [dbo].[PADescuentosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PADESCD_PADESC_DescuentoId] FOREIGN KEY([PADESCD_PADESC_DescuentoId])
REFERENCES [dbo].[PADescuentos] ([PADESC_DescuentoId])
GO

ALTER TABLE [dbo].[PADescuentosDetalles] CHECK CONSTRAINT [FK_PADESCD_PADESC_DescuentoId]
GO

---Programas
ALTER TABLE [dbo].[PADescuentosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PADESCD_PROG_ProgramaId] FOREIGN KEY([PADESCD_PROG_ProgramaId])
REFERENCES [dbo].[Programas] ([PROG_ProgramaId])
GO

ALTER TABLE [dbo].[PADescuentosDetalles] CHECK CONSTRAINT [FK_PADESCD_PROG_ProgramaId]
GO

---Modalidades
ALTER TABLE [dbo].[PADescuentosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PADESCD_PAMOD_ModalidadId] FOREIGN KEY([PADESCD_PAMOD_ModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO

ALTER TABLE [dbo].[PADescuentosDetalles] CHECK CONSTRAINT [FK_PADESCD_PAMOD_ModalidadId]
GO

---Modalidades Horarios
ALTER TABLE [dbo].[PADescuentosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PADESCD_PAMODH_PAModalidadHorarioId] FOREIGN KEY([PADESCD_PAMODH_PAModalidadHorarioId])
REFERENCES [dbo].[PAModalidadesHorarios] ([PAMODH_PAModalidadHorarioId])
GO

ALTER TABLE [dbo].[PADescuentosDetalles] CHECK CONSTRAINT [FK_PADESCD_PAMODH_PAModalidadHorarioId]
GO

--------------------------------- ProgramasIdiomasDescuentosDetalles ----------------------------------------------------------
CREATE TABLE [dbo].[ProgramasIdiomasDescuentosDetalles](
	[PIDD_DescuentoDetalleCursoId] [int] IDENTITY(1,1) NOT NULL,
	[PIDD_PADESCD_DescuentoDetalleId] [int] NOT NULL,
	[PIDD_PROGI_ProgramaIdiomaId] [int] NOT NULL
 CONSTRAINT [PK_DescuentosDetallesCursos] PRIMARY KEY CLUSTERED 
(
	[PIDD_DescuentoDetalleCursoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

---Descuentos destalles
ALTER TABLE [dbo].[ProgramasIdiomasDescuentosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PIDD_PADESCD_DescuentoDetalleId] FOREIGN KEY([PIDD_PADESCD_DescuentoDetalleId])
REFERENCES [dbo].[PADescuentosDetalles] ([PADESCD_DescuentoDetalleId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasDescuentosDetalles] CHECK CONSTRAINT [FK_PIDD_PADESCD_DescuentoDetalleId]
GO

---Programas Idiomas (cursos)
ALTER TABLE [dbo].[ProgramasIdiomasDescuentosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PIDD_PROGI_ProgramaIdiomaId] FOREIGN KEY([PIDD_PROGI_ProgramaIdiomaId])
REFERENCES [dbo].[ProgramasIdiomas] ([PROGI_ProgramaIdiomaId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasDescuentosDetalles] CHECK CONSTRAINT [FK_PIDD_PROGI_ProgramaIdiomaId]
GO