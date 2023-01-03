SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Rene Carrillo
-- Create date: 31/05/2022
-- Description:	Funcion para obtener el alumno grupo
-- =============================================
CREATE FUNCTION [dbo].[fn_getAlumnoGrupoBoleta]
(
	@AlumnoGrupoId INT
)
RETURNS TABLE
AS
RETURN
(
	SELECT
		ALU_Codigo codigo,
		CONCAT_WS(' ', ALU_Nombre, ALU_PrimerApellido, ALU_SegundoApellido) alumno,
		PROG_Codigo programaCorto,
		PROG_Nombre programaLargo,
		idioma.CMM_Valor idioma,
		PAMOD_Nombre modalidad,
		tipo.CMM_Valor tipo,
		FORMAT(PROGRU_Nivel, '00') nivel,
		FORMAT(PROGRU_FechaInicio, 'dd/MM/yyyy') inicio,
		FORMAT(PROGRU_FechaFin, 'dd/MM/yyyy') fin,
		CONCAT_WS(' ',PAMODH_Horario,'h') horario,
		CONCAT_WS(' ', EMP_PrimerApellido, EMP_SegundoApellido, EMP_Nombre) instructor,
		CONCAT_WS(' - ', SUC_Nombre, SUC_Domicilio) sede,
		COALESCE(ALUG_CalificacionFinal,0) calificacion,
		[dbo].[NumeroLetra] (ALUG_CalificacionFinal) calificacionLetra,
		format(case when ALUG_CalificacionFinal < PROGI_CalificacionMinima then PROGRU_Nivel else case when PROGRU_Nivel = PROGI_NumeroNiveles then null else PROGRU_Nivel + 1 end end, '00') siguiente,
		BAN_Nombre institucion,
		BAC_Codigo cuenta,
		BAC_CLABE clabe,
		BAC_Convenio convenio,
		ALU_Referencia referencia
	FROM
		AlumnosGrupos
		inner join Alumnos on ALUG_ALU_AlumnoId = ALU_AlumnoId
		inner join ProgramasGrupos on  ALUG_PROGRU_GrupoId = PROGRU_GrupoId
		inner join ControlesMaestrosMultiples tipo on  PROGRU_CMM_TipoGrupoId = tipo.CMM_ControlId
		inner join ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		inner join Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId
		inner join ControlesMaestrosMultiples idioma on PROGI_CMM_Idioma = idioma.CMM_ControlId
		inner join PAModalidades on PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
		inner join PAModalidadesHorarios on PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
		inner join Inscripciones on INS_ALU_AlumnoId = ALU_AlumnoId and INS_PROGRU_GrupoId = PROGRU_GrupoId
		inner join OrdenesVentaDetalles on INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
		inner join OrdenesVenta on OVD_OV_OrdenVentaId = OV_OrdenVentaId
		left join Empleados on PROGRU_EMP_EmpleadoId = EMP_EmpleadoId
		inner join Sucursales on OV_SUC_SucursalId = SUC_SucursalId
		left join BancosCuentas on SUC_BAC_CuentaId = BAC_CuentaId
		left join Bancos on BAC_BAN_BancoId = BAN_BancoId
	WHERE
		ALUG_AlumnoGrupoId = @AlumnoGrupoId
)