CREATE OR ALTER VIEW [dbo].[VW_LISTADO_ALMACENES] AS

SELECT
	ALM_CodigoAlmacen AS "Código Almacén",
	ALM_Nombre AS "Nombre Almacén",
	Responsable.USU_Nombre + ' ' + Responsable.USU_PrimerApellido + COALESCE(' ' + Responsable.USU_SegundoApellido,'') AS "Responsable",
	SUC_Nombre AS "Sucursal",
	COALESCE(ALM_Telefono, SUC_Telefono, '-') AS "Teléfono",
	ALM_Activo AS "Activo"
FROM Almacenes
INNER JOIN Usuarios Responsable ON USU_UsuarioId = ALM_USU_ResponsableId
INNER JOIN Sucursales ON SUC_SucursalId = ALM_SUC_SucursalId

GO