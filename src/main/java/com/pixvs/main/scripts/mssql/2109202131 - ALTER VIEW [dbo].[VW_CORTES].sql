SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_CORTES]
AS
(
	select 
		SCC_SucursalCorteCajaId corteId,
		SUC_Nombre sede,
		CONCAT(USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido) usuario,
		SCC_FechaInicio fechaInicio,
		coalesce(SCC_FechaFin, getdate()) fechaFin,
		'' factura,
		OV_Codigo codigoOV,
		curso,
		libro,
		examen,
		otros,
		total,
		'' totalFiscal,
		coalesce(vw.grupoCodigo, '') claveCurso,
		codigoArticulo,
		descripcionArticulo,
		'' cuentaDeposito,
		MPPV_Nombre metodoPago,
		OV_ReferenciaPago referenciaPago,
		coalesce(OV_FechaModificacion, OV_FechaCreacion) fechaPago 
	from
	SucursalesCortesCajas 
	inner join OrdenesVenta on coalesce(OV_FechaModificacion,OV_FechaCreacion) >= SCC_FechaInicio
								and coalesce(OV_FechaModificacion,OV_FechaCreacion) <= SCC_FechaFin
								and SCC_USU_UsuarioAbreId = coalesce(OV_USU_ModificadoPorId, OV_USU_CreadoPorId)
	inner join
	(
		select  
			OVD_OV_OrdenVentaId ovId,
			ovd.curso + coalesce(desglose.curso,0) curso,
			ovd.libro + coalesce(desglose.libro,0) libro,
			ovd.examen + coalesce(desglose.examen,0) examen,
			ovd.otros + coalesce(desglose.otros,0) otros,
			ovd.total + coalesce(desglose.total,0) total,
			ART_CodigoArticulo codigoArticulo,
			ART_NombreArticulo descripcionArticulo,
			INS_PROGRU_GrupoId grupoId
		from
		(
			select 
				OVD_OV_OrdenVentaId,
				OVD_OrdenVentaDetalleId,
				CASE WHEN ART_PROGI_ProgramaIdiomaId IS NOT NULL THEN OVD_Cantidad * OVD_Precio ELSE 0 END curso,
				CASE WHEN ART_ARTST_ArticuloSubtipoId = 2 THEN OVD_Cantidad * OVD_Precio ELSE 0 END libro,
				CASE WHEN ART_ARTST_ArticuloSubtipoId = 6 THEN OVD_Cantidad * OVD_Precio ELSE 0 END examen,
				CASE WHEN ART_PROGI_ProgramaIdiomaId IS NULL AND ART_ARTST_ArticuloSubtipoId <> 2 AND ART_ARTST_ArticuloSubtipoId <> 6 THEN OVD_Cantidad * OVD_Precio ELSE 0 END otros,
				OVD_Cantidad * OVD_Precio total,
				ART_CodigoArticulo,
				ART_NombreArticulo
			from 
				OrdenesVentaDetalles 
				inner join Articulos on ART_ArticuloId = OVD_ART_ArticuloId
			where OVD_OVD_DetallePadreId IS NULL
		) ovd left join
		(
		select 
			OVD_OV_OrdenVentaId ovId,
			OVD_OVD_DetallePadreId padreId,
			SUM(CASE WHEN ART_PROGI_ProgramaIdiomaId IS NOT NULL THEN OVD_Cantidad * OVD_Precio ELSE 0 END) curso,
			SUM(CASE WHEN ART_ARTST_ArticuloSubtipoId = 2 THEN OVD_Cantidad * OVD_Precio ELSE 0 END) libro,
			SUM(CASE WHEN ART_ARTST_ArticuloSubtipoId = 6 THEN OVD_Cantidad * OVD_Precio ELSE 0 END) examen,
			SUM(CASE WHEN ART_PROGI_ProgramaIdiomaId IS NULL AND ART_ARTST_ArticuloSubtipoId <> 2 AND ART_ARTST_ArticuloSubtipoId <> 6 THEN OVD_Cantidad * OVD_Precio ELSE 0 END) otros,
			SUM(OVD_Cantidad * OVD_Precio) total
		from 
			OrdenesVentaDetalles 
			inner join Articulos on ART_ArticuloId = OVD_ART_ArticuloId
		group by
			OVD_OV_OrdenVentaId, OVD_OVD_DetallePadreId
		having
			OVD_OVD_DetallePadreId IS NOT NULL
		) desglose on ovd.OVD_OrdenVentaDetalleId = desglose.padreId and OVD_OV_OrdenVentaId = desglose.ovId
		left join Inscripciones on INS_OVD_OrdenVentaId = ovd.OVD_OrdenVentaDetalleId
	) detalles on detalles.ovId = OV_OrdenVentaId
	inner join MediosPagoPV on MPPV_MedioPagoPVId = OV_MPPV_MedioPagoPVId
	left join VW_Codigo_ProgramasGrupos vw on detalles.grupoId = vw.grupoId
	inner join Sucursales on SUC_SucursalId = OV_SUC_SucursalId
	inner join Usuarios on USU_UsuarioId = SCC_USU_UsuarioAbreId
)
GO