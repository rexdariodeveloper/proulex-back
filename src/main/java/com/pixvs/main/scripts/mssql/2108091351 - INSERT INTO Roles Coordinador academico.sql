INSERT INTO [dbo].[Roles]
           ([ROL_Nombre]
           ,[ROL_Activo]
           ,[ROL_USU_CreadoPorId]
           ,[ROL_FechaCreacion]
           ,[ROL_USU_ModificadoPorId]
           ,[ROL_FechaModificacion])
     VALUES
           (N'Coordinador académico'
           ,1
           ,1
           ,GETDATE()
           ,null
           ,null)
GO