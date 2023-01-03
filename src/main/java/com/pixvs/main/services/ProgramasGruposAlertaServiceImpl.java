package com.pixvs.main.services;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.AlertasConfiguraciones;
import com.pixvs.main.models.projections.Alumno.AlumnoComboProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoEditarProjection;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.spring.services.ProcesadorAlertasService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
public class ProgramasGruposAlertaServiceImpl implements ProgramasGruposAlertaService {
    @Autowired
    private InscripcionDao inscripcionDao;
    @Autowired
    private AlumnoDao alumnoDao;
    @Autowired
    private ProcesadorAlertasService alertasService;
    @Autowired
    private LogController logController;
    @Autowired
    private ProgramaGrupoDao programaGrupoDao;



    public Boolean createAlerta(Integer inscripcionId, Integer nuevoGrupoId, Integer idUsuario, String codigoGrupo
    ) throws Exception {
        Inscripcion inscripcion = inscripcionDao.findById(inscripcionId);
        AlumnoComboProjection alumno = alumnoDao.findProjectedComboById(inscripcion.getAlumnoId());
        ProgramaGrupoEditarProjection grupoNuevo = programaGrupoDao.findProjectedEditarById(nuevoGrupoId);
        Boolean requiereAlerta = alertasService.validarAutorizacion(AlertasConfiguraciones.GRUPOS_CAMBIO_MULTISEDE, inscripcion.getId(), inscripcion.getCodigo(), "Cambio de grupo del alumno "+alumno.getCodigo()+" al grupo "+codigoGrupo, grupoNuevo.getSucursal().getId(), idUsuario, grupoNuevo.getSucursal().getNombre());
        if(requiereAlerta){
            Integer logTipo = LogTipo.ACEPTADA;
            logController.insertaLogUsuario(
                    new Log(null,
                            logTipo,
                            LogProceso.ALUMNO_MULTISEDE,
                            nuevoGrupoId
                    ),
                    idUsuario
            );
            return true;
        }else {
            return false;
        }



    }


}
