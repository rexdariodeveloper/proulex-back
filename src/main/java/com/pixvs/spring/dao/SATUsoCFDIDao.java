package com.pixvs.spring.dao;

import com.pixvs.spring.models.SATUsoCFDI;
import com.pixvs.spring.models.projections.SATUsoCFDI.SATUsoCFDIComboProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SATUsoCFDIDao extends CrudRepository<SATUsoCFDI, String> {

    SATUsoCFDI findById(Integer id);

    SATUsoCFDIComboProjection findComboProjectedByCodigoAndActivoTrue(String codigo);

    List<SATUsoCFDIComboProjection> findAllComboProjectedByActivoTrueOrderByCodigo();

    @Modifying
    @Query(value = "UPDATE SATUsosCFDI SET UCFDI_Activo = :activo WHERE UCFDI_UsoCFDIId = :id", nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    @Query(nativeQuery = true, value =
            "SELECT UCFDI_UsoCFDIId AS id,\n" +
            "       UCFDI_Codigo AS codigo,\n" +
            "       UCFDI_Descripcion AS descripcion,\n" +
            "       UCFDI_Fisica AS fisica,\n" +
            "       UCFDI_Moral AS moral,\n" +
            "       UCFDI_Activo AS activo\n" +
            "FROM SATUsosCFDI\n" +
            "     INNER JOIN SATUsosCFDIRegimenesFiscales ON UCFDI_UsoCFDIId = UCFDIRF_UCFDI_UsoCFDIId AND UCFDIRF_Activo = 1\n" +
            "     INNER JOIN SATRegimenesFiscales ON UCFDIRF_RF_RegimenFiscalId = RF_RegimenFiscalId AND RF_Activo = 1 AND RF_RegimenFiscalId = :regimenFiscalId\n" +
            "WHERE UCFDI_Activo = 1\n" +
            "ORDER BY codigo")
    List<SATUsoCFDIComboProjection> findAllComboProjectedByRegimenFiscalId(@Param("regimenFiscalId") Integer regimenFiscalId);
}