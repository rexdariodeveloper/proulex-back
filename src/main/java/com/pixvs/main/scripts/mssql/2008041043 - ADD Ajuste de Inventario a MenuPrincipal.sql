SET IDENTITY_INSERT MenuPrincipal ON
INSERT INTO MenuPrincipal
(
    MP_NodoId, -- column value is auto-generated
    MP_NodoPadreId,
    MP_Titulo,
    MP_TituloEN,
    MP_Activo,
    MP_Icono,
    MP_Orden,
    MP_Tipo,
    MP_URL,
    MP_CMM_SistemaAccesoId,
    MP_FechaCreacion
)
VALUES
(
    11, -- MP_NodoId - int
    NULL, -- MP_NodoPadreId - int
    'Inventario', -- MP_Titulo - varchar
    'Inventory', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    NULL, -- MP_Icono - varchar
    3, -- MP_Orden - int
    'group', -- MP_Tipo - varchar
    NULL, -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE() -- MP_FechaCreacion - datetime2
),
(
    12, -- MP_NodoId - int
    11, -- MP_NodoPadreId - int
    'Ajuste de Inventario', -- MP_Titulo - varchar
    'Inventory Adjustment', -- MP_TituloEN - varchar
    1, -- MP_Activo - bit
    'list_alt', -- MP_Icono - varchar
    1, -- MP_Orden - int
    'item', -- MP_Tipo - varchar
    '/app/inventario/ajuste-inventario', -- MP_URL - varchar
    1000021, -- MP_CMM_SistemaAccesoId - int
    GETDATE() -- MP_FechaCreacion - datetime2
)
SET IDENTITY_INSERT MenuPrincipal OFF
GO

DECLARE @ident INT = ( SELECT MAX(MP_NodoId ) FROM MenuPrincipal )
DBCC CHECKIDENT( MenuPrincipal, RESEED, @ident )
GO

INSERT INTO Autonumericos
(
    --AUT_AutonumericoId - column value is auto-generated
    AUT_Nombre,
    AUT_Prefijo,
    AUT_Siguiente,
    AUT_Digitos,
    AUT_Activo
)
VALUES
(
    -- AUT_AutonumericoId - int
    'Ajuste de Inventario', -- AUT_Nombre - varchar
    'AI', -- AUT_Prefijo - varchar
    1, -- AUT_Siguiente - int
    6, -- AUT_Digitos - int
    1 -- AUT_Activo - bit
)
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
    1000091, --CMM_ControlId - int
    'CMM_AI_MotivoAjuste', -- CMM_Control - varchar
    'Discrepancia conteo físico', -- CMM_Valor - varchar
    1, -- CMM_Activo - bit
    NULL, -- CMM_Referencia - varchar
    1, -- CMM_Sistema - bit
    NULL, -- CMM_USU_CreadoPorId - int
    GETDATE(), -- CMM_FechaCreacion - datetime2
    NULL, -- CMM_USU_ModificadoPorId - int
    NULL -- CMM_FechaModificacion - datetime2
),
(
    1000092, --CMM_ControlId - int
    'CMM_AI_MotivoAjuste', -- CMM_Control - varchar
    'Error de registro', -- CMM_Valor - varchar
    1, -- CMM_Activo - bit
    NULL, -- CMM_Referencia - varchar
    1, -- CMM_Sistema - bit
    NULL, -- CMM_USU_CreadoPorId - int
    GETDATE(), -- CMM_FechaCreacion - datetime2
    NULL, -- CMM_USU_ModificadoPorId - int
    NULL -- CMM_FechaModificacion - datetime2
),
(
    1000093, --CMM_ControlId - int
    'CMM_AI_MotivoAjuste', -- CMM_Control - varchar
    'Spill', -- CMM_Valor - varchar
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