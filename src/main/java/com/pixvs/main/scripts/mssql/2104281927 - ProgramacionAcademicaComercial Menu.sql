/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 * Object:  Table [dbo].[ProgramacionAcademicaComercial]
 */



-- UPDATE [dbo].[MenuPrincipal] SET [MP_Orden] = 8 WHERE [MP_URL] = '/app/compras/listado-alertas'
-- GO

-- INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo]) 
-- VALUES ( 1, GETDATE(), N'calendar_today', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'MÓDULOS'), 7, 1000021, N'Programación Académica', N'Academic Programming', N'collapsable')
-- GO

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'calendar_today', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Programación Académica'), 1, 1000021, N'Programación Académica Comercial', N'Commercial Academic Programming', N'item', N'/app/programacion-academica/programacion-academica-comercial')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Programación Académica Comercial' and MP_Icono = 'calendar_today' and MP_Orden = 1)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO

