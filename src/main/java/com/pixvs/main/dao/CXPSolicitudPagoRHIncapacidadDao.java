package com.pixvs.main.dao;

import com.pixvs.main.models.CXPSolicitudPagoRH;
import com.pixvs.main.models.CXPSolicitudPagoRHIncapacidad;
import com.pixvs.main.models.projections.CXPSolicitudPagoRH.CXPSolicitudPagoRHEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRH.CXPSolicitudPagoRHListadoProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRHIncapacidad.CXPSolicitudPagoRHIncapacidadEditarProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
public interface CXPSolicitudPagoRHIncapacidadDao extends CrudRepository<CXPSolicitudPagoRHIncapacidad, String> {

    //CXPSolicitudPagoRH findById(Integer id);

    //List<CXPSolicitudPagoRHListadoProjection> findProjectedListadoAllByEstatusIdIn(List<Integer> estatusIds);

    //CXPSolicitudPagoRHListadoProjection findProjectedById(Integer id);

    CXPSolicitudPagoRHIncapacidadEditarProjection findEditarProjectionById(Integer id);

}
