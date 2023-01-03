package com.pixvs.spring.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date parse(String fechaStr){
        return parse(fechaStr,"yyyy-MM-dd HH:mm:ss.SSSSSSS z");
    }

    public static Date parse(String fechaStr, String formato){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(fechaStr + " America/Mexico_City", formatter);
        return Date.from(zonedDateTime.toInstant());
    }

    public static Timestamp parseAsTimestamp(String fechaStr){
        return parseAsTimestamp(fechaStr,"yyyy-MM-dd HH:mm:ss.SSSSSSS z");
    }

    public static Timestamp parseAsTimestamp(String fechaStr, String formato){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(fechaStr + " America/Mexico_City", formatter);
        return Timestamp.from(zonedDateTime.toInstant());
    }

    public static String getFechaCompleta(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        return dateFormat.format(date);
    }

    public static String getFechaCompletaArchivos(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HHmm");
        return dateFormat.format(date);
    }

    public static String getFechaHoraMin(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        return dateFormat.format(date);
    }

    public static String getFecha(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static String getFecha(Date date, String formato){
        DateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(date);
    }

    public static String cambiarFormato(String fechaStr, String formatoEntrada, String formatoSalida){
        if(fechaStr == null){
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(formatoSalida);
        return dateFormat.format(parse(fechaStr,formatoEntrada));
    }

    /**
     * Recibe una fecha retorna otra fecha con los días indicados a sumar (o restar si son negativos)
     * @param fecha         fecha a modificar
     * @param diasSumar     dias a sumar (o restar si se ingresa un número negativo)
     * @return Nueva fecha
     */
    public static Date fechaSumar(Date fecha, Integer diasSumar){
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.DATE,diasSumar);
        return c.getTime();
    }

}
