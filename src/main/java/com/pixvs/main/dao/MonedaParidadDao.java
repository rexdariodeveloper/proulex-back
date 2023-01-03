package com.pixvs.main.dao;

import com.pixvs.main.models.MonedaParidad;
import com.pixvs.main.models.projections.Moneda.MonedaParidadComboProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface MonedaParidadDao extends CrudRepository<MonedaParidad, String> {

    MonedaParidad findByMonedaIdAndFecha(Integer monedaId,Date fecha);

    MonedaParidad findByFecha(Date fecha);

    MonedaParidad save(MonedaParidad monedaParidad);

    Long countBy();

    @Query(nativeQuery = true, value =
            "SELECT MON_MonedaId AS MonedaId,\n" +
            "       CASE WHEN MON_Codigo = 'MXN' THEN 1 ELSE MONP_TipoCambioOficial END AS TipoCambioOficial\n" +
            "FROM Monedas\n" +
            "     OUTER APPLY\n" +
            "     (\n" +
            "          SELECT TOP 1 * FROM MonedasParidad WHERE MONP_MON_MonedaId = MON_MonedaId AND MONP_Fecha <= GETDATE() ORDER BY MONP_Fecha DESC\n" +
            "     ) AS tipoCambio\n" +
            "WHERE MON_Activo = 1")
    List<MonedaParidadComboProjection> findAllMonedaParidadComboProjected();

    @Query("" +
            "SELECT monp.tipoCambioOficial \n" +
            "FROM MonedaParidad monp \n" +
            "WHERE \n" +
            "   monp.monedaId = :monedaId \n" +
            "   AND CAST(monp.fecha AS date) = CAST(:fecha AS date) \n" +
            "")
    BigDecimal getTipoCambio(@Param("monedaId") Integer monedaId, @Param("fecha") Date fecha);
}
