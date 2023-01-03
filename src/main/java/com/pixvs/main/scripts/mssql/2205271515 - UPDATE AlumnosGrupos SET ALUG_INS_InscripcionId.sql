UPDATE [dbo].[AlumnosGrupos] SET [ALUG_INS_InscripcionId] = (SELECT TOP 1 [INS_InscripcionId] FROM [dbo].[Inscripciones] WHERE [INS_ALU_AlumnoId] = [ALUG_ALU_AlumnoId] AND [INS_PROGRU_GrupoId] = [ALUG_PROGRU_GrupoId])
GO

UPDATE [dbo].[AlumnosGrupos] SET [ALUG_INS_InscripcionId] = 76 WHERE [ALUG_AlumnoGrupoId] = 2361
GO
DELETE [dbo].[AlumnosGrupos] WHERE [ALUG_AlumnoGrupoId] = 9130
GO

UPDATE [dbo].[AlumnosGrupos] SET [ALUG_INS_InscripcionId] = 98 WHERE [ALUG_AlumnoGrupoId] = 905
GO
DELETE [dbo].[AlumnosGrupos] WHERE [ALUG_AlumnoGrupoId] = 9176
GO

UPDATE [dbo].[AlumnosGrupos] SET [ALUG_INS_InscripcionId] = 3015 WHERE [ALUG_AlumnoGrupoId] = 237
GO
DELETE [dbo].[AlumnosGrupos] WHERE [ALUG_AlumnoGrupoId] = 17861
GO

UPDATE [dbo].[AlumnosGrupos] SET [ALUG_INS_InscripcionId] = 8203 WHERE [ALUG_AlumnoGrupoId] = 5372
GO
DELETE [dbo].[AlumnosGrupos] WHERE [ALUG_AlumnoGrupoId] = 16162
GO

UPDATE [dbo].[AlumnosGrupos] SET [ALUG_INS_InscripcionId] = 8367 WHERE [ALUG_AlumnoGrupoId] = 3119
GO
DELETE [dbo].[AlumnosGrupos] WHERE [ALUG_AlumnoGrupoId] = 16300
GO

UPDATE [dbo].[AlumnosGrupos] SET [ALUG_INS_InscripcionId] = 8404 WHERE [ALUG_AlumnoGrupoId] = 6382
GO
DELETE [dbo].[AlumnosGrupos] WHERE [ALUG_AlumnoGrupoId] = 16323
GO

UPDATE [dbo].[AlumnosGrupos] SET [ALUG_INS_InscripcionId] = 30741 WHERE [ALUG_AlumnoGrupoId] = 30793
GO

UPDATE [dbo].[AlumnosGrupos] SET [ALUG_INS_InscripcionId] = 30756 WHERE [ALUG_AlumnoGrupoId] = 30808
GO