/**
* Created by Angel Daniel Hernández Silva on 15/06/2022.
*/

INSERT INTO Articulos(
	ART_CodigoArticulo,
	ART_NombreArticulo,
	ART_CodigoAlterno,
	ART_Descripcion,
	ART_DescripcionCorta,
	ART_ClaveProductoSAT,
	ART_IVA,
	ART_IVAExento,
	ART_IEPS,
	ART_PermitirCambioAlmacen,
	ART_PlaneacionTemporadas,
	ART_AFAM_FamiliaId,
	ART_ACAT_CategoriaId,
	ART_ARTT_TipoArticuloId,
	ART_UM_UnidadMedidaInventarioId,
	ART_CMM_TipoCostoId,
	ART_CostoUltimo,
	ART_CostoPromedio,
	ART_CostoEstandar,
	ART_Activo,
	ART_Inventariable,
	ART_ArticuloParaVenta,
	ART_PROGI_ProgramaIdiomaId,
	ART_PAMOD_ModalidadId,
	ART_CMM_ObjetoImpuestoId,
	ART_FechaCreacion
)
SELECT
	CONCAT(PROG_Codigo,' ',CMM_Referencia,' ',PAMOD_Codigo), -- ART_CodigoArticulo
	CONCAT(PROG_Codigo,' ',CMM_Valor,' ',PAMOD_Nombre), -- ART_NombreArticulo
	PROGI_CLAVE, -- ART_CodigoAlterno
	PROGI_Descripcion, -- ART_Descripcion
	'', -- ART_DescripcionCorta
	ART_ClaveProductoSAT, -- ART_ClaveProductoSAT
	0, -- ART_IVA
	1, -- ART_IVAExento
	0, -- ART_IEPS
	0, -- ART_PermitirCambioAlmacen
	0, -- ART_PlaneacionTemporadas
	9, -- ART_AFAM_FamiliaId
	37, -- ART_ACAT_CategoriaId
	5, -- ART_ARTT_TipoArticuloId
	9, -- ART_UM_UnidadMedidaInventarioId
	2000041, -- ART_CMM_TipoCostoId
	0, -- ART_CostoUltimo
	0, -- ART_CostoPromedio
	0, -- ART_CostoEstandar
	1, -- ART_Activo
	1, -- ART_Inventariable
	0, -- ART_ArticuloParaVenta
	PROGI_ProgramaIdiomaId, -- ART_PROGI_ProgramaIdiomaId
	PAMOD_ModalidadId, -- ART_PAMOD_ModalidadId
	PROGI_CMM_ObjetoImpuestoId, -- ART_CMM_ObjetoImpuestoId
	GETDATE() -- ART_FechaCreacion
FROM ProgramasIdiomas
INNER JOIN PAModalidades ON PAMOD_Codigo = 'PER'
LEFT JOIN Articulos
	ON ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
	AND ART_PAMOD_ModalidadId = PAMOD_ModalidadId
	AND ART_ARTT_TipoArticuloId = 5
INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma
WHERE
	ART_ArticuloId IS NULL