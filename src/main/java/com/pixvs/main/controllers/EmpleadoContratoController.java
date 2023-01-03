package com.pixvs.main.controllers;


import com.pixvs.main.dao.EmpleadoContratoDao;
import com.pixvs.main.dao.EmpleadoDao;
import com.pixvs.main.dao.EntidadContratoDao;
import com.pixvs.main.dao.EntidadDao;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboContratoProjection;
import com.pixvs.main.models.projections.EmpleadoContrato.EmpleadoContratoOpenOfficeProjection;
import com.pixvs.main.services.OpenOfficeService;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by Rene Carrillo on 11/08/2022.
 */
@RestController
@RequestMapping("/api/v1/empleados-contratos")
public class EmpleadoContratoController {

    @Autowired
    private Environment environment;

    @Autowired
    private EntidadDao entidadDao;

    @Autowired
    private EntidadContratoDao entidadContratoDao;

    @Autowired
    private EmpleadoContratoDao empleadoContratoDao;

    @Autowired
    private EmpleadoDao empleadoDao;

    @Autowired
    private OpenOfficeService openOfficeService;


    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public void getPDF(@RequestBody JSONObject json, HttpServletResponse response) throws Exception {
        Integer idContrato = (Integer) json.get("id");
        Integer idEntidad = (Integer) json.get("entidadId");
        String idEmpleado = json.get("empleadoId").toString();

        EmpleadoComboContratoProjection empleado = empleadoDao.findEmpleadoComboEntidad(idEntidad, 0, idEmpleado);

        // Obtenemos el word y lo pasamos PDF
        InputStream inputStream = getDocumento(idContrato, idEntidad, "PDF");
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + empleado.getNombreCompleto() + ".pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

    public InputStream getDocumento(Integer idContrato, Integer entidadId, String tipo) throws Exception {
        EmpleadoContratoOpenOfficeProjection contrato = empleadoContratoDao.findComboContratoOdtById(idContrato, entidadId);
        String archivo = contrato.getTipoContrato()+"-"+contrato.getEntidad()+".odt";
        String reportPath = environment.getProperty("environments.pixvs.reportes.location")+ File.separator +"modulos"+File.separator+"rh"+File.separator+"contratos-empleados"+File.separator+archivo;

        return openOfficeService.updateFile(reportPath, contrato, tipo);

    }
}
