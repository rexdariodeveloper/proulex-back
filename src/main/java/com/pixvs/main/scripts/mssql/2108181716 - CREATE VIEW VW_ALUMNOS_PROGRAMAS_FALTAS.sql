CREATE OR ALTER VIEW [dbo].[VW_ALUMNOS_GRUPOS_FALTAS]
AS
(
	SELECT
		alumnoId,
		PROGRU_GrupoId grupoId,
		COALESCE(CAST(minutos AS FLOAT) / 60, 0) + (COALESCE(dias, 0) * PAMOD_HorasPorDia) horas,
		(COALESCE(diasTotal,0) * PAMOD_HorasPorDia * (CAST(PROGRU_FaltasPermitidas AS FLOAT)/ 100)) limite,
		(COALESCE(diasTotal,0) * PAMOD_HorasPorDia) horasTotal,
		CASE 
			WHEN 
				COALESCE(CAST(minutos AS FLOAT) / 60, 0) + (COALESCE(dias, 0) * PAMOD_HorasPorDia) >
				(COALESCE(diasTotal,0) * PAMOD_HorasPorDia * (CAST(PROGRU_FaltasPermitidas AS FLOAT)/ 100))
			THEN
				CAST(1 AS BIT)
			ELSE
				CAST(0 AS BIT)
		END limiteFaltasExcedido
	FROM
		ProgramasGrupos
		INNER JOIN PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
		LEFT JOIN (
		SELECT
			AA_ALU_AlumnoId alumnoId, 
			AA_PROGRU_GrupoId grupoId, 
			SUM(COALESCE(AA_MinutosRetardo,0)) minutos,
			SUM(CASE WHEN AA_CMM_TipoAsistenciaId = 2000551 THEN 1 ELSE 0 END) dias,
			COUNT(*) diasTotal
		FROM 
			AlumnosAsistencias
		GROUP BY
			AA_ALU_AlumnoId, AA_PROGRU_GrupoId
	)aa ON PROGRU_GrupoId = aa.grupoId
)
GO