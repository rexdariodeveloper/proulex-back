/**
 * Created by Angel Daniel Hernández Silva on 31/03/2022.
 * Object: ALTER TABLE [dbo].[PrenominaMovimientos]
 */

ALTER TABLE [dbo].[PrenominaMovimientos] ADD [PRENOM_ReferenciaProcesoTabla] varchar(100) NULL
GO

ALTER TABLE [dbo].[PrenominaMovimientos] ADD [PRENOM_ReferenciaProcesoId] int NULL
GO

DELETE ProgramasGruposProfesores WHERE PROGRUP_ProgramaGrupoProfesorId IN (927,935,483,481,491,489,487,485)
GO