SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW VW_CONDENSADO_SIAP_INGRESOS
AS
	select 
		IMPORTE_TOTAL ingresos,
		TIPO_DESCUENTO descuentoDescripcion,
		IMPORTE_DESCUENTO descuentoImporte,
		SUC_Nombre nombreSucursal,
		tipoSucursal.CMM_Valor tipoSucursal,
		SUBSTRING(DES3,8,2) mes,
		SUBSTRING(DES3,11,4) año,
		SUBSTRING(CODCUR,4,3) curso,
		SUBSTRING(CODCUR,9,2) horario,
		COALESCE(idioma.CMM_Valor, 'Otro') idioma,
		COALESCE(programa.CMM_Valor, 'Otro') programa,

		SUC_SucursalId sucursalId
	from 
		PAGOSDET 
		inner join Sucursales on SEDE_PIXVS_ID = SUC_SucursalId
		inner join ControlesMaestrosMultiples tipoSucursal on SUC_CMM_TipoSucursalId = tipoSucursal.CMM_ControlId
		left join _SIAP_CURSOS curso ON SUBSTRING(CODCUR,4,3) = curso.CODIGO_CURSO
		left join (select CODIGO_CURSO, MAX(ART_ArticuloId) articuloId from _SIAP_CURSOS_LIBROS group by CODIGO_CURSO) libros ON curso.CODIGO_CURSO = libros.CODIGO_CURSO
		left join Articulos on libros.articuloId = Articulos.ART_ArticuloId
		left join ControlesMaestrosMultiples idioma on ART_CMM_IdiomaId = idioma.CMM_ControlId
		left join ControlesMaestrosMultiples programa on ART_CMM_ProgramaId = programa.CMM_ControlId
	where
		DES3 IS NOT NULL
		AND DES3 like 'DEL %'
		AND DES3 not like '%/1899%';
GO