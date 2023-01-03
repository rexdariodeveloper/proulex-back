SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[fn_getTransferenciaMovimientos] ( @transferenciaId INT )
RETURNS TABLE 
AS
RETURN 
(
	SELECT TRAD_TransferenciaDetalleId AS Id,
			ISNULL(TRA_FechaModificacion, TRA_FechaCreacion) AS Fecha,
			ART_CodigoArticulo AS CodigoArticulo,
			ART_NombreArticulo AS NombreArticulo,
			UM_Nombre AS UM,
			TRAD_Cantidad AS CantidadEnviada,
			TRAD_CantidadTransferida AS CantidadTransferida,
			TRAD_CantidadDevuelta AS CantidadDevuelta,
			TRAD_Spill AS CantidadAjuste,
			origen.ALM_Nombre AS AlmacenOrigen,
			destino.ALM_Nombre AS AlmacenDestino,
			USU_Nombre+ISNULL(' '+USU_PrimerApellido, '')+ISNULL(' '+USU_SegundoApellido, '') AS Usuario
	FROM Transferencias
			INNER JOIN TransferenciasDetalles ON TRA_TransferenciaId = TRAD_TRA_TransferenciaId
			INNER JOIN Articulos ON TRAD_ART_ArticuloId = ART_ArticuloId
			INNER JOIN UnidadesMedidas ON TRAD_UM_UnidadMedidaId = UM_UnidadMedidaId
			INNER JOIN Localidades AS locOrigen ON TRA_LOC_LocalidadOrigenId = locOrigen.LOC_LocalidadId
			INNER JOIN Almacenes AS origen ON locOrigen.LOC_ALM_AlmacenId = origen.ALM_AlmacenId
			INNER JOIN Localidades AS locDestino ON TRA_LOC_LocalidadDestinoId = locDestino.LOC_LocalidadId
			INNER JOIN Almacenes AS destino ON locDestino.LOC_ALM_AlmacenId = destino.ALM_AlmacenId
			INNER JOIN Usuarios ON ISNULL(TRA_USU_ModificadoPorId, TRA_USU_CreadoPorId) = USU_UsuarioId
	WHERE TRA_TransferenciaId = @transferenciaId
)
