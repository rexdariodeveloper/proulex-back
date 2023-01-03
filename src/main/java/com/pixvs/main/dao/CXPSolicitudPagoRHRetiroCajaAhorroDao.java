package com.pixvs.main.dao;

import com.pixvs.main.models.CXPSolicitudPagoRHIncapacidad;
import com.pixvs.main.models.CXPSolicitudPagoRHRetiroCajaAhorro;
import com.pixvs.main.models.projections.CXPSolicitudPagoRHIncapacidad.CXPSolicitudPagoRHIncapacidadEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRHRetiroCajaAhorro.CXPSolicitudPagoRHRetiroCajaAhorroEditarProjection;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
public interface CXPSolicitudPagoRHRetiroCajaAhorroDao extends CrudRepository<CXPSolicitudPagoRHRetiroCajaAhorro, String> {

    //CXPSolicitudPagoRH findById(Integer id);

    //List<CXPSolicitudPagoRHListadoProjection> findProjectedListadoAllByEstatusIdIn(List<Integer> estatusIds);

    //CXPSolicitudPagoRHListadoProjection findProjectedById(Integer id);

    CXPSolicitudPagoRHRetiroCajaAhorroEditarProjection findEditarProjectionById(Integer id);

}
