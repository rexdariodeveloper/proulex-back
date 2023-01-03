SET IDENTITY_INSERT ControlesMaestrosMultiples ON
INSERT INTO ControlesMaestrosMultiples
(
    CMM_ControlId, -- column value is auto-generated
    CMM_Control,
    CMM_Valor,
    CMM_Activo,
    CMM_Referencia,
    CMM_Sistema,
    CMM_USU_CreadoPorId,
    CMM_FechaCreacion,
    CMM_USU_ModificadoPorId,
    CMM_FechaModificacion
)
VALUES
(
    2000071, --CMM_ControlId - int
    'CMM_IF_EstatusInventarioFisico', -- CMM_Control - varchar
    'En edición', -- CMM_Valor - varchar
    1, -- CMM_Activo - bit
    NULL, -- CMM_Referencia - varchar
    1, -- CMM_Sistema - bit
    NULL, -- CMM_USU_CreadoPorId - int
    GETDATE(), -- CMM_FechaCreacion - datetime2
    NULL, -- CMM_USU_ModificadoPorId - int
    NULL -- CMM_FechaModificacion - datetime2
),
(
    2000072, --CMM_ControlId - int
    'CMM_IF_EstatusInventarioFisico', -- CMM_Control - varchar
    'Borrado', -- CMM_Valor - varchar
    1, -- CMM_Activo - bit
    NULL, -- CMM_Referencia - varchar
    1, -- CMM_Sistema - bit
    NULL, -- CMM_USU_CreadoPorId - int
    GETDATE(), -- CMM_FechaCreacion - datetime2
    NULL, -- CMM_USU_ModificadoPorId - int
    NULL -- CMM_FechaModificacion - datetime2
),
(
    2000073, --CMM_ControlId - int
    'CMM_IF_EstatusInventarioFisico', -- CMM_Control - varchar
    'Ajustado', -- CMM_Valor - varchar
    1, -- CMM_Activo - bit
    NULL, -- CMM_Referencia - varchar
    1, -- CMM_Sistema - bit
    NULL, -- CMM_USU_CreadoPorId - int
    GETDATE(), -- CMM_FechaCreacion - datetime2
    NULL, -- CMM_USU_ModificadoPorId - int
    NULL -- CMM_FechaModificacion - datetime2
),
(
    2000015, --CMM_ControlId - int
    'CMM_IM_TipoMovimiento', -- CMM_Control - varchar
    'Inventario Físico', -- CMM_Valor - varchar
    1, -- CMM_Activo - bit
    NULL, -- CMM_Referencia - varchar
    1, -- CMM_Sistema - bit
    NULL, -- CMM_USU_CreadoPorId - int
    GETDATE(), -- CMM_FechaCreacion - datetime2
    NULL, -- CMM_USU_ModificadoPorId - int
    NULL -- CMM_FechaModificacion - datetime2
)
SET IDENTITY_INSERT ControlesMaestrosMultiples OFF
GO