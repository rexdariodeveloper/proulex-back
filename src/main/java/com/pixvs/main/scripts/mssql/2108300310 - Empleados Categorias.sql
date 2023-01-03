/**
 * Created by Hernandez Soto Cesar on 27/08/2021.
 * Object:  Table [dbo].[[EmpleadosCategorias]]
 */

/**************************************************/
/***** [EmpleadosCategorias] *****/
/**************************************************/

CREATE TABLE [dbo].[EmpleadosCategorias](
	[EMPCA_EmpleadoCategoriaId] [int] IDENTITY(1,1) NOT NULL,
	[EMPCA_EMP_EmpleadoId] [int] NOT NULL,
    [EMPCA_CMM_IdiomaId] [int] NOT NULL,
	[EMPCA_PAPC_ProfesorCategoriaId] [int] NOT NULL,
	[EMPCA_Activo] [bit] NOT NULL
PRIMARY KEY CLUSTERED 
(
	[EMPCA_EmpleadoCategoriaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmpleadosCategorias]  WITH CHECK ADD  CONSTRAINT [FK_EMPCA_EMP_EmpleadoId] FOREIGN KEY([EMPCA_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[EmpleadosCategorias] CHECK CONSTRAINT [FK_EMPCA_EMP_EmpleadoId]
GO

ALTER TABLE [dbo].[EmpleadosCategorias]  WITH CHECK ADD  CONSTRAINT [FK_EMPCA_PAPC_ProfesorCategoriaId] FOREIGN KEY([EMPCA_PAPC_ProfesorCategoriaId])
REFERENCES [dbo].[PAProfesoresCategorias] ([PAPC_ProfesorCategoriaId])
GO

ALTER TABLE [dbo].[EmpleadosCategorias] CHECK CONSTRAINT [FK_EMPCA_PAPC_ProfesorCategoriaId]
GO

ALTER TABLE [dbo].[EmpleadosCategorias]  WITH CHECK ADD  CONSTRAINT [FK_EMPCA_CMM_IdiomaId] FOREIGN KEY([EMPCA_CMM_IdiomaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EmpleadosCategorias] CHECK CONSTRAINT [FK_EMPCA_CMM_IdiomaId]
GO