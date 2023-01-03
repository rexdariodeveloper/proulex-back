package com.pixvs.main.dao;

import com.pixvs.main.models.CXPSolicitudPagoRH;
import com.pixvs.main.models.CXPSolicitudPagoRHBecarioDocumento;
import com.pixvs.main.models.projections.CXPSolicitudPagoRH.CXPSolicitudPagoRHEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRH.CXPSolicitudPagoRHListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
public interface CXPSolicitudPagoRHBecarioDocumentoDao extends CrudRepository<CXPSolicitudPagoRHBecarioDocumento, String> {
    List<CXPSolicitudPagoRHBecarioDocumento> findAllByCpxSolicitudPagoRhId(Integer id);
}
