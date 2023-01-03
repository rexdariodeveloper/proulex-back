package com.pixvs.spring.dao;

import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.projections.Usuario.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UsuarioDao extends CrudRepository<Usuario, String> {

    UsuarioLoginVerificarProjection findProjectedLoginByCorreoElectronico(String correoElectronico);

    UsuarioLoginVerificarProjection findProjectedLoginByTipoId(Integer tipoId);

    UsuarioLoginProjection findProjectedLoginById(Integer id);

    UsuarioLoginProjection findProjectedByCorreoElectronicoAndCodigoAndVencimientoGreaterThan(String correo, String codigo, Date fecha);

    UsuarioComboProjection findByCorreoElectronico(String correoElectronico);

    List<UsuarioComboProjection> findProjectedComboAllByEstatusId(Integer estatusId);

    Usuario findByCorreoElectronicoAndIdNot(String correoElectronico, Integer id);

    UsuarioEditarProjection findProjectedByIdAndEstatusId(Integer id, Integer estatusId);

    UsuarioEditarProjection findProjectedById(Integer id);

    UsuarioEditarProjection findProjectedByCodigo(String codigo);

    List<UsuarioListadoProjection> findAllProjecteBy();

    List<UsuarioListadoProjection> findTop200ProjectedByEstatusIdOrderByIdDesc(Integer estatusId);

    List<UsuarioListadoProjection> findProjectedByEstatusIdOrderByIdDesc(Integer estatusId);

    Usuario findById(Integer id);

    @Modifying
    @Query(value = "UPDATE Usuarios set USU_Codigo = :codigo, USU_FechaVencimiento = DATEADD(minute,:timeout,GETDATE()) WHERE USU_UsuarioId = :id", nativeQuery = true)
    int actualizarCodigo(@Param("codigo") String codigo, @Param("timeout") Integer timeout, @Param("id") Integer id);

    @Modifying
    @Query(value = "UPDATE Usuarios set USU_Codigo = :codigo, USU_FechaVencimiento = (now() + (:timeout * interval '1 minute')) WHERE USU_UsuarioId = :id", nativeQuery = true)
    int actualizarCodigoPG(@Param("codigo") String codigo, @Param("timeout") Integer timeout, @Param("id") Integer id);

    @Modifying
    @Query(value = "UPDATE Usuarios set USU_FechaUltimaSesion = GETDATE() WHERE USU_UsuarioId = :id",
            nativeQuery = true)
    int actualizarInicioSesion(@Param("id") Integer id);

    @Modifying
    @Query(value = "UPDATE Usuarios set USU_FechaUltimaSesion = now() WHERE USU_UsuarioId = :id",
            nativeQuery = true)
    int actualizarInicioSesionPG(@Param("id") Integer id);

    @Modifying
    @Query(value = "UPDATE Usuarios set USU_CMM_EstatusId = :estatusId WHERE USU_UsuarioId = :id",
            nativeQuery = true)
    int actualizarEstatus(@Param("id") Integer id, @Param("estatusId") Integer estatusId);

    @Modifying
    @Query(value = "UPDATE Usuarios set USU_FechaModificacion = GETDATE(),  USU_Contrasenia = :contrasenia WHERE USU_UsuarioId = :id",
            nativeQuery = true)
    int actualizarContrasenia(@Param("id") Integer id, @Param("contrasenia") String contrasenia);

    @Modifying
    @Query(value = "UPDATE Usuarios set USU_FechaModificacion = now(),  USU_Contrasenia = :contrasenia WHERE USU_UsuarioId = :id",
            nativeQuery = true)
    int actualizarContraseniaPG(@Param("id") Integer id, @Param("contrasenia") String contrasenia);

    @Query("SELECT p " +
            "   FROM Usuario p" +
            "   INNER JOIN p.estatus es" +
            "   WHERE ( cast(:fechaCreacionDesde as date) is null or CAST(p.fechaCreacion AS date) >= :fechaCreacionDesde) " +
            "   and ( cast(:fechaCreacionHasta as date) is null or CAST(p.fechaCreacion AS date) <= :fechaCreacionHasta) " +
            "   and (es.id IN (:estatus) ) " +
            "   ORDER BY p.fechaCreacion ASC")
    List<UsuarioListadoProjection> findAllQueryProjectedBy(@Param("fechaCreacionDesde") Date fechaCreacion, @Param("fechaCreacionHasta") Date fechaCreacionHasta, @Param("estatus") List<Integer> estatus);

    @Query(nativeQuery = true , value = "Select \n" +
            "DEP_DepartamentoId as id,\n" +
            "null as nombreCompleto,\n" +
            "null as primerApellido,\n" +
            "null as segundoApellido,\n" +
            "null as estatusId,\n" +
            "CONCAT(DEP_Nombre,' - ',USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido) as nombre\n" +
            "from Usuarios\n" +
            "INNER JOIN Departamentos on DEP_USU_ResponsableId = USU_UsuarioId\n" +
            "WHERE USU_CMM_EstatusId= 1000001 AND DEP_Activo= 1")
    List<UsuarioComboProjection> findResponsablesDepartamentos();

    @Query(nativeQuery = true , value = "Select \n" +
            "DEP_DepartamentoId as id,\n" +
            "null as nombreCompleto,\n" +
            "null as primerApellido,\n" +
            "null as segundoApellido,\n" +
            "null as estatusId,\n" +
            "CONCAT(DEP_Nombre,' - ',USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido) as nombre\n" +
            "from Usuarios\n" +
            "INNER JOIN Departamentos on DEP_USU_ResponsableId = USU_UsuarioId\n" +
            "WHERE USU_CMM_EstatusId= 1000001 AND DEP_Activo= 1 AND CONCAT(DEP_Nombre,' - ',USU_Nombre,' ',USU_PrimerApellido,' ',USU_SegundoApellido) = :nombreCompleto")
    UsuarioComboProjection findResponsableDepartamentoByIdAndNombre(@Param("nombreCompleto") String nombreCompleto);
}
