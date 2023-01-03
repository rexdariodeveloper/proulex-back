package com.pixvs.main.dao;

import com.pixvs.main.models.Banco;
import com.pixvs.main.models.projections.Banco.BancoComboProjection;
import com.pixvs.main.models.projections.Banco.BancoEditarProjection;
import com.pixvs.main.models.projections.Banco.BancoListadoProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BancoDao extends CrudRepository<Banco, String> {

    List<BancoComboProjection> findProjectedComboAllByActivoTrue();

    List<BancoListadoProjection> findAllBancoListadoProjectedByIdNotNullOrderByCodigo();

    BancoEditarProjection findBancoEditarProjectedById(Integer id);
}
