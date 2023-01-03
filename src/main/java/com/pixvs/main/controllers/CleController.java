package com.pixvs.main.controllers;

import com.pixvs.main.dao.AlumnoDao;
import com.pixvs.main.dao.AlumnoGrupoDao;
import com.pixvs.main.dao.ProgramaGrupoDao;
import com.pixvs.main.models.Alumno;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.AlumnoGrupo.AlumnoGrupoCleProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoProyeccionCLEProjection;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.Email;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.pixvs.spring.util.Utilidades.logFile;
import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;

/**
 * Created by David Arroyo on 12/07/2022.
 */
@RestController
@RequestMapping("/api/v1/cle")
@PropertySource({"classpath:cle.properties"})
public class CleController {

    @Autowired
    private Environment environment;

    @Autowired
    private ProgramaGrupoDao programaGrupoDao;

    @Autowired
    private AlumnoGrupoDao alumnoGrupoDao;

    @Autowired
    private AlumnoDao alumnoDao;

    @Autowired
    private Email email;

    /*private final RestTemplate restTemplate;

    public CleController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    */

    @Scheduled(cron = "0 */10 * * * *")
    @RequestMapping(value = "/sync_usuarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public JsonResponse syncAlumnosGrupos() throws Exception {

        if (environment.getProperty("enviroment.cle.sincronizacion").equals("true")) {

            String url = environment.getProperty("enviroment.cle.url");
            String iss = environment.getProperty("enviroment.cle.iss");
            String user = environment.getProperty("enviroment.cle.user");
            String uai = environment.getProperty("enviroment.cle.uai");
            String token = getCleToken(url, iss, user, uai);

            List<AlumnoGrupoCleProjection> alumnosGrupos = alumnoGrupoDao.getAlumnoGrupoSync();

            for (AlumnoGrupoCleProjection alumnosGrupo : alumnosGrupos) {

                /* Si esta activo*/
                if (alumnosGrupo.getAlumnoGrupoEstatusId() == ControlesMaestrosMultiples.CMM_ALUG_Estatus.ACTIVO
                        || alumnosGrupo.getAlumnoGrupoEstatusId() == ControlesMaestrosMultiples.CMM_ALUG_Estatus.REGISTRADO
                        || alumnosGrupo.getAlumnoGrupoEstatusId() == ControlesMaestrosMultiples.CMM_ALUG_Estatus.EN_RIESGO) {
                    //Integer idGrupoProfesor = alumnosGrupo.getGrupoProfesorId();
                    Integer idGrupoEstudiantes = alumnosGrupo.getGrupoEstudiantesId();

                    if (alumnosGrupo.getUsuarioCleId() == null) {

                        /*Crear usuario - alumno*/
                        Integer idUsuario = crearUsuarioAlumno(alumnosGrupo, url, token, uai);

                        if (idUsuario != null && alumnosGrupo.getFechaUltimaActualizacionCle() == null) {
                            /*Crear Relacion Grupo - Alumno en CLE*/
                            boolean ok = relacionarCleGrupoUsuarios(new int[]{idUsuario}, idGrupoEstudiantes, url, token, uai);
                            if(ok)
                                alumnoGrupoDao.actualizarAlumnoGrupoCle(alumnosGrupo.getAlumnoGrupoId());

                        }

                    } else if (alumnosGrupo.getUsuarioCleId() != null && alumnosGrupo.getFechaUltimaActualizacionCle() == null) {
                        /*Crear Relacion Grupo - Alumno en CLE*/
                        boolean ok = relacionarCleGrupoUsuarios(new int[]{alumnosGrupo.getUsuarioCleId()}, idGrupoEstudiantes, url, token, uai);
                        if(ok)
                            alumnoGrupoDao.actualizarAlumnoGrupoCle(alumnosGrupo.getAlumnoGrupoId());

                    }
                } else if (alumnosGrupo.getAlumnoGrupoEstatusId() == ControlesMaestrosMultiples.CMM_ALUG_Estatus.DESERTOR
                        || alumnosGrupo.getAlumnoGrupoEstatusId() == ControlesMaestrosMultiples.CMM_ALUG_Estatus.BAJA
                    /*|| alumnosGrupo.getAlumnoGrupoEstatusId() == ControlesMaestrosMultiples.CMM_ALUG_Estatus.REPROBADO*/) {

                    /*Borrar relacion Estudiante - Grupo Cle*/
                    if (alumnosGrupo.getProgramaGrupoEstudianteCleId() != null && alumnosGrupo.getUsuarioCleId() != null) {
                        borrarCleRelacionUsuarioGrupo(alumnosGrupo.getProgramaGrupoEstudianteCleId(), new int[]{alumnosGrupo.getUsuarioCleId()}, url, token, uai);
                        alumnoGrupoDao.borrarAlumnoGrupoCle(alumnosGrupo.getAlumnoGrupoId());
                    }


                }


            }


        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);

    }


    @RequestMapping(value = "/email_usuario/{codigo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse emailAlumnosGrupos(@PathVariable(required = false) String codigo, ServletRequest req) throws Exception {


        Alumno alumno = alumnoDao.findByCodigo(codigo);

        if (alumno != null && alumno.getUsuarioCleId() != null) {

            String pass = getPassAlumno(alumno.getNombre() , alumno.getPrimerApellido(), alumno.getFechaNacimiento());
            enviarCorreoUsuario(alumno.getCorreoElectronico(), alumno.getCodigo(), pass);
            return new JsonResponse(null, null, JsonResponse.STATUS_OK);
        }else{
            return new JsonResponse(null, alumno == null ? "Alumno no encontrado" : "Alumno sin usuario CLE", JsonResponse.STATUS_OK);
        }




    }


    private Integer crearUsuarioProfesor(ProgramaGrupoProyeccionCLEProjection grupo, String url, String token, String uai) {

        String pass = getPassAlumno(grupo.getProfesorNombre() , grupo.getProfesorApellidos(), grupo.getProfesorFechaNacimiento());
        //Profile code (2=student / 3=teacher)
        Integer idProfesor = crearCleUsuario(grupo.getProfesorCorreoElectronico(), grupo.getProfesorNombre(), grupo.getProfesorApellidos(), grupo.getProfesorCodigo(), pass, "3" /*teacher*/, url, token, uai);
        if (idProfesor != null)
            programaGrupoDao.actualizarProfesorCle(grupo.getProfesorId(), idProfesor);

        return idProfesor;

    }

    private Integer crearUsuarioAlumno(AlumnoGrupoCleProjection alumnosGrupo, String url, String token, String uai) {

        String pass = getPassAlumno(alumnosGrupo.getAlumnoNombre() , alumnosGrupo.getAlumnoApellidos(), alumnosGrupo.getAlumnoFechaNacimiento());
        /*Crear Usuario en CLE*/
        //Profile code (2=student / 3=teacher)
        Integer idUsuario = crearCleUsuario(alumnosGrupo.getAlumnoCorreoElectronico(), alumnosGrupo.getAlumnoNombre(), alumnosGrupo.getAlumnoApellidos(), alumnosGrupo.getAlumnoCodigo(), pass, "2", url, token, uai);
        if (idUsuario != null)
            alumnoGrupoDao.actualizarAlumnoCle(alumnosGrupo.getAlumnoId(), idUsuario);

        return idUsuario;

    }

    public String getPassAlumno(String nombre, String apellido1, Date fechaNacimiento){
        Calendar calendarFechaNac = Calendar.getInstance();
        calendarFechaNac.setTime(fechaNacimiento == null ? new Date(1900, 1, 1) : fechaNacimiento);
        return nombre.trim().substring(0, 2).toLowerCase() + apellido1.trim().substring(0, 2).toLowerCase() + padLeftZeros(String.valueOf(calendarFechaNac.get(Calendar.DAY_OF_MONTH)), 2) + padLeftZeros(String.valueOf(calendarFechaNac.get(Calendar.MONTH) + 1), 2);
    }


    @Scheduled(cron = "0 */15 * * * *")
    @RequestMapping(value = "/sync_profesores", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public JsonResponse syncProfesoresGrupos() throws Exception {

        if (environment.getProperty("enviroment.cle.sincronizacion").equals("true")) {


            String url = environment.getProperty("enviroment.cle.url");
            String iss = environment.getProperty("enviroment.cle.iss");
            String user = environment.getProperty("enviroment.cle.user");
            String uai = environment.getProperty("enviroment.cle.uai");
            String token = getCleToken(url, iss, user, uai);
            Integer idGrupoProfesor = null;
            Integer idGrupoEstudiantes = null;

            List<ProgramaGrupoProyeccionCLEProjection> grupos = programaGrupoDao.getCleGruposCreadosSync(environment.getProperty("enviroment.cle.inicio"));

            for (ProgramaGrupoProyeccionCLEProjection grupo : grupos) {

                if (grupo.getProgramaGrupoEstatusId() == ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO) {

                    idGrupoProfesor = grupo.getGrupoProfesorId();
                    idGrupoEstudiantes = grupo.getGrupoEstudiantesId();



                    /*Si NO existe el profesor*/
                    if (grupo.getProfesorId() != null && grupo.getProfesorCleId() == null) {

                        /*Creación Profesor*/
                        Integer idProfesor = crearUsuarioProfesor(grupo, url, token, uai);

                        if (idProfesor != null && grupo.getProgramGrupoProfesorCleId() == null) {
                            /* Relacionar Grupo - Usuario Profesor*/
                            boolean ok = relacionarCleGrupoUsuarios(new int[]{idProfesor}, idGrupoProfesor, url, token, uai);
                            relacionarCleGrupoUsuarios(new int[]{idProfesor}, idGrupoEstudiantes, url, token, uai);
                            if(ok)
                                programaGrupoDao.actualizarGrupoProfesorCle(grupo.getId(), idProfesor);
                        }

                    }
                    /*Si ya existe el profesor*/
                    else if (grupo.getProfesorId() != null && grupo.getProfesorCleId() != null) {

                        /* Relacionar Grupo - Usuario Profesor*/
                        boolean ok = relacionarCleGrupoUsuarios(new int[]{grupo.getProfesorCleId()}, idGrupoProfesor, url, token, uai);
                        relacionarCleGrupoUsuarios(new int[]{grupo.getProfesorCleId()}, idGrupoEstudiantes, url, token, uai);
                        if(ok)
                            programaGrupoDao.actualizarGrupoProfesorCle(grupo.getId(), grupo.getProfesorCleId());
                    }
                }

            }


        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);

    }



    @RequestMapping(value = "/sync_grupos_finalizados", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public JsonResponse syncGruposFinalizados() throws Exception {

        String url = environment.getProperty("enviroment.cle.url");
        String iss = environment.getProperty("enviroment.cle.iss");
        String user = environment.getProperty("enviroment.cle.user");
        String uai = environment.getProperty("enviroment.cle.uai");
        String token = null;


        if (environment.getProperty("enviroment.cle.sincronizacion").equals("true") &&
                (environment.getProperty("enviroment.cle.eliminar").equals("true")) ) {

            token = getCleToken(url, iss, user, uai);


            List<ProgramaGrupoProyeccionCLEProjection> grupos = programaGrupoDao.getCleGruposFinalizadosCanceladosSync();

            GRUPOS:
            for (ProgramaGrupoProyeccionCLEProjection grupo : grupos) {



                /*Si el grupo esta cancelado o finalizado*/
                if (grupo.getProgramaGrupoEstatusId() == ControlesMaestrosMultiples.CMM_PROGRU_Estatus.CANCELADO
                        || grupo.getProgramaGrupoEstatusId() == ControlesMaestrosMultiples.CMM_PROGRU_Estatus.FINALIZADO) {

                    /*Eliminar Grupo Profesor*/
                    if (grupo.getGrupoProfesorId() != null)
                        borrarCleGrupo(grupo.getGrupoProfesorId(), url, token, uai);

                    /*Eliminar Grupo Estudiantes*/
                    if (grupo.getGrupoEstudiantesId() != null)
                        borrarCleGrupo(grupo.getGrupoEstudiantesId(), url, token, uai);

                    programaGrupoDao.actualizarGrupoCle(grupo.getId(), null, null);

                }

            }

            return new JsonResponse(null, null, JsonResponse.STATUS_OK);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Scheduled(cron = "0 */16 * * * *")
    @RequestMapping(value = "/sync_grupos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public JsonResponse syncGrupos() throws Exception {

        Integer idGrupoProfesor = null;
        Integer idGrupoEstudiantes = null;
        String url = environment.getProperty("enviroment.cle.url");
        String iss = environment.getProperty("enviroment.cle.iss");
        String user = environment.getProperty("enviroment.cle.user");
        String uai = environment.getProperty("enviroment.cle.uai");
        String token = null;


        if (environment.getProperty("enviroment.cle.sincronizacion").equals("true")) {

            token = getCleToken(url, iss, user, uai);


            List<ProgramaGrupoProyeccionCLEProjection> grupos = programaGrupoDao.getCleGruposSync(environment.getProperty("enviroment.cle.inicio"));

            GRUPOS:
            for (ProgramaGrupoProyeccionCLEProjection grupo : grupos) {


                if (grupo.getProgramaGrupoEstatusId() == ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO
                    && (grupo.getProfesorResourceId() == null || grupo.getEstudianteResourceId() == null) ) {

                    Calendar calendarIni = Calendar.getInstance();
                    Calendar calendarFin = Calendar.getInstance();
                    calendarIni.setTime(grupo.getFechaInicio());
                    calendarFin.setTime(grupo.getFechaFin());

                    //long duracionSemanas = getFullWeeks(calendarIni,calendarFin);

                    String cle_codigo = grupo.getCodigoGrupo() + "-" + (padLeftZeros(String.valueOf(grupo.getDuracionSemanas()), 2) +
                            "-" + padLeftZeros(String.valueOf(calendarIni.get(Calendar.DAY_OF_MONTH)), 2) + padLeftZeros(String.valueOf(calendarIni.get(Calendar.MONTH) + 1), 2) +
                            "-" + padLeftZeros(String.valueOf(calendarFin.get(Calendar.DAY_OF_MONTH)), 2) + padLeftZeros(String.valueOf(calendarFin.get(Calendar.MONTH) + 1), 2)
                    );


                    /*Crear Grupo Profesor*/
                    if (grupo.getGrupoProfesorId() == null) {

                        idGrupoProfesor = buscarCleGrupo(cle_codigo + "-P", url, token, uai);
                        try {
                            if(idGrupoProfesor == null) {
                                idGrupoProfesor = crearCleGrupo(grupo, cle_codigo + "-P", url, token, uai);
                            }
                        } catch (Exception ex) {

                            if (idGrupoProfesor != null) {
                                borrarCleGrupo(idGrupoProfesor, url, token, uai);
                            }
                            programaGrupoDao.actualizarGrupoCle(grupo.getId(), null, null);

                            log(cle_codigo + "-P"+"/"+grupo.getCodigoGrupo()+" no se pudo crear");

                            continue GRUPOS;
                        }
                    } else
                        idGrupoProfesor = grupo.getGrupoProfesorId();

                    /*Crear Grupo Estudiantes*/
                    if (grupo.getGrupoEstudiantesId() == null) {

                        idGrupoEstudiantes = buscarCleGrupo(cle_codigo + "-E", url, token, uai);
                        try {
                            if(idGrupoEstudiantes == null) {
                                idGrupoEstudiantes = crearCleGrupo(grupo, cle_codigo + "-E", url, token, uai);
                            }
                        } catch (Exception ex) {

                            if (idGrupoProfesor != null) {
                                borrarCleGrupo(idGrupoProfesor, url, token, uai);
                            }

                            if (idGrupoEstudiantes != null) {
                                borrarCleGrupo(idGrupoEstudiantes, url, token, uai);
                            }

                            programaGrupoDao.actualizarGrupoCle(grupo.getId(), null, null);

                            log( cle_codigo + "-P"+"/"+grupo.getCodigoGrupo()+" no se pudo crear");

                            continue GRUPOS;
                        }
                    } else
                        idGrupoEstudiantes = grupo.getGrupoEstudiantesId();

                    programaGrupoDao.actualizarGrupoCle(grupo.getId(), idGrupoProfesor, idGrupoEstudiantes);


                    if (idGrupoProfesor != null && idGrupoEstudiantes != null &&
                            grupo.getProfesorResourceId() != null && grupo.getEstudianteResourceId() != null) {
                        /* Relacionar Grupo - Resource*/
                        relacionarCleGrupoResource(grupo.getProfesorResourceId(), grupo.getProfesorResourceFechaFin(), idGrupoProfesor, url, token, uai);
                        relacionarCleGrupoResource(grupo.getEstudianteResourceId(), grupo.getEstudianteResourceFechaFin(), idGrupoEstudiantes, url, token, uai);

                         //programaGrupoDao.actualizarGrupoProfesorCle(grupo.getId(), grupo.getProfesorCleId());
                    }


                    /*Si NO existe el profesor RELACIONADO*/
                    if (grupo.getProfesorId() != null && grupo.getProfesorCleId() == null) {

                        /*Creación Profesor*/
                        Integer idProfesor = crearUsuarioProfesor(grupo, url, token, uai);

                        if (idProfesor != null && grupo.getProgramGrupoProfesorCleId() == null) {
                            /* Relacionar Grupo - Usuario Profesor*/
                            boolean ok = relacionarCleGrupoUsuarios(new int[]{idProfesor}, idGrupoProfesor, url, token, uai);
                            relacionarCleGrupoUsuarios(new int[]{idProfesor}, idGrupoEstudiantes, url, token, uai);
                            if(ok)
                                programaGrupoDao.actualizarGrupoProfesorCle(grupo.getId(), idProfesor);
                        }

                    }
                    /*Si ya existe el profesor en el Grupo RELACIONADO*/
                    else if (grupo.getProfesorId() != null && grupo.getProfesorCleId() != null) {

                        /* Relacionar Grupo - Usuario Profesor*/
                        /*
                        boolean ok = relacionarCleGrupoUsuarios(new int[]{grupo.getProfesorCleId()}, idGrupoProfesor, url, token, uai);
                        relacionarCleGrupoUsuarios(new int[]{grupo.getProfesorCleId()}, idGrupoEstudiantes, url, token, uai);
                        if(ok)
                            programaGrupoDao.actualizarGrupoProfesorCle(grupo.getId(), grupo.getProfesorCleId());

                         */
                    }
                } else {

                    /*Validar primero manualmente y con CLE*/
                    if (false)
                        /*Si el grupo esta cancelado o finalizado*/
                        if (grupo.getProgramaGrupoEstatusId() == ControlesMaestrosMultiples.CMM_PROGRU_Estatus.CANCELADO
                                || grupo.getProgramaGrupoEstatusId() == ControlesMaestrosMultiples.CMM_PROGRU_Estatus.FINALIZADO) {

                            /*Eliminar Grupo Profesor*/
                            if (grupo.getGrupoProfesorId() != null)
                                borrarCleGrupo(idGrupoProfesor, url, token, uai);

                            /*Eliminar Grupo Estudiantes*/
                            if (grupo.getGrupoEstudiantesId() != null)
                                borrarCleGrupo(idGrupoEstudiantes, url, token, uai);

                            programaGrupoDao.actualizarGrupoCle(grupo.getId(), null, null);

                        }


                }


            }

            return new JsonResponse(null, null, JsonResponse.STATUS_OK);

        } else {
            return new JsonResponse(null, null, JsonResponse.STATUS_OK);
        }


    }

    private Integer crearCleGrupo(ProgramaGrupoProyeccionCLEProjection grupo, String codigo, String url, String token, String uai) throws Exception {

        OkHttpClient client = new OkHttpClient();
        okhttp3.MediaType mediaType  = okhttp3.MediaType.get("application/json; charset=utf-8");


        JSONObject json = new JSONObject();
        json.put("nom", codigo);
        json.put("codeNiveau", grupo.getNivel());
        json.put("typeClasse", true);

        RequestBody body = RequestBody.create(json.toString(), mediaType);

        Request request = new Request.Builder()
                .url(url + "/etablissement/" + uai + "/groupes")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("access_token", token)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        if (response != null) {
            if (response.code() == HttpStatus.OK.value()) {

                JSONParser parser = new JSONParser(DEFAULT_PERMISSIVE_MODE);
                JSONObject json_body = (JSONObject) parser.parse(response.body().string());

                return Integer.valueOf(json_body.get("codeGroupe").toString());

            } else {
                log("Error al crear el grupo (" + codigo + "): " + response.code());
                throw new Exception("Error al crear el grupo (" + codigo + "): " + response.code());
            }
        } else {
            log("No hubó respuesta al crear el grupo (" + codigo + ")");
            throw new Exception("No hubó respuesta al crear el grupo (" + codigo + ")");
        }

    }

    private Integer crearCleUsuario(String correoElectronico, String nom, String prenom, String login, String mdp, String profil, String url, String token, String uai) {

        OkHttpClient client = new OkHttpClient();
        okhttp3.MediaType mediaType  = okhttp3.MediaType.get("application/json; charset=utf-8");


        JSONObject json = new JSONObject();
        json.put("nom", nom);
        json.put("prenom", prenom);
        json.put("login", login);
        json.put("mdp", mdp);
        json.put("profil", profil);

        RequestBody body = RequestBody.create(json.toString(), mediaType);



        Response response = null;
        try {
            Request request = new Request.Builder()
                    .url(url + "/etablissement/" + uai + "/utilisateurs")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("access_token", token)
                    .build();

            Call call = client.newCall(request);
            response = call.execute();

        } catch (Exception ex) {

            log("Error al crear el usuario (" + login + "): " + ex.getLocalizedMessage());
            System.out.println("Error al crear el usuario (" + login + "): " + ex.getLocalizedMessage());
        }


        if (response != null) {
            if (response.code() == HttpStatus.OK.value()) {

                JSONParser parser = new JSONParser(DEFAULT_PERMISSIVE_MODE);
                JSONObject json_body = null;
                try {
                    json_body = (JSONObject) parser.parse(response.body().string());
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                    log(e.getMessage());
                }

                enviarCorreoUsuario(correoElectronico, login, mdp);

                return Integer.valueOf(((JSONObject) ((JSONObject) json_body.get("data")).get("utilisateur")).get("codeUtilisateur").toString());

            } else {
                log("Error al crear el usuario (" + login + "): " + response.code());
                System.out.println("Error al crear el usuario (" + login + "): " + response.code());
                //throw new Exception("Error al crear el usuario (" + login + "): " + response.code());
            }
        } else {
            log("No hubó respuesta al crear el usuario (" + login + ")");
            System.out.println("No hubó respuesta al crear el usuario (" + login + ")");
            //throw new Exception("No hubó respuesta al crear el usuario (" + login + ")");
        }

        return null;
    }

    private void enviarCorreoUsuario(String correoElectronico, String login, String mdp) {


        String mensaje = "<pre>\n" +
                "¡Bienvenido al programa de francés de PROULEX! \n" +
                "</pre>\n" +
                "\n" +
                "<pre>" +
                "Al momento de tu inscripción, te fue entregado en físico el Método de ODYSSÉE. \n" +
                "IMPORTANTE: El cuaderno de actividades viene dentro de la plataforma.\n" +
                "Es necesario que des click en la siguiente liga: \n" +
                "<p><a href=\"https://biblio.manuel-numerique.com/\" rel=\"noopener noreferrer\" target=\"_blank\">https://biblio.manuel-numerique.com</a></p> para poder acceder a la plataforma.\n" +
                "user: <strong>" + login + "</strong></pre>\n" +
                "\n" +
                "<pre>\n" +
                "pass: <strong>" + mdp + "</strong>\n" +
                "</pre>\n" +
                "\n" +

                "\n" +
                "<p>&nbsp;</p>\n" +
                "\n" +
                "<p>Es necesario que veas el siguiente video: </p>\n" +
                "\n" +
                "<p><a href=\"https://www.youtube.com/watch?v=bMDeMtJwiiY\" rel=\"noopener noreferrer\" target=\"_blank\">https://www.youtube.com/watch?v=bMDeMtJwiiY</a></p> para que te familiarices con la plataforma.\n" +
                "";

        if (environment.getProperty("enviroment.cle.enviar-correo").equals("true")) {

            if(correoElectronico != null && !correoElectronico.isEmpty()){
                email.sendHtmlEmail("licenciasplx@proulex.udg.mx"+", "+correoElectronico.trim(), "Usuario CLE", mensaje);
            }else{
                email.sendHtmlEmail("licenciasplx@proulex.udg.mx", "Usuario CLE", mensaje);
            }

        } else {
            email.sendHtmlEmail("chevy.arroyo@gmail.com", "Usuario CLE", mensaje);
        }

    }


    private void relacionarCleGrupoResource(String ean13, String dateFinAbt, int groupe, String url, String token, String uai) {
        OkHttpClient client = new OkHttpClient();
        okhttp3.MediaType mediaType  = okhttp3.MediaType.get("application/json; charset=utf-8");


        JSONArray arrayResources = new JSONArray();
        JSONObject jsonResource = new JSONObject();
        jsonResource.put("ean13", ean13);
        jsonResource.put("dateFinAbt", dateFinAbt);
        arrayResources.appendElement(jsonResource);

        JSONObject json = new JSONObject();
        json.put("ressources", arrayResources);

        RequestBody body = RequestBody.create(json.toString(), mediaType);


        Response response = null;
        try {

            Request request = new Request.Builder()
                    .url(url + "/etablissement/" + uai + "/groupe/" + groupe + "/ressources")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("access_token", token)
                    .build();

            Call call = client.newCall(request);
            response = call.execute();


        } catch (Exception ex) {
            log("Error al relacionar el recurso (" + ean13 + "): " + ex.getLocalizedMessage());
            System.out.println("Error al relacionar el recurso (" + ean13 + "): " + ex.getLocalizedMessage());
        }

        if (response != null) {
            if (response.code() == HttpStatus.OK.value()) {
                /*
                JSONParser parser = new JSONParser(DEFAULT_PERMISSIVE_MODE);
                JSONObject json_body = (JSONObject) parser.parse(response.body().string());

                return (Integer) json_body.get("codeGroupe");

                 */

            } else {
                log("Error al relacionar el recurso (" + ean13 + "): " + response.code());
                System.out.println("Error al relacionar el recurso (" + ean13 + "): " + response.code());
                //throw new Exception("Error al relacionar el recurso (" + ean13 + "): " + response.code());
            }
        } else {
            log("Error al relacionar el recurso (" + ean13 + "): ");
            System.out.println("Error al relacionar el recurso (" + ean13 + "): ");
            //throw new Exception("No hubó respuesta al relacionar el recurso (" + ean13 + ")");
        }

    }

    private void borrarCleRelacionUsuarioGrupo(int groupe, int[] codeUtilisateurs, String url, String token, String uai) {
        OkHttpClient client = new OkHttpClient();
        okhttp3.MediaType mediaType  = okhttp3.MediaType.get("application/json; charset=utf-8");


        JSONArray arrayResources = new JSONArray();
        for (int i = 0; i < codeUtilisateurs.length; i++) {
            arrayResources.appendElement(codeUtilisateurs[i]);
        }

        JSONObject json = new JSONObject();
        json.put("codeUtilisateurs", arrayResources);

        RequestBody body = RequestBody.create(json.toString(), mediaType);

        Response response = null;
        try {

            Request request = new Request.Builder()
                    .url(url + "/etablissement/" + uai + "/groupe/" + groupe + "/utilisateurs")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("access_token", token)
                    .build();

            Call call = client.newCall(request);
            response = call.execute();

        } catch (Exception ex) {
            log("Error al borrar relación usuario en grupo (" + groupe + "): " + ex.getLocalizedMessage());
            System.out.println("Error al borrar relación usuario en grupo (" + groupe + "): " + ex.getLocalizedMessage());
        }

        if (response != null) {
            if (response.code() == HttpStatus.OK.value()) {
                /*
                JSONParser parser = new JSONParser(DEFAULT_PERMISSIVE_MODE);
                JSONObject json_body = (JSONObject) parser.parse(response.body().string());

                return (Integer) json_body.get("codeGroupe");

                 */

            } else {
                log("Error al borrar relación usuario en grupo (" + groupe + "): " + response.code());
                //throw new Exception("Error al borrar relación usuario en grupo (" + groupe + "): " + response.code());
                System.out.println("Error al borrar relación usuario en grupo (" + groupe + "): " + response.code());
            }
        } else {
            log("No hubó respuesta al borrar la relacion curso-usuario (" + groupe + ")");
            //throw new Exception("No hubó respuesta al borrar la relacion curso-usuario (" + groupe + ")");
            System.out.println("No hubó respuesta al borrar la relacion curso-usuario (" + groupe + ")");
        }

    }

    private void borrarCleGrupo(int groupe, String url, String token, String uai) {

        if (environment.getProperty("enviroment.cle.eliminar").equals("true")) {

            OkHttpClient client = new OkHttpClient();
            okhttp3.MediaType mediaType  = okhttp3.MediaType.get("application/json; charset=utf-8");

            Response response = null;
            try {


                Request request = new Request.Builder()
                        .url(url + "/etablissement/" + uai + "/groupe/" + groupe)
                        .delete()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("access_token", token)
                        .build();

                Call call = client.newCall(request);
                response = call.execute();

            } catch (Exception ex) {
                log("Error al borrar relación usuario en grupo (" + groupe + "): " + ex.getLocalizedMessage());
                System.out.println("Error al borrar relación usuario en grupo (" + groupe + "): " + ex.getLocalizedMessage());
            }

            if (response != null) {
                if (response.code() == HttpStatus.OK.value()) {
                /*
                JSONParser parser = new JSONParser(DEFAULT_PERMISSIVE_MODE);
                JSONObject json_body = (JSONObject) parser.parse(response.body().string());

                return (Integer) json_body.get("codeGroupe");

                 */

                } else {
                    log("Error al borrar el curso (" + groupe + "): " + response.code());
                    System.out.println("Error al borrar el curso (" + groupe + "): " + response.code());
                    //throw new Exception("Error al borrar el curso (" + groupe + "): " + response.code());
                }
            } else {
                log("No hubó respuesta al borrar el curso (" + groupe + ")");
                System.out.println("No hubó respuesta al borrar el curso (" + groupe + ")");
                //throw new Exception("No hubó respuesta al borrar el curso (" + groupe + ")");
            }
        }
    }


    private boolean relacionarCleGrupoUsuarios(int[] codeUtilisateurs, int groupe, String url, String token, String uai) {
        boolean ok = false;

        OkHttpClient client = new OkHttpClient();
        okhttp3.MediaType mediaType  = okhttp3.MediaType.get("application/json; charset=utf-8");


        JSONArray arrayResources = new JSONArray();
        for (int i = 0; i < codeUtilisateurs.length; i++) {
            arrayResources.appendElement(codeUtilisateurs[i]);
        }

        JSONObject json = new JSONObject();
        json.put("codeUtilisateurs", arrayResources);

        RequestBody body = RequestBody.create(json.toString(), mediaType);

        Response response = null;
        try {

            Request request = new Request.Builder()
                    .url(url + "/etablissement/" + uai + "/groupe/" + groupe + "/utilisateurs")
                    .put(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("access_token", token)
                    .build();

            Call call = client.newCall(request);
            response = call.execute();

        } catch (Exception ex) {
            log("Error al relacionar usuario en grupo (" + groupe + "): " + ex.getLocalizedMessage());
            System.out.println("Error al relacionar usuario en grupo (" + groupe + "): " + ex.getLocalizedMessage());
        }

        if (response != null) {
            if (response.code() == HttpStatus.OK.value()) {
                /*
                JSONParser parser = new JSONParser(DEFAULT_PERMISSIVE_MODE);
                JSONObject json_body = (JSONObject) parser.parse(response.body().string());

                return (Integer) json_body.get("codeGroupe");

                 */

                ok = true;

            } else {
                log("Error al relacionar usuarios al grupo (" + groupe + "): " + response.code());
                System.out.println("Error al relacionar usuarios al grupo (" + groupe + "): " + response.code());
                //throw new Exception("Error al relacionar usuarios al grupo (" + groupe + "): " + response.code());
            }
        } else {
            log("No hubó respuesta al relacionar usuarios al grupo (" + groupe + ")");
            System.out.println("No hubó respuesta al relacionar usuarios al grupo (" + groupe + ")");
            //throw new Exception("No hubó respuesta al relacionar usuarios al grupo (" + groupe + ")");
        }

        return ok;

    }

    private Integer buscarCleGrupo(String codigoGrupo, String url, String token, String uai) throws IOException {

        OkHttpClient client = new OkHttpClient();
        okhttp3.MediaType mediaType  = okhttp3.MediaType.get("application/json; charset=utf-8");

        JSONObject json = new JSONObject();
        json.put("nom", codigoGrupo);

        RequestBody body = RequestBody.create(json.toString(), mediaType);

        Request request = new Request.Builder()
                .url(url + "/etablissement/" + uai + "/groupes/search")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("access_token", token)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        if (response != null) {
            if (response.code() == HttpStatus.OK.value()) {

                JSONParser parser = new JSONParser(DEFAULT_PERMISSIVE_MODE);

                try{
                    JSONObject json_body = (JSONObject) parser.parse(response.body().string());

                    if((Integer) json_body.get("count") >= 1){
                        return (Integer) ((JSONObject)((JSONArray)json_body.get("groupes")).get(0)).get("code");
                    }
                }catch (Exception ex){
                    log("Error al parsear la peticion de busqueda de grupo (" + codigoGrupo + "): " + response.code());
                }



            } else {
                log("Error al buscar el grupo (" + codigoGrupo + "): " + response.code());
            }
        } else {
            log("No hubó respuesta al buscar el grupo (" + codigoGrupo + ")");
        }

        return null;
    }

    private String getCleToken(String url, String iss, String user, String uai) throws ParseException, Exception {



        /*

                HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Authorization", "Bearer " + tokenPlic);


        //JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
        //JSONObject json = (JSONObject) parser.parse(jsonXML);

        HttpEntity<String> entity = new HttpEntity<String>(json.toString(), headers);

        ResponseEntity response = this.restTemplate.postForEntity(
                url + "/misc/token",
                entity,
                String.class
        );

        */

        OkHttpClient client = new OkHttpClient();
        okhttp3.MediaType mediaType  = okhttp3.MediaType.get("application/json; charset=utf-8");

        JSONObject json = new JSONObject();
        json.put("iss", iss);
        json.put("user", user);
        json.put("uai", uai);

        RequestBody body = RequestBody.create(json.toString(), mediaType);

        Request request = new Request.Builder()
                .url(url + "/misc/token")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        if (response != null) {
            if (response.code() == HttpStatus.OK.value()) {

                JSONParser parser = new JSONParser(DEFAULT_PERMISSIVE_MODE);
                JSONObject json_body = (JSONObject) parser.parse(response.body().string());

                return json_body.get("accessToken").toString();
            }
        }



        return null;
    }


    public static long getFullWeeks(Calendar d1, Calendar d2) {

        Instant d1i = Instant.ofEpochMilli(d1.getTimeInMillis());
        Instant d2i = Instant.ofEpochMilli(d2.getTimeInMillis());

        LocalDateTime startDate = LocalDateTime.ofInstant(d1i, ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(d2i, ZoneId.systemDefault());

        return ChronoUnit.WEEKS.between(startDate, endDate);
    }


    public String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }

    private void log(String mensaje) {
        logFile("pixvs-cle", mensaje, true);
    }

}
