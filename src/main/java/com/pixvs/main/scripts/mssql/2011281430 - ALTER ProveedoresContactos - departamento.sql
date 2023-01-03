ALTER TABLE [dbo].[ProveedoresContactos] ADD [PROC_Departamento] [varchar](250) NULL
GO

UPDATE ProveedoresContactos SET PROC_Departamento = DEP_Nombre
FROM ProveedoresContactos
INNER JOIN Departamentos ON DEP_DepartamentoId = PROC_DEP_DepartamentoId
GO

ALTER TABLE [dbo].[ProveedoresContactos] ALTER COLUMN [PROC_Departamento] [varchar](250) NOT NULL
GO

ALTER TABLE [dbo].[ProveedoresContactos] DROP CONSTRAINT [FK_ProveedoresContactos_Departamentos]
GO

ALTER TABLE [dbo].[ProveedoresContactos] DROP COLUMN [PROC_DEP_DepartamentoId]
GO