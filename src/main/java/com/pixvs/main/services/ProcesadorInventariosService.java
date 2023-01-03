package com.pixvs.main.services;

import com.pixvs.main.models.InventarioMovimiento;

import java.math.BigDecimal;

public interface ProcesadorInventariosService {

    InventarioMovimiento procesaMovimiento(Integer articuloId, Integer localidadId, BigDecimal cantidad, String razon, String referencia, Integer referenciaMovimientoId, BigDecimal precioUnitario, Integer tipoMovimientoId, Integer usuarioId) throws Exception;

    InventarioMovimiento procesaMovimiento(Integer articuloId, Integer localidadId, BigDecimal cantidad, String razon, String referencia, Integer referenciaMovimientoId, BigDecimal precioUnitario, Integer tipoMovimientoId, Integer usuarioId, Boolean omitirInventarioNegativo) throws Exception;
}