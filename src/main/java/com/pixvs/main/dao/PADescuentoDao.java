package com.pixvs.main.dao;

import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.PADescuento;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoListadoProjection;
import com.pixvs.main.models.projections.PADescuento.PADescuentoEditarProjection;
import com.pixvs.main.models.projections.PADescuento.PADescuentoListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface PADescuentoDao extends CrudRepository<PADescuento, String> {

    List<PADescuentoListadoProjection> findProjectedListadoAllBy();

    PADescuentoEditarProjection findProjectedEditarById(Integer id);

    List<PADescuentoListadoProjection> findAllByClienteId(Integer clienteId);

    //List<ProveedorEditarProjection> findProjectedEditarByRfc(String rfc);

    PADescuento findById(Integer id);


    @Modifying
    @Query(value = "UPDATE PADescuentos SET PADESC_Activo = :activo WHERE PADESC_DescuentoId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    @Modifying
    @Query(value = "UPDATE PADescuentos SET PADESC_Activo = CAST(0 as bit) WHERE cast(PADESC_FechaFin as DATE) < cast (GETDATE() as DATE) and PADESC_Activo = CAST(1 as bit);",
            nativeQuery = true)
    int actualizarActivosByFecha();

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_LISTADO_DESCUENTOS] ORDER BY codigo")
    List<PADescuentoListadoProjection> findAllByView();

    @Query("" +
            "SELECT CASE WHEN COUNT(padesc) > 0 THEN true ELSE false END \n" +
            "FROM PADescuento padesc \n" +
            "INNER JOIN padesc.usuariosAutorizados padesua \n" +
            "WHERE \n" +
            "   padesc.activo = true \n" +
            "   AND padesc.tipoId = 2000701 \n" + // Bajo demanda
            "   AND padesc.porcentajeDescuento >= :montoDescuento \n" +
            "   AND padesua.activo = true" +
            "   AND padesua.usuarioId = :usuarioId" +
            "")
    boolean validarDescuentoUsuario(@Param("usuarioId") Integer usuarioId, @Param("montoDescuento") Integer montoDescuento);

    @Query("" +
            "SELECT CASE WHEN COUNT(padesc) > 0 THEN true ELSE false END \n" +
            "FROM PADescuento padesc \n" +
            "INNER JOIN padesc.usuariosAutorizados padesua \n" +
            "WHERE \n" +
            "   padesc.activo = true \n" +
            "   AND padesc.tipoId = 2000701 \n" + // Bajo demanda
            "   AND padesua.activo = true" +
            "   AND padesua.usuarioId = :usuarioId" +
            "")
    boolean validarDescuentoUsuarioEstaConfigurado(@Param("usuarioId") Integer usuarioId);

    @Query(nativeQuery = true, value = "SELECT [dbo].[getDescuentoAutomaticoGrupo]( :grupoId, :sucursalId, :fecha )")
    Integer getPorcentajeDescuentoCurso(@Param("grupoId") Integer grupoId, @Param("sucursalId") Integer sucursalId, @Param("fecha") Date fecha);

}