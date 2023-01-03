SET IDENTITY_INSERT [dbo].[LogsProcesos] ON 
GO
INSERT [dbo].[LogsProcesos] (
    [LOGP_LogProcesoId],
    [LOGP_Nombre]
) VALUES (
    6,
    N'CXPSolicitudesPagos'
)
GO
SET IDENTITY_INSERT [dbo].[LogsProcesos] OFF
GO