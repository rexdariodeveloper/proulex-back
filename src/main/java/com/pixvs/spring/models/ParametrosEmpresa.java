package com.pixvs.spring.models;

import java.util.List;

public class ParametrosEmpresa {

    public Integer id;

    public String nombre;

    public String rfc;

    public List<EmpresaDiaNoLaboral> diasNoLaborales;

    public List<EmpresaDiaNoLaboralFijo> diasNoLaboralesFijos;

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public List<EmpresaDiaNoLaboral> getDiasNoLaborales() {
        return diasNoLaborales;
    }

    public List<EmpresaDiaNoLaboralFijo> getDiasNoLaboralesFijos() {
        return diasNoLaboralesFijos;
    }
}
