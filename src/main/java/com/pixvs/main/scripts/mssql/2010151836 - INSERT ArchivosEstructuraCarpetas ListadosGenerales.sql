SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] ON 
GO

INSERT [dbo].[ArchivosEstructuraCarpetas] 
	([AEC_EstructuraId],[AEC_AEC_EstructuraReferenciaId],[AEC_Descripcion],[AEC_NombreCarpeta],[AEC_Activo],[AEC_USU_CreadoPorId],[AEC_FechaCreacion]) 
VALUES 
	(9 , NULL, 'Listados Generales'                , 'listados'     , 1,(SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'pixvs.server@gmail.com'),GETDATE()),
	(10, 9   , 'Imagenes de ArticulosFamilias'     , 'familias'     , 1,(SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'pixvs.server@gmail.com'),GETDATE()),
	(11, 9   , 'Imagenes de ArticulosCategorias'   , 'categorias'   , 1,(SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'pixvs.server@gmail.com'),GETDATE()),
	(12, 9   , 'Imagenes de ArticulosSubcategorias', 'subcategorias', 1,(SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'pixvs.server@gmail.com'),GETDATE()),
	(13, 9   , 'Imagenes de FormasPagoPV'          , 'formaspagopv' , 1,(SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'pixvs.server@gmail.com'),GETDATE())
GO

SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] OFF
GO