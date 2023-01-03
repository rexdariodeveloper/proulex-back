/**
* Created by Angel Daniel Hernández Silva on 10/09/2021.
* Object:  CM_RAZON_SOCIAL y CM_REGIMEN_FISCAL
*/

INSERT INTO [dbo].[ControlesMaestros]
           ([CMA_Nombre]
           ,[CMA_Valor]
           ,[CMA_Sistema]
           ,[CMA_FechaModificacion])
     VALUES
           ('CM_RAZON_SOCIAL'
           ,'Universidad de Guadalajara'
           ,1
           ,GETDATE())
GO

INSERT INTO [dbo].[ControlesMaestros]
           ([CMA_Nombre]
           ,[CMA_Valor]
           ,[CMA_Sistema]
           ,[CMA_FechaModificacion])
     VALUES
           ('CM_REGIMEN_FISCAL'
           ,'Personas Morales con Fines No Lucrativos'
           ,1
           ,GETDATE())
GO