SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_RPT_ALUMNOS_PAGOS]
AS
select 
	ALU_CodigoAlumnoUDG codigo,
	ALU_PrimerApellido primerApellido,
	ALU_SegundoApellido segundoApellido,
	ALU_Nombre nombre,
	COALESCE(centros.CMM_Valor, prepas.CMM_Valor) plantel,
	PROGRU_Codigo clave,
	CONCAT_WS(' ',PROG_Codigo,idiomas.CMM_Valor,PAMOD_Nombre,'Nivel',FORMAT(PROGRU_Nivel,'00'),'Grupo',FORMAT(PROGRU_Grupo,'00')) grupo,
	CONCAT_WS(' - ',CAST(PAMODH_HoraInicio as nvarchar(5)),CAST(PAMODH_HoraFin as nvarchar(5))) horario,
	case when PROG_JOBSSEMS = 1 then null else SedesGrupo.SUC_Nombre end sede,
	SP_Nombre escuela,
	ALU_Referencia referencia,
	case when INS_CMM_EstatusId = 2000510 then OV_Codigo else null end poliza,
	case when INS_CMM_EstatusId = 2000510 then (OVD_Cantidad * OVD_Precio) + hijos.precio else null end precio,
	estatus.CMM_Valor estatus,

	Sedes.SUC_SucursalId sedeId,
	PROGRU_SP_SucursalPlantelId plantelId,
	PROGRU_PACIC_CicloId cicloId,
	PROGRU_PAC_ProgramacionAcademicaComercialId paId,
	CAST(PROGRU_FechaInicio as date) fechaInicio,
	INS_CMM_EstatusId estatusId
from 
Inscripciones 
inner join ProgramasGrupos on INS_PROGRU_GrupoId = PROGRU_GrupoId
inner join Alumnos on INS_ALU_AlumnoId = ALU_AlumnoId
left join ControlesMaestrosMultiples centros on ALU_CMM_CentroUniversitarioJOBSId = centros.CMM_ControlId
left join ControlesMaestrosMultiples prepas on ALU_CMM_PreparatoriaJOBSId = prepas.CMM_ControlId
inner join ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
inner join ControlesMaestrosMultiples idiomas on PROGI_CMM_Idioma = idiomas.CMM_ControlId
inner join Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId
inner join PAModalidades on PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
inner join PAModalidadesHorarios on PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
left join SucursalesPlanteles on PROGRU_SP_SucursalPlantelId = SP_SucursalPlantelId
inner join OrdenesVentaDetalles on INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
inner join OrdenesVenta on OVD_OV_OrdenVentaId = OV_OrdenVentaId
left join (
	select OVD_OVD_DetallePadreId padreId, SUM(OVD_Cantidad * OVD_Precio) precio from OrdenesVentaDetalles group by OVD_OVD_DetallePadreId
) hijos on OVD_OrdenVentaDetalleId = padreId
left join Sucursales SedesGrupo on PROGRU_SUC_SucursalId = SedesGrupo.SUC_SucursalId
left join Sucursales Sedes on OV_SUC_SucursalId = Sedes.SUC_SucursalId
left join AlumnosGrupos on ALUG_INS_InscripcionId = INS_InscripcionId
left join ControlesMaestrosMultiples estatus on ALUG_CMM_EstatusId = estatus.CMM_ControlId
where
	INS_CMM_EstatusId <> 2000512
GO


