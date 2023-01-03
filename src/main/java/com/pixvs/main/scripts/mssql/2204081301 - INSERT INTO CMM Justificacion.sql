SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON
GO

INSERT INTO [dbo].[ControlesMaestrosMultiples]
           ([CMM_ControlId],[CMM_Control],[CMM_Valor],[CMM_Activo],[CMM_Referencia],[CMM_Sistema],[CMM_USU_CreadoPorId],[CMM_FechaCreacion],[CMM_USU_ModificadoPorId],[CMM_FechaModificacion],[CMM_Orden],[CMM_ARC_ImagenId],[CMM_CMM_ReferenciaId],[CMM_Color])
VALUES
	-- Tipo de Horario
	(2000680,'CMM_ENT_Justificacion','Vacante',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000681,'CMM_ENT_Justificacion','Incapacidad',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000682,'CMM_ENT_Justificacion','Nuevo Puesto',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000683,'CMM_ENT_Justificacion','Proyecto Por Obra',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000684,'CMM_ENT_Justificacion','Renovación',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL)

GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO