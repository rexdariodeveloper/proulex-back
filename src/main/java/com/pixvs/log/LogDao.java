package com.pixvs.log;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogDao extends CrudRepository<Log, String> {


    Log save(Log log);

    List<Log> findAllByReferenciaIdAndProcesoIdOrderByIdAsc(@Param("idReferencia") Integer idReferencia, @Param("idProceso") Integer idProceso);


}