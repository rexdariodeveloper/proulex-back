SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER PROCEDURE [dbo].[sp_getListadoTabuladores]
AS
    DECLARE
        @cols  nvarchar(max),
        @stmt  nvarchar(max)
    SET @cols = (SELECT STRING_AGG(PAPC_Categoria,',') FROM PAProfesoresCategorias WHERE PAPC_Activo = 1)

    SELECT @stmt = '
        SELECT
        	TAB_Codigo AS codigo,
            TAB_Descripcion AS descripcion,
            TAB_PagoDiasFestivos AS pagoDiasFestivos,
        	'+@cols+'
        FROM Tabuladores tab
        LEFT JOIN(
        SELECT
        	TD.TABD_TAB_TabuladorId AS tabId,
        	PAPC.PAPC_Categoria AS categoria,
        	TD.TABD_Sueldo AS sueldo
        FROM TabuladoresDetalles TD
        LEFT JOIN PAProfesoresCategorias PAPC ON PAPC.PAPC_ProfesorCategoriaId = TD.TABD_PAPC_ProfesorCategoriaId
         WHERE TD.TABD_Activo = 1
        ) AS sueldos
        PIVOT(
        	MAX(sueldo) FOR categoria IN('+@cols+')
        ) AS pvt_tabd ON pvt_tabd.tabId = tab.TAB_TabuladorId
       WHERE TAB_Activo = 1
    '
    EXEC sp_executesql @stmt = @stmt
GO