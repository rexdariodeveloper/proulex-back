SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Rene Carrillo
-- Create date: 08/12/2022
-- Modify date:
-- Description:	La vista de el reporte de vale calificacion
-- Version 1.0.0
-- =============================================

CREATE OR ALTER VIEW [dbo].[VW_RPT_ReporteValeCalificacion]
AS
	SELECT picv.PICV_ALUG_AlumnoGrupoId AS AlumnoGrupoId,
		a.ALU_Codigo AS Codigo,
		CONCAT(a.ALU_Nombre, ' ', a.ALU_PrimerApellido, ' ', a.ALU_SegundoApellido) AS Alumno,
		s.SUC_Nombre AS Sede,
		CONCAT(p.PROG_Codigo, ' ', idioma.CMM_Valor) AS Curso,
		pg.PROGRU_Nivel AS Nivel,
		certificacion.NombreArticulo AS Certificacion,
		CONCAT(picv.PICV_PorcentajeDescuento, '%') AS Descuento,
		CONCAT(picv.PICV_FechaVigenciaInicio, ' al ', picv.PICV_FechaVigenciaFin) AS Vigencia,
		picv.PICV_CostoFinal AS CostoFinal,
		estatus.CMM_Valor AS Estatus
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
GO