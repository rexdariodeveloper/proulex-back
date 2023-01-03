SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[validarAutofacturaDiasFacturarOV](@ordenVentaId INT)
RETURNS BIT
AS
	BEGIN
		DECLARE @puedeFacturar BIT = ( 
				SELECT
					   CONVERT(BIT, 
					   CASE WHEN tipoVigenciaFacturar.CMA_Valor = '1000061'  /*Fin de mes*/ AND ( MONTH(OV_FechaOV) = MONTH(GETDATE()) AND YEAR(OV_FechaOV) = YEAR(GETDATE()) ) THEN 1
					   ELSE CASE WHEN tipoVigenciaFacturar.CMA_Valor = '1000060'  /*Tipo Fecha*/ AND ( DATEDIFF(DAY, OV_FechaOV, GETDATE()) <= CASE WHEN TRIM(ISNULL(diasFacturar.CMA_Valor, '')) = '' THEN 30 ELSE CONVERT(INT, TRIM(diasFacturar.CMA_Valor)) END ) THEN 1
					   ELSE 0 END END) AS puedeFacturar
				FROM OrdenesVenta
					 LEFT JOIN ControlesMaestros AS tipoVigenciaFacturar ON tipoVigenciaFacturar.CMA_Nombre = 'CMA_Autofactura_TipoVigenciaFacturar'
					 LEFT JOIN ControlesMaestros AS diasFacturar ON diasFacturar.CMA_Nombre = 'CMA_Autofactura_DiasFacturar'
				WHERE OV_OrdenVentaId = @ordenVentaId
		)

		RETURN @puedeFacturar
	END