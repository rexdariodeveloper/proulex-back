DELETE FROM ControlesMaestrosMultiples WHERE CMM_Control IN ('CMM_RH_TipoProcesoRH', 'CMM_GEN_TipoOpcion', 'CMM_GEN_TipoVigencia', 'CMM_UMT_TipoTiempo')
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON
GO

INSERT INTO [dbo].[ControlesMaestrosMultiples]
           ([CMM_ControlId],[CMM_Control],[CMM_Valor],[CMM_Activo],[CMM_Referencia],[CMM_Sistema],[CMM_USU_CreadoPorId],[CMM_FechaCreacion],[CMM_USU_ModificadoPorId],[CMM_FechaModificacion],[CMM_Orden],[CMM_ARC_ImagenId],[CMM_CMM_ReferenciaId],[CMM_Color])
VALUES
	-- Tipo de Proceso RH
	(2000900,'CMM_RH_TipoProcesoRH','Nueva Contratación',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000901,'CMM_RH_TipoProcesoRH','Renovación',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000902,'CMM_RH_TipoProcesoRH','Baja',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000903,'CMM_RH_TipoProcesoRH','Cambio',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),

	-- Tipo de Opcion
	(2000910,'CMM_GEN_TipoOpcion','Opcional',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000911,'CMM_GEN_TipoOpcion','Obligatorio',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000912,'CMM_GEN_TipoOpcion','No requerido',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),

	-- Tipo de Vigencia
	(2000920,'CMM_GEN_TipoVigencia','Vigencia',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000921,'CMM_GEN_TipoVigencia','Fecha de vencimiento',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000922,'CMM_GEN_TipoVigencia','No aplica',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),

	-- Tipo de Unidad Medida Tiempo
	(2000930,'CMM_UMT_TipoTiempo','Día (s)',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000931,'CMM_UMT_TipoTiempo','Mes (es)',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL),
	(2000932,'CMM_UMT_TipoTiempo','Año (s)',1,NULL,1,NULL,GETDATE(),NULL,NULL,NULL,NULL,NULL,NULL)

GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO