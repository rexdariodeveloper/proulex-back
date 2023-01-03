INSERT INTO [dbo].[MenuListadosGenerales]
           ([MLG_NodoPadreId],[MLG_Titulo],[MLG_TituloEN],[MLG_Activo],[MLG_Icono],[MLG_Orden]
           ,[MLG_CMM_TipoNodoId],[MLG_NombreTablaCatalogo],[MLG_CMM_ControlCatalogo]
           ,[MLG_PermiteBorrar],[MLG_UrlAPI],[MLG_FechaCreacion])
     VALUES
           ((Select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_TituloEN='ACADEMY PROGRAMATION'),
		   'Zona','Zone',1,'list',1,1000082,'ControlesMaestrosMultiples','CMM_INC_Zona',1,'/api/v1/cmm',GETDATE())
GO

INSERT INTO [dbo].[MenuListadosGeneralesDetalles]
           ([MLGD_MLG_ListadoGeneralNodoId],[MLGD_JsonConfig],[MLGD_JsonListado]
           ,[MLGD_CampoTabla],[MLGD_CampoModelo])
     VALUES
           ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Zona')
           ,'{"type": "input","label": "Nombre","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Zona requerida"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "valor","title": "Nombre","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'CMM_Valor'
           ,'valor')
GO
