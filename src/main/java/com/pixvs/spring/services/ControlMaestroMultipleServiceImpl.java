package com.pixvs.spring.services;

import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.query.procedure.internal.ProcedureParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@Service
public class ControlMaestroMultipleServiceImpl implements ControlMaestroMultipleService {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;

    public Integer spInsertCMM(ControlMaestroMultiple cmm){
        StoredProcedureQuery query = em.createStoredProcedureQuery("sp_InsertCMM")
                .registerStoredProcedureParameter(
                        "control",
                        String.class,
                        ParameterMode.IN
                ).registerStoredProcedureParameter(
                        "valor",
                        String.class,
                        ParameterMode.IN
                ).registerStoredProcedureParameter(
                        "referencia",
                        String.class,
                        ParameterMode.IN
                ).registerStoredProcedureParameter(
                        "referenciaId",
                        Integer.class,
                        ParameterMode.IN
                ).registerStoredProcedureParameter(
                        "usuarioId",
                        Integer.class,
                        ParameterMode.IN
                ).registerStoredProcedureParameter(
                        "id",
                        Integer.class,
                        ParameterMode.INOUT
                );

        for (Parameter parameter : query.getParameters()) {
            ((ProcedureParameterImpl) parameter).enablePassingNulls(true);
        }

        Integer id = null;
        query.setParameter("control", cmm.getControl())
                .setParameter("valor", cmm.getValor())
                .setParameter("referencia", cmm.getReferencia())
                .setParameter("referenciaId", cmm.getCmmReferenciaId())
                .setParameter("usuarioId", cmm.getCreadoPorId())
                .setParameter("id", id);

        query.execute();

        return (Integer)query.getOutputParameterValue("id");
    }

}
