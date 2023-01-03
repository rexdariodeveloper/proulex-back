DROP FUNCTION [dbo].[fn_getKardexArticulo]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getKardexArticulo] ( @articulos NVARCHAR(3000),
														@fechaInicio DATETIME, 
														@fechaFin DATETIME, 
														@localidades NVARCHAR(300), 																				  
														@referencia NVARCHAR(300))
RETURNS TABLE
AS
RETURN
	SELECT 
		articulo, 
		CONCAT(almacen,' - ',localidad) almacenLocalidad, 
		existenciaAnterior, 
		cantidad ,
		case when cantidad >= 0 then ABS(cantidad) else null end entrada,
		case when cantidad <  0 then ABS(cantidad) else null end salida,
		existenciaAcumulado total,
		ABS(costoMovimiento) costo,
		fechaMovimiento fecha,
		referencia,
		tipoMovimiento,
		razon,
		creador usuario,
		costoUnitario
	FROM 
		VW_KardexAgrupadoPorLocalidad
	WHERE
		CAST(fechaMovimiento AS DATE) BETWEEN CAST(@fechaInicio AS DATE) AND CAST(@fechaFin AS DATE)
		AND ISNULL(@articulos  , '|' + CAST(idArticulo  AS NVARCHAR(10)) + '|') LIKE concat('%|',CAST(idArticulo  AS NVARCHAR(10)),'|%')
		AND ISNULL(@localidades, '|' + CAST(idLocalidad AS NVARCHAR(10)) + '|') LIKE concat('%|',CAST(idLocalidad AS NVARCHAR(10)),'|%')
		AND referencia = ISNULL(@referencia, referencia)
GO


