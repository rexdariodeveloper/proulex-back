package com.pixvs.spring.services;

import com.pixvs.main.models.Usuario;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.handler.exceptions.ConcurrenciaException;
import com.pixvs.spring.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConcurrenciaServiceImpl implements ConcurrenciaService {

    @Autowired
    UsuarioDao usuarioDao;

    public Boolean verificarIntegridad(Date fechaModificacionActual, Date fechaUltimaModificacion) throws Exception {

        if (fechaModificacionActual == null && fechaUltimaModificacion == null) return true;

        if (fechaModificacionActual.getTime() != fechaUltimaModificacion.getTime())
            throw new ConcurrenciaException(DateUtil.getFechaHoraMin(fechaModificacionActual) );

        return true;
    }


    public Boolean verificarIntegridad(Date fechaModificacionActual, Date fechaUltimaModificacion, String modificador) throws Exception {

        if (fechaModificacionActual == null && fechaUltimaModificacion == null) return true;

        if (fechaModificacionActual.getTime() != fechaUltimaModificacion.getTime())
            throw new ConcurrenciaException(modificador + " - " + DateUtil.getFechaHoraMin(fechaModificacionActual) );

        return true;
    }

    public Boolean verificarIntegridad(Date fechaModificacionActual,Date fechaCreacionActual, Date fechaUltimaModificacion, Integer idModificador) throws Exception {

        if (fechaModificacionActual == null && fechaUltimaModificacion == null) return true;

        if (fechaModificacionActual == null) return true;

        if (fechaModificacionActual != null && fechaCreacionActual != null) {
            if (fechaModificacionActual.getTime() == fechaCreacionActual.getTime()){
                return true;
            }
        }

        if (fechaModificacionActual.getTime() != fechaUltimaModificacion.getTime()){
            Usuario modificador = usuarioDao.findById(idModificador);
            throw new ConcurrenciaException( modificador.getNombreCompleto() + " - " +DateUtil.getFechaHoraMin(fechaModificacionActual) );
        }

        return true;
    }

    public Boolean verificarIntegridad(Date fechaModificacionActual, Date fechaUltimaModificacion, Integer idModificador) throws Exception {

        if (fechaModificacionActual == null && fechaUltimaModificacion == null) return true;

        if (fechaModificacionActual == null) return true;

        if (fechaModificacionActual.getTime() != fechaUltimaModificacion.getTime()){
            Usuario modificador = usuarioDao.findById(idModificador);
            throw new ConcurrenciaException( modificador.getNombreCompleto() + " - " +DateUtil.getFechaHoraMin(fechaModificacionActual) );
        }

        return true;
    }


}