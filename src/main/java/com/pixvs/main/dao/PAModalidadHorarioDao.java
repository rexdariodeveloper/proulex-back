package com.pixvs.main.dao;

import com.pixvs.main.models.PAModalidadHorario;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.PAModalidadHorario.PAModalidadHorarioComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.PAModalidadHorarioComboSimpleProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PAModalidadHorarioDao extends CrudRepository<PAModalidadHorario, String> {

    PAModalidadHorario findById(Integer id);
    PAModalidadHorarioComboProjection findByModalidadIdAndNombre(Integer idModalidad, String nombre);
    List<PAModalidadHorarioComboProjection> findAllBy();
    List<PAModalidadHorarioComboProjection> findProjectedComboAllByModalidadIdOrderByCodigo(Integer modalidadId);

    List<PAModalidadHorarioComboSimpleProjection> findProjectedComboSimpleAllByModalidadIdOrderByCodigo(Integer modalidadId);
}
