package com.pixvs.spring.dao;

import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleCardProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ControlMaestroMultipleDao extends CrudRepository<ControlMaestroMultiple, String> {

    ControlMaestroMultiple findCMMById(Integer id);

    ControlMaestroMultipleComboProjection findById(Integer id);
    List<ControlMaestroMultipleComboProjection> findAllByControl(String control);
    List<ControlMaestroMultipleComboProjection> findAllByControlAndActivoIsTrueOrderByValor(String control);
    List<ControlMaestroMultipleComboProjection> findAllByControlAndActivoIsTrueOrderBySistemaDescValorAsc(String control);
    List<ControlMaestroMultipleComboProjection> findAllByControlAndActivoIsTrueOrderByFechaCreacion(String control);
    List<ControlMaestroMultipleComboProjection> findAllByControlInOrderByValor(String[] control);
    List<ControlMaestroMultipleComboProjection> findAllByIdInOrderByValor(Integer[] id);
    List<ControlMaestroMultipleComboProjection> findAllByCmmReferenciaIdAndActivoIsTrueOrderByValor(Integer cmmReferenciaId);

    List<ControlMaestroMultipleCardProjection> findAllByControlAndActivoIsTrueOrderByOrden(String control);
    List<ControlMaestroMultipleCardProjection> findAllByControlAndActivoIsTrueAndSistemaIsFalseOrderByOrden(String control);
    ControlMaestroMultipleCardProjection findProjectedCardById(Integer id);
    List<ControlMaestroMultipleCardProjection> findProjectedCardByIdIn(List<Integer> ids);

    ControlMaestroMultiple findByValor(String valor);

    @Query(nativeQuery = true, value = "SELECT [dbo].[getCMMReferencia](:control)")
    String getCMMReferencia(@Param("control") String control);

    @Modifying
    @Query(value = "UPDATE ControlesMaestrosMultiples SET CMM_Activo = :activo WHERE CMM_ControlId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    List<ControlMaestroMultipleComboSimpleProjection> findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByReferencia(@Param("control") String control);
    @Query(nativeQuery = true , value =
            "SELECT CMM_ControlId AS id,\n" +
            "       CMM_Valor AS valor,\n" +
            "       CMM_Referencia AS referencia\n" +
            "FROM ControlesMaestrosMultiples\n" +
            "WHERE CMM_Control = :control\n" +
            "      AND CMM_Activo = 1\n" +
            "ORDER BY CMM_Valor"
    )
    List<ControlMaestroMultipleComboSimpleProjection> findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByValor(@Param("control") String control);
    @Query(nativeQuery = true , value = "" +
            "SELECT \n" +
            "   CMM_ControlId AS id, \n" +
            "   CMM_Valor AS valor, \n" +
            "   CMM_Referencia AS referencia \n" +
            "FROM ControlesMaestrosMultiples \n" +
            "WHERE CMM_ControlId = :id AND CMM_Activo = 1 \n" +
            "")
    ControlMaestroMultipleComboSimpleProjection findComboSimpleProjectionById(@Param("id") Integer id);

    ControlMaestroMultipleComboSimpleProjection findByControlAndReferencia(String control, String referencia);

    ControlMaestroMultipleComboSimpleProjection findComboSimpleProjectedById(Integer id);
}
