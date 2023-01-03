package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.Almacen;
import com.pixvs.main.models.Articulo;
import com.pixvs.main.models.Localidad;
import com.pixvs.main.models.projections.Almacen.AlmacenEditarProjection;
import com.pixvs.main.models.projections.Almacen.AlmacenListadoProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadEditarProjection;

import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.PaisDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 */
@RestController
@RequestMapping("/api/v1/almacenes")
public class AlmacenController {

    @Autowired
    private LocalidadController localidadController;

    @Autowired
    private AlmacenDao almacenDao;
    @Autowired
    private PaisDao paisDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private LocalidadDao localidadDao;
    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private LocalidadArticuloDao localidadArticuloDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getAlmacenes() throws SQLException {

        List<AlmacenListadoProjection> almacenes = almacenDao.findProjectedListadoAllByTipoAlmacenIdNotIn(Arrays.asList(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_ALM_TipoAlmacen.TRANSITO));

        HashMap<String,Object> data = new HashMap<>();
        data.put("datos",almacenes);

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/detalle", "/detalle/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosDetalle(@PathVariable(required = false) Integer id) throws SQLException {

        List<PaisComboProjection> paises = paisDao.findProjectedComboAllBy();
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByActivoTrue();
        List<UsuarioComboProjection> usuarios = usuarioDao.findProjectedComboAllByEstatusId(ControlesMaestrosMultiples.CMM_Estatus.ACTIVO);
        List<ArticuloComboProjection> articulos = articuloDao.findProjectedComboAllByActivoTrueAndTipoArticuloNoSistema();

        HashMap<String,Object> datos = new HashMap<>();
        datos.put("paises",paises);
        datos.put("sucursales",sucursales);
        datos.put("usuarios",usuarios);
        datos.put("articulos",articulos);

        if(id != null){
            AlmacenEditarProjection almacen = almacenDao.findProjectedEditarById(id);
            datos.put("almacen",almacen);
            datos.put("localidaedesArticulosActivos", localidadArticuloDao.findLocalidadArticuloByalmacenId(id) );
        }

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Almacen almacen, ServletRequest req) throws Exception {

        boolean esNuevo = false;
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        almacen.setMismaDireccionSucursal(false);
        almacen.setMismaDireccionCliente(false);
        if(almacen.getPredeterminado()){
            Almacen almacenPredeterminadoExistente = null;
            if(almacen.getId() != null){
                almacenPredeterminadoExistente = almacenDao.findByIdNotAndSucursalIdAndPredeterminadoTrue(almacen.getId(),almacen.getSucursal().getId());
            }else{
                almacenPredeterminadoExistente = almacenDao.findBySucursalIdAndPredeterminadoTrue(almacen.getSucursal().getId());
            }
            if(almacenPredeterminadoExistente != null){
                return new JsonResponse("", "Ya existe un almacén predeterminado en la sucursal seleccionada (" + almacenPredeterminadoExistente.getNombre() + ")", JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
            }
        }

        if(almacen.getSucursal() != null){
            almacen.setSucursalId(almacen.getSucursal().getId());
        } else{
            almacen.setSucursalId(null);
        }
        if(almacen.getResponsable() != null){
            almacen.setResponsableId(almacen.getResponsable().getId());
        } else{
            almacen.setResponsableId(null);
        }
        if(almacen.getPais() != null){
            almacen.setPaisId(almacen.getPais().getId());
        } else{
            almacen.setPaisId(null);
        }
        if(almacen.getEstado() != null){
            almacen.setEstadoId(almacen.getEstado().getId());
        } else{
            almacen.setEstadoId(null);
        }

        if(almacen.getMismaDireccionSucursal()){
            almacen.setDomicilio(null);
            almacen.setCp(null);
            almacen.setColonia(null);
            almacen.setCiudad(null);
            almacen.setPaisId(null);
            almacen.setEstadoId(null);
            almacen.setTelefono(null);
            almacen.setExtension(null);
        }


        Almacen almacenCodigoExistente;
        if (almacen.getId() == null) {
            esNuevo = true;
            almacen.setCreadoPorId(idUsuario);
            almacen.setActivo(true);
            almacen.setTipoAlmacenId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_ALM_TipoAlmacen.NORMAL);
            almacenCodigoExistente = almacenDao.findByCodigoAlmacen(almacen.getCodigoAlmacen());
        } else {
            Almacen objetoActual = almacenDao.findById(almacen.getId().intValue());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(),almacen.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            almacen.setModificadoPorId(idUsuario);
            almacen.setTipoAlmacenId(objetoActual.getTipoAlmacenId());
            almacenCodigoExistente = almacenDao.findByCodigoAlmacenAndIdNot(almacen.getCodigoAlmacen(),almacen.getId());
            almacen.setUsuariosPermisos(objetoActual.getUsuariosPermisos());
        }

        if(almacenCodigoExistente != null){
            return new JsonResponse("", "Ya existe un almacén con el código ingresado", JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
        }

        if(almacen.getLocalidades()!=null){
            for(Integer i=0; i<almacen.getLocalidades().size();i++){
                if (almacen.getLocalidades().get(i).getId() == null) {
                    almacen.getLocalidades().get(i).setCreadoPorId(idUsuario);
                    if(almacen.getLocalidades().get(i).getActivo() == null){
                        almacen.getLocalidades().get(i).setActivo(true);
                    }
                } else {
                    almacen.getLocalidades().get(i).setModificadoPorId(idUsuario);
                }

                almacen.getLocalidades().get(i).setLocalidadGeneral(false);

                LocalidadEditarProjection temp = localidadDao.findByCodigoLocalidad(almacen.getLocalidades().get(i).getCodigoLocalidad());
                if(temp != null){
                    new JsonResponse(null,"El código de localidad "+almacen.getLocalidades().get(i).getNombre()+" ya existe",JsonResponse.STATUS_ERROR);
                }
            }
            Localidad localidadGeneralAlmacen = localidadDao.findByAlmacenIdAndLocalidadGeneralTrue(almacen.getId());
            if(localidadGeneralAlmacen == null || esNuevo){
                Localidad localidadGeneral = new Localidad();

                localidadGeneral.setLocalidadGeneral(true);
                localidadGeneral.setCodigoLocalidad(almacen.getCodigoAlmacen() + "-LG");
                localidadGeneral.setActivo(true);
                localidadGeneral.setNombre(almacen.getCodigoAlmacen() + "-LG");
                localidadGeneral.setCreadoPorId(idUsuario);
                String prefijo = "IF-" + almacen.getCodigoAlmacen();
                String nombre = "Inventario Físico " + almacen.getNombre();

                autonumericoService.create(prefijo, nombre);
                almacen.getLocalidades().add(0,localidadGeneral);
            }
        }
        almacen = almacenDao.save(almacen);

        /*if(esNuevo){
            String codigoLocalidad = almacen.getCodigoAlmacen() + "-LG";

            Localidad localidadGeneral = new Localidad();
            localidadGeneral.setCodigoLocalidad(codigoLocalidad);
            localidadGeneral.setNombre("");
            localidadGeneral.setAlmacenId(almacen.getId());

            localidadController.guardar(localidadGeneral,req);

            String prefijo = "IF-" + almacen.getCodigoAlmacen();
            String nombre = "Inventario Físico " + almacen.getNombre();

            autonumericoService.create(prefijo, nombre);
        }*/

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/{idAlmacen}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idAlmacen) throws SQLException {

        AlmacenEditarProjection almacen = almacenDao.findProjectedEditarById(idAlmacen);

        return new JsonResponse(almacen, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_ALMACENES]";
        String[] alColumnas = new String[]{"Código Almacén", "Nombre Almacén", "Responsable", "Sucursal", "Teléfono", "Activo"};

        excelController.downloadXlsx(response, "almacenes", query, alColumnas, null);
    }

}
