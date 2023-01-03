package com.pixvs.spring.dao;

import com.pixvs.spring.models.EmpresaPermisoComponente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpresaPermisoComponenteDao extends CrudRepository<EmpresaPermisoComponente, String> {

    @Query(nativeQuery = true, value =
            "SELECT EmpresasPermisosComponentes.*\n" +
            "FROM EmpresasPermisosComponentes\n" +
            "     INNER JOIN MenuPrincipal ON EMPC_MP_NodoId = MP_NodoId AND MP_Activo = 1\n" +
            "     INNER JOIN RolesMenus ON MP_NodoId = ROLMP_MP_NodoId AND ROLMP_ROL_RolId = :rolId\n" +
            "WHERE EMPC_EM_EmpresaId = COALESCE(:empresaId, -1)\n" +
            "ORDER BY MP_Orden")
//    @Query("SELECT mp FROM EmpresaPermisoComponente epc" +
//            "   INNER JOIN MenuPrincipal mp on epc.nodoId = mp.id AND mp.activo = true " +
//            "   INNER JOIN RolMenu rm on mp.id = rm.nodoId AND rm.rolId = :rolId " +
//            "   WHERE COALESCE(epc.empresaId, -1) = :empresaId " +
//            "   ORDER BY mp.orden")
    List<EmpresaPermisoComponente> findAllByEmpresaIdAndRolId(@Param("empresaId") Integer empresaId, @Param("rolId") Integer rolId);
}