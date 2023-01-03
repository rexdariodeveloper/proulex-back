SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


-- =============================================
-- Author:		Rene Carrillo
-- Create date: 06/12/2022
-- Modify date: 21/12/2022
-- Description:	La vista de los alumnos con vales certificaciones
-- Version 1.0.2
-- =============================================

ALTER   VIEW [dbo].[VW_AlumnosGruposValesCertificaciones]
AS
	SELECT ag.ALUG_AlumnoGrupoId AS Id,
		a.ALU_Codigo AS Codigo,
		CONCAT(a.ALU_Nombre, ' ', a.ALU_PrimerApellido, ' ', a.ALU_SegundoApellido) AS Alumno,
		s.SUC_Nombre AS Sede,
		CONCAT(p.PROG_Codigo, ' ', idioma.CMM_Valor) AS Curso,
		aprobado.Aprobados AS Nivel,
		certificacion.NombreArticulo AS Certificacion,
		CASE WHEN picdd.PICDD_PorcentajeDescuento IS NOT NULL THEN CONCAT(picdd.PICDD_PorcentajeDescuento, '%') ELSE '0%' END AS Descuento,
		DATEADD(DAY, (SELECT TOP 1 picvi.PICVI_Vigencia FROM ProgramasIdiomasCertificacionesVigencias picvi), pg.PROGRU_FechaFin) AS Vigencia,
		CASE WHEN certificacion.CostoUltimo > 0 THEN ROUND(certificacion.CostoUltimo - ((certificacion.CostoUltimo * COALESCE(picdd.PICDD_PorcentajeDescuento, 0)) / 100), 2) ELSE certificacion.CostoUltimo END AS CostoFinal,
		CASE WHEN DATEADD(DAY, (SELECT TOP 1 picvi.PICVI_Vigencia FROM ProgramasIdiomasCertificacionesVigencias picvi), pg.PROGRU_FechaFin) > GETDATE() THEN 'No generado' ELSE 'Vencido' END AS Estatus,
		pg.PROGRU_PROGI_ProgramaIdiomaId AS CursoId,
		s.SUC_SucursalId AS SucursalId,
		pam.PAMOD_ModalidadId AS ModalidadId,
		pg.PROGRU_FechaInicio AS FechaInicio,
		pg.PROGRU_FechaFin AS FechaFin,
		certificacion.CostoUltimo AS CostoUltimo,
		picd.PICD_ProgramaIdiomaCertificacionDescuentoId AS ProgramaIdiomaCertificacionDescuentoId,
		CASE WHEN picdd.PICDD_PorcentajeDescuento IS NOT NULL THEN picdd.PICDD_PorcentajeDescuento ELSE 0 END AS PorcentajeDescuento
	FROM AlumnosGrupos ag
		INNER JOIN Alumnos a ON ag.ALUG_ALU_AlumnoId = a.ALU_AlumnoId
		INNER JOIN ProgramasGrupos pg ON ag.ALUG_PROGRU_GrupoId = pg.PROGRU_GrupoId
		INNER JOIN Sucursales s ON pg.PROGRU_SUC_SucursalId = s.SUC_SucursalId
		INNER JOIN ProgramasIdiomas pi ON pg.PROGRU_PROGI_ProgramaIdiomaId = pi.PROGI_ProgramaIdiomaId AND pg.PROGRU_Nivel = pi.PROGI_NumeroNiveles
		INNER JOIN Programas p ON pi.PROGI_PROG_ProgramaId = p.PROG_ProgramaId
		INNER JOIN ControlesMaestrosMultiples idioma ON pi.PROGI_CMM_Idioma = idioma.CMM_ControlId
		INNER JOIN VW_CertificacionesArticulos certificacion ON pi.PROGI_ProgramaIdiomaId = certificacion.ProgramaIdiomaId
		LEFT JOIN ProgramasIdiomasCertificacionesDescuentos picd ON certificacion.Id = picd.PICD_PROGIC_ProgramaIdiomaCertificacionId
		INNER JOIN (
			SELECT ag.ALUG_ALU_AlumnoId AS AlumnoId, COUNT(*) AS Aprobados
			FROM AlumnosGrupos ag
			WHERE ag.ALUG_CMM_EstatusId = 2000675
			GROUP BY ag.ALUG_ALU_AlumnoId
		) AS aprobado ON ag.ALUG_ALU_AlumnoId = aprobado.AlumnoId
		LEFT JOIN ProgramasIdiomasCertificacionesDescuentosDetalles picdd ON picd.PICD_ProgramaIdiomaCertificacionDescuentoId = picdd.PICDD_PICD_ProgramaIdiomaCertificacionDescuentoId AND picdd.PICDD_NumeroNivel = aprobado.Aprobados AND picdd.PICDD_Activo = 1
		INNER JOIN PAModalidadesHorarios pamh ON pg.PROGRU_PAMODH_PAModalidadHorarioId = pamh.PAMODH_PAModalidadHorarioId
		INNER JOIN PAModalidades pam ON pamh.PAMODH_PAMOD_ModalidadId = pam.PAMOD_ModalidadId
	WHERE ag.ALUG_CMM_EstatusId = 2000675 AND pg.PROGRU_CMM_EstatusId = 2000621 AND COALESCE(picdd.PICDD_PorcentajeDescuento, 0) != 0
	GROUP BY ag.ALUG_AlumnoGrupoId, a.ALU_Codigo, a.ALU_Nombre, a.ALU_PrimerApellido, a.ALU_SegundoApellido, s.SUC_Nombre, p.PROG_Codigo, idioma.CMM_Valor, aprobado.Aprobados, certificacion.NombreArticulo, picdd.PICDD_PorcentajeDescuento, pg.PROGRU_FechaFin, certificacion.CostoUltimo, pg.PROGRU_PROGI_ProgramaIdiomaId, s.SUC_SucursalId, pam.PAMOD_ModalidadId, pg.PROGRU_FechaInicio, picd.PICD_ProgramaIdiomaCertificacionDescuentoId
GO