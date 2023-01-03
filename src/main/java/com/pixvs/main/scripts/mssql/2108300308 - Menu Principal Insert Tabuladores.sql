/*************************/
/***** MenuPrincipal *****/
/*************************/

INSERT [dbo].[MenuPrincipal] (
    [MP_Activo],
    [MP_FechaCreacion],
    [MP_Icono],
    [MP_NodoPadreId],
    [MP_Orden],
    [MP_CMM_SistemaAccesoId],
    [MP_Titulo],
    [MP_TituloEN],
    [MP_Tipo],
    [MP_URL]
) 
VALUES (
    1, -- [MP_Activo]
    GETDATE(), -- [MP_FechaCreacion]
    N'receipt', -- [MP_Icono]
    (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Catálogos'), -- [MP_NodoPadreId]
    11, -- [MP_Orden]
    1000021, -- [MP_CMM_SistemaAccesoId]
    N'Tabuladores', -- [MP_Titulo]
    N'Tabs', -- [MP_TituloEN]
    N'item', -- [MP_Tipo]
    N'/app/catalogos/tabuladores' -- [MP_URL]
)
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Tabuladores' and MP_Icono = 'receipt' and MP_Orden = 11)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO