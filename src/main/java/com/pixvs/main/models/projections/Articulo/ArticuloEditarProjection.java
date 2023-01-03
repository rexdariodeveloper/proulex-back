package com.pixvs.main.models.projections.Articulo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.*;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloComponente.ArticuloComponenteEditarProjection;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import com.pixvs.main.models.projections.ArticuloSubcategoria.ArticuloSubcategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloSubtipo.ArticuloSubtipoComboProjection;
import com.pixvs.main.models.projections.ArticuloTipo.ArticuloTipoComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 */
@Projection(types = {Articulo.class})
public interface ArticuloEditarProjection {

    Integer getId();
    String getCodigoArticulo();
    String getNombreArticulo();
    String getCodigoBarras();
    String getCodigoAlterno();
    String getNombreAlterno();
    String getDescripcion();
    String getDescripcionCorta();
    String getClaveProductoSAT();
    Boolean getArticuloParaVenta();
    @Value("#{target.iva == null ? null : (target.iva * 100)}")
    BigDecimal getIva();
    Boolean getIvaExento();
    @Value("#{target.ieps == null ? null : (target.ieps * 100)}")
    BigDecimal getIeps();
    BigDecimal getIepsCuotaFija();
    BigDecimal getMultiploPedido();
    Boolean getPermitirCambioAlmacen();
    BigDecimal getMaximoAlmacen();
    BigDecimal getMinimoAlmacen();
    Boolean getPlaneacionTemporadas();
    ArchivoProjection getImagen();
    Integer getImagenId();
    ArticuloFamiliaComboProjection getFamilia();
    Integer getFamiliaId();
    ArticuloCategoriaComboProjection getCategoria();
    Integer getCategoriaId();
    ArticuloSubcategoriaComboProjection getSubcategoria();
    Integer getSubcategoriaId();
    ControlMaestroMultipleComboProjection getClasificacion1();
    Integer getClasificacion1Id();
    ControlMaestroMultipleComboProjection getClasificacion2();
    Integer getClasificacion2Id();
    ControlMaestroMultipleComboProjection getClasificacion3();
    Integer getClasificacion3Id();
    ControlMaestroMultipleComboProjection getClasificacion4();
    Integer getClasificacion4Id();
    ControlMaestroMultipleComboProjection getClasificacion5();
    Integer getClasificacion5Id();
    ControlMaestroMultipleComboProjection getClasificacion6();
    Integer getClasificacion6Id();
    ArticuloTipoComboProjection getTipoArticulo();
    Integer getTipoArticuloId();
    ArticuloSubtipoComboProjection getArticuloSubtipo();
    Integer getArticuloSubtipoId();
    UnidadMedidaComboProjection getUnidadMedidaInventario();
    Integer getUnidadMedidaInventarioId();
    UnidadMedidaComboProjection getUnidadMedidaConversionVentas();
    Integer getUnidadMedidaConversionVentasId();
    BigDecimal getFactorConversionVentas();
    UnidadMedidaComboProjection getUnidadMedidaConversionCompras();
    Integer getUnidadMedidaConversionComprasId();
    BigDecimal getFactorConversionCompras();
    ControlMaestroMultipleComboProjection getTipoCosto();
    Integer getTipoCostoId();
    BigDecimal getCostoUltimo();
    BigDecimal getCostoPromedio();
    BigDecimal getCostoEstandar();
    Boolean getActivo();
    Boolean getInventariable();

    ControlMaestroMultipleComboProjection getIdioma();
    ProgramaComboProjection getPrograma();
    ControlMaestroMultipleComboProjection getEditorial();
    String getMarcoCertificacion();

    ControlMaestroMultipleComboProjection getObjetoImpuesto();

    Boolean getPedirCantidadPV();

    List<SucursalComboProjection> getMostrarSucursales();
    List<SucursalComboProjection> getMostrarSucursalesPV();

    List<ArticuloComponenteEditarProjection> getComponentes();


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();



}