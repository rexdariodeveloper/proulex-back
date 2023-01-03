package com.pixvs.main.dao;

import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.PAProfesorCategoria;
import com.pixvs.main.models.projections.PACiclos.PACicloComboProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloFechaProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloListadoProjection;
import com.pixvs.main.models.projections.PAProfesorCategoria.PAProfesorCategoriaListadoProjection;
import com.pixvs.main.models.projections.PAProfesorCategoria.PAProfesorComboProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PAProfesorCategoriaDao extends CrudRepository<PAProfesorCategoria, String> {

    PAProfesorCategoria findById(Integer id);

    List<PAProfesorCategoriaListadoProjection> findProjectedListadoAllByActivoTrue();
    List<PAProfesorComboProjection> findProjectedComboAllByActivoTrue();

    @Query(nativeQuery = true, value = "Select \n" +
            "PAPC_ProfesorCategoriaId as id,\n" +
            "PAPC_Categoria as categoria,\n" +
            "FORMAT(PAPC_SalarioDiario, 'C') as salarioDiario\n" +
            "from PAProfesoresCategorias\n" +
            "WHERE PAPC_Activo=1")
    List<PAProfesorCategoriaListadoProjection> findCategoriasFormateada();

    @Modifying
    @Query(value = "UPDATE PAProfesoresCategorias SET PAPC_Activo = :activo WHERE PAPC_ProfesorCategoriaId = :id", nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}
