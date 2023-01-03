package com.pixvs.spring.services;

import com.pixvs.spring.models.Autonumerico;

import java.util.List;

public interface AutonumericoService {

    String getSiguienteAutonumericoByPrefijo(String prefijo) throws Exception;

    String getSiguienteAutonumericoById(int id) throws Exception;

    Autonumerico create(String prefijo, String nombre) throws  Exception;

    Autonumerico buscaAutonumericoPorReferenciaId(Integer referenciaId) throws Exception;

    Autonumerico buscaAutonumericoPorReferenciaIdYNombre(Integer referenciaId, String nombre) throws Exception;

    Autonumerico save(Autonumerico autonumerico) throws Exception;

    void save(List<Autonumerico> autonumericos) throws Exception;
}