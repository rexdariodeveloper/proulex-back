SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* ****************************************************************
 * VW_Listado_Baja
 * ****************************************************************
 * Descripción: La vista de el listado de Baja
 * Autor: Rene Carrillo
 * Fecha: 31.08.2022
 * Versión: 1.0.0
 *****************************************************************
*/
CREATE OR ALTER VIEW [dbo].[VW_Listado_Baja]
AS
	SELECT
		sbc.SBC_SolicitudBajaContratacionId AS Id,
		sbc.SBC_Codigo AS Codigo,
		sbc.SBC_FechaCreacion AS FechaCreacion,
		(u.USU_Nombre + ' ' + u.USU_PrimerApellido) AS Usuario,
		(emp.EMP_Nombre + ' ' + emp.EMP_PrimerApellido) AS Empleado,
		tp.CMM_Valor AS TipoContrato,
		cmm.CMM_Valor AS Estatus
	FROM SolicitudesBajasContrataciones sbc
		INNER JOIN Usuarios u ON sbc.SBC_USU_CreadoPorId = u.USU_UsuarioId
		INNER JOIN EmpleadosContratos ec ON sbc.SBC_EMPCO_EmpleadoContratoId = ec.EMPCO_EmpleadoContratoId
		INNER JOIN Empleados emp ON ec.EMPCO_EMP_EmpleadoId = emp.EMP_EmpleadoId
		INNER JOIN ControlesMaestrosMultiples tp ON ec.EMPCO_CMM_TipoContratoId = tp.CMM_ControlId
		INNER JOIN ControlesMaestrosMultiples cmm ON sbc.SBC_CMM_EstatusId = cmm.CMM_ControlId
	WHERE sbc.SBC_CMM_EstatusId = 2000956
GO