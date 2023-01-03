SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_reporteAntiguedadSaldosResumen] (@proveedoresIds varchar(max), @facturasIds varchar(max), @monedaId int, @diasAgrupados int, @mostrarVencidos bit, @mostrarPorVencer bit)
RETURNS @tablaTMP table(
	id int,
	codigo varchar(80),
	nombre varchar(250),
	montoRegistro decimal(28,2),
	montoActual decimal(28,2),
	montoP1 decimal(28,2),
	montoP2 decimal(28,2),
	montoP3 decimal(28,2),
	montoP4 decimal(28,2),
	montoProgramado decimal(28,2)
)

AS BEGIN

	INSERT @tablaTMP
	SELECT proveedorId AS id,
		   proveedorCodigo AS codigo, 
		   proveedorNombre AS nombre, 
		   COALESCE(SUM(montoRegistro),0) AS montoRegistro, 
		   COALESCE(SUM(montoActual),0) AS montoActual,
		   COALESCE(SUM(montoP1),0) AS montoP1,
		   COALESCE(SUM(montoP2),0) AS montoP2,
		   COALESCE(SUM(montoP3),0) AS montoP3,
		   COALESCE(SUM(montoP4),0) AS montoP4,
		   COALESCE(SUM(montoProgramado),0) AS montoProgramado
	FROM [dbo].[fn_reporteAntiguedadSaldosDetalle](@proveedoresIds,@facturasIds,@monedaId,@diasAgrupados,@mostrarVencidos,@mostrarPorVencer)

	GROUP BY proveedorId, 
			 proveedorCodigo, 
			 proveedorNombre;

RETURN;
END
GO