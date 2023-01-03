package com.pixvs.main.dao;

import com.pixvs.main.models.Localidad;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadEditarProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
public interface LocalidadDao extends CrudRepository<Localidad, String> {

    LocalidadEditarProjection findProjectedEditarById(Integer id);

    LocalidadEditarProjection findByCodigoLocalidad(String codigo);

    List<LocalidadListadoProjection> findProjectedListadoAllBy();

    List<LocalidadComboProjection> findProjectedComboAllByActivoTrueOrderByCodigoLocalidad();

    @Query("" +
            "SELECT \n" +
            "    l \n" +
            "FROM Localidad l \n" +
            "INNER JOIN Almacen a on a.id = l.almacenId \n" +
            "WHERE a.tipoAlmacenId = :tipoAlmacenId \n")
    List<LocalidadComboProjection> findProjectedComboAllByActivoTrueAndTipoAlmacenIdOrderByCodigoLocalidad(@Param("tipoAlmacenId") Integer tipoAlmacenId);

    @Query("" +
            "SELECT l " +
            "FROM Localidad l " +
            "INNER JOIN Almacen a on a.id = l.almacenId " +
            "INNER JOIN a.usuariosPermisos u " +
            "WHERE " +
            "a.activo = true and " +
            "l.activo = true and " +
            "a.tipoAlmacenId = :tipoAlmacenId and " +
            "u.id = :usuarioId " +
            "ORDER BY l.codigoLocalidad")
    List<LocalidadComboProjection> findProjectedComboAllByActivoTrueAndTipoAlmacenIdAndPermiso(@Param("tipoAlmacenId") Integer tipoAlmacenId, @Param("usuarioId") Integer usuarioId);

	List<LocalidadListadoProjection> findProjectedListadoAllByAlmacenId(Integer almacenId);

    List<LocalidadComboProjection> findComboProjectedListadoAllByAlmacenId(Integer almacenId);
    List<LocalidadComboProjection> findComboProjectedListadoAllByAlmacenIdAndActivoTrue(Integer almacenId);
	
	List<LocalidadComboProjection> findProjectedComboAllByActivoTrue();
    List<LocalidadComboProjection> findProjectedComboAllByActivoTrueAndAlmacenId(Integer almacenId);
    @Query("" +
            "SELECT l " +
            "FROM Localidad l " +
            "INNER JOIN Almacen a on a.id = l.almacenId " +
            "INNER JOIN a.usuariosPermisos u " +
            "WHERE " +
            "a.activo = true and " +
            "l.activo = true and " +
            "u.id = :usuarioId " +
            "ORDER BY l.codigoLocalidad")
    List<LocalidadComboProjection> findProjectedComboAllByActivoTrueAndPermiso(@Param("usuarioId") Integer usuarioId);
    LocalidadComboProjection findProjectedComboById(Integer localidadId);
    @Query("" +
            "SELECT loc " +
            "FROM Localidad loc " +
            "INNER JOIN Almacen alm on alm.id = loc.almacenId " +
            "INNER JOIN Sucursal suc on suc.id = alm.sucursalId " +
            "WHERE " +
            "alm.activo = true and " +
            "loc.activo = true and " +
            "suc.activo = true and " +
            "suc.id = :sucursalId " +
            "ORDER BY loc.codigoLocalidad")
    List<LocalidadComboProjection> findProjectedComboAllBySucursalId(@Param("sucursalId") Integer sucursalId);

    Localidad findById(Integer id);

    Localidad findByAlmacenIdAndLocalidadGeneralTrue(Integer almacenId);

    @Query("" +
            "SELECT l.id " +
            "FROM Localidad l " +
            "INNER JOIN Almacen a on a.id = l.almacenId " +
            "INNER JOIN a.usuariosPermisos u " +
            "WHERE " +
            "a.activo = true and " +
            "l.activo = true and " +
            "u.id = :usuarioId " +
            "ORDER BY l.codigoLocalidad")
    List<Integer> findIdAllByActivoTrueAndPermiso(@Param("usuarioId") Integer usuarioId);

}
