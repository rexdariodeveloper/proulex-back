/**
* Created by Angel Daniel Hernández Silva on 06/10/2021.
* Object: ALTER TABLE [dbo].[OrdenesVenta] ADD CONSTRAINT [UNQ_OV_Codigo]
*/

/**************************************/
/***** Cambiar códigos duplicados *****/
/**************************************/

DECLARE @next int = 0;
select @next = AUT_Siguiente from Autonumericos where AUT_Nombre = 'OrdenesVenta'
​
​
​
select CONCAT('OV',FORMAT((@next+ROW_NUMBER() over(order by OV_Codigo, OV_OrdenVentaId)),'00000#')) as NUEVO_CODIGO, *
into #TMP_OV_REPETIDAS
from( select ROW_NUMBER() OVER(PARTITION BY OV_Codigo order by OV_Codigo, OV_OrdenVentaId) as NUM, OV_OrdenVentaId,  OV_Codigo from OrdenesVenta) CON
​
WHERE CON.NUM >= 2
​
​
UPDATE OrdenesVenta
SET OV_Codigo = NUEVO_CODIGO
FROM(select NUEVO_CODIGO, OV_OrdenVentaId as id from #TMP_OV_REPETIDAS tbl) CON
WHERE CON.id = OV_OrdenVentaId;
​
​
UPDATE Autonumericos
set AUT_Siguiente = AUT_Siguiente + (SELECT count(*) FROM #TMP_OV_REPETIDAS)
where AUT_Nombre = 'OrdenesVenta'
​
​
​
select MAX(OV_Codigo) from OrdenesVenta
​
GO

/*************************/
/***** UNQ_OV_Codigo *****/
/*************************/

ALTER TABLE [dbo].[OrdenesVenta] WITH CHECK ADD CONSTRAINT [UNQ_OV_Codigo] UNIQUE ([OV_Codigo])
GO