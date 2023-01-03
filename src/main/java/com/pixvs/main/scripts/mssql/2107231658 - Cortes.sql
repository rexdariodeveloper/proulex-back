/**
 * Created by Angel Daniel Hernández Silva on 30/06/2021.
 * Object:  Table [dbo].[Cortes]
 */

/******************/
/***** Cortes *****/
/******************/

CREATE TABLE [dbo].[Cortes](
	[COR_CorteId] [int] IDENTITY(1,1) NOT NULL,
    [COR_SUC_SucursalId] [int] NOT NULL,
    [COR_USU_UsuarioAbreId] [int] NOT NULL,
	[COR_FechaInicio] [datetime2](7) NOT NULL,
    [COR_MontoAbrirCaja] [decimal](28,2) NOT NULL,
	[COR_FechaFin] [datetime2](7) NULL,
    [COR_MontoCerrarCaja] [decimal](28,2) NULL,
    [COR_USU_UsuarioCierraId] [int] NULL,
	[COR_Parcial] [bit] NOT NULL DEFAULT(0),
PRIMARY KEY CLUSTERED 
(
	[COR_CorteId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK

ALTER TABLE [dbo].[Cortes]  WITH CHECK ADD  CONSTRAINT [FK_COR_SUC_SucursalId] FOREIGN KEY([COR_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[Cortes] CHECK CONSTRAINT [FK_COR_SUC_SucursalId]
GO

ALTER TABLE [dbo].[Cortes]  WITH CHECK ADD  CONSTRAINT [FK_COR_USU_UsuarioAbreId] FOREIGN KEY([COR_USU_UsuarioAbreId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Cortes] CHECK CONSTRAINT [FK_COR_USU_UsuarioAbreId]
GO

ALTER TABLE [dbo].[Cortes]  WITH CHECK ADD  CONSTRAINT [FK_COR_USU_UsuarioCierraId] FOREIGN KEY([COR_USU_UsuarioCierraId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Cortes] CHECK CONSTRAINT [FK_COR_USU_UsuarioCierraId]
GO