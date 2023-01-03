SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[getNombreCompletoUsuario](@usuarioId INT)
RETURNS NVARCHAR(200)
AS
	BEGIN
		DECLARE @nombreCompleto NVARCHAR(300) = ( SELECT TRIM(USU_Nombre) + ' ' + TRIM(USU_PrimerApellido) + ISNULL(' ' + TRIM(USU_SegundoApellido), '') FROM Usuarios  WHERE USU_UsuarioId = @usuarioId )

		RETURN @nombreCompleto
	END