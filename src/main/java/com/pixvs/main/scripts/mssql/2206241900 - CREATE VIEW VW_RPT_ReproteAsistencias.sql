SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Rene Carrillo
-- Create date: 23/06/2022
-- Modify date:
-- Description:	Vista obtener los datos para el reporte de Asistencias
-- Version 1.0.0
-- =============================================

CREATE VIEW VW_RPT_ReporteAsistencias AS
	SELECT pg.PROGRU_GrupoId AS GrupoId,
		UPPER(p.PROG_Nombre) AS Programa,
		'PROULEX ' + UPPER(SUC_Nombre) AS Institucion,
		UPPER(CONCAT(p.PROG_Codigo, ' ', CMM_Valor)) AS Curso,
		CONCAT_WS(' - ', CAST(pamh.PAMODH_HoraInicio AS NVARCHAR(5)), CAST(pamh.PAMODH_HoraFin AS NVARCHAR(5))) AS Horario,
		UPPER(CONCAT_WS(' ', e.EMP_Nombre, e.EMP_PrimerApellido, e.EMP_SegundoApellido)) AS Profesor,
		pg.PROGRU_SUC_SucursalId AS SedeId,
		pg.PROGRU_PAMOD_ModalidadId AS ModalidadId,
		pg.PROGRU_FechaInicio AS FechaInicio,
		p.PROG_ProgramaId AS ProgramaId,
		ROW_NUMBER() OVER (ORDER BY p.PROG_Codigo, s.SUC_Nombre, CONCAT(p.PROG_Codigo, ' ', cmm.CMM_Valor), CONCAT_WS(' - ', CAST(pamh.PAMODH_HoraInicio AS NVARCHAR(5)), CAST(pamh.PAMODH_HoraFin AS NVARCHAR(5))), UPPER(CONCAT_WS(' ', e.EMP_Nombre, e.EMP_PrimerApellido, e.EMP_SegundoApellido))) AS Orden
	FROM ProgramasGrupos pg
		INNER JOIN ProgramasIdiomas pi ON PROGRU_PROGI_ProgramaIdiomaId = pi.PROGI_ProgramaIdiomaId AND pi.PROGI_Activo = 1
		INNER JOIN Programas p ON pi.PROGI_PROG_ProgramaId = p.PROG_ProgramaId AND p.PROG_PCP = 1 AND p.PROG_Activo = 1
		INNER JOIN Sucursales s ON pg.PROGRU_SUC_SucursalId = s.SUC_SucursalId
		INNER JOIN PAModalidadesHorarios pamh ON pg.PROGRU_PAMODH_PAModalidadHorarioId = pamh.PAMODH_PAModalidadHorarioId
		LEFT JOIN Empleados e ON pg.PROGRU_EMP_EmpleadoId = e.EMP_EmpleadoId
		INNER JOIN ControlesMaestrosMultiples cmm ON pi.PROGI_CMM_Idioma = cmm.CMM_ControlId
	WHERE pg.PROGRU_CMM_EstatusId != 2000622