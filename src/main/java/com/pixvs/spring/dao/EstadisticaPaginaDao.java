package com.pixvs.spring.dao;

import com.pixvs.spring.models.EstadisticaPagina;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EstadisticaPaginaDao extends CrudRepository<EstadisticaPagina, String> {

    EstadisticaPagina save(EstadisticaPagina estadisticaPagina);

}
