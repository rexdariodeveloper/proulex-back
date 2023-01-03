
INSERT [dbo].[MenuListadosGenerales] ([MLG_NodoPadreId], [MLG_Titulo], [MLG_TituloEN], [MLG_Activo], [MLG_Icono], [MLG_Orden], [MLG_CMM_TipoNodoId], [MLG_NombreTablaCatalogo], [MLG_CMM_ControlCatalogo], [MLG_PermiteBorrar], [MLG_UrlAPI], [MLG_FechaCreacion]) 
VALUES ((SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='PROGRAMACIÓN ACADÉMICA' AND MLG_NodoPadreId IS NULL), N'Tipo de beca', N'Scholarship Type center', 1, N'school', 1, 1000082, N'ControlesMaestrosMultiples', 
N'CMM_BEC_TipoBeca', 0, N'/api/v1/cmm', GETDATE())
GO

INSERT [dbo].[MenuListadosGeneralesDetalles] ([MLGD_MLG_ListadoGeneralNodoId], [MLGD_JsonConfig], [MLGD_JsonListado], [MLGD_CampoTabla], [MLGD_CampoModelo]) 
VALUES ((SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Tipo de beca' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='PROGRAMACIÓN ACADÉMICA' AND MLG_NodoPadreId IS NULL)), N'{"type": "input","label": "Código","inputType": "text","name": "referencia","validations": [{"name": "required","validator": "Validators.required","message": "Código requerido"},{"name": "maxlength","validator": "Validators.maxLength(10)","message": "El código no debe sobrepasar los 10 caracteres"}],"fxFlex": "100"}', N'{"name": "referencia","title": "Código","class": "","centrado": false,"type": null,"tooltip": false}', N'CMM_Referencia', N'referencia')
GO
INSERT [dbo].[MenuListadosGeneralesDetalles] ([MLGD_MLG_ListadoGeneralNodoId], [MLGD_JsonConfig], [MLGD_JsonListado], [MLGD_CampoTabla], [MLGD_CampoModelo]) 
VALUES ((SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='Tipo de beca' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo='PROGRAMACIÓN ACADÉMICA' AND MLG_NodoPadreId IS NULL)), N'{"type": "input","label": "Nombre","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Nombre requerida"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La nombre no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}', N'{"name": "valor","title": "Nombre","class": "","centrado": false,"type": null,"tooltip": false}', N'CMM_Valor', N'valor')
GO

