SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getConsolidadoEntradasSalidas] ( @tipo BIT,
																	@fechaInicio DATETIME,
																	@fechaFin DATETIME,
																	@almacenes NVARCHAR(300))
RETURNS TABLE
AS
RETURN
	select 
		CMM_Valor serie,
		ART_NombreArticulo nombreArticulo,
		ALM_Nombre nombreAlmacen,
		abs(cantidad * art_costopromedio) valor,
		art_costopromedio costoPromedio,
		movimientos.*
	from 
		Articulos
		left join ControlesMaestrosMultiples on ART_CMM_Clasificacion1Id = CMM_ControlId
		inner join 
		(
			select 
				LOC_ALM_AlmacenId almacenId,
				IM_ART_ArticuloId articuloId,
				SUM(IM_Cantidad) cantidad
			from 
				InventariosMovimientos 
				inner join Localidades on IM_LOC_LocalidadId = LOC_LocalidadId
			where
				LOC_Activo = 1
				-- Tipo 1: Entradas
				-- Tipo 2: Salidas
				and case when @tipo = 1 then IM_Cantidad else IM_Cantidad * -1 end > 0
				and IM_FechaCreacion between CAST(@fechaInicio as date) and CAST(@fechaFin as date)
			group by IM_ART_ArticuloId, LOC_ALM_AlmacenId
		) movimientos on movimientos.articuloId = ART_ArticuloId
		inner join Almacenes on ALM_AlmacenId = almacenId
		where ART_Inventariable = 1
		and ART_ARTST_ArticuloSubtipoId = 2
		and ISNULL(@almacenes, '|' + CAST(almacenId AS NVARCHAR(10)) + '|') like concat('%|',CAST(almacenId AS NVARCHAR(10)),'|%')
GO