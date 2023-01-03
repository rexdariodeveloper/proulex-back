/**
 * Created by César Hernández Soto on 28/06/2021.
 * Object:  Table [dbo].[EmpleadosCursos]
 */


/*******************/
/*Empleados Cursos**/
/*******************/

CREATE TABLE [dbo].[EmpleadosCursos](
	[EMPCU_EmpleadoCursoId] [int] IDENTITY(1,1) NOT NULL ,
	[EMPCU_EMP_EmpleadoId] [int] NOT NULL,
	[EMPCU_CMM_IdiomaId] [int] NOT NULL,
	[EMPCU_PROG_ProgramaId] [int] NOT NULL,
	[EMPCU_Comentarios] [varchar]  (150) NOT NULL,
	[EMPCU_Activo] [bit]  NOT NULL ,
PRIMARY KEY CLUSTERED 
(
	[EMPCU_EmpleadoCursoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmpleadosCursos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCU_CMM_IdiomaId] FOREIGN KEY([EMPCU_CMM_IdiomaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EmpleadosCursos] CHECK CONSTRAINT [FK_EMPCU_CMM_IdiomaId]
GO

ALTER TABLE [dbo].[EmpleadosCursos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCU_PROG_ProgramaId] FOREIGN KEY([EMPCU_PROG_ProgramaId])
REFERENCES [dbo].[Programas] ([PROG_ProgramaId])
GO

ALTER TABLE [dbo].[EmpleadosCursos] CHECK CONSTRAINT [FK_EMPCU_PROG_ProgramaId]
GO

ALTER TABLE [dbo].[EmpleadosCursos]  WITH CHECK ADD  CONSTRAINT [FK_EMPCU_EMP_EmpleadoId] FOREIGN KEY([EMPCU_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[EmpleadosCursos] CHECK CONSTRAINT [FK_EMPCU_EMP_EmpleadoId]
GO