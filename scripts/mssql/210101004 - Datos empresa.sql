INSERT [dbo].[ControlesMaestros] (
	[CMA_Nombre],
	[CMA_Valor],
	[CMA_Sistema],
	[CMA_FechaModificacion]
) 
VALUES (
	N'CMA_CCNF_EmpresaNombre',
	N'',
	1,
	GETDATE()
),(
	N'CMA_CCNF_EmpresaSede',
	N'',
	1,
	GETDATE()
),(
	N'CMA_CCNF_EmpresaId',
	N'',
	1,
	GETDATE()
)
GO



SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[getDatosEmpresa]()
RETURNS 
@tbl TABLE 
(	
	VALOR NVARCHAR(100),
	ORDEN INT
)
AS
BEGIN

		INSERT INTO @tbl
		SELECT CMA_Valor,
			   CASE CMA_Nombre WHEN 'CMA_CCNF_EmpresaId' THEN 1 WHEN 'CMA_CCNF_EmpresaNombre' THEN 2 WHEN 'CMA_CCNF_EmpresaSede' THEN 3 END AS orden
		FROM ControlesMaestros
		WHERE CMA_Nombre IN('CMA_CCNF_EmpresaId', 'CMA_CCNF_EmpresaNombre', 'CMA_CCNF_EmpresaSede')
		ORDER BY orden
	
	RETURN 
END