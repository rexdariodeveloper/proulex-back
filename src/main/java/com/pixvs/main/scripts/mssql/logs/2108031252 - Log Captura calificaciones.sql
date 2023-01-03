SET IDENTITY_INSERT [dbo].[LogsProcesos] ON 
GO
INSERT [dbo].[LogsProcesos] (
    [LOGP_LogProcesoId],
    [LOGP_Nombre],
    [LOGP_Icono]
) VALUES (
    8,
    N'Captura calificaciones',
    N'school'
)
GO
SET IDENTITY_INSERT [dbo].[LogsProcesos] OFF
GO