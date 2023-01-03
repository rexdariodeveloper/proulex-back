/**
* Created by Angel Daniel Hernández Silva on 10/07/2022.
*/

CREATE OR ALTER VIEW [dbo].[VW_HistorialPV_OrdenesVenta] AS
	SELECT
		id,
		fechaCreacion AS fecha,
		codigo,
		montoTotal AS monto,
		estatusId,
		estatusValor AS estatus,

		SCC_USU_UsuarioAbreId AS usuarioId,
		SCC_SUC_SucursalId AS sucursalId
	FROM VW_OrdenesVenta
	INNER JOIN SucursalesCortesCajas ON SCC_SucursalCorteCajaId = sucursalCorteCajaId
GO