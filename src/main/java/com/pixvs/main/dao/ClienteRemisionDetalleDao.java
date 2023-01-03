package com.pixvs.main.dao;

import com.pixvs.main.models.ClienteRemisionDetalle;
import com.pixvs.main.models.projections.ClienteRemisionDetalle.ClienteRemisionDetalleFacturarProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 16/06/2021.
 */
public interface ClienteRemisionDetalleDao extends CrudRepository<ClienteRemisionDetalle, String> {

    // Modelo
    ClienteRemisionDetalle findById(Integer id);

    // Facturar
    @Query(nativeQuery = true,value = "SELECT * FROM [dbo].[VW_Facturar_ClientesRemisionesDetalles] WHERE id = :id")
    ClienteRemisionDetalleFacturarProjection findProjectedFacturarById(@Param("id") Integer id);
    @Query(nativeQuery = true,value = "SELECT * FROM [dbo].[VW_Facturar_ClientesRemisionesDetalles] WHERE clienteRemisionId = :clienteRemisionId")
    List<ClienteRemisionDetalleFacturarProjection> findProjectedFacturarAllByClienteRemisionId(@Param("clienteRemisionId") Integer clienteRemisionId);

}
