SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ====================================================
-- Author:		Javier Elías
-- Create date: 29/09/2022
-- Description:	Vista para obtener el reporte de Facturas CXP
-- ====================================================
CREATE OR ALTER VIEW [dbo].[VW_ReporteFacturasCXP]
AS
	SELECT CXPF_CXPFacturaId AS id,
		   CONVERT(DATE, CXPF_FechaRegistro) AS fecha,
		   PRO_ProveedorId AS proveedorId,
		   TRIM(PRO_Nombre) AS proveedor,
		   TRIM(CXPF_CodigoRegistro) AS folio,
		   CXPF_MontoRegistro AS monto,
		   MON_MonedaId AS monedaId,
		   MON_Nombre AS moneda,
		   CXPF_CMM_EstatusId AS estatusId,
		   CMM_Valor AS estatus,
		   CXPF_ARC_FacturaXMLId AS XMLId,
		   CXPF_ARC_FacturaPDFId AS PDFId,
		   ISNULL(PRO_RFC, 'NA') + '/' + CONVERT(VARCHAR(100), ISNULL(CXPF_UUID, ISNULL(SUBSTRING(xml.ARC_NombreFisico, 0, CHARINDEX('.', xml.ARC_NombreFisico)), SUBSTRING(pdf.ARC_NombreFisico, 0, CHARINDEX('.', pdf.ARC_NombreFisico))))) AS PathArchivo,
		   CONVERT(VARCHAR(100), ISNULL(CXPF_UUID, ISNULL(SUBSTRING(xml.ARC_NombreFisico, 0, CHARINDEX('.', xml.ARC_NombreFisico)), SUBSTRING(pdf.ARC_NombreFisico, 0, CHARINDEX('.', pdf.ARC_NombreFisico))))) AS NombreArchivo
	FROM CXPFacturas
		 LEFT JOIN Proveedores ON CXPF_PRO_ProveedorId = PRO_ProveedorId AND PRO_Activo = 1
		 LEFT JOIN Monedas ON ISNULL(CXPF_MON_MonedaId, PRO_MON_MonedaId) = MON_MonedaId
		 INNER JOIN ControlesMaestrosMultiples ON CXPF_CMM_EstatusId = CMM_ControlId
		 LEFT JOIN Archivos AS xml ON CXPF_ARC_FacturaXMLId = xml.ARC_ArchivoId AND xml.ARC_Activo = 1
		 LEFT JOIN Archivos AS pdf ON CXPF_ARC_FacturaPDFId = pdf.ARC_ArchivoId AND pdf.ARC_Activo = 1
	WHERE CXPF_CMM_EstatusId != 1599 -- No Borrado
GO