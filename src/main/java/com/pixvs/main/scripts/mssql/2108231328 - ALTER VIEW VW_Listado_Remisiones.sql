/**
 * Created by Angel Daniel Hernández Silva on 23/08/2021.
 * Object: ALTER VIEW [dbo].[VW_Listado_Remisiones]
 */

CREATE OR ALTER VIEW [dbo].[VW_Listado_Remisiones] AS

    SELECT
        CLIR_ClienteRemisionId AS id,
		CLIR_Codigo AS codigo,
        CLI_Nombre AS clienteNombre,
        CLI_RFC AS clienteRFC,
        CLIR_Fecha AS fecha,
        Origen.ALM_Nombre AS almacenOrigenNombre,
        Destino.ALM_Nombre AS almacenDestinoNombre,
        SUM(CLIRD_Cantidad * LIPRED_Precio) AS monto,
        CMM_Valor AS estatusValor
    FROM ClientesRemisiones
    INNER JOIN Clientes ON CLI_ClienteId = CLIR_CLI_ClienteId
    INNER JOIN Almacenes AS Origen ON Origen.ALM_AlmacenId = CLIR_ALM_AlmacenOrigenId
    INNER JOIN Almacenes AS Destino ON Destino.ALM_AlmacenId = CLIR_ALM_AlmacenDestinoId
    INNER JOIN ClientesRemisionesDetalles ON CLIRD_CLIR_ClienteRemisionId = CLIR_ClienteRemisionId
    INNER JOIN ListadosPreciosDetalles ON LIPRED_LIPRE_ListadoPrecioId = CLI_LIPRE_ListadoPrecioId AND LIPRED_ART_ArticuloId = CLIRD_ART_ArticuloId
    INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = CLIR_CMM_EstatusId
    GROUP BY CLIR_ClienteRemisionId, CLIR_Codigo, CLI_Nombre, CLI_RFC, CLIR_Fecha, Origen.ALM_Nombre, Destino.ALM_Nombre, CMM_Valor

GO