UPDATE MenuPrincipalPermisos SET MPP_MP_NodoId = (SELECT MP_NodoId FROM MenuPrincipal WHERE MP_URL = N'/app/compras/solicitud-pago-rh') WHERE MPP_MP_NodoId = 1051
GO
