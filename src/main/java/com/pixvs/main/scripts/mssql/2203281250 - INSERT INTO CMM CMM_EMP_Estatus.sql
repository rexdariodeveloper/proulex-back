/**
* Created by Rene Carrillo on 22/03/2022.
*/

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON
GO

INSERT INTO [dbo].[ControlesMaestrosMultiples]
           ([CMM_ControlId],[CMM_Control],[CMM_Valor],[CMM_Activo],[CMM_Referencia],[CMM_Sistema],[CMM_USU_CreadoPorId],[CMM_FechaCreacion],[CMM_USU_ModificadoPorId],[CMM_FechaModificacion],[CMM_Orden],[CMM_ARC_ImagenId],[CMM_CMM_ReferenciaId],[CMM_Color])
VALUES
	-- Tipo de Proceso RH
	(2000950,'CMM_EMP_Estatus','Activo',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000951,'CMM_EMP_Estatus','Borrado',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000952,'CMM_EMP_Estatus','Guardado',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000953,'CMM_EMP_Estatus','Prospecto',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000954,'CMM_EMP_Estatus','Baja',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL)

GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO