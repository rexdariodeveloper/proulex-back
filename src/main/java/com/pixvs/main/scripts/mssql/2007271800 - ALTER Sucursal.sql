ALTER TABLE Sucursales ADD
	[SUC_Prefijo] [varchar] (4) NULL,
	[SUC_USU_ResponsableId] int NULL,
	[SUC_Domicilio] [varchar] (250) NULL,
	[SUC_Colonia] [varchar] (250) NULL,
	[SUC_PAI_PaisId] smallint NULL,
	[SUC_EST_EstadoId] int NULL,
	[SUC_Ciudad] [varchar] (100) NULL,
	[SUC_CP] [varchar] (10) NULL,
	[SUC_Telefono] [varchar] (25) NULL,
	[SUC_Extension] [varchar] (10) NULL,
	[SUC_PorcentajeComision] [decimal] (28,2) NULL,
	[SUC_PresupuestoSemanal] [decimal] (28,2) NULL,
	[SUC_Predeterminada] [bit] NULL,
	[SUC_FechaCreacion] [datetime2](7) NULL,
	[SUC_USU_CreadoPorId] int NULL,
	[SUC_FechaUltimaModificacion] [datetime2](7) NULL,
	[SUC_USU_ModificadoPorId] int NULL
GO


UPDATE Sucursales SET
	SUC_Prefijo = '.',
	SUC_USU_ResponsableId = 1,
	SUC_Domicilio = '.',
	SUC_Colonia = '.',
	SUC_PAI_PaisId = 1,
	SUC_EST_EstadoId = 14,
	SUC_Ciudad = '.',
	SUC_CP = '.',
	SUC_Telefono = ',',
	SUC_Predeterminada = 1,
	SUC_FechaCreacion = GETDATE(),
	SUC_USU_CreadoPorId = 1,
	SUC_PresupuestoSemanal = 0
GO

ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_Prefijo] [varchar] (4) NOT NULL
GO
ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_USU_ResponsableId] int NOT NULL
GO
ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_Domicilio] [varchar] (250) NOT NULL
GO
ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_Colonia] [varchar] (250) NOT NULL
GO
ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_PAI_PaisId] smallint NOT NULL
GO
ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_EST_EstadoId] int NOT NULL
GO
ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_Ciudad] [varchar] (100) NOT NULL
GO
ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_CP] [varchar] (10) NOT NULL
GO
ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_Telefono] [varchar] (25) NOT NULL
GO
ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_Predeterminada] [bit] NOT NULL
GO
ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_FechaCreacion] [datetime2](7) NOT NULL
GO
ALTER TABLE [dbo].[Sucursales] ALTER COLUMN [SUC_USU_CreadoPorId] int NOT NULL
GO

-- FKs

ALTER TABLE [dbo].[Sucursales]  WITH CHECK ADD  CONSTRAINT [FK_SUC_USU_ResponsableId] FOREIGN KEY([SUC_USU_ResponsableId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Sucursales] CHECK CONSTRAINT [FK_SUC_USU_ResponsableId]
GO

ALTER TABLE [dbo].[Sucursales]  WITH CHECK ADD  CONSTRAINT [FK_SUC_PAI_PaisId] FOREIGN KEY([SUC_PAI_PaisId])
REFERENCES [dbo].[Paises] ([PAI_PaisId])
GO

ALTER TABLE [dbo].[Sucursales] CHECK CONSTRAINT [FK_SUC_PAI_PaisId]
GO

ALTER TABLE [dbo].[Sucursales]  WITH CHECK ADD  CONSTRAINT [FK_SUC_EST_EstadoId] FOREIGN KEY([SUC_EST_EstadoId])
REFERENCES [dbo].[Estados] ([EST_EstadoId])
GO

ALTER TABLE [dbo].[Sucursales] CHECK CONSTRAINT [FK_SUC_EST_EstadoId]
GO

ALTER TABLE [dbo].[Sucursales]  WITH CHECK ADD  CONSTRAINT [FK_SUC_USU_CreadoPorId] FOREIGN KEY([SUC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Sucursales] CHECK CONSTRAINT [FK_SUC_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[Sucursales]  WITH CHECK ADD  CONSTRAINT [FK_SUC_USU_ModificadoPorId] FOREIGN KEY([SUC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Sucursales] CHECK CONSTRAINT [FK_SUC_USU_ModificadoPorId]
GO

-- CHECKs

CREATE FUNCTION existeSucursalMatriz ( @sucursalValidarId int )
RETURNS bit
AS
BEGIN
	
	IF (@sucursalValidarId IS NOT NULL) AND EXISTS (
		SELECT *
		FROM Sucursales
		WHERE
			SUC_SucursalId != @sucursalValidarId
			AND SUC_Activo = CAST(1 AS bit)
			AND SUC_Predeterminada = CAST(1 AS bit)
	) return CAST(1 as bit)

	IF (@sucursalValidarId IS NULL) AND EXISTS (
		SELECT *
		FROM Sucursales
		WHERE
			SUC_Activo = CAST(1 AS bit)
			AND SUC_Predeterminada = CAST(1 AS bit)
	) return CAST(1 as bit)
    
	return CAST(0 AS bit)
END
GO

ALTER TABLE [dbo].[Sucursales] WITH CHECK ADD CONSTRAINT [CHK_Matriz] CHECK ([SUC_Predeterminada] = CAST(0 AS bit) OR [dbo].[existeSucursalMatriz](SUC_SucursalId) = CAST(0 AS bit))
GO

ALTER   VIEW [dbo].[VW_LISTADO_SUCURSALES] AS

SELECT
	SUC_CodigoSucursal AS "Código",
	SUC_Nombre AS "Nombre",
	SUC_CodigoSucursal AS "Responsable",
	SUC_PorcentajeComision AS "% Comisión",
	SUC_Telefono AS "Teléfono",
	SUC_Activo AS "Activo"
FROM Sucursales 
INNER JOIN Usuarios ON USU_UsuarioId = SUC_USU_ResponsableId

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
	N'store',
	(select MP_NodoId from MenuPrincipal where MP_Titulo = 'Catálogos'),
	4,
	1000021,
	N'Sucursales',
	N'Branches',
	N'item',
	N'/app/catalogos/sucursales'
)
GO

INSERT INTO [dbo].[RolesMenus]([ROLMP_FechaModificacion],[ROLMP_MP_NodoId],[ROLMP_ROL_RolId], ROLMP_Crear, ROLMP_Modificar, ROLMP_Eliminar, ROLMP_FechaCreacion)
     VALUES
           (null
           ,(SELECT MP_NodoId FROM MenuPrincipal WHERE MP_Titulo = 'Sucursales' and MP_Icono = 'store' and MP_Orden = 4)
           ,(select USU_ROL_RolId from Usuarios where USU_CorreoElectronico = 'pixvs.server@gmail.com')
		   , 1, 1, 1
		   , GETDATE()
		   )
GO