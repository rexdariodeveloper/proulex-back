UPDATE PAModalidadesHorarios
SET PAMODH_Horario = CONCAT(CONVERT(VARCHAR(5),PAMODH_HoraInicio,108),' ','-',' ',CONVERT(VARCHAR(5),PAMODH_HoraFin,108))
WHERE PAMODH_PAMOD_ModalidadId IS NOT NULL
GO
