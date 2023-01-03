/**
 * Created by Angel Daniel Hernández Silva on 11/03/2022.
 * Object: CREATE FUNCTION fn_getSiguienteCodigoOV
 */

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getSiguienteCodigoOV] (@prefijo varchar(10))
RETURNS varchar(16)
AS
BEGIN
    DECLARE @ultimoCodigoOV varchar(16) = NULL;

    SELECT TOP 1 @ultimoCodigoOV = OV_Codigo
    FROM OrdenesVenta
    WHERE
        LEFT(OV_Codigo,LEN(@prefijo)) = @prefijo
        AND LEN(@prefijo) + 6 = LEN(OV_Codigo)
    ORDER BY OV_Codigo DESC

    IF @ultimoCodigoOV IS NOT NULL BEGIN
        RETURN CONCAT(@prefijo,RIGHT(CONCAT('000000',CAST(RIGHT(@ultimoCodigoOV,6) AS int) + 1),6))
    END

    RETURN CONCAT(@prefijo,'000001')
END
GO


