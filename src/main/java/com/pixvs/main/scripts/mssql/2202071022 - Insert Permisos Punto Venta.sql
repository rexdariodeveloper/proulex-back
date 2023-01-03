SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON
GO

INSERT INTO [dbo].[MenuPrincipalPermisos](
	MPP_MenuPrincipalPermisoId,
	MPP_MP_NodoId,
	MPP_Nombre,
	MPP_Activo
)
VALUES (
	28,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Punto de venta'),
	N'Inscripciones - Becas Sindicato',
	1
), (
	29,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Punto de venta'),
	N'Inscripciones - Becas Proulex',
	1
), (
	30,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Punto de venta'),
	N'Inscripciones - Re inscripciones',
	1
), (
	31,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Punto de venta'),
	N'Inscripciones - JOBS',
	1
), (
	32,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Punto de venta'),
	N'Inscripciones - JOBS SEMS',
	1
), (
	33,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Punto de venta'),
	N'Inscripciones - Cursos PCP',
	1
), (
	34,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Punto de venta'),
	N'Inscripciones - Historial',
	1
), (
	35,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Punto de venta'),
	N'Inscripciones - Descuentos',
	1
), (
	36,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Punto de venta'),
	N'Inscripciones - Alumnos sin grupo',
	1
), (
	37,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Punto de venta'),
	N'Acciones - Entrega de libros',
	1
), (
	38,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Punto de venta'),
	N'Acciones - Cancelar inscripción',
	1
)
GO

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] OFF
GO

