UPDATE ControlesMaestrosMultiples
SET CMM_Valor='Sí objeto de impuesto.'
WHERE CMM_ControlId=2000971
GO

UPDATE ControlesMaestrosMultiples
SET CMM_Valor='Sí objeto del impuesto y no obligado al desglose.'
WHERE CMM_ControlId=2000971
GO

UPDATE MenuListadosGenerales
SET MLG_Titulo='Régimen Fiscal'
WHERE MLG_Titulo='R?gimen Fiscal'
GO

UPDATE MenuListadosGeneralesDetalles
SET MLGD_JsonConfig='{"type": "input","label": "Código","inputType": "text","name": "referencia","validations": [{"name": "required","validator": "Validators.required","message": "Código requerido"},{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El código no debe sobrepasar los 5 caracteres"}],"fxFlex": "100"}', MLGD_JsonListado='{"name": "referencia","title": "Código","class": "","centrado": false,"type": null,"tooltip": false}'
WHERE MLGD_JsonConfig='{"type": "input","label": "C?digo","inputType": "text","name": "referencia","validations": [{"name": "required","validator": "Validators.required","message": "C?digo requerido"},{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El c?digo no debe sobrepasar los 5 caracteres"}],"fxFlex": "100"}'
GO

UPDATE MenuListadosGeneralesDetalles
SET MLGD_JsonConfig='{"type": "input","label": "Descripción","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Descripción requerida"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La descripción no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}', MLGD_JsonListado='{"name": "valor","title": "Descripción","class": "","centrado": false,"type": null,"tooltip": false}'
WHERE MLGD_JsonConfig='{"type": "input","label": "Descripci?n","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Descripci?n requerida"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La descripci?n no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}'
GO