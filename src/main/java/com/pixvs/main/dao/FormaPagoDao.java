package com.pixvs.main.dao;

import com.pixvs.main.models.FormaPago;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.main.models.projections.FormaPago.FormaPagoListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FormaPagoDao extends CrudRepository<FormaPago, String> {

    FormaPago findById(Integer id);

    List<FormaPagoListadoProjection> findProjectedListadoAllByActivoTrue();
    List<FormaPagoComboProjection> findProjectedComboAllByActivoTrue();

    @Modifying
    @Query(value = "UPDATE FormaPago SET FP_Activo = :activo WHERE FP_FormaPagoId = :id", nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}
