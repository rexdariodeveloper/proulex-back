SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_LISTADO_TRANSFERENCIAS]
AS
     SELECT TRA_Codigo AS N'Código',
       TRA_Fecha AS N'Fecha',
       almacenOrigen.ALM_CodigoAlmacen+' - '+almacenOrigen.ALM_Nombre AS N'Almacén Origen',
       localidadOrigen.LOC_CodigoLocalidad+' - '+localidadOrigen.LOC_Nombre AS N'Localidad Origen',
       almacenDestino.ALM_CodigoAlmacen+' - '+almacenDestino.ALM_Nombre AS N'Almacén Destino',
       localidadDestino.LOC_CodigoLocalidad+' - '+localidadDestino.LOC_Nombre AS N'Localidad Destino',
	   ISNULL(TRA_Comentario, '') AS N'Comentario',
       CMM_Valor AS N'Estatus',
	   CAST(CMM_ControlId as varchar) AS N'EstatusId'
FROM Transferencias
     INNER JOIN Localidades AS localidadOrigen ON TRA_LOC_LocalidadOrigenId = localidadOrigen.LOC_LocalidadId
     INNER JOIN Localidades AS localidadDestino ON TRA_LOC_LocalidadDestinoId = localidadDestino.LOC_LocalidadId
     INNER JOIN Almacenes AS almacenOrigen ON localidadOrigen.LOC_ALM_AlmacenId = almacenOrigen.ALM_AlmacenId
     INNER JOIN Almacenes AS almacenDestino ON localidadDestino.LOC_ALM_AlmacenId = almacenDestino.ALM_AlmacenId
     INNER JOIN ControlesMaestrosMultiples ON TRA_CMM_EstatusId = CMM_ControlId
GO


