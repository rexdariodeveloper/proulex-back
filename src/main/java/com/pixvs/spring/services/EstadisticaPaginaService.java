package com.pixvs.spring.services;

import com.pixvs.spring.models.EstadisticaPagina;

/**
 * Created by David Arroyo on 7/7/2020.
 */
public interface EstadisticaPaginaService {
    void incrementaContador(String url, int tipo);

    void incrementaContadorNavegador(EstadisticaPagina estadisticaPagina);
}
