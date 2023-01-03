/**
 * Created by Angel Daniel Hernández Silva on 19/10/2022.
 */
 
ALTER TABLE [dbo].[ListadosPrecios] ADD [LIPRE_MON_MonedaId] smallint NULL
GO

UPDATE [dbo].[ListadosPrecios] SET [LIPRE_MON_MonedaId] = 1
GO

ALTER TABLE [dbo].[ListadosPrecios] ALTER COLUMN [LIPRE_MON_MonedaId] smallint NOT NULL
GO

ALTER TABLE [dbo].[ListadosPrecios]  WITH CHECK ADD  CONSTRAINT [FK_LIPRE_MON_MonedaId] FOREIGN KEY([LIPRE_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[ListadosPrecios] CHECK CONSTRAINT [FK_LIPRE_MON_MonedaId]
GO