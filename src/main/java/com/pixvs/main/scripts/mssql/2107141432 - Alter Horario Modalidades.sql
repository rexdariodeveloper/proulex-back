ALTER TABLE PAModalidadesHorarios
ADD PAMODH_Codigo varchar(8) null
GO

UPDATE PAModalidadesHorarios
SET PAMODH_Codigo='01'
WHERE PAMODH_Horario='7:00 - 9:00'
GO
UPDATE PAModalidadesHorarios
SET PAMODH_Codigo='02'
WHERE PAMODH_Horario='9:00 - 11:00'
GO
UPDATE PAModalidadesHorarios
SET PAMODH_Codigo='03'
WHERE PAMODH_Horario='11:00 - 13:00'
GO
UPDATE PAModalidadesHorarios
SET PAMODH_Codigo='04'
WHERE PAMODH_Horario='13:00 - 15:00'
GO
UPDATE PAModalidadesHorarios
SET PAMODH_Codigo='05'
WHERE PAMODH_Horario='15:00 - 17:00'
GO
UPDATE PAModalidadesHorarios
SET PAMODH_Codigo='06'
WHERE PAMODH_Horario='17:00 - 19:00'
GO
UPDATE PAModalidadesHorarios
SET PAMODH_Codigo='07'
WHERE PAMODH_Horario='19:00 - 21:00'
GO
UPDATE PAModalidadesHorarios
SET PAMODH_Codigo='08'
WHERE PAMODH_Horario='9:00 - 14:00'
GO
UPDATE PAModalidadesHorarios
SET PAMODH_Codigo='09'
WHERE PAMODH_Horario='9:00 - 13:00'
GO
UPDATE PAModalidadesHorarios
SET PAMODH_Codigo='10'
WHERE PAMODH_Horario='15:00 - 20:00'
GO
UPDATE PAModalidadesHorarios
SET PAMODH_Codigo='11'
WHERE PAMODH_Horario='17:00 - 21:00'
GO
