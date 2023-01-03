SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_getRenovacionesConFiltros]
	@FechaInicio DATE,
	@FechaFin DATE
AS
/* ****************************************************************
 * sp_getRenovacionesConFiltros
 * ****************************************************************
 * Descripción: Obtenemos los renovaciones con filtros (fecha inicio y fecha fin).
 * Autor: Rene Carrillo
 * Fecha: 28.04.2022
 * Versión: 1.0.0
 *****************************************************************
 * PARAMETROS DE ENTRADA: FechaInicio, FechaFin
 * PARAMETROS DE SALIDA: Empleados
 *****************************************************************
*/

DECLARE @EmpleadoIds NVARCHAR(MAX);

SELECT @EmpleadoIds = STRING_AGG(CAST(ec.EMPCO_EMP_EmpleadoId AS VARCHAR(20)), ',')
FROM EmpleadosContratos ec
WHERE EMPCO_CMM_EstatusId = 2000952
	AND ec.EMPCO_FechaInicio >= @FechaInicio
	AND ec.EMPCO_FechaFin <= @FechaFin;

-- SALIDA
SELECT *
FROM Empleados
WHERE EMP_EmpleadoId IN ((SELECT value FROM string_split(@EmpleadoIds, ',')))
	AND EMP_CMM_EstatusId = 2000952