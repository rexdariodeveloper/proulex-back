SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_GRUPOS_HORARIOS]
AS
(
	select * from
	(
		select 
			PROGRU_GrupoId grupoId,
			PROGRU_Codigo grupo,
			modalidadId,
			horarioId,
			nombre modalidad,
			dia,
			horaInicio,
			horaFin,
			horas
		from
		(
		select 
			PAMOD_ModalidadId modalidadId,
			PAMODH_PAModalidadHorarioId horarioId,
			PAMOD_Codigo codigo, 
			PAMOD_Nombre nombre, 
			CASE Dia WHEN 'PAMOD_Domingo' THEN 1
					 WHEN 'PAMOD_Lunes' THEN 2
					 WHEN 'PAMOD_Martes' THEN 3
					 WHEN 'PAMOD_Miercoles' THEN 4
					 WHEN 'PAMOD_Jueves' THEN 5
					 WHEN 'PAMOD_Viernes' THEN 6
					 WHEN 'PAMOD_Sabado' THEN 7
			END dia,
			PAMODH_HoraInicio horaInicio,
			PAMODH_HoraFin horaFin,
			DATEDIFF(MINUTE, PAMODH_HoraInicio, PAMODH_HoraFin) / 60.0 horas
		from 
		(select 
			PAMOD_ModalidadId, PAMOD_Codigo, PAMOD_Nombre, PAMOD_Lunes, PAMOD_Martes, PAMOD_Miercoles, PAMOD_Jueves, PAMOD_Viernes, PAMOD_Sabado, PAMOD_Domingo, PAMODH_HoraInicio, PAMODH_HoraFin, PAMODH_PAModalidadHorarioId
		from 
			PAModalidades 
			inner join PAModalidadesHorarios on PAMOD_ModalidadId = PAMODH_PAMOD_ModalidadId
			where PAMOD_Activo = 1
		) p
		UNPIVOT
			(Activo FOR Dia IN (PAMOD_Lunes, PAMOD_Martes, PAMOD_Miercoles, PAMOD_Jueves, PAMOD_Viernes, PAMOD_Sabado, PAMOD_Domingo))up
		where Activo = 1
		) t inner join ProgramasGrupos on PROGRU_PAMODH_PAModalidadHorarioId = horarioId
	) modalidadesFijas
	UNION
	(
	select 
		PROGRU_GrupoId grupoId,
		PROGRU_Codigo grupo,
		PROGRU_PAMOD_ModalidadId modalidadId,
		PROGRU_PAMODH_PAModalidadHorarioId horarioId,
		'Personalizada' modalidad,
		CASE PGINCH_Dia WHEN 'Domingo' THEN 1
				 WHEN 'Lunes' THEN 2
				 WHEN 'Martes' THEN 3
				 WHEN 'Miercoles' THEN 4
				 WHEN 'Jueves' THEN 5
				 WHEN 'Viernes' THEN 6
				 WHEN 'Sabado' THEN 7
		END dia,
		PGINCH_HoraInicio horaInicio,
		PGINCH_HoraFin horaFin,
		DATEDIFF(MINUTE, PGINCH_HoraInicio, PGINCH_HoraFin) / 60.0 horas
	from ProgramasGruposIncompanyHorarios
	inner join ProgramasGrupos on PGINCH_PROGRU_GrupoId = PROGRU_GrupoId
	where PGINCH_HoraInicio IS NOT NULL
	)
)