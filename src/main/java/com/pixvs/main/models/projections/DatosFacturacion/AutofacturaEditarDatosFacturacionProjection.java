package com.pixvs.main.models.projections.DatosFacturacion;

import com.pixvs.main.models.DatosFacturacion;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Municipio.MunicipioComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.models.projections.SATRegimenFiscal.SATRegimenFiscalComboProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {DatosFacturacion.class})
public interface AutofacturaEditarDatosFacturacionProjection {

    Integer getId();

    int getTipoPersonaId();

    ControlMaestroMultipleComboSimpleProjection getTipoPersona();

    String getRfc();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();

    String getRazonSocial();

    String getRegistroIdentidadFiscal();

    Integer getRegimenFiscalId();

    SATRegimenFiscalComboProjection getRegimenFiscal();

    String getCalle();

    String getNumeroExterior();

    String getNumeroInterior();

    String getColonia();

    String getCp();

    Integer getPaisId();

    PaisComboProjection getPais();

    Integer getEstadoId();

    EstadoComboProjection getEstado();

    Integer getMunicipioId();

    MunicipioComboProjection getMunicipio();

    String getCiudad();

    String getCorreoElectronico();

    String getTelefonoFijo();

    String getTelefonoMovil();

    String getTelefonoTrabajo();

    String getTelefonoTrabajoExtension();

    String getTelefonoMensajeriaInstantanea();
}