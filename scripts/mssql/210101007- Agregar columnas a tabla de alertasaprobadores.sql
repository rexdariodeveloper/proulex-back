ALTER TABLE AlertasConfigEtapaAprobadores ALTER COLUMN ACEA_USU_AprobadorId INT NULL
GO
ALTER TABLE AlertasConfigEtapaAprobadores ADD ACEA_DEP_DepartamentoId INT NULL
GO
ALTER TABLE AlertasConfigEtapaAprobadores ADD ACEA_Activo BIT NULL
GO
