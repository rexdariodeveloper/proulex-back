SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_LISTADO_SOLICITUD_PAGO] AS
	SELECT DISTINCT
		CXPSPS_CXPSolicitudPagoServicioId AS id,
		CXPSPS_CodigoSolicitudPagoServicio AS codigoSolicitud, 
		CXPSPS_FechaCreacion AS fechaSolicitud,
		DATEADD(day, CXPF_DiasCredito, CXPF_FechaRegistro) AS fechaVencimiento,
		SUC_Nombre AS sede,
		tipoSolicitud.CMM_Valor AS solicitud,
		ART_NombreArticulo AS servicio,
		coalesce(USU_Nombre,'')+' '+coalesce(USU_PrimerApellido,'')+' '+coalesce(USU_SegundoApellido,'') AS usuario,
		estatus.CMM_Valor AS estatus,
		'{
			"id": ' + CAST(facturaPDF.ARC_ArchivoId AS varchar) + ',
			"nombreOriginal": "' + facturaPDF.ARC_NombreOriginal + '",
			"tipo": null,
			"nombreFisico": null,
			"rutaFisica": null,
			"publico": null,
			"activo": true,
			"creadoPor": null,
			"fechaCreacion": "' + CAST(facturaPDF.ARC_FechaCreacion AS varchar) + '"
		}' AS facturaPDFStr,
		'{
			"id": ' + CAST(facturaXML.ARC_ArchivoId AS varchar) + ',
			"nombreOriginal": "' + facturaXML.ARC_NombreOriginal + '",
			"tipo": null,
			"nombreFisico": null,
			"rutaFisica": null,
			"publico": null,
			"activo": true,
			"creadoPor": null,
			"fechaCreacion": "' + CAST(facturaXML.ARC_FechaCreacion AS varchar) + '"
		}' AS facturaXMLStr,
		PRO_Nombre AS proveedorNombre,
		CXPF_MontoRegistro AS montoRegistro,
		SUC_SucursalId AS sedeId
	FROM
		CXPSolicitudesPagosServicios 
		LEFT JOIN CXPSolicitudesPagosServiciosDetalles ON CXPSPS_CXPSolicitudPagoServicioId = CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId
		LEFT JOIN CXPFacturas ON CXPSPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		LEFT JOIN CXPFacturasDetalles ON CXPF_CXPFacturaId = CXPFD_CXPF_CXPFacturaId
		LEFT JOIN Sucursales ON CXPSPS_SUC_SucursalId = SUC_SucursalId
		LEFT JOIN Servicios ON CXPFD_ART_ArticuloId = SRV_ART_ArticuloId
		LEFT JOIN Articulos ON SRV_ART_ArticuloId = ART_ArticuloId
		LEFT JOIN ControlesMaestrosMultiples tipoSolicitud ON tipoSolicitud.CMM_ControlId = SRV_CMM_TipoServicioId
		LEFT JOIN ControlesMaestrosMultiples estatus ON CXPSPS_CMM_EstatusId = estatus.CMM_ControlId
		LEFT JOIN Usuarios ON CXPSPS_USU_CreadoPorId = USU_UsuarioId
		LEFT JOIN Archivos facturaPDF ON facturaPDF.ARC_ArchivoId = CXPF_ARC_FacturaPDFId
		LEFT JOIN Archivos facturaXML ON facturaXML.ARC_ArchivoId = CXPF_ARC_FacturaXMLId
		LEFT JOIN Proveedores ON PRO_ProveedorId = CXPF_PRO_ProveedorId
GO