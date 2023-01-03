SET IDENTITY_INSERT [dbo].[LogsTipos] ON 
GO
INSERT [dbo].[LogsTipos] (
    [LOGT_LogTipoId],
    [LOGT_Nombre],
    [LOGT_Icono],
    [LOGT_ColorFondo]
) VALUES
(
    33,
    N'Por autorizar',
    N'av_time',
    N'#007bff'
),(
    34,
    N'Aceptada',
    N'av_time',
    N'#007bff'
)
GO
SET IDENTITY_INSERT [dbo].[LogsTipos] OFF
GO