CREATE OR ALTER FUNCTION [dbo].[fn_getDatosAdicionalesUsuarioAlmacenes] ( @usuarioId INT )
RETURNS TABLE 
AS
RETURN 
(
	SELECT SUC_SucursalId AS SucursalId,
		   SUC_CodigoSucursal AS SucursalCodigo,
		   SUC_Nombre AS SucursalNombre,
		   SUC_Prefijo AS SucursalPrefijo,
		   ALM_AlmacenId AS AlmacenId,
		   ALM_CodigoAlmacen AS AlmacenCodigo,
		   ALM_Nombre AS AlmacenNombre,
		   CONVERT(BIT, CASE WHEN USUA_USU_UsuarioId IS NOT NULL THEN 1 ELSE 0 END) AS Seleccionado,
		   ROW_NUMBER() OVER (ORDER BY SUC_SucursalId, ALM_AlmacenId) AS Orden
	FROM Sucursales
		 INNER JOIN Almacenes ON SUC_SucursalId = ALM_SUC_SucursalId AND ALM_Activo = 1
		 LEFT JOIN UsuariosAlmacenes ON ALM_AlmacenId = USUA_ALM_AlmacenId AND USUA_USU_UsuarioId = @usuarioId
	WHERE SUC_Activo = 1
)