UPDATE Articulos
SET ART_UM_UnidadMedidaInventarioId=9
WHERE ART_PROGI_ProgramaIdiomaId IS NOT NULL 
GO


UPDATE ART
SET ART_IVA=(PROGI_IVA/100), ART_IEPS = (PROGI_IEPS / 100), ART_IVAExento = PROGI_IVAExento
FROM Articulos ART
INNER JOIN ProgramasIdiomas on PROGI_ProgramaIdiomaId = ART_PROGI_ProgramaIdiomaId
GO