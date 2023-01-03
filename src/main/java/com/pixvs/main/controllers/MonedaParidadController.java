package com.pixvs.main.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.pixvs.main.dao.MonedaParidadDao;
import com.pixvs.main.models.MonedaParidad;
import com.pixvs.main.models.mapeos.Monedas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 26/06/2020.
 */
@RestController
@RequestMapping("/api/v1/monedas-paridad")
public class MonedaParidadController {

    @Autowired
    private MonedaParidadDao monedaParidadDao;

    private String banxicoToken = "7bc2608a85bd8de1cf654ced0dfb8d9334ea1d6d0add3628098e5743a61ee1c3";

    @Scheduled(cron = "0 0 1 * * *")
//    @Scheduled(cron = "0 46 10 * * *")
    private void actualizarParidad() throws Exception{

        Date fechaActual = new Date();
        dateTo0(fechaActual);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        String urlBancoService = "https://www.banxico.org.mx/SieAPIRest/service/v1/series/SF43718/datos/oportuno";
        int responseCode;
        HttpURLConnection con;
        BigDecimal paridadBanco = null;

        con = getConection(urlBancoService,true);
        responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK){
            paridadBanco = getParidadBanco(con);
        }

        if(paridadBanco != null){
            MonedaParidad paridadNueva = new MonedaParidad();
            paridadNueva.setMonedaId(Monedas.DOLAR);
            paridadNueva.setFecha(dateTo0(fechaActual));
            paridadNueva.setTipoCambioOficial(paridadBanco);

            monedaParidadDao.save(paridadNueva);
        }
    }

    private Date dateTo0(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date dateTo2359(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    private BigDecimal getParidadBanco(HttpURLConnection con) throws Exception {

        Date fechaActual = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        try{
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String,Object> response = mapper.readValue(con.getInputStream(), HashMap.class);
            HashMap<String,Object> bmx = ((HashMap<String,Object>)response.get("bmx"));
            HashMap<String,Object> serie = ((List<HashMap<String,Object>>)bmx.get("series")).get(0);
            HashMap<String,Object> dato = ((List<HashMap<String,Object>>)serie.get("datos")).get(0);
            return new BigDecimal((String)dato.get("dato"));
        }catch (Exception e){
            return null;
        }
    }

    private HttpURLConnection getConection(String ruta, boolean bmxToken) throws Exception{

        URL url = new URL(ruta);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");
        if(bmxToken){
            con.setRequestProperty("Bmx-Token",banxicoToken);
        }

        return con;

    }

}
