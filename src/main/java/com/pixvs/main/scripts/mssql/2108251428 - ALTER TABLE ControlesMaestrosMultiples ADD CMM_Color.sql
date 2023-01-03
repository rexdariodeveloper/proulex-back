ALTER TABLE [dbo].[ControlesMaestrosMultiples] ADD [CMM_Color] NVARCHAR(50) NULL
GO

UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Color] = N'#4D6EB5/#99CEB0' WHERE [CMM_ControlId] = 27
GO
UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Color] = N'#E5810F/#FDC647' WHERE [CMM_ControlId] = 28
GO
UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Color] = N'#B2950D/#E8C626' WHERE [CMM_ControlId] = 29
GO
UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Color] = N'#BF1822/#EC5125' WHERE [CMM_ControlId] = 31
GO
UPDATE [dbo].[ControlesMaestrosMultiples] SET [CMM_Color] = N'#429835/#909E23' WHERE [CMM_ControlId] = 32
GO