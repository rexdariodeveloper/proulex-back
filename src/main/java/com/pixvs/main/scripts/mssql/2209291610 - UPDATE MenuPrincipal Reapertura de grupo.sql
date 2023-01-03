UPDATE MenuPrincipal SET MP_URL = '/app/configuracion/reapertura-grupo' WHERE MP_NodoId = (SELECT _mp.MP_NodoId FROM MenuPrincipal _mp WHERE MP_Titulo = 'Reapertura de grupo')
GO