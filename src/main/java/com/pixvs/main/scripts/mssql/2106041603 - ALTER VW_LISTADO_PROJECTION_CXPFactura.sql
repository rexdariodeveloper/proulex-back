
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_PROJECTION_CXPFactura] AS

    SELECT
        CXPF_CXPFacturaId AS id,
        PRO_Nombre AS proveedorNombre,
        PRO_RFC AS proveedorRfc,
        CXPF_CodigoRegistro AS codigoRegistro,
        CXPF_MontoRegistro AS montoRegistro,
        CXPF_FechaRegistro AS fechaRegistro,
        DATEADD(DAY,CXPF_DiasCredito,CXPF_FechaRegistro) AS fechaVencimiento,
        CASE COALESCE(totalOCs,0) WHEN 1 THEN codigoOC WHEN 0 THEN '-' ELSE 'Múltiple' END AS ordenCompraTexto,
        CXPF_FechaReciboRegistro AS fechaReciboRegistro,
        CAST(1 AS bit) AS relacionada,
        COALESCE(evidencia,'[]') AS evidenciaStr,
        '{
            "id": ' + CAST(FacturaPDF.ARC_ArchivoId AS varchar) + ',
            "nombreOriginal": "' + FacturaPDF.ARC_NombreOriginal + '",
            "tipo": null,
            "nombreFisico": null,
            "rutaFisica": null,
            "publico": null,
            "activo": true,
            "creadoPor": null,
            "fechaCreacion": ' + CAST(FacturaPDF.ARC_FechaCreacion AS varchar) + '
        }' AS facturaPDFStr,
        '{
            "id": ' + CAST(FacturaXML.ARC_ArchivoId AS varchar) + ',
            "nombreOriginal": "' + FacturaXML.ARC_NombreOriginal + '",
            "tipo": null,
            "nombreFisico": null,
            "rutaFisica": null,
            "publico": null,
            "activo": true,
            "creadoPor": null,
            "fechaCreacion": ' + CAST(FacturaXML.ARC_FechaCreacion AS varchar) + '
        }' AS facturaXMLStr,

        CXPF_CMM_EstatusId AS estatusId,
        PRO_ProveedorId AS proveedorId,
        nombresSucursales AS sedeNombre,
        codigosRecibos AS codigoRecibo
    FROM CXPFacturas
    INNER JOIN Proveedores ON PRO_ProveedorId = CXPF_PRO_ProveedorId
    LEFT JOIN (
        SELECT
            id,
            COUNT(*) AS totalOCs,
            STRING_AGG(codigoOC,', ') AS codigoOC,
            STRING_AGG(nombreSucursal,', ') AS nombresSucursales,
            STRING_AGG(codigosRecibos,', ') AS codigosRecibos
        FROM(
            SELECT
                id,
                codigoOC,
                nombreSucursal,
                STRING_AGG(codigoRecibo,', ') AS codigosRecibos
            FROM(
                SELECT
                    CXPF_CXPFacturaId AS id,
                    OC_Codigo AS codigoOC,
                    SUC_Nombre AS nombreSucursal,
                    OCR_CodigoRecibo AS codigoRecibo
                FROM CXPFacturas
                INNER JOIN CXPFacturasDetalles ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
                INNER JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId
                INNER JOIN OrdenesCompra ON OC_OrdenCompraId = OCR_OC_OrdenCompraId
                INNER JOIN Almacenes ON ALM_AlmacenId = OC_ALM_RecepcionArticulosAlmacenId
                INNER JOIN Sucursales ON ALM_SUC_SucursalId = SUC_SucursalId
                GROUP BY CXPF_CXPFacturaId, OC_Codigo, SUC_Nombre, OCR_CodigoRecibo
            ) CXPFOCCodigoRecibos
            GROUP BY id, codigoOC, nombreSucursal
        ) CXPFOCCodigo
        GROUP BY id
    ) CXPFOC ON CXPFOC.id = CXPF_CXPFacturaId
    LEFT JOIN(

        SELECT
            id,
            '[' + STRING_AGG(evidencia,',') + ']' AS evidencia
        FROM(
            SELECT
                CXPF_CXPFacturaId AS id,
                '{
                    "id": ' + CAST(Evidencia.ARC_ArchivoId AS varchar) + ',
                    "nombreOriginal": "' + Evidencia.ARC_NombreOriginal + '",
                    "tipo": null,
                    "nombreFisico": null,
                    "rutaFisica": null,
                    "publico": null,
                    "activo": true,
                    "creadoPor": null,
                    "fechaCreacion": ' + CAST(Evidencia.ARC_FechaCreacion AS varchar) + '
                }' AS evidencia
            FROM CXPFacturas
            INNER JOIN CXPFacturasDetalles ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
            INNER JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId
            LEFT JOIN OrdenesCompraRecibosEvidencia ON OCRE_OCR_OrdenCompraReciboId = OCR_OrdenCompraReciboId
            LEFT JOIN Archivos Evidencia ON Evidencia.ARC_ArchivoId = OCRE_ARC_ArchivoId AND Evidencia.ARC_Activo = 1
        ) OCSinAgrupar
        GROUP BY id
    ) CXPFEvidencia ON CXPF_CXPFacturaId = CXPFEvidencia.id
    LEFT JOIN Archivos FacturaPDF ON FacturaPDF.ARC_ArchivoId = CXPF_ARC_FacturaPDFId AND FacturaPDF.ARC_Activo = 1
    LEFT JOIN Archivos FacturaXML ON FacturaXML.ARC_ArchivoId = CXPF_ARC_FacturaXMLId AND FacturaXML.ARC_Activo = 1

GO