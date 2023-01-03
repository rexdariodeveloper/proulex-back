INSERT INTO [dbo].[ControlesMaestros]
           ([CMA_Nombre]
           ,[CMA_Valor]
           ,[CMA_Sistema]
           ,[CMA_FechaModificacion])
     VALUES
      -- Contratacion Directa
			('CMA_RH_ContratacionDirecta'
           ,'1'
           ,1
           ,GETDATE()),
			-- Nueva Contratacion
			('CMA_RH_NuevaContratacion'
           ,'1'
           ,1
           ,GETDATE())

GO