SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[fn_redondearCalificaciones](@calificacion DECIMAL(28,10))
RETURNS INT WITH SCHEMABINDING
AS 
BEGIN
	IF @calificacion IS NULL BEGIN RETURN 0 END
	DECLARE @CONVERTIDA INT = NULL
	IF @calificacion = 79.0
	BEGIN SET @CONVERTIDA = 80 END
	ELSE IF @calificacion > 79.0 AND @calificacion <= 80.0
	BEGIN SET @CONVERTIDA = CEILING(@calificacion) END
	ELSE IF @calificacion >= 78.0 AND @calificacion < 79.0
	BEGIN SET @CONVERTIDA = FLOOR(@calificacion) END
	ELSE IF @calificacion % 1 <= 0.5
	BEGIN SET @CONVERTIDA = FLOOR(@calificacion) END
	ELSE
	BEGIN SET @CONVERTIDA = CEILING(@calificacion) END
	RETURN @CONVERTIDA
END
GO


