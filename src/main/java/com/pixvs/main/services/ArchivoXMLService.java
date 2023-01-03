package com.pixvs.main.services;

import com.pixvs.main.models.JsonFacturaXML;

import java.io.File;

public interface ArchivoXMLService {

    JsonFacturaXML getDatosFacturaXML(Integer archivoId) throws Exception;
    JsonFacturaXML getDatosFacturaXML(File xml) throws Exception;

}
