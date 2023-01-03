UPDATE ControlesMaestrosMultiples
  SET
      CMM_Control = 'CMM_OVC_MotivoDevolucion',
      CMM_FechaModificacion = GETDATE()
WHERE CMM_Control = 'CMM_OVC_MotivoCancelacion'
GO

UPDATE MenuListadosGenerales
  SET
      MLG_Titulo = 'Motivo de devolución de nota de venta',
      MLG_TituloEN = 'Reason for return of sales note',
      MLG_Icono = 'subdirectory_arrow_left',
      MLG_CMM_ControlCatalogo = 'CMM_OVC_MotivoDevolucion',
      MLG_FechaModificacion = GETDATE()
WHERE MLG_CMM_ControlCatalogo = 'CMM_OVC_MotivoCancelacion'
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
    14, -- MLG_NodoPadreId - int
    'Motivo de cancelación de nota de venta', -- MLG_Titulo - varchar
    'Reason for cancellation of sales note', -- MLG_TituloEN - varchar
    1, -- MLG_Activo - bit
    'cancel_presentation', -- MLG_Icono - varchar
    5, -- MLG_Orden - int
    1000082, -- MLG_CMM_TipoNodoId - int
    'ControlesMaestrosMultiples', -- MLG_NombreTablaCatalogo - varchar
    'CMM_OVC_MotivoCancelacion', -- MLG_CMM_ControlCatalogo - varchar
    0, -- MLG_PermiteBorrar - bit
    '/api/v1/cmm', -- MLG_UrlAPI - varchar
    GETDATE() -- MLG_FechaCreacion - datetime2
)
GO

INSERT INTO MenuListadosGeneralesDetalles
SELECT ( SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_CMM_ControlCatalogo = 'CMM_OVC_MotivoCancelacion' ),
       MLGD_JsonConfig,
       MLGD_JsonListado,
       MLGD_CampoTabla,
       MLGD_CampoModelo
FROM MenuListadosGeneralesDetalles
WHERE MLGD_MLG_ListadoGeneralNodoId = ( SELECT MLG_ListadoGeneralNodoId FROM MenuListadosGenerales WHERE MLG_CMM_ControlCatalogo = 'CMM_OVC_MotivoDevolucion' )
GO