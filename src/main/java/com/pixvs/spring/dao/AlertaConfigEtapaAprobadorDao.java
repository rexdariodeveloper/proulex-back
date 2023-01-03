package com.pixvs.spring.dao;


import com.pixvs.spring.models.AlertaConfigEtapaAprobadores;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlertaConfigEtapaAprobadorDao extends CrudRepository<AlertaConfigEtapaAprobadores, String> {

   AlertaConfigEtapaAprobadores findByAprobadorIdAndEtapaIdAndOrden(Integer aprobadorId, Integer etapaId, Integer orden);

    AlertaConfigEtapaAprobadores findByDepartamentoIdAndEtapaIdAndOrden(Integer departamentoId, Integer etapaId, Integer orden);


}
