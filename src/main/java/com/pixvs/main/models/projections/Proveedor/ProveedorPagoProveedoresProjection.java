package com.pixvs.main.models.projections.Proveedor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaPagoProveedoresProjection;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaProgramacionPagoProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 18/09/2020.
 */
@Projection(types = {Proveedor.class})
public interface ProveedorPagoProveedoresProjection {

    Integer getId();
    String getNombre();
    String getCodigo();
    String getRfc();
    @Value("0")
    BigDecimal getMontoProgramado();

//    List<CXPFacturaPagoProveedoresProjection> getFacturas();

}
