SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


-- =============================================
-- Author:		Rene Carrillo
-- Create date: 06/12/2022
-- Modify date: 26/12/2022
-- Description:	La vista de los alumnos con vales certificaciones
-- Version 1.0.3
-- =============================================

ALTER   VIEW [dbo].[VW_AlumnosGruposValesCertificaciones]
AS
	SELECT DISTINCT ag.ALUG_AlumnoGrupoId AS Id,
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
	FROM Inscripciones ins
		INNER JOIN ProgramasGrupos pg ON INS_PROGRU_GrupoId = PROGRU_GrupoId
		INNER JOIN Alumnos a ON INS_ALU_AlumnoId = ALU_AlumnoId
		INNER JOIN AlumnosGrupos ag ON ins.INS_InscripcionId = ag.ALUG_INS_InscripcionId
		INNER JOIN ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		INNER JOIN ControlesMaestrosMultiples idiomas ON PROGI_CMM_Idioma = idiomas.CMM_ControlId
		INNER JOIN PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
		INNER JOIN PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
		INNER JOIN OrdenesVentaDetalles ON INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
		INNER JOIN OrdenesVenta ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
		INNER JOIN Sucursales s ON OV_SUC_SucursalId = s.SUC_SucursalId
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
		INNER JOIN PAModalidades pam ON pg.PROGRU_PAMOD_ModalidadId = pam.PAMOD_ModalidadId
	WHERE INS_CMM_EstatusId != 2000512 AND ag.ALUG_CMM_EstatusId = 2000675 AND pg.PROGRU_CMM_EstatusId = 2000621 AND COALESCE(picdd.PICDD_PorcentajeDescuento, 0) != 0
GO