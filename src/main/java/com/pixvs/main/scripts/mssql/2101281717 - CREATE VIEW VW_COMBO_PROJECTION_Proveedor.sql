-- ============================================= 
-- Author:		Angel Daniel Hernández Silva
-- Create date: 2021/01/28
-- Description:	Vista VW_COMBO_PROJECTION_Proveedor
-- --------------------------------------------- 

CREATE OR ALTER VIEW [dbo].[VW_COMBO_PROJECTION_Proveedor] AS

SELECT
	PRO_ProveedorId AS id,
	PRO_Codigo AS codigo,
	PRO_Nombre AS nombre,
	PRO_RFC AS rfc,
	COALESCE(PRO_Domicilio,'') + ', ' + COALESCE(PRO_Colonia,'') + ', ' + COALESCE(PRO_Ciudad,'') + ', ' + COALESCE(EST_Nombre,'') + ', ' + COALESCE(PAI_Nombre,'') AS domicilio,
	PRO_Cp AS cp,
	PRO_Telefono AS telefono,
	PRO_DiasPlazoCredito AS diasPlazoCredito,
	'{
		"id": ' + CAST(MON_MonedaId AS varchar(MAX)) + ',
		"nombre": "' + MON_Nombre + '",
		"codigo": "' + MON_Codigo + '"
	}' AS monedaStr,
	'{
		"id": ' + CAST(PROC_ProveedorContactoId AS varchar(MAX)) + ',
		"nombre": "' + PROC_Nombre + '",
		"primerApellido": "' + PROC_PrimerApellido + '",
		"segundoApellido": "' + COALESCE(' ' + PROC_SegundoApellido,'') + '",
		"nombreCompleto": "' + PROC_Nombre + ' ' + PROC_PrimerApellido + COALESCE(' ' + PROC_SegundoApellido,'') + '",
		"predeterminado": ' + CAST(PROC_Predeterminado AS varchar(MAX)) + '
	}' AS contactoPredeterminadoStr,
	'[' + COALESCE(STRING_AGG('{
		"id": ' + CAST(PROFP_ProveedorFormaPagoId AS varchar(MAX)) + ',
		"referencia": "' + PROFP_Referencia + '"
	}',','),'') + ']' AS formasPagoStr,
	PRO_Activo AS activo
FROM Proveedores
LEFT JOIN ProveedoresFormasPagos ON PROFP_PRO_ProveedorId = PRO_ProveedorId
LEFT JOIN ProveedoresContactos ON PROC_PRO_ProveedorId = PRO_ProveedorId AND PROC_Predeterminado = 1
LEFT JOIN Estados ON EST_EstadoId = PRO_EST_EstadoId
LEFT JOIN Paises ON PAI_PaisId = PRO_PAI_PaisId
LEFT JOIN Monedas ON MON_MonedaId = PRO_MON_MonedaId

GROUP BY
	PRO_ProveedorId, PRO_Codigo, PRO_Nombre, PRO_RFC,
	PRO_Domicilio, PRO_Colonia, PRO_Ciudad, EST_Nombre, PAI_Nombre,
	PRO_Cp, PRO_Telefono, PRO_DiasPlazoCredito,
	MON_MonedaId, MON_Nombre, MON_Codigo,
	PROC_ProveedorContactoId, PROC_Nombre, PROC_PrimerApellido, PROC_SegundoApellido, PROC_Predeterminado,
	PRO_Activo

GO