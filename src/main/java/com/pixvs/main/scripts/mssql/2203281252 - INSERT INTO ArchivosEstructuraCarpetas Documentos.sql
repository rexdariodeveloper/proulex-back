-- =============================================
-- Author: Rene Carrillo
-- Create date: 2022/04/24
-- ---------------------------------------------

SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] ON
GO

INSERT [dbo].[ArchivosEstructuraCarpetas]
	([AEC_EstructuraId],[AEC_AEC_EstructuraReferenciaId],[AEC_Descripcion],[AEC_NombreCarpeta],[AEC_Activo],[AEC_USU_CreadoPorId],[AEC_FechaCreacion])
VALUES
	(20 , 1, 'Documentos', 'documentos', 1,(SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'pixvs.server@gmail.com'),GETDATE())
GO

SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] OFF
GO