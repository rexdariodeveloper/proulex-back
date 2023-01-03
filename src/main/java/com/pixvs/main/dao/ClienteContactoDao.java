package com.pixvs.main.dao;

import com.pixvs.main.models.ProveedorContacto;
import com.pixvs.main.models.projections.ProveedorContacto.ProveedorContactoComboProjection;
import com.pixvs.main.models.projections.ProveedorContacto.ProveedorContactoEditarProjection;
import com.pixvs.main.models.projections.ProveedorContacto.ProveedorContactoListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteContactoDao extends CrudRepository<ProveedorContacto, String> {

    List<ProveedorContactoListadoProjection> findProjectedListadoAllBy();

    List<ProveedorContactoComboProjection> findProjectedComboAllByActivoTrue();

    ProveedorContactoEditarProjection findProjectedEditarById(Integer id);

    ProveedorContacto findById(Integer id);

    @Modifying
    @Query(value = "UPDATE ProveedorContactos SET PROC_Activo = :activo WHERE PROC_ProveedorContactoId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);
}