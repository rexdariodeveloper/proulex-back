UPDATE MenuPrincipal SET MP_Titulo = 'Alumnos Sin Grupo', MP_TituloEN = 'Students Without Group' WHERE MP_NodoId = (SELECT _mp.MP_NodoId FROM MenuPrincipal _mp WHERE _mp.MP_Titulo = 'Reporte de Alumnos Sin Grupo')