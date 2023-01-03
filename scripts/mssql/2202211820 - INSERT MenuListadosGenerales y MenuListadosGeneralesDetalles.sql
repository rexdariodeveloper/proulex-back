INSERT INTO [dbo].[MenuListadosGenerales]
           ([MLG_NodoPadreId],[MLG_Titulo],[MLG_TituloEN],[MLG_Activo],[MLG_Icono],[MLG_Orden],[MLG_CMM_TipoNodoId],[MLG_NombreTablaCatalogo],[MLG_CMM_ControlCatalogo],[MLG_PermiteBorrar],[MLG_UrlAPI],[MLG_FechaCreacion],[MLG_FechaModificacion],[MLG_USU_CreadoPorId],[MLG_USU_ModificadoPorId])
     VALUES
           (NULL,'GENERALES','GENERAL',1,NULL,(select MAX(MLG_Orden)+1 from MenuListadosGenerales where MLG_NodoPadreId IS null),1000081,NULL,NULL,0,NULL,GETDATE(),NULL,NULL,NULL)
GO
INSERT INTO [dbo].[MenuListadosGenerales]
           ([MLG_NodoPadreId],[MLG_Titulo],[MLG_TituloEN],[MLG_Activo],[MLG_Icono],[MLG_Orden],[MLG_CMM_TipoNodoId],[MLG_NombreTablaCatalogo],[MLG_CMM_ControlCatalogo],[MLG_PermiteBorrar],[MLG_UrlAPI],[MLG_FechaCreacion],[MLG_FechaModificacion],[MLG_USU_CreadoPorId],[MLG_USU_ModificadoPorId])
     VALUES
           ((select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo = 'GENERALES'),'Documentos','Documents',1,'list',1,1000082,'ControlesMaestrosMultiples','CMM_GEN_TipoDocumento',0,'/api/v1/cmm',GETDATE(),NULL,NULL,NULL)
GO

INSERT INTO [dbo].[MenuListadosGeneralesDetalles]
           ([MLGD_MLG_ListadoGeneralNodoId]
           ,[MLGD_JsonConfig]
           ,[MLGD_JsonListado]
           ,[MLGD_CampoTabla]
           ,[MLGD_CampoModelo])
     VALUES
           ((select  MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo = 'Documentos')
           ,'{"type": "input","label": "Código","inputType": "text","name": "referencia","validations": [{"name": "required","validator": "Validators.required","message": "Código requerido"},{"name": "maxlength","validator": "Validators.maxLength(10)","message": "El código no debe sobrepasar los 10 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "referencia","title": "Código","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'CMM_Referencia'
           ,'referencia'),
		   ((select  MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo = 'Documentos')
           ,'{"type": "input","label": "Nombre","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerida"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La nombre no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "descripcion","title": "Descripción","class": "","centrado": false,"type": null,"tooltip": true}'
           ,'CMM_Valor'
           ,'valor')
GO
