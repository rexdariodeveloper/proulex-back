package com.pixvs.main.dao;

import com.pixvs.main.models.Servicio;
import com.pixvs.main.models.projections.Servicio.ServicioComboProjection;
import com.pixvs.main.models.projections.Servicio.ServicioEditarProjection;
import com.pixvs.main.models.projections.Servicio.ServicioListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicioDao extends CrudRepository<Servicio, String> {

    Servicio findById(Integer id);

    ServicioEditarProjection findProjectedById(Integer id);

    List<ServicioListadoProjection> findProjectedListadoAllByActivoTrue();

    List<ServicioComboProjection> findProjectedComboAllByActivoTrue();
    List<ServicioComboProjection> findProjectedComboByActivoTrueAndArticuloId(Integer articuloId);

    List<ServicioComboProjection> findProjectedComboByArticuloId(Integer articuloId);

    @Modifying
    @Query(value = "UPDATE Servicios SET SRV_Activo = :activo WHERE SRV_ServicioId = :id", nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}
