UPDATE ControlesMaestrosMultiples
SET CMM_Valor='Deducción'
WHERE CMM_Valor='Deducci?n'
GO

UPDATE ControlesMaestrosMultiples
SET CMM_Valor='Percepción'
WHERE CMM_Valor='Percepci?n'
GO

UPDATE MenuListadosGeneralesDetalles
SET MLGD_JsonConfig='{"type": "input","label": "Código","inputType": "text","name": "codigo","validations": [{"name": "required","validator": "Validators.required","message": "Código requerido"},{"name": "maxlength","validator": "Validators.maxLength(1)","message": "El código no debe sobrepasar 1 caracter"}],"fxFlex": "100"}',MLGD_JsonListado='{"name": "codigo","title": "Código","class": "","centrado": false,"type": null,"tooltip": false}'
WHERE MLGD_JsonConfig='{"type": "input","label": "C?digo","inputType": "text","name": "codigo","validations": [{"name": "required","validator": "Validators.required","message": "C?digo requerido"},{"name": "maxlength","validator": "Validators.maxLength(1)","message": "El c?digo no debe sobrepasar 1 caracter"}],"fxFlex": "100"}'
GO