/**
 * Created by Angel Daniel Hernández Silva on 06/10/2022.
 */

UPDATE CXPFacturas
SET CXPF_MON_MonedaId = 1
FROM CXPFacturas
INNER JOIN CXPSolicitudesPagosRH ON CPXSPRH_CXPF_CXPFacturaId = CXPF_CXPFacturaId
GO