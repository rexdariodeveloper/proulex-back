INSERT [dbo].[MenuListadosGenerales] (
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
	/* [MLG_NodoPadreId] */ 14,
	/* [MLG_Titulo] */ 'Uso CFDI',
	/* [MLG_TituloEN] */ 'CFDI Use',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'attach_money',
	/* [MLG_Orden] */ 1,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'SATUsosCFDI',
	/* [MLG_CMM_ControlCatalogo] */ NULL,
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/satUsoCfdi',
	/* [MLG_FechaCreacion] */ GETDATE()
)
GO

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales where MLG_UrlAPI='/api/v1/satUsoCfdi'),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Código",' +
    '"inputType": "text",' +
    '"name": "codigo",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Código requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(10)","message": "El código no debe sobrepasar los 10 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "codigo",' +
    '"title": "Código",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
  /* [MLGD_CampoTabla] */ 'UCFDI_Codigo',
	/* [MLGD_CampoModelo] */ 'codigo'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales where MLG_UrlAPI='/api/v1/satUsoCfdi'),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Descripción",' +
    '"inputType": "text",' +
    '"name": "descripcion",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Descripción requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(50)","message": "El código no debe sobrepasar los 50 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "descripcion",' +
    '"title": "Descripción",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
  /* [MLGD_CampoTabla] */ 'UCFDI_Descripcion',
	/* [MLGD_CampoModelo] */ 'descripcion'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales where MLG_UrlAPI='/api/v1/satUsoCfdi'),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "checkbox",' +
    '"label": "Persona Física",' +
    '"name": "fisica",' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "fisica",' +
    '"title": "Persona Física",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": "boolean",' +
    '"tooltip": false' +
  '}',
  /* [MLGD_CampoTabla] */ 'UCFDI_Fisica',
	/* [MLGD_CampoModelo] */ 'fisica'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales where MLG_UrlAPI='/api/v1/satUsoCfdi'),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "checkbox",' +
    '"label": "Persona Moral",' +
    '"name": "moral",' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "moral",' +
    '"title": "Persona Moral",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": "boolean",' +
    '"tooltip": false' +
  '}',
  /* [MLGD_CampoTabla] */ 'UCFDI_Moral',
	/* [MLGD_CampoModelo] */ 'fisica'
)
GO

UPDATE MenuListadosGenerales SET MLG_Activo=0 
WHERE MLG_ListadoGeneralNodoId=34
GO