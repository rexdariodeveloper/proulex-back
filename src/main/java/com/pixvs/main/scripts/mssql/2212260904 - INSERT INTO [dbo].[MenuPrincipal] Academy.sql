INSERT INTO [dbo].[MenuPrincipal]
	(MP_NodoPadreId, MP_Titulo, MP_TituloEN, MP_Icono, MP_Orden, MP_Tipo, MP_URL, MP_CMM_SistemaAccesoId, MP_FechaCreacion, MP_Repetible, MP_Personalizado, MP_Activo)
VALUES
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_TituloEN = N'Academic programs'), N'Workshop'             , N'Workshop'                , N'work_outline', 10, N'item', N'/app/programacion-academica/workshops'  , 1000021, GETDATE(), 0, 0, 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_TituloEN = N'Academic programs'), N'Diplomado'            , N'Cert. Program'           , N'work_outline', 11, N'item', N'/app/programacion-academica/diplomados' , 1000021, GETDATE(), 0, 0, 1),
	((SELECT MP_NodoId FROM MenuPrincipal WHERE MP_TituloEN = N'School control')   , N'Gestión de diplomados', N'Cert. Program Management', N'view_list'   , 9 , N'item', N'/app/control-escolar/gestion-diplomados', 1000021, GETDATE(), 0, 0, 1)
GO