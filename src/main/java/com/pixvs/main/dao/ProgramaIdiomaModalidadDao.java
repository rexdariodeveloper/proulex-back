package com.pixvs.main.dao;

import com.pixvs.main.models.Programa;
import com.pixvs.main.models.ProgramaIdiomaModalidad;
import com.pixvs.main.models.projections.Programa.ProgramaEditarProjection;
import com.pixvs.main.models.projections.Programa.ProgramaListadoProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaModalidad.ProgramaIdiomaModalidadEditarProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaIdiomaModalidadDao extends CrudRepository<ProgramaIdiomaModalidad, String> {

    void deleteByProgramaIdiomaId(Integer id);
    void deleteById(Integer id);
    List<ProgramaIdiomaModalidadEditarProjection> findAllByModalidadId(Integer modalidadId);

    /*@Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_COMBO_PROJECTION_Proveedor]")
    List<ProveedorComboProjection> findProjectedComboAllBy();*/


}