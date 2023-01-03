package com.pixvs.spring.dao;

import com.pixvs.spring.models.AlertaConfigEtapa;
import com.pixvs.spring.models.projections.AlertaConfigEtapa.AlertaConfigEtapaComboProjection;
import com.pixvs.spring.models.projections.AlertaConfigEtapa.AlertaConfigEtapaEditarProjection;
import com.pixvs.spring.models.projections.AlertaDetalle.AlertaDetalleListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AlertaConfigEtapaDao extends CrudRepository<AlertaConfigEtapa, String> {

    AlertaConfigEtapa findById(Integer id);

    AlertaConfigEtapaEditarProjection findProjectedById(Integer id);

    List<AlertaConfigEtapaComboProjection> findAllProjectedBy();

    List<AlertaConfigEtapaComboProjection> findAllProjectedByAlertaConfigId(Integer id);

    List<AlertaConfigEtapaComboProjection> findAllProjectedByAlertaConfigIdAndSucursalId(Integer id, Integer sucursalId);

    List<AlertaConfigEtapa> findBySucursalId(Integer sucursalId);

    AlertaConfigEtapa findByAlertaConfigIdAndSucursalIdAndOrden(Integer id, Integer sucursalId,Integer orden);

    AlertaConfigEtapa findByAlertaConfigIdAndOrden(Integer id,Integer orden);

    AlertaConfigEtapa save(AlertaConfigEtapa alertaConfigEtapa);

    List<AlertaConfigEtapa> findByAlertaConfigId(Integer idAlertaConfig);

}
