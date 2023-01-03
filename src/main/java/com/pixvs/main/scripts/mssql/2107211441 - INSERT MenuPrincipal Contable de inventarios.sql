INSERT INTO [dbo].[MenuPrincipal](
      [MP_NodoPadreId]
      ,[MP_Titulo]
      ,[MP_TituloEN]
      ,[MP_Activo]
      ,[MP_Icono]
      ,[MP_Orden]
      ,[MP_Tipo]
      ,[MP_URL]
      ,[MP_CMM_SistemaAccesoId]
      ,[MP_FechaCreacion]
)
VALUES(
     (select MP_NodoPadreId from MenuPrincipal where MP_URL = '/app/inventario/kardex-articulos')
     ,N'Contable de inventarios'
     ,N'Inventory accountant'
     ,1
     ,N'assignment'
     ,(select MAX(MP_Orden) + 1 from MenuPrincipal where MP_NodoPadreId = (select MP_NodoPadreId from MenuPrincipal where MP_URL = '/app/inventario/kardex-articulos'))
     ,N'item'
     ,N'/app/inventario/contable-inventarios'
     ,1000021
     ,GETDATE()
)
GO

INSERT INTO [dbo].[RolesMenus](
	[ROLMP_Crear],
	[ROLMP_Eliminar],
	[ROLMP_Modificar],
	[ROLMP_MP_NodoId],
	[ROLMP_ROL_RolId],
	[ROLMP_FechaCreacion]
)
VALUES(
	0,
	0,
	0,
	(select MP_NodoId from MenuPrincipal where MP_URL = N'/app/inventario/contable-inventarios'),
	1,
	GETDATE()
)
GO