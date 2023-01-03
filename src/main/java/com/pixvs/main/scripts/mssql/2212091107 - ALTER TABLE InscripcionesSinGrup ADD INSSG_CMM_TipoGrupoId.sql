/**
* Created by Angel Daniel Hernández Silva on 04/11/2022.
*/

ALTER TABLE [dbo].[InscripcionesSinGrupo] ADD [INSSG_CMM_TipoGrupoId] int NULL
GO

UPDATE InscripcionesSinGrupo SET INSSG_CMM_TipoGrupoId = 2000392
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] ALTER COLUMN [INSSG_CMM_TipoGrupoId] int NOT NULL
GO