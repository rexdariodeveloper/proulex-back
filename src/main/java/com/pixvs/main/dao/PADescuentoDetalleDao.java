package com.pixvs.main.dao;

import com.pixvs.main.models.PADescuento;
import com.pixvs.main.models.PADescuentoDetalle;
import com.pixvs.main.models.projections.PADescuento.PADescuentoEditarProjection;
import com.pixvs.main.models.projections.PADescuento.PADescuentoListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface PADescuentoDetalleDao extends CrudRepository<PADescuentoDetalle, String> {
    void deleteById(Integer id);
}