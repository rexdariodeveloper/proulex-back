

CREATE OR ALTER VIEW [dbo].[VW_LISTADO_PEDIDOS] AS

SELECT
	PED_Codigo AS "Código",
	FORMAT(PED_Fecha,'dd/MM/yyyy') AS "Fecha",
	Origen.ALM_Nombre AS "Origen",
	CEDIS.ALM_Nombre AS "CEDIS",
	CMM_Valor AS "Estatus",
	USUA_USU_UsuarioId AS usuarioId
FROM Pedidos
INNER JOIN Localidades LocOrigen ON LocOrigen.LOC_LocalidadId = PED_LOC_LocalidadOrigenId
INNER JOIN Almacenes Origen ON Origen.ALM_AlmacenId = LocOrigen.LOC_ALM_AlmacenId
INNER JOIN Localidades LocCEDIS ON LocCEDIS.LOC_LocalidadId = PED_LOC_LocalidadCEDISId
INNER JOIN Almacenes CEDIS ON CEDIS.ALM_AlmacenId = LocCEDIS.LOC_ALM_AlmacenId
INNER JOIN UsuariosAlmacenes ON USUA_ALM_AlmacenId = Origen.ALM_AlmacenId
INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PED_CMM_EstatusId

GO