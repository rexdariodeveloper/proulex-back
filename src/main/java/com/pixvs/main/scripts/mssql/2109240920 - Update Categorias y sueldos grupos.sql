UPDATE ProgramasGrupos
SET PROGRU_CategoriaProfesor=PAP.PAPC_Categoria, PROGRU_SueldoProfesor=TABD.TABD_Sueldo
--Select TOP 1 PAP.PAPC_Categoria as categoria, TABD.TABD_Sueldo as sueldo
from TabuladoresDetalles TABD
LEFT JOIN Tabuladores TAB on TAB.TAB_TabuladorId = TABD.TABD_TAB_TabuladorId
LEFT JOIN ProgramasIdiomas PROGI on PROGI.PROGI_TAB_TabuladorId = TAB.TAB_TabuladorId
LEFT JOIN PAProfesoresCategorias PAP on PAP.PAPC_ProfesorCategoriaId = TABD.TABD_PAPC_ProfesorCategoriaId 
LEFT JOIN EmpleadosCategorias EMPCA on EMPCA.EMPCA_PAPC_ProfesorCategoriaId = PAP.PAPC_ProfesorCategoriaId
LEFT JOIN Empleados EMP on EMP.EMP_EmpleadoId = EMPCA.EMPCA_EMP_EmpleadoId
WHERE PROGI.PROGI_ProgramaIdiomaId=PROGRU_PROGI_ProgramaIdiomaId and EMP.EMP_EmpleadoId=PROGRU_EMP_EmpleadoId and PROGI.PROGI_CMM_Idioma=PROGI.PROGI_CMM_Idioma 
and TAB.TAB_Activo=1 AND PROGRU_EMP_EmpleadoId is not null and PROGRU_CategoriaProfesor is null