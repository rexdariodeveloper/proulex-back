/**
* Created by Angel Daniel Hernández Silva on 23/03/2022.
* Object: INSERT MenuListadosGenerales MotivosCancelacion
*/

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
	/* [MLG_NodoPadreId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='VENTAS' AND MLG_NodoPadreId IS NULL),
	/* [MLG_Titulo] */ 'Motivo de cancelación de nota de venta',
	/* [MLG_TituloEN] */ 'Reason for cancellation of sales note',
	/* [MLG_Activo] */ 1,
	/* [MLG_Icono] */ 'cancel_presentation',
	/* [MLG_Orden] */ 4,
	/* [MLG_CMM_TipoNodoId] */ 1000082,
	/* [MLG_NombreTablaCatalogo] */ 'ControlesMaestrosMultiples',
	/* [MLG_CMM_ControlCatalogo] */ 'CMM_OVC_MotivoCancelacion',
	/* [MLG_PermiteBorrar] */ 0,
	/* [MLG_UrlAPI] */ '/api/v1/cmm',
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
	/* [MLGD_MLG_ListadoGeneralNodoId] */ (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Motivo de cancelación de nota de venta' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='VENTAS' AND MLG_NodoPadreId IS NULL)),
	/* [MLGD_JsonConfig] */ '{' +
    '"type": "input",' +
    '"label": "Motivo",' +
    '"inputType": "text",' +
    '"name": "valor",' +
    '"validations": [' +
      '{"name": "required","validator": "Validators.required","message": "Motivo requerido"},' +
      '{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El motivo no debe sobrepasar los 255 caracteres"}' +
    '],' +
    '"fxFlex": "100"' +
  '}',
  /* [MLGD_JsonListado] */ '{' +
    '"name": "valor",' +
    '"title": "Motivo",' +
    '"class": "",' +
    '"centrado": false,' +
    '"type": null,' +
    '"tooltip": false' +
  '}',
	/* [MLGD_CampoTabla] */ 'CMM_Valor',
	/* [MLGD_CampoModelo] */ 'valor'
)
GO