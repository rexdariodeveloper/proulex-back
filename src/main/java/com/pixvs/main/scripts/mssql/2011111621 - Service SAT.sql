INSERT [dbo].[ControlesMaestros] (
	[CMA_Nombre],
	[CMA_Valor],
	[CMA_Sistema],
	[CMA_FechaModificacion]
) 
VALUES (
	N'CMA_ServiceSAT_URL',
	N'http://auditoriotelmex.pixvs.com:9000',
	1,
	GETDATE()
),(
	N'CMA_ServiceSAT_ListaNegra',
	N'/listanegrasat/rfc/',
	1,
	GETDATE()
),(
	N'CMA_ServiceSAT_ListadoEmpresas',
	N'/listanegrasat/listadoempresas/',
	1,
	GETDATE()
),(
	N'CMA_ServiceSAT_RptListaNegraSAT',
	N'/listanegrasat/rptlistanegrasat/',
	1,
	GETDATE()
)
GO


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[getPathServiceSAT] ( @control NVARCHAR(50) )
RETURNS VARCHAR(200)
AS
BEGIN
	DECLARE @URL NVARCHAR(100)
	DECLARE @path NVARCHAR(100)

	SELECT @URL = CMA_Valor
	FROM ControlesMaestros
	WHERE CMA_Nombre = 'CMA_ServiceSAT_URL'

	SELECT @path = CMA_Valor
	FROM ControlesMaestros
	WHERE CMA_Nombre = @control

	RETURN @URL + @path
END