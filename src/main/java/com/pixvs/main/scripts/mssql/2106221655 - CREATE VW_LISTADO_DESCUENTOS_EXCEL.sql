SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_DESCUENTOS_EXCEL]
AS
	Select 
	PADESC_Codigo as codigo,
	PADESC_Concepto as concepto,
	PADESC_PorcentajeDescuento as porcentajeDescuento,
	PADESC_FechaInicio as fechaInicio,
	PADESC_FechaFin as fechaFin,
	CASE
	WHEN PADESC_FechaFin < GETDATE() and PADESC_Activo = 1 THEN 'Inactivo'
	WHEN PADESC_Activo = 1 THEN 'Activo'
	ELSE 'Inactivo'
	END as activo
	from PADescuentos
GO