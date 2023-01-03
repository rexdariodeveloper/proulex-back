DECLARE @TipoPersonaFisica INT = 2000410
DECLARE @TipoPersonaMoral INT = 2000411

DECLARE @tipoPersonaId INT = 2000410

SELECT *
FROM SATRegimenesFiscales
WHERE CASE WHEN @tipoPersonaId = @TipoPersonaFisica AND RF_Fisica = 1 THEN 1 
	  ELSE CASE WHEN @tipoPersonaId = @TipoPersonaMoral AND RF_Moral = 1 THEN 1 ELSE 0 END END = 1
	  AND RF_Activo = 1

SELECT SATUsosCFDI.*
FROM SATUsosCFDI
     INNER JOIN SATUsosCFDIRegimenesFiscales ON UCFDI_UsoCFDIId = UCFDIRF_UCFDI_UsoCFDIId AND UCFDIRF_RF_RegimenFiscalId = 4
WHERE CASE WHEN @tipoPersonaId = @TipoPersonaFisica AND UCFDI_Fisica = 1 THEN 1
      ELSE CASE WHEN @tipoPersonaId = @TipoPersonaMoral AND UCFDI_Moral = 1 THEN 1 ELSE 0 END END = 1
	  AND UCFDI_Activo = 1

--SELECT UCFDI_Codigo,
--       UCFDI_Fisica,
--       UCFDI_Moral,
--	   RF_Codigo,
--	   RF_Fisica,
--       RF_Moral
--FROM SATUsosCFDIRegimenesFiscales
--     INNER JOIN SATUsosCFDI ON UCFDIRF_UCFDI_UsoCFDIId = UCFDI_UsoCFDIId
--     INNER JOIN SATRegimenesFiscales ON UCFDIRF_RF_RegimenFiscalId = RF_RegimenFiscalId
--WHERE SATUsosCFDI.UCFDI_Fisica != SATRegimenesFiscales.RF_Fisica
--      OR SATUsosCFDI.UCFDI_Moral != SATRegimenesFiscales.RF_Moral
--ORDER BY UCFDI_UsoCFDIId,
--       RF_RegimenFiscalId