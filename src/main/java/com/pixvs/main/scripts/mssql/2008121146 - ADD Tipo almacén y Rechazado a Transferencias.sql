UPDATE Almacenes SET ALM_Activo = 1, ALM_CMM_TipoAlmacenId = 2000052 WHERE ALM_AlmacenId = 0
GO

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
    2000024, --CMM_ControlId - int
    'CMM_TRA_EstatusTransferencia', -- CMM_Control - varchar
    'Rechazado', -- CMM_Valor - varchar
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