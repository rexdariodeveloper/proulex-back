SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER TRIGGER [dbo].[trg_actualizacionBecas] ON [dbo].[BECAS_ALUMNOS_RH] AFTER UPDATE
AS
BEGIN
	DECLARE @ID [int]
	DECLARE @CODCURUDG [varchar](10)
	DECLARE @ESTATUSUDG [varchar](2)
	--select * from inserted;
	--select * from deleted;
	DECLARE cur CURSOR FOR SELECT ID, CODCURUDG, ESTATUSUDG FROM INSERTED
    OPEN cur
    FETCH NEXT FROM cur INTO @ID, @CODCURUDG, @ESTATUSUDG
    WHILE @@FETCH_STATUS = 0
    BEGIN
		DECLARE @estatusActualId [int]
		DECLARE @estatusNuevoId  [int]
		IF @ESTATUSUDG IS NULL BEGIN SET @estatusNuevoId = 2000570 END
		IF @ESTATUSUDG = N'AP' BEGIN SET @estatusNuevoId = 2000571 END
		IF @ESTATUSUDG = N'CA' BEGIN SET @estatusNuevoId = 2000572 END
		IF @ESTATUSUDG = N'CX' BEGIN SET @estatusNuevoId = 2000573 END
		SELECT @estatusActualId = [BECU_CMM_EstatusId] FROM [dbo].[BecasUDG] WHERE [BECU_SIAPId] = @ID
		--select @estatusActualId, @estatusNuevoId, @STATUSPLX;
		IF @estatusActualId = 2000570 --Solo actualizar la beca si se encuentra 'Pendiente por aplicar'
		BEGIN
			UPDATE [dbo].[BecasUDG] 
				SET BECU_CMM_EstatusId = @estatusNuevoId 
			FROM 
				[dbo].[BecasUDG] 
			WHERE
				BECU_SIAPId = @ID;
		END
      FETCH NEXT FROM cur INTO @ID, @CODCURUDG, @ESTATUSUDG
    END
    CLOSE cur
    DEALLOCATE cur
END