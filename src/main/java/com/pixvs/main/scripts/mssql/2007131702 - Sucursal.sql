/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 * Object:  Table [dbo].[Sucursales]
 */


CREATE TABLE [dbo].[Sucursales](
	[SUC_SucursalId] [int] IDENTITY(1,1) NOT NULL ,
	[SUC_CodigoSucursal] [varchar]  (10) NOT NULL ,
	[SUC_Nombre] [varchar]  (100) NOT NULL ,
	[SUC_Activo] [bit]  NOT NULL 
PRIMARY KEY CLUSTERED 
(
	[SUC_SucursalId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Sucursales] WITH CHECK ADD CONSTRAINT [DF_Sucursales_SUC_Activo]  DEFAULT (1) FOR [SUC_Activo]
GO


CREATE   VIEW [dbo].[VW_LISTADO_SUCURSALES] AS

SELECT
	SUC_Nombre AS "Nombre" 
FROM Sucursales 

GO


INSERT [dbo].[Sucursales] (
	[SUC_CodigoSucursal],
	[SUC_Nombre],
	[SUC_Activo]
) VALUES (
	/* [SUC_CodigoSucursal] */ 'MAT',
	/* [SUC_Nombre] */ 'Matriz',
	/* [SUC_Activo] */ 1
)
GO