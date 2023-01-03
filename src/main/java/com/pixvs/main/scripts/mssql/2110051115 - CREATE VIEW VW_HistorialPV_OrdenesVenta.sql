/**
* Created by Angel Daniel Hernández Silva on 30/09/2021.
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

		SCC_USU_UsuarioAbreId AS usuarioId
	FROM VW_OrdenesVenta
	INNER JOIN SucursalesCortesCajas ON SCC_SucursalCorteCajaId = sucursalCorteCajaId
GO