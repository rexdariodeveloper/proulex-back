/**
 * Created by Angel Daniel Hernández Silva on 03/09/2021.
 * Object:  Table [dbo].[Cortes] a [dbo].[SucursalesCortesCaja]
 */

/*********************************/
/***** SucursalesCortesCajas *****/
/*********************************/

CREATE TABLE [dbo].[SucursalesCortesCajas](
	[SCC_SucursalCorteCajaId] [int] IDENTITY(1,1) NOT NULL,
    [SCC_SUC_SucursalId] [int] NOT NULL,
    [SCC_SP_SucursalPlantelId] [int] NULL,
    [SCC_USU_UsuarioAbreId] [int] NOT NULL,
	[SCC_FechaInicio] [datetime2](7) NOT NULL,
    [SCC_MontoAbrirCaja] [decimal](28,2) NOT NULL,
	[SCC_FechaFin] [datetime2](7) NULL,
    [SCC_MontoCerrarCaja] [decimal](28,2) NULL,
	[SCC_Parcial] [bit] NOT NULL DEFAULT(0),
PRIMARY KEY CLUSTERED 
(
	[SCC_SucursalCorteCajaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK

ALTER TABLE [dbo].[SucursalesCortesCajas]  WITH CHECK ADD  CONSTRAINT [FK_SCC_SUC_SucursalId] FOREIGN KEY([SCC_SUC_SucursalId])
REFERENCES [dbo].[Sucursales] ([SUC_SucursalId])
GO

ALTER TABLE [dbo].[SucursalesCortesCajas] CHECK CONSTRAINT [FK_SCC_SUC_SucursalId]
GO

ALTER TABLE [dbo].[SucursalesCortesCajas]  WITH CHECK ADD  CONSTRAINT [FK_SCC_SP_SucursalPlantelId] FOREIGN KEY([SCC_SP_SucursalPlantelId])
REFERENCES [dbo].[SucursalesPlanteles] ([SP_SucursalPlantelId])
GO

ALTER TABLE [dbo].[SucursalesCortesCajas] CHECK CONSTRAINT [FK_SCC_SP_SucursalPlantelId]
GO

ALTER TABLE [dbo].[SucursalesCortesCajas]  WITH CHECK ADD  CONSTRAINT [FK_SCC_USU_UsuarioAbreId] FOREIGN KEY([SCC_USU_UsuarioAbreId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[SucursalesCortesCajas] CHECK CONSTRAINT [FK_SCC_USU_UsuarioAbreId]
GO

/*********************************************/
/***** VW_TotalOVs_SucursalesCortesCajas *****/
/*********************************************/

CREATE OR ALTER VIEW [dbo].[VW_TotalOVs_SucursalesCortesCajas] AS

    SELECT
        corteId,
        SUM(totalDetalle) AS total
    FROM (
        SELECT
            SCC_SucursalCorteCajaId AS corteId,
            (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS totalDetalle
        FROM OrdenesVenta
        INNER JOIN OrdenesVentaDetalles ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
        INNER JOIN SucursalesCortesCajas
            ON SCC_USU_UsuarioAbreId = COALESCE(OV_USU_ModificadoPorId,OV_USU_CreadoPorId)
            AND SCC_FechaInicio <= COALESCE(OV_FechaModificacion,OV_FechaCreacion)
            AND COALESCE(SCC_FechaFin,GETDATE()) >= COALESCE(OV_FechaModificacion,OV_FechaCreacion)
        WHERE
            OV_CMM_EstatusId = 2000508 -- PAGADAS
    ) TotalDetallesCorte
    GROUP BY corteId

GO

/***********************/
/***** DROP Cortes *****/
/***********************/

DROP TABLE [dbo].[Cortes]
GO