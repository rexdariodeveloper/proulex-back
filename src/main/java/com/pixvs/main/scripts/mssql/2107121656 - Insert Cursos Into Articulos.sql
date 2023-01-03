ALTER TABLE Articulos
ADD ART_PAMOD_ModalidadId int null;

ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_PAMOD_ModalidadId] FOREIGN KEY([ART_PAMOD_ModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_PAMOD_ModalidadId]
GO

Delete from ListadosPreciosDetalles where LIPRED_ListadoPrecioDetalleId is not null;
UPDATE Clientes set CLI_LIPRE_ListadoPrecioId = null where CLI_LIPRE_ListadoPrecioId is not null;
UPDATE Sucursales set SUC_LIPRE_ListadoPrecioId = null where SUC_LIPRE_ListadoPrecioId is not null;
Delete from ListadosPrecios where LIPRE_Codigo is not null;
Delete from Articulos where ART_ARTT_TipoArticuloId=5 AND ART_AFAM_FamiliaId=9 AND ART_ACAT_CategoriaId=37 AND ART_ArticuloParaVenta=1;


INSERT INTO [dbo].[Articulos]
([ART_CodigoArticulo],[ART_NombreArticulo],[ART_Descripcion],
 [ART_DescripcionCorta],[ART_PermitirCambioAlmacen],[ART_PlaneacionTemporadas],[ART_AFAM_FamiliaId],
 [ART_ACAT_CategoriaId],[ART_ARTT_TipoArticuloId],[ART_UM_UnidadMedidaInventarioId],
 [ART_CMM_TipoCostoId],[ART_CostoUltimo],[ART_CostoPromedio],[ART_CostoEstandar],[ART_Activo],[ART_FechaCreacion],
 [ART_Inventariable],[ART_ArticuloParaVenta],[ART_PROGI_ProgramaIdiomaId],[ART_PAMOD_ModalidadId])
Select DISTINCT
CONCAT(PROG_Codigo,' ',CMM_Referencia,' ',PAMOD_Codigo) as codigo,
CONCAT(PROG_Codigo,' ',CMM_Valor, ' ',PAMOD_Nombre) as nombre, 
PROGI_Descripcion as descripcion,
'' as descripcionCorta,
CAST(0 AS BIT) as permiteCambio,
0 as planeacion,
(Select AFAM_FamiliaId from ArticulosFamilias where AFAM_Nombre='Servicios') as familia,
(Select ACAT_CategoriaId from ArticulosCategorias where ACAT_Nombre='Servicios') as categoria,
5 as tipoArticulo,
(Select UM_UnidadMedidaId from UnidadesMedidas where UM_Nombre='Pieza') as unidadMedida,
2000041 as tipoCosto,
0,
0,
0,
1,
GETDATE(),
1,
1,
PROGI_ProgramaIdiomaId,
PAMOD_ModalidadId
FROM ProgramasIdiomas
INNER JOIN Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
INNER JOIN ControlesMaestrosMultiples on CMM_ControlId = PROGI_CMM_Idioma
INNER JOIN ProgramasIdiomasModalidades on PROGIM_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
INNER JOIN PAModalidades on PAMOD_ModalidadId = PROGIM_PAMOD_ModalidadId
WHERE PROGI_Activo = CAST(1 as BIT)
GO
