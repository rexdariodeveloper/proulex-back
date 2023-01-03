CREATE OR ALTER VIEW [dbo].[VW_LISTADO_EMPLEADOS] AS
	SELECT 
		Emp.EMP_CodigoEmpleado codigoEmpleado,
		CONCAT(Emp.EMP_Nombre,' ',Emp.EMP_PrimerApellido,' ',Emp.EMP_SegundoApellido) as nombre,
		(Select Cmm.CMM_Valor from ControlesMaestrosMultiples Cmm where Cmm.CMM_ControlId = Emp.EMP_CMM_PuestoId) as puesto,
		(Select Cmm.CMM_Valor from ControlesMaestrosMultiples Cmm where Cmm.CMM_ControlId = Emp.EMP_CMM_TipoEmpleadoId) as tipoEmpleado,
		Dep.DEP_Nombre as departamento,
		Suc.SUC_Nombre as sucursal
	FROM 
		Empleados Emp
		INNER JOIN Departamentos Dep ON Dep.DEP_DepartamentoId = Emp.EMP_DEP_DepartamentoId
		INNER JOIN Sucursales Suc ON Suc.SUC_SucursalId = Emp.EMP_SUC_SucursalId
	WHERE 
		EMP_Activo = 1
GO