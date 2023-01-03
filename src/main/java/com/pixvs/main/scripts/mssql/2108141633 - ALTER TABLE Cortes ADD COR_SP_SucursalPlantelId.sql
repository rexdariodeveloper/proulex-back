/**
* Created by Angel Daniel Hernández Silva on 12/08/2021.
* Object:  ALTER TABLE [dbo].[Cortes] ADD [COR_SP_SucursalPlantelId]
*/

ALTER TABLE [dbo].[Cortes] ADD [COR_SP_SucursalPlantelId] int NULL
GO

ALTER TABLE [dbo].[Cortes]  WITH CHECK ADD  CONSTRAINT [FK_COR_SP_SucursalPlantelId] FOREIGN KEY([COR_SP_SucursalPlantelId])
REFERENCES [dbo].[SucursalesPlanteles] ([SP_SucursalPlantelId])
GO

ALTER TABLE [dbo].[Cortes] CHECK CONSTRAINT [FK_COR_SP_SucursalPlantelId]
GO