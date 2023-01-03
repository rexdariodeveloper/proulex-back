SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[VW_LISTADO_INVENTARIOS_FISICOS]
AS
     SELECT IF_Codigo AS "Código",       
       ALM_CodigoAlmacen+' - '+ALM_Nombre AS "Almacén",
       CASE WHEN LOC_LocalidadGeneral = 0 THEN LOC_CodigoLocalidad+' - '+LOC_Nombre ELSE '' END AS "Localidad",
	   dbo.getNombreCompletoUsuario(IF_USU_CreadoPorId) AS "Creado Por",
	   IF_Fecha AS "Fecha",
	   ISNULL(dbo.getNombreCompletoUsuario(IF_USU_AfectadoPorId), '') AS "Afectado Por",
	   ISNULL(IF_FechaAfectacion, '') AS "Afectado",
       CMM_Valor AS "Estatus"
FROM InventariosFisicos 
     INNER JOIN Localidades ON IF_LOC_LocalidadId = LOC_LocalidadId
     INNER JOIN Almacenes ON LOC_ALM_AlmacenId = ALM_AlmacenId
     INNER JOIN ControlesMaestrosMultiples ON IF_CMM_EstatusId = CMM_ControlId
GO