SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =====================================================
-- Author:		Javier Elías
-- Create date: 25/08/2022
-- Modified date: 
-- Description:	Función para obtener el Formato de Solicitud
--						de Reembolso de una OV
-- =====================================================
CREATE OR ALTER FUNCTION [dbo].[fn_getFormatoReembolsoOV](@ovCancelacionId INT)
RETURNS TABLE
AS
RETURN
(
		SELECT OVC_OrdenVentaCancelacionId AS id,
			   CMM_Valor AS motivo,
			   SUC_Nombre AS sede,
			   FORMAT(OVC_FechaDevolucion, 'dd/MM/yyyy') AS fechaCancelacion,
			   ov.alumno AS alumno,
			   OVC_Banco AS banco,
			   OVC_Beneficiario AS beneficiario,
			   OVC_NumeroCuenta AS cuenta,
			   OVC_CLABE AS CLABE,
			   OVC_ImporteReembolsar AS importe,
			   OVC_TelefonoContacto AS telefono,
			   ov.codigo AS notaVenta,
			   SCC_Codigo AS corte
		FROM OrdenesVentaCancelaciones
			 INNER JOIN ControlesMaestrosMultiples ON OVC_CMM_MotivoDevolucionId = CMM_ControlId
			 INNER JOIN
			 (
				SELECT OV_Codigo AS codigo,
					   OV_SUC_SucursalId AS sucursalId,
					   OV_SCC_SucursalCorteCajaId AS corteId,
					   OVCD_OVC_OrdenVentaCancelacionId AS cancelacionId,
					   CONCAT_WS(' ', COALESCE(ALU_PrimerApellido, ''), COALESCE(ALU_SegundoApellido, ''), COALESCE(ALU_Nombre, '')) AS alumno
				FROM OrdenesVenta
					 INNER JOIN OrdenesVentaDetalles ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
					 INNER JOIN OrdenesVentaCancelacionesDetalles ON OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
					 LEFT JOIN Inscripciones ON OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
					 LEFT JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
				GROUP BY OV_Codigo,
						 OV_SUC_SucursalId,
						 OV_SCC_SucursalCorteCajaId,
						 OVCD_OVC_OrdenVentaCancelacionId,
						 CONCAT_WS(' ', COALESCE(ALU_PrimerApellido, ''), COALESCE(ALU_SegundoApellido, ''), COALESCE(ALU_Nombre, ''))
			 ) AS ov ON ov.cancelacionId = OVC_OrdenVentaCancelacionId
			 INNER JOIN Sucursales ON ov.sucursalId = SUC_SucursalId
			 INNER JOIN SucursalesCortesCajas ON ov.corteId = SCC_SucursalCorteCajaId
		WHERE OVC_OrdenVentaCancelacionId = @ovCancelacionId
)