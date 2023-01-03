SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* ****************************************************************
 * VW_Listado_Nueva_Coantratacion
 * ****************************************************************
 * Descripción: La vista de el listado de Nueva Contratacion
 * Autor: Rene Carrillo
 * Fecha: 07.04.2022
 * Fecha de Modificar: 19.04.2022
 * Versión: 1.0.1
 *****************************************************************
*/
CREATE OR ALTER VIEW [dbo].[VW_Listado_Nueva_Contratacion]
AS
	WITH TipoContrato AS (
		SELECT empco.EMPCO_EmpleadoContratoId, TipoContrato = STRING_AGG(RTRIM(emp.CuantasTipoContrato) + ' ' + emp.CMM_Valor, ',')
		FROM EmpleadosContratos empco
			INNER JOIN (SELECT emp.EMP_EMPCO_EmpleadoContratoId, COUNT(empl.EMPL_CMM_TipoContratoId) AS CuantasTipoContrato, cmm.CMM_Valor
						FROM EmpleadosLaborales empl
							INNER JOIN Empleados emp ON empl.EMPL_EMP_EmpleadoId = emp.EMP_EmpleadoId AND emp.EMP_CMM_EstatusId = 2000950
							INNER JOIN ControlesMaestrosMultiples cmm ON empl.EMPL_CMM_TipoContratoId = cmm.CMM_ControlId
						GROUP BY emp.EMP_EMPCO_EmpleadoContratoId, empl.EMPL_CMM_TipoContratoId, cmm.CMM_Valor
						) AS emp ON empco.EMPCO_EmpleadoContratoId = emp.EMP_EMPCO_EmpleadoContratoId
		WHERE empco.EMPCO_CMM_TipoProcesoRHId = 2000900
		GROUP BY empco.EMPCO_EmpleadoContratoId
	)

	SELECT
		empco.EMPCO_EmpleadoContratoId AS id,
		empco.EMPCO_Codigo AS  codigo,
		empco.EMPCO_FechaCreacion AS fechaCreacion,
		(u.USU_Nombre + ' ' + u.USU_PrimerApellido) AS usuario,
		tp.TipoContrato AS tipoContrato,
		(SELECT COUNT(*) FROM Empleados e WHERE e.EMP_EMPCO_EmpleadoContratoId = empco.EMPCO_EmpleadoContratoId AND e.EMP_CMM_EstatusId = 2000950) AS cantidadEmpleado,
		cmm.CMM_Valor as estatus
	FROM EmpleadosContratos empco
		INNER JOIN Usuarios u ON empco.EMPCO_USU_CreadoPorId = u.USU_UsuarioId
		INNER JOIN ControlesMaestrosMultiples cmm ON empco.EMPCO_CMM_EstatusId = cmm.CMM_ControlId
		INNER JOIN TipoContrato tp ON empco.EMPCO_EmpleadoContratoId = tp.EMPCO_EmpleadoContratoId
	WHERE empco.EMPCO_CMM_TipoProcesoRHId = 2000900
