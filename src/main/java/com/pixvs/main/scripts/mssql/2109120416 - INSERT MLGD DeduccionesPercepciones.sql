/***********************************************************/
/***** MenuListadosGenerales - DeduccionesPercepciones *****/
/***********************************************************/

INSERT [dbo].[MenuListadosGenerales] (
	-- [MLG_ListadoGeneralNodoId],
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
	-- /* [MLG_ListadoGeneralNodoId] */ 8,
	/* [MLG_NodoPadreId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL),
	/* [MLG_Titulo] */ 'Deducciones y percepciones',
	/* [MLG_TituloEN] */ 'Deductions and perceptions',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'attach_money',
	/* [MLG_Orden] */ 1,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'DeduccionesPercepciones',
	/* [MLG_CMM_ControlCatalogo] */ NULL,
	/* [MLG_PermiteBorrar] */ 1,
	/* [MLG_UrlAPI] */ '/api/v1/deducciones-percepciones',
	/* [MLG_FechaCreacion] */ GETDATE()
)
GO

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES
(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Deducciones y percepciones' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Código",' +
    '"inputType": "text",' +
    '"name": "codigo",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Código requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(1)","message": "El código no debe sobrepasar 1 caracter"}' +
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
	/* [MLGD_CampoTabla] */ 'DEDPER_Codigo',
	/* [MLGD_CampoModelo] */ 'codigo'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Deducciones y percepciones' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "pixvsMatSelect",' +
    '"label": "Tipo",' +
    '"name": "tipo",' +
    '"formControl": "new FormControl(null,[Validators.required])",' +
    '"validations": [],' +
    '"multiple": false,' +
    '"selectAll": false,' +
    '"list": [],' +
    '"campoValor": "valor",' +
    '"fxFlex": "100"' +
  '}',
	/* [MLGD_JsonListado] */ '{' +
    '"name": "tipo.valor",' +
    '"title": "Tipo",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'DEDPER_CMM_TipoId',
	/* [MLGD_CampoModelo] */ 'tipo'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Deducciones y percepciones' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Concepto",' +
    '"inputType": "text",' +
    '"name": "concepto",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Concepto requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(50)","message": "El concepto no debe sobrepasar los 50 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "concepto",' +
    '"title": "Concepto",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'DEDPER_Concepto',
	/* [MLGD_CampoModelo] */ 'concepto'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Deducciones y percepciones' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "pixvsMatSelect",' +
    '"label": "Tabulador",' +
    '"name": "tabulador",' +
    '"formControl": "new FormControl(null,[Validators.required])",' +
    '"validations": [],' +
    '"multiple": false,' +
    '"selectAll": false,' +
    '"list": [],' +
    '"campoValor": "codigo",' +
    '"fxFlex": "100"' +
  '}',
	/* [MLGD_JsonListado] */ '{' +
    '"name": "tabulador.codigo",' +
    '"title": "Tabulador",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'DEDPER_TAB_TabuladorId',
	/* [MLGD_CampoModelo] */ 'tabulador'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Deducciones y percepciones' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Porcentaje",' +
    '"inputType": "text",' +
	'"mask":"separator.2",'+
	'"suffix":"%",'+
    '"name": "porcentaje",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Porcentaje requerido"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "porcentaje",' +
    '"title": "Porcentaje",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'DEDPER_Porcentaje',
	/* [MLGD_CampoModelo] */ 'porcentaje'
)