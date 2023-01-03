-- ============================================= 
-- Author:		Angel Daniel Hernández Silva
-- Create date: 2020/11/24
-- Description:	MenuListadosGenerales (Idiomas, Programas, Editoriales)
-- --------------------------------------------- 

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
	/* [MLG_ListadoGeneralNodoId] */ 16,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Idiomas',
	/* [MLG_TituloEN] */ 'Languages',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'translate',
	/* [MLG_Orden] */ 11,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_ART_Idioma',
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
),(
	/* [MLG_ListadoGeneralNodoId] */ 17,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Programas',
	/* [MLG_TituloEN] */ 'Programs',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'assignment',
	/* [MLG_Orden] */ 12,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_ART_Programa',
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
	/* [MLG_FechaCreacion] */ GETDATE()
),(
	/* [MLG_ListadoGeneralNodoId] */ 18,
	/* [MLG_NodoPadreId] */ 1,
	/* [MLG_Titulo] */ 'Editoriales',
	/* [MLG_TituloEN] */ 'Publishers',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'book',
	/* [MLG_Orden] */ 13,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_ART_Editorial',
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 16,
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 16,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Prefijo",' +
    '"inputType": "text",' +
    '"name": "referencia",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Prefijo requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El prefijo no debe sobrepasar los 5 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "referencia",' +
    '"title": "Prefijo",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Referencia',
	/* [MLGD_CampoModelo] */ 'referencia'
), (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 17,
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 18,
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