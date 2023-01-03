/**
 * Created by Angel Daniel Hernández Silva on 21/10/2020.
 */

CREATE   VIEW VW_LISTADO_ARTICULOS_TIPOS AS

SELECT
	ARTT_Descripcion AS "Descripción" 
FROM ArticulosTipos;

CREATE   VIEW VW_LISTADO_ARTICULOS_SUBTIPOS AS

SELECT
	ARTST_Descripcion AS "Descripción" 
FROM ArticulosSubtipos;

CREATE VIEW VW_LISTADO_SUCURSALES AS

SELECT
	SUC_CodigoSucursal AS "Código",
	SUC_Nombre AS "Nombre",
	USU_Nombre + ' ' + USU_PrimerApellido + COALESCE(' ' + USU_SegundoApellido,'') AS "Responsable",
	SUC_PorcentajeComision AS "% Comisión",
	SUC_Telefono AS "Teléfono",
	SUC_Activo AS "Activo"
FROM Sucursales
INNER JOIN Usuarios ON USU_UsuarioId = SUC_USU_ResponsableId;

CREATE   VIEW VW_LISTADO_ARTICULOS AS

SELECT ART_CodigoArticulo AS "Código Artículo", ART_NombreArticulo AS "Nombre Artículo", ART_CodigoAlterno AS "Código Alterno", ART_NombreAlterno AS "Nombre Alterno", ART_DescripcionCorta AS "Descripción Corta", ART_Activo AS "Activo", ART_FechaCreacion AS "Fecha Creación" 
FROM Articulos;

CREATE   VIEW VW_LISTADO_ALMACENES AS

SELECT
	ALM_CodigoAlmacen AS "Código Almacén",
	ALM_Nombre AS "Nombre Almacén",
	Responsable.USU_Nombre + ' ' + Responsable.USU_PrimerApellido + COALESCE(' ' + Responsable.USU_SegundoApellido,'') AS "Responsable",
	SUC_Nombre AS "Sucursal",
	ALM_Telefono AS "Teléfono",
	ALM_Activo AS "Activo"
FROM Almacenes 
INNER JOIN Usuarios Responsable ON USU_UsuarioId = ALM_USU_ResponsableId
INNER JOIN Sucursales ON SUC_SucursalId = ALM_SUC_SucursalId;

CREATE   VIEW VW_LISTADO_DEPARTAMENTOS AS

SELECT DEP_Activo AS "Activo", DEP_Prefijo AS "Prefijo", DEP_Nombre AS "Nombre", DEP_Autoriza AS "Autoriza" 
FROM Departamentos;

CREATE   VIEW VW_LISTADO_ORDENES_COMPRA AS

SELECT
	OC_Codigo AS "Código",
	PRO_Nombre AS "Proveedor",
	PRO_RFC AS "RFC",
	OC_FechaOC AS "Fecha OC",
	CMM_Valor AS "Estatus" 
FROM OrdenesCompra
INNER JOIN Proveedores ON PRO_ProveedorId = OC_PRO_ProveedorId
INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = OC_CMM_EstatusId;

CREATE   VIEW [dbo].[VW_LISTADO_REQUISICIONES] AS

SELECT
	REQ_Codigo AS "Código de requisición",
	REQ_Fecha AS "Fecha de requisición",
	SUC_Nombre + ' - ' + DEP_Nombre AS "Sucursal - Departamento",
	USU_Nombre + COALESCE(' ' + USU_PrimerApellido + COALESCE(' ' + USU_SegundoApellido,''),'') AS "Usuario solicitante",
	CMM_Valor AS "Estado requisición"
FROM Requisiciones 
INNER JOIN Sucursales ON SUC_SucursalId = REQ_SUC_SucursalId
INNER JOIN Departamentos ON DEP_DepartamentoId = REQ_DEP_DepartamentoId
INNER JOIN Usuarios ON USU_UsuarioId = REQ_USU_CreadoPorId
INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = REQ_CMM_EstadoRequisicionId;