package com.pixvs.main.controllers;

import com.pixvs.main.dao.FormaPagoDao;
import com.pixvs.main.models.FormaPago;
import com.pixvs.main.models.projections.FormaPago.FormaPagoListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/formas-pago")
public class FormaPagoController {

    @Autowired
    private FormaPagoDao formaPagoDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCombo() throws SQLException {

        List<FormaPagoListadoProjection> formasPago = formaPagoDao.findProjectedListadoAllByActivoTrue();

        return new JsonResponse(formasPago, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody FormaPago formaPago, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (formaPago.getId() == null) {
            formaPago.setCreadoPorId(idUsuario);
            formaPago.setActivo(true);
        } else {
            FormaPago objetoActual = formaPagoDao.findById(formaPago.getId());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), formaPago.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            formaPago.setModificadoPorId(idUsuario);
            formaPago.setActivo(objetoActual.getActivo());
            if (formaPago.getImg64() != null && formaPago.getArchivoId() != null) {
                fileSystemStorageService.borrarArchivo(formaPago.getArchivoId());
            }
        }

        if (formaPago.getImg64() != null) {
            Archivo archivo = fileSystemStorageService.storeBase64(formaPago.getImg64(), idUsuario, 13, null, true, true);
            formaPago.setArchivoId(archivo.getId());
        }

        formaPago = formaPagoDao.save(formaPago);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String id) throws SQLException {

        int actualizado = formaPagoDao.actualizarActivo(hashId.decode(id), false);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

}
