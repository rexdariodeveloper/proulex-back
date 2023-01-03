/**
 * Created by Hernandez Soto Cesar on 10/08/2021.
 * Object:  Table [dbo].[ProgramasIdiomasLibrosMaterialesReglas]
 */

/**************************************************/
/***** ProgramasIdiomasLibrosMaterialesReglas *****/
/**************************************************/

CREATE TABLE [dbo].[ProgramasIdiomasLibrosMaterialesReglas](
	[PROGILMR_ProgramaIdiomaLibroMaterialReglaId] [int] IDENTITY(1,1) NOT NULL,
	[PROGILMR_PROGILM_ProgramaIdiomaLibroMaterialId] [int] NOT NULL,
    [PROGILMR_CMM_CarreraId] [int] NOT NULL,
	[PROGILMR_Activo] [bit] NOT NULL DEFAULT(1)
PRIMARY KEY CLUSTERED 
(
	[PROGILMR_ProgramaIdiomaLibroMaterialReglaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK

ALTER TABLE [dbo].[ProgramasIdiomasLibrosMaterialesReglas]  WITH CHECK ADD  CONSTRAINT [FK_PROGILMR_PROGILM_ProgramaIdiomaLibroMaterialId] FOREIGN KEY([PROGILMR_PROGILM_ProgramaIdiomaLibroMaterialId])
REFERENCES [dbo].[ProgramasIdiomasLibrosMateriales] ([PROGILM_ProgramaIdiomaLibroMaterialId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasLibrosMaterialesReglas] CHECK CONSTRAINT [FK_PROGILMR_PROGILM_ProgramaIdiomaLibroMaterialId]
GO

ALTER TABLE [dbo].[ProgramasIdiomasLibrosMaterialesReglas]  WITH CHECK ADD  CONSTRAINT [FK_PROGILMR_CMM_CarreraId] FOREIGN KEY([PROGILMR_CMM_CarreraId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasLibrosMaterialesReglas] CHECK CONSTRAINT [FK_PROGILMR_CMM_CarreraId]
GO