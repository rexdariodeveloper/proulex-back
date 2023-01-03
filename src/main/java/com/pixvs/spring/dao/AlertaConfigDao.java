package com.pixvs.spring.dao;

import com.pixvs.spring.models.AlertaConfig;
import com.pixvs.spring.models.projections.AlertaConfig.AlertaConfigComboProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AlertaConfigDao extends CrudRepository<AlertaConfig, String> {

    AlertaConfig findById(Integer id);
    AlertaConfig findByNodoId(Integer id);

    List<AlertaConfigComboProjection> findAllProjectedBy();

    AlertaConfigComboProjection findProjectedByNodoId(Integer nodoId);

    List<AlertaConfigComboProjection> findAllProjectedByNodoId(Integer nodoId);

    @Query(value="select SUC_SucursalId as id, SUC_Nombre as nombre from Sucursales where SUC_Activo = 1", nativeQuery = true)
    List<Map <String, Object>> getSucursales();

    @Query(value="select ENT_EntidadId as id, ENT_Nombre as nombre from Entidades", nativeQuery = true)
    List<Map <String, Object>> getSucursalesEntidades();

    @Query(value="select dbo.verificarCriteriosEconomicosAlertas(:idTipoMonto,:idConfigAlerta,:montoMaximo,:montoMinimo,:idMovimiento)", nativeQuery = true)
    Boolean getAplicaCriteriosEconomicos(@Param("idTipoMonto") int idTipoMonto,@Param("idConfigAlerta") int idConfigAlerta,@Param("montoMaximo") BigDecimal montoMaximo,@Param("montoMinimo") BigDecimal montoMinimo,@Param("idMovimiento") int idMovimiento);

    List<AlertaConfigComboProjection> findAllProjectedBytipoMovimientoId(Integer control);

}
