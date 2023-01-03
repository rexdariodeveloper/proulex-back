/**
 * Created by Angel Daniel Hernández Silva on 19/10/2022.
 */
 
ALTER TABLE [dbo].[ProgramasIdiomas] ADD [PROGI_AgruparListadosPreciosPorTipoGrupo] bit NULL
GO

UPDATE ProgramasIdiomas SET PROGI_AgruparListadosPreciosPorTipoGrupo = 0
GO

ALTER TABLE [dbo].[ProgramasIdiomas] ALTER COLUMN [PROGI_AgruparListadosPreciosPorTipoGrupo] bit NOT NULL
GO