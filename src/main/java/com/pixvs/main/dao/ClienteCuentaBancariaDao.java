package com.pixvs.main.dao;

import com.pixvs.main.models.ClienteCuentaBancaria;
import com.pixvs.main.models.projections.ClienteCuentaBancaria.ClienteCuentaBancariaComboProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteCuentaBancariaDao extends CrudRepository<ClienteCuentaBancaria, Integer> {

    void deleteById(Integer id);

    List<ClienteCuentaBancariaComboProjection> findProjectedComboAllByClienteIdAndActivoTrue(int clienteId);
}