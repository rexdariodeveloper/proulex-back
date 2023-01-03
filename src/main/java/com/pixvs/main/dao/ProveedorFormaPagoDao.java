package com.pixvs.main.dao;

import com.pixvs.main.models.ProveedorFormaPago;
import com.pixvs.main.models.projections.ProveedorFormaPago.ProveedorFormaPagoComboProjection;
import com.pixvs.main.models.projections.ProveedorFormaPago.ProveedorFormaPagoEditarProjection;
import com.pixvs.main.models.projections.ProveedorFormaPago.ProveedorFormaPagoListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 05/11/2020.
 */
public interface ProveedorFormaPagoDao extends CrudRepository<ProveedorFormaPago, String> {

    List<ProveedorFormaPagoListadoProjection> findProjectedListadoAllBy();

    List<ProveedorFormaPagoComboProjection> findProjectedComboAllByActivoTrue();

    ProveedorFormaPagoEditarProjection findProjectedEditarById(Integer id);

    ProveedorFormaPago findById(Integer id);


    @Modifying
    @Query(value = "UPDATE ProveedorFormaPagos SET PROFP_Activo = :activo WHERE PROFP_ProveedorFormaPagoId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}