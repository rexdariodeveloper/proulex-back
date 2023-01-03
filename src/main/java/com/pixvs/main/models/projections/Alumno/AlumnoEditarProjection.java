package com.pixvs.main.models.projections.Alumno;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Alumno;
import com.pixvs.main.models.projections.AlumnoContacto.AlumnoContactoEditarProjection;
import com.pixvs.main.models.projections.AlumnoDatosFacturacion.AlumnoDatosFacturacionEditarProjection;

import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Municipio.MunicipioComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.util.DateUtil;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 28/05/2021.
 */
@Projection(types = {Alumno.class})
public interface AlumnoEditarProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    String getPrimerApellido();
    String getSegundoApellido();
    SucursalComboProjection getSucursal();
    String getCodigoUDG();
    String getCodigoUDGAlterno();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaNacimiento();
    PaisComboProjection getPaisNacimiento();
    EstadoComboProjection getEstadoNacimiento();
    String getCiudadNacimiento();
    MunicipioComboProjection getMunicipioNacimiento();
    ControlMaestroMultipleComboProjection getGenero();
    String getCurp();
    String getEmpresaOEscuela();
    String getProblemaSaludOLimitante();
    ControlMaestroMultipleComboProjection getMedioEnteradoProulex();
    ControlMaestroMultipleComboProjection getRazonEleccionProulex();
    Boolean getAlumnoJOBS();
    ControlMaestroMultipleComboProjection getProgramaJOBS();
    ControlMaestroMultipleComboProjection getCentroUniversitarioJOBS();
    ControlMaestroMultipleComboProjection getPreparatoriaJOBS();
    ControlMaestroMultipleComboProjection getCarreraJOBS();
    String getBachilleratoTecnologico();
    ArchivoProjection getFoto();
    Integer getFotoId();
    String getDomicilio();
    String getColonia();
    String getCp();
    PaisComboProjection getPais();
    EstadoComboProjection getEstado();
    String getCiudad();
    MunicipioComboProjection getMunicipio();
    String getTelefonoFijo();
    String getTelefonoMovil();
    String getTelefonoTrabajo();
    String getTelefonoTrabajoExtension();
    String getTelefonoMensajeriaInstantanea();
    String getCodigoAlumnoUDG();
    ControlMaestroMultipleComboProjection getTurno();
    ControlMaestroMultipleComboProjection getGrado();
    String getGrupo();
    ControlMaestroMultipleComboProjection getTipoAlumno();
    String getCorreoElectronico();
    Boolean getActivo();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
    String getFolio();
    String getDependencia();
    String getReferencia();
    List<AlumnoContactoEditarProjection> getContactos();
    List<AlumnoDatosFacturacionEditarProjection> getFacturacion();

}
