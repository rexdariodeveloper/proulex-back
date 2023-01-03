
ALTER TABLE [dbo].[ArticulosSubcategorias] ADD [ASC_Prefijo] [varchar](10) NULL
GO

UPDATE ArticulosSubcategorias SET ASC_Prefijo = CAST(ASC_SubcategoriaId AS varchar(10))
GO

ALTER TABLE [dbo].[ArticulosSubcategorias] ALTER COLUMN [ASC_Prefijo] [varchar](10) NOT NULL
GO


DELETE FROM MenuListadosGeneralesDetalles WHERE MLGD_MLG_ListadoGeneralNodoId IN (2,3,4)
GO

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
    '"label": "Prefijo",' +
    '"inputType": "text",' +
    '"name": "prefijo",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Prefijo requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El prefijo no debe sobrepasar los 5 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "prefijo",' +
    '"title": "Prefijo",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'AFAM_Prefijo',
	/* [MLGD_CampoModelo] */ 'prefijo'
),(
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
),(
	2, '{"type": "imageCropper", "label": "Imagen", "name": "img64", "validations": []}','null','AFAM_ARC_ImagenId','archivoId'
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
    '"type": "input",' +
    '"label": "Prefijo",' +
    '"inputType": "text",' +
    '"name": "prefijo",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Prefijo requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El prefijo no debe sobrepasar los 5 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "prefijo",' +
    '"title": "Prefijo",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'ACAT_Prefijo',
	/* [MLGD_CampoModelo] */ 'prefijo'
),(
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
),(
	3, '{"type": "imageCropper", "label": "Imagen", "name": "img64", "validations": []}','null','ACAT_ARC_ImagenId','archivoId'
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
    '"type": "input",' +
    '"label": "Prefijo",' +
    '"inputType": "text",' +
    '"name": "prefijo",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Prefijo requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El prefijo no debe sobrepasar los 5 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "prefijo",' +
    '"title": "Prefijo",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'ACAT_Prefijo',
	/* [MLGD_CampoModelo] */ 'prefijo'
),(
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
),(
	4, '{"type": "imageCropper", "label": "Imagen", "name": "img64", "validations": []}','null','ASC_ARC_ImagenId' ,'archivoId'
)
GO