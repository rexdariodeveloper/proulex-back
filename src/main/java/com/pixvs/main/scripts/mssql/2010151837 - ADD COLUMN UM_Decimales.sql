ALTER TABLE UnidadesMedidas ADD UM_Decimales SMALLINT DEFAULT 0
GO
INSERT INTO MenuListadosGeneralesDetalles VALUES (5, '{"type": "input","label": "Decimales","inputType": "number","name": "decimales","validations": [],"fxFlex": "100"}','{"name": "decimales","title": "Decimales","class": "","centrado": false,"type": null,"tooltip": false}','UM_Decimales','decimales')
GO