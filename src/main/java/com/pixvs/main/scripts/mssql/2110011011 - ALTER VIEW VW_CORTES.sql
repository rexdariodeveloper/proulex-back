SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_CORTES]
AS
(
	SELECT
		corteId, 
		SUC_Nombre sede, 
		CONCAT(USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido) usuario, 
		SCC_FechaInicio fechaInicio, 
		COALESCE(SCC_FechaFin, getdate()) fechaFin, 
		'' factura, 
		ovs.OV_Codigo codigoOV, 
		(curso + d_curso) curso,
		(libro + d_libro) libro,
		(examen + d_examen) examen,
		(otros + d_otros) otros,
		(total + d_total) total,
		'' totalFiscal,
		COALESCE(PROGRU_Codigo, '') claveCurso,
		ART_CodigoArticulo codigoArticulo,
		ART_NombreArticulo descripcionArticulo,
		'' cuentaDeposito,
		MPPV_Nombre metodoPago,
		ovs.OV_ReferenciaPago referenciaPago,
		FORMAT(fechaPago,'dd/MM/yyyy HH:mm:ss') fechaPago,
		CMM_Valor estatus,
		CASE WHEN OV_CMM_EstatusId = 2000508 THEN CASE WHEN curso > 0 THEN curso - d_total ELSE curso END ELSE 0 END pagadoCurso,
		CASE WHEN OV_CMM_EstatusId = 2000508 THEN (libro + d_libro) ELSE 0 END pagadoLibro,
		CASE WHEN OV_CMM_EstatusId = 2000508 THEN (examen + d_examen) ELSE 0 END pagadoExamen,
		CASE WHEN OV_CMM_EstatusId = 2000508 THEN (otros + d_otros) ELSE 0 END pagadoOtros,
		CASE WHEN OV_CMM_EstatusId = 2000508 THEN total ELSE 0 END pagadoTotal,
		PROGRU_GrupoId grupoId
	FROM
	(
	SELECT
		OV_Codigo,
		OV_MPPV_MedioPagoPVId,
		OV_ReferenciaPago,
		coalesce(OV_FechaModificacion, OV_FechaCreacion) fechaPago,
		SUM(CASE WHEN primario.ART_PROGI_ProgramaIdiomaId IS NOT NULL THEN padre.OVD_Cantidad * padre.OVD_Precio ELSE 0 END) curso,
		SUM(CASE WHEN primario.ART_ARTST_ArticuloSubtipoId = 2 THEN padre.OVD_Cantidad * padre.OVD_Precio ELSE 0 END) libro,
		SUM(CASE WHEN primario.ART_ARTST_ArticuloSubtipoId = 5 THEN padre.OVD_Cantidad * padre.OVD_Precio ELSE 0 END) examen,
		SUM(CASE WHEN primario.ART_PROGI_ProgramaIdiomaId IS NULL AND primario.ART_ARTST_ArticuloSubtipoId <> 2 AND primario.ART_ARTST_ArticuloSubtipoId <> 5 THEN padre.OVD_Cantidad * padre.OVD_Precio ELSE 0 END) otros,
		SUM(padre.OVD_Cantidad * padre.OVD_Precio) total,
		SUM(CASE WHEN secundario.ART_PROGI_ProgramaIdiomaId IS NOT NULL THEN hijos.OVD_Cantidad * hijos.OVD_Precio ELSE 0 END) d_curso,
		SUM(CASE WHEN secundario.ART_ARTST_ArticuloSubtipoId = 2 THEN hijos.OVD_Cantidad * hijos.OVD_Precio ELSE 0 END) d_libro,
		SUM(CASE WHEN secundario.ART_ARTST_ArticuloSubtipoId = 5 THEN hijos.OVD_Cantidad * hijos.OVD_Precio ELSE 0 END) d_examen,
		SUM(CASE WHEN secundario.ART_PROGI_ProgramaIdiomaId IS NULL AND primario.ART_ARTST_ArticuloSubtipoId <> 2 AND primario.ART_ARTST_ArticuloSubtipoId <> 5 THEN hijos.OVD_Cantidad * hijos.OVD_Precio ELSE 0 END) d_otros,
		SUM(hijos.OVD_Cantidad * hijos.OVD_Precio) d_total,
		OV_CMM_EstatusId,
		OV_SCC_SucursalCorteCajaId corteId,
		padre.OVD_ART_ArticuloId articuloId,
		padre.OVD_OrdenVentaDetalleId detalleId
	FROM 
		OrdenesVenta
		INNER JOIN OrdenesVentaDetalles padre on OV_OrdenVentaId = padre.OVD_OV_OrdenVentaId AND OVD_OVD_DetallePadreId IS NULL
		LEFT JOIN OrdenesVentaDetalles hijos on padre.OVD_OrdenVentaDetalleId = hijos.OVD_OVD_DetallePadreId
		INNER JOIN Articulos primario on padre.OVD_ART_ArticuloId = primario.ART_ArticuloId
		LEFT JOIN Articulos secundario on hijos.OVD_ART_ArticuloId = secundario.ART_ArticuloId
	WHERE
		OV_SCC_SucursalCorteCajaId IS NOT NULL
	GROUP BY
		OV_Codigo, OV_MPPV_MedioPagoPVId, OV_ReferenciaPago, OV_FechaModificacion, OV_FechaCreacion, OV_CMM_EstatusId, OV_SCC_SucursalCorteCajaId, padre.OVD_OrdenVentaDetalleId, padre.OVD_ART_ArticuloId, hijos.OVD_OVD_DetallePadreId
	) ovs
	INNER JOIN SucursalesCortesCajas ON ovs.corteId = SCC_SucursalCorteCajaId
	INNER JOIN Sucursales ON SCC_SUC_SucursalId = SUC_SucursalId
	INNER JOIN Usuarios ON SCC_USU_UsuarioAbreId = USU_UsuarioId
	INNER JOIN Articulos ON ovs.articuloId = ART_ArticuloId
	LEFT JOIN Inscripciones ON ovs.detalleId = INS_OVD_OrdenVentaId
	LEFT JOIN ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
	INNER JOIN MediosPagoPV ON ovs.OV_MPPV_MedioPagoPVId = MPPV_MedioPagoPVId
	INNER JOIN ControlesMaestrosMultiples ON ovs.OV_CMM_EstatusId = CMM_ControlId
)
GO