ALTER TABLE PAModalidadesHorarios
ADD PAMODH_HoraInicio time null;

ALTER TABLE PAModalidadesHorarios
ADD PAMODH_HoraFin time null;

UPDATE PAModalidadesHorarios
SET PAMODH_HoraInicio = CAST(SUBSTRING(PAMODH_Horario, (PATINDEX('%-%',[PAMODH_Horario])+1),10) AS TIME),
	PAMODH_HoraFin = CAST(LEFT(PAMODH_Horario, CHARINDEX('-', PAMODH_Horario, CHARINDEX('-', PAMODH_Horario)) - 1) AS TIME)
FROM PAModalidadesHorarios;