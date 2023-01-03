package com.pixvs.main.services;

import com.pixvs.main.dao.ControlMaestroMainDao;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SATServiceImpl implements SATService {

    @Autowired
    private ControlMaestroMainDao controlMaestroMainDao;

    public Boolean proveedorEnListaNegra(String rfc) throws Exception {
        URL url = new URL(controlMaestroMainDao.fnGetPathServiceSAT() + rfc);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("GET");

        if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new Exception("No se pudo validar la lista negra del SAT. Contacte al administrador.");
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        JSONArray jsonArray = (JSONArray) ((JSONObject) JSONValue.parse(response.toString())).get("data");

        return !jsonArray.isEmpty();
    }
}