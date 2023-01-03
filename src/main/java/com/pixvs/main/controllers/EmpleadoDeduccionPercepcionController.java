package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.DeduccionPercepcion.DeduccionComboProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.EmpleadoDeduccionPercepcion.EmpleadoDeduccionPercepcionEditarProjection;
import com.pixvs.main.models.projections.EmpleadoDeduccionPercepcion.EmpleadoDeduccionPercepcionListadoProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.services.ArchivoXMLService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.services.ProcesadorAlertasService;
import com.pixvs.spring.storage.StorageService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

@RestController
@RequestMapping("/api/v1/empleado-deduccion-percepcion")
public class EmpleadoDeduccionPercepcionController {

    @Autowired
    private EmpleadoDeduccionPercepcionDao empleadoDeduccionPercepcionDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private DeduccionPercepcionDao deduccionPercepcionDao;
    @Autowired
    private HashId hashId;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private ArchivoXMLService archivoXMLService;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private ProcesadorAlertasService alertasService;

    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getSolicitudesPago(ServletRequest req) throws SQLException {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        List<EmpleadoDeduccionPercepcionListadoProjection> listado = empleadoDeduccionPercepcionDao.findProjectedListadoAllByActivoIsTrueOrderByCodigo();
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);
        List<EmpleadoDeduccionPercepcionListadoProjection> temp = new ArrayList<>();
        for(EmpleadoDeduccionPercepcionListadoProjection dato: listado){
            /*if(Arrays.stream(new List[]{sucursales}).anyMatch(x -> x == dato)){
                temp.add(dato);
            }*/
            for(SucursalComboProjection sucursal: sucursales){
                System.out.println("Sucursal: "+sucursal.getNombre());
                System.out.println("Percepcion: "+dato.getSucursal().getNombre());
                if(sucursal.getNombre().equals(dato.getSucursal().getNombre())){
                    temp.add(dato);
                }
            }
        }
        return new JsonResponse(temp, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody EmpleadoDeduccionPercepcion deduccionPercepcion, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        deduccionPercepcion.setModificadoPorId(idUsuario);
        if(deduccionPercepcion.getDeduccionPercepcion()!=null){
            deduccionPercepcion.setDeduccionPercepcionId(deduccionPercepcion.getDeduccionPercepcion().getId());
            deduccionPercepcion.setDeduccionPercepcion(null);
        }
        if(deduccionPercepcion.getEmpleado()!=null){
            deduccionPercepcion.setEmpleadoId(deduccionPercepcion.getEmpleado().getId());
            deduccionPercepcion.setEmpleado(null);
        }
        if(deduccionPercepcion.getTipoMovimiento()!=null){
            deduccionPercepcion.setTipoMovimientoId(deduccionPercepcion.getTipoMovimiento().getId());
            deduccionPercepcion.setTipoMovimiento(null);
        }
        if(deduccionPercepcion.getDocumentos() != null && deduccionPercepcion.getDocumentos().size() >0){
            for(EmpleadoDeduccionPercepcionDocumento documento : deduccionPercepcion.getDocumentos()){
                if(documento.getArchivo()!= null){
                    documento.setArchivoId(documento.getArchivo().getId());
                    documento.setArchivo(null);
                }
            }
        }
        //solicitud.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("SPRH"));
        deduccionPercepcion = empleadoDeduccionPercepcionDao.save(deduccionPercepcion);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save/multiple", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarMultiple(@RequestBody List<EmpleadoDeduccionPercepcion> deduccionPercepciones, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        List<String> cambios = new ArrayList<>();
        for(EmpleadoDeduccionPercepcion deduccion: deduccionPercepciones){
            if(deduccion.getTipoMovimiento().getId() == ControlesMaestrosMultiples.CMM_DEDPER_Tipo.DEDUCCION){
                deduccion.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("DED"));
            }else {
                deduccion.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("PER"));
            }
            deduccion.setFechaCreacion(new Time(System.currentTimeMillis()));
            deduccion.setActivo(true);
            deduccion.setCreadoPorId(idUsuario);
            if(deduccion.getDeduccionPercepcion()!=null){
                deduccion.setDeduccionPercepcionId(deduccion.getDeduccionPercepcion().getId());
                deduccion.setDeduccionPercepcion(null);
            }
            if(deduccion.getEmpleado()!=null){
                deduccion.setEmpleadoId(deduccion.getEmpleado().getId());
                deduccion.setEmpleado(null);
            }
            if(deduccion.getTipoMovimiento()!=null){
                deduccion.setTipoMovimientoId(deduccion.getTipoMovimiento().getId());
                deduccion.setTipoMovimiento(null);
            }
            if(deduccion.getSucursal()!=null){
                deduccion.setSucursalId(deduccion.getSucursal().getId());
                deduccion.setSucursal(null);
            }
            if(deduccion.getDocumentos() != null && deduccion.getDocumentos().size() >0){
                for(EmpleadoDeduccionPercepcionDocumento documento : deduccion.getDocumentos()){
                    documento.setActivo(true);
                    if(documento.getArchivo()!= null){
                        documento.setArchivoId(documento.getArchivo().getId());
                        documento.setArchivo(null);
                    }
                }
            }
            deduccion = empleadoDeduccionPercepcionDao.save(deduccion);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idSolicitud}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idSolicitud) throws SQLException {

        int actualizado = empleadoDeduccionPercepcionDao.actualizarActivo(hashId.decode(idSolicitud), false);
        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idOrdenCompra) throws SQLException {

        EmpleadoDeduccionPercepcionEditarProjection empleadoDeduccionPercepcion = empleadoDeduccionPercepcionDao.findProjectedEditarById(idOrdenCompra);

        return new JsonResponse(empleadoDeduccionPercepcion, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(idUsuario);

        HashMap<String, Object> json = new HashMap<>();

        List<ControlMaestroMultipleComboProjection> tipos = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_DEDPER_Tipo.NOMBRE);
        List<DeduccionComboProjection> percepciones = deduccionPercepcionDao.findAllByTipoIdAndActivoIsTrue(ControlesMaestrosMultiples.CMM_DEDPER_Tipo.PERCEPCION);
        List<DeduccionComboProjection> deducciones = deduccionPercepcionDao.findAllByTipoIdAndActivoIsTrue(ControlesMaestrosMultiples.CMM_DEDPER_Tipo.DEDUCCION);
        List<EmpleadoComboProjection> empleados = empleadoDao.findAllByEstatusIdNotIn(Arrays.asList(ControlesMaestrosMultiples.CMM_EMP_Estatus.BORRADO));
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);
        if(id != null){
            json.put("deduccionPercepcion",empleadoDeduccionPercepcionDao.findProjectedEditarById(id));
        }else{
            json.put("tipos", tipos);
            json.put("percepciones", percepciones);
            json.put("deducciones", deducciones);
            json.put("empleados", empleados);
            json.put("sucursales", sucursales);

            HashMap<String, Boolean> permisos = new HashMap<>();
            if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.MODIFICAR_SUELDO_HORA) != null)
                permisos.put("MODIFICAR_SUELDO_HORA", Boolean.TRUE);

            json.put("permisos", permisos);
        }
        
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_DEDUCCIONES_PERCEPCIONES_EXCEL] ORDER BY codigo";
        String[] alColumnas = new String[]{"CÓDIGO", "FECHA DE AJUSTE", "EMPLEADO", "TIPO", "CONCEPTO", "MONTO"};

        excelController.downloadXlsx(response, "deducciones_percepciones", query, alColumnas, null);
    }

}


