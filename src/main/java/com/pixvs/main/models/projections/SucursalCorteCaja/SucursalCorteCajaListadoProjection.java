package com.pixvs.main.models.projections.SucursalCorteCaja;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.SucursalCorteCaja;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {SucursalCorteCaja.class})
public interface SucursalCorteCajaListadoProjection {

    public Integer getId();
    public String getSede();
    public String getCodigo();
    public String getPlantel();
    public String getUsuario();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    public Date getFechaInicio();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    public Date getFechaFin();
    public BigDecimal getTotal();
    public String getEstatus();
    public Integer getSucursalId();
    public Integer getUsuarioId();
}