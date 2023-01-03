UPDATE MenuPrincipal SET MP_Orden = MP_Orden + 1 WHERE MP_NodoPadreId IS NULL AND MP_TituloEN <> 'Income'
GO