UPDATE MenuListadosGenerales SET MLG_Orden = 2 WHERE MLG_Titulo = 'Uso de CFDI' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = 'Ventas' AND MLG_NodoPadreId IS NULL)
GO

INSERT INTO MenuListadosGenerales
(
    --MLG_ListadoGeneralNodoId - this column value is auto-generated
    MLG_NodoPadreId,
    MLG_Titulo,
    MLG_TituloEN,
    MLG_Activo,
    MLG_Icono,
    MLG_Orden,
    MLG_CMM_TipoNodoId,
    MLG_NombreTablaCatalogo,
    MLG_CMM_ControlCatalogo,
    MLG_PermiteBorrar,
    MLG_UrlAPI,
    MLG_FechaCreacion
)
VALUES
(
    -- MLG_ListadoGeneralNodoId - int
    (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = 'Ventas' AND MLG_NodoPadreId IS NULL), -- MLG_NodoPadreId - int
    'R�gimen Fiscal', -- MLG_Titulo - varchar
    'R�gimen Fiscal', -- MLG_TituloEN - varchar
    1, -- MLG_Activo - bit
    'list', -- MLG_Icono - varchar
    3, -- MLG_Orden - int
    1000082, -- MLG_CMM_TipoNodoId - int
    'ControlesMaestrosMultiples', -- MLG_NombreTablaCatalogo - varchar
    'CMM_CXC_RegimenFiscal', -- MLG_CMM_ControlCatalogo - varchar
    0, -- MLG_PermiteBorrar - bit
    '/api/v1/cmm', -- MLG_UrlAPI - varchar
    GETDATE() -- MLG_FechaCreacion - datetime2
)
GO

INSERT INTO MenuListadosGeneralesDetalles
(
    --MLGD_ListadoGeneralDetalleId - this column value is auto-generated
    MLGD_MLG_ListadoGeneralNodoId,
    MLGD_JsonConfig,
    MLGD_JsonListado,
    MLGD_CampoTabla,
    MLGD_CampoModelo
)
VALUES
(
    -- MLGD_ListadoGeneralDetalleId - int
    (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = 'R�gimen Fiscal' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = 'Ventas' AND MLG_NodoPadreId IS NULL)), -- MLGD_MLG_ListadoGeneralNodoId - int
    '{"type": "input","label": "C�digo","inputType": "text","name": "referencia","validations": [{"name": "required","validator": "Validators.required","message": "C�digo requerido"},{"name": "maxlength","validator": "Validators.maxLength(5)","message": "El c�digo no debe sobrepasar los 5 caracteres"}],"fxFlex": "100"}', -- MLGD_JsonConfig - varchar
    '{"name": "referencia","title": "C�digo","class": "","centrado": false,"type": null,"tooltip": false}', -- MLGD_JsonListado - varchar
    'CMM_Referencia', -- MLGD_CampoTabla - varchar
    'referencia' -- MLGD_CampoModelo - varchar
),
(
    -- MLGD_ListadoGeneralDetalleId - int
    (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = 'R�gimen Fiscal' AND MLG_NodoPadreId = (SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_Titulo = 'Ventas' AND MLG_NodoPadreId IS NULL)), -- MLGD_MLG_ListadoGeneralNodoId - int
    '{"type": "input","label": "Descripci�n","inputType": "text","name": "valor","validations": [{"name": "required","validator": "Validators.required","message": "Descripci�n requerida"},{"name": "maxlength","validator": "Validators.maxLength(255)","message": "La descripci�n no debe sobrepasar los 255 caracteres"}],"fxFlex": "100"}', -- MLGD_JsonConfig - varchar
    '{"name": "valor","title": "Descripci�n","class": "","centrado": false,"type": null,"tooltip": false}', -- MLGD_JsonListado - varchar
    'CMM_Valor', -- MLGD_CampoTabla - varchar
    'valor' -- MLGD_CampoModelo - varchar
)
GO