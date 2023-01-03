UPDATE MenuListadosGenerales
SET MLG_UrlAPI = '/api/v1/alumnos/centros-universitarios'
WHERE
    MLG_Titulo='Centros universitarios'
    AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)
GO

UPDATE MenuListadosGenerales
SET MLG_UrlAPI = '/api/v1/alumnos/preparatorias'
WHERE
    MLG_Titulo='Preparatorias'
    AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)
GO

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Centros universitarios' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "pixvsMatSelect",' +
    '"label": "Responsables",' +
    '"name": "responsables",' +
    '"formControl": "new FormControl(null,[])",' +
    '"validations": [],' +
    '"multiple": true,' +
    '"selectAll": false,' +
    '"list": [],' +
    '"campoValor": "nombreCompleto",' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ 'null',
	/* [MLGD_CampoTabla] */ '',
	/* [MLGD_CampoModelo] */ 'responsables'
)
GO

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Preparatorias' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='CONTROL ESCOLAR' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "pixvsMatSelect",' +
    '"label": "Responsables",' +
    '"name": "responsables",' +
    '"formControl": "new FormControl(null,[])",' +
    '"validations": [],' +
    '"multiple": true,' +
    '"selectAll": false,' +
    '"list": [],' +
    '"campoValor": "nombreCompleto",' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ 'null',
	/* [MLGD_CampoTabla] */ '',
	/* [MLGD_CampoModelo] */ 'responsables'
)
GO