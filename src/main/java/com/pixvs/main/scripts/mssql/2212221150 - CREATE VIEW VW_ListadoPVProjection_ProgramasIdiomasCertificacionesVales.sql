
-- =============================================
-- Author:		Ángeel Daniel Hernández Silva
-- Create date: 20/12/2022
-- Modify date:
-- Description:	La vista de los alumnos con vales certificaciones para mostrar en el PV
-- Version 1.0.0
-- =============================================

CREATE OR ALTER VIEW [dbo].[VW_ListadoPVProjection_ProgramasIdiomasCertificacionesVales] AS
	SELECT
	   PICV_ProgramaIdiomaCertificacionValeId AS id,
	   ALU_Codigo AS alumnoCodigo,
	   ALU_PrimerApellido AS alumnoPrimerApellido,
	   ALU_SegundoApellido AS alumnoSegundoApellido,
	   ALU_Nombre AS alumnoNombre,
	   PROG_Codigo + ' ' + CMM_Valor AS curso,
	   ART_NombreArticulo AS certificacion,
	   PICV_PorcentajeDescuento AS descuento,
	   PICV_CMM_EstatusId AS estatusId,
	   ALU_AlumnoId AS alumnoId,
	   ART_ArticuloId AS certificacionId
	FROM ProgramasIdiomasCertificacionesVales
	INNER JOIN AlumnosGrupos ON PICV_ALUG_AlumnoGrupoId = ALUG_AlumnoGrupoId
	INNER JOIN Alumnos ON ALU_AlumnoId = ALUG_ALU_AlumnoId
	INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = ALUG_PROGRU_GrupoId
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN ProgramasIdiomasCertificacionesDescuentos ON PICD_ProgramaIdiomaCertificacionDescuentoId = PICV_PICD_ProgramaIdiomaCertificacionDescuentoId
	INNER JOIN ProgramasIdiomasCertificacion ON PROGIC_ProgramaIdiomaCertificacionId = PICD_PROGIC_ProgramaIdiomaCertificacionId
	INNER JOIN Articulos ON ART_ArticuloId = PROGIC_ART_ArticuloId
GO