/**
* Created by Angel Daniel Hernández Silva on 02/05/2022.
*/

UPDATE Inscripciones
	SET INS_BECU_BecaId = BECU_BecaId
FROM Inscripciones
INNER JOIN Alumnos ON ALU_AlumnoId = INS_ALU_AlumnoId
INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
INNER JOIN BecasUDG
	ON BECU_CMM_EstatusId = 2000571
	AND (
		(
			BECU_CMM_TipoId = 2000582
			AND BECU_CodigoAlumno = ALU_Codigo
		)
		OR (
			BECU_CMM_TipoId IN (2000580,2000581)
			AND COALESCE(BECU_CodigoEmpleado,'') = COALESCE(ALU_CodigoUDG,'')
			AND BECU_Nombre = ALU_Nombre
			AND BECU_PrimerApellido = ALU_PrimerApellido
			AND (
				(BECU_SegundoApellido IS NULL AND ALU_SegundoApellido IS NULL)
				OR BECU_SegundoApellido = ALU_SegundoApellido
			)
		)
	) AND BECU_PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	AND BECU_Nivel = PROGRU_Nivel
	AND BECU_PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
WHERE INS_BECU_BecaId IS NULL
GO

UPDATE InscripcionesSinGrupo
	SET INSSG_BECU_BecaId = BECU_BecaId
FROM InscripcionesSinGrupo
INNER JOIN Alumnos ON ALU_AlumnoId = INSSG_ALU_AlumnoId AND ALU_Activo = 1
INNER JOIN ProgramasIdiomas
	ON PROGI_CMM_Idioma = INSSG_CMM_IdiomaId
	AND PROGI_PROG_ProgramaId = INSSG_PROG_ProgramaId
	AND PROGI_Activo = 1
INNER JOIN BecasUDG
	ON BECU_CMM_EstatusId = 2000571
	AND (
		(
			BECU_CMM_TipoId = 2000582
			AND BECU_CodigoAlumno = ALU_Codigo
		)
		OR (
			BECU_CMM_TipoId IN (2000580,2000581)
			AND COALESCE(BECU_CodigoEmpleado,'') = COALESCE(ALU_CodigoUDG,'')
			AND BECU_Nombre = ALU_Nombre
			AND BECU_PrimerApellido = ALU_PrimerApellido
			AND (
				(BECU_SegundoApellido IS NULL AND ALU_SegundoApellido IS NULL)
				OR BECU_SegundoApellido = ALU_SegundoApellido
			)
		)
	) AND BECU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
	AND BECU_Nivel = INSSG_Nivel
	AND BECU_PAMOD_ModalidadId = INSSG_PAMOD_ModalidadId
WHERE INSSG_BECU_BecaId IS NULL
GO