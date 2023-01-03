SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER   VIEW [dbo].[VW_RPT_ARTICULOS_TRANSITO]
AS
	SELECT
		referencia,
		-- tipo.CMM_Valor tipo,
		-- fecha,
		-- envia,
		recibe,
		-- estatus.CMM_Valor estatus,
		ART_CodigoArticulo codigoArticulo,
		ART_NombreArticulo nombreArticulo,
		UM_Nombre unidad,
		cantidad,
		ART_CostoPromedio costo,
		cantidad * ART_CostoPromedio total,
		localidadId
	FROM
	(
		SELECT
			articuloId,
			umId,
			referencia,
			fecha,
			-- tipoId,
			cantidad,
			COALESCE(p.envia, t.envia) envia,
			COALESCE(p.recibe, t.recibe) recibe,
			COALESCE(p.estatusId, t.estatusId) estatusId,
			COALESCE(p.localidadId, t.localidadId) localidadId
		FROM
			VW_ARTICULOS_TRANSITO	
			LEFT JOIN
			(
				SELECT
					PED_Codigo codigo, 
					CONCAT(ao.ALM_Nombre,' - ',o.LOC_Nombre) envia, 
					CONCAT(ad.ALM_Nombre,' - ',d.LOC_Nombre) recibe,
					d.LOC_LocalidadId localidadId,
					PED_CMM_EstatusId estatusId
				FROM
					Pedidos
					INNER JOIN Localidades o ON PED_LOC_LocalidadCEDISId = o.LOC_LocalidadId
					INNER JOIN Almacenes ao ON o.LOC_ALM_AlmacenId = ao.ALM_AlmacenId
					INNER JOIN Localidades d ON PED_LOC_LocalidadOrigenId = d.LOC_LocalidadId
					INNER JOIN Almacenes ad ON d.LOC_ALM_AlmacenId = ad.ALM_AlmacenId
			) p ON p.codigo = referencia
			LEFT JOIN
			(
				SELECT
					TRA_Codigo codigo, 
					CONCAT(ao.ALM_Nombre,' - ',o.LOC_Nombre) envia, 
					CONCAT(ad.ALM_Nombre,' - ',d.LOC_Nombre) recibe,
					d.LOC_LocalidadId localidadId,
					TRA_CMM_EstatusId estatusId
				FROM
					Transferencias
					INNER JOIN Localidades o ON TRA_LOC_LocalidadOrigenId = o.LOC_LocalidadId
					INNER JOIN Almacenes ao ON o.LOC_ALM_AlmacenId = ao.ALM_AlmacenId
					INNER JOIN Localidades d ON TRA_LOC_LocalidadDestinoId = d.LOC_LocalidadId
					INNER JOIN Almacenes ad ON d.LOC_ALM_AlmacenId = ad.ALM_AlmacenId
			) t ON t.codigo = referencia
		) movimientos
		INNER JOIN ControlesMaestrosMultiples estatus ON estatusId = estatus.CMM_ControlId 
		INNER JOIN UnidadesMedidas ON umId = UM_UnidadMedidaId
		INNER JOIN Articulos ON articuloId = ART_ArticuloId
GO