UPDATE MenuPrincipal set MP_NodoPadreId =(select MP_NodoId from MenuPrincipal where MP_Titulo = 'CONFIGURACIÓN'),MP_URL='/config/alertas',MP_Orden = (select MAX(MP_Orden)+1
from MenuPrincipal 
where MP_NodoPadreId = (select MP_NodoId from MenuPrincipal where MP_Titulo = 'CONFIGURACIÓN'))
where MP_Titulo = 'Alertas'
GO
UPDATE MenuPrincipal set MP_NodoPadreId =(select MP_NodoId from MenuPrincipal where MP_Titulo = 'CONFIGURACIÓN'),MP_URL='/config/departamentos',MP_Orden = (select MAX(MP_Orden)+1
from MenuPrincipal 
where MP_NodoPadreId = (select MP_NodoId from MenuPrincipal where MP_Titulo = 'CONFIGURACIÓN'))
where MP_Titulo = 'Organigrama'
GO