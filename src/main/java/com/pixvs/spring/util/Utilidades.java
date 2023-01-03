package com.pixvs.spring.util;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utilidades {

    public static String bytesToString(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());
    }

    public static void logFile(String nombre, String texto, boolean crear) {
        try {

            if (crear) {
                File log = new File(nombre + ".log");
                log.createNewFile();
            }

            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            Files.write(Paths.get(nombre + ".log"), ("["+timeStamp+"]\n" + texto + "\n\n").getBytes(), StandardOpenOption.APPEND);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String buscarValorJSONArray(JSONArray jsonArray, String key, String keyValue, String id){
        if(id == null)
            return null;

        for (Object object : jsonArray) {
            if(id.equals(((JSONObject) object).get(key)))
                return (String) ((JSONObject) object).get(keyValue);
        }
        return null;
    }

    public static Document StringToXml(String xml, boolean isNamespaceAware) throws Exception, ParserConfigurationException {
        DocumentBuilder builder = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(isNamespaceAware);
        builder = factory.newDocumentBuilder();
        Document document = null;
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        document = builder.parse(is);
        return document;
    }

    public static <T> List<T> getListItems(Object object, String key) {
        List<T> ids = new ArrayList<>();

        List<HashMap<String, Object>> items = object != null ? (List<HashMap<String, Object>>) object : null;

        if (items != null) {
            for (HashMap<String, Object> item : items) {
                ids.add((T) item.get(key));
            }
        }

        return !ids.isEmpty() ? ids : null;
    }

    public static <T> T getItem(Object object, String key) {
        HashMap<String, Object> json = object != null ? (HashMap<String, Object>) object : null;

        return json != null ? (T) json.get(key) : null;
    }
}
