package com.pixvs.spring.dao;

import com.pixvs.spring.models.DepartamentoResponsabilidadHabilidad;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartamentoHabilidadResponsabilidadDao extends CrudRepository<DepartamentoResponsabilidadHabilidad, String> {

    DepartamentoResponsabilidadHabilidad findById(Integer id);

    //void deleteById(Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM DepartamentoResposabilidadHabilidad WHERE DEPREHA_DepartamentoResposabilidadHabilidadId in :ids")
    void deleteHabilidadesResponsabilidadesWithIds(@Param("ids") List<Integer> ids);

    //void deleteInBatch(List<Integer> list);
}
