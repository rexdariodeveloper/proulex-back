DELETE ControlesMaestrosMultiples WHERE CMM_ControlId IN (2001020, 2001021, 2001022, 2001023)
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
           ,[CMM_USU_ModificadoPorId]
           ,[CMM_FechaModificacion]
           ,[CMM_Orden]
           ,[CMM_ARC_ImagenId]
           ,[CMM_CMM_ReferenciaId]
           ,[CMM_Color])
     VALUES
           (2001020,'CMM_RH_TipoMotivo','Por baja',1,NULL,1,1,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
		   (2001021,'CMM_RH_TipoMotivo','Por despido ',1,NULL,1,1,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
		   (2001022,'CMM_RH_TipoMotivo','Por causa establecida ',1,NULL,1,1,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
		   (2001023,'CMM_RH_TipoMotivo','Por modificaciones ',1,NULL,1,1,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL)
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO