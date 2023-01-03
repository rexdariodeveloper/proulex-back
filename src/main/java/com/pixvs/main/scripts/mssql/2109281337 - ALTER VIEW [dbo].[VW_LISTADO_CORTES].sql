SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_LISTADO_CORTES]
AS
(
	SELECT
		vw.corteId id,
		vw.sede,
		SP_Nombre plantel,
		vw.usuario,
		vw.fechaInicio,
		vw.fechaFin,
		SUM(vw.total) total,

		SCC_SUC_SucursalId sedeId,
		SCC_USU_UsuarioAbreId usuarioId
	FROM 
		[dbo].[VW_CORTES] vw
		INNER JOIN SucursalesCortesCajas on vw.corteId = SCC_SucursalCorteCajaId
		INNER JOIN SucursalesPlanteles on SCC_SP_SucursalPlantelId = SP_SucursalPlantelId
	GROUP BY
		vw.corteId, vw.sede, SP_Nombre, vw.usuario, SCC_USU_UsuarioAbreId, vw.fechaInicio, vw.fechaFin, SCC_SUC_SucursalId
)
GO