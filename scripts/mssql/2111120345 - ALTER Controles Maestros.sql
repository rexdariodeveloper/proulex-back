/**
* Created by Cesar Hernandez Soto on 11/09/2021.
* Object:  CM_DIRECTOR_GENERAL y CM_DIRECTOR_RECURSOS_HUMANOS
*/

INSERT INTO [dbo].[ControlesMaestros]
           ([CMA_Nombre]
           ,[CMA_Valor]
           ,[CMA_Sistema]
           ,[CMA_FechaModificacion])
     VALUES
           ('CM_DIRECTOR_GENERAL'
           ,''
           ,1
           ,GETDATE())
GO

INSERT INTO [dbo].[ControlesMaestros]
           ([CMA_Nombre]
           ,[CMA_Valor]
           ,[CMA_Sistema]
           ,[CMA_FechaModificacion])
     VALUES
           ('CM_DIRECTOR_RECURSOS_HUMANOS'
           ,''
           ,1
           ,GETDATE())
GO