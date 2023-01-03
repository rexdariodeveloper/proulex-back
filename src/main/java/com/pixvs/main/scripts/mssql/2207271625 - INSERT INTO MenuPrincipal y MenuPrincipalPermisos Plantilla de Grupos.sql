INSERT INTO MenuPrincipal(MP_NodoPadreId, MP_Titulo, MP_TituloEN, MP_Activo, MP_Icono, MP_Orden, MP_Tipo, MP_URL, MP_CMM_SistemaAccesoId, MP_FechaCreacion, MP_Repetible, MP_Personalizado)
VALUES(1057, 'Plantilla de Grupos', 'Groups Template', 1, 'groups', 5, 'item', '/app/programacion-academica/plantilla-grupos', 1000021, GETDATE(), 0, 0)
GO

UPDATE MenuPrincipal SET MP_Orden = 6 WHERE MP_NodoId = 1064 -- In Company
GO

UPDATE MenuPrincipal SET MP_Orden = 7 WHERE MP_NodoId = 1087 -- Contratos
GO

UPDATE MenuPrincipal SET MP_Orden = 8 WHERE MP_NodoId = 1091 -- Becas
GO

UPDATE MenuPrincipal SET MP_Orden = 9 WHERE MP_NodoId = 1098 -- Proyeccion de Grupos
GO

INSERT INTO MenuPrincipalPermisos (MPP_MP_NodoId, MPP_Nombre, MPP_Activo)
VALUES ((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Descargar plantilla alumnos SEMS', 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Descargar plantilla profesores SEMS', 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Descargar listado alumnos SEMS', 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Importar plantilla alumnos SEMS', 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Importar plantilla profesores SEMS', 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Descargar plantilla alumnos JOBS', 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Descargar plantilla profesores JOBS', 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Descargar listado alumnos JOBS', 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Importar plantilla alumnos JOBS', 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Importar plantilla profesores JOBS', 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Descargar plantilla alumnos PCP', 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Importar plantilla alumnos PCP', 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_NodoPadreId = 1057 AND MP_TituloEN = 'Groups Template'), 'Exportar a excel', 1)
GO