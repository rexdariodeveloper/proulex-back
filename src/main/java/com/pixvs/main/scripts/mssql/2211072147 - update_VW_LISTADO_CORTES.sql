SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_LISTADO_CORTES]
AS
(
    SELECT
        Cortes.corteId AS id,
        codigo,
        SUC_Nombre AS sede,
        SP_Nombre AS plantel,
        CONCAT(USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido) AS usuario,
        fechaInicio,
        fechaFin,
        (total - COALESCE(totalCancelaciones, 0)) AS total,
		CASE WHEN fechaFin IS NULL THEN 'Abierto' ELSE 'Cerrado' END estatus,
        sedeId,
        usuarioId
    FROM(
        SELECT
            SCC_SucursalCorteCajaId AS corteId,
            SCC_Codigo AS codigo,
            SCC_SUC_SucursalId AS sedeId,
            SCC_SP_SucursalPlantelId AS plantelId,
            SCC_USU_UsuarioAbreId AS usuarioId,
            SCC_FechaInicio AS fechaInicio,
            SCC_FechaFin AS fechaFin,
            SUM(montoTotal) AS total
        FROM SucursalesCortesCajas
        LEFT JOIN VW_OrdenesVenta ON SCC_SucursalCorteCajaId = sucursalCorteCajaId
        GROUP BY SCC_SucursalCorteCajaId, SCC_Codigo, SCC_SUC_SucursalId, SCC_SP_SucursalPlantelId, SCC_USU_UsuarioAbreId, SCC_FechaInicio, SCC_FechaFin
    ) Cortes
    INNER JOIN Sucursales ON cortes.sedeId = SUC_SucursalId
    LEFT JOIN SucursalesPlanteles ON cortes.plantelId = SP_SucursalPlantelId
    INNER JOIN Usuarios ON cortes.usuarioId = USU_UsuarioId
    LEFT JOIN(
    SELECT
                corteId,
                SUM(subtotalDetalle) AS montoSubtotal,
                SUM(ivaDetalle) AS montoIVA,
                SUM(iepsDetalle) AS montoIEPS,
                SUM(descuentoDetalle) AS montoDescuento,
                SUM(totalDetalle) AS totalCancelaciones
            FROM (
                SELECT
                    OV_OrdenVentaId AS id,
                    OV_SCC_SucursalCorteCajaId AS corteId,
                    (SELECT Subtotal FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS subtotalDetalle,
                    (SELECT IVA FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS ivaDetalle,
                    (SELECT IEPS FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS iepsDetalle,
                    (SELECT Descuento FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS descuentoDetalle,
                    (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](OVD_Cantidad,OVD_Precio,OVD_Descuento,CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END,OVD_IEPS,OVD_IEPSCuotaFija)) AS totalDetalle
                FROM OrdenesVenta
                INNER JOIN OrdenesVentaDetalles ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
                INNER JOIN OrdenesVentaCancelacionesDetalles ON OVCD_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
              ) MontosOVD
              GROUP BY corteId
    ) AS cancelaciones ON Cortes.corteId = cancelaciones.corteId
)
GO