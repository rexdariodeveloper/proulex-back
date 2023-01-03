package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaIdiomaCertificacionVale;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacionVale.ProgramaIdiomaCertificacionValeGeneradoProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacionVale.ProgramaIdiomaCertificacionValeListadoPVProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacionVale.ProgramaIdiomaCertificacionValeListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Rene Carrillo on 08/12/2022.
 */
public interface ProgramaIdiomaCertificacionValeDao extends CrudRepository<ProgramaIdiomaCertificacionVale, String> {

    ProgramaIdiomaCertificacionVale findById(Integer id);

    @Query(value = "SELECT * " +
            "FROM VW_LTO_ListadoValeCertificacion " +
            "WHERE (COALESCE(:sedeId, 0) = 0 OR SucursalId = :sedeId) " +
            "   AND (COALESCE(:listaCursoId, 0) = 0 OR CursoId IN (:listaCursoId)) " +
            "   AND (COALESCE(:listaModalidadId, 0) = 0 OR ModalidadId IN (:listaModalidadId)) " +
            "   AND (COALESCE(:listaFecha, '') = '' OR FORMAT(FechaInicio, 'dd/MM/yyyy') IN (:listaFecha)) " +
            "   AND (COALESCE(:alumno, '') = '' OR Alumno LIKE '%' + :alumno + '%')", nativeQuery = true)
    List<ProgramaIdiomaCertificacionValeListadoProjection> vw_lto_listadoValeCertificacion(@Param("sedeId") Integer sedeId,
                                                                                           @Param("listaCursoId") List<Integer> listaCursoId,
                                                                                           @Param("listaModalidadId") List<Integer> listaModalidadId,
                                                                                           @Param("listaFecha") List<String> listaFecha,
                                                                                           @Param("alumno") String alumno);

    @Query(value = "SELECT * " +
            "FROM VW_AlumnosGruposValesCertificaciones " +
            "WHERE Id = :alumnoGrupoId", nativeQuery = true)
    ProgramaIdiomaCertificacionValeGeneradoProjection vw_alumnosGruposValesCertificaciones(@Param("alumnoGrupoId") Integer alumnoGrupoId);

    @Query(value = "SELECT * " +
            "FROM VW_LTO_ListadoValeCertificacion " +
            "WHERE Id = :alumnoGrupoId", nativeQuery = true)
    ProgramaIdiomaCertificacionValeListadoProjection buscaIistadoValeCertificacionConAlumnoGrupoId(@Param("alumnoGrupoId") Integer alumnoGrupoId);

    @Query(value = "SELECT * " +
            "FROM VW_LTO_ListadoValeCertificacion " +
            "WHERE Id IN (:listaAlumnoGrupoId)", nativeQuery = true)
    List<ProgramaIdiomaCertificacionValeListadoProjection> buscaTodosIistadoValeCertificacionConAlumnoGrupoId(@Param("listaAlumnoGrupoId") List<Integer> listaAlumnoGrupoId);

    ProgramaIdiomaCertificacionVale findByAlumnoGrupoId(@Param("alumnoGrupoId") Integer alumnoGrupoId);

    @Query(nativeQuery = true, value = "\n" +
            "SELECT *  FROM [dbo].[VW_ListadoPVProjection_ProgramasIdiomasCertificacionesVales] \n" +
            "WHERE \n" +
            "   estatusId IN :estatusIds \n" +
            "   AND CONCAT(alumnoCodigo,'|',alumnoNombre,' ',alumnoPrimerApellido,' ' + alumnoSegundoApellido,' ',alumnoNombre,'|',curso,'|',certificacion) LIKE CONCAT('%',:filtro,'%') \n" +
            "ORDER BY id \n" +
            "OFFSET :offset ROWS FETCH NEXT :top ROWS ONLY \n" +
            "")
    List<ProgramaIdiomaCertificacionValeListadoPVProjection> findListadoPVProjectedAllByEstatusIdInAndFiltro(@Param("estatusIds") List<Integer> estatusIds, @Param("filtro") String filtro, @Param("offset") Integer offset, @Param("top") Integer top);
    
    @Query(nativeQuery = true, value = "\n" +
            "SELECT *  FROM [dbo].[VW_ListadoPVProjection_ProgramasIdiomasCertificacionesVales] \n" +
            "WHERE \n" +
            "   alumnoId = :alumnoId \n" +
            "   AND certificacionId = :certificacionId \n" +
            "   AND estatusId IN :estatusIds \n" +
            "")
    ProgramaIdiomaCertificacionValeListadoPVProjection findListadoPVProjectedByAlumnoIdAndCertificacionIdAndEstatusIdIn(@Param("alumnoId") Integer alumnoId, @Param("certificacionId") Integer certificacionId, @Param("estatusIds") List<Integer> estatusIds);

}
