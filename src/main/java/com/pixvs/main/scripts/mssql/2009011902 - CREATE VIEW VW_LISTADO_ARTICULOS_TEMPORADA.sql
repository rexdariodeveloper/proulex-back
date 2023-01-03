SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/** 
	Name: 
		VW_LISTADO_ARTICULOS_TEMPORADA
	Description:
		Returns a list of tuples (articuloId, minimo, maximo, criterioId). 
		If ART_PlaneacionTemporadas is inactive, returns data from Articulos, else returns data from active Temporada.
		If the row of Articulos hasn't an active Temporada, returns data from Articulos.
	Notes: 
		Active Temporada is given by the row that has current date between start and end.
**/

CREATE OR ALTER VIEW [dbo].[VW_LISTADO_ARTICULOS_TEMPORADA]
AS
	SELECT 
		COALESCE(ART_ArticuloId, temporada.articuloId) articuloId,
		CASE WHEN ART_PlaneacionTemporadas = 0 THEN COALESCE(ART_MinimoAlmacen,0) ELSE COALESCE(temporada.minimo,0) END minimo,
		CASE WHEN ART_PlaneacionTemporadas = 0 THEN COALESCE(ART_MaximoAlmacen,0) ELSE COALESCE(temporada.maximo,0) END maximo,
		CASE WHEN ART_PlaneacionTemporadas = 0 THEN COALESCE(1000001,0) ELSE COALESCE(temporada.citerioId,1000001) END criterioId
	FROM 
		Articulos 
		LEFT JOIN	(	
					SELECT 
						TEMD_ART_ArticuloId articuloId,
						TEMD_Minimo minimo,
						TEMD_Maximo maximo,
						TEMD_CMM_CriterioId citerioId
					FROM 
						Temporadas 
						INNER JOIN TemporadasDetalles ON TEM_TemporadaId = TEMD_TEM_TemporadaId
					WHERE
						CAST(GETDATE() AS DATE) BETWEEN CAST(TEM_FechaInicio AS DATE) AND CAST(TEM_FechaFin AS DATE)
					) temporada on ART_ArticuloId = temporada.articuloId;
GO