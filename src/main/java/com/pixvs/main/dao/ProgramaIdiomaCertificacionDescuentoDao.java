package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaIdiomaCertificacionDescuento;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacion.ProgramaIdiomaCertificacionEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacionDescuento.ProgramaIdiomaCertificacionDescuentoEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacionDescuento.ProgramaIdiomaCertificacionDescuentoListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProgramaIdiomaCertificacionDescuentoDao extends CrudRepository<ProgramaIdiomaCertificacionDescuento, String> {

    // ListadoProjection
    @Query(nativeQuery = true,value = "SELECT * FROM [dbo].[VW_Listado_DescuentosCertificaciones]")
    List<ProgramaIdiomaCertificacionDescuentoListadoProjection> findProjectedListadoAllBy();

    ProgramaIdiomaCertificacionDescuentoEditarProjection findById(Integer id);

    ProgramaIdiomaCertificacionDescuentoEditarProjection findByProgramaIdiomaCertificacionId(Integer programaIdiomaCertificacionId);
}
