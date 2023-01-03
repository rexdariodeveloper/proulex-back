package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaIdiomaModalidad;
import com.pixvs.main.models.ProgramaIdiomaSucursal;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaIdiomaSucursalDao extends CrudRepository<ProgramaIdiomaSucursal, String> {

    void deleteByProgramaIdiomaId(Integer id);
    void deleteById(Integer id);


    /*@Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_COMBO_PROJECTION_Proveedor]")
    List<ProveedorComboProjection> findProjectedComboAllBy();*/


}