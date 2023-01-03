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
           ,[CMM_USU_ModificadoPorId]
           ,[CMM_FechaModificacion]
           ,[CMM_Orden]
           ,[CMM_ARC_ImagenId]
           ,[CMM_CMM_ReferenciaId]
           ,[CMM_Color])
     VALUES
           (2001011,'CMM_ENT_TipoPermiso','Solo lectura',1,NULL,1,1,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
		   (2001012,'CMM_ENT_TipoPermiso','Ocultar',1,NULL,1,1,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO