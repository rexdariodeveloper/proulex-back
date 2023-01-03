UPDATE OrdenesVenta SET OV_SCC_SucursalCorteCajaId = (
	SELECT SCC_SucursalCorteCajaId FROM SucursalesCortesCajas 
	WHERE SCC_USU_UsuarioAbreId = COALESCE(OV_USU_ModificadoPorId, OV_USU_CreadoPorId) 
	AND COALESCE(OV_FechaModificacion,OV_FechaCreacion) >= SCC_FechaInicio
	AND COALESCE(OV_FechaModificacion,OV_FechaCreacion) <= COALESCE(SCC_FechaFin, getdate())
)
WHERE OV_SCC_SucursalCorteCajaId IS NULL
GO