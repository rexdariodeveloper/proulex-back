DECLARE @quertys TABLE (Id INT, Querty NVARCHAR(4000))

INSERT INTO @quertys
SELECT ROW_NUMBER() OVER (ORDER BY Tabla, column_id), 'UPDATE Autonumericos SET AUT_Siguiente = AUT_Siguiente + 1 FROM Autonumericos INNER JOIN ' + Tabla + ' ON AUT_Prefijo+RIGHT(''00000000000000000''+CONVERT(VARCHAR(10), AUT_Siguiente), AUT_Digitos) = ' + Columna
FROM
(
    SELECT SO.NAME AS Tabla,
           SC.NAME AS Columna,
           ST.NAME AS tipo,
		   column_id
    FROM sys.objects SO
         INNER JOIN sys.columns SC ON SO.OBJECT_ID = SC.OBJECT_ID
         INNER JOIN sys.types ST ON sc.system_type_id = ST.system_type_id
    WHERE SO.TYPE = 'U'
      AND ST.NAME != 'SYSNAME'
	  AND (SC.NAME LIKE '%folio%' OR SC.NAME LIKE '%codigo%') AND SC.NAME NOT LIKE '% %'
) AS tablas

DECLARE @noRegistros INT = ( SELECT COUNT(Id) FROM @quertys )
DECLARE @contador INT = 1
DECLARE @sqlCommand NVARCHAR(4000)

WHILE (@contador <= @noRegistros)
BEGIN
		SET @sqlCommand = ( SELECT Querty FROM @quertys WHERE Id = @contador )
		
		EXEC (@sqlCommand)

		SET @contador = @contador + 1
END