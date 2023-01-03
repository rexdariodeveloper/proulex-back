package com.pixvs.main.dao;

import com.pixvs.main.models.Entidad;
import com.pixvs.main.models.projections.Entidad.EntidadComboProjection;
import com.pixvs.main.models.projections.Entidad.EntidadEditarProjection;
import com.pixvs.main.models.projections.Entidad.EntidadListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntidadDao extends CrudRepository<Entidad, String> {

    Entidad findEntidadById(Integer id);
    List<EntidadComboProjection> findComboAllByActivoIsTrue();
    //List<EntidadListadoProjection> findListadoAllByActivoIsTrue();
    EntidadEditarProjection findEditarFindById(Integer id);

    @Modifying
    @Query(value = "UPDATE Entidades SET ENT_Activo = :activo WHERE ENT_EntidadId = :id", nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    @Query(value = "" +
            "Select \n" +
            "i.ENT_EntidadId as id,\n" +
            "i.ENT_Codigo as codigo,\n" +
            "i.ENT_Nombre as nombre,\n" +
            "i.ENT_Activo as activo,\n" +
            "CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as director,\n" +
            "x.ENT_Nombre as entidadIndependiente\n" +
            "from Entidades i\n" +
            "LEFT JOIN Empleados on ENT_EMP_Director = EMP_EmpleadoId\n" +
            "LEFT OUTER JOIN\n" +
            "     (SELECT ENT_Nombre,ENT_EntidadId\n" +
            "          FROM Entidades\n" +
            "\t\t  WHERE ENT_EntidadId IS NOT NULL\n" +
            "       GROUP BY ENT_EntidadId,ENT_Nombre) x\n" +
            "ON x.ENT_EntidadId = i.ENT_EntidadDependienteId\n" +
            "WHERE ENT_Activo=1 \n" +
            "", nativeQuery = true)
    List<EntidadListadoProjection> findListadoAllByActivoIsTrue();
}