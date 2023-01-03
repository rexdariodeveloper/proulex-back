package com.pixvs.main.dao;

import com.pixvs.main.models.ControlMaestroMultipleDatosAdicionales;
import com.pixvs.main.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboResponsablesProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 05/11/2021.
 */
public interface ControlMaestroMultipleDatosAdicionalesDao extends CrudRepository<ControlMaestroMultipleDatosAdicionales, String> {

    // Modelo
    ControlMaestroMultipleDatosAdicionales findById(Integer id);

    // ComboResponsablesProjection
    List<ControlMaestroMultipleComboResponsablesProjection> findProjectedComboResponsablesAllByControlAndActivoIsTrueOrderBySistemaDescValorAsc(String control);

}
