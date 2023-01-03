

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
	LEFT JOIN LocalidadesArticulos ON ART_ArticuloId = LOCA_ART_ArticuloId
	LEFT JOIN Localidades ON LOC_LocalidadId = LOCA_LOC_LocalidadId
	LEFT JOIN LocalidadesArticulosAcumulados ON ART_ArticuloId = LOCAA_ART_ArticuloId AND LOC_LocalidadId = LOCAA_LOC_LocalidadId
	WHERE ART_ArticuloId = @articuloId AND LOC_LocalidadId = @localidadId
	
	RETURN COALESCE(@existencia,0);

END
GO


CREATE OR ALTER VIEW [dbo].[VW_LISTADO_ARTICULOS_PAQUETES_EXISTENCIAS]
AS
	SELECT
		ART_ArticuloId AS articuloId,
		LOC_LocalidadId AS localidadId,
		[dbo].[getExistenciaArticuloLocalidad](ART_ArticuloId,LOC_LocalidadId) AS existencia
	FROM Articulos
	INNER JOIN Localidades ON 1=1
	WHERE ART_ARTT_TipoArticuloId = 4
GO