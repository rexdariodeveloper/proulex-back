CREATE OR ALTER FUNCTION [dbo].[getClienteRemisionCantidadPendienteFacturar] ( @clienteRemisionId int )
RETURNS decimal(28,6)
AS
BEGIN

	DECLARE @cantidadPendiente decimal(28,6);
	
	SELECT
		@cantidadPendiente = SUM(CRD.cantidad)
	FROM(
		SELECT
			CLIRD_Cantidad - SUM(CXCFD_Cantidad) AS cantidad
		FROM ClientesRemisiones
		INNER JOIN ClientesRemisionesDetalles ON CLIRD_CLIR_ClienteRemisionId = CLIR_ClienteRemisionId
		LEFT JOIN CXCFacturasDetalles ON CXCFD_CLIRD_ClienteRemisionDetalleId = CLIRD_ClienteRemisionDetalleId
		WHERE CLIR_ClienteRemisionId = @clienteRemisionId
		GROUP BY CLIR_ClienteRemisionId, CLIRD_ClienteRemisionDetalleId, CLIRD_Cantidad
	) CRD
    
	return @cantidadPendiente
END
GO