package com.pixvs.spring.dao;

import com.pixvs.spring.models.AlertaConfig;
import com.pixvs.spring.models.EmpresaDiaNoLaboral;
import com.pixvs.spring.models.projections.AlertaConfig.AlertaConfigComboProjection;
import com.pixvs.spring.models.projections.EmpresaDiaNoLaboral.EmpresaDiaNoLaboralEditarProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface EmpresaDiaNoLaboralDao extends CrudRepository<EmpresaDiaNoLaboral, String> {

    @Query(value = "Select \n" +
            "EMPDNL_EmpresaDiaNoLaboralId as id,\n" +
            "EMPDNL_Fecha as fecha,\n" +
            "EMPDNL_Descripcion as descripcion,\n" +
            "EMPDNL_Activo as activo\n" +
            "from EmpresaDiasNoLaborales\n" +
            "WHERE EMPDNL_Activo=1", nativeQuery = true)
    List<EmpresaDiaNoLaboralEditarProjection> findAllByActivoIsTrue();
}
