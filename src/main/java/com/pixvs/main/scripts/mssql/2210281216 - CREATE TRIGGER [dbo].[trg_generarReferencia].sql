SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TRIGGER [dbo].[trg_generarReferencia] ON  [dbo].[ALUMNOS] AFTER INSERT
AS 
BEGIN
	UPDATE [dbo].[Alumnos] 
	SET [Alumnos].[ALU_Referencia] = [dbo].[fn_getDigitoVerificador]([Alumnos].[ALU_Codigo], 1) 
	FROM inserted INNER JOIN [dbo].[ALUMNOS] ON inserted.[ALU_AlumnoId] = [dbo].[Alumnos].[ALU_AlumnoId]
END
GO