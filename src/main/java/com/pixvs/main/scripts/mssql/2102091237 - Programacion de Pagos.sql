CREATE OR ALTER VIEW [dbo].[VW_RPT_CXPSolicitudesPagos] AS
SELECT 
		CXPS_CXPSolicitudPagoId as id, CXPS_CodigoSolicitud as codigo, 
		CONVERT(DATE, CXPS_FechaCreacion) AS fechaCreacion,
		dbo.getNombreCompletoUsuario(CXPS_USU_CreadoPorId) as creador, CXPS_USU_CreadoPorId as creadorId,
		SUC_SucursalId as sucursalId, coalesce(SUC_Nombre, '-') as sucursal

,spd.* 
FROM CXPSolicitudesPagos
LEFT JOIN Sucursales on CXPS_SUC_SucursalId = SUC_SucursalId

LEFT JOIN (
	SELECT CXPSD_CXPS_CXPSolicitudPagoId, cmm_es.CMM_Valor as detalleEstatus, cmm_es.CMM_ControlId as detalleEstatusId, COUNT ( DISTINCT cxpf.idProveedor ) as proveedores,
		count(DISTINCT CXPSD_CXPF_CXPFacturaId) as facturas, sum(total) as total, sum(total) as totalMN
	FROM CXPSolicitudesPagosDetalles
	LEFT JOIN ControlesMaestrosMultiples cmm_es on CXPSD_CMM_EstatusId = cmm_es.CMM_ControlId
	LEFT JOIN dbo.[VW_CXPFacturas] cxpf on CXPSD_CXPF_CXPFacturaId = cxpf.id

	GROUP BY CXPSD_CXPS_CXPSolicitudPagoId, cmm_es.CMM_Valor, cmm_es.CMM_ControlId

) spd on CXPSD_CXPS_CXPSolicitudPagoId = CXPS_CXPSolicitudPagoId

--WHERE CXPS_CXPSolicitudPagoId = 1073