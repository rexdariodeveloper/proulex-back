SET IDENTITY_INSERT [dbo].[LogsProcesos] ON 
GO
INSERT [dbo].[LogsProcesos] (
    [LOGP_LogProcesoId],
    [LOGP_Nombre],
    [LOGP_Icono]
) VALUES (
    9,
    N'Captura asistencias',
    N'assignment_turned_in'
)
GO
SET IDENTITY_INSERT [dbo].[LogsProcesos] OFF
GO