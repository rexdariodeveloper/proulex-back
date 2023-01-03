SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER TRIGGER [dbo].[trg_actualizacionBecas] ON [dbo].[BECAS_ALUMNOS_RH] AFTER UPDATE
AS
BEGIN
	DECLARE @ID [int]
	DECLARE @CODCURUDG [varchar](10)
	DECLARE @STATUSPLX [varchar](2)
	DECLARE cur CURSOR FOR SELECT ID, CODCURUDG, STATUSPLX FROM INSERTED
    OPEN cur
    FETCH NEXT FROM cur INTO @ID, @CODCURUDG, @STATUSPLX
    WHILE @@FETCH_STATUS = 0
    BEGIN
		DECLARE @estatusActualId [int]
		DECLARE @estatusNuevoId  [int]
		IF @STATUSPLX IS NULL BEGIN SET @estatusNuevoId = 2000570 END
		IF @STATUSPLX = N'AP' BEGIN SET @estatusNuevoId = 2000571 END
		IF @STATUSPLX = N'CA' BEGIN SET @estatusNuevoId = 2000572 END
		IF @STATUSPLX = N'CX' BEGIN SET @estatusNuevoId = 2000573 END
		SELECT @estatusActualId = [BECU_CMM_EstatusId] FROM [dbo].[BecasUDG] WHERE [BECU_SIAPId] = @ID
		IF @estatusActualId = 2000570 --Solo actualizar la beca si se encuentra 'Pendiente por aplicar'
		BEGIN
			UPDATE [dbo].[BecasUDG] 
				SET BECU_CMM_EstatusId = @estatusNuevoId 
			FROM 
				[dbo].[BecasUDG] 
				INNER JOIN INSERTED ON BECU_SIAPId = ID
			WHERE
				ID = @ID;
		END
      FETCH NEXT FROM cur INTO @ID, @CODCURUDG, @STATUSPLX
    END
    CLOSE cur
    DEALLOCATE cur
END