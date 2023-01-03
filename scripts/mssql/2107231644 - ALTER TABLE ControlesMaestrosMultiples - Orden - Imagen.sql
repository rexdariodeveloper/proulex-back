/**
 * Created by Angel Daniel Hernández Silva on 06/07/2021.
 * Object:  ALTER TABLE [dbo].[ControlesMaestrosMultiples] - Orden, Imagen
 */

/***************************/
/***** Nuevas columnas *****/
/***************************/

ALTER TABLE [dbo].[ControlesMaestrosMultiples] ADD [CMM_Orden] smallint NULL
GO

ALTER TABLE [dbo].[ControlesMaestrosMultiples] ADD [CMM_ARC_ImagenId] int NULL
GO

/***************/
/***** FKs *****/
/***************/

ALTER TABLE [dbo].[ControlesMaestrosMultiples]  WITH CHECK ADD  CONSTRAINT [FK_CMM_ARC_ImagenId] FOREIGN KEY([CMM_ARC_ImagenId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[ControlesMaestrosMultiples] CHECK CONSTRAINT [FK_CMM_ARC_ImagenId]
GO

/***********************/
/***** CHK - Orden *****/
/***********************/

CREATE FUNCTION existeCMMOrden ( @controlId int, @orden smallint )
RETURNS bit
AS
BEGIN
	
	IF (@orden IS NULL) return CAST(0 as bit)

    IF (@controlId IS NOT NULL) AND EXISTS (
		SELECT *
		FROM ControlesMaestrosMultiples
		WHERE
			CMM_ControlId != @controlId
			AND CMM_Orden = @orden
			AND CMM_Activo = CAST(1 AS bit)
	) return CAST(1 as bit)

	IF (@controlId IS NULL) AND EXISTS (
		SELECT *
		FROM ControlesMaestrosMultiples
		WHERE
			CMM_Orden = @orden
			AND CMM_Activo = CAST(1 AS bit)
	) return CAST(1 as bit)
    
	return CAST(0 AS bit)
END
GO

ALTER TABLE [dbo].[ControlesMaestrosMultiples] WITH CHECK ADD CONSTRAINT [CHK_CMM_Orden] CHECK ([dbo].[existeCMMOrden](CMM_ControlId,CMM_Orden) = CAST(0 AS bit))
GO

/*

Orden de idiomas en PROULEX

UPDATE ControlesMaestrosMultiples SET CMM_Orden = 1 WHERE CMM_Control = 'CMM_ART_Idioma' AND CMM_Valor = 'Inglés' AND CMM_Referencia = 'ING' AND CMM_Activo = 1
GO

UPDATE ControlesMaestrosMultiples SET CMM_Orden = 2 WHERE CMM_Control = 'CMM_ART_Idioma' AND CMM_Valor = 'Fránces' AND CMM_Referencia = 'FRA' AND CMM_Activo = 1
GO

UPDATE ControlesMaestrosMultiples SET CMM_Orden = 3 WHERE CMM_Control = 'CMM_ART_Idioma' AND CMM_Valor = 'Alemán' AND CMM_Referencia = 'ALE' AND CMM_Activo = 1
GO

UPDATE ControlesMaestrosMultiples SET CMM_Orden = 4 WHERE CMM_Control = 'CMM_ART_Idioma' AND CMM_Valor = 'Chino' AND CMM_Referencia = 'CHI' AND CMM_Activo = 1
GO

UPDATE ControlesMaestrosMultiples SET CMM_Orden = 5 WHERE CMM_Control = 'CMM_ART_Idioma' AND CMM_Valor = 'Español' AND CMM_Referencia = 'ESP' AND CMM_Activo = 1
GO

UPDATE ControlesMaestrosMultiples SET CMM_Orden = 6 WHERE CMM_Control = 'CMM_ART_Idioma' AND CMM_Valor = 'Computación' AND CMM_Referencia = 'COM' AND CMM_Activo = 1
GO
*/