ALTER TABLE ProgramasIdiomasExamenes ADD PROGIE_Orden INT NULL
GO

DECLARE @programaIdiomaNivelId INT
DECLARE @programaIdiomaExamenId INT

DECLARE listaId CURSOR FOR SELECT pie.PROGIE_PROGIN_ProgramaIdiomaNivelId FROM ProgramasIdiomasExamenes pie WHERE pie.PROGIE_Activo = 1 GROUP BY pie.PROGIE_PROGIN_ProgramaIdiomaNivelId

OPEN listaId
FETCH NEXT FROM listaId INTO @programaIdiomaNivelId

WHILE @@FETCH_STATUS = 0
BEGIN
	PRINT(@programaIdiomaNivelId)

	DECLARE @orden INT = 0

	DECLARE _listaId CURSOR FOR SELECT pie.PROGIE_ProgramaIdiomaExamenId FROM ProgramasIdiomasExamenes pie WHERE pie.PROGIE_PROGIN_ProgramaIdiomaNivelId = @programaIdiomaNivelId AND pie.PROGIE_Activo = 1

	OPEN _listaId
	FETCH NEXT FROM _listaId INTO @programaIdiomaExamenId
	WHILE @@FETCH_STATUS = 0
	BEGIN
		PRINT(@programaIdiomaExamenId)

		UPDATE ProgramasIdiomasExamenes SET PROGIE_Orden = @orden WHERE PROGIE_ProgramaIdiomaExamenId = @programaIdiomaExamenId

		SET @orden = @orden + 1
		FETCH NEXT FROM _listaId INTO @programaIdiomaExamenId
	END
	CLOSE _listaId
	DEALLOCATE _listaId

	FETCH NEXT FROM listaId INTO @programaIdiomaNivelId
END
CLOSE listaId
DEALLOCATE listaId
GO


