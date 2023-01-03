CREATE OR ALTER VIEW [dbo].[VW_LISTADO_SOLICITUDES_BECAS] AS
	SELECT 
	BECU_BecaId AS id,
	BECS_Codigo AS solicitudBeca,
	CMM_Referencia AS tipoBeca,
	BECU_CodigoBeca AS codigoBeca,
	BECU_CodigoAlumno AS codigoAlumno,
	BECU_PrimerApellido AS primerApellido,
	BECU_SegundoApellido AS segundoApellido,
	BECU_Nombre AS nombre,
	(Select CONCAT(PROG_Codigo,' ',CMM_Valor) from ProgramasIdiomas 
	INNER JOIN Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples on CMM_ControlId = PROGI_CMM_Idioma
	WHERE BECU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId) AS curso,
	PAMOD_Nombre AS modalidad,
	BECU_Nivel AS nivel,
	BECU_Descuento*100 as descuento,
	(SELECT CMM_Valor FROM ControlesMaestrosMultiples WHERE CMM_ControlId = BECU_CMM_EstatusId) AS estatus
	from BecasUDG
	INNER JOIN BecasSolicitudes on BECU_BECS_BecaSolicitudId = BECS_BecaSolicitudId
	INNER JOIN ControlesMaestrosMultiples on BECU_CMM_TipoId = CMM_ControlId
	INNER JOIN PAModalidades on BECU_PAMOD_ModalidadId = PAMOD_ModalidadId
GO