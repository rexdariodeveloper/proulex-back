package com.pixvs.main.dao;

import com.pixvs.main.models.ClienteRemision;
import com.pixvs.main.models.projections.ClienteRemision.ClienteRemisionEditarProjection;
import com.pixvs.main.models.projections.ClienteRemision.ClienteRemisionFacturarProjection;
import com.pixvs.main.models.projections.ClienteRemision.ClienteRemisionListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 14/06/2021.
 */
public interface ClienteRemisionDao extends CrudRepository<ClienteRemision, String> {

    // Modelo
    ClienteRemision findById(Integer id);
    List<ClienteRemision> findAllByClienteIdAndEstatusIdIn(Integer clienteId, List<Integer> estatusIds);

    // ListadoProjection
    @Query(nativeQuery = true,value = "SELECT * FROM [dbo].[VW_Listado_Remisiones]")
    List<ClienteRemisionListadoProjection> findProjectedListadoAllBy();

    // EditarProjection
    ClienteRemisionEditarProjection findProjectedEditarById(Integer id);

    // Facturar
    @Query(nativeQuery = true,value = "SELECT * FROM [dbo].[VW_Facturar_ClientesRemisiones] WHERE id = :id")
    ClienteRemisionFacturarProjection findProjectedFacturarById(@Param("id") Integer id);
    @Query(nativeQuery = true,value = "SELECT * FROM [dbo].[VW_Facturar_ClientesRemisiones] WHERE clienteId = :clienteId")
    List<ClienteRemisionFacturarProjection> findProjectedFacturarAllByClienteId(@Param("clienteId") Integer clienteId);

    // BigDecimal
    @Query(nativeQuery = true,value = "SELECT [dbo].[getClienteRemisionCantidadPendienteFacturar](:id)")
    BigDecimal getCantidadPendienteFacturar(@Param("id") Integer id);

}
