package com.pixvs.main.models.cfdi;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public final class NamespacePrefixMapperImpl extends NamespacePrefixMapper {
    public NamespacePrefixMapperImpl() {
    }

    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        switch (namespaceUri) {
            case "http://www.w3.org/2001/XMLSchema-instance":
                return "xsi";

            case "http://www.sat.gob.mx/cfd/3":
            case "http://www.sat.gob.mx/cfd/4":
                return "cfdi";

            case "http://www.sat.gob.mx/Pagos":
                return "pago10";

            case "http://www.sat.gob.mx/Pagos20":
                return "pago20";

            case "http://www.sat.gob.mx/TimbreFiscalDigital":
                return "tfd";

            default:
                return suggestion;
        }
    }

    public String[] getPreDeclaredNamespaceUris() {
        return new String[0];
    }
}