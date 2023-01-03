SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO

INSERT INTO [dbo].[ControlesMaestrosMultiples]
           ([CMM_ControlId]
		   ,[CMM_Control]
           ,[CMM_Valor]
           ,[CMM_Activo]
           ,[CMM_Referencia]
           ,[CMM_Sistema]
           ,[CMM_USU_CreadoPorId]
           ,[CMM_FechaCreacion]
           ,[CMM_Orden]
           ,[CMM_ARC_ImagenId]
           ,[CMM_CMM_ReferenciaId])
     VALUES
           (1000193
           ,'CMM_USU_TipoId'
		   ,'Plataforma'
		   ,1
           ,null
           ,1
           ,null
           ,GETDATE()
           ,null
           ,null
           ,null)
		   
SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] Off 
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO

INSERT INTO [dbo].[ControlesMaestrosMultiples]
           ([CMM_ControlId]
		   ,[CMM_Control]
           ,[CMM_Valor]
           ,[CMM_Activo]
           ,[CMM_Referencia]
           ,[CMM_Sistema]
           ,[CMM_USU_CreadoPorId]
           ,[CMM_FechaCreacion]
           ,[CMM_Orden]
           ,[CMM_ARC_ImagenId]
           ,[CMM_CMM_ReferenciaId])
     VALUES
           (1000194
           ,'CMM_USU_TipoId'
		   ,'Usuario SIIAU'
		   ,1
           ,null
           ,1
           ,null
           ,GETDATE()
           ,null
           ,null
           ,null)
		   
SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF 
GO
