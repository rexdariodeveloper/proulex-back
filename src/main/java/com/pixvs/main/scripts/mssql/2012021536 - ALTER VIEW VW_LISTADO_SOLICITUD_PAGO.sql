SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_LISTADO_SOLICITUD_PAGO] AS
select 
	CXPS_CXPSolicitudPagoId id,
	CXPS_CodigoSolicitud codigoSolicitud, 
	CXPS_FechaCreacion fechaSolicitud,
	CXPF_FechaPago fechavencimiento,
	SUC_Nombre cede,
	tipoSolicitud.CMM_Valor solicitud,
	ART_NombreArticulo servicio,
	coalesce(USU_Nombre,'')+' '+coalesce(USU_PrimerApellido,'')+' '+coalesce(USU_SegundoApellido,'') usuario,
	estatus.CMM_Valor estatus
from 
	CXPSolicitudesPagos 
	left join CXPSolicitudesPagosDetalles on CXPS_CXPSolicitudPagoId = CXPSD_CXPS_CXPSolicitudPagoId
	left join CXPFacturas on CXPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
	left join CXPFacturasDetalles on CXPF_CXPFacturaId = CXPFD_CXPF_CXPFacturaId
	left join Sucursales on CXPS_SUC_SucursalId = SUC_SucursalId
	left join Servicios on CXPFD_ART_ArticuloId = SRV_ART_ArticuloId
	left join Articulos on SRV_ART_ArticuloId = ART_ArticuloId
	left join ControlesMaestrosMultiples tipoSolicitud on tipoSolicitud.CMM_ControlId = SRV_CMM_TipoServicioId
	left join ControlesMaestrosMultiples estatus on CXPS_CMM_EstatusId = estatus.CMM_ControlId
	left join Usuarios on CXPS_USU_CreadoPorId = USU_UsuarioId
GO