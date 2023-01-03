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
           (2000980,N'CMM_INO_InscripcionOrigen',N'Proyeccion'	  ,1,NULL,1,1,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
		   (2000981,N'CMM_INO_InscripcionOrigen',N'Plantilla'	  ,1,NULL,1,1,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
		   (2000982,N'CMM_INO_InscripcionOrigen',N'Punto de venta',1,NULL,1,1,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO