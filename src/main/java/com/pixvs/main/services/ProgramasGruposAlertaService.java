package com.pixvs.main.services;

public interface ProgramasGruposAlertaService {

    Boolean createAlerta(Integer inscripcionId, Integer nuevoGrupoId, Integer idUsuario, String codigoGrupo) throws Exception;

}
