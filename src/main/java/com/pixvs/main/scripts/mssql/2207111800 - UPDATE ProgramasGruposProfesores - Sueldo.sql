/**
 * Created by Angel Daniel Hernández Silva on 11/07/2022.
 */

UPDATE ProgramasGruposProfesores SET PROGRUP_Sueldo = PROGRU_SueldoProfesor
FROM ProgramasGruposProfesores
INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = PROGRUP_PROGRU_GrupoId AND PROGRU_EMP_EmpleadoId = PROGRUP_EMP_EmpleadoId
WHERE PROGRUP_Activo = 1 AND PROGRU_SueldoProfesor != PROGRUP_Sueldo