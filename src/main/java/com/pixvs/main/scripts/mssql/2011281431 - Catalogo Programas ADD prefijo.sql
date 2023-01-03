
UPDATE ControlesMaestrosMultiples SET CMM_Referencia = CAST(CMM_ControlId AS varchar) WHERE CMM_Control = 'CMM_ART_Programa'
GO


INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 17,
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
)
GO