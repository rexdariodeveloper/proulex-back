INSERT INTO UnidadesMedidas
(
    --UM_UnidadMedidaId - this column value is auto-generated
    UM_Nombre,
    UM_Clave,
    UM_ClaveSAT,
    UM_Activo,
    UM_FechaCreacion,
    UM_USU_CreadoPorId,
    UM_Decimales
)
VALUES
(
    -- UM_UnidadMedidaId - smallint
    'Actividad', -- UM_Nombre - varchar
    'ACT', -- UM_Clave - varchar
    'ACT', -- UM_ClaveSAT - varchar
    1, -- UM_Activo - bit
    GETDATE(), -- UM_FechaCreacion - datetime2
    1, -- UM_USU_CreadoPorId - int
    0 -- UM_Decimales - smallint
)
GO