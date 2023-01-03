

UPDATE MenuPrincipal
SET MP_Titulo = 'Artículos'
WHERE MP_Titulo = 'Articulos' and MP_Icono = 'free_breakfast' and MP_Orden = 2
GO

UPDATE MenuPrincipal
SET MP_Titulo = 'Órdenes de Compra'
WHERE MP_Titulo = 'Ordenes Compra' and MP_Icono = 'assignment' and MP_Orden = 1
GO

UPDATE MenuPrincipal
SET MP_Titulo = 'Menú Listados Generales'
WHERE MP_Titulo = 'Menu Listados Generales' and MP_Icono = 'list' and MP_Orden = 3
GO