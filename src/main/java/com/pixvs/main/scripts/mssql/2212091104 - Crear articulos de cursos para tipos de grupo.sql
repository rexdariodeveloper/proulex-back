/**
 * Created by Angel Daniel Hernández Silva on 21/10/2022.
 */

ALTER TABLE [dbo].[Articulos] ALTER COLUMN [ART_CodigoArticulo] varchar(60) NOT NULL
GO

DELETE PROGIM
FROM ProgramasIdiomasModalidades AS PROGIM
INNER JOIN (
	SELECT PROGIM_PROGI_ProgramaIdiomaId AS cursoId, PROGIM_PAMOD_ModalidadId AS modalidadId, MIN(PROGIM_ProgramaIdiomaModalidadId) AS originalId
	FROM ProgramasIdiomasModalidades
	GROUP BY PROGIM_PROGI_ProgramaIdiomaId, PROGIM_PAMOD_ModalidadId
	HAVING COUNT(*) > 1
) Repetidos ON cursoId = PROGIM_PROGI_ProgramaIdiomaId AND modalidadId = PROGIM_PAMOD_ModalidadId and originalId != PROGIM_ProgramaIdiomaModalidadId
GO

INSERT INTO Articulos(
	ART_CodigoArticulo,
	ART_NombreArticulo,
	ART_Descripcion,
	ART_ClaveProductoSAT,
	ART_DescripcionCorta,
	ART_PermitirCambioAlmacen,
	ART_CodigoAlterno,
	ART_PlaneacionTemporadas,
	ART_AFAM_FamiliaId,
	ART_ACAT_CategoriaId,
	ART_ARTT_TipoArticuloId,
	ART_UM_UnidadMedidaInventarioId,
	ART_CMM_TipoCostoId,
	ART_IVA,
	ART_IEPS,
	ART_CostoUltimo,
	ART_CostoPromedio,
	ART_CostoEstandar,
	ART_Activo,
	ART_Inventariable,
	ART_ArticuloParaVenta,
	ART_PROGI_ProgramaIdiomaId,
	ART_PAMOD_ModalidadId,
	ART_CMM_TipoGrupoId,
	ART_CMM_ObjetoImpuestoId,
	ART_FechaCreacion,
	ART_USU_CreadoPorId
)
SELECT
	CONCAT(PROG_Codigo, ' ', CMMIdioma.CMM_Referencia, ' ', PAMOD_Codigo, ' ', CMMTipoGrupo.CMM_Valor) AS ART_CodigoArticulo,
	CONCAT(PROG_Codigo, ' ', CMMIdioma.CMM_Valor, ' ', PAMOD_Nombre, ' ', CMMTipoGrupo.CMM_Valor) AS ART_NombreArticulo,
	PROGI_Descripcion AS ART_Descripcion,
	PROGI_CLAVE AS ART_ClaveProductoSAT,
	'' AS ART_DescripcionCorta,
	0 AS ART_PermitirCambioAlmacen,
	PROGI_CLAVE AS ART_CodigoAlterno,
	0 AS ART_PlaneacionTemporadas,
	9 AS ART_AFAM_FamiliaId,
	37 AS ART_ACAT_CategoriaId,
	5 AS ART_ARTT_TipoArticuloId,
	1 AS ART_UM_UnidadMedidaInventarioId,
	2000041 AS ART_CMM_TipoCostoId,
	CASE WHEN PROGI_IVA IS NULL THEN NULL ELSE PROGI_IVA / 100 END AS ART_IVA,
	CASE WHEN PROGI_IEPS IS NULL THEN NULL ELSE PROGI_IEPS / 100 END AS ART_IEPS,
	0 AS ART_CostoUltimo,
	0 AS ART_CostoPromedio,
	0 AS ART_CostoEstandar,
	1 AS ART_Activo,
	1 AS ART_Inventariable,
	1 AS ART_ArticuloParaVenta,
	PROGI_ProgramaIdiomaId AS ART_PROGI_ProgramaIdiomaId,
	PAMOD_ModalidadId AS ART_PAMOD_ModalidadId,
	CMMTipoGrupo.CMM_ControlId AS ART_CMM_TipoGrupoId,
	PROGI_CMM_ObjetoImpuestoId AS ART_CMM_ObjetoImpuestoId,
	GETDATE() AS ART_FechaCreacion,
	1 AS ART_USU_CreadoPorId
FROM ProgramasIdiomas
INNER JOIN ProgramasIdiomasModalidades ON PROGIM_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND PROGI_Activo = 1
INNER JOIN ControlesMaestrosMultiples AS CMMTipoGrupo ON CMMTipoGrupo.CMM_Control = 'CMM_PROGRU_TipoGrupo'
INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
INNER JOIN ControlesMaestrosMultiples AS CMMIdioma ON CMMIdioma.CMM_ControlId = PROGI_CMM_Idioma
INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGIM_PAMOD_ModalidadId
LEFT JOIN Articulos ON
	ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
	AND ART_PAMOD_ModalidadId = PROGIM_PAMOD_ModalidadId
	AND ART_CMM_TipoGrupoId = CMMTipoGrupo.CMM_ControlId
	AND ART_AFAM_FamiliaId = 9
	AND ART_ACAT_CategoriaId = 37
	AND ART_ARTT_TipoArticuloId = 5
WHERE ART_ArticuloId IS NULL
GO