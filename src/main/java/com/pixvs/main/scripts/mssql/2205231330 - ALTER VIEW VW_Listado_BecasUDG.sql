/**
* Created by Angel Daniel Hernández Silva on 23/05/2022.
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
		CONCAT(PROG_Codigo,' ',Idioma.CMM_Valor) AS curso,
		BECU_Nivel AS nivel,
		PAMOD_Nombre AS modalidad,
		ALU_AlumnoId AS alumnoId,
		COALESCE(ENBE_Codigo,'') AS entidadBeca,

		-- Datos de consulta, no aparecen en la proyección
		PROG_ProgramaId AS programaId,
		PROGI_CMM_Idioma AS idiomaId,
		PAMOD_ModalidadId AS modalidadId,
		BECU_CMM_TipoId AS tipoId
	FROM BecasUDG
	LEFT JOIN Alumnos
		ON (
			(
				BECU_CMM_TipoId = 2000582
				AND ALU_Codigo = BECU_CodigoAlumno
			)
			OR (
				BECU_CMM_TipoId IN (2000580,2000581)
				AND (
					COALESCE(ALU_CodigoUDG,'') = COALESCE(BECU_CodigoEmpleado,'')
					OR ALU_CodigoUDGAlterno = BECU_CodigoEmpleado
				)
				AND ALU_Nombre = BECU_Nombre
				AND ALU_PrimerApellido = BECU_PrimerApellido
				AND (
					(ALU_SegundoApellido IS NULL AND BECU_SegundoApellido IS NULL)
					OR ALU_SegundoApellido = BECU_SegundoApellido
				)
			)
		) AND ALU_Activo = 1
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = BECU_PROGI_ProgramaIdiomaId
	INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples AS Idioma ON Idioma.CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = BECU_PAMOD_ModalidadId
	LEFT JOIN EntidadesBecas ON ENBE_EntidadBecaId = BECU_ENBE_EntidadBecaId
	WHERE BECU_CMM_EstatusId = 2000570 -- Pendiente por aplicar

GO