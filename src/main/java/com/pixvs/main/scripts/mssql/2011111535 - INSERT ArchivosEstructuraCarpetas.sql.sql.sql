-- ============================================= 
-- Author:		PIXVS-FRANCISCO 
-- Create date: 2020/11/11 
-- Description:	 1 INSERT ArchivosEstructuraCarpetas.sql.sql"" 
-- --------------------------------------------- 
-- 
-- 
SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] ON 
GO

INSERT [dbo].[ArchivosEstructuraCarpetas] 
	([AEC_EstructuraId],[AEC_AEC_EstructuraReferenciaId],[AEC_Descripcion],[AEC_NombreCarpeta],[AEC_Activo],[AEC_USU_CreadoPorId],[AEC_FechaCreacion]) 
VALUES 
	(14 , NULL, 'Solicitud Pago CXP', 'pagoCXP'     , 1,(SELECT USU_UsuarioId FROM Usuarios WHERE USU_CorreoElectronico = 'pixvs.server@gmail.com'),GETDATE())
GO

SET IDENTITY_INSERT [dbo].[ArchivosEstructuraCarpetas] OFF
GO