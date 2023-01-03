/*****************************/
/***** ArticulosFamilias *****/
/*****************************/

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 2,
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
	/* [MLGD_CampoTabla] */ 'AFAM_Nombre',
	/* [MLGD_CampoModelo] */ 'nombre'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 2,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Descripción",' +
    '"inputType": "text",' +
    '"name": "descripcion",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Descripción requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(50)","message": "La descripción no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
	/* [MLGD_JsonListado] */ '{' +
    '"name": "descripcion",' +
    '"title": "Descripción",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": true' +
  '}',
	/* [MLGD_CampoTabla] */ 'AFAM_Descripcion',
	/* [MLGD_CampoModelo] */ 'descripcion'
)
GO





/*******************************/
/***** ArticulosCategorias *****/
/*******************************/

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 3,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "pixvsMatSelect",' +
    '"label": "Familia",' +
    '"name": "familia",' +
    '"formControl": "new FormControl(null,[Validators.required])",' +
    '"validations": [],' +
    '"multiple": false,' +
    '"selectAll": false,' +
    '"list": [],' +
    '"campoValor": "nombre",' +
    '"fxFlex": "100"' +
  '}',
	/* [MLGD_JsonListado] */ '{' +
    '"name": "familia.nombre",' +
    '"title": "Familia",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'ACAT_AFAM_FamiliaId',
	/* [MLGD_CampoModelo] */ 'familia'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 3,
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
  /* [MLGD_CampoTabla] */ 'ACAT_Nombre',
	/* [MLGD_CampoModelo] */ 'nombre'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 3,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Descripción",' +
    '"inputType": "text",' +
    '"name": "descripcion",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Descripción requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(50)","message": "La descripción no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
	/* [MLGD_JsonListado] */ '{' +
    '"name": "descripcion",' +
    '"title": "Descripción",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": true' +
  '}',
	/* [MLGD_CampoTabla] */ 'ACAT_Descripcion',
	/* [MLGD_CampoModelo] */ 'descripcion'
)
GO





/**********************************/
/***** ArticulosSubcategorias *****/
/**********************************/

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 4,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "pixvsMatSelect",' +
    '"label": "Categoría",' +
    '"name": "categoria",' +
    '"formControl": "new FormControl(null,[Validators.required])",' +
    '"validations": [],' +
    '"multiple": false,' +
    '"selectAll": false,' +
    '"list": [],' +
    '"campoValor": "nombre",' +
    '"fxFlex": "100"' +
  '}',
	/* [MLGD_JsonListado] */ '{' +
    '"name": "categoria.nombre",' +
    '"title": "Categoría",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'ASC_ACAT_CategoriaId',
	/* [MLGD_CampoModelo] */ 'categoria'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 4,
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
  /* [MLGD_CampoTabla] */ 'ASC_Nombre',
	/* [MLGD_CampoModelo] */ 'nombre'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 4,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Descripción",' +
    '"inputType": "text",' +
    '"name": "descripcion",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Descripción requerida"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(50)","message": "La descripción no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
	/* [MLGD_JsonListado] */ '{' +
    '"name": "descripcion",' +
    '"title": "Descripción",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": true' +
  '}',
	/* [MLGD_CampoTabla] */ 'ASC_Descripcion',
	/* [MLGD_CampoModelo] */ 'descripcion'
)
GO





/***************************/
/***** UnidadesMedidas *****/
/***************************/

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 5,
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
  /* [MLGD_CampoTabla] */ 'UM_Nombre',
	/* [MLGD_CampoModelo] */ 'nombre'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 5,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Clave",' +
    '"inputType": "text",' +
    '"name": "clave",' +
    '"validations": [' +
      '{"name": "maxlength","validator": "Validators.maxLength(5)","message": "La clave no debe sobrepasar los 5 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "clave",' +
    '"title": "Clave",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
  /* [MLGD_CampoTabla] */ 'UM_Clave',
	/* [MLGD_CampoModelo] */ 'clave'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 5,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Clave SAT",' +
    '"inputType": "text",' +
    '"name": "claveSAT",' +
    '"validations": [' +
      '{"name": "maxlength","validator": "Validators.maxLength(5)","message": "La clave SAT no debe sobrepasar los 5 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "claveSAT",' +
    '"title": "Clave SAT",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
  /* [MLGD_CampoTabla] */ 'UM_ClaveSAT',
	/* [MLGD_CampoModelo] */ 'claveSAT'
)
GO





/*******************/
/***** Monedas *****/
/*******************/

INSERT [dbo].[MenuListadosGeneralesDetalles] (
	[MLGD_MLG_ListadoGeneralNodoId],
	[MLGD_JsonConfig],
	[MLGD_JsonListado],
	[MLGD_CampoTabla],
	[MLGD_CampoModelo]
) VALUES (
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 7,
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
  /* [MLGD_CampoTabla] */ 'MON_Codigo',
	/* [MLGD_CampoModelo] */ 'codigo'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 7,
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
  /* [MLGD_CampoTabla] */ 'MON_Nombre',
	/* [MLGD_CampoModelo] */ 'nombre'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 7,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Símbolo",' +
    '"inputType": "text",' +
    '"name": "simbolo",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Símbolo requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(4)","message": "El símbolo no debe sobrepasar los 4 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "simbolo",' +
    '"title": "Símbolo",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
  /* [MLGD_CampoTabla] */ 'MON_Simbolo',
	/* [MLGD_CampoModelo] */ 'simbolo'
),(
	/* [MLGD_MLG_ListadoGeneralNodoId] */ 7,
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "checkbox",' +
    '"label": "Predeterminada",' +
    '"name": "predeterminada",' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "predeterminada",' +
    '"title": "Predeterminada",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": "boolean",' +
    '"tooltip": false' +
  '}',
  /* [MLGD_CampoTabla] */ 'MON_Predeterminada',
	/* [MLGD_CampoModelo] */ 'predeterminada'
)
GO