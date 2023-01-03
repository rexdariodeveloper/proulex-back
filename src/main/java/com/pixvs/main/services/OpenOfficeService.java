package com.pixvs.main.services;

import com.pixvs.main.models.projections.EmpleadoContrato.EmpleadoContratoOpenOfficeProjection;

import java.io.InputStream;

public interface OpenOfficeService {

    InputStream updateFile(String pathFile, EmpleadoContratoOpenOfficeProjection contrato, String tipo) throws Exception;

}
