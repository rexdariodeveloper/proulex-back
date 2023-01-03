package com.pixvs.spring.controllers;

import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.*;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.projections.Rol.RolListadoProjection;
import com.pixvs.spring.models.projections.RolMenu.RolMenuEditarProjection;
import com.pixvs.spring.models.projections.RolMenuPermiso.RolMenuPermisoProjection;
import com.pixvs.spring.util.BD;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RolController {

    @Autowired
    private ControlMaestroMultipleDao cmmDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RolDao rolDao;

    @Autowired
    private RolMenuDao rolMenuDao;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getUsuarios() throws SQLException {
        List<RolListadoProjection> roles = rolDao.findAllProjectedBy();

        return new JsonResponse(roles, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable(required = false) Integer id) {


        HashMap<String, Object> json = new HashMap<>();

        json.put("rol", rolDao.findProjectedById(id));
        json.put("menu", getMenu(id, ControlesMaestrosMultiples.CMM_SYS_SistemaAcceso.WEB, null, null));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Rol rol, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (rol.getId() == null) {
            rol.setCreadoPorId(idUsuario);
            ArrayList<RolMenu> menu = (ArrayList) rol.getMenu();
            rol.setMenu(null);
            rol = rolDao.save(rol);
            rol.setMenu(setRolMenu(menu, rol.getId()));
        } else {
            rol.setModificadoPorId(idUsuario);
            rolMenuDao.deleteAllByRolId(rol.getId());
        }

        rol = rolDao.save(rol);

        return new JsonResponse(rol.getId(), null, JsonResponse.STATUS_OK);
    }


    private JSONObject getMenu(Integer idRol, Integer sistemaAccesoId, Integer nodoPadreId, List<MenuPrincipal> alMenu) {

        if (alMenu == null) {
            List<MenuPrincipal> alChildren;
            alMenu = menuDao.findAllProjectedAllByUsuarioIdAndSistemaAccesoIdOrderByOrdenAsc(idRol, sistemaAccesoId, -1);
            setSelectedRol(alMenu, idRol);
        }


        getChildren(idRol, sistemaAccesoId, alMenu);

        JSONObject jsonMenu = new JSONObject();
        jsonMenu.put("navigation", alMenu);

        return jsonMenu;
    }

    private void setSelectedRol(List<MenuPrincipal> alMenu, Integer rolId) {
        for (MenuPrincipal menu : alMenu) {
            RolMenuEditarProjection rolMenuEditarProjection = rolMenuDao.findAllProjectedByRolIdAndAndNodoId(rolId, menu.getId());
            if (rolMenuEditarProjection != null) {
                menu.setSelected(true);
                menu.setRolMenu(rolMenuEditarProjection);

                List<RolMenuPermisoProjection> permisos = rolMenuPermisoDao.findAllProjectedByRolIdAndNodoId(rolId, menu.getId());
                if(permisos != null)
                    menu.setRolMenuPermisos(permisos);
            } else {
                RolMenu rm = new RolMenu();
                rm.setRolId(rolId);
                rm.setNodoId(menu.getId());
                rm.setCrear(true);
                rm.setModificar(true);
                rm.setEliminar(true);
                menu.setRolMenu(rm);

                List<RolMenuPermisoProjection> permisos = rolMenuPermisoDao.findAllProjectedByRolIdAndNodoId(rolId, menu.getId());
                if(permisos != null)
                    menu.setRolMenuPermisos(permisos);
            }
        }
    }

    public void getChildren(Integer rolId, Integer sistemaAccesoId, List<MenuPrincipal> alMenu) {
        for (MenuPrincipal menu : alMenu) {

            List<MenuPrincipal> alChildren;
            alChildren = menuDao.findAllProjectedAllByUsuarioIdAndSistemaAccesoIdOrderByOrdenAsc(rolId, sistemaAccesoId, menu.getId());
            setSelectedRol(alChildren, rolId);
            if (alChildren.size() > 0) {
                menu.getChildren().addAll(alChildren);
                getChildren(rolId, sistemaAccesoId, alChildren);
            }
        }
    }

    @GetMapping("/download/excel")
    public void downloadCsv(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_ROLES] vw ORDER BY vw.Nombre, vw.Orden";
        if (BD.POSTGRES.equals(BD.PLATAFORM))
            query = "SELECT * FROM \"VW_LISTADO_ROLES\"";
        String[] alColumnas = new String[]{"Nombre", "Fecha Creación", "Orden", "Menu"};

        excelController.downloadXlsx(response, "roles", query, alColumnas, null);
    }

    public ArrayList<RolMenu> setRolMenu(ArrayList<RolMenu> menu, Integer idRol) {
        for (RolMenu m : menu) {
            m.setRolId(idRol);
        }
        return menu;
    }

    @RequestMapping(value = "/permiso", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse togglePermiso(@RequestBody RolMenuPermiso permiso, ServletRequest req) throws Exception {
        if(permiso.getId() != null){
            rolMenuPermisoDao.deleteById(permiso.getId());
            permiso = null;
        } else {
            permiso = rolMenuPermisoDao.save(permiso);
        }

        return new JsonResponse(permiso, null, JsonResponse.STATUS_OK);
    }
}
