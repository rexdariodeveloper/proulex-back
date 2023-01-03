SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_LISTADO_CUENTAS_BANCARIAS] AS

SELECT 
	BAC_CuentaId id, BAC_Codigo codigo, BAC_Descripcion descripcion, MON_Nombre moneda, BAN_Nombre banco, BAC_Activo activo, BAC_FechaCreacion fechaCreacion
FROM 
	BancosCuentas 
	INNER JOIN Monedas ON BAC_MON_MonedaId = MON_MonedaId 
	INNER JOIN Bancos ON BAC_BAN_BancoId = BAN_BancoId
GO