
/****** Object:  UserDefinedFunction [dbo].[fn_getPrenominaPagosNoPagados]    Script Date: 19/10/2020 12:32:36 p. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getPrenominaPagosNoPagados] (@fechaInicioQuincena Date)
RETURNS @tablaTMP table(
	fecha DATE
)

AS BEGIN
	INSERT @tablaTMP
	SELECT *
	FROM(
		SELECT
			fecha
		FROM(
		
			select
				(Select fechaInicio from dbo.fn_getFechaInicioFinQuincena(PROGRU_FechaInicio)) as fecha
			from Empleados
			inner join ProgramasGrupos on PROGRU_EMP_EmpleadoId = EMP_EmpleadoId
			left join Sucursales on SUC_SucursalId = PROGRU_SUC_SucursalId
			left join PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
			inner join PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
			inner join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			left join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
			left join ControlesMaestrosMultiples CMM on CMM_ControlId = PROGI_CMM_Idioma
			left join TabuladoresCursos on TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND TABC_PROG_ProgramaId = PROG_ProgramaId AND TABC_PAMOD_ModalidadId = PAMOD_ModalidadId AND TABC_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
			left join Tabuladores on TAB_TabuladorId = TABC_TAB_TabuladorId
			inner join ProgramasGruposListadoClases
				ON PROGRULC_PROGRU_GrupoId = PROGRU_GrupoId
				AND PROGRULC_CMM_FormaPagoId IN (2000520,2000523) AND PROGRULC_Fecha < @fechaInicioQuincena 
			LEFT JOIN Prenominas on PRENO_PROGRU_GrupoId = PROGRU_GrupoId AND PRENO_FechaInicioQuincena < @fechaInicioQuincena AND PRENO_EMP_EmpleadoId = EMP_EmpleadoId
			WHERE PRENO_PrenominaId is null and PROGRU_Activo = 1

		) Q1
		GROUP BY fecha

		UNION ALL

		SELECT
			fecha
		FROM(
				select
				PROGRULC_Fecha as fecha
				from Empleados
				INNER join ProgramasGruposListadoClases ON PROGRULC_CMM_FormaPagoId IN (2000520,2000522) AND PROGRULC_EMP_EmpleadoId = EMP_EmpleadoId AND PROGRULC_Fecha < @fechaInicioQuincena -- pago a suplente y con pago
				inner join ProgramasGrupos on PROGRU_GrupoId = PROGRULC_PROGRU_GrupoId
				left join Sucursales on SUC_SucursalId = PROGRU_SUC_SucursalId
				left join PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
				inner join PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
				left join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
				left join TabuladoresCursos on TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND TABC_PAMOD_ModalidadId = PAMOD_ModalidadId AND TABC_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
				left join Tabuladores on TAB_TabuladorId = TABC_TAB_TabuladorId
				left join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
				left join ControlesMaestrosMultiples CMM on CMM_ControlId = PROGI_CMM_Idioma
				left JOIN Prenominas on PRENO_PROGRU_GrupoId = PROGRU_GrupoId AND PRENO_EMP_EmpleadoId = EMP_EmpleadoId AND PRENO_FechaClaseSuplida = PROGRULC_Fecha
				WHERE PRENO_PrenominaId is null
		) Q2
		GROUP BY fecha

		UNION ALL

		SELECT
			fecha
		FROM(
			select
				EDP_Fecha as fecha
			from Empleados
			INNER join EmpleadosDeduccionesPercepciones ON EDP_EMP_EmpleadoId = EMP_EmpleadoId AND EDP_Activo=1
			INNER join DeduccionesPercepciones on DEDPER_DeduccionPercepcionId = EDP_DEDPER_DeduccionPercepcionId
			INNER join Sucursales on SUC_SucursalId = EDP_SUC_SucursalId
			INNER join Tabuladores on TAB_TabuladorId = DEDPER_TAB_TabuladorId
			LEFT JOIN Prenominas on PRENO_EDP_EmpleadoDeduccionPercepcionId = EDP_EmpleadoDeduccionPercepcionId AND PRENO_FechaFinQuincena >= EDP_FechaCreacion AND PRENO_FechaInicioQuincena <= EDP_FechaCreacion
			WHERE PRENO_PrenominaId is null AND EDP_FechaCreacion < @fechaInicioQuincena
		) Q3
		GROUP By fecha

		UNION ALL

		SELECT
			fecha
		FROM(
		SELECT 
		fecha as fecha
		from Empleados
		inner join ProgramasGrupos on PROGRU_EMP_EmpleadoId = EMP_EmpleadoId
		inner join Sucursales on SUC_SucursalId = PROGRU_SUC_SucursalId
		inner join PAModalidades modalidad on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
		inner join PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
		inner join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
		inner join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
		inner join ControlesMaestrosMultiples CMM on CMM_ControlId = PROGI_CMM_Idioma
		inner join TabuladoresCursos on TABC_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND TABC_PROG_ProgramaId = PROG_ProgramaId AND TABC_PAMOD_ModalidadId = PAMOD_ModalidadId AND TABC_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
		inner join Tabuladores on TAB_TabuladorId = TABC_TAB_TabuladorId
		outer apply dbo.fn_getDiasFestivos(PROGRU_FechaInicio,@fechaInicioQuincena,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado)--  fecha >= '2021-09-16' and fecha <= '2021-09-30'
		where fecha > PROGRU_FechaInicio AND fecha < PROGRU_FechaFin and EMP_FechaAlta <= fecha AND fecha is not null AND concat(PROGRU_GrupoId,fecha) not in (Select concat(PRENO_PROGRU_GrupoId,PRENO_DiasFestivo) from Prenominas where PRENO_DiasFestivo >= PROGRU_FechaInicio and PRENO_DiasFestivo <=@fechaInicioQuincena)
		)Q5
		GROUP BY fecha

	) Q4
	WHERE fecha < @fechaInicioQuincena
	ORDER BY fecha ASC


RETURN;
END
GO

