package com.pixvs.main.services;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ArticulosTipos;
import com.pixvs.main.models.mapeos.ControlesMaestros;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.models.ControlMaestro;
import com.pixvs.spring.handler.exceptions.InventarioNegativoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_ART_TipoCosto.*;
import static com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.RECIBO_OC;

@Service
public class ProcesadorInventariosServiceImpl implements ProcesadorInventariosService {

    @Autowired
    private InventarioMovimientoDao inventarioMovimientoDao;

    @Autowired
    private LocalidadArticuloAcumuladoDao localidadArticuloAcumuladoDao;

    @Autowired
    private ArticuloDao articuloDao;

    @Autowired
    private LocalidadDao localidadDao;

    @Autowired
    private ControlMaestroDao controlMaestroDao;

    @Autowired
    private LocalidadArticuloDao localidadArticuloDao;

    public InventarioMovimiento procesaMovimiento(Integer articuloId, Integer localidadId, BigDecimal cantidad, String razon, String referencia, Integer referenciaMovimientoId, BigDecimal precioUnitario, Integer tipoMovimientoId, Integer usuarioId) throws Exception {

        return procesaMovimiento(articuloId,localidadId,cantidad,razon,referencia,referenciaMovimientoId,precioUnitario,tipoMovimientoId,usuarioId,BigDecimal.ONE, false);
    }

    public InventarioMovimiento procesaMovimiento(Integer articuloId, Integer localidadId, BigDecimal cantidad, String razon, String referencia, Integer referenciaMovimientoId, BigDecimal precioUnitario, Integer tipoMovimientoId, Integer usuarioId, Boolean omitirInventarioNegativo) throws Exception {

        return procesaMovimiento(articuloId,localidadId,cantidad,razon,referencia,referenciaMovimientoId,precioUnitario,tipoMovimientoId,usuarioId,BigDecimal.ONE, omitirInventarioNegativo);
    }

    public InventarioMovimiento procesaMovimiento(Integer articuloId, Integer localidadId, BigDecimal cantidad, String razon, String referencia, Integer referenciaMovimientoId, BigDecimal precioUnitario, Integer tipoMovimientoId, Integer usuarioId, BigDecimal multiplicador, Boolean omitirInventarioNegativo) throws Exception {

        cantidad = cantidad.multiply(multiplicador);

        Articulo articuloValidar = articuloDao.findById(articuloId);
        if (articuloValidar.getTipoArticuloId().intValue() == ArticulosTipos.PT_FABRICADO) {
            for (ArticuloComponente componente : articuloValidar.getComponentes()) {
                procesaMovimiento(componente.getComponenteId(), localidadId, componente.getCantidad().multiply(cantidad), razon, referencia, referenciaMovimientoId, precioUnitario, tipoMovimientoId, usuarioId, omitirInventarioNegativo);
            }
            return null;
        }
        if (!articuloValidar.getInventariable()) {
            return null;
        }

        InventarioMovimiento inventarioMovimiento = new InventarioMovimiento();
        LocalidadArticuloAcumulado localidadArticuloAcumulado;

        inventarioMovimiento.setArticuloId(articuloId);
        inventarioMovimiento.setLocalidadId(localidadId);

        inventarioMovimiento.setCantidad(cantidad);
        inventarioMovimiento.setRazon(razon);
        inventarioMovimiento.setReferencia(referencia);
        inventarioMovimiento.setReferenciaMovimientoId(referenciaMovimientoId);
        inventarioMovimiento.setPrecioUnitario(precioUnitario);
        inventarioMovimiento.setTipoMovimientoId(tipoMovimientoId);

        inventarioMovimiento.setCreadoPorId(usuarioId);

        Articulo articulo = articuloDao.findById(articuloId);

        Localidad localidad = localidadDao.findById(localidadId);

        LocalidadArticulo localidadArticulo = localidadArticuloDao.findByArticuloIdAndLocalidadId(articuloId, localidadId);
        if (localidadArticulo == null) {
            localidadArticulo = new LocalidadArticulo();
            localidadArticulo.setArticuloId(articuloId);
            localidadArticulo.setLocalidadId(localidadId);
            localidadArticuloDao.save(localidadArticulo);
        }

        localidadArticuloAcumulado = localidadArticuloAcumuladoDao.findByArticuloIdAndLocalidadId(articuloId, localidadId);

        if (localidadArticuloAcumulado == null) {
            localidadArticuloAcumulado = new LocalidadArticuloAcumulado();
            localidadArticuloAcumulado.setArticuloId(articuloId);
            localidadArticuloAcumulado.setLocalidadId(localidadId);
            localidadArticuloAcumulado.setCantidad(BigDecimal.ZERO);
        }

        localidadArticuloAcumulado.setCantidad(localidadArticuloAcumulado.getCantidad().add(cantidad));

        if(cantidad.compareTo(BigDecimal.ZERO) < 0){
            ControlMaestro cmPermitirInventarioNegativo = controlMaestroDao.findCMByNombre(ControlesMaestros.CM_Permitir_Inventario_Negativo);
            if (!omitirInventarioNegativo && !cmPermitirInventarioNegativo.getValorAsBoolean() && localidadArticuloAcumulado.getCantidad().compareTo(BigDecimal.ZERO) < 0) {
                throw new InventarioNegativoException();
            }
        }

        localidadArticuloAcumuladoDao.save(localidadArticuloAcumulado);

        if (localidad == null) {
            throw new Exception("La Localidad no existe");
        } else if (!localidad.getActivo()) {
            throw new Exception("La Localidad no esta Activo");
        }

        if (RECIBO_OC == inventarioMovimiento.getTipoMovimientoId() && precioUnitario != null) {
            articuloDao.actualizarCostoUltimo(articuloId, precioUnitario);
        }

        inventarioMovimiento.setUnidadMedidaId(articulo.getUnidadMedidaInventarioId());
        inventarioMovimiento.setTipoCostoId(articulo.getTipoCostoId());

        switch (articulo.getTipoCostoId()) {
            case ULTIMO: inventarioMovimiento.setCostoUnitario(articulo.getCostoUltimo()); break;
            case PROMEDIO: inventarioMovimiento.setCostoUnitario(articulo.getCostoPromedio()); break;
            case ESTANDAR: inventarioMovimiento.setCostoUnitario(articulo.getCostoEstandar()); break;
        }

        return inventarioMovimientoDao.save(inventarioMovimiento);
    }
}