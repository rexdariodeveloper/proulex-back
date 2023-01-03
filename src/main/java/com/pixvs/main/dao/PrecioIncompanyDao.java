package com.pixvs.main.dao;

import com.pixvs.main.models.Banco;
import com.pixvs.main.models.PrecioIncompany;
import com.pixvs.main.models.projections.Banco.BancoComboProjection;
import com.pixvs.main.models.projections.Banco.BancoEditarProjection;
import com.pixvs.main.models.projections.Banco.BancoListadoProjection;
import com.pixvs.main.models.projections.PrecioIncompany.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrecioIncompanyDao extends CrudRepository<PrecioIncompany, String> {

    //List<BancoComboProjection> findProjectedComboAllByActivoTrueOrderByNombre();

    List<PrecioIncompanyListadoProjection> findAllBy();
    PrecioIncompanyEditarProjection findProjectionById(Integer id);
    PrecioIncompany findById(Integer id);
    List<PrecioIncompanyComboProjection> findCombosProjectionByEstatusId(Integer estatusId);
    List<PrecioIncompanyComisionProjection> findPrecioIncompanyComisionProjectionByEstatusId(Integer estatusId);

    @Modifying
    @Query(value = "UPDATE PreciosIncompany SET PREINC_CMM_EstatusId = :estatus WHERE PREINC_PrecioIncompanyId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("estatus") Integer estatus);

    @Query(value = "Select TOP 1 PREINCD_PrecioVenta as precioVenta, PREINCD_PorcentajeTransporte as porcentaje from PreciosIncompanyDetalles\n" +
            "INNER JOIN PreciosIncompany on PREINC_PrecioIncompanyId = PREINCD_PREINC_PrecioIncompanyId\n" +
            "INNER JOIN PreciosIncompanySucursales on PREINC_PrecioIncompanyId = PREINCS_PREINC_PrecioIncompanyId\n" +
            "WHERE PREINC_CMM_EstatusId = 2001000 AND PREINCD_PROG_ProgramaId=:programaId AND PREINCD_PAMOD_ModalidadId = :modalidadId " +
            "AND PREINCD_PAMODH_PAModalidadHorarioId = :horarioId AND PREINCD_CMM_IdiomaId = :idiomaId AND PREINC_PrecioIncompanyId=:precioId", nativeQuery = true)
    PrecioIncompanyVentaProjection findProjectedDatosVenta(@Param("programaId") Integer programaId,
                                                                 @Param("modalidadId") Integer modalidadId,
                                                                 @Param("horarioId") Integer horarioId,
                                                                 @Param("idiomaId") Integer idiomaId,
                                                                 @Param("precioId") Integer precioId);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[fn_getComboZona](:sedeId, :programaId, :idiomaId, :modalidadId, :modalidadHorarioId)")
    List<PrecioIncompanyComboZonaProjection> findPrecioIncompanyComboZonaProjection(@Param("sedeId") Integer sedeId,
                                                                                    @Param("programaId") Integer programaId,
                                                                                    @Param("idiomaId") Integer idiomaId,
                                                                                    @Param("modalidadId") Integer modalidadId,
                                                                                    @Param("modalidadHorarioId") Integer modalidadHorarioId);
}
