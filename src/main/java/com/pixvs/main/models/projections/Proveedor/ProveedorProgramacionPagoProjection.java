package com.pixvs.main.models.projections.Proveedor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaProgramacionPagoProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Projection(types = {Proveedor.class})
public interface ProveedorProgramacionPagoProjection {

    Integer getId();
    String getNombre();
    String getCodigo();
    String getRfc();
    default List<CXPFacturaProgramacionPagoProjection> getFacturasProgramacion(){
//        List<CXPFacturaProgramacionPagoProjection> facturas = new ArrayList<>();
//
//        for(CXPFacturaProgramacionPagoProjection factura : getFacturas()){
//            if(factura.getEstatus().getId().intValue() == ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_EN_PROCESO){
//                facturas.add(factura);
//            }
//        }
//
//        return facturas;
        return new ArrayList<>();
    }

//    @JsonIgnore
//    List<CXPFacturaProgramacionPagoProjection> getFacturas();

}
