package com.pixvs.spring.dao;

import com.pixvs.spring.models.ArchivoEstructuraCarpeta;
import com.pixvs.spring.models.projections.ArchivoEstructuraCarpeta.ArchivoEstructuraCarpetaProjection;
import org.springframework.data.repository.CrudRepository;

public interface ArchivoEstructuraCarpetaDao extends CrudRepository<ArchivoEstructuraCarpeta, String> {

    ArchivoEstructuraCarpetaProjection findById(Integer id);

}
