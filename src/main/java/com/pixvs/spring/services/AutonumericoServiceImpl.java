package com.pixvs.spring.services;

import com.pixvs.spring.dao.AutonumericoDao;
import com.pixvs.spring.models.Autonumerico;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutonumericoServiceImpl implements AutonumericoService {

    @Autowired
    private AutonumericoDao autonumericoDao;

    public String getSiguienteAutonumericoByPrefijo(String prefijo) throws Exception {
        return getSiguienteAutonumerico(null, prefijo);
    }

    public String getSiguienteAutonumericoById(int id) throws Exception {
        return getSiguienteAutonumerico(id, null);
    }

    public String getSiguienteAutonumerico(Integer id, String prefijo) throws Exception {
        Autonumerico autonumerico = id != null
                ? autonumericoDao.findAutonumericoByIdAndActivoIsTrue(id)
                : autonumericoDao.findAutonumericoByPrefijoAndActivoIsTrue(prefijo);

        if (autonumerico == null) {
            throw new Exception("No se ha encontrado el autonumerico.");
        }

        String siguienteAutonumerico = autonumerico.getPrefijo() + StringUtils.leftPad("" + autonumerico.getSiguiente(), autonumerico.getDigitos(), "0");

        autonumerico.setSiguiente(autonumerico.getSiguiente() + 1);
        autonumericoDao.save(autonumerico);

        return siguienteAutonumerico;
    }

    public Autonumerico create(String prefijo, String nombre) throws Exception {
        Autonumerico autonumerico = new Autonumerico();

        autonumerico.setNombre(nombre);
        autonumerico.setPrefijo(prefijo);
        autonumerico.setActivo(true);
        autonumerico.setDigitos(6);
        autonumerico.setSiguiente(1);

        return autonumericoDao.save(autonumerico);
    }

    public Autonumerico buscaAutonumericoPorReferenciaId(Integer referenciaId) throws Exception {
        return autonumericoDao.findAutonumericoByReferenciaIdAndActivoIsTrue(referenciaId);
    }

    public Autonumerico buscaAutonumericoPorReferenciaIdYNombre(Integer referenciaId, String nombre) throws Exception {
        return autonumericoDao.findAutonumericoByReferenciaIdAndNombreContainsAndActivoIsTrue(referenciaId, nombre);
    }

    public Autonumerico save(Autonumerico autonumerico) {
        return autonumericoDao.save(autonumerico);
    }

    public void save(List<Autonumerico> autonumericos) {
        autonumericoDao.saveAll(autonumericos);
    }
}