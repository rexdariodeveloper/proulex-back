package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaIdiomaLibroMaterial;
import com.pixvs.main.models.ProgramaIdiomaModalidad;
import com.pixvs.main.models.projections.ProgramaIdiomaLibroMaterial.ProgramaIdiomaLibroMaterialEditarProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaIdiomaLibrosMaterialesDao extends CrudRepository<ProgramaIdiomaLibroMaterial, String> {

    void deleteByProgramaIdiomaId(Integer id);

    List<ProgramaIdiomaLibroMaterialEditarProjection> findAllByArticuloIdAndNivel(Integer articuloId, Integer nivel);

    List<ProgramaIdiomaLibroMaterialEditarProjection> findAllByProgramaIdiomaId(Integer programaIdiomaId);
    /*@Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_COMBO_PROJECTION_Proveedor]")
    List<ProveedorComboProjection> findProjectedComboAllBy();*/

    List<ProgramaIdiomaLibroMaterialEditarProjection> findAllByNivelAndArticuloId(Integer nivel, Integer articuloId);
}