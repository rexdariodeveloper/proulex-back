SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* ****************************************************************
 * VW_Listado_Renovacion
 * ****************************************************************
 * Descripción: La vista de el listado de Renovacion
 * Autor: Rene Carrillo
 * Fecha: 27.04.2022
 * Fecha de Modificar: 02.08.2022
 * Versión: 1.0.1
 *****************************************************************
*/
ALTER VIEW [dbo].[VW_Listado_Renovacion]
AS
	WITH TipoContrato AS (
		SELECT srcd.SRCD_SRC_SolicitudRenovacionContratacionId as SolicitudNuevaContratacionId, COUNT(ec.EMPCO_CMM_TipoContratoId) AS CuantasTipoContrato, cmm.CMM_Valor
		FROM SolicitudesRenovacionesContratacionesDetalles srcd
			INNER JOIN EmpleadosContratos ec ON srcd.SRCD_EMP_EmpleadoId = ec.EMPCO_EMP_EmpleadoId AND ec.EMPCO_CMM_EstatusId = 2000955
			INNER JOIN ControlesMaestrosMultiples cmm ON ec.EMPCO_CMM_TipoContratoId = cmm.CMM_ControlId
		WHERE srcd.SRCD_CMM_EstatusId = 2000956
		GROUP BY srcd.SRCD_SRC_SolicitudRenovacionContratacionId, ec.EMPCO_CMM_TipoContratoId, cmm.CMM_Valor

	)

	SELECT
		src.SRC_SolicitudRenovacionContratacionId as id,
		src.SRC_Codigo as codigo,
		src.SRC_FechaCreacion as fechaCreacion,
		(u.USU_Nombre + ' ' + u.USU_PrimerApellido) AS usuario,
		(SELECT COUNT(*) FROM SolicitudesRenovacionesContratacionesDetalles srcd WHERE srcd.SRCD_SRC_SolicitudRenovacionContratacionId = src.SRC_SolicitudRenovacionContratacionId AND srcd.SRCD_CMM_EstatusId = 2000956) as cantidadEmpleado,
		STRING_AGG(RTRIM(tp.CuantasTipoContrato) + ' ' + tp.CMM_Valor, ',') as tipoContrato,
		cmm.CMM_Valor as estatus
	FROM SolicitudesRenovacionesContrataciones src
		INNER JOIN Usuarios u ON src.SRC_USU_CreadoPorId = u.USU_UsuarioId
		INNER JOIN TipoContrato tp ON src.SRC_SolicitudRenovacionContratacionId = tp.SolicitudNuevaContratacionId
		INNER JOIN ControlesMaestrosMultiples cmm ON src.SRC_CMM_EstatusId = cmm.CMM_ControlId
	WHERE src.SRC_CMM_EstatusId = 2000956
	GROUP BY src.SRC_SolicitudRenovacionContratacionId, src.SRC_Codigo, src.SRC_FechaCreacion, u.USU_Nombre, u.USU_PrimerApellido, cmm.CMM_Valor
