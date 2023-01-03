package com.pixvs.spring.dao;

import com.pixvs.spring.models.EmpresaDiaNoLaboral;
import com.pixvs.spring.models.EmpresaDiaNoLaboralFijo;
import com.pixvs.spring.models.projections.EmpresaDiaNoLaboral.EmpresaDiaNoLaboralEditarProjection;
import com.pixvs.spring.models.projections.EmpresaDiaNoLaboralFijo.EmpresaDiaNoLaboralFijoEditarProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmpresaDiaNoLaboralFijoDao extends CrudRepository<EmpresaDiaNoLaboralFijo, String> {

    @Query(value = "Select \n" +
            "EMPDNLF_EmpresaDiaNoLaboralId as id,\n" +
            "EMPDNLF_Dia as dia,\n" +
            "EMPDNLF_Mes as mes,\n" +
            "EMPDNLF_Descripcion as descripcion,\n" +
            "EMPDNLF_Activo as activo\n" +
            "FROM EmpresaDiasNoLaboralesFijos\n" +
            "WHERE EMPDNLF_Activo = 1", nativeQuery = true)
    List<EmpresaDiaNoLaboralFijoEditarProjection> findAllByActivoIsTrue();
}
