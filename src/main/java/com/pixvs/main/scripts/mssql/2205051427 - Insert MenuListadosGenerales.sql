UPDATE MenuListadosGenerales
SET MLG_Activo=0
WHERE MLG_Titulo='Entidad Beca'
GO

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
	/* [MLG_NodoPadreId] */ 25,
	/* [MLG_Titulo] */ 'Entidades Becas',
	/* [MLG_TituloEN] */ 'Scholarship Entities',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'school',
	/* [MLG_Orden] */ 1,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'EntidadesBecas',
	/* [MLG_CMM_ControlCatalogo] */ NULL,
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/entidades-becas',
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales where MLG_UrlAPI='/api/v1/entidades-becas'),
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
  /* [MLGD_CampoTabla] */ 'ENBE_Codigo',
	/* [MLGD_CampoModelo] */ 'codigo'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales where MLG_UrlAPI='/api/v1/entidades-becas'),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Nombre",' +
    '"inputType": "text",' +
    '"name": "nombre",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Nombre requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(50)","message": "El nombre no debe sobrepasar los 50 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "nombre",' +
    '"title": "Nombre",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
  /* [MLGD_CampoTabla] */ 'ENBE_Nombre',
	/* [MLGD_CampoModelo] */ 'nombre'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales where MLG_UrlAPI='/api/v1/entidades-becas'),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Precio Anual",' +
    '"inputType": "text",' + 
	'"mask":"separator.2",'+
	'"suffix":"%",'+
    '"name": "precioAnual",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Precio Anual requerido"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "precioAnual",' +
    '"title": "Precio Anual",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
  /* [MLGD_CampoTabla] */ 'ENBE_PrecioAnual',
	/* [MLGD_CampoModelo] */ 'precioAnual'
)
GO

