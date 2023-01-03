SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getModalidadesHorarios] (@modalidadId INT)
RETURNS @tablaTMP table(
	modalidadId int,
	modalidad varchar(50),
	horarioId int,
	dia int,
	horaInicio time(7),
	horaFin time(7),
	horasPorDia decimal(10,1)
)

AS BEGIN
	INSERT @tablaTMP
	SELECT * FROM(
	SELECT PAMOD_ModalidadId as modalidadId, PAMOD_Nombre as modalidad ,PAMODH_PAModalidadHorarioId as horarioId ,CASE WHEN PAMOD_Lunes = 1 THEN 0 ELSE NULL END as dia, PAMODH_HoraInicio as hiraInicio, PAMODH_HoraFin as horaFin, DATEDIFF(MINUTE, PAMODH_HoraInicio, PAMODH_HoraFin) / 60.0 as horasPorDia
	FROM PAModalidades
	INNER JOIN PAModalidadesHorarios on PAMODH_PAMOD_ModalidadId = PAMOD_ModalidadId
	WHERE PAMOD_ModalidadId = @modalidadId
	UNION ALL
	SELECT PAMOD_ModalidadId as modalidadId, PAMOD_Nombre as modalidad , PAMODH_PAModalidadHorarioId as horarioId ,CASE WHEN PAMOD_Martes = 1 THEN 1 ELSE NULL END as dia, PAMODH_HoraInicio as hiraInicio, PAMODH_HoraFin as horaFin, DATEDIFF(MINUTE, PAMODH_HoraInicio, PAMODH_HoraFin) / 60.0 as horasPorDia
	FROM PAModalidades
	INNER JOIN PAModalidadesHorarios on PAMODH_PAMOD_ModalidadId = PAMOD_ModalidadId
	WHERE PAMOD_ModalidadId = @modalidadId
	UNION ALL
	SELECT PAMOD_ModalidadId as modalidadId, PAMOD_Nombre as modalidad , PAMODH_PAModalidadHorarioId as horarioId ,CASE WHEN PAMOD_Miercoles = 1 THEN 2 ELSE NULL END as dia, PAMODH_HoraInicio as hiraInicio, PAMODH_HoraFin as horaFin, DATEDIFF(MINUTE, PAMODH_HoraInicio, PAMODH_HoraFin) / 60.0 as horasPorDia
	FROM PAModalidades
	INNER JOIN PAModalidadesHorarios on PAMODH_PAMOD_ModalidadId = PAMOD_ModalidadId
	WHERE PAMOD_ModalidadId = @modalidadId
	UNION ALL
	SELECT PAMOD_ModalidadId as modalidadId, PAMOD_Nombre as modalidad , PAMODH_PAModalidadHorarioId as horarioId ,CASE WHEN PAMOD_Jueves = 1 THEN 3 ELSE NULL END as dia, PAMODH_HoraInicio as hiraInicio, PAMODH_HoraFin as horaFin, DATEDIFF(MINUTE, PAMODH_HoraInicio, PAMODH_HoraFin) / 60.0 as horasPorDia
	FROM PAModalidades
	INNER JOIN PAModalidadesHorarios on PAMODH_PAMOD_ModalidadId = PAMOD_ModalidadId
	WHERE PAMOD_ModalidadId = @modalidadId
	UNION ALL
	SELECT PAMOD_ModalidadId as modalidadId, PAMOD_Nombre as modalidad , PAMODH_PAModalidadHorarioId as horarioId ,CASE WHEN PAMOD_Viernes = 1 THEN 4 ELSE NULL END as dia, PAMODH_HoraInicio as hiraInicio, PAMODH_HoraFin as horaFin, DATEDIFF(MINUTE, PAMODH_HoraInicio, PAMODH_HoraFin) / 60.0 as horasPorDia
	FROM PAModalidades
	INNER JOIN PAModalidadesHorarios on PAMODH_PAMOD_ModalidadId = PAMOD_ModalidadId
	WHERE PAMOD_ModalidadId = @modalidadId
	UNION ALL
	SELECT PAMOD_ModalidadId as modalidadId, PAMOD_Nombre as modalidad , PAMODH_PAModalidadHorarioId as horarioId ,CASE WHEN PAMOD_Sabado = 1 THEN 5 ELSE NULL END as dia, PAMODH_HoraInicio as hiraInicio, PAMODH_HoraFin as horaFin, DATEDIFF(MINUTE, PAMODH_HoraInicio, PAMODH_HoraFin) / 60.0 as horasPorDia
	FROM PAModalidades
	INNER JOIN PAModalidadesHorarios on PAMODH_PAMOD_ModalidadId = PAMOD_ModalidadId
	WHERE PAMOD_ModalidadId = @modalidadId
	UNION ALL
	SELECT PAMOD_ModalidadId as modalidadId, PAMOD_Nombre as modalidad , PAMODH_PAModalidadHorarioId as horarioId ,CASE WHEN PAMOD_Domingo = 1 THEN 6 ELSE NULL END as dia, PAMODH_HoraInicio as hiraInicio, PAMODH_HoraFin as horaFin, DATEDIFF(MINUTE, PAMODH_HoraInicio, PAMODH_HoraFin) / 60.0 as horasPorDia
	FROM PAModalidades
	INNER JOIN PAModalidadesHorarios on PAMODH_PAMOD_ModalidadId = PAMOD_ModalidadId
	WHERE PAMOD_ModalidadId = @modalidadId
	) T1 WHERE T1.dia IS NOT NULL
	RETURN;
END