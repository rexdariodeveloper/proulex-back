SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_PRECIO_EXCEL]
AS
	Select 
	LIPRE_Codigo as codigo,
	LIPRE_Nombre as nombre,
	CASE
	WHEN LIPRE_FechaInicio is not null and LIPRE_FechaFin is not null THEN CONCAT(LIPRE_FechaInicio,' ','-',' ',LIPRE_FechaFin)
	ELSE 'Indeterminado'
	END as fecha,
	CASE 
	WHEN CLI_Nombre is not null then CLI_Nombre
	WHEN SUC_Nombre is not null then SUC_Nombre
	ELSE ''
	END as asignado,
	LIPRE_Activo as activo
	from ListadosPrecios
	LEFT JOIN Clientes ON CLI_LIPRE_ListadoPrecioId = LIPRE_ListadoPrecioId
	LEFT JOIN Sucursales ON SUC_LIPRE_ListadoPrecioId = LIPRE_ListadoPrecioId
GO