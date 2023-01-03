INSERT INTO MenuListadosGeneralesDetalles VALUES (2, '{"type": "imageCropper", "label": "Imagen", "name": "img64", "validations": []}','null','AFAM_ARC_ImagenId','archivoId')
GO
INSERT INTO MenuListadosGeneralesDetalles VALUES (3, '{"type": "imageCropper", "label": "Imagen", "name": "img64", "validations": []}','null','ACAT_ARC_ImagenId','archivoId')
GO
INSERT INTO MenuListadosGeneralesDetalles VALUES (4, '{"type": "imageCropper", "label": "Imagen", "name": "img64", "validations": []}','null','ASC_ARC_ImagenId' ,'archivoId')
GO

INSERT INTO 
	[dbo].[MenuListadosGeneralesDetalles]
VALUES
	(15,'{"type": "input","label": "Código","inputType": "text","name": "codigo","validations": [{"name": "required","validator": "Validators.required","message": "Código requerido"},{"name": "maxlength","validator": "Validators.maxLength(10)","message": "El código no debe sobrepasar los 10 caracteres"}],"fxFlex": "100"}','{"name": "codigo","title": "Código","class": "","centrado": false,"type": null,"tooltip": true}','FPPV_Codigo','codigo'),
	(15,'{"type": "input","label": "Nombre","inputType": "text","name": "nombre","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerido"},{"name": "maxlength","validator": "Validators.maxLength(250)","message": "El nombre no debe sobrepasar los 250 caracteres"}],"fxFlex": "100"}','{"name": "nombre","title": "Nombre","class": "","centrado": false,"type": null,"tooltip": false}','FPPV_Nombre','nombre'),
	(15,'{"type": "imageCropper", "label": "Imagen", "name": "img64", "validations": []}','null','FPPV_ARC_ImagenId','archivoId')
GO