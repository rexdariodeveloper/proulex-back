package com.pixvs.spring.services;
import com.pixvs.main.models.Usuario;

import java.util.Date;

public interface ConcurrenciaService {

    Boolean verificarIntegridad(Date fechaModificacionActual, Date fechaUltimaModificacion) throws Exception;
    Boolean verificarIntegridad(Date fechaModificacionActual, Date fechaUltimaModificacion, String modificador) throws Exception;
    Boolean verificarIntegridad(Date fechaModificacionActual, Date fechaUltimaModificacion, Integer idModificador) throws Exception;
    Boolean verificarIntegridad(Date fechaModificacionActual,Date fechaCreacionActual, Date fechaUltimaModificacion, Integer idModificador) throws Exception;

}