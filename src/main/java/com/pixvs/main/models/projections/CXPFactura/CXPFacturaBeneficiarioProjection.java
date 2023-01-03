package com.pixvs.main.models.projections.CXPFactura;

import com.pixvs.main.models.CXPFactura;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Benjamin OB on 19/20/2021.
 */
@Projection(types = {CXPFactura.class})
public interface CXPFacturaBeneficiarioProjection {

    Integer getId();
    String getBeneficiario();

}
