
-- =============================================
-- Author:		Ángeel Daniel Hernández Silva
-- Create date: 20/12/2022
-- Modify date:
-- Description:	Permiso para ver el menú de Vales de certificación en el PV
-- Version 1.0.0
-- =============================================

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] ON
GO

INSERT INTO [dbo].[MenuPrincipalPermisos](
	MPP_MenuPrincipalPermisoId,
	MPP_MP_NodoId,
	MPP_Nombre,
	MPP_Activo
)
VALUES (
	74,
	(select MP_NodoId from MenuPrincipal where MP_Titulo = N'Punto de venta'),
	N'Inscripciones - Vales de certificación',
	1
)
GO

SET IDENTITY_INSERT [dbo].[MenuPrincipalPermisos] OFF
GO

