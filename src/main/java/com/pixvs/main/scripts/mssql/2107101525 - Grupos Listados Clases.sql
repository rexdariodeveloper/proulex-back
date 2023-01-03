/**
 * Created by César Hernández Soto on 06/07/2021.
 * Object:  Table [dbo].[ProgramasGruposListadoClases]
 */


/*******************/
/*Programas Grupos Listado Clases**/
/*******************/

CREATE TABLE [dbo].[ProgramasGruposListadoClases](
	[PROGRULC_ProgramaGrupoListadoClaseId] [int] IDENTITY(1,1) NOT NULL ,
	[PROGRULC_EMP_EmpleadoId] [int] NOT NULL,
	[PROGRULC_PROGRU_GrupoId] [int] NOT NULL,
	[PROGRULC_Fecha] [date] NOT NULL,
	[PROGRULC_Activo] [bit]  NOT NULL DEFAULT(1),
PRIMARY KEY CLUSTERED 
(
	[PROGRULC_ProgramaGrupoListadoClaseId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasGruposListadoClases]  WITH CHECK ADD  CONSTRAINT [FK_PROGRULC_EMP_EmpleadoId] FOREIGN KEY([PROGRULC_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[ProgramasGruposListadoClases] CHECK CONSTRAINT [FK_PROGRULC_EMP_EmpleadoId]
GO

ALTER TABLE [dbo].[ProgramasGruposListadoClases]  WITH CHECK ADD  CONSTRAINT [FK_PROGRULC_PROGRU_GrupoId] FOREIGN KEY([PROGRULC_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposListadoClases] CHECK CONSTRAINT [FK_PROGRULC_PROGRU_GrupoId]
GO