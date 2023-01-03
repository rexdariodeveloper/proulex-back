SET IDENTITY_INSERT ArchivosEstructuraCarpetas ON
INSERT INTO ArchivosEstructuraCarpetas
(
    AEC_EstructuraId,
    AEC_AEC_EstructuraReferenciaId,
    AEC_Descripcion,
    AEC_NombreCarpeta,
    AEC_Activo,
    AEC_USU_CreadoPorId,
    AEC_FechaCreacion
)
VALUES
(
    23, --AEC_EstructuraId - int
    NULL, -- AEC_AEC_EstructuraReferenciaId - int
    'Grupos', -- AEC_Descripcion - varchar
    'grupos', -- AEC_NombreCarpeta - varchar
    1, -- AEC_Activo - bit
    1, -- AEC_USU_CreadoPorId - int
    GETDATE() -- AEC_FechaCreacion - datetime2
),
(
    24, --AEC_EstructuraId - int
    23, -- AEC_AEC_EstructuraReferenciaId - int
    'Evidencias Fotográficas', -- AEC_Descripcion - varchar
    'evidencias', -- AEC_NombreCarpeta - varchar
    1, -- AEC_Activo - bit
    1, -- AEC_USU_CreadoPorId - int
    GETDATE() -- AEC_FechaCreacion - datetime2
)
SET IDENTITY_INSERT ArchivosEstructuraCarpetas OFF