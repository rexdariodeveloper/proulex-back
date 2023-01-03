Update MenuPrincipal
SET MP_NodoPadreId=(Select MP_NodoId from MenuPrincipal where MP_TituloEN='Academic programs'), MP_Orden=1
WHERE MP_Titulo='Programación Comercial';

Update MenuPrincipal
SET MP_NodoPadreId=(Select MP_NodoId from MenuPrincipal where MP_TituloEN='Academic programs'), MP_Orden=2
WHERE MP_Titulo='Metas';

Update MenuPrincipal
SET MP_NodoPadreId=(Select MP_NodoId from MenuPrincipal where MP_TituloEN='Academic programs'), MP_Orden=3
WHERE MP_Titulo='Cursos';

Update MenuPrincipal
SET MP_NodoPadreId=(Select MP_NodoId from MenuPrincipal where MP_TituloEN='Academic programs'), MP_Orden=4
WHERE MP_Titulo='Grupos';

Update MenuPrincipal
SET MP_NodoPadreId=(Select MP_NodoId from MenuPrincipal where MP_TituloEN='Academic programs'), MP_Orden=5
WHERE MP_Titulo='In company';