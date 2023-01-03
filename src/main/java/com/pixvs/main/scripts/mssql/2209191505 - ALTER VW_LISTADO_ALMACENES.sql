CREATE OR ALTER VIEW [dbo].[VW_LISTADO_ALMACENES]
AS
SELECT ALM_CodigoAlmacen AS "C�digo Almac�n",
       ALM_Nombre AS "Nombre Almac�n",
       Responsable.USU_Nombre+' '+Responsable.USU_PrimerApellido+COALESCE(' '+Responsable.USU_SegundoApellido, '') AS "Responsable",
       SUC_Nombre AS "Sucursal",
       COALESCE(ALM_Telefono, SUC_Telefono, '-') AS "Tel�fono",
       CASE WHEN ALM_Activo = 1 THEN 'S�' ELSE 'No' END AS "Activo"
FROM Almacenes
     INNER JOIN Usuarios Responsable ON USU_UsuarioId = ALM_USU_ResponsableId
     LEFT JOIN Sucursales ON SUC_SucursalId = ALM_SUC_SucursalId
GO