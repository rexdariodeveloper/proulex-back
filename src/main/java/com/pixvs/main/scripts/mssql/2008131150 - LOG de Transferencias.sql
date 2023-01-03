USE PixvsLog

DELETE FROM Logs
GO

DELETE FROM LogsProcesos
GO

DBCC CHECKIDENT(LogsProcesos, RESEED, 1)
GO

SET IDENTITY_INSERT LogsProcesos ON
INSERT INTO LogsProcesos
(
    LOGP_LogProcesoId, -- column value is auto-generated
    LOGP_Nombre,
    LOGP_Icono
)
VALUES
(
    1, --LOGP_LogProcesoId - int
    'Transferencias', -- LOGP_Nombre - varchar
    'Transfers' -- LOGP_Icono - varchar
)
SET IDENTITY_INSERT LogsProcesos OFF
GO

SET IDENTITY_INSERT LogsTipos ON
INSERT INTO LogsTipos
(
    LOGT_LogTipoId, -- column value is auto-generated
    LOGT_Nombre,
    LOGT_Icono,
    LOGT_ColorFondo
)
VALUES
(
    6, -- int
    'En Tránsito', -- LOGT_Nombre - varchar
    'transfer_within_a_station', -- LOGT_Icono - varchar
    '#28a745' -- LOGT_ColorFondo - varchar
),
(
    7, -- int
    'Transferencia Parcial', -- LOGT_Nombre - varchar
    'more_horiz', -- LOGT_Icono - varchar
    '#28a745' -- LOGT_ColorFondo - varchar
),
(
    8, -- int
    'Transferido', -- LOGT_Nombre - varchar
    'transit_enterexit', -- LOGT_Icono - varchar
    '#28a745' -- LOGT_ColorFondo - varchar
)
SET IDENTITY_INSERT LogsTipos OFF
GO