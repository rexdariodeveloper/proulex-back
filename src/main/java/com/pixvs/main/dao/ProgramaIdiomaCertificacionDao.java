package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaIdiomaCertificacion;
import com.pixvs.main.models.ProgramaIdiomaLibroMaterial;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacion.ProgramaIdiomaCertificacionComboProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacion.ProgramaIdiomaCertificacionEditarProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaIdiomaCertificacionDao extends CrudRepository<ProgramaIdiomaCertificacion, String> {

    void deleteByProgramaIdiomaId(Integer id);


    /*@Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_COMBO_PROJECTION_Proveedor]")
    List<ProveedorComboProjection> findProjectedComboAllBy();*/


    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_CertificacionesArticulos] WHERE ProgramaIdiomaId = :programaIdiomaId")
    List<ProgramaIdiomaCertificacionComboProjection> findCertificacionComboProjection(@Param("programaIdiomaId") Integer programaIdiomaId);

}