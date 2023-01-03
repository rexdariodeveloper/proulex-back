/**
 * Created by Angel Daniel Hernández Silva on 11/06/2020.
 * Object:  Table [dbo].[MenuListadosGenerales]
 */


CREATE TABLE [dbo].[MenuListadosGenerales](
	[MLG_ListadoGeneralNodoId] [int] IDENTITY(1,1) NOT NULL ,
	[MLG_NodoPadreId] [int]  NULL ,
	[MLG_Titulo] [varchar]  (255) NOT NULL ,
	[MLG_TituloEN] [varchar]  (255) NOT NULL ,
	[MLG_Activo] [bit]  NULL ,
	[MLG_Icono] [varchar]  (255) NULL ,
	[MLG_Orden] [int]  NOT NULL ,
	[MLG_CMM_TipoNodoId] [int] NOT NULL ,
	[MLG_NombreTablaCatalogo] [varchar] (255) NULL,
	[MLG_CMM_ControlCatalogo] [varchar] (255) NULL,
	[MLG_PermiteBorrar] [bit] NULL,
	[MLG_UrlAPI] [varchar] (MAX) NULL,
	[MLG_FechaCreacion] [datetime2](7) NOT NULL,
	[MLG_FechaModificacion] [datetime2](7) NULL,
	[MLG_USU_CreadoPorId] [int] NULL,
	[MLG_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[MLG_ListadoGeneralNodoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[MenuListadosGenerales]  WITH CHECK ADD  CONSTRAINT [FK_MLG_CMM_TipoNodoId] FOREIGN KEY([MLG_CMM_TipoNodoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[MenuListadosGenerales] CHECK CONSTRAINT [FK_MLG_CMM_TipoNodoId]
GO

ALTER TABLE [dbo].[MenuListadosGenerales]  WITH CHECK ADD  CONSTRAINT [FK_MLG_USU_ModificadoPorId] FOREIGN KEY([MLG_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[MenuListadosGenerales] CHECK CONSTRAINT [FK_MLG_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[MenuListadosGenerales]  WITH CHECK ADD  CONSTRAINT [FK_MLG_USU_CreadoPorId] FOREIGN KEY([MLG_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[MenuListadosGenerales] CHECK CONSTRAINT [FK_MLG_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[MenuListadosGenerales] WITH CHECK ADD CONSTRAINT [CHK_MLG_NombreTablaCatalogo] CHECK ([MLG_CMM_TipoNodoId] != 1000082 OR [MLG_NombreTablaCatalogo] IS NOT NULL)
GO

ALTER TABLE [dbo].[MenuListadosGenerales] WITH CHECK ADD CONSTRAINT [CHK_MLG_CMM_ControlCatalogo] CHECK ([MLG_NombreTablaCatalogo] != 'ControlesMaestrosMultiples' OR [MLG_CMM_ControlCatalogo] IS NOT NULL)
GO

ALTER TABLE [dbo].[MenuListadosGenerales] WITH CHECK ADD CONSTRAINT [CHK_MLG_PermiteBorrar] CHECK ([MLG_CMM_TipoNodoId] != 1000082 OR [MLG_PermiteBorrar] IS NOT NULL)
GO

ALTER TABLE [dbo].[MenuListadosGenerales] WITH CHECK ADD CONSTRAINT [DF_MenuListadosGenerales_MLG_Activo]  DEFAULT (1) FOR [MLG_Activo]
GO

CREATE   VIEW [dbo].[VW_LISTADO_MENU_LISTADOS_GENERALES] AS

SELECT
	MLG_NodoPadreId AS "Nodo Padre Id",
	MLG_Titulo AS "Título",
	MLG_TituloEN AS "Título EN",
	MLG_Activo AS "Activo",
	MLG_Icono AS "Icono",
	MLG_Orden AS "Orden",
	TipoNodo.CMM_Valor AS "Tipo Nodo",
	MLG_FechaCreacion AS "Fecha Creación" 
FROM MenuListadosGenerales 
INNER JOIN ControlesMaestrosMultiples TipoNodo ON TipoNodo.CMM_ControlId = MLG_CMM_TipoNodoId

GO

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
	1,
	GETDATE(),
	N'list',
	(select MP_NodoId from MenuPrincipal where MP_Titulo = 'CONFIGURACIÓN'),
	3,
	1000021,
	N'Menu Listados Generales',
	N'General Listings Menu',
	N'item',
	N'/config/menu-listados-generales'
)
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Menu Listados Generales' and MP_Icono = 'list' and MP_Orden = 3)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO

SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] ON 
GO
INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_USU_CreadoPorId],
	[CMM_FechaCreacion],
	[CMM_FechaModificacion],
	[CMM_USU_ModificadoPorId],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 1000081,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_MLG_TipoNodoId',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ CAST(N'2020-06-12T11:00:00.0000000' AS DateTime2),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Módulo'
)
GO

INSERT [dbo].[ControlesMaestrosMultiples] (
	[CMM_ControlId],
	[CMM_Activo],
	[CMM_Control],
	[CMM_USU_CreadoPorId],
	[CMM_FechaCreacion],
	[CMM_FechaModificacion],
	[CMM_USU_ModificadoPorId],
	[CMM_Referencia],
	[CMM_Sistema],
	[CMM_Valor]
) VALUES (
	/* [CMM_ControlId] */ 1000082,
	/* [CMM_Activo] */ 1,
	/* [CMM_Control] */ N'CMM_MLG_TipoNodoId',
	/* [CMM_USU_CreadoPorId] */ NULL,
	/* [CMM_FechaCreacion] */ CAST(N'2020-06-12T11:00:00.0000000' AS DateTime2),
	/* [CMM_FechaModificacion] */ NULL,
	/* [CMM_USU_ModificadoPorId] */ NULL,
	/* [CMM_Referencia] */ NULL,
	/* [CMM_Sistema] */ 1,
	/* [CMM_Valor] */ N'Catálogo'
)
GO
SET IDENTITY_INSERT [dbo].[ControlesMaestrosMultiples] OFF
GO

CREATE TABLE [dbo].[MenuListadosGeneralesDetalles](
	[MLGD_ListadoGeneralDetalleId] [int] IDENTITY(1,1) NOT NULL ,
	[MLGD_MLG_ListadoGeneralNodoId] [int] NOT NULL ,
	[MLGD_JsonConfig] [varchar] (MAX) NOT NULL ,
	[MLGD_JsonListado] [varchar] (MAX) NOT NULL ,
  [MLGD_CampoTabla] [varchar] (255) NOT NULL ,
  [MLGD_CampoModelo] [varchar] (255) NOT NULL
PRIMARY KEY CLUSTERED
(
	[MLGD_ListadoGeneralDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[MenuListadosGeneralesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_MLGD_MLG_ListadoGeneralNodoId] FOREIGN KEY([MLGD_MLG_ListadoGeneralNodoId])
REFERENCES [dbo].[MenuListadosGenerales] ([MLG_ListadoGeneralNodoId])
GO

ALTER TABLE [dbo].[MenuListadosGeneralesDetalles] CHECK CONSTRAINT [FK_MLGD_MLG_ListadoGeneralNodoId]
GO