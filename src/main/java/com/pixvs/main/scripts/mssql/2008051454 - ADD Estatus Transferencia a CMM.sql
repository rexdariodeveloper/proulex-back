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
    2000021, --CMM_ControlId - int
    'CMM_TRA_EstatusTransferencia', -- CMM_Control - varchar
    'En tránsito', -- CMM_Valor - varchar
    1, -- CMM_Activo - bit
    NULL, -- CMM_Referencia - varchar
    1, -- CMM_Sistema - bit
    NULL, -- CMM_USU_CreadoPorId - int
    GETDATE(), -- CMM_FechaCreacion - datetime2
    NULL, -- CMM_USU_ModificadoPorId - int
    NULL -- CMM_FechaModificacion - datetime2
),
(
    2000022, --CMM_ControlId - int
    'CMM_TRA_EstatusTransferencia', -- CMM_Control - varchar
    'Transferido parcialmente', -- CMM_Valor - varchar
    1, -- CMM_Activo - bit
    NULL, -- CMM_Referencia - varchar
    1, -- CMM_Sistema - bit
    NULL, -- CMM_USU_CreadoPorId - int
    GETDATE(), -- CMM_FechaCreacion - datetime2
    NULL, -- CMM_USU_ModificadoPorId - int
    NULL -- CMM_FechaModificacion - datetime2
),
(
    2000023, --CMM_ControlId - int
    'CMM_TRA_EstatusTransferencia', -- CMM_Control - varchar
    'Transferido', -- CMM_Valor - varchar
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