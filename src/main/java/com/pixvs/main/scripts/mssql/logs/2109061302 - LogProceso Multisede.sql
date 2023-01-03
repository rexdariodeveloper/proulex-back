SET IDENTITY_INSERT [dbo].[LogsProcesos] ON 
GO
INSERT [dbo].[LogsProcesos] (
    [LOGP_LogProcesoId],
    [LOGP_Nombre]
) VALUES (
    10,
    N'InscripcionesAlumnoMultisede'
)
GO
SET IDENTITY_INSERT [dbo].[LogsProcesos] OFF
GO