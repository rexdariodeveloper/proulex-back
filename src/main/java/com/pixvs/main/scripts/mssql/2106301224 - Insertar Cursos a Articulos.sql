INSERT INTO [dbo].[Articulos]
([ART_CodigoArticulo],[ART_NombreArticulo],[ART_Descripcion],
 [ART_DescripcionCorta],[ART_PermitirCambioAlmacen],[ART_PlaneacionTemporadas],[ART_AFAM_FamiliaId],
 [ART_ACAT_CategoriaId],[ART_ARTT_TipoArticuloId],[ART_UM_UnidadMedidaInventarioId],
 [ART_CMM_TipoCostoId],[ART_CostoUltimo],[ART_CostoPromedio],[ART_CostoEstandar],[ART_Activo],[ART_FechaCreacion],
 [ART_Inventariable],[ART_ArticuloParaVenta],[ART_PROGI_ProgramaIdiomaId])
Select 
CONCAT(PROG_Codigo,'',CMM_Referencia) as codigo,
CONCAT(PROG_Codigo,' ',CMM_Valor) as nombre, 
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
PROGI_ProgramaIdiomaId
FROM ProgramasIdiomas
INNER JOIN Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
INNER JOIN ControlesMaestrosMultiples on CMM_ControlId = PROGI_CMM_Idioma
WHERE PROGI_Activo = CAST(1 as BIT)
GO