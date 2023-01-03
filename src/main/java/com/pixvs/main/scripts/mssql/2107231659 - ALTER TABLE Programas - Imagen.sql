/**
 * Created by Angel Daniel Hernández Silva on 07/07/2021.
 * Object:  ALTER TABLE [dbo].[Programas] - Imagen
 */

/***************************/
/***** Nuevas columnas *****/
/***************************/

ALTER TABLE [dbo].[Programas] ADD [PROG_ARC_ImagenId] int NULL
GO

/***************/
/***** FKs *****/
/***************/

ALTER TABLE [dbo].[Programas]  WITH CHECK ADD  CONSTRAINT [FK_PROG_ARC_ImagenId] FOREIGN KEY([PROG_ARC_ImagenId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[Programas] CHECK CONSTRAINT [FK_PROG_ARC_ImagenId]
GO