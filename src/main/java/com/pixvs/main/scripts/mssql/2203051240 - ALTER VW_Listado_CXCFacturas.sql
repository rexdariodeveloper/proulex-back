CREATE OR ALTER VIEW [dbo].[VW_Listado_CXCFacturas]
AS
SELECT id,
       codigoRegistro,
       clienteNombre,
       clienteRFC,
       fecha,
       monedaNombre,
       SUM(monto) AS monto
FROM
(
    SELECT CXCF_FacturaId AS id,
           CXCF_Folio AS codigoRegistro,
           COALESCE(CLI_Nombre, '') AS clienteNombre,
           COALESCE(CLI_RFC, '') AS clienteRFC,
           CXCF_Fecha AS fecha,
           MON_Nombre AS monedaNombre,
		   CXCFD_Importe - CXCFD_Descuento + CXCFDI_Importe AS monto
    FROM CXCFacturas
         LEFT JOIN Clientes ON CLI_ClienteId = CXCF_CLI_ClienteId
         INNER JOIN Monedas ON MON_MonedaId = CXCF_MON_MonedaId
         INNER JOIN CXCFacturasDetalles ON CXCFD_CXCF_FacturaId = CXCF_FacturaId
		 CROSS APPLY
		 (
				SELECT SUM(CXCFDI_Importe) AS CXCFDI_Importe FROM CXCFacturasDetallesImpuestos WHERE CXCFDI_CXCFD_FacturaDetalleId = CXCFD_FacturaDetalleId
		 ) AS impuestos
) CXCFD
GROUP BY id,
         codigoRegistro,
         clienteNombre,
         clienteRFC,
         fecha,
         monedaNombre
GO