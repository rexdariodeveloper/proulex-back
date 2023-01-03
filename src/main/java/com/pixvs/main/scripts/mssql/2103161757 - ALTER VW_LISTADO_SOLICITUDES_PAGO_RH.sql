CREATE OR ALTER VIEW [dbo].[VW_LISTADO_SOLICITUDES_PAGO_RH] AS
	SELECT 
	rh.CPXSPRH_CXPSolicitudPagoRhId as id,
	rh.CPXSPRH_Codigo as codigo,
	rh.CPXSPRH_FechaCreacion as fechaCreacion,
	CONCAT(emp.EMP_CodigoEmpleado,'-',emp.EMP_Nombre,' ',emp.EMP_PrimerApellido,' ',emp.EMP_SegundoApellido) as nombre,
	cmm.CMM_Valor as tipoPago,
	CASE rh.CPXSPRH_CMM_TipoPagoId
	WHEN 2000356 THEN caja.CPXSPRHID_CantidadRetirar --Retir caja ahorro
	WHEN 2000357 THEN rh.CPXSPRH_Monto--Pensión alimenticia
	WHEN 2000358 THEN 
	(	Select 
		SUM(CASE WHEN INCD.CPXSPRHID_CMM_TipoId is not null THEN (INCD.CPXSPRHID_SalarioDiario * INCD.CPXSPRHID_Porcentaje/100 * INCD.CPXSPRHID_Dias) ELSE 0 END) - SUM(CASE WHEN INCD.CPXSPRHID_CMM_TipoMovimientoId is not null THEN INCD.CPXSPRHID_SalarioDiario ELSE 0 END)
		from CXPSolicitudesPagosRHIncapacidades INC
		Left Join CXPSolicitudesPagosRHIncapacidadesDetalles INCD on INCD.CPXSPRHID_CPXSPRHI_IncapacidadId = INC.CPXSPRHI_CXPSolicitudPagoRhIncapacidadId 
		WHERE INC.CPXSPRHI_CPXSPRH_CXPSolicitudPagoRhId=rh.CPXSPRH_CXPSolicitudPagoRhId
		GROUP BY INC.CPXSPRHI_CPXSPRH_CXPSolicitudPagoRhId
	)--incapacidad
	ELSE rh.CPXSPRH_Monto END as monto,
	(Select CMM_Valor from ControlesMaestrosMultiples where CMM_ControlId = rh.CPXSPRH_CMM_EstatusId) as estatus,
	CONCAT(usu.USU_Nombre,' ',usu.USU_PrimerApellido,' ',usu.USU_SegundoApellido) as usuarioCreador,
	suc.SUC_SucursalId as sedeId
	FROM CXPSolicitudesPagosRH rh
	LEFT JOIN Empleados emp on emp.EMP_EmpleadoId = rh.CPXSPRH_EMP_EmpleadoId
	LEFT JOIN ControlesMaestrosMultiples cmm on cmm.CMM_ControlId = rh.CPXSPRH_CMM_TipoPagoId
	LEFT JOIN CXPSolicitudesPagosRHRetiroCajaAhorro caja on caja.CPXSPRHRCA_CPXSPRH_CXPSolicitudPagoRhId = rh.CPXSPRH_CXPSolicitudPagoRhId
	LEFT JOIN CXPSolicitudesPagosRHPensionesAlimenticias pension on pension.CPXSPRHPA_CPXSPRH_CXPSolicitudPagoRhId = rh.CPXSPRH_CXPSolicitudPagoRhId
	LEFT JOIN Sucursales suc on suc.SUC_SucursalId = rh.CPXSPRH_SUC_SucursalId
	LEFT JOIN Usuarios usu on usu.USU_UsuarioId = rh.CPXSPRH_USU_CreadoPorId
GO