/**
* Created by Angel Daniel Hernández Silva on 28/07/2021.
* Object:  CREATE FUNCTION [dbo].[getCMMReferencia]
*/

/**************************************/
/***** Campo CMM_CMM_ReferenciaId *****/
/**************************************/

ALTER TABLE [dbo].[ControlesMaestrosMultiples] ADD [CMM_CMM_ReferenciaId] int NULL
GO

ALTER TABLE [dbo].[ControlesMaestrosMultiples]  WITH CHECK ADD  CONSTRAINT [FK_CMM_CMM_ReferenciaId] FOREIGN KEY([CMM_CMM_ReferenciaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

/*************************************/
/***** FUNCTION getCMMReferencia *****/
/*************************************/

CREATE OR ALTER FUNCTION [dbo].[getCMMReferencia] (@control varchar(255))

RETURNS varchar(255)

AS BEGIN
	
	DECLARE @controlReferencia varchar(255) = NULL;

	SELECT @controlReferencia = CASE
		WHEN 1=2 THEN '' -- Borrar en main cuando se agreguen casos
		-- Ejemplo de caso donde un cmm de estados depende de otro cmm de paises: WHEN @control = 'CMM_EST_Estados' THEN 'CMM_PAI_Paises'
		ELSE NULL
	END

	return @controlReferencia
END	
GO