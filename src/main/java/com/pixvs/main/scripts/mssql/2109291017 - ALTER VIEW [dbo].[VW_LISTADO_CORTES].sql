SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_LISTADO_CORTES]
AS
(
	SELECT
		corteId id,
		SUC_Nombre sede,
		SP_Nombre plantel,
		CONCAT(USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido) usuario,
		fechaInicio,
		fechaFin,
		total,
		sedeId,
		usuarioId
	FROM
	(
	SELECT
		SCC_SucursalCorteCajaId corteId,
		SCC_SUC_SucursalId sedeId,
		SCC_SP_SucursalPlantelId plantelId,
		SCC_USU_UsuarioAbreId usuarioId,
		SCC_FechaInicio fechaInicio,
		SCC_FechaFin fechaFin,
		SUM(OVD_Cantidad * OVD_Precio) total
	FROM
		SucursalesCortesCajas
		LEFT JOIN OrdenesVenta ON SCC_SucursalCorteCajaId = OV_SCC_SucursalCorteCajaId
		LEFT JOIN OrdenesVentaDetalles ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
	WHERE
		OVD_OVD_DetallePadreId IS NULL
	GROUP BY 
		SCC_SucursalCorteCajaId, SCC_SUC_SucursalId, SCC_SP_SucursalPlantelId, SCC_USU_UsuarioAbreId, SCC_FechaInicio, SCC_FechaFin
	) cortes
	INNER JOIN Sucursales ON cortes.sedeId = SUC_SucursalId
	INNER JOIN SucursalesPlanteles ON cortes.plantelId = SP_SucursalPlantelId
	INNER JOIN Usuarios ON cortes.usuarioId = USU_UsuarioId
)
GO