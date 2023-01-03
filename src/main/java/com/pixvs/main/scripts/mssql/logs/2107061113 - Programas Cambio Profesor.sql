SET IDENTITY_INSERT [dbo].[LogsProcesos] ON 
GO
INSERT [dbo].[LogsProcesos] (
    [LOGP_LogProcesoId],
    [LOGP_Nombre]
) VALUES (
    7,
    N'ProgramasGruposCambioProfesor'
)
GO
SET IDENTITY_INSERT [dbo].[LogsProcesos] OFF
GO