ALTER TABLE PAProfesoresCategorias
ALTER COLUMN [PAPC_Categoria] [varchar](5) not null
GO

UPDATE MenuListadosGeneralesDetalles 
SET MLGD_JsonConfig='{"type": "input","label": "Categoria","inputType": "text","name": "categoria","validations": [{"name": "required","validator": "Validators.required","message": "Categoria requerida"},{"name": "maxlength","validator": "Validators.maxLength(5)","message": "La categoria debe ser de solo 5 caracteres"}],"fxFlex": "100"}'
where MLGD_JsonConfig='{"type": "input","label": "Categoria","inputType": "text","name": "categoria","validations": [{"name": "required","validator": "Validators.required","message": "Categoria requerida"},{"name": "maxlength","validator": "Validators.maxLength(1)","message": "La categoria debe ser de solo 1 caracter"}],"fxFlex": "100"}'
GO