CREATE OR ALTER VIEW [dbo].[VW_LISTADO_REQUISICIONES] AS

SELECT
	REQ_Codigo AS "Código de requisición",
	REQ_Fecha AS "Fecha de requisición",
	SUC_Nombre + ' - ' + DEP_Nombre AS "Sede - Departamento",
	USU_Nombre + COALESCE(' ' + USU_PrimerApellido + COALESCE(' ' + USU_SegundoApellido,''),'') AS "Usuario solicitante",
	CMM_Valor AS "Estado requisición"
FROM Requisiciones 
INNER JOIN Almacenes ON ALM_AlmacenId = REQ_ALM_AlmacenId
INNER JOIN Sucursales ON SUC_SucursalId = ALM_SUC_SucursalId
INNER JOIN Departamentos ON DEP_DepartamentoId = REQ_DEP_DepartamentoId
INNER JOIN Usuarios ON USU_UsuarioId = REQ_USU_CreadoPorId
INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = REQ_CMM_EstadoRequisicionId
WHERE REQ_CMM_EstadoRequisicionId != 2000198

GO