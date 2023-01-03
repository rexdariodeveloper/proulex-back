SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =======================================================
-- Author:		Ángel Daniel Hernández Silva
-- Create date: 20/04/2022
-- Modified by:		Javier Elías
-- Modified date: 22/08/2022
-- Description:	VIEWs VW_OrdenesVentaCancelaciones y 
--						VW_OrdenesVentaCancelaciones_ExcelListado
-- =======================================================
CREATE OR ALTER VIEW VW_OrdenesVentaCancelaciones
AS
SELECT id,
       tipoMovimiento,
	   codigo,
       fechaCancelacion,
       motivoCancelacionId,
       motivoCancelacion,
       banco,
       beneficiario,
       numeroCuenta,
       clabe,
       telefonoContacto,
       importeReembolsar,
       tipoCancelacionId,
       tipoCancelacion,
       estatusId,
       estatus,
       ordenVentaId,
       ordenVenta,
       SUM(montoDetalle) AS monto,
       sucursalId,
       sucursal,
       fechaCreacion,
       fechaModificacion,
       creadoPorId,
       creadoPor,
       modificadoPorId,
       modificadoPorNombreCompleto,
       archivos
FROM
(
    SELECT OVC_OrdenVentaCancelacionId AS id,
           tipoMovto.CMM_Valor AS tipoMovimiento,
		   OVC_Codigo AS codigo,
           ISNULL(OVC_FechaDevolucion, OVC_FechaCancelacion) AS fechaCancelacion,
           ISNULL(OVC_CMM_MotivoDevolucionId, OVC_CMM_MotivoCancelacionId) AS motivoCancelacionId,
           MotivoCancelacion.CMM_Valor AS motivoCancelacion,
           OVC_Banco AS banco,
           OVC_Beneficiario AS beneficiario,
           OVC_NumeroCuenta AS numeroCuenta,
           OVC_CLABE AS clabe,
           OVC_TelefonoContacto AS telefonoContacto,
           OVC_ImporteReembolsar AS importeReembolsar,
           OVC_CMM_TipoCancelacionId AS tipoCancelacionId,
           TipoCancelacion.CMM_Valor AS tipoCancelacion,
           OVC_CMM_EstatusId AS estatusId,
           Estatus.CMM_Valor AS estatus,
           OV_OrdenVentaId AS ordenVentaId,
           OV_Codigo AS ordenVenta,
		   ( SELECT Total FROM fn_getTotal (OVD_Cantidad, OVD_Precio, OVD_Descuento, CASE WHEN OVD_IVAExento = 1 THEN 0 ELSE OVD_IVA END, OVD_IEPS, OVD_IEPSCuotaFija, NULL ) ) AS montoDetalle,
           SUC_SucursalId AS sucursalId,
           SUC_Nombre AS sucursal,
           OVC_FechaCreacion AS fechaCreacion,
           OVC_FechaModificacion AS fechaModificacion,
           OVC_USU_CreadoPorId AS creadoPorId,
           CONCAT(UsuarioCreador.USU_Nombre, ' '+UsuarioCreador.USU_PrimerApellido, ' '+UsuarioCreador.USU_SegundoApellido) AS creadoPor,
           OVC_USU_ModificadoPorId AS modificadoPorId,
           CONCAT(UsuarioModificador.USU_Nombre, ' '+UsuarioModificador.USU_PrimerApellido, ' '+UsuarioModificador.USU_SegundoApellido) AS modificadoPorNombreCompleto,
           COUNT(OVCA_OrdenVentaCancelacionArchivoId) AS archivos
    FROM OrdenesVentaCancelaciones
         INNER JOIN ControlesMaestrosMultiples AS tipoMovto ON OVC_CMM_TipoMovimientoId = tipoMovto.CMM_ControlId
		 INNER JOIN ControlesMaestrosMultiples AS MotivoCancelacion ON ISNULL(OVC_CMM_MotivoDevolucionId, OVC_CMM_MotivoCancelacionId) = MotivoCancelacion.CMM_ControlId
         INNER JOIN ControlesMaestrosMultiples AS TipoCancelacion ON TipoCancelacion.CMM_ControlId = OVC_CMM_TipoCancelacionId
         INNER JOIN ControlesMaestrosMultiples AS Estatus ON Estatus.CMM_ControlId = OVC_CMM_EstatusId
         INNER JOIN Usuarios AS UsuarioCreador ON UsuarioCreador.USU_UsuarioId = OVC_USU_CreadoPorId
         LEFT JOIN Usuarios AS UsuarioModificador ON UsuarioModificador.USU_UsuarioId = OVC_USU_ModificadoPorId
         INNER JOIN OrdenesVentaCancelacionesDetalles ON OVCD_OVC_OrdenVentaCancelacionId = OVC_OrdenVentaCancelacionId
         INNER JOIN OrdenesVentaDetalles ON OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
         INNER JOIN OrdenesVenta ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
         INNER JOIN Sucursales ON SUC_SucursalId = OV_SUC_SucursalId
         LEFT JOIN OrdenesVentaCancelacionesArchivos ON OVCA_OVC_OrdenVentaCancelacionId = OVC_OrdenVentaCancelacionId
    GROUP BY OVC_OrdenVentaCancelacionId,
             OVC_Codigo,
			 tipoMovto.CMM_Valor,
			 OVC_FechaDevolucion,
			 OVC_CMM_MotivoDevolucionId,
             OVC_FechaCancelacion,
             OVC_CMM_MotivoCancelacionId,
             MotivoCancelacion.CMM_Valor,
             OVC_Banco,
             OVC_Beneficiario,
             OVC_NumeroCuenta,
             OVC_CLABE,
             OVC_TelefonoContacto,
             OVC_ImporteReembolsar,
             OVC_CMM_TipoCancelacionId,
             TipoCancelacion.CMM_Valor,
             OVC_CMM_EstatusId,
             Estatus.CMM_Valor,
             OV_OrdenVentaId,
             OV_Codigo,
             OVD_Cantidad,
             OVD_Precio,
             OVD_Descuento,
             OVD_IVAExento,
             OVD_IVA,
             OVD_IEPS,
             OVD_IEPSCuotaFija,
             SUC_SucursalId,
             SUC_Nombre,
             OVC_FechaCreacion,
             OVC_FechaModificacion,
             OVC_USU_CreadoPorId,
             UsuarioCreador.USU_Nombre,
             UsuarioCreador.USU_PrimerApellido,
             UsuarioCreador.USU_SegundoApellido,
             OVC_USU_ModificadoPorId,
             UsuarioModificador.USU_Nombre,
             UsuarioModificador.USU_PrimerApellido,
             UsuarioModificador.USU_SegundoApellido
) AS MontosDetalles
GROUP BY id,
         tipoMovimiento,
		 codigo,
         fechaCancelacion,
         motivoCancelacionId,
         motivoCancelacion,
         banco,
         beneficiario,
         numeroCuenta,
         clabe,
         telefonoContacto,
         importeReembolsar,
         tipoCancelacionId,
         tipoCancelacion,
         estatusId,
         estatus,
         ordenVentaId,
         ordenVenta,
         sucursalId,
         sucursal,
         fechaCreacion,
         fechaModificacion,
         creadoPorId,
         creadoPor,
         modificadoPorId,
         modificadoPorNombreCompleto,
         archivos
GO

CREATE OR ALTER VIEW VW_OrdenesVentaCancelaciones_ExcelListado
AS
SELECT tipoMovimiento AS "Tipo Movimiento",
       codigo AS "Folio",
	   ordenVenta AS "Nota de venta",
       sucursal AS "Sede",
       fechaCancelacion AS "Fecha movimiento",
       importeReembolsar AS "Importe a reembolsar",
       creadoPor AS "Usuario",
       estatus AS "Estatus"
FROM VW_OrdenesVentaCancelaciones
GO