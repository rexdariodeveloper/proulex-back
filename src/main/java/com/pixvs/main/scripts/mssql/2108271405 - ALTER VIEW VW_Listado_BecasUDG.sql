/**
* Created by Angel Daniel Hernández Silva on 24/08/2021.
* Object:  ALTER VIEW [dbo].[VW_Listado_BecasUDG]
*/

CREATE OR ALTER VIEW [dbo].[VW_Listado_BecasUDG] AS

	SELECT
		BECU_BecaId AS id,
		ALU_Codigo AS codigoProulex,
		BECU_CodigoBeca AS codigoBeca,
		BECU_CodigoEmpleado AS codigoEmpleado,
		BECU_Nombre AS nombre,
		BECU_PrimerApellido AS primerApellido,
		BECU_SegundoApellido AS segundoApellido,
		BECU_Descuento AS descuento,
		CONCAT(PROG_Codigo,' ',CMM_Valor) AS curso,
		BECU_Nivel AS nivel,
		PAMOD_Nombre AS modalidad,
		ALU_AlumnoId AS alumnoId,

		-- Datos de consulta, no aparecen en la proyección
		PROG_ProgramaId AS programaId,
		PROGI_CMM_Idioma AS idiomaId,
		PAMOD_ModalidadId AS modalidadId
	FROM BecasUDG
	LEFT JOIN Alumnos
		ON ALU_CodigoUDG = BECU_CodigoEmpleado
		AND ALU_Nombre = BECU_Nombre
		AND ALU_PrimerApellido = ALU_PrimerApellido
		AND (
			(ALU_SegundoApellido IS NULL AND ALU_SegundoApellido IS NULL)
			OR ALU_SegundoApellido = ALU_SegundoApellido
		)
		AND ALU_Activo = 1
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = BECU_PROGI_ProgramaIdiomaId
	INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = BECU_PAMOD_ModalidadId
	WHERE BECU_CMM_EstatusId = 2000570 -- Pendiente por aplicar

GO