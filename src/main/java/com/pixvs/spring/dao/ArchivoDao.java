package com.pixvs.spring.dao;

import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.projections.Archivo.ArchivoDescargarProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.springframework.data.repository.CrudRepository;

public interface ArchivoDao extends CrudRepository<Archivo, String> {

    ArchivoProjection findById(Integer id);

    Archivo save(Archivo archivo);

    ArchivoDescargarProjection findDescargarProjectedById(Integer id);
}
