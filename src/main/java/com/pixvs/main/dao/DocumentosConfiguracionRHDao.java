package com.pixvs.main.dao;

import com.pixvs.main.models.DocumentosConfiguracionRH;
import com.pixvs.main.models.projections.DocumentosConfiguracionRH.DocumentosConfiguracionRHEditarProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentosConfiguracionRHDao extends CrudRepository<DocumentosConfiguracionRH, String> {
    List<DocumentosConfiguracionRHEditarProjection> findAllBy();

    List<DocumentosConfiguracionRHEditarProjection> findAllByTipoProcesoRHIdAndTipoContratoId(Integer tipoProcesoRHId, Integer tipoContratoId);
}
