INSERT INTO [dbo].[ControlesMaestros]
           ([CMA_Nombre]
           ,[CMA_Valor]
           ,[CMA_Sistema]
           ,[CMA_FechaModificacion])
     VALUES
           ('CMA_Inscripciones_PlazoDiasReinscripcion'
           ,'180'
           ,1
           ,GETDATE())
GO