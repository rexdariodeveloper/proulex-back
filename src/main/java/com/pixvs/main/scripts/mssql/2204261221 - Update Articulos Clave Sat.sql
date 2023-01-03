UPDATE ART
SET ART.ART_ClaveProductoSAT =PROGI_CLAVE
FROM Articulos ART
INNER JOIN ProgramasIdiomas on ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
WHERE ART_ClaveProductoSAT IS NULL
GO