package com.pixvs.spring.services;

import com.pixvs.spring.dao.EstadisticaPaginaDao;
import com.pixvs.spring.models.EstadisticaPagina;
import com.pixvs.spring.util.HttpReqRespUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by David Arroyo on 7/7/2020.
 */
@Component
public class EstadisticaPaginaServiceImpl implements EstadisticaPaginaService {

    @Autowired
    private EstadisticaPaginaDao estadisticaPaginaDao;

    public void incrementaContador(String url, int tipo) {

        EstadisticaPagina estadisticaPagina = new EstadisticaPagina();
        estadisticaPagina.setTipoId(tipo);
        estadisticaPagina.setUrl(url);
        estadisticaPagina.setIp(HttpReqRespUtils.getClientIpAddress());

        estadisticaPaginaDao.save(estadisticaPagina);
    }

    public void incrementaContadorNavegador(EstadisticaPagina estadisticaPagina) {

        estadisticaPagina.setIp(HttpReqRespUtils.getClientIpAddress());

        estadisticaPaginaDao.save(estadisticaPagina);
    }



}
