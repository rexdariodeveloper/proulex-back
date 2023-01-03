CREATE OR ALTER VIEW [dbo].[VW_CORTES]
AS
(
	select
		corteId,
		SUC_Nombre sede,
		CONCAT(USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido) usuario,
		fechaInicio,
		fechaFin,
		factura,
		codigoOV,
		curso,
		libro,
		examen,
		otros,
		total,
		totalFiscal,
		claveCurso,
		codigoArticulo,
		descripcionArticulo,
		cuentaDeposito,
		metodoPago,
		referenciaPago,
		fechaPago
	from
	(
		select 
			SCC_SucursalCorteCajaId corteId,
			SCC_SUC_SucursalId sucursalId,
			SCC_USU_UsuarioAbreId usuarioId,
			MAX(SCC_FechaInicio) fechaInicio,
			MAX(coalesce(SCC_FechaFin, getdate())) fechaFin,
			'' factura,
			OV_Codigo codigoOV,
			SUM(CASE WHEN ART_PROGI_ProgramaIdiomaId IS NOT NULL THEN OVD_Cantidad * OVD_Precio ELSE 0 END) curso,
			SUM(CASE WHEN ART_ARTST_ArticuloSubtipoId = 2 THEN OVD_Cantidad * OVD_Precio ELSE 0 END) libro,
			SUM(CASE WHEN ART_ARTST_ArticuloSubtipoId = 6 THEN OVD_Cantidad * OVD_Precio ELSE 0 END) examen,
			SUM(CASE WHEN ART_PROGI_ProgramaIdiomaId IS NULL AND ART_ARTST_ArticuloSubtipoId <> 2 AND ART_ARTST_ArticuloSubtipoId <> 6 THEN OVD_Cantidad * OVD_Precio ELSE 0 END) otros,
			SUM(OVD_Cantidad * OVD_Precio) total,
			'' totalFiscal,
			coalesce(grupoCodigo,'NO APLICA') claveCurso,
			ART_CodigoArticulo codigoArticulo,
			ART_NombreArticulo descripcionArticulo,
			'' cuentaDeposito,
			MPPV_Nombre metodoPago,
			OV_ReferenciaPago referenciaPago,
			format(OV_FechaCreacion,'dd/MM/yyyy HH:mm') fechaPago

		from  
			SucursalesCortesCajas 
			inner join OrdenesVenta on OV_FechaCreacion between SCC_FechaInicio and coalesce(SCC_FechaFin, getdate())
			inner join MediosPagoPV on MPPV_MedioPagoPVId = OV_MPPV_MedioPagoPVId
			inner join OrdenesVentaDetalles on OVD_OV_OrdenVentaId = OV_OrdenVentaId
			inner join Articulos on ART_ArticuloId = OVD_ART_ArticuloId
			left join Inscripciones on INS_OVD_OrdenVentaId = OVD_OrdenVentaDetalleId
			left join VW_Codigo_ProgramasGrupos on INS_PROGRU_GrupoId = grupoId
		group by
			SCC_SucursalCorteCajaId, SCC_SUC_SucursalId, OV_Codigo, grupoCodigo, ART_CodigoArticulo, ART_NombreArticulo, MPPV_Nombre, OV_ReferenciaPago, OV_FechaCreacion, SCC_USU_UsuarioAbreId
	) as t inner join Sucursales on SUC_SucursalId = t.sucursalId
	inner join Usuarios on USU_UsuarioId = t.usuarioId
)
GO