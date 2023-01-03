package com.pixvs.main.dao;

import com.pixvs.main.models.Cliente;
import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Cliente.*;
import com.pixvs.main.models.projections.Proveedor.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteDao extends CrudRepository<Cliente, String> {

    Cliente findClienteById(Integer id);

    ClienteEditarProjection findClienteEditarProjectionById(Integer id);

    List<ClienteListadoProjection> findClienteListadoProjectionAllByIdNotNull();

    List<ClienteComboProjection> findAllByActivoIsTrue();
    List<ClienteComboProjection> findProjectedComboAllByListadoPrecioIdNotNullAndActivoTrue();
    @Query("" +
            "SELECT c \n" +
            "FROM Cliente c \n" +
            "WHERE \n" +
            "   c.consignacion = true \n" +
            "   AND c.almacenesConsignacion.size > 0 \n" +
            "   AND c.listadoPrecioId IS NOT null \n" +
            "   AND c.activo = true \n" +
            "")
    List<ClienteComboProjection> findProjectedComboAllByConsignadosAndListaPrecios();

    ClienteDatosFacturarProjection findProjectedDatosFacturarById(Integer id);

    // ClienteCardProjection
    @Query("\n" +
            "SELECT DISTINCT \n" +
            "   c.id AS id, \n" +
            "   c.nombre AS nombre \n" +
            "FROM Cliente c \n" +
            "INNER JOIN ProgramaGrupoIncompany pginc ON c.id = pginc.clienteId \n" +
            "WHERE \n" +
            "   pginc.sucursalId = :sucursalId \n" +
            "   AND pginc.activo = true \n" +
            "")
    List<ClienteCardProjection> findProjectedCardAllBySucursalInCompany(@Param("sucursalId") Integer sucursalId);

    @Modifying
    @Query(value = "UPDATE Clientes SET CLI_Activo = :activo WHERE CLI_ClienteId = :id", nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    @Query(nativeQuery = true, value = "Select * from Clientes where CLI_RFC = :rfc AND CLI_ClienteId!=:id AND CLI_Activo=1")
    List<Cliente> getClientesByRfc(@Param("rfc") String rfc, @Param("id") Integer id);
}