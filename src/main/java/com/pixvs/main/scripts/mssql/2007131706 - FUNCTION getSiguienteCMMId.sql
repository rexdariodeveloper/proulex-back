SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[getSiguienteCMMId] (@devolverCMMSistema BIT)

RETURNS int

AS BEGIN
	
	DECLARE @siguienteCMMId int;

	IF @devolverCMMSistema = 1 BEGIN
		SELECT @siguienteCMMId = ((((COALESCE(MAX(CMM_ControlId),1000000) - 1) / 10) + 1) * 10) + 1
		FROM ControlesMaestrosMultiples
		WHERE CMM_ControlId > 1000000
	END ELSE BEGIN
		SELECT @siguienteCMMId = COALESCE(MAX(CMM_ControlId),0) + 1
		FROM ControlesMaestrosMultiples
		WHERE CMM_ControlId <= 1000000
	END

	return @siguienteCMMId
END	
GO

