/**
 * Created by Angel Daniel Hernández Silva on 19/10/2022.
 */
 
ALTER TABLE [dbo].[Articulos] ADD [ART_CMM_TipoGrupoId] int NULL
GO

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_CMM_TipoGrupoId] FOREIGN KEY([ART_CMM_TipoGrupoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_CMM_TipoGrupoId]
GO