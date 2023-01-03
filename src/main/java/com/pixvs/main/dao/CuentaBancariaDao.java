package com.pixvs.main.dao;

import com.pixvs.main.models.CuentaBancaria;
import com.pixvs.main.models.projections.CuentaBancaria.CuentaBancariaComboProjection;
import com.pixvs.main.models.projections.CuentaBancaria.CuentaBancariaEditarProjection;
import com.pixvs.main.models.projections.CuentaBancaria.CuentaBancariaListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CuentaBancariaDao extends CrudRepository<CuentaBancaria, String> {

    @Query(value = "SELECT * FROM VW_LISTADO_CUENTAS_BANCARIAS", nativeQuery = true)
    List<Map<String, Object>> getListado();

    @Modifying
    @Query(value = "UPDATE BancosCuentas SET BAC_Activo = :estatus, BAC_USU_ModificadoPorId = :usuario WHERE BAC_CuentaId = :id", nativeQuery = true)
    Integer actualizarEstatus(@Param("id") Integer id, @Param("estatus") Boolean estatus, @Param("usuario") Integer usuario);

    CuentaBancariaEditarProjection findProjectedById(@Param("id") Integer id);

    CuentaBancaria findById(@Param("id") Integer id);

    List<CuentaBancariaListadoProjection> findProjectedListadoAllByActivoTrue();

    List<CuentaBancariaComboProjection> findProjectedComboAllByActivoTrue();
}