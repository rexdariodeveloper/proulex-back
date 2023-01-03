package com.pixvs.main.dao;

import com.pixvs.main.models.TabuladorCurso;
import com.pixvs.main.models.TabuladorDetalle;
import com.pixvs.main.models.projections.TabuladorDetalle.TabuladorDetalleEmpleadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
public interface TabuladorCursoDao extends CrudRepository<TabuladorCurso, String> {
    void deleteById(Integer id);
    List<TabuladorCurso> findProjectByTabuladorId(Integer id);
}
