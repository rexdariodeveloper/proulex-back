

CREATE OR ALTER FUNCTION [dbo].[getExistenciaArticuloLocalidad] ( @articuloId int, @localidadId int )
RETURNS decimal(28,6)
AS
BEGIN

	DECLARE @tipoArticuloId int;
	DECLARE @existencia decimal(28,6);

	SELECT @tipoArticuloId = ART_ARTT_TipoArticuloId
	FROM Articulos
	WHERE ART_ArticuloId = @articuloId

	IF(@tipoArticuloId = 4) BEGIN

		SELECT
			@existencia = MIN([dbo].[getExistenciaArticuloLocalidad](ARTC_ART_ComponenteId,@localidadId) / ARTC_Cantidad)
		FROM ArticulosComponentes
		WHERE ARTC_ART_ArticuloId = @articuloId

		RETURN COALESCE(@existencia,0);
	END

	SELECT
		@existencia = COALESCE(LOCAA_Cantidad,0)
	FROM Articulos
	INNER JOIN Localidades ON 1 = 1
	LEFT JOIN LocalidadesArticulos ON ART_ArticuloId = LOCA_ART_ArticuloId AND LOC_LocalidadId = LOCA_LOC_LocalidadId
	LEFT JOIN LocalidadesArticulosAcumulados ON ART_ArticuloId = LOCAA_ART_ArticuloId AND LOC_LocalidadId = LOCAA_LOC_LocalidadId
	WHERE ART_ArticuloId = @articuloId AND LOC_LocalidadId = @localidadId
	
	RETURN COALESCE(@existencia,0);

END
GO