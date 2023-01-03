/**
* Created by Angel Daniel Hernández Silva on 18/11/2021.
* Object: CREATE OR ALTER VIEW [dbo].[VW_HistorialPV_OrdenesVenta]
*/

CREATE OR ALTER VIEW [dbo].[VW_HistorialPV_OrdenesVenta] AS
	SELECT
		id,
		COALESCE(fechaModificacion,fechaCreacion) AS fecha,
		codigo,
		montoTotal AS monto,
		estatusId,
		estatusValor AS estatus,

		SCC_USU_UsuarioAbreId AS usuarioId,
		SCC_SUC_SucursalId AS sucursalId
	FROM VW_OrdenesVenta
	INNER JOIN SucursalesCortesCajas ON SCC_SucursalCorteCajaId = sucursalCorteCajaId
GO