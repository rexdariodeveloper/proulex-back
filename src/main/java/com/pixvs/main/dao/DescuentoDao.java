package com.pixvs.main.dao;

import com.pixvs.main.models.CXPPago;
import com.pixvs.main.models.PADescuento;
import com.pixvs.main.models.projections.Dashboard.*;
import com.pixvs.main.models.projections.PADescuento.PADescuentoListadoProjection;
import com.pixvs.main.models.projections.Reporte.ReporteConfirmingProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DescuentoDao extends CrudRepository<PADescuento, String> {

    List<PADescuentoListadoProjection> findAllByClienteId(Integer clienteId);
    @Modifying
    @Query(value = "UPDATE PADescuentos SET PADESC_Activo = :activo WHERE PADESC_DescuentoId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);
}
