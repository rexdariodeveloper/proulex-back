/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 * Object:  Table [dbo].[Proveedores]
 */


ALTER TABLE [dbo].[Proveedores] ADD [PRO_RazonSocial] [varchar]  (100) NOT NULL;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_Domicilio] [varchar]  (200) NOT NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_Colonia] [varchar]  (100) NOT NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_PAI_PaisId] [smallint]  NOT NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_EST_EstadoId] [int]  NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_Ciudad] [varchar]  (100) NOT NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_Cp] [varchar]  (5) NOT NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_Telefono] [varchar]  (25) NOT NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_Extension] [varchar]  (3) NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_CorreoElectronico] [varchar]  (50) NOT NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_PaginaWeb] [varchar]  (200) NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_DiasPlazoCredito] [int]  NOT NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_MontoCredito] [numeric]  (10,2) NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_DiasPago] [varchar]  (150) NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_MON_MonedaId] [smallint]  NOT NULL ;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_CuentaContable] [varchar](18) NULL;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_Activo] [bit]  NOT NULL;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_FechaCreacion] [datetime2](7) NOT NULL;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_FechaModificacion] [datetime2](7) NULL;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_USU_CreadoPorId] [int] NULL;
ALTER TABLE [dbo].[Proveedores] ADD [PRO_USU_ModificadoPorId] [int] NULL;
GO

ALTER TABLE [dbo].[Proveedores]  WITH CHECK ADD  CONSTRAINT [FK_PRO_USU_ModificadoPorId] FOREIGN KEY([PRO_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Proveedores] CHECK CONSTRAINT [FK_PRO_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[Proveedores]  WITH CHECK ADD  CONSTRAINT [FK_PRO_USU_CreadoPorId] FOREIGN KEY([PRO_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Proveedores] CHECK CONSTRAINT [FK_PRO_USU_CreadoPorId]

GO

ALTER TABLE [dbo].[Proveedores]  WITH CHECK ADD  CONSTRAINT [FK_Proveedores_Estados] FOREIGN KEY([PRO_EST_EstadoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO

ALTER TABLE [dbo].[Proveedores] CHECK CONSTRAINT [FK_Proveedores_Estados]
GO

ALTER TABLE [dbo].[Proveedores]  WITH CHECK ADD  CONSTRAINT [FK_Proveedores_Monedas] FOREIGN KEY([PRO_MON_MonedaId])
REFERENCES [dbo].[Monedas] ([MON_MonedaId])
GO

ALTER TABLE [dbo].[Proveedores] CHECK CONSTRAINT [FK_Proveedores_Monedas]
GO

ALTER TABLE [dbo].[Proveedores]  WITH CHECK ADD  CONSTRAINT [FK_Proveedores_Paises] FOREIGN KEY([PRO_PAI_PaisId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO

ALTER TABLE [dbo].[Proveedores] CHECK CONSTRAINT [FK_Proveedores_Paises]
GO

ALTER TABLE Proveedores
ADD CONSTRAINT UC_CodigoProveedor UNIQUE (PRO_Codigo);
GO


CREATE   VIEW [dbo].[VW_LISTADO_PROVEEDORES] AS

SELECT PRO_Activo AS "Activo", PRO_Codigo AS "Código", PRO_Nombre AS "Nombre", PRO_Rfc AS "RFC", PRO_FechaCreacion AS "Fecha Creación" 
FROM Proveedores 

GO

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'local_shipping', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'Catálogos'), 6, 1000021, N'Proveedores', N'Suppliers', N'item', N'/app/catalogos/proveedores')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Proveedores' and MP_Icono = 'local_shipping' and MP_Orden = 6)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO

