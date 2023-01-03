INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'folder_open', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Compras'), 4, 1000021, N'Reportes', N'Reports', N'collapsable', NULL)
GO

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'toc', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Reportes' and MP_Orden = 4), 1, 1000021, N'Reporte antigüedad saldos', N'Aging balances report', N'item', N'/app/compras/reportes/antiguedad-saldos')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Reporte antigüedad saldos' and MP_Icono = 'toc' and MP_Orden = 1)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO

