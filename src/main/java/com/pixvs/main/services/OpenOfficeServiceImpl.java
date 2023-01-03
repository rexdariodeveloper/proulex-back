package com.pixvs.main.services;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import com.pixvs.main.dao.EmpleadoBeneficiarioDao;
import com.pixvs.main.dao.EmpleadoContratoResponsabilidadesDao;
import com.pixvs.main.dao.EmpleadoDao;
import com.pixvs.main.models.EmpleadoContratoResponsabilidades;
import com.pixvs.main.models.projections.EmpleadoContrato.EmpleadoContratoOpenOfficeProjection;
import com.pixvs.main.models.projections.EmpleadoBeneficiario.EmpleadoBeneficiarioContratoProjection;
//import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.w3c.dom.*;
//import org.jodconverter.core.DocumentConverter;
import java.util.Map;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class OpenOfficeServiceImpl implements OpenOfficeService {

    @Autowired
    private Environment environment;

    @Autowired
    private EmpleadoDao empleadoDao;

    @Autowired
    private EmpleadoBeneficiarioDao empleadoBeneficiarioDao;

    @Autowired
    private EmpleadoContratoResponsabilidadesDao empleadoContratoResponsabilidadesDao;

    /*@Resource
    private DocumentConverter documentConverter;*/

    // Ejemplo de nuevo documento
    //https://cmop17.wordpress.com/2010/11/27/java-generar-archivo-odt-usando-odftoolkit/

    public InputStream updateFile(String pathFile, EmpleadoContratoOpenOfficeProjection contrato, String formato) throws Exception {
        String urlBase = environment.getProperty("environments.pixvs.reportes.location")+ File.separator +"modulos"+File.separator+"rh"+File.separator+"contratos-empleados"+File.separator;
        String pathTemp = urlBase +"temp.odt";
        // Eliminamos si existe
        File f = new File(pathTemp);
        if(f.exists() && !f.isDirectory()) {
            f.delete();
        }
        Map<String, String> informacion = getDatos(contrato);
        List<EmpleadoContratoResponsabilidades> responsabilidadesList = empleadoContratoResponsabilidadesDao.findByEmpleadoContratoId(contrato.getId());
        List<EmpleadoBeneficiarioContratoProjection> beneficiarios = empleadoBeneficiarioDao.findProjectedBeneficiarioContratoByEmpleadoId(contrato.getEmpleadoId());
        OdfDocument odfDoc = OdfDocument.loadDocument(new File(pathFile));

        ArrayList<Node> nodosBeneficiarios = new ArrayList<>();
        ArrayList<Node> nodosResponsabilidades = new ArrayList<>();
        boolean beneficiariosEnd = false;
        boolean responsabilidadesEnd = false;

        for (int i = 0; i < odfDoc.getContentRoot().getChildNodes().getLength(); i++) {
            for (int j = 0; j < odfDoc.getContentRoot().getChildNodes().item(i).getChildNodes().getLength(); j++) {
                Node nodo = odfDoc.getContentRoot().getChildNodes().item(i).getChildNodes().item(j);
                /*Beneficiarios */
                if (nodo.getTextContent().contains("benNombre")) {

                    if (!beneficiariosEnd) {
                        nodosBeneficiarios.add(nodo);
                        for (int k = 0; k < beneficiarios.size() - 1; k++) {
                            nodosBeneficiarios.add(odfDoc.getContentRoot().getChildNodes().item(i).insertBefore(nodo.cloneNode(true), nodo));

                        }
                        editaBeneficiarios(nodosBeneficiarios, beneficiarios);
                        beneficiariosEnd = true;
                    }

                }

                if (nodo.getTextContent().contains("paramResponsabilidades")) {

                    if (!responsabilidadesEnd) {
                        nodosResponsabilidades.add(nodo);
                        for (int k = 0; k < beneficiarios.size() - 1; k++) {
                            nodosResponsabilidades.add(odfDoc.getContentRoot().getChildNodes().item(i).insertBefore(nodo.cloneNode(true), nodo));

                        }
                        editaResponsabilidad(nodosResponsabilidades, responsabilidadesList);
                        beneficiariosEnd = true;
                    }

                }

                for (String key : informacion.keySet()){
                    if(nodo.getTextContent().contains(key)){
                        updateNode(nodo.getChildNodes(), informacion);

                    }
                }


            }
        }

        NodeList headFoot = odfDoc.getMasterPages().get("Standard").getChildNodes();
        updateNode(headFoot, informacion);

        odfDoc.save(pathTemp);
        File source = new File(pathTemp);
        String pathResult;
        if(formato.equals("ODT")){
            pathResult = pathTemp;
            //documentConverter.convert(source).to(new File(pathResult)).as(DefaultDocumentFormatRegistry.DOCX).execute();
        }else{
            pathResult = urlBase +"temp.pdf";
            //documentConverter.convert(source).to(new File(pathResult)).as(DefaultDocumentFormatRegistry.PDF).execute();
        }

        return new FileInputStream(new File(pathResult));
    }

    void updateNode(NodeList elements, Map<String, String> reemplazar){
        for (Node node: asList(elements)){
            String text = node.getTextContent();
            if(node.getChildNodes() != null && node.getNodeName() != "#text"){//text.equals("")
                updateNode(node.getChildNodes(), reemplazar);
            }else{
                for (String key : reemplazar.keySet()){
                    if(text.contains(key)){
                        String newText = text.replace(key, reemplazar.get(key));
                        node.setTextContent(newText);
                    }
                }
            }
        }
    }

    void updateNodePosition(NodeList elements, String reemplazar, String value, int position){
        for (Node node: asList(elements)){
            String text = node.getTextContent();
            if(node.getChildNodes() != null && node.getNodeName() != "#text"){//text.equals("")
                updateNodePosition(node.getChildNodes(), reemplazar, value, position);
            }else{
                if(text.contains(reemplazar)){
                    String newText = position + ". " + text.replace(reemplazar, value);
                    node.setTextContent( newText);
                }
            }
        }
    }

    /**
     * Se elimino el codigo inicial, ya que en algunos ODT cambiaba el primer elemento, dependiendo de como lo leyera en el documento
     * Podia leerlo como parrafo o bien como listado, de esta forma, se accede siempre a los nodos, sin importar en que nivel se encuentren
     */
    private void editaActividades(ArrayList<Node> nodosActividades, String[] actividades) {
        int fixNumeracion = 1;
        for (int i = 0; i < nodosActividades.size(); i++) {
            Node nodo = nodosActividades.get(i);
            updateNodePosition(nodo.getChildNodes(), "paramActividad", actividades[i], (i == 0 ? actividades.length : fixNumeracion));
            if(i > 0)
                fixNumeracion ++;
        }
    }

    private void editaResponsabilidad(ArrayList<Node> nodosResponsabilidades, List<EmpleadoContratoResponsabilidades> responsabilidades) {
        int count = 0;
        for (EmpleadoContratoResponsabilidades responsabilidad: responsabilidades) {
            Node nodo = nodosResponsabilidades.get(count);
            for (int k = 0; k < nodo.getChildNodes().getLength(); k++) {
                Node nodok = nodo.getChildNodes().item(k);
                if (nodok.getTextContent().contains("paramActividad")) {
                    nodok.setTextContent(nodok.getTextContent().replace("paramActividad", responsabilidad.getDescripcion()));
                }
            }
            count++;
        }
    }

    private void editaBeneficiarios(ArrayList<Node> nodosBeneficiarios, List<EmpleadoBeneficiarioContratoProjection> beneficiarios) {
        int count = 0;
        for (EmpleadoBeneficiarioContratoProjection beneficiario: beneficiarios) {
            Node nodo = nodosBeneficiarios.get(count);
            for (int k = 0; k < nodo.getChildNodes().getLength(); k++) {
                Node nodok = nodo.getChildNodes().item(k);
                if (nodok.getTextContent().contains("benNombre")) {
                    nodok.setTextContent(nodok.getTextContent().replace("benNombre", beneficiario.getNombreCompleto()));
                }

                if (nodok.getTextContent().contains("benParentesco")) {
                    nodok.setTextContent(nodok.getTextContent().replace("benParentesco", beneficiario.getParentesco().getValor()));
                }

                if (nodok.getTextContent().contains("benPorc")) {
                    String porcentaje = new StringBuilder(beneficiario.getPorcentaje().toString()).append("%").toString();
                    nodok.setTextContent(nodok.getTextContent().replace("benPorc", porcentaje));
                }
            }
            count++;
        }
    }


    private Map<String, String> getDatos(EmpleadoContratoOpenOfficeProjection contrato){

        Map<String, String> datos = new HashMap<>();

        //if(tipo.equals("head")){
            datos.put("paramEntidad", contrato.getEntidad());
            datos.put("paramEntidadUpper", contrato.getEntidad().toUpperCase(Locale.ROOT));
            datos.put("paramTipoContrato", contrato.getTipoContrato());
            datos.put("paramFolioContrato", contrato.getFolioContrato());
        //}else if (tipo.equals("foot")){
            datos.put("paramNombreFooter", contrato.getNombreFooter());
            datos.put("paramVigencia", contrato.getVigencia());
        //}else{
            datos.put("paramNombre", contrato.getNombre());
            datos.put("paramNacionalidad", contrato.getNacionalidad());
            datos.put("paramFechaNacimiento", contrato.getFechaNacimiento());
            datos.put("paramGenero", contrato.getGenero());
            datos.put("paramEstadoCivil", contrato.getEstadoCivil());
            datos.put("paramGradoEstudio", contrato.getGradoEstudio());
            datos.put("paramCurp", contrato.getCurp());
            datos.put("paramRfc", contrato.getRfc());
            datos.put("paramDomicilio", contrato.getDomicilio());
            datos.put("paramPuesto", contrato.getPuesto());
            datos.put("paramPropositoPuesto", contrato.getPropositoPuesto());
            datos.put("paramIngresosAdicionales", (contrato.getIngresosAdicionales() == null ? BigDecimal.ZERO.toString() : contrato.getIngresosAdicionales().toString()));
            datos.put("paramSueldo", contrato.getSueldo());
            datos.put("paramFechaInicio", contrato.getFechaInicio());
            datos.put("paramFechaFin", contrato.getFechaFin());
            //datos.put("paramResponsabilidades", contrato.getResponsabilidades());
            datos.put("paramCargaHoraria", contrato.getCargaHoraria());
            datos.put("paramCoordinador", contrato.getNombreCoordinador());
            datos.put("paramJefaUnidad", contrato.getNombreJefeUnidad());
            datos.put("paramDirector", contrato.getNombreDirector());
        //}

        return datos;
    }

    public static List<Node> asList(NodeList n) {
        return n.getLength()==0?
                Collections.<Node>emptyList(): new NodeListWrapper(n);
    }
    static final class NodeListWrapper extends AbstractList<Node>
            implements RandomAccess {
        private final NodeList list;
        NodeListWrapper(NodeList l) {
            list=l;
        }
        public Node get(int index) {
            return list.item(index);
        }
        public int size() {
            return list.getLength();
        }
    }

}
