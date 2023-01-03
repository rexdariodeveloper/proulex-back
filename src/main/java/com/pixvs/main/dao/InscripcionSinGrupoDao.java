package com.pixvs.main.dao;

import com.pixvs.main.models.InscripcionSinGrupo;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.InscripcionSinGrupo.InscripcionSinGrupoListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 10/08/2021.
 */
public interface InscripcionSinGrupoDao extends CrudRepository<InscripcionSinGrupo, String> {

    // Modelo
    InscripcionSinGrupo findById(Integer id);
    List<InscripcionSinGrupo> findAllByOrdenVentaDetalleIdAndEstatusIdIn(Integer ordenVentaDetalleId, List<Integer> estatusIds);
    @Query("" +
            "SELECT inssg \n" +
            "FROM InscripcionSinGrupo inssg \n" +
            "INNER JOIN inssg.paModalidadHorario horario \n" +
            "INNER JOIN ProgramaGrupo pgComparar ON pgComparar.id = :grupoId \n" +
            "INNER JOIN pgComparar.modalidadHorario horarioComparar \n" +
            "INNER JOIN pgComparar.programaIdioma piComparar \n" +
            "WHERE \n" +
            "   inssg.alumnoId = :alumnoId \n" +
            "   AND inssg.estatusId IN (" + ControlesMaestrosMultiples.CMM_INSSG_Estatus.PAGADA + "," + ControlesMaestrosMultiples.CMM_INSSG_Estatus.PENDIENTE_DE_PAGO + ") \n" +
            "   AND ( \n" +
            "       inssg.idiomaId = piComparar.idiomaId \n" +
            "       OR (\n" +
            "           horarioComparar.horaInicio >= horario.horaInicio \n" +
            "           AND horarioComparar.horaInicio < horario.horaFin \n" +
            "       )\n" +
            "       OR (\n" +
            "           horarioComparar.horaFin > horario.horaInicio \n" +
            "           AND horarioComparar.horaFin <= horario.horaFin \n" +
            "       )\n" +
            "   ) \n" +
            "")
    List<InscripcionSinGrupo> findAllByInterferenciaGrupo(@Param("grupoId") Integer grupoId, @Param("alumnoId") Integer alumnoId);
    @Query("" +
            "SELECT inssg \n" +
            "FROM InscripcionSinGrupo inssg \n" +
            "WHERE \n" +
            "   inssg.alumnoId = :alumnoId \n" +
            "   AND inssg.programaId = :programaId \n" +
            "   AND inssg.idiomaId = :idiomaId \n" +
            "   AND inssg.paModalidadId = :paModalidadId \n" +
            "   AND inssg.nivel = :nivel \n" +
            "   AND inssg.estatusId IN (" + ControlesMaestrosMultiples.CMM_INSSG_Estatus.PAGADA + "," + ControlesMaestrosMultiples.CMM_INSSG_Estatus.PENDIENTE_DE_PAGO + ") \n" +
            "")
    InscripcionSinGrupo findAllByRelacionar(@Param("alumnoId") Integer alumnoId, @Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId, @Param("paModalidadId") Integer paModalidadId, @Param("nivel") Integer nivel);
    List<InscripcionSinGrupo> findByOrdenVentaDetalleId(Integer ordenVentaDetalleId);
    InscripcionSinGrupo findByAlumnoIdAndEstatusId(Integer alumnoId, Integer estatusId);

    // ListadoProjection
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_ListadoProjection_InscripcionesSinGrupo] WHERE sucursalId = :sucursalId")
    List<InscripcionSinGrupoListadoProjection> findProjectedListadoAllBySucursalId(@Param("sucursalId") Integer sucursalId);
    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM [dbo].[VW_ListadoProjection_InscripcionesSinGrupo] \n" +
            "WHERE \n" +
            "   sucursalId IN :listaSedeId \n" +
            "   AND estatusId IN :estatusIds \n" +
            "   AND CONCAT(COALESCE(alumnoCodigo,''),'|',COALESCE(alumnoCodigoUDG,''),'|',alumnoNombre,' ',alumnoPrimerApellido,' ' + alumnoSegundoApellido,' ',alumnoNombre,'|',curso,'|',paModalidadNombre,'|',medioPago,'|',ovCodigo ,'|',estatus) LIKE CONCAT('%',:filtro,'%') \n" +
            "ORDER BY id \n" +
            "OFFSET :offset ROWS FETCH NEXT CAST(COALESCE(:top, 0x7ffffff) AS INT) ROWS ONLY \n")
    List<InscripcionSinGrupoListadoProjection> findProjectedListadoAllBySucursalIdAndEstatusIdIn(@Param("listaSedeId") List<Integer> listaSedeId, @Param("estatusIds") List<Integer> estatusIds, @Param("filtro") String filtro, @Param("offset") Integer offset, @Param("top") Integer top);

}
