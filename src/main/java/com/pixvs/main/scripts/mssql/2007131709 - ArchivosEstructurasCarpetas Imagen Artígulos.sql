
SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] ON 
GO

INSERT [dbo].[ArchivosEstructuraCarpetas] (
	[AEC_EstructuraId],
	[AEC_AEC_EstructuraReferenciaId],
	[AEC_Descripcion],
	[AEC_NombreCarpeta],
	[AEC_Activo],
	[AEC_USU_CreadoPorId],
	[AEC_FechaCreacion]
) VALUES (
	/* [AEC_EstructuraId] */ 3,
	/* [AEC_AEC_EstructuraReferenciaId] */ NULL,
	/* [AEC_Descripcion] */ 'Artículos',
	/* [AEC_NombreCarpeta] */ 'articulos',
	/* [AEC_Activo] */ 1,
	/* [AEC_USU_CreadoPorId] */ 1,
	/* [AEC_FechaCreacion] */ GETDATE()
), (
	/* [AEC_EstructuraId] */ 4,
	/* [AEC_AEC_EstructuraReferenciaId] */ 3,
	/* [AEC_Descripcion] */ 'Imágenes',
	/* [AEC_NombreCarpeta] */ 'imagenes',
	/* [AEC_Activo] */ 1,
	/* [AEC_USU_CreadoPorId] */ 1,
	/* [AEC_FechaCreacion] */ GETDATE()
)
GO

SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] OFF
GO