/**
* Created by Angel Daniel Hernández Silva on 06/10/2022.
*/

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON
GO

INSERT INTO [dbo].[MenuPrincipalPermisos](
	MPP_MenuPrincipalPermisoId,
	MPP_MP_NodoId,
	MPP_Nombre,
	MPP_Activo
)
VALUES (
	72,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Solicitudes de pago'),
	N'Reemplazar documentos',
	1
)
GO

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] OFF
GO

