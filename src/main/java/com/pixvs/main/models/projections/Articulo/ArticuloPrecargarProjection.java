package com.pixvs.main.models.projections.Articulo;

import com.pixvs.main.models.Articulo;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import com.pixvs.main.models.projections.ArticuloSubcategoria.ArticuloSubcategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloTipo.ArticuloTipoComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 */
@Projection(types = {Articulo.class})
public interface ArticuloPrecargarProjection {

    Integer getId();
    String getCodigoArticulo();
    String getNombreArticulo();
    UnidadMedidaComboProjection getUnidadMedidaInventario();
    UnidadMedidaComboProjection getUnidadMedidaConversionCompras();
    BigDecimal getFactorConversionCompras();
    ControlMaestroMultipleComboProjection getTipoCosto();
    Integer getTipoCostoId();
    BigDecimal getCostoUltimo();
    BigDecimal getCostoPromedio();
    BigDecimal getCostoEstandar();
    @Value("#{target.iva == null ? null : (target.iva * 100)}")
    BigDecimal getIva();
    Boolean getIvaExento();
    @Value("#{target.ieps == null ? null : (target.ieps * 100)}")
    BigDecimal getIeps();
    BigDecimal getIepsCuotaFija();

    ArticuloFamiliaComboProjection getFamilia();
    ArticuloCategoriaComboProjection getCategoria();
    ArticuloSubcategoriaComboProjection getSubcategoria();

    ArticuloTipoComboProjection getTipoArticulo();
    String getCuentaCompras();

}
