USE [PixvsLog]
GO

SET IDENTITY_INSERT [dbo].[LogsProcesos] ON 
GO
INSERT [dbo].[LogsProcesos] (
    [LOGP_LogProcesoId],
    [LOGP_Nombre]
) VALUES (
    5,
    N'Pedidos'
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
    22,
    N'Guardado',
    N'save',
    N'#007bff'
),(
    23,
    N'Cancelado',
    N'block',
    N'#dc3545'
),(
    24,
    N'Por surtir',
    N'av_time',
    N'#007bff'
),(
    25,
    N'Surtido parcial',
    N'done',
    N'#007bff'
),(
    26,
    N'Surtido',
    N'done_all',
    N'#007bff'
),(
    27,
    N'Cerrado',
    N'lock',
    N'#dc3545'
),(
    28,
    N'Borrado',
    N'delete',
    N'#dc3545'
)
GO
SET IDENTITY_INSERT [dbo].[LogsTipos] OFF
GO