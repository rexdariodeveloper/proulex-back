SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[VW_LISTADO_TRANSFERENCIAS]
AS
     SELECT TRA_Codigo AS "Código",
       TRA_Fecha AS "Fecha",
       almacenOrigen.ALM_CodigoAlmacen+' - '+almacenOrigen.ALM_Nombre AS "Almacén Origen",
       localidadOrigen.LOC_CodigoLocalidad+' - '+localidadOrigen.LOC_Nombre AS "Localidad Origen",
       almacenDestino.ALM_CodigoAlmacen+' - '+almacenDestino.ALM_Nombre AS "Almacén Destino",
       localidadDestino.LOC_CodigoLocalidad+' - '+localidadDestino.LOC_Nombre AS "Localidad Destino",
	   ISNULL(TRA_Comentario, '') AS "Comentario",
       CMM_Valor AS "Estatus"
FROM Transferencias
     INNER JOIN Localidades AS localidadOrigen ON TRA_LOC_LocalidadOrigenId = localidadOrigen.LOC_LocalidadId
     INNER JOIN Localidades AS localidadDestino ON TRA_LOC_LocalidadDestinoId = localidadDestino.LOC_LocalidadId
     INNER JOIN Almacenes AS almacenOrigen ON localidadOrigen.LOC_ALM_AlmacenId = almacenOrigen.ALM_AlmacenId
     INNER JOIN Almacenes AS almacenDestino ON localidadDestino.LOC_ALM_AlmacenId = almacenDestino.ALM_AlmacenId
     INNER JOIN ControlesMaestrosMultiples ON TRA_CMM_EstatusId = CMM_ControlId
GO