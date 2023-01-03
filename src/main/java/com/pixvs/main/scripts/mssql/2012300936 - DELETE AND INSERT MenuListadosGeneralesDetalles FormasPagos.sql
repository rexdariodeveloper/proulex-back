DELETE [dbo].[MenuListadosGeneralesDetalles] WHERE MLGD_MLG_ListadoGeneralNodoId = 15
GO

INSERT INTO 
	[dbo].[MenuListadosGeneralesDetalles]
VALUES
	(15,N'{"type": "input","label": "Código","inputType": "text","name": "codigo","validations": [{"name": "required","validator": "Validators.required","message": "Código requerido"},{"name": "maxlength","validator": "Validators.maxLength(10)","message": "El código no debe sobrepasar los 10 caracteres"}],"fxFlex": "100"}',N'{"name": "codigo","title": "Código","class": "","centrado": false,"type": null,"tooltip": true}',N'FPPV_Codigo',N'codigo'),
	(15,N'{"type": "input","label": "Nombre","inputType": "text","name": "nombre","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerido"},{"name": "maxlength","validator": "Validators.maxLength(250)","message": "El nombre no debe sobrepasar los 250 caracteres"}],"fxFlex": "100"}',N'{"name": "nombre","title": "Nombre","class": "","centrado": false,"type": null,"tooltip": false}',N'FPPV_Nombre',N'nombre'),
	(15,N'{"type": "imageCropper", "label": "Imagen", "name": "img64", "validations": []}',N'null',N'FPPV_ARC_ImagenId',N'archivoId')
GO