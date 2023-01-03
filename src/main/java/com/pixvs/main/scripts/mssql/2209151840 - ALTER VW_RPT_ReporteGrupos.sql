SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ==================================================
-- Author:		-------
-- Create date: 09/03/2022
-- Modified by: Javier Elías
-- Modify date: 15/09/2022
-- Description:	la vista para obtener el reporte de grupos
-- ==================================================
ALTER VIEW [dbo].[VW_RPT_ReporteGrupos]
AS
     SELECT PROGRU_GrupoId AS id,
            PROGRU_Codigo AS codigoGrupo,
            CONCAT_WS(' ', PROG_Codigo, idioma.CMM_Valor, PAMOD_Nombre, FORMAT(PROGRU_Nivel, 'Nivel 00'), FORMAT(PROGRU_Grupo, 'Grupo 00')) AS grupoNombre,
            COALESCE(SP_Nombre, '#N/A') AS plantel,
            PROGRU_FechaInicio AS fechaInicio,
            PROGRU_FechaFin AS fechaFin,
            FORMAT(PROGRU_Nivel, '00') AS nivel,
            PAMODH_Horario AS horario,
            PROGRU_Cupo AS cupo,
            COALESCE(t.inscritos, 0) AS totalInscritos,
            CONCAT_WS(' ', EMP_Nombre, EMP_PrimerApellido, EMP_SegundoApellido) AS profesor,
            PROGRU_SUC_SucursalId sedeId,
            PROGRU_PAC_ProgramacionAcademicaComercialId paId,
            PROGRU_PACIC_CicloId cicloId,
            PROGRU_PAMOD_ModalidadId modalidadId,
			PROG_ProgramaId AS programaId,
			PROG_PCP AS pcp,
			tipo.CMM_Valor AS tipo
     FROM ProgramasGrupos
          INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
          INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
          INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
          INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
          INNER JOIN ControlesMaestrosMultiples AS idioma ON PROGI_CMM_Idioma = idioma.CMM_ControlId
          LEFT JOIN SucursalesPlanteles ON SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId
          LEFT JOIN Empleados ON EMP_EmpleadoId = PROGRU_EMP_EmpleadoId
		  INNER JOIN ControlesMaestrosMultiples AS tipo ON PROGRU_CMM_TipoGrupoId = tipo.CMM_ControlId
          LEFT JOIN
		  (
				SELECT INS_PROGRU_GrupoId grupoId,
						COUNT(INS_InscripcionId) inscritos
				FROM Inscripciones
				WHERE INS_CMM_EstatusId <> 2000512
				GROUP BY INS_PROGRU_GrupoId
		  ) t ON t.grupoId = PROGRU_GrupoId
     WHERE PROGRU_CMM_EstatusId IN (2000620, 2000621)
GO