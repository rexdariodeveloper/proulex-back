/*

	DROP TABLE UsuariosDepartamentos

	DELETE FROM RolesMenus WHERE ROLMP_MP_NodoId = 16
	DELETE FROM MenuPrincipal WHERE MP_NodoId = 16

	DROP VIEW VW_LISTADO_DEPARTAMENTOS

	DROP TABLE Departamentos

*/

/**
 * Created by Angel Daniel Hernández Silva on 05/08/2020.
 * Object:  Table [dbo].[Departamentos]
 */


CREATE TABLE [dbo].[Departamentos](
	[DEP_DepartamentoId] [int] IDENTITY(1,1) NOT NULL ,
	[DEP_DEP_DepartamentoPadreId] [int]  NULL ,
	[DEP_Prefijo] [varchar]  (20) NOT NULL ,
	[DEP_Nombre] [varchar]  (150) NOT NULL ,
	[DEP_USU_ResponsableId] [int]  NOT NULL ,
	[DEP_Autoriza] [bit]  NOT NULL ,
	[DEP_Activo] [bit]  NOT NULL ,
	[DEP_FechaCreacion] [datetime2](7) NOT NULL,
	[DEP_FechaModificacion] [datetime2](7) NULL,
	[DEP_USU_CreadoPorId] [int] NULL,
	[DEP_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[DEP_DepartamentoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],

 CONSTRAINT [UK_DEP_Prefijo] UNIQUE NONCLUSTERED 
(
	[DEP_Prefijo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Departamentos]  WITH CHECK ADD  CONSTRAINT [FK_DEP_DEP_DepartamentoPadreId] FOREIGN KEY([DEP_DEP_DepartamentoPadreId])
REFERENCES [dbo].[Departamentos] ([DEP_DepartamentoId])
GO

ALTER TABLE [dbo].[Departamentos] CHECK CONSTRAINT [FK_DEP_DEP_DepartamentoPadreId]
GO

ALTER TABLE [dbo].[Departamentos]  WITH CHECK ADD  CONSTRAINT [FK_DEP_USU_ResponsableId] FOREIGN KEY([DEP_USU_ResponsableId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Departamentos] CHECK CONSTRAINT [FK_DEP_USU_ResponsableId]
GO

ALTER TABLE [dbo].[Departamentos]  WITH CHECK ADD  CONSTRAINT [FK_DEP_USU_ModificadoPorId] FOREIGN KEY([DEP_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Departamentos] CHECK CONSTRAINT [FK_DEP_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[Departamentos]  WITH CHECK ADD  CONSTRAINT [FK_DEP_USU_CreadoPorId] FOREIGN KEY([DEP_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Departamentos] CHECK CONSTRAINT [FK_DEP_USU_CreadoPorId]

GO


CREATE   VIEW [dbo].[VW_LISTADO_DEPARTAMENTOS] AS

SELECT DEP_Activo AS "Activo", DEP_Prefijo AS "Prefijo", DEP_Nombre AS "Nombre", DEP_Autoriza AS "Autoriza" 
FROM Departamentos 

GO

INSERT [dbo].[MenuPrincipal] ([MP_Activo], [MP_FechaCreacion], [MP_Icono], [MP_NodoPadreId], [MP_Orden], [MP_CMM_SistemaAccesoId], [MP_Titulo], [MP_TituloEN], [MP_Tipo], [MP_URL]) 
VALUES ( 1, GETDATE(), N'card_travel', (select MP_NodoId from MenuPrincipal where MP_Titulo = 'CONFIGURACIÓN'), 5, 1000021, N'Organigrama', N'Organization chart', N'item', N'/config/departamentos')
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Organigrama' and MP_Icono = 'card_travel' and MP_Orden = 5)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO

CREATE TABLE [dbo].[UsuariosDepartamentos](
	[USUD_USU_UsuarioId] [int] NOT NULL,
	[USUD_DEP_DepartamentoId] [int] NOT NULL
PRIMARY KEY CLUSTERED 
(
	[USUD_USU_UsuarioId] ASC,
	[USUD_DEP_DepartamentoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[UsuariosDepartamentos]  WITH CHECK ADD  CONSTRAINT [FK_USUD_USU_UsuarioId] FOREIGN KEY([USUD_USU_UsuarioId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[UsuariosDepartamentos] CHECK CONSTRAINT [FK_USUD_USU_UsuarioId]
GO

ALTER TABLE [dbo].[UsuariosDepartamentos]  WITH CHECK ADD  CONSTRAINT [FK_USUD_DEP_DepartamentoId] FOREIGN KEY([USUD_DEP_DepartamentoId])
REFERENCES [dbo].[Departamentos] ([DEP_DepartamentoId])
GO

ALTER TABLE [dbo].[UsuariosDepartamentos] CHECK CONSTRAINT [FK_USUD_DEP_DepartamentoId]
GO