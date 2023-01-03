SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


-- =============================================
-- Author:		Rene Carrillo
-- Create date: 06/12/2022
-- Modify date: 21/12/2022
-- Description:	La vista de los alumnos con vales certificaciones
-- Version 1.0.1
-- =============================================

ALTER   VIEW [dbo].[VW_ValesCertificaciones]
AS
	SELECT picv.PICV_ALUG_AlumnoGrupoId AS Id,
		a.ALU_Codigo AS Codigo,
		CONCAT(a.ALU_Nombre, ' ', a.ALU_PrimerApellido, ' ', a.ALU_SegundoApellido) AS Alumno,
		s.SUC_Nombre AS Sede,
		CONCAT(p.PROG_Codigo, ' ', idioma.CMM_Valor) AS Curso,
		aprobado.Aprobados AS Nivel,
		certificacion.NombreArticulo AS Certificacion,
		CONCAT(picv.PICV_PorcentajeDescuento, '%') AS Descuento,
		picv.PICV_FechaVigenciaFin AS Vigencia,
		picv.PICV_CostoFinal AS CostoFinal,
		estatus.CMM_Valor AS Estatus,
		pg.PROGRU_PROGI_ProgramaIdiomaId AS CursoId,
		s.SUC_SucursalId AS SucursalId,
		pam.PAMOD_ModalidadId AS ModalidadId,
		pg.PROGRU_FechaInicio AS FechaInicio
	FROM ProgramasIdiomasCertificacionesVales picv
		INNER JOIN AlumnosGrupos ag ON picv.PICV_ALUG_AlumnoGrupoId = ag.ALUG_AlumnoGrupoId
		INNER JOIN Alumnos a ON ag.ALUG_ALU_AlumnoId = a.ALU_AlumnoId
		INNER JOIN ProgramasGrupos pg ON ag.ALUG_PROGRU_GrupoId = pg.PROGRU_GrupoId
		INNER JOIN Sucursales s ON pg.PROGRU_SUC_SucursalId = s.SUC_SucursalId
		INNER JOIN ProgramasIdiomas pi ON pg.PROGRU_PROGI_ProgramaIdiomaId = pi.PROGI_ProgramaIdiomaId
		INNER JOIN Programas p ON pi.PROGI_PROG_ProgramaId = p.PROG_ProgramaId
		INNER JOIN ControlesMaestrosMultiples idioma ON pi.PROGI_CMM_Idioma = idioma.CMM_ControlId
		INNER JOIN VW_CertificacionesArticulos certificacion ON pi.PROGI_ProgramaIdiomaId = certificacion.ProgramaIdiomaId
		LEFT JOIN ProgramasIdiomasCertificacionesDescuentos picd ON picv.PICV_PICD_ProgramaIdiomaCertificacionDescuentoId = picd.PICD_PROGIC_ProgramaIdiomaCertificacionId
		INNER JOIN ControlesMaestrosMultiples estatus ON picv.PICV_CMM_EstatusId = estatus.CMM_ControlId
		INNER JOIN PAModalidadesHorarios pamh ON pg.PROGRU_PAMODH_PAModalidadHorarioId = pamh.PAMODH_PAModalidadHorarioId
		INNER JOIN PAModalidades pam ON pamh.PAMODH_PAMOD_ModalidadId = pam.PAMOD_ModalidadId
		INNER JOIN (
			SELECT ag.ALUG_ALU_AlumnoId AS AlumnoId, COUNT(*) AS Aprobados
			FROM AlumnosGrupos ag
			WHERE ag.ALUG_CMM_EstatusId = 2000675
			GROUP BY ag.ALUG_ALU_AlumnoId
		) AS aprobado ON ag.ALUG_ALU_AlumnoId = aprobado.AlumnoId
GO