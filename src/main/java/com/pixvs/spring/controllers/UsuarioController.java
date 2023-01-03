package com.pixvs.spring.controllers;

import com.pixvs.main.dao.AlmacenDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.main.dao.UsuarioDatosAdicionalesDao;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.DepartamentoDao;
import com.pixvs.spring.dao.RolDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioEditarProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioListadoProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.BD;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.handler.exceptions.UsuarioException;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private RolDao rolDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private AlmacenDao almacenDao;

    @Autowired
    private UsuarioDatosAdicionalesDao usuarioDatosAdicionalesDao;

    @Autowired
    private DepartamentoDao departamentoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getUsuarios() throws SQLException {

        HashMap<String, Object> json = new HashMap<>();

        json.put("datos", usuarioDao.findProjectedByEstatusIdOrderByIdDesc(ControlesMaestrosMultiples.CMM_Estatus.ACTIVO));
        json.put("cmmEstatus", controlMaestroMultipleDao.findAllByControlInOrderByValor(new String[]{"CMM_Estatus"}));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getUsuariosFiltros(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        ArrayList<HashMap<String, Integer>> alEstatus = (ArrayList<HashMap<String, Integer>>) json.get("estatus");

        ArrayList<Integer> estatus = new ArrayList<>();
        for (HashMap<String, Integer> est : alEstatus) {
            estatus.add(est.get("id"));
        }


        String fechaCreacionDesde = (String) json.get("fechaCreacionDesde");
        String fechaCreacionHasta = (String) json.get("fechaCreacionHasta");

        Date dateFechaCreacionDesde = isNullorEmpty(fechaCreacionDesde) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionDesde);
        Date dateFechaCreacionHasta = isNullorEmpty(fechaCreacionHasta) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionHasta);

        List<UsuarioListadoProjection> usuarios = usuarioDao.findAllQueryProjectedBy(dateFechaCreacionDesde, dateFechaCreacionHasta, estatus);

        return new JsonResponse(usuarios, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Usuario usuario, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (usuario.getId() == null) {
            //if(usuario.getTipoId().equals(ControlesMaestrosMultiples.CMM_USU_TipoId.PLATAFORMA))
            usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
            usuario.setUsuarioCreadoPorId(idUsuario);
            usuario.setEstatus(new ControlMaestroMultiple(ControlesMaestrosMultiples.CMM_Estatus.ACTIVO));
            usuario.setRolId(usuario.getRol().getId());
        } else {
            Usuario usuarioActual = usuarioDao.findById(usuario.getId());

            try {
                if (usuarioActual.getUsuarioModificadoPorId() != null && !usuarioActual.getUsuarioModificadoPorId().equals(idUsuario)) {
                    concurrenciaService.verificarIntegridad(usuarioActual.getFechaModificacion(), usuario.getFechaModificacion());
                }
            } catch (Exception e) {
                return new JsonResponse("", usuarioActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }

            usuario.setUsuarioModificadoPorId(idUsuario);

            if (usuario.getImg64() != null && usuario.getArchivoId() != null) {
                fileSystemStorageService.borrarArchivo(usuario.getArchivoId());
            }
        }

        if (usuario.getImg64() != null) {
            Archivo archivo = fileSystemStorageService.storeBase64(usuario.getImg64(), idUsuario, 2, null, true, true);
            usuario.setArchivoId(archivo.getId());
        }
        if (usuario.getId() == null) {
            UsuarioComboProjection usr = usuarioDao.findByCorreoElectronico(usuario.getCorreoElectronico());

            try {
                if (usr != null) throw new Exception();
            } catch (Exception e) {
                return new JsonResponse("", usuario.getCorreoElectronico(), JsonResponse.STATUS_ERROR_CORREO_DUPLICADO);
            }
        }

        usuario = usuarioDao.save(usuario);

        return new JsonResponse(idUsuario == usuario.getId() ? usuarioDao.findProjectedById(usuario.getId()) : null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse cambiarContrasena(@RequestBody JSONObject json, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Usuario usuario = usuarioDao.findById(idUsuario);

        if (!passwordEncoder.matches((String) json.get("contraseniaAnterior"), usuario.getContrasenia())) {
            throw new UsuarioException("Contraseña incorrecta.");
        }

        if (BD.POSTGRES.equals(BD.PLATAFORM)) {
            usuarioDao.actualizarContraseniaPG(usuario.getId(), passwordEncoder.encode(json.get("contrasenia").toString()));
        } else {
            usuarioDao.actualizarContrasenia(usuario.getId(), passwordEncoder.encode(json.get("contrasenia").toString()));
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/change-password-usuario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse cambiarContrasenaUsuario(@RequestBody JSONObject json, ServletRequest req) throws Exception {

        Usuario usuario = usuarioDao.findById((int) json.get("usuarioId"));

        if (BD.POSTGRES.equals(BD.PLATAFORM)) {
            usuarioDao.actualizarContraseniaPG(usuario.getId(), passwordEncoder.encode(json.get("contrasenia").toString()));
        } else {
            usuarioDao.actualizarContrasenia(usuario.getId(), passwordEncoder.encode(json.get("contrasenia").toString()));
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/activo/{idUsuario}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getActivoById(@PathVariable Integer idUsuario) throws SQLException {

        UsuarioEditarProjection usuario = usuarioDao.findProjectedByIdAndEstatusId(idUsuario, ControlesMaestrosMultiples.CMM_Estatus.ACTIVO);

        return new JsonResponse(usuario, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/{idUsuario}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idUsuario) throws SQLException {

        UsuarioEditarProjection usuario = usuarioDao.findProjectedById(idUsuario);

        return new JsonResponse(usuario, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idUsuario}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idUsuario) throws SQLException {

        int actualizado = usuarioDao.actualizarEstatus(hashId.decode(idUsuario), ControlesMaestrosMultiples.CMM_Estatus.BORRADO);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadosArticuloById(@PathVariable(required = false) Integer id) throws SQLException {
        HashMap<String, Object> json = new HashMap<>();
        HashMap<String, Object> datosAdicionales = new HashMap<>();

        UsuarioEditarProjection usuario = null;

        if (id != null) {
            usuario = usuarioDao.findProjectedById(id);
        }

        json.put("usuario", usuario);
        json.put("cmmEstatus", controlMaestroMultipleDao.findAllByControl("CMM_Estatus"));
        json.put("cmmTipoUsuario", controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor("CMM_USU_TipoId"));
        json.put("roles", rolDao.findAllProjectedByActivoTrue());

        datosAdicionales.put("almacenes", almacenDao.findProjectedDatosAdicionalesByUsuarioId(id));
        datosAdicionales.put("departamentos", departamentoDao.findProjectedDatosAdicionalesByUsuarioId(id));

        json.put("datosAdicionales", datosAdicionales);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadCsv(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_USUARIOS]";
        if (BD.POSTGRES.equals(BD.PLATAFORM))
            query = "SELECT * FROM \"VW_LISTADO_USUARIOS\"";
        String[] alColumnas = new String[]{"Nombre", "Correo Electrónico", "Fecha Creación", "Estatus", "Rol"};

        excelController.downloadXlsx(response, "usuarios", query, alColumnas, null);
    }
}


