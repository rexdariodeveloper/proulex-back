ALTER TABLE PAProfesoresCategorias
DROP COLUMN PAPC_SalarioDiario
GO

DELETE FROM MenuListadosGeneralesDetalles
WHERE MLGD_JsonConfig='{"type": "input","label": "Salario Diario","inputType": "text","name": "salarioDiario", "prefix":"$", "mask":"separator.2", "thousandSeparator":"," ,"validations": [{"name": "required","validator": "Validators.required","message": "Test requerido"}],"fxFlex": "100"}'
GO
