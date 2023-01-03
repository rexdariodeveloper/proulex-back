SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Rene Carrillo
-- Create date: 12/08/2022
-- Modify date:
-- Description: la funcion para obtener el contrato del empleado
-- Version 1.0.0
-- =============================================

CREATE OR ALTER FUNCTION [dbo].[fn_getFormatoContrato] (@empleadoContratoId INTEGER, @entidadId INTEGER)
RETURNS TABLE
AS
RETURN
(
	/* id del empleado contrato necesario*/
	SELECT e.EMP_EmpleadoId AS EmpleadoId,
		ent.ENT_Nombre AS Entidad,
		CONCAT('Contrato ', tipoContrato.CMM_Valor) AS TipoContrato,
		ec.EMPCO_Codigo AS FolioContrato,
		UPPER(CONCAT(e.EMP_Nombre, ' ', e.EMP_PrimerApellido, ' ', NULLIF(e.EMP_SegundoApellido, ''))) AS Nombre,
		(SELECT [dbo].[fn_titleCast](nacionalidad.CMM_Valor, 0) ) AS Nacionalidad,
		(SELECT [dbo].[getFechaLetra](e.EMP_FechaNacimiento, null)) as FechaNacimiento,
		(SELECT [dbo].[fn_titleCast](genero.CMM_Valor, 0)) AS Genero,
		(SELECT [dbo].[fn_titleCast](estadoCivil.CMM_Valor, 0)) AS EstadoCivil,
		(SELECT [dbo].[fn_titleCast](gradoEstudio.CMM_Valor, 0)) AS GradoEstudio,
		e.EMP_CURP AS Curp,
		e.EMP_RFC as Rfc,
		CONCAT(e.EMP_Domicilio, ' ', e.EMP_Colonia, ' ', e.EMP_Municipio, ' ', estado.EST_Nombre) AS Domicilio,
		d.DEP_Nombre AS Puesto,
		ec.EMPCO_IngresosAdicionales AS IngresosAdicionales,
		CONCAT(FORMAT(ec.EMPCO_SueldoMensual,'C', 'es-MX'), ' (', LOWER(CONCAT((dbo.[getNumeroLetra](ec.EMPCO_SueldoMensual,  'MONEDA', 'Peso', 'Pesos', 'es' )), ' M.N.')), ')') AS Sueldo,
		(SELECT dbo.[getFechaLetra](ec.EMPCO_FechaInicio, 2)) AS FechaInicio,
		(SELECT dbo.[getFechaLetra](ec.EMPCO_FechaFin, 2)) AS FechaFin,
		(SELECT [dbo].[fn_titleCast]( CONCAT(e.EMP_Nombre, ' ', e.EMP_PrimerApellido, ' ', NULLIF(e.EMP_SegundoApellido, '')) , 0) ) AS NombreFooter,
		CONCAT('Vigencia: ', (SELECT [dbo].[getFechaLetra](ec.EMPCO_FechaInicio, null)),' al ', (SELECT [dbo].[getFechaLetra](ec.EMPCO_FechaFin, null)))AS Vigencia,
		drh.ResposabilidadHabilidad AS Responsabilidades
	FROM EmpleadosContratos ec
		INNER JOIN Empleados e ON ec.EMPCO_EMP_EmpleadoId = e.EMP_EmpleadoId
		INNER JOIN Entidades ent ON @entidadId = ent.ENT_EntidadId
		INNER JOIN ControlesMaestrosMultiples tipoContrato ON ec.EMPCO_CMM_TipoContratoId = tipoContrato.CMM_ControlId
		INNER JOIN ControlesMaestrosMultiples nacionalidad ON e.EMP_CMM_NacionalidadId = nacionalidad.CMM_ControlId
		INNER JOIN ControlesMaestrosMultiples genero ON e.EMP_CMM_GeneroId = genero.CMM_ControlId
		INNER JOIN ControlesMaestrosMultiples estadoCivil ON e.EMP_CMM_EstadoCivilId = estadoCivil.CMM_ControlId
		INNER JOIN ControlesMaestrosMultiples gradoEstudio ON e.EMP_CMM_GradoEstudiosId = gradoEstudio.CMM_ControlId
		INNER JOIN Estados estado ON e.EMP_EST_EstadoId = estado.EST_EstadoId
		INNER JOIN Departamentos d ON ec.EMPCO_DEP_DepartamentoId = d.DEP_DepartamentoId
		LEFT JOIN (SELECT drh.DEPREHA_DEP_DepartamentoId, STRING_AGG(drh.Descripcion, '&#9;<br>') AS ResposabilidadHabilidad
					FROM (SELECT CONCAT(ROW_NUMBER() OVER(ORDER BY _drh.DEPREHA_DEP_DepartamentoId), '.- ', _drh.DEPREHA_Descripcion) AS Descripcion,
							_drh.DEPREHA_DEP_DepartamentoId
							FROM DepartamentoResposabilidadHabilidad _drh
						) AS drh
					GROUP BY drh.DEPREHA_DEP_DepartamentoId) AS drh ON d.DEP_DepartamentoId = drh.DEPREHA_DEP_DepartamentoId
	WHERE ec.EMPCO_EmpleadoContratoId = @empleadoContratoId
		AND ec.EMPCO_CMM_EstatusId = 2000950
)