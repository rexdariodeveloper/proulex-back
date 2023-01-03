UPDATE [dbo].[ControlesMaestros] SET [CMA_Valor]='PSCA', [CMA_FechaModificacion]=GETDATE() WHERE [CMA_Nombre]=N'CMA_CCNF_EmpresaNombre'
GO

UPDATE [dbo].[ControlesMaestros] SET [CMA_Valor]='05DF7E2E-E425-4567-B2DA-41BEFE81A75A', [CMA_FechaModificacion]=GETDATE() WHERE [CMA_Nombre]=N'CMA_CCNF_EmpresaId'
GO