package com.pixvs.spring.dao;

import com.pixvs.spring.models.Autonumerico;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AutonumericoDao extends CrudRepository<Autonumerico, String> {

    Autonumerico findAutonumericoById(Integer id);

    Autonumerico findAutonumericoByPrefijoAndActivoIsTrue(String prefijo);

    Autonumerico findAutonumericoByIdAndActivoIsTrue(Integer id);

    @Modifying
//    @Query(value = "UPDATE Autonumericos set AUT_Siguiente = :siguiente WHERE AUT_AutonumericoId = :id",
//            nativeQuery = true)
    @Query(value = "UPDATE Autonumerico set siguiente = :siguiente WHERE id = :id")
    int actualizarSiguiente(@Param("id") Integer id, @Param("siguiente") Integer siguiente);

    Autonumerico findAutonumericoByReferenciaIdAndActivoIsTrue(Integer referenciaId);

    Autonumerico findAutonumericoByReferenciaIdAndNombreContainsAndActivoIsTrue(Integer referenciaId, String nombre);
}
