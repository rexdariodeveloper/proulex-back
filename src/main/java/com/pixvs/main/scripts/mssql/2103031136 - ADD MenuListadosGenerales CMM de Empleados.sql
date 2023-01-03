INSERT INTO [dbo].[MenuListadosGenerales]
           ([MLG_NodoPadreId],[MLG_Titulo],[MLG_TituloEN],[MLG_Activo],[MLG_Icono],[MLG_Orden]
           ,[MLG_CMM_TipoNodoId],[MLG_NombreTablaCatalogo],[MLG_CMM_ControlCatalogo]
           ,[MLG_PermiteBorrar],[MLG_UrlAPI],[MLG_FechaCreacion])
     VALUES
           (null,'EMPLEADOS','EMPLOYEES',1,null,4,1000081,null,null,0,null,GETDATE())
GO

INSERT INTO [dbo].[MenuListadosGenerales]
           ([MLG_NodoPadreId],[MLG_Titulo],[MLG_TituloEN],[MLG_Activo],[MLG_Icono],[MLG_Orden]
           ,[MLG_CMM_TipoNodoId],[MLG_NombreTablaCatalogo],[MLG_CMM_ControlCatalogo]
           ,[MLG_PermiteBorrar],[MLG_UrlAPI],[MLG_FechaCreacion])
     VALUES
           ((Select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='EMPLEADOS'),
		   'Genero','Genre',1,'list',1,1000082,'ControlesMaestrosMultiples','CMM_EMP_GeneroId',1,'/api/v1/cmm',GETDATE()),
		   ((Select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='EMPLEADOS'),
		   'Tipo Empleado','Employee Type',1,'list',2,1000082,'ControlesMaestrosMultiples','CMM_EMP_TipoEmpleadoId',1,'/api/v1/cmm',GETDATE()),
		   ((Select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='EMPLEADOS'),
		   'Tipo Contrato','Contract Type',1,'list',3,1000082,'ControlesMaestrosMultiples','CMM_EMP_TipoContratoId',1,'/api/v1/cmm',GETDATE()),
		   ((Select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='EMPLEADOS'),
		   'Estado Civil','Marital Status',1,'list',4,1000082,'ControlesMaestrosMultiples','CMM_EMP_EstadoCivilId',1,'/api/v1/cmm',GETDATE()),
		   ((Select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='EMPLEADOS'),
		   'Puesto','Position',1,'list',5,1000082,'ControlesMaestrosMultiples','CMM_EMP_PuestoId',1,'/api/v1/cmm',GETDATE())
GO

INSERT INTO [dbo].[MenuListadosGeneralesDetalles]
           ([MLGD_MLG_ListadoGeneralNodoId],[MLGD_JsonConfig],[MLGD_JsonListado]
           ,[MLGD_CampoTabla],[MLGD_CampoModelo])
     VALUES
           ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Genero')
           ,'{"type": "input","label": "Nombre","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerido"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "valor","title": "Nombre","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'CMM_Valor'
           ,'valor'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Tipo Empleado')
           ,'{"type": "input","label": "Nombre","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerido"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "valor","title": "Nombre","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'CMM_Valor'
           ,'valor'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Tipo Contrato')
           ,'{"type": "input","label": "Nombre","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerido"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "valor","title": "Nombre","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'CMM_Valor'
           ,'valor'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Estado Civil')
           ,'{"type": "input","label": "Nombre","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerido"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "valor","title": "Nombre","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'CMM_Valor'
           ,'valor'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Puesto')
           ,'{"type": "input","label": "Nombre","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerido"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "valor","title": "Nombre","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'CMM_Valor'
           ,'valor')
GO
