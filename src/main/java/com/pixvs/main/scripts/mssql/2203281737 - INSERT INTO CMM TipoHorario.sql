SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON
GO

INSERT INTO [dbo].[ControlesMaestrosMultiples]
           ([CMM_ControlId],[CMM_Control],[CMM_Valor],[CMM_Activo],[CMM_Referencia],[CMM_Sistema],[CMM_USU_CreadoPorId],[CMM_FechaCreacion],[CMM_USU_ModificadoPorId],[CMM_FechaModificacion],[CMM_Orden],[CMM_ARC_ImagenId],[CMM_CMM_ReferenciaId],[CMM_Color])
VALUES
	-- Tipo de Horario
	(2000940,'CMM_ENT_TipoHorario','Carga horaria semanal',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000941,'CMM_ENT_TipoHorario','Rolar turnos',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000942,'CMM_ENT_TipoHorario','Horario Fijo',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL)

GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO