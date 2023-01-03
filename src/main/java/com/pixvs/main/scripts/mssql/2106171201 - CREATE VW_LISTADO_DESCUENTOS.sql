SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_DESCUENTOS]
AS
	Select 
	PADESC_DescuentoId as id,
	PADESC_Codigo as codigo,
	PADESC_Concepto as concepto,
	PADESC_PorcentajeDescuento as porcentajeDescuento,
	PADESC_FechaInicio as fechaInicio,
	PADESC_FechaFin as fechaFin,
	CASE
	WHEN PADESC_FechaFin < GETDATE() and PADESC_Activo = 1 THEN CAST(0 as bit)
	ELSE PADESC_Activo
	END as activo
	from PADescuentos
GO