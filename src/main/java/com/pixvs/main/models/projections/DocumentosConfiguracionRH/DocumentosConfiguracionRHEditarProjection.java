package com.pixvs.main.models.projections.DocumentosConfiguracionRH;

import com.pixvs.main.models.DocumentosConfiguracionRH;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Rene Carrillo on 09/03/2022.
 */
@Projection(types = {DocumentosConfiguracionRH.class})
public interface DocumentosConfiguracionRHEditarProjection {
    Integer getId();
    Integer getTipoProcesoRHId();
    ControlMaestroMultipleComboProjection getTipoProcesoRH();
    Integer getTipoDocumentoId();
    ControlMaestroMultipleComboProjection getTipoDocumento();
    Integer getTipoContratoId();
    ControlMaestroMultipleComboProjection getTipoContrato();
    Integer getTipoOpcionId();
    ControlMaestroMultipleComboProjection getTipoOpcion();
    Integer getTipoVigenciaId();
    ControlMaestroMultipleComboProjection getTipoVigencia();
    Integer getTipoTiempoId();
    ControlMaestroMultipleComboProjection getTipoTiempo();
    BigDecimal getVigenciaCantidad();
}
