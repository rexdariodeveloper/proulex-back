USE [PixvsLog]
GO

SET IDENTITY_INSERT [dbo].[LogsProcesos] ON 
GO
INSERT [dbo].[LogsProcesos] (
    [LOGP_LogProcesoId],
    [LOGP_Nombre]
) VALUES (
    4,
    N'Solicitud de pago'
)
GO
SET IDENTITY_INSERT [dbo].[LogsProcesos] OFF
GO

SET IDENTITY_INSERT [dbo].[LogsTipos] ON 
GO
INSERT [dbo].[LogsTipos] (
    [LOGT_LogTipoId],
    [LOGT_Nombre],
    [LOGT_Icono],
    [LOGT_ColorFondo]
) VALUES
(
    19,
    N'En proceso',
    N'av_timer',
    N'#007bff'
),(
    20,
    N'Finalizada',
    N'lock',
    N'#dc3545'
),(
    21,
    N'Cancelada',
    N'block',
    N'#dc3545'
)
GO
SET IDENTITY_INSERT [dbo].[LogsTipos] OFF
GO