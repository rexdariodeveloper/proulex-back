SET IDENTITY_INSERT [dbo].[Roles] ON 
GO

INSERT INTO [dbo].[Roles]
           ([ROL_RolId]
		   ,[ROL_Nombre]
           ,[ROL_Activo]
           ,[ROL_USU_CreadoPorId]
           ,[ROL_FechaCreacion]
           )
     VALUES
           (1000101
		   ,'Público General'
           ,1
           ,1
           ,GETDATE())
GO
SET IDENTITY_INSERT [dbo].[Roles] OFF 
GO

