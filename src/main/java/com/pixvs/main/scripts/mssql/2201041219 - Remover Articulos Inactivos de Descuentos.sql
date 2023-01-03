Delete w from PADescuentosArticulos w
INNER JOIN Articulos a on a.ART_ArticuloId = w.PADESCA_ART_ArticuloId
WHERE a.ART_Activo=0
GO
