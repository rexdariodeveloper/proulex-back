SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


-- =============================================
-- Author:		Rene Carrillo
-- Create date: 25/07/2022
-- Modify date: 15/08/2022
-- Description:	la vista para obtener el listado de plantilla de grupos
-- Version 1.0.2
-- =============================================

ALTER VIEW [dbo].[VW_Listado_PlantillaGrupos]
AS
	SELECT pg.PROGRU_GrupoId AS Id,
    pg.PROGRU_Codigo AS CodigoGrupo,
    CONCAT(p.PROG_Codigo, ' ', idioma.CMM_Valor, ' ', pam.PAMOD_Nombre, ' Nivel ', FORMAT(pg.PROGRU_Nivel, '00'), ' Grupo ', FORMAT(pg.PROGRU_Grupo, '00')) AS NombreGrupo,
    sp.SP_Nombre AS Plantel,
    pg.PROGRU_FechaInicio AS FechaInicio,
    pg.PROGRU_FechaFin AS FechaFin,
    pg.PROGRU_Nivel AS Nivel,
    pamh.PAMODH_Horario AS Horario,
    COALESCE(pg.PROGRU_Cupo, 0) AS Cupo,
    COALESCE(inscrito.Inscrito, 0) AS Inscrito,
    CONCAT(e.EMP_Nombre, ' ', e.EMP_PrimerApellido, ' ', e.EMP_SegundoApellido) AS NombreProfesor,
    pg.PROGRU_PROGI_ProgramaIdiomaId AS CursoId,
    pg.PROGRU_SP_SucursalPlantelId AS PlantelId,
    s.SUC_SucursalId AS SucursalId,
    pam.PAMOD_ModalidadId AS ModalidadId
  FROM  ProgramasGrupos pg
    INNER JOIN ProgramasIdiomas pi ON pg.PROGRU_PROGI_ProgramaIdiomaId = pi.PROGI_ProgramaIdiomaId
    INNER JOIN ControlesMaestrosMultiples idioma ON pi.PROGI_CMM_Idioma = idioma.CMM_ControlId
    INNER JOIN ControlesMaestrosMultiples tipoGrupo ON pg.PROGRU_CMM_TipoGrupoId = tipoGrupo.CMM_ControlId
    INNER JOIN PAModalidadesHorarios pamh ON pg.PROGRU_PAMODH_PAModalidadHorarioId = pamh.PAMODH_PAModalidadHorarioId
    INNER JOIN PAModalidades pam ON pamh.PAMODH_PAMOD_ModalidadId = pam.PAMOD_ModalidadId
    INNER JOIN Sucursales s ON pg.PROGRU_SUC_SucursalId = s.SUC_SucursalId
    LEFT OUTER JOIN Empleados e ON pg.PROGRU_EMP_EmpleadoId = e.EMP_EmpleadoId
    LEFT OUTER JOIN SucursalesPlanteles sp ON pg.PROGRU_SP_SucursalPlantelId = sp.SP_SucursalPlantelId
    INNER JOIN Programas p ON pi.PROGI_PROG_ProgramaId = p.PROG_ProgramaId
    LEFT JOIN (SELECT COUNT(*) AS Inscrito, ag.ALUG_PROGRU_GrupoId AS GrupoId FROM AlumnosGrupos ag GROUP BY ag.ALUG_PROGRU_GrupoId) AS inscrito ON pg.PROGRU_GrupoId = inscrito.GrupoId
  WHERE pg.PROGRU_PGINCG_ProgramaIncompanyId IS NULL
    --AND pg.PROGRU_SP_SucursalPlantelId IS NOT NULL
    AND pg.PROGRU_CMM_EstatusId <> 2000622
GO