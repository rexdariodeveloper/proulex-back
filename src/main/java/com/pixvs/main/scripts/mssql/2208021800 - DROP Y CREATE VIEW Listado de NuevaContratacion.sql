DROP VIEW IF EXISTS VW_Listado_Nueva_Contratacion
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* ****************************************************************
 * VW_Listado_NuevaCoantratacion
 * ****************************************************************
 * Descripción: La vista de el listado de Nueva Contratacion
 * Autor: Rene Carrillo
 * Fecha: 07.04.2022
 * Fecha de Modificar: 02.08.2022
 * Versión: 1.0.3
 *****************************************************************
*/
CREATE OR ALTER VIEW [dbo].[VW_Listado_NuevaContratacion]
AS
	WITH TipoContrato AS (
		SELECT sncd.SNCD_SNC_SolicitudNuevaContratacionId as SolicitudNuevaContratacionId, COUNT(ec.EMPCO_CMM_TipoContratoId) AS CuantasTipoContrato, cmm.CMM_Valor
		FROM SolicitudesNuevasContratacionesDetalles sncd
			INNER JOIN EmpleadosContratos ec ON sncd.SNCD_EMP_EmpleadoId = ec.EMPCO_EMP_EmpleadoId
			INNER JOIN ControlesMaestrosMultiples cmm ON ec.EMPCO_CMM_TipoContratoId = cmm.CMM_ControlId
		WHERE sncd.SNCD_CMM_EstatusId = 2000956
		GROUP BY sncd.SNCD_SNC_SolicitudNuevaContratacionId, ec.EMPCO_CMM_TipoContratoId, cmm.CMM_Valor

	)

	SELECT
		snc.SNC_SolicitudNuevaContratacionId as id,
		snc.SNC_Codigo as codigo,
		snc.SNC_FechaCreacion as fechaCreacion,
		(u.USU_Nombre + ' ' + u.USU_PrimerApellido) AS usuario,
		(SELECT COUNT(*) FROM SolicitudesNuevasContratacionesDetalles sncd WHERE sncd.SNCD_SNC_SolicitudNuevaContratacionId = snc.SNC_SolicitudNuevaContratacionId) as cantidadEmpleado,
		STRING_AGG(RTRIM(tp.CuantasTipoContrato) + ' ' + tp.CMM_Valor, ',') as tipoContrato,
		cmm.CMM_Valor as estatus
	FROM SolicitudesNuevasContrataciones snc
		INNER JOIN Usuarios u ON snc.SNC_USU_CreadoPorId = u.USU_UsuarioId
		INNER JOIN TipoContrato tp ON snc.SNC_SolicitudNuevaContratacionId = tp.SolicitudNuevaContratacionId
		INNER JOIN ControlesMaestrosMultiples cmm ON snc.SNC_CMM_EstatusId = cmm.CMM_ControlId
	WHERE snc.SNC_CMM_EstatusId = 2000956
	GROUP BY snc.SNC_SolicitudNuevaContratacionId, snc.SNC_Codigo, snc.SNC_FechaCreacion, u.USU_Nombre, u.USU_PrimerApellido, cmm.CMM_Valor
