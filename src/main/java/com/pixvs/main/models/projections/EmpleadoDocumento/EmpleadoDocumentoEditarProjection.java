package com.pixvs.main.models.projections.EmpleadoDocumento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.EmpleadoDocumento;
import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Rene Carrillo on 24/03/2022.
 */
@Projection(types = {EmpleadoDocumento.class})
public interface EmpleadoDocumentoEditarProjection {

    Integer getId();

    Integer getEmpleadoId();

    ControlMaestroMultipleComboProjection getTipoDocumento();

    Integer getArchivoId();

    ArchivoProjection getArchivo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaVencimiento();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    Date getFechaVigencia();

    Boolean getActivo();

    Integer getTipoProcesoRHId();
}
