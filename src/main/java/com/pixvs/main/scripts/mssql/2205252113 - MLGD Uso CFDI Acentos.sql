UPDATE MenuListadosGeneralesDetalles
SET MLGD_JsonConfig='{"type": "input","label": "Código","inputType": "text","name": "referencia","validations": [{"name": "required","validator": "Validators.required","message": "Código requerido"},{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El código no debe sobrepasar los 5 caracteres"}],"fxFlex": "100"}',MLGD_JsonListado='{"name": "referencia","title": "Código","class": "","centrado": false,"type": null,"tooltip": false}'
WHERE MLGD_ListadoGeneralDetalleId=83
GO

UPDATE MenuListadosGeneralesDetalles
SET MLGD_JsonConfig='{"type": "input","label": "Descripción","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Descripción requerida"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La descripción no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}', MLGD_JsonListado='{"name": "valor","title": "Descripción","class": "","centrado": false,"type": null,"tooltip": false}'
WHERE MLGD_ListadoGeneralDetalleId=84
GO