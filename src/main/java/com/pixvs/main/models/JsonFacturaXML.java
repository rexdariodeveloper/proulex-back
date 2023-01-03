package com.pixvs.main.models;

import java.util.List;

public class JsonFacturaXML {

    private Integer id;
    private DatosFactura datosFactura;
    private DatosFacturaProveedor proveedor;
    private List<DatosFacturaConcepto> conceptos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DatosFactura getDatosFactura() {
        return datosFactura;
    }

    public void setDatosFactura(DatosFactura datosFactura) {
        this.datosFactura = datosFactura;
    }

    public DatosFacturaProveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(DatosFacturaProveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<DatosFacturaConcepto> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<DatosFacturaConcepto> conceptos) {
        this.conceptos = conceptos;
    }
}
