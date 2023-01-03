/*
update Articulos
set ART_CodigoArticulo = 'ART' + RIGHT('000000' + CAST(ART_ArticuloId AS varchar(10)),6)
*/

ALTER TABLE [dbo].[Articulos] ADD CONSTRAINT [UNQ_ART_CodigoArticulo] UNIQUE ([ART_CodigoArticulo])
GO