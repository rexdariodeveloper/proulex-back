Update MenuListadosGeneralesDetalles
SET MLGD_JsonConfig='{"type": "input","label": "Código","inputType": "text","name": "codigo","validations": [{"name": "required","validator": "Validators.required","message": "Código requerido"},{"name": "maxlength","validator": "Validators.maxLength(3)","message": "El código no debe sobrepasar 3 caracter"}],"fxFlex": "100"}'
WHERE MLGD_JsonConfig='{"type": "input","label": "Código","inputType": "text","name": "codigo","validations": [{"name": "required","validator": "Validators.required","message": "Código requerido"},{"name": "maxlength","validator": "Validators.maxLength(1)","message": "El código no debe sobrepasar 1 caracter"}],"fxFlex": "100"}'
GO

ALTER TABLE DeduccionesPercepciones
ALTER COLUMN [DEDPER_Codigo] [VARCHAR](3) not null
GO