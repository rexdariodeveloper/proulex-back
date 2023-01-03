/**
 * Created by Angel Daniel Hernández Silva on 07/07/2021.
 * Object:  ALTER TABLE [dbo].[PAModalidades] - Imagen
 */

/***************************/
/***** Nuevas columnas *****/
/***************************/

ALTER TABLE [dbo].[PAModalidades] ADD [PAMOD_ARC_ImagenId] int NULL
GO

/***************/
/***** FKs *****/
/***************/

ALTER TABLE [dbo].[PAModalidades]  WITH CHECK ADD  CONSTRAINT [FK_PAMOD_ARC_ImagenId] FOREIGN KEY([PAMOD_ARC_ImagenId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[PAModalidades] CHECK CONSTRAINT [FK_PAMOD_ARC_ImagenId]
GO