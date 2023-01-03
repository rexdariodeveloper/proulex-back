package com.pixvs.log;

import com.pixvs.main.models.Usuario;
import com.pixvs.spring.dao.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
public class LogController {


    @Autowired
    @Qualifier("logEntityManagerFactory")
    private EntityManager em;

    @Autowired
    private LogDao logDao;

    @Autowired
    private UsuarioDao usuarioDao;


    @Transactional
    public void insertaLog(Log log) {

        logDao.save(log);
    }

    @Transactional
    public void insertaLogUsuario(Log log, Integer idUsuario) {

        if (idUsuario != null) {
            Usuario usuario = usuarioDao.findById(idUsuario);
            if (usuario != null) {
                log.setCreadoPor(usuario.getNombreCompleto());
                log.setUsuarioId(usuario.getId());
            }
        }
        logDao.save(log);
    }

    public List<Log> getHistorial(int idReferencia, int idProceso) {

        List<Log> logs = logDao.findAllByReferenciaIdAndProcesoIdOrderByIdAsc(idReferencia, idProceso);

        return logs;
    }


}