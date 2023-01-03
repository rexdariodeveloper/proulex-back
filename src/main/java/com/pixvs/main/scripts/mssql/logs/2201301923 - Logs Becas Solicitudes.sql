SET IDENTITY_INSERT [dbo].[LogsProcesos] ON 
GO
INSERT [dbo].[LogsProcesos] (
    [LOGP_LogProcesoId],
    [LOGP_Nombre]
) VALUES (
    11,
    N'Solicitudes de becas'
)
GO
SET IDENTITY_INSERT [dbo].[LogsProcesos] OFF
GO

SET IDENTITY_INSERT [dbo].[LogsTipos] ON 
GO
INSERT [dbo].[LogsTipos] (
    [LOGT_LogTipoId],
    [LOGT_Nombre]
) VALUES (
    41,
    N'Solicitud Aprobada'
),(
    42,
    N'Solicitud En Proceso'
),(
    43,
    N'Solicitud Cancelada'
)
GO
SET IDENTITY_INSERT [dbo].[LogsTipos] OFF
GO