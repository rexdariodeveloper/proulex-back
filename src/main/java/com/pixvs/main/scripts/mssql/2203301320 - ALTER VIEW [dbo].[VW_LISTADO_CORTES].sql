SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER VIEW [dbo].[VW_LISTADO_CORTES]
AS
(
    SELECT
        corteId AS id,
        codigo,
        SUC_Nombre AS sede,
        SP_Nombre AS plantel,
        CONCAT(USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido) AS usuario,
        fechaInicio,
        fechaFin,
        total,
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
)
GO
