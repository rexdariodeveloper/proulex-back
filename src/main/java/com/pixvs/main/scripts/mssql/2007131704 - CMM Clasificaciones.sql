
SET IDENTITY_INSERT [dbo].[MenuListadosGenerales] ON
GO

INSERT [dbo].[MenuListadosGenerales] (
	[MLG_ListadoGeneralNodoId],
	[MLG_NodoPadreId],
	[MLG_Titulo],
	[MLG_TituloEN],
	[MLG_Activo],
	[MLG_Icono],
	[MLG_Orden],
	[MLG_CMM_TipoNodoId],
	[MLG_NombreTablaCatalogo],
	[MLG_CMM_ControlCatalogo],
	[MLG_PermiteBorrar],
	[MLG_UrlAPI],
	[MLG_FechaCreacion]
) VALUES (
	/* [MLG_ListadoGeneralNodoId] */ 8,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Clasificación 1',
	/* [MLG_TituloEN] */ 'Clasification 1',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 5,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_ART_Clasificacion1',
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
), (
	/* [MLG_ListadoGeneralNodoId] */ 9,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Clasificación 2',
	/* [MLG_TituloEN] */ 'Clasification 2',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 6,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_ART_Clasificacion2',
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
), (
	/* [MLG_ListadoGeneralNodoId] */ 10,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Clasificación 3',
	/* [MLG_TituloEN] */ 'Clasification 3',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 7,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_ART_Clasificacion3',
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
), (
	/* [MLG_ListadoGeneralNodoId] */ 11,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Clasificación 4',
	/* [MLG_TituloEN] */ 'Clasification 4',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 8,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_ART_Clasificacion4',
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
), (
	/* [MLG_ListadoGeneralNodoId] */ 12,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Clasificación 5',
	/* [MLG_TituloEN] */ 'Clasification 5',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 9,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_ART_Clasificacion5',
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
), (
	/* [MLG_ListadoGeneralNodoId] */ 13,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Clasificación 6',
	/* [MLG_TituloEN] */ 'Clasification 6',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'list',
	/* [MLG_Orden] */ 10,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_ART_Clasificacion6',
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
)
GO

SET IDENTITY_INSERT [dbo].[MenuListadosGenerales] OFF
GO

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 8,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Nombre",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Nombre requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Nombre",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
), (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 9,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Nombre",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Nombre requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Nombre",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
), (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 10,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Nombre",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Nombre requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Nombre",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
), (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 11,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Nombre",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Nombre requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Nombre",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
), (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 12,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Nombre",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Nombre requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Nombre",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
), (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 13,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Nombre",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Nombre requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Nombre",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO