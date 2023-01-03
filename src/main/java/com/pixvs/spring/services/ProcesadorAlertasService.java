package com.pixvs.spring.services;

public interface ProcesadorAlertasService {
    boolean validarAutorizacion (Integer alertaConfigId, Integer idMovimiento, String codigoMovimiento, String nombreMovimiento, Integer sucursalId, Integer idUsuario, String textoOrigen) throws Exception;
    void actualizaEstatusAlerta (Integer idDetalleAlerta, Integer idUsuario, Boolean autorizar, String comentario) throws Exception;
}