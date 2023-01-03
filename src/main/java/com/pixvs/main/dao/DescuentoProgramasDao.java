package com.pixvs.main.dao;

import com.pixvs.main.models.PADescuento;
import com.pixvs.main.models.PADescuentoDetalle;
import com.pixvs.main.models.projections.PADescuento.PADescuentoListadoProjection;
import com.pixvs.main.models.projections.PADescuentoDetalle.PADescuentoDetalleEditarProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DescuentoProgramasDao extends CrudRepository<PADescuentoDetalle, String> {
    @Query(value = "Select PADESCD_DescuentoDetalleId as id, PADESCD_PADESC_DescuentoId as descuentoId from PADescuentos\n" +
            "INNER JOIN PADescuentosDetalles on PADESCD_PADESC_DescuentoId = PADESC_DescuentoId\n" +
            "INNER JOIN Programas on PROG_ProgramaId = PADESCD_PROG_ProgramaId\n" +
            "WHERE PROG_ProgramaId= :programaId", nativeQuery = true)
    List<PADescuentoDetalleEditarProjection> findDescuentoDetallessByProgramaId(@Param("programaId") Integer programaId);

    void deleteById(Integer id);

    /*@Modifying
    @Query(value = "UPDATE PADescuentos SET PADESC_Activo = :activo WHERE PADESC_DescuentoId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);*/
}
