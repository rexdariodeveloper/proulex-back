package com.pixvs.spring.models;

public class JsonResponse {

    private int status;

    private String message;

    private Object data;

    private String title;

    public static final int STATUS_OK = 200;
    public static final int STATUS_OK_REGISTRO_NO_ENCONTRADO = 1201;
    public static final int STATUS_OK_ACCION_NO_DISPONIBLE = 1202;
    public static final int STATUS_OK_USUARIO_INACTIVO = 1203;
    public static final int STATUS_ERROR = 500;
    public static final int STATUS_ERROR_SERVIDOR = 1500;
    public static final int STATUS_ERROR_SQL = 1501;
    public static final int STATUS_ERROR_NULL = 1502;
    public static final int STATUS_ERROR_USUARIO_CREDENCIALES = 1503;
    public static final int STATUS_ERROR_MEDIA_TYPE = 1504;
    public static final int STATUS_ERROR_REGISTRO_DUPLICADO = 1505;
    public static final int STATUS_ERROR_INVENTARIO_NEGATIVO = 1506;
    public static final int STATUS_ERROR_DATOS_NO_COINCIDEN = 1507;
    public static final int STATUS_ERROR_SQL_NULL= 1515;
    public static final int STATUS_ERROR_SQL_DELETE_FK= 1547;
    public static final int STATUS_ERROR_SQL_INTEGRIDAD= 1548;
    public static final int STATUS_ERROR_CONEXION_SERVIDOR= 1555;
    public static final int STATUS_ERROR_CONEXION_SQL_SERVIDOR= 1556;
    public static final int STATUS_ERROR_CONCURRENCIA= 1557;
    public static final int STATUS_ERROR_CORREO_DUPLICADO= 1558;
    public static final int STATUS_ERROR_PROBLEMA = 1599;
    public static final int STATUS_ERROR_CLIENT_ABORT = 1610;
    public static final int STATUS_ERROR_ADVERTENCIA = 1700;
    public static final int STATUS_ERROR_CON_DATOS = 900;

    public static final int STATUS_ERROR_DROPZONE = 1700;

    public static final int STATUS_PROVEEDOR_MISMO_RFC=1800;
    public static final int STATUS_DOS_PROVEEDORES_MISMO_RFC=1900;

    public static final int STATUS_ERROR_PUNTO_VENTA_SUCURSAL = 2000;
    public static final int STATUS_ERROR_PUNTO_VENTA_INSCRIPCION_INVENTARIO_NEGATIVO = 2001;

    public static final int STATUS_ERROR_RECEPCION_SOLICITUD_CERTIFICADOS = 3000;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JsonResponse() {}

    public JsonResponse(Object data, String message, int status) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.title = null;
    }

    public JsonResponse(Object data) {
        this.status = STATUS_OK;
        this.message = "OK";
        this.data = data;
        this.title = null;
    }

    public JsonResponse(Object data, String message) {
        this.status = STATUS_OK;
        this.message = message;
        this.data = data;
        this.title = null;
    }

    public JsonResponse(Object data, String message, String title) {
        this.status = STATUS_OK;
        this.message = message;
        this.data = data;
        this.title = title;
    }

    public JsonResponse(Object data, String message, String title, int status) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.title = title;
    }
}
