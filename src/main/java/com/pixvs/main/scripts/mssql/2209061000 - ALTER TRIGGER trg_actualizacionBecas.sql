SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER TRIGGER [dbo].[trg_actualizacionBecas] ON [dbo].[BECAS_ALUMNOS_RH] AFTER UPDATE
AS
BEGIN		
		UPDATE becas
		  SET
			  BECU_CMM_EstatusId =
					CASE WHEN TRIM(ISNULL(inserted.ESTATUSUDG, '')) = '' THEN 2000570 ELSE
					CASE WHEN inserted.ESTATUSUDG = 'AP' THEN 2000571 ELSE
					CASE WHEN inserted.ESTATUSUDG = 'CA' THEN 2000572 ELSE
					CASE WHEN inserted.ESTATUSUDG = 'CX' THEN 2000573 ELSE
					CASE WHEN inserted.ESTATUSUDG = 'EN' THEN 2000570 ELSE
					becas.BECU_CMM_EstatusId
					END END END END END
		FROM inserted
			 INNER JOIN BecasUDG AS becas ON inserted.ID = becas.BECU_SIAPId
											 AND becas.BECU_CMM_EstatusId = 2000570 --Solo actualizar la beca si se encuentra 'Pendiente por aplicar'
END