SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_comparaCadenas] (@textoA VARCHAR(4000), @textoB VARCHAR(4000))
RETURNS BIT
AS
BEGIN
		SET @textoA = UPPER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(
							@textoA
						, '�', 'a'), '�', 'e'), '�', 'i'), '�', 'o'), '�', 'u'), '�', 'A'), '�', 'E'), '�', 'I'), '�', 'O'), '�', 'U'), ' ', ''), '-', ''), '(', ''), ')', ''), '_', ''), '.', ''))

		SET @textoB = UPPER(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(
							@textoB
						, '�', 'a'), '�', 'e'), '�', 'i'), '�', 'o'), '�', 'u'), '�', 'A'), '�', 'E'), '�', 'I'), '�', 'O'), '�', 'U'), ' ', ''), '-', ''), '(', ''), ')', ''), '_', ''), '.', ''))

		RETURN CASE WHEN @textoA = @textoB THEN 1 ELSE 0 END
END