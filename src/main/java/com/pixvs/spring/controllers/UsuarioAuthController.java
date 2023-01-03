package com.pixvs.spring.controllers;

import com.pixvs.main.models.Usuario;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.handler.exceptions.UsuarioException;
import com.pixvs.spring.models.*;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.mapeos.Roles;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import com.pixvs.spring.models.projections.Municipio.MunicipioComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.models.projections.RolMenu.RolMenuEditarProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioEditarProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioLoginProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioLoginVerificarProjection;
import com.pixvs.spring.models.projections.UsuarioRecuperacion.UsuarioRecuperacionProjection;
import com.pixvs.spring.util.*;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/usuario")
public class UsuarioAuthController {

    @Autowired
    private Environment environment;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private UsuarioRecuperacionDao usuarioRecuperacionDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RolMenuDao rolMenuDao;

    @Autowired
    private EmpresaPermisoComponenteDao permisoComponenteDao;

    @Autowired
    private Email email;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HashId hashId;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private RolDao rolDao;

    @Autowired
    private PaisDao paisDao;

    @Autowired
    private EstadoDao estadoDao;

    @Autowired
    private MunicipioDao municipioDao;

    @Transactional
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResponse loginAdmin(@RequestBody Map<String, String> json) throws UsuarioException, SQLException, UnsupportedEncodingException {
        String correo = json.get("usuario");
        String contrasenia = json.get("contrasenia");


        //Validamos que el dato usuario y contraseña no vengan NULL
        if (correo == null || contrasenia == null) {
            throw new UsuarioException("Usuario o Contraseña no pueden ir vacios.");
        }

        //Buscamos al AbstractUsuario por Nombre de AbstractUsuario y Contraseña
        UsuarioLoginVerificarProjection usuarioLogin = usuarioDao.findProjectedLoginByCorreoElectronico(correo);

        //Si no encontramos alguno, mandamos error
        if (usuarioLogin == null) {
            throw new UsuarioException("Usuario no encontrado.");
        }

        //Verificamos la contraseña
        if (!passwordEncoder.matches(contrasenia, usuarioLogin.getContrasenia())) {
            throw new UsuarioException("Contraseña incorrecta.");
        }

        //Verificamos si el AbstractUsuario esta Activo, si no lo esta mandamos error
        if (usuarioLogin.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_Estatus.ACTIVO) {
            throw new UsuarioException("Usuario no activo.");
        }

        JSONObject jsonLogin = new JSONObject();

        if(!Boolean.valueOf(environment.getProperty("environments.required-verification-code", "false"))) {
            UsuarioLoginProjection usuario = usuarioDao.findProjectedLoginById(usuarioLogin.getId());

            //Obtenemos el menú
            JSONObject alMenu = getMenu(usuario.getRolId(), ControlesMaestrosMultiples.CMM_SYS_SistemaAcceso.WEB, null);

            //Generamos el Token del usuario
            String token = JWT.generaTokenPixvs(environment.getProperty("environments.pixvs.empresa"), usuario, environment.getProperty("environments.jwt.secret"));

            jsonLogin.put("token", token);
            jsonLogin.put("usuario", usuario);
            jsonLogin.put("menu", alMenu);

            //Actualizamos la última sesión
            if (BD.POSTGRES.equals(BD.PLATAFORM)) {
                usuarioDao.actualizarInicioSesionPG(usuario.getId());
            } else {
                usuarioDao.actualizarInicioSesion(usuario.getId());
            }
        } else {
            String codigo = this.generarCodigoVerificacion();
            Integer timeout = Integer.valueOf(environment.getProperty("environments.verification-code-timeout","15"));

            if (BD.POSTGRES.equals(BD.PLATAFORM)) {
                usuarioDao.actualizarCodigoPG(codigo,timeout,usuarioLogin.getId());
            } else {
                usuarioDao.actualizarCodigo(codigo,timeout,usuarioLogin.getId());
            }

            this.email.sendEmail(correo, environment.getProperty("environments.pixvs.empresa") + " - Código de verificación", "" + "\n\nTu código para ingresar a la plataforma es:\n\n"+codigo);

            jsonLogin.put("verification", true);
        }

        return new JsonResponse(jsonLogin, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/verificar", method = RequestMethod.POST)
    public JsonResponse verificar(@RequestBody Map<String, String> json) throws UsuarioException, SQLException, UnsupportedEncodingException {

        String correo = json.get("usuario");
        String codigo = json.get("codigo");

        if (codigo == null) {
            throw new UsuarioException("El código no puede ir vacío.");
        }

        //Buscamos al AbstractUsuario por Nombre de AbstractUsuario y Contraseña
        UsuarioLoginVerificarProjection usuarioLogin = usuarioDao.findProjectedLoginByCorreoElectronico(correo);

        //Si no encontramos alguno, mandamos error
        if (usuarioLogin == null) {
            throw new UsuarioException("Usuario no encontrado.");
        }

        //Verificamos si el AbstractUsuario esta Activo, si no lo esta mandamos error
        if (usuarioLogin.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_Estatus.ACTIVO) {
            throw new UsuarioException("Usuario no activo.");
        }

        JSONObject jsonLogin = new JSONObject();

        UsuarioLoginProjection usuario = usuarioDao.findProjectedByCorreoElectronicoAndCodigoAndVencimientoGreaterThan(correo, codigo, new Date());

        if(usuario == null){
            throw new UsuarioException("Datos incorrectos");
        }

        //Obtenemos el menú
        JSONObject alMenu = getMenu(usuario.getRolId(), ControlesMaestrosMultiples.CMM_SYS_SistemaAcceso.WEB, null);

        //Generamos el Token del usuario
        String token = JWT.generaTokenPixvs(environment.getProperty("environments.pixvs.empresa"), usuario, environment.getProperty("environments.jwt.secret"));

        jsonLogin.put("token", token);
        jsonLogin.put("usuario", usuario);
        jsonLogin.put("menu", alMenu);

        //Actualizamos la última sesión
        if (BD.POSTGRES.equals(BD.PLATAFORM)) {
            usuarioDao.actualizarInicioSesionPG(usuario.getId());
        } else {
            usuarioDao.actualizarInicioSesion(usuario.getId());
        }


        return new JsonResponse(jsonLogin, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/recuperacion/generar", method = RequestMethod.POST)
    public JsonResponse generarTokenRecuperacion(@RequestBody Map<String, String> json) throws UsuarioException, SQLException, UnsupportedEncodingException {

        String email = json.get("email");


        //Validamos que el dato usuario y contraseña no vengan NULL
        if (email == null) {
            throw new UsuarioException("El email no puede ir vacío.");
        }

        //Buscamos al AbstractUsuario por Email
        UsuarioComboProjection usuario = usuarioDao.findByCorreoElectronico(email);

        //Si no encontramos alguno, mandamos error
        if (usuario == null) {
            throw new UsuarioException("Correo Electrónico no encontrado.");
        }

        //Verificamos si el AbstractUsuario esta Activo, si no lo esta mandamos error
        if (usuario.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_Estatus.ACTIVO) {
            throw new UsuarioException("Usuario no activo.");
        }

        UsuarioRecuperacion usuarioRecuperacion = usuarioRecuperacionDao.findByIdAndEstatus(usuario.getId(), ControlesMaestrosMultiples.CMM_USUR_Estatus.SOLICITADO);
        if (usuarioRecuperacion == null) {
            usuarioRecuperacion = new UsuarioRecuperacion(usuario.getId(), UUID.randomUUID().toString());
            usuarioRecuperacion.setFechaSolicitud(new Timestamp(new Date().getTime()));
        }

        usuarioRecuperacionDao.save(usuarioRecuperacion);

        this.email.sendEmail(email, environment.getProperty("environments.pixvs.empresa") + " - Recuperación de Contraseña", "" + "\n\nDa clic en el siguiente vínculo para reestablecer tu contraseña:\n\n" + environment.getProperty("environments.pixvs.front.url") + "/acceso/reset-password?tkn=" + usuarioRecuperacion.getToken());

        //Retornamos Login Correcto
        return new JsonResponse(null, "Revisa tu correo", "Token generado");
    }


    @RequestMapping(value = "/recuperacion/buscar", method = RequestMethod.POST)
    public JsonResponse buscarTokenRecuperacion(@RequestBody Map<String, String> json) throws UsuarioException, SQLException, UnsupportedEncodingException {

        String token = json.get("token");


        //Validamos que el dato usuario y contraseña no vengan NULL
        if (token == null) {
            throw new UsuarioException("El token no puede ir vacío.");
        }

        //Integer estatus = usuarioService.actualizarEstadoRecuperacion(token);
        UsuarioRecuperacionProjection usuarioRecuperacion = usuarioRecuperacionDao.findProjectionByToken(token);

        if (usuarioRecuperacion.getEstatus().intValue() == ControlesMaestrosMultiples.CMM_USUR_Estatus.EXPIRADO) {
            throw new UsuarioException("El token ha expirado.");
        }

        if (usuarioRecuperacion.getEstatus().intValue() == ControlesMaestrosMultiples.CMM_USUR_Estatus.USADO) {
            throw new UsuarioException("El token ya ha sido utilizado.");
        }

        //Retornamos Login Correcto
        return new JsonResponse(usuarioRecuperacion, "Token válido", "Recuperar contraseña");
    }

    @Transactional
    @RequestMapping(value = "/recuperacion/reestablecer", method = RequestMethod.POST)
    public JsonResponse reestablecerContrasenia(@RequestBody Map<String, String> json) throws UsuarioException, SQLException, UnsupportedEncodingException {

        String token = json.get("token");
        String contrasenia = json.get("contrasenia");

        //Validamos que el dato usuario y contraseña no vengan NULL
        if (token == null) {
            throw new UsuarioException("Token o Contraseña no pueden ir vacios.");
        }

        //Buscamos al AbstractUsuario por Email
        UsuarioRecuperacion recuperacion = usuarioRecuperacionDao.findByToken(token);
        Integer estatus = recuperacion.getEstatus().intValue();
        if (recuperacion.getEstatus().intValue() == ControlesMaestrosMultiples.CMM_USUR_Estatus.SOLICITADO) {
            recuperacion.setEstatus(ControlesMaestrosMultiples.CMM_USUR_Estatus.EXPIRADO);
            recuperacion.setFechaUltimaModificacion(new Timestamp(System.currentTimeMillis()));
            recuperacion = usuarioRecuperacionDao.save(recuperacion);
        }


        if (estatus == ControlesMaestrosMultiples.CMM_USUR_Estatus.EXPIRADO) {
            throw new UsuarioException("El token ha expirado.");
        }

        if (estatus == ControlesMaestrosMultiples.CMM_USUR_Estatus.USADO) {
            throw new UsuarioException("El token ya ha sido utilizado.");
        }

        if (contrasenia == null)
            throw new UsuarioException("No se ha podido restablecer la contraseña.");


        recuperacion.setEstatus(ControlesMaestrosMultiples.CMM_USUR_Estatus.USADO);
        recuperacion.setFechaUltimaModificacion(new Timestamp(System.currentTimeMillis()));
        usuarioRecuperacionDao.save(recuperacion);

        if (BD.POSTGRES.equals(BD.PLATAFORM)) {
            usuarioDao.actualizarContraseniaPG(recuperacion.getUsuario().getId(), passwordEncoder.encode(contrasenia));
        } else {
            usuarioDao.actualizarContrasenia(recuperacion.getUsuario().getId(), passwordEncoder.encode(contrasenia));
        }


        //Retornamos Login Correcto
        return new JsonResponse(null, "Contraseña actualizada", "Recuperar contraseña");
    }

    private JSONObject getMenu(Integer rolId, Integer sistemaAccesoId, List<MenuPrincipal> alMenu) {
        if (alMenu == null) {
            alMenu = menuDao.findAllProjectedAllByUsuarioIdAndSistemaAccesoIdOrderByOrdenAsc(rolId, sistemaAccesoId, -1);
            setSelectedRol(alMenu, rolId);
        }

        JSONObject jsonExtras = new JSONObject();
        JSONObject jsonEN = new JSONObject();
        JSONObject jsonES = new JSONObject();
        JSONObject jsonTraducciones = new JSONObject();

        jsonEN.put("lang", "en");
        jsonEN.put("data", new JSONObject());

        jsonES.put("lang", "es");
        jsonES.put("data", new JSONObject());

        jsonTraducciones.put("EN", jsonEN);
        jsonTraducciones.put("ES", jsonES);
        jsonExtras.put("traducciones", jsonTraducciones);
        jsonExtras.put("permisos", new ArrayList<String>());

        List<EmpresaPermisoComponente> permisosComponentes =
                permisoComponenteDao.findAllByEmpresaIdAndRolId(environment.getProperty("environments.empresa.id", Integer.class), rolId);

        jsonExtras = getChildren(rolId, sistemaAccesoId, alMenu, jsonExtras, permisosComponentes);

        if (rolId != null) {
            eliminaNodosSinPermiso(alMenu); //elimina nodos
        }

        JSONObject jsonMenu = new JSONObject();
        jsonMenu.put("navigation", alMenu);
        jsonMenu.put("traducciones", jsonExtras.get("traducciones"));
        jsonMenu.put("permisos", jsonExtras.get("permisos"));

        return jsonMenu;
    }

    private void eliminaNodosSinPermiso(List<MenuPrincipal> alMenu) {
        if (alMenu != null) {
            Iterator<MenuPrincipal> it = alMenu.iterator();
            while (it.hasNext()) {
                MenuPrincipal menu = it.next();
                 if (menu != null && menu.getChildren().size() > 0) {
                    eliminaNodosSinPermiso(menu.getChildren());
                 }
                if (menu.getChildren() != null && menu.getChildren().size() == 0 && !menu.getSelected()) {
                    it.remove();
                }
            }
        }
    }

    private void setSelectedRol(List<MenuPrincipal> alMenu, Integer rolId) {
        for (MenuPrincipal menu : alMenu) {
            RolMenuEditarProjection rolMenuEditarProjection = rolMenuDao.findAllProjectedByRolIdAndAndNodoId(rolId, menu.getId());
            if (rolMenuEditarProjection != null) {

                if(menu.getRepetible() && !menu.getSelected()){
                    menu.setUrl(menu.getUrl()+"/"+hashId.encode(menu.getId()));
                }
                menu.setSelected(true);
                menu.setRolMenu(rolMenuEditarProjection);
            }
        }
    }

    public JSONObject getChildren(Integer rolId, Integer sistemaAccesoId, List<MenuPrincipal> alMenu, JSONObject jsonExtras, List<EmpresaPermisoComponente> permisosComponentes) {
        for (MenuPrincipal menu : alMenu) {
            ((JSONObject) ((JSONObject) ((JSONObject) jsonExtras.get("traducciones")).get("ES")).get("data")).put("M" + menu.getId(), menu.getTitle());
            ((JSONObject) ((JSONObject) ((JSONObject) jsonExtras.get("traducciones")).get("EN")).get("data")).put("M" + menu.getId(), menu.getTitleEN());

            if (menu.getUrl() != null) {
                ((ArrayList<String>) jsonExtras.get("permisos")).add(menu.getUrl() + (menu.getRepetible() && !menu.getSelected() ? "/" + hashId.encode(menu.getId()) : ""));
            }

            if (permisosComponentes != null) {
                menu.getPermisosComponentes().addAll(permisosComponentes.stream().filter(item -> item.getNodoId().equals(menu.getId())).collect(Collectors.toList()));
            }

            List<MenuPrincipal> alChildren = rolId != null
                    ? menuDao.findAllProjectedAllByUsuarioIdAndSistemaAccesoIdOrderByOrdenAsc(rolId, sistemaAccesoId, menu.getId())
                    : menuDao.getMenuPublico(sistemaAccesoId, menu.getId());

            setSelectedRol(alMenu, rolId);

            if (alChildren.size() > 0) {
                menu.getChildren().addAll(alChildren);
                getChildren(rolId, sistemaAccesoId, alChildren, jsonExtras, permisosComponentes);
            }
        }

        return jsonExtras;
    }

    private String generarCodigoVerificacion(){
        Random random = new Random(System.currentTimeMillis());
        String codigoVerificacion = "";
        for(int i=0 ; i<6 ; i++){
            codigoVerificacion += String.valueOf(random.nextInt(10));
        }
        return codigoVerificacion;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public JsonResponse reestablecerContrasenia() throws Exception {
        List<MenuPrincipal> menu = menuDao.getMenuPublico(ControlesMaestrosMultiples.CMM_SYS_SistemaAcceso.APP, -1);
        JSONObject alMenu = getMenu(null,ControlesMaestrosMultiples.CMM_SYS_SistemaAcceso.APP,menu);
        return new JsonResponse(alMenu,"Ok");
    }
    ////Request para token de SIIAU_GENERAL
    @Transactional
    @RequestMapping(value = "/login_siiau_general", method = RequestMethod.POST)
    public JsonResponse loginSIIAUGENERAL(@RequestBody Map<String, String> json) throws UsuarioException, SQLException, UnsupportedEncodingException, MalformedURLException {
        String code = json.get("codigo");
        String contrasenia = json.get("psw");
        String url = "https://ms.mw.siiau.udg.mx/WSEscolarCentroWeb-war/webresources/WSEscolarCentroWeb/login";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Username", "SISCERTIFICADO");
        headers.add("Password", "WSCERTUDG22");
        Map<String, Object> map = new HashMap<>();
        map.put("usuario", code);
        map.put("contrania", contrasenia);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<String> response;

        //Validamos que el dato usuario y contraseña no vengan NULL
        if (code == null || contrasenia == null) {
            throw new UsuarioException("Código o Contraseña no pueden ir vacios.");
        }

        if (usuarioDao.findProjectedByCodigo(code) == null) throw new UsuarioException("Usuario no esta dado de alta en el sistema");
        else{
            UsuarioEditarProjection usuarioVerificar =usuarioDao.findProjectedByCodigo(code);
            try{
                response =  restTemplate.postForEntity(url, entity, String.class);

            }catch (HttpServerErrorException ex) {
                throw new UsuarioException("Problemas de conexión con SIIAU, favor de intentar más tarde");
            }catch (HttpClientErrorException exc){
                throw new UsuarioException("La IP no coincide con el usuario");
            }
            if(response.getStatusCode().value()==200 && response.getBody().toString().equals("respuesta:true")){
                //Buscamos al AbstractUsuario por Nombre de AbstractUsuario y Contraseña
                UsuarioLoginVerificarProjection usuarioLogin = usuarioDao.findProjectedLoginByCorreoElectronico(usuarioVerificar.getCorreoElectronico());

                //Si no encontramos alguno, mandamos error
                if (usuarioLogin == null) {
                    throw new UsuarioException("Usuario no encontrado.");
                }

                //Verificamos si el AbstractUsuario esta Activo, si no lo esta mandamos error
                if (usuarioLogin.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_Estatus.ACTIVO) {
                    throw new UsuarioException("Usuario no activo.");
                }

                JSONObject jsonLogin = new JSONObject();

                if(!Boolean.valueOf(environment.getProperty("environments.required-verification-code", "false"))) {
                    UsuarioLoginProjection usuario = usuarioDao.findProjectedLoginById(usuarioLogin.getId());

                    //Obtenemos el menú
                    JSONObject alMenu = getMenu(usuario.getRolId(), ControlesMaestrosMultiples.CMM_SYS_SistemaAcceso.WEB, null);

                    //Generamos el Token del usuario
                    String token = JWT.generaTokenPixvs(environment.getProperty("environments.pixvs.empresa"), usuario, environment.getProperty("environments.jwt.secret"));

                    jsonLogin.put("token", token);
                    jsonLogin.put("usuario", usuario);
                    jsonLogin.put("menu", alMenu);

                    //Actualizamos la última sesión
                    if (BD.POSTGRES.equals(BD.PLATAFORM)) {
                        usuarioDao.actualizarInicioSesionPG(usuario.getId());
                    } else {
                        usuarioDao.actualizarInicioSesion(usuario.getId());
                    }
                } else {
                    String codigo = this.generarCodigoVerificacion();
                    Integer timeout = Integer.valueOf(environment.getProperty("environments.verification-code-timeout","15"));

                    if (BD.POSTGRES.equals(BD.PLATAFORM)) {
                        usuarioDao.actualizarCodigoPG(codigo,timeout,usuarioLogin.getId());
                    } else {
                        usuarioDao.actualizarCodigo(codigo,timeout,usuarioLogin.getId());
                    }

                    this.email.sendEmail(usuarioVerificar.getCorreoElectronico(), environment.getProperty("environments.pixvs.empresa") + " - Código de verificación", "" + "\n\nTu código para ingresar a la plataforma es:\n\n"+codigo);

                    jsonLogin.put("verification", true);
                }
                return new JsonResponse(jsonLogin, null, JsonResponse.STATUS_OK);
            }else{
            return new JsonResponse(null, "Error de credenciales", JsonResponse.STATUS_ERROR_USUARIO_CREDENCIALES);
            }
        }
    }

    //Request para token de SIIAU
    @Transactional
    @RequestMapping(value = "/login_siiau", method = RequestMethod.POST)
    public JsonResponse loginSIIAU(@RequestBody Map<String, String> json) throws UsuarioException, SQLException, UnsupportedEncodingException, MalformedURLException {
        String code = json.get("codigo");
        String contrasenia = json.get("psw");

        //Validamos que el dato usuario y contraseña no vengan NULL
        if (code == null || contrasenia == null) {
            throw new UsuarioException("Código o Contraseña no pueden ir vacios.");
        }


        JSONObject jsonLogin = new JSONObject();
        UsuarioLoginVerificarProjection usuarioLogin = null;

        if(contrasenia.length() > 10){
            contrasenia = contrasenia.substring(0, 10);
        }

        String tokenSIIAU = JWT.generaTokenSiiauCucea(code, contrasenia,"", JWT.SCRAPAPI_SECRET);

        //Verificamos el token haciendo una consulta a la API
        Integer status = null;
        JSONObject datos = new JSONObject();

        ResponseEntity<JSONObject> response;
        try{
            response = JWT.getDatosSiiau("acceso/?token=" + tokenSIIAU);
        }catch (HttpServerErrorException ex){
            throw new UsuarioException("Problemas de conexión con SIIAU, favor de intentar más tarde");

        }


        status = response.getStatusCodeValue();
        datos = response.getBody();

        if(status == 200 && datos.get("error") == null){
            //Buscamos al AbstractUsuario por el tipo de usuario
            usuarioLogin = usuarioDao.findProjectedLoginByTipoId(ControlesMaestrosMultiples.CMM_USU_TipoId.ESTUDIANTE_SIIAU);

        }else{
            String error = (String) datos.get("error");
            return new JsonResponse(null, error, JsonResponse.STATUS_ERROR_USUARIO_CREDENCIALES);
        }

        //Si no encontramos alguno, mandamos error
        if (usuarioLogin == null) {
            throw new UsuarioException("Usuario no encontrado.");
        }

        //Verificamos si el AbstractUsuario esta Activo, si no lo esta mandamos error
        if (usuarioLogin.getEstatusId().intValue() != ControlesMaestrosMultiples.CMM_Estatus.ACTIVO) {
            throw new UsuarioException("Usuario no activo.");
        }

        UsuarioLoginProjection usuario = usuarioDao.findProjectedLoginById(usuarioLogin.getId());

        //Obtenemos el menú
        JSONObject alMenu = getMenu(usuario.getRolId(), ControlesMaestrosMultiples.CMM_SYS_SistemaAcceso.WEB, null);

        //Generamos el Token del usuario
        String token = JWT.generaTokenPixvs(environment.getProperty("environments.pixvs.empresa"), usuario, environment.getProperty("environments.jwt.secret"));

        jsonLogin.put("token", token);
        jsonLogin.put("usuario", usuario);
        jsonLogin.put("menu", alMenu);
        jsonLogin.put("tokenSIIAU", tokenSIIAU);
        jsonLogin.put("datosSIIAU", datos);

        //Actualizamos la última sesión
        if (BD.POSTGRES.equals(BD.PLATAFORM)) {
            usuarioDao.actualizarInicioSesionPG(usuario.getId());
        } else {
            usuarioDao.actualizarInicioSesion(usuario.getId());
        }

        return new JsonResponse(jsonLogin, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResponse registrar(@RequestBody Usuario usuario) throws UsuarioException, SQLException, UnsupportedEncodingException {

        if (usuario.getCorreoElectronico() == null || usuario.getContrasenia() == null) {
            throw new UsuarioException("Usuario o Contraseña no pueden ir vacios.");
        }

        UsuarioLoginVerificarProjection usuarioLogin = usuarioDao.findProjectedLoginByCorreoElectronico(usuario.getCorreoElectronico());
        if (usuarioLogin != null) {
            return new JsonResponse(null, "El correo electrónico ya ha sido registrado.", 500);
        }
        usuario.setEstatusId(ControlesMaestrosMultiples.CMM_Estatus.INACTIVO);
        ControlMaestroMultiple estatus = controlMaestroMultipleDao.findCMMById(ControlesMaestrosMultiples.CMM_Estatus.INACTIVO);
        usuario.setEstatus(estatus);
        Rol rol = rolDao.findById(Roles.PUBLICO_GENERAL);
        usuario.setRol(rol);
        Pais pais = paisDao.findById(usuario.getPaisId());
        usuario.setPais(pais);
        if (usuario.getEstadoId() != null){
            Estado estado = estadoDao.findById(usuario.getEstadoId());
            usuario.setEstado(estado);
        }
        if (usuario.getMunicipioId() != null){
            Municipio municipio = municipioDao.findById(usuario.getMunicipioId());
            usuario.setMunicipio(municipio);
        }
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        usuario = usuarioDao.save(usuario);

        String userId = hashId.encode(usuario.getId());

        this.email.sendEmail(usuario.getCorreoElectronico(), environment.getProperty("environments.pixvs.empresa") + " - Verificación de cuenta", "" + "\n\nPara poder utilizar tu cuenta debes confirmar la dirección de correo electrónico dando click en el siguiente enlace:\n\n" + environment.getProperty("environments.pixvs.front.url") + "/acceso/mail-confirm?userId=" + userId);
        //System.out.println( "Para poder utilizar tu cuenta debes confirmar la dirección de correo electrónico dando click en el siguiente enlace:\n\n" + environment.getProperty("environments.pixvs.front.url") + "/acceso/mail-confirm?userId=" + userId);
        return new JsonResponse(null, "Usuario registrado exitosamente!", 200);
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public JsonResponse confirmarCorreo(@RequestBody Map<String, String> json) throws UsuarioException, SQLException, UnsupportedEncodingException {

        String usuarioId = json.get("usuarioId");
        Usuario usuario = usuarioDao.findById(hashId.decode(usuarioId));

        if(usuario != null)
            usuarioDao.actualizarEstatus(usuario.getId(), ControlesMaestrosMultiples.CMM_Estatus.ACTIVO);
        else
            throw new UsuarioException("Usuario no encontrado.");

        return new JsonResponse(null, "Correo confirmado!", 200);
    }

    @RequestMapping(value = "/listados/paises", method = RequestMethod.GET)
    public JsonResponse getPaises(){
        List<PaisComboProjection> paises = paisDao.findProjectedComboAllBy();
        return new JsonResponse(paises, "", 200);
    }

    @RequestMapping(value = "/listados/estados/{paisId}", method = RequestMethod.GET)
    public JsonResponse getEstadosByPaisId(@PathVariable Integer paisId){
        List<EstadoComboProjection> estados = estadoDao.findProjectedComboAllByPaisId(paisId);
        return new JsonResponse(estados, "", 200);
    }

    @RequestMapping(value = "/listados/municipios/{estadoId}", method = RequestMethod.GET)
    public JsonResponse getMunicipiosByEstadoId(@PathVariable Integer estadoId){
        List<MunicipioComboProjection> municipios = municipioDao.findProjectedComboAllByEstadoId(estadoId);
        return new JsonResponse(municipios, "", 200);
    }
}
