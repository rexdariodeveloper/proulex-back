package com.pixvs.spring.dao;

import com.pixvs.spring.models.UsuarioRecuperacion;
import com.pixvs.spring.models.projections.UsuarioRecuperacion.UsuarioRecuperacionProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioRecuperacionDao extends CrudRepository<UsuarioRecuperacion, String> {

    UsuarioRecuperacion save(UsuarioRecuperacion usuarioRecuperacion);

    UsuarioRecuperacion findByIdAndEstatus(Integer id, Integer estatus);

    UsuarioRecuperacionProjection findProjectionByToken(String token);

    UsuarioRecuperacion findByToken(String token);


}
