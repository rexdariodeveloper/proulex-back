INSERT INTO [dbo].[MenuListadosGenerales]
           ([MLG_NodoPadreId],[MLG_Titulo],[MLG_TituloEN],[MLG_Activo],[MLG_Icono],[MLG_Orden]
           ,[MLG_CMM_TipoNodoId],[MLG_NombreTablaCatalogo],[MLG_CMM_ControlCatalogo]
           ,[MLG_PermiteBorrar],[MLG_UrlAPI],[MLG_FechaCreacion])
     VALUES
           (null,'PROGRAMACIÓN ACADÉMICA','ACADEMY PROGRAMATION',1,null,5,1000081,null,null,0,null,GETDATE())
GO

INSERT INTO [dbo].[MenuListadosGenerales]
           ([MLG_NodoPadreId],[MLG_Titulo],[MLG_TituloEN],[MLG_Activo],[MLG_Icono],[MLG_Orden]
           ,[MLG_CMM_TipoNodoId],[MLG_NombreTablaCatalogo],[MLG_CMM_ControlCatalogo]
           ,[MLG_PermiteBorrar],[MLG_UrlAPI],[MLG_FechaCreacion])
     VALUES
           ((Select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='PROGRAMACIÓN ACADÉMICA'),
		   'Modalidades','Modalities',1,'list',1,1000082,'PAModalidades',null,1,'/api/v1/modalidades',GETDATE()),
		   ((Select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='PROGRAMACIÓN ACADÉMICA'),
		   'Ciclos','Cycles',1,'list',2,1000082,'PACiclos',null,1,'/api/v1/ciclos',GETDATE())
GO

INSERT INTO [dbo].[MenuListadosGeneralesDetalles]
           ([MLGD_MLG_ListadoGeneralNodoId],[MLGD_JsonConfig],[MLGD_JsonListado]
           ,[MLGD_CampoTabla],[MLGD_CampoModelo])
     VALUES
           ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Ciclos')
           ,'{"type": "input","label": "Codigo","inputType": "text","name": "codigo","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerido"},{"name": "maxlength","validator": "Validators.maxLength(20)","message": "El código no debe sobrepasar los 20 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "codigo","title": "Codigo","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'PACIC_Codigo'
           ,'codigo'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Ciclos')
           ,'{"type": "input","label": "Nombre","inputType": "text","name": "nombre","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerido"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "nombre","title": "Nombre","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'PACIC_Nombre'
           ,'nombre'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Ciclos')
           ,'{"type": "input","label": "Fecha inicio","inputType": "date","name": "fechaInicio","validations": [{"name": "required","validator": "Validators.required","message": "Fecha requerida"}],"fxFlex": "100"}'
           ,'null'
           ,'PACIC_FechaInicio'
           ,'fechaInicio'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Ciclos')
           ,'{"type": "input","label": "Fecha fin","inputType": "date","name": "fechaFin","validations": [{"name": "required","validator": "Validators.required","message": "Fecha requerida"}],"fxFlex": "100"}'
           ,'null'
           ,'PACIC_FechaFin'
           ,'fechaFin'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Ciclos')
           ,'{"type": "","label": "Fecha","readonly":true,"fxFlex": "100"}'
           ,'{"name": "fecha","title": "Periodo","class": "mat-column-flex","centrado": true,"type": null,"tooltip": true}'
           ,'null'
           ,'fecha')
GO

INSERT INTO [dbo].[MenuListadosGeneralesDetalles]
           ([MLGD_MLG_ListadoGeneralNodoId],[MLGD_JsonConfig],[MLGD_JsonListado]
           ,[MLGD_CampoTabla],[MLGD_CampoModelo])
     VALUES
           ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Modalidades')
           ,'{"type": "input","label": "Codigo","inputType": "text","name": "codigo","validations": [{"name": "required","validator": "Validators.required","message": "Código requerido"},{"name": "maxlength","validator": "Validators.maxLength(20)","message": "El código no debe sobrepasar los 20 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "codigo","title": "Codigo","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'PAMOD_Codigo'
           ,'codigo'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Modalidades')
           ,'{"type": "input","label": "Descripción de la modalidad","inputType": "text","name": "nombre","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerido"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "nombre","title": "Modalidad","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'PAMOD_Nombre'
           ,'nombre'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Modalidades')
           ,'{"type": "input","label": "Dias por semana","inputType": "number","name": "diasPorSemana","validations": [{"name": "required","validator": "Validators.required","message": "Días requeridos"}],"fxFlex": "100"}'
           ,'{"name": "diasPorSemana","title": "Días por semana","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'PAMOD_DiasPorSemana'
           ,'diasPorSemana'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Modalidades')
           ,'{"type": "input","label": "Horas por semana","inputType": "number","name": "horasPorDia","validations": [{"name": "required","validator": "Validators.required","message": "Horas requeridas"}],"fxFlex": "100"}'
           ,'{"name": "horasPorDia","title": "Horas por día","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'PAMOD_HorasPorDia'
           ,'horasPorDia'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Modalidades')
           ,'{"type": "input","label": "Días por semana","readonly":true,"fxFlex": "100"}'
           ,'null'
           ,'null'
           ,'null'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Modalidades')
           ,'{"type": "checkbox","label": "Lunes","name": "lunes","fxFlex": "100"}'
           ,'null'
           ,'PAMOD_Lunes'
           ,'lunes'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Modalidades')
           ,'{"type": "checkbox","label": "Martes","name": "martes","fxFlex": "100"}'
           ,'null'
           ,'PAMOD_Martes'
           ,'martes'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Modalidades')
           ,'{"type": "checkbox","label": "Miércoles","name": "miercoles","fxFlex": "100"}'
           ,'null'
           ,'PAMOD_Miercoles'
           ,'miercoles'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Modalidades')
           ,'{"type": "checkbox","label": "Jueves","name": "jueves","fxFlex": "100"}'
           ,'null'
           ,'PAMOD_Jueves'
           ,'jueves'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Modalidades')
           ,'{"type": "checkbox","label": "Viernes","name": "viernes","fxFlex": "100"}'
           ,'null'
           ,'PAMOD_Viernes'
           ,'viernes'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Modalidades')
           ,'{"type": "checkbox","label": "Sábado","name": "sabado","fxFlex": "100"}'
           ,'null'
           ,'PAMOD_Sabado'
           ,'sabado'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Modalidades')
           ,'{"type": "checkbox","label": "Domingo","name": "domingo","fxFlex": "100"}'
           ,'null'
           ,'PAMOD_Domingo'
           ,'domingo')
GO

INSERT INTO [dbo].[MenuListadosGenerales]
           ([MLG_NodoPadreId],[MLG_Titulo],[MLG_TituloEN],[MLG_Activo],[MLG_Icono],[MLG_Orden]
           ,[MLG_CMM_TipoNodoId],[MLG_NombreTablaCatalogo],[MLG_CMM_ControlCatalogo]
           ,[MLG_PermiteBorrar],[MLG_UrlAPI],[MLG_FechaCreacion])
     VALUES
           ((Select MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='PROGRAMACIÓN ACADÉMICA'),
		   'Tests','Tests',1,'list',3,1000082,'PAActividadesEvaluacion',null,1,'/api/v1/actividades-evaluacion',GETDATE())
		   
GO

INSERT INTO [dbo].[MenuListadosGeneralesDetalles]
           ([MLGD_MLG_ListadoGeneralNodoId],[MLGD_JsonConfig],[MLGD_JsonListado]
           ,[MLGD_CampoTabla],[MLGD_CampoModelo])
     VALUES
           ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Tests')
           ,'{"type": "input","label": "Codigo","inputType": "text","name": "codigo","validations": [{"name": "required","validator": "Validators.required","message": "Código requerido"},{"name": "maxlength","validator": "Validators.maxLength(20)","message": "El código no debe sobrepasar los 20 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "codigo","title": "Codigo","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'PAAE_Codigo'
           ,'codigo'),
		   ((SELECT MLG_ListadoGeneralNodoId from MenuListadosGenerales where MLG_Titulo='Tests')
           ,'{"type": "input","label": "Test","inputType": "text","name": "actividad","validations": [{"name": "required","validator": "Validators.required","message": "Test requerido"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La actividad no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}'
           ,'{"name": "actividad","title": "Test","class": "","centrado": false,"type": null,"tooltip": false}'
           ,'PAAE_Actividad'
           ,'actividad')
GO