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
		FORMAT(CAST(FECHA_CREACION AS date), 'MM') mes,
		FORMAT(CAST(FECHA_CREACION AS date), 'yyyy') año,
		SUBSTRING(CODCUR,4,3) curso,
		case when CODCUR like 'N-%' then NULL else SUBSTRING(CODCUR,9,2) end horario,
		COALESCE(idioma.CMM_Valor, 'Otro') programa,
		COALESCE(programa.CMM_Valor, 'Otro') idioma,

		SUC_SucursalId sucursalId
	from 
		PAGOSDET 
		inner join Sucursales on SEDE_PIXVS_ID = SUC_SucursalId
		inner join ControlesMaestrosMultiples tipoSucursal on SUC_CMM_TipoSucursalId = tipoSucursal.CMM_ControlId
		left join _SIAP_CURSOS curso ON SUBSTRING(CODCUR,4,3) = curso.CODIGO_CURSO
		left join ControlesMaestrosMultiples idioma on curso.CMM_IdiomaId = idioma.CMM_ControlId
		left join ControlesMaestrosMultiples programa on curso.CMM_ProgramaId = programa.CMM_ControlId
GO