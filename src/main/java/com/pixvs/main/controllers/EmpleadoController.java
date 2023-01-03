package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoListadoProjection;
import com.pixvs.main.models.projections.Entidad.EntidadEditarProjection;
import com.pixvs.main.models.projections.PAProfesorCategoria.PAProfesorComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.TabuladorDetalle.TabuladorDetalleEmpleadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {
    @Autowired
    private Environment environment;

    @Autowired
    private EntidadDao entidadDao;

    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private EmpleadoContactoDao empleadoContactoDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private PaisDao paisDao;
    @Autowired
    private EstadoDao estadoDao;
    @Autowired
    private MonedaDao monedaDao;
    @Autowired
    private DepartamentoDao departamentoDao;
    @Autowired
    private HashId hashId;
    @Autowired
    private ExcelController excelController;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private FileSystemStorageService fileSystemStorageService;
    @Autowired
    private RolDao rolDao;
    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private PAProfesorCategoriaDao profesorCategoriaDao;
    @Autowired
    private EmpleadoDatoSaludDao empleadoDatoSaludDao;
    @Autowired
    private ControlMaestroDao controlMaestroDao;
    @Autowired
    private EmpleadoDocumentoDao empleadoDocumentoDao;
    @Autowired
    private ProgramaGrupoDao programaGrupoDao;
    @Autowired
    private TabuladorDetalleDao tabuladorDetalleDao;
    @Autowired
    private EmpleadoContratoDao empleadoContratoDao;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados() throws SQLException {

        List<EmpleadoListadoProjection> empleados = empleadoDao.findProjectedListadoAllByEmpleadoContratoIdNull();

        return new JsonResponse(empleados, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Empleado empleado, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Boolean actualizarDatos = empleado.getActualizarDatos();

        List<EmpleadoEditarProjection> empleadosExistentes;
        empleadosExistentes = empleadoDao.findAllByCorreoElectronico(empleado.getCorreoElectronico());

        if(empleado.getId()==null && empleadosExistentes!=null & empleadosExistentes.size()>0){
            return new JsonResponse(null,"Empleado con correo electrónico existente",JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
        }

        empleadosExistentes = empleadoDao.findAllByCodigoEmpleado(empleado.getCodigoEmpleado());
        if(empleado.getId()==null && empleadosExistentes!=null & empleadosExistentes.size()>0){
            return new JsonResponse(null,"Empleado con código de empleado existente",JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
        }

        if(empleado.getId()==null && usuarioDao.findProjectedLoginByCorreoElectronico(empleado.getCorreoElectronico())!=null){
            return new JsonResponse(null,"Usuario con correo electrónico existente",JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
        }

        if(empleado.getEstadoCivil() != null){
            empleado.setEstadoCivilId(empleado.getEstadoCivil().getId());
        }else{
            empleado.setEstadoCivilId(null);
        }
        if(empleado.getGenero() != null){
            empleado.setGeneroId(empleado.getGenero().getId());
        }else{
            empleado.setGeneroId(null);
        }
        if(empleado.getTipoEmpleado() != null){
            empleado.setTipoEmpleadoId(empleado.getTipoEmpleado().getId());
        }else{
            empleado.setTipoEmpleadoId(null);
        }
        if(empleado.getSucursal() != null){
            empleado.setSucursalId(empleado.getSucursal().getId());
        }else{
            empleado.setSucursalId(null);
        }
        if(empleado.getDepartamento() != null){
            empleado.setDepartamentoId(empleado.getDepartamento().getId());
        }else{
            empleado.setDepartamentoId(null);
        }
        if(empleado.getPaisNacimiento() != null){
            empleado.setPaisNacimientoId(empleado.getPaisNacimiento().getId());
        }else{
            empleado.setPaisNacimientoId(null);
        }
        if(empleado.getEstadoNacimiento() != null){
            empleado.setEstadoNacimientoId(empleado.getEstadoNacimiento().getId());
        }else{
            empleado.setEstadoNacimientoId(null);
        }
        if(empleado.getPais() != null){
            empleado.setPaisId(empleado.getPais().getId());
        }else{
            empleado.setPaisId(null);
        }
        if(empleado.getEstado() != null){
            empleado.setEstadoId(empleado.getEstado().getId());
        }else{
            empleado.setEstadoId(null);
        }
        if(empleado.getGradoEstudios() != null){
            empleado.setGradoEstudiosId(empleado.getGradoEstudios().getId());
            empleado.setGradoEstudios(null);
        }

        if(empleado.getNacionalidad() != null){
            empleado.setNacionalidadId(empleado.getNacionalidad().getId());
            empleado.setNacionalidad(null);
        }

        // Dato Salud
        for(EmpleadoDatoSalud datoSalud: empleado.getDatosSalud()){
            if(datoSalud.getTipoSangre() != null){
                datoSalud.setTipoSangreId(datoSalud.getTipoSangre().getId());
                datoSalud.setTipoSangre(null);
            }
        }

        // Beneficiario
        for(EmpleadoBeneficiario beneficiario: empleado.getBeneficiarios()){
            if(beneficiario.getParentesco() != null){
                beneficiario.setParentescoId(beneficiario.getParentesco().getId());
                beneficiario.setParentesco(null);
            }
        }

        for(EmpleadoContacto contacto : empleado.getContactos()){
            if(contacto.getId() == null){
                contacto.setCreadoPorId(idUsuario);
                contacto.setBorrado(false);
            }
            else {
                contacto.setModificadoPorId(idUsuario);
            }
        }

        if(empleado.getCursos() != null) {
            for (EmpleadoCurso curso : empleado.getCursos()) {
                if (curso.getIdioma() != null) {
                    curso.setIdiomaId(curso.getIdioma().getId());
                    curso.setIdioma(null);
                }
                if (curso.getPrograma() != null) {
                    curso.setProgramaId(curso.getPrograma().getId());
                    curso.setPrograma(null);
                }
            }
        }

        if(empleado.getCategorias() != null){
            for(EmpleadoCategoria categoria: empleado.getCategorias()){
                if(categoria.getCategoria() != null){
                    categoria.setCategoriaId(categoria.getCategoria().getId());
                    //categoria.setCategoria(null);
                }
                if(categoria.getIdioma() != null){
                    categoria.setIdiomaId(categoria.getIdioma().getId());
                    categoria.setIdioma(null);
                }
            }
        }

        // Archivos
        for(EmpleadoDocumento empleadoDocumento: empleado.getListaEmpleadoDocumento()){
            if(empleadoDocumento.getTipoDocumento() != null){
                empleadoDocumento.setTipoDocumentoId(empleadoDocumento.getTipoDocumento().getId());
                empleadoDocumento.setTipoDocumento(null);
            }

            if(empleadoDocumento.getId() < 0){
                empleadoDocumento.setCreadoPorId(idUsuario);
                empleadoDocumento.setFechaCreacion(new Date());
            }else{
                empleadoDocumento.setModificadoPorId(idUsuario);
                empleadoDocumento.setFechaUltimaModificacion(new Date());
            }
        }

        if(empleado.getUsuario()!=null) {
            if (empleado.getUsuario().getId() == null) {
                empleado.getUsuario().setContrasenia(passwordEncoder.encode(empleado.getUsuario().getContrasenia()));
                empleado.getUsuario().setUsuarioCreadoPorId(idUsuario);
                empleado.getUsuario().setEstatus(new ControlMaestroMultiple(ControlesMaestrosMultiples.CMM_Estatus.ACTIVO));
                empleado.getUsuario().setRolId(empleado.getUsuario().getRol().getId());
            } else {
                Usuario usuarioActual = usuarioDao.findById(empleado.getUsuario().getId());
                try {
                    concurrenciaService.verificarIntegridad(usuarioActual.getFechaModificacion(), empleado.getUsuario().getFechaModificacion());
                } catch (Exception e) {
                    return new JsonResponse("", usuarioActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
                }

                empleado.getUsuario().setUsuarioModificadoPorId(idUsuario);
                /*if (empleado.getUsuario().getImg64() != null && empleado.getUsuario().getArchivoId() != null) {
                    fileSystemStorageService.borrarArchivo(empleado.getUsuario().getArchivoId());
                }*/
            }

            if(empleado.getFotoId() != null){
                empleado.getUsuario().setArchivoId(empleado.getFotoId());
            }
            if (empleado.getUsuario().getId() == null) {
                UsuarioComboProjection usr = usuarioDao.findByCorreoElectronico(empleado.getUsuario().getCorreoElectronico());
                try {
                    if (usr != null) throw new Exception();
                } catch (Exception e) {
                    return new JsonResponse("Correo Duplicado", empleado.getUsuario().getCorreoElectronico(), JsonResponse.STATUS_ERROR_CORREO_DUPLICADO);
                }
            }
            if(empleado.getUsuario().getArchivoId() != null){
                try {
                    fileSystemStorageService.borrarArchivo(empleado.getFotoId());
                }
                catch (Exception e){}
            }
            Usuario temp = empleado.getUsuario();
            temp = usuarioDao.save(temp);
            empleado.setUsuarioId(temp.getId());
            empleado.setUsuario(null);
        }

        if (empleado.getId() < 0) {
            empleado.setCreadoPorId(idUsuario);
            empleado.setEstatusId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_Estatus.ACTIVO);
        }
        else {
            /*Empleado objetoActual =empleadoDao.findById(empleado.getId().intValue());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(),empleado.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }*/
            empleado.setModificadoPorId(idUsuario);
        }
        /*if(empleado.getFotoId() != null){
            try {
                fileSystemStorageService.borrarArchivo(empleado.getFotoId());
            }
            catch (Exception e){}
        }*/

        if (empleado.getImg64() != null) {
            Archivo archivo = fileSystemStorageService.storeBase64(empleado.getImg64(), idUsuario, 2, null, true, true);
            //empleado.getUsuario().setArchivoId(archivo.getId());
            empleado.setFotoId(archivo.getId());
            if(empleado.getUsuarioId() != null){
                //empleado.getUsuario().setArchivoId(archivo.getId());
                Usuario temp = usuarioDao.findById(empleado.getUsuarioId());
                temp.setArchivoId(archivo.getId());
                temp = usuarioDao.save(temp);
            }
        }




        empleado = empleadoDao.save(empleado);

        if(empleado.getCategorias() != null && actualizarDatos != null && actualizarDatos){
            List<ProgramaGrupo> gruposProfesor = programaGrupoDao.findAllByEmpleadoIdAndEstatusId(empleado.getId(), com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO);
            for(ProgramaGrupo grupo : gruposProfesor) {
                TabuladorDetalleEmpleadoProjection datosSueldo = tabuladorDetalleDao.findDatosEmpleadoSueldo(grupo.getProgramaIdiomaId(), empleado.getId(), grupo.getId());
                if(datosSueldo != null) {
                    grupo.setSueldoProfesor(datosSueldo.getSueldo());
                    grupo.setCategoriaProfesor(datosSueldo.getCategoria());
                    programaGrupoDao.save(grupo);
                }
            }
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idEmpleado}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idEmpleado) throws SQLException {

        EmpleadoEditarProjection empleado = empleadoDao.findProjectedEditarById(idEmpleado);

        return new JsonResponse(empleado, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idEmpleado}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idEmpleado) throws SQLException {

        int actualizado = empleadoDao.actualizarEstatusBorrado(hashId.decode(idEmpleado), com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_Estatus.BORRADO);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresById(@PathVariable(required = false) Integer id) throws SQLException {

        List<EstadoComboProjection> listaEstadoNacimiento = new ArrayList<>();
        List<EstadoComboProjection> listaEstado = new ArrayList<>();

        EmpleadoEditarProjection empleado = null;
        if (id != null) {
            empleado = empleadoDao.findProjectedEditarById(id);
            if(empleado.getUsuario()!=null){
                Usuario temp = usuarioDao.findById(empleado.getUsuario().getId());
                //Revisar si se actualizó la foto del usuario, actualizarla al empleado
                if(temp != null && temp.getArchivoId() != empleado.getFotoId()){
                    homologarFotografías(empleado.getId(),temp.getArchivoId());
                    empleado = empleadoDao.findProjectedEditarById(id);
                }
                //Revisar si se actualizó el nombre y apellidos
                homologarDatos(empleado.getId(),temp);
                empleado = empleadoDao.findProjectedEditarById(id);
                /*if(temp != null && temp.getNombre() != empleado.getNombre() ){
                    homologarDatos(empleado.getId(),temp.getNombre(),null,null,null);
                }
                if(temp != null && temp.getPrimerApellido() != empleado.getPrimerApellido()){
                    homologarDatos(empleado.getId(),null,temp.getPrimerApellido(),null,null);
                }
                if(temp != null && temp.getSegundoApellido() != empleado.getSegundoApellido())*/
                //Revisar si se actualizó el correo
            }

            if(empleado.getPaisNacimiento() != null) {
                listaEstadoNacimiento = estadoDao.findProjectedComboAllByPaisId(empleado.getPaisNacimiento().getId());
            }
            if(empleado.getPais() != null) {
                listaEstado = estadoDao.findProjectedComboAllByPaisId(empleado.getPais().getId());
            }
        }

        List<ControlMaestroMultipleComboProjection> listaGenero = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_GeneroId.NOMBRE);
        List<ControlMaestroMultipleComboProjection> idiomas = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_ART_Idioma.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaEstadoCivil = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_EstadoCivilId.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaTipoEmpleado = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_TipoEmpleadoId.NOMBRE);
        List<ControlMaestroMultipleComboProjection> parentesco = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_CE_Parentesco.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaGradoEstudio = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_GradoEstudios.NOMBRE);
        List<ControlMaestroMultipleComboProjection> listaNacionalidad = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_Nacionalidad.NOMBRE);
        List<PaisComboProjection> listaPais = paisDao.findProjectedComboAllBy();
        List<DepartamentoComboProjection> listaDepartamento = departamentoDao.findProjectedComboAllByActivoTrue();
        List<SucursalComboProjection> listaSucursal = sucursalDao.findProjectedComboAllByActivoTrue();
        List<PAProfesorComboProjection> categoriasProfesores = profesorCategoriaDao.findProjectedComboAllByActivoTrue();
        List<ControlMaestroMultipleComboProjection> listaTipoSangre = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_TipoSangre.NOMBRE);
        Boolean permisoNuevaContratacion = controlMaestroDao.findCMByNombre(com.pixvs.main.models.mapeos.ControlesMaestros.CMA_RH_NuevaContratacion).getValor().equals("1") ? true : false;
        List<ControlMaestroMultipleComboProjection> listaTipoDocumento = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_GEN_TipoDocumento.NOMBRE);

        HashMap<String, Object> json = new HashMap<>();

        json.put("empleado", empleado);
        json.put("listaGenero", listaGenero);
        json.put("listaGradoEstudio", listaGradoEstudio);
        json.put("listaNacionalidad", listaNacionalidad);
        json.put("parentesco", parentesco);
        json.put("listaEstadoCivil", listaEstadoCivil);
        json.put("listaTipoEmpleado", listaTipoEmpleado);
        json.put("listaPais", listaPais);
        json.put("listaEstadoNacimiento", listaEstadoNacimiento);
        json.put("listaEstado", listaEstado);
        json.put("listaTipoSangre", listaTipoSangre);
        json.put("listaDepartamento", listaDepartamento);
        json.put("listaSucursal", listaSucursal);
        json.put("cmmEstatus", controlMaestroMultipleDao.findAllByControl("CMM_Estatus"));
        json.put("roles", rolDao.findAllProjectedByActivoTrue());
        json.put("categoriasProfesor",categoriasProfesores);
        json.put("idiomas",idiomas);
        json.put("permisoNuevaContratacion",permisoNuevaContratacion);
        json.put("listaTipoDocumento", listaTipoDocumento);
        if(empleado != null && empleado.getId() != null)
            json.put("empleadoContrato", empleadoContratoDao.findByEmpleadoIdAndEstatusId(empleado.getId(), com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_EMP_Estatus.ACTIVO));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "EXEC sp_getListadoEmpleados";
        String columnas = "Código de empleado,Nombre,Fecha de nacimiento,País nacimientinto,Estado de nacimiento,Estado civil,Genero,RFC,CURP,Correo,Estatus,Tipo de empleado,Grado de estudios,Nacionalidad," +
                "Departamento,Fecha de alta,Sucursal,Domicilio,Colonia,Código postal,País,Estado,Ciudad,Telefono,Beneficiarios";

        List<ControlMaestroMultipleComboProjection> idiomas = controlMaestroMultipleDao.findAllByControl("CMM_ART_Idioma");
        for(ControlMaestroMultipleComboProjection Idioma:  idiomas){
            columnas +=","+Idioma.getValor();
        }
        String[] alColumnas = columnas.split(",");

        excelController.downloadXlsx(response, "empleados", query, alColumnas, null,"Empleados");
    }

    public void homologarFotografías(Integer empleadoId, Integer archivoId){
        Empleado empleado = empleadoDao.findById(empleadoId);
        empleado.setFotoId(archivoId);
        empleadoDao.save(empleado);
    }

    public void homologarDatos(Integer empleadoId, Usuario usuario){
        Empleado empleado = empleadoDao.findById(empleadoId);
        if(!usuario.getNombre().equals(empleado.getNombre())){
            empleado.setNombre(usuario.getNombre());
        }
        if(!usuario.getPrimerApellido().equals(empleado.getPrimerApellido())){
            empleado.setPrimerApellido(usuario.getPrimerApellido());
        }
        if(usuario.getSegundoApellido() != null && !usuario.getSegundoApellido().equals(empleado.getSegundoApellido())){
            empleado.setSegundoApellido(usuario.getSegundoApellido());
        }
        if(!usuario.getCorreoElectronico().equals(empleado.getCorreoElectronico())){
            empleado.setCorreoElectronico(usuario.getCorreoElectronico());
        }
        empleado = empleadoDao.save(empleado);
    }

}

