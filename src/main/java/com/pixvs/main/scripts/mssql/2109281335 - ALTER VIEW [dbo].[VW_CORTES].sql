SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_CORTES]
AS
(
	SELECT
		corteId, sede, usuario, fechaInicio, fechaFin, factura, codigoOV, 
		CASE WHEN curso > 0 THEN curso - d_total ELSE curso END curso,
		(libro + d_libro) libro,
		(examen + d_examen) examen,
		(otros + d_otros) otros,
		total,
		totalFiscal,
		claveCurso,
		codigoArticulo,
		descripcionArticulo,
		cuentaDeposito,
		metodoPago,
		referenciaPago,
		FORMAT(fechaPago,'dd/MM/yyyy HH:mm:ss') fechaPago,
		estatus,
		CASE WHEN estatusId = 2000508 THEN CASE WHEN curso > 0 THEN curso - d_total ELSE curso END ELSE 0 END pagadoCurso,
		CASE WHEN estatusId = 2000508 THEN (libro + d_libro) ELSE 0 END pagadoLibro,
		CASE WHEN estatusId = 2000508 THEN (examen + d_examen) ELSE 0 END pagadoExamen,
		CASE WHEN estatusId = 2000508 THEN (otros + d_otros) ELSE 0 END pagadoOtros,
		CASE WHEN estatusId = 2000508 THEN total ELSE 0 END pagadoTotal,
		grupoId
	FROM
	(
	SELECT 
		SCC_SucursalCorteCajaId corteId,
		SUC_Nombre sede,
		CONCAT(USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido) usuario,
		SCC_FechaInicio fechaInicio,
		coalesce(SCC_FechaFin, getdate()) fechaFin,
		'' factura,
		OV_Codigo codigoOV,
		SUM(CASE WHEN primario.ART_PROGI_ProgramaIdiomaId IS NOT NULL THEN OVD_Cantidad * LIPRED_Precio ELSE 0 END) curso,
		SUM(CASE WHEN primario.ART_ARTST_ArticuloSubtipoId = 2 THEN OVD_Cantidad * LIPRED_Precio ELSE 0 END) libro,
		SUM(CASE WHEN primario.ART_ARTST_ArticuloSubtipoId = 6 THEN OVD_Cantidad * LIPRED_Precio ELSE 0 END) examen,
		SUM(CASE WHEN primario.ART_PROGI_ProgramaIdiomaId IS NULL AND primario.ART_ARTST_ArticuloSubtipoId <> 2 AND primario.ART_ARTST_ArticuloSubtipoId <> 6 THEN OVD_Cantidad * LIPRED_Precio ELSE 0 END) otros,
		SUM(OVD_Cantidad * LIPRED_Precio) total,
		SUM(CASE WHEN secundario.ART_PROGI_ProgramaIdiomaId IS NOT NULL THEN OVD_Cantidad * LIPREDC_Precio ELSE 0 END) d_curso,
		SUM(CASE WHEN secundario.ART_ARTST_ArticuloSubtipoId = 2 THEN OVD_Cantidad * LIPREDC_Precio ELSE 0 END) d_libro,
		SUM(CASE WHEN secundario.ART_ARTST_ArticuloSubtipoId = 6 THEN OVD_Cantidad * LIPREDC_Precio ELSE 0 END) d_examen,
		SUM(CASE WHEN secundario.ART_PROGI_ProgramaIdiomaId IS NULL AND primario.ART_ARTST_ArticuloSubtipoId <> 2 AND primario.ART_ARTST_ArticuloSubtipoId <> 6 THEN OVD_Cantidad * LIPREDC_Precio ELSE 0 END) d_otros,
		SUM(OVD_Cantidad * LIPREDC_Precio) d_total,
		'' totalFiscal,
		coalesce(vw.grupoCodigo, '') claveCurso,
		primario.ART_CodigoArticulo codigoArticulo,
		primario.ART_NombreArticulo descripcionArticulo,
		'' cuentaDeposito,
		MPPV_Nombre metodoPago,
		OV_ReferenciaPago referenciaPago,
		coalesce(OV_FechaModificacion, OV_FechaCreacion) fechaPago,
		estatus.CMM_Valor estatus,
		estatus.CMM_ControlId estatusId,
		vw.grupoId grupoId
	FROM
	SucursalesCortesCajas 
	INNER JOIN OrdenesVenta ON SCC_SucursalCorteCajaId = OV_SCC_SucursalCorteCajaId 
	INNER JOIN OrdenesVentaDetalles ON OV_OrdenVentaId = OVD_OV_OrdenVentaId 
	INNER JOIN Sucursales ON OV_SUC_SucursalId = SUC_SucursalId 
	INNER JOIN ListadosPrecios ON SUC_LIPRE_ListadoPrecioId = LIPRE_ListadoPrecioId 
	INNER JOIN ListadosPreciosDetalles ON OVD_ART_ArticuloId = LIPRED_ART_ArticuloId AND LIPRE_ListadoPrecioId = LIPRED_LIPRE_ListadoPrecioId 
	LEFT JOIN ListadosPreciosDetallesCursos ON LIPRED_ListadoPrecioDetalleId = LIPREDC_LIPRED_ListadoPrecioDetalleId
	INNER JOIN Articulos primario ON OVD_ART_ArticuloId = primario.ART_ArticuloId
	LEFT JOIN Articulos secundario ON LIPREDC_ART_ArticuloId = secundario.ART_ArticuloId
	INNER JOIN MediosPagoPV ON MPPV_MedioPagoPVId = OV_MPPV_MedioPagoPVId
	INNER JOIN Usuarios ON USU_UsuarioId = SCC_USU_UsuarioAbreId
	LEFT JOIN Inscripciones ON INS_OVD_OrdenVentaId = OVD_OrdenVentaDetalleId
	LEFT JOIN VW_Codigo_ProgramasGrupos vw ON vw.grupoId = INS_PROGRU_GrupoId
	INNER JOIN ControlesMaestrosMultiples estatus ON OV_CMM_EstatusId = estatus.CMM_ControlId
	WHERE OVD_OVD_DetallePadreId IS NULL AND OV_CMM_EstatusId NOT IN (2000500, 2000501, 2000504)
	GROUP BY
		SCC_SucursalCorteCajaId, SUC_Nombre, SCC_FechaInicio, SCC_FechaFin, OV_Codigo, grupoCodigo, MPPV_Nombre, OV_ReferenciaPago, 
		OV_FechaModificacion, OV_FechaCreacion, CMM_ControlId, CMM_Valor, USU_Nombre, USU_PrimerApellido, USU_SegundoApellido, primario.ART_CodigoArticulo, 
		primario.ART_NombreArticulo, vw.grupoId
	) reporte
)
GO