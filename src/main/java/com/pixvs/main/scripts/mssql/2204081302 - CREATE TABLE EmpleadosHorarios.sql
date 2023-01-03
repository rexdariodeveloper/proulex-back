SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- ==========================================================
-- Author: Rene Carrillo
-- Create date: 04/04/2022
-- Modified date:
-- Description:	Creamos la tabla para Empleados Horarios
-- ==========================================================

CREATE TABLE [dbo].[EmpleadosHorarios](
	[EMPH_EmpleadoHorarioId] [int] IDENTITY(1,1) NOT NULL,
	[EMPH_EMP_EmpleadoId] [int] NOT NULL,
	[EMPH_Dia] [varchar](20) NOT NULL,
	[EMPH_HoraInicio] [time](0) NULL,
	[EMPH_HoraFin] [time](0) NULL
PRIMARY KEY CLUSTERED
(
	[EMPH_EmpleadoHorarioId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmpleadosHorarios]  WITH CHECK ADD  CONSTRAINT [FK_EMPH_EMP_EmpleadoId] FOREIGN KEY([EMPH_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[EmpleadosHorarios] CHECK CONSTRAINT [FK_EMPH_EMP_EmpleadoId]
GO