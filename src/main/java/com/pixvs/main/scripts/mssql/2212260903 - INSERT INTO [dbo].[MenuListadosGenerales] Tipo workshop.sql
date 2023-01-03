INSERT INTO MenuListadosGenerales(MLG_NodoPadreId, MLG_Titulo, MLG_TituloEN, MLG_Activo, MLG_Icono, MLG_Orden, MLG_CMM_TipoNodoId, MLG_NombreTablaCatalogo, MLG_CMM_ControlCatalogo, MLG_PermiteBorrar, MLG_UrlAPI, MLG_FechaCreacion)
VALUES ( 25, N'Tipo workshop', N'Workshop category', 1, N'list', 6, 1000082, N'ControlesMaestrosMultiples', N'CMM_WKS_TipoWorkshop', 0, N'/api/v1/cmm', GETDATE())
GO

INSERT INTO MenuListadosGeneralesDetalles(MLGD_MLG_ListadoGeneralNodoId, MLGD_JsonConfig, MLGD_JsonListado, MLGD_CampoTabla, MLGD_CampoModelo)
VALUES(
	(SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = N'Tipo workshop'),
	N'{"type": "input","label": "Nombre","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerido"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "El nombre no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}',
	N'{"name": "valor","title": "Nombre","class": "","centrado": false,"type": null,"tooltip": false}',
	N'CMM_Valor',
	N'valor'
)
GO