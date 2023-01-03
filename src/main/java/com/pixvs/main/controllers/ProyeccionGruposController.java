package com.pixvs.main.controllers;

import com.pixvs.main.dao.PAModalidadDao;
import com.pixvs.main.dao.ProgramaGrupoDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoProyeccionProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.StringCheck;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/proyeccion-grupos")
public class ProyeccionGruposController {

    @Autowired
    private PAModalidadDao paModalidadDao;

    @Autowired
    private ProgramaGrupoDao programaGrupoDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> json = new HashMap<>();

        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId);
        sucursales = sucursales.stream()
                .filter(p -> !p.getNombre().equals("JOBS ") && !p.getNombre().equals("JOBS SEMS")).collect(Collectors.toList());

        json.put("datos", new ArrayList<>());
        json.put("modalidades", paModalidadDao.findComboAllByActivoTrueOrderByNombre());
        json.put("sucursales", sucursales);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getGrupos(@RequestBody JSONObject json) throws Exception {
        Integer sedeId = Utilidades.getItem(json.get("sede"), "id");
        Integer paId = Utilidades.getItem(json.get("pa"), "id");
        Integer modalidadId = Utilidades.getItem(json.get("modalidad"), "id");
        String fechaFin = Utilidades.getItem(json.get("fechaFin"), "fecha");
        fechaFin = !StringCheck.isNullorEmpty(fechaFin) ? fechaFin.trim() : null;

        List<ProgramaGrupoProyeccionProjection> grupos =
                programaGrupoDao.getReporteGruposFechaFin(sedeId, modalidadId, paId, fechaFin);

        return new JsonResponse(grupos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/nuevos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getGruposProyectar(@RequestBody JSONObject json) throws Exception {
        return new JsonResponse(programaGrupoDao.getGruposProyectar((String) json.get("grupos")), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/proyeccionGrupos", method = RequestMethod.POST)
    public JsonResponse proyeccionGrupos(@RequestBody JSONObject json, ServletRequest req) throws AdvertenciaException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        String gruposIds = (String) json.get("grupos");
        List<JSONObject> errors = new ArrayList<>();

        try {
            List<Object[]> results = em
                    .createNativeQuery("EXEC sp_proyectarGrupos :gruposIds, :creadoPorId")
                    .setParameter("gruposIds", gruposIds)
                    .setParameter("creadoPorId", usuarioId)
                    .getResultList();
            results.size();

            for (Object[] result : results) {
                JSONObject feedback = new JSONObject();

                feedback.appendField("student", result[0]);
                feedback.appendField("group", result[1]);
                feedback.appendField("message", result[2]);

                errors.add(feedback);
            }
        } catch (PersistenceException e) {
            throw new AdvertenciaException(e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JsonResponse(errors, null, JsonResponse.STATUS_OK);
    }
}