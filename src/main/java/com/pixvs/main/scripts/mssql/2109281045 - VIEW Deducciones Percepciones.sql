SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_DEDUCCIONES_PERCEPCIONES_EXCEL]
AS
(
	Select 
	EDP_Codigo as codigo,
	EDP_Fecha as fecha,
	CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as empleado,
	CMM_Valor,
	DEDPER_Concepto as concepto,
	FORMAT(EDP_Total, 'C', 'en-us') as monto
	from DeduccionesPercepciones
	INNER JOIN EmpleadosDeduccionesPercepciones on EDP_DEDPER_DeduccionPercepcionId = DEDPER_DeduccionPercepcionId
	INNER JOIN Empleados on EMP_EmpleadoId = EDP_EMP_EmpleadoId
	INNER JOIN ControlesMaestrosMultiples on CMM_ControlId = DEDPER_CMM_TipoId
	WHERE EDP_Activo = 1
)
GO