CREATE OR ALTER VIEW [dbo].[VW_LISTADO_EMPLEADOS] AS
	SELECT 
		Emp.EMP_CodigoEmpleado codigoEmpleado,
		CONCAT(COALESCE(Usu.USU_Nombre,Emp.EMP_Nombre),' ',COALESCE(Usu.USU_PrimerApellido,Emp.EMP_PrimerApellido),' ',COALESCE(Usu.USU_SegundoApellido,Emp.EMP_SegundoApellido)) as nombre,
		(Select Cmm.CMM_Valor from ControlesMaestrosMultiples Cmm where Cmm.CMM_ControlId = Emp.EMP_CMM_PuestoId) as puesto,
		(Select Cmm.CMM_Valor from ControlesMaestrosMultiples Cmm where Cmm.CMM_ControlId = Emp.EMP_CMM_TipoEmpleadoId) as tipoEmpleado,
		Dep.DEP_Nombre as departamento,
		Suc.SUC_Nombre as sucursal
	FROM 
		Empleados Emp
		INNER JOIN Departamentos Dep ON Dep.DEP_DepartamentoId = Emp.EMP_DEP_DepartamentoId
		INNER JOIN Sucursales Suc ON Suc.SUC_SucursalId = Emp.EMP_SUC_SucursalId
		LEFT JOIN Usuarios Usu ON Usu.USU_UsuarioId = Emp.EMP_USU_UsuarioId
	WHERE 
		EMP_Activo = 1
GO