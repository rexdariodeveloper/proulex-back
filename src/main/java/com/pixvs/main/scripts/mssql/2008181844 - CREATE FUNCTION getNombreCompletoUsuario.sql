SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[getNombreCompletoUsuario](@usuarioId INT)
RETURNS NVARCHAR(200)
AS
	BEGIN
		DECLARE @nombreCompleto NVARCHAR(300) = ( SELECT USU_Nombre + ' ' + USU_PrimerApellido + ISNULL(' ' + USU_SegundoApellido, '') FROM Usuarios  WHERE USU_UsuarioId = @usuarioId )

		RETURN @nombreCompleto
	END