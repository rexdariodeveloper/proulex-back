package com.pixvs.main.models.projections.Cliente;

import com.pixvs.main.models.Cliente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 17/06/2021.
 */
@Projection(types = {Cliente.class})
public interface ClienteDatosFacturarProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    String getRFC();
    String getRazonSocial();
    @Value("#{target.domicilio + ', ' + target.colonia + ', ' + target.cp}")
    String getDireccion();
    @Value("#{target.pais.nombre + ', ' + target.estado.nombre + ', ' + target.ciudad}")
    String getCiudadOrigen();

}
