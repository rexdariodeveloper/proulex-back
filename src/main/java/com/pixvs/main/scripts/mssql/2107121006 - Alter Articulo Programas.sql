Delete from ListadosPreciosDetalles where LIPRED_ListadoPrecioDetalleId is not null;
UPDATE Clientes set CLI_LIPRE_ListadoPrecioId = null where CLI_LIPRE_ListadoPrecioId is not null;
UPDATE Sucursales set SUC_LIPRE_ListadoPrecioId = null where SUC_LIPRE_ListadoPrecioId is not null;
Delete from ListadosPrecios where LIPRE_Codigo is not null;
Delete from Articulos where ART_ARTT_TipoArticuloId=5 AND ART_AFAM_FamiliaId=9 AND ART_ACAT_CategoriaId=37 AND ART_ArticuloParaVenta=1;
