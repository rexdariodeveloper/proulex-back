UPDATE MenuListadosGeneralesDetalles SET 
	MLGD_JsonConfig = N'{"type": "input","label": "C�digo","inputType": "text","name": "codigo","validations": [{"name": "required","validator": "Validators.required","message": "C�digo requerido"},{"name": "maxlength","validator": "Validators.maxLength(10)","message": "El c�digo no debe sobrepasar los 10 caracteres"}],"fxFlex": "100"}',
	MLGD_JsonListado = N'{"name": "codigo","title": "C�digo","class": "","centrado": false,"type": null,"tooltip": true}'
WHERE
	MLGD_CampoTabla = N'FPPV_Codigo'
GO