Update Articulos
SET ART_ClaveProductoSAT = PROGI_CLAVE, ART_IVA=PROGI_IVA, ART_IEPS=PROGI_IEPS, ART_IVAExento = PROGI_IVAExento, ART_IEPSCuotaFija = PROGI_CuotaFija
FROM ProgramasIdiomas
WHERE ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId