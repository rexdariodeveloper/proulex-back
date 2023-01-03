package com.pixvs.main.dao;

import com.pixvs.main.models.DatosFacturacion;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_RFC_TipoPersona;
import com.pixvs.main.models.projections.DatosFacturacion.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DatosFacturacionDao extends CrudRepository<DatosFacturacion, String> {

    DatosFacturacion findById(Integer id);

    @Query(nativeQuery = true, value =
            "SELECT DF_DatosFacturacionId AS Id,\n" +
            "       DF_RFC AS rfc,\n" +
            "       ISNULL(ALU_Codigo, CLI_Codigo) AS Codigo,\n" +
            "       DF_Nombre AS Nombre,\n" +
            "       DF_PrimerApellido AS PrimerApellido,\n" +
            "       DF_SegundoApellido AS SegundoApellido,\n" +
            "       DF_RazonSocial AS RazonSocial,\n" +
            "       DF_RF_RegimenFiscalId AS RegimenFiscalId,\n" +
            "       DF_CMM_TipoPersonaId AS TipoPersonaId,\n" +
            "       CONVERT(BIT, CASE WHEN ALU_AlumnoId IS NOT NULL THEN 1 ELSE 0 END) AS Alumno,\n" +
            "       CONVERT(BIT, CASE WHEN CLI_ClienteId IS NOT NULL THEN 1 ELSE 0 END) AS Cliente,\n" +
            "       DF_RFC+' - ('+ISNULL(ALU_Codigo, CLI_Codigo)+')'+ISNULL(' '+ISNULL(DF_RazonSocial, (DF_Nombre+ISNULL(' '+DF_PrimerApellido, '')+ISNULL(' '+DF_SegundoApellido, ''))), '') AS Valor\n" +
            "FROM DatosFacturacion\n" +
            "     LEFT JOIN AlumnosDatosFacturacion ON DF_DatosFacturacionId = ADF_DF_DatosFacturacionId\n" +
            "     LEFT JOIN Alumnos ON ADF_ALU_AlumnoId = ALU_AlumnoId\n" +
            "     LEFT JOIN ClientesDatosFacturacion ON DF_DatosFacturacionId = CDF_DF_DatosFacturacionId\n" +
            "     LEFT JOIN Clientes ON CDF_CLI_ClienteId = CLI_ClienteId\n" +
            "WHERE(ALU_AlumnoId IS NOT NULL AND ALU_Activo = 1)\n" +
            "     OR (CLI_ClienteId IS NOT NULL AND CLI_Activo = 1)\n" +
            "ORDER BY Valor")
    List<DatosFacturacionComboProjection> findDatosFacturacionComboProjection();

    @Query(nativeQuery = true, value =
            "SELECT ROW_NUMBER() OVER(ORDER BY Rfc ASC) AS Id,\n" +
            "       Rfc\n" +
            "FROM\n" +
            "(\n" +
            "    SELECT DISTINCT\n" +
            "           Rfc\n" +
            "    FROM\n" +
            "    (\n" +
            "        SELECT DISTINCT\n" +
            "            CXCF_ReceptorRFC AS Rfc\n" +
            "        FROM CXCFacturas\n" +
            "        UNION ALL\n" +
            "        SELECT DISTINCT\n" +
            "            DF_RFC AS Rfc\n" +
            "        FROM DatosFacturacion\n" +
            "          LEFT JOIN AlumnosDatosFacturacion ON DF_DatosFacturacionId = ADF_DF_DatosFacturacionId\n" +
            "          LEFT JOIN Alumnos ON ADF_ALU_AlumnoId = ALU_AlumnoId\n" +
            "          LEFT JOIN ClientesDatosFacturacion ON DF_DatosFacturacionId = CDF_DF_DatosFacturacionId\n" +
            "          LEFT JOIN Clientes ON CDF_CLI_ClienteId = CLI_ClienteId\n" +
            "        WHERE(ALU_AlumnoId IS NOT NULL AND ALU_Activo = 1)\n" +
            "          OR (CLI_ClienteId IS NOT NULL AND CLI_Activo = 1)\n" +
            "    ) AS todo\n" +
            ") AS todo\n" +
            "ORDER BY rfc")
    List<DatosFacturacionRfcComboProjection> findDatosFacturacionRfcComboProjection();

    @Query(nativeQuery = true, value =
            "SELECT ISNULL(1000000  +  ALU_AlumnoId, 2000000  +  CLI_ClienteId) AS Id,\n" +
            "       ISNULL(ALU_Codigo, CLI_Codigo) AS Codigo,\n" +
            "       ISNULL(ALU_Nombre + ISNULL(' ' + ALU_PrimerApellido, '') + ISNULL(' ' + ALU_SegundoApellido, ''), CLI_RazonSocial) AS Nombre\n" +
            "FROM DatosFacturacion\n" +
            "     LEFT JOIN AlumnosDatosFacturacion ON DF_DatosFacturacionId = ADF_DF_DatosFacturacionId\n" +
            "     LEFT JOIN Alumnos ON ADF_ALU_AlumnoId = ALU_AlumnoId\n" +
            "     LEFT JOIN ClientesDatosFacturacion ON DF_DatosFacturacionId = CDF_DF_DatosFacturacionId\n" +
            "     LEFT JOIN Clientes ON CDF_CLI_ClienteId = CLI_ClienteId\n" +
            "GROUP BY ISNULL(1000000  +  ALU_AlumnoId, 2000000  +  CLI_ClienteId),\n" +
            "         ISNULL(ALU_Codigo, CLI_Codigo),\n" +
            "         ISNULL(ALU_Nombre + ISNULL(' ' + ALU_PrimerApellido, '') + ISNULL(' ' + ALU_SegundoApellido, ''), CLI_RazonSocial)\n" +
            "ORDER BY Id, Codigo\n" +
            "OPTION (RECOMPILE)")
    List<DatosFacturacionClientesComboProjection> findDatosFacturacionClienteComboProjection();

    @Query(nativeQuery = true, value =
            "SELECT DF_DatosFacturacionId AS id,\n" +
            "       ISNULL(DF_RazonSocial, DF_Nombre+ISNULL(' '+DF_PrimerApellido, '')+ISNULL(' '+DF_SegundoApellido, '')) AS nombre,\n" +
            "       RF_Codigo AS regimenFiscal,\n" +
            "       DF_CP AS cp,\n" +
            "       TRIM(DF_Calle)+' '+TRIM(DF_NumeroExterior)+ISNULL(' Int. '+TRIM(DF_NumeroInterior), '')+' Col. '+TRIM(DF_Colonia)+ISNULL(', '+ISNULL(MUN_Nombre, TRIM(DF_Ciudad)), '')+ISNULL(', '+EST_Nombre, '')+ISNULL(', '+PAI_Nombre, '') AS domicilio,\n" +
            "       DF_CorreoElectronico AS correo,\n" +
            "       CONVERT(BIT,\n" +
            "              CASE WHEN \n" +
            "              TRIM(ISNULL(DF_CP, '')) = ''\n" +
            "              OR DF_RF_RegimenFiscalId IS NULL\n" +
            "              OR TRIM(ISNULL(DF_CorreoElectronico, '')) = ''\n" +
            "              OR (DF_CMM_TipoPersonaId = " + CMM_RFC_TipoPersona.FISICA + " AND (TRIM(ISNULL(DF_Nombre, '')) = '')) -- Persona Física\n" +
            "              OR (DF_CMM_TipoPersonaId IN (" + CMM_RFC_TipoPersona.MORAL + ", " + CMM_RFC_TipoPersona.EXTRANJERO + ") AND TRIM(ISNULL(DF_RazonSocial, '')) = '') -- Persona Moral o Extranjero\n" +
            "       THEN 1 ELSE 0 END, 1) AS completar\n" +
            "FROM DatosFacturacion\n" +
            "     LEFT JOIN SATRegimenesFiscales ON DF_RF_RegimenFiscalId = RF_RegimenFiscalId\n" +
            "     LEFT JOIN Paises ON DF_PAI_PaisId = PAI_PaisId\n" +
            "     LEFT JOIN Estados ON DF_EST_EstadoId = EST_EstadoId\n" +
            "     LEFT JOIN Municipios ON DF_MUN_MunicipioId = MUN_MunicipioId\n" +
            "WHERE DF_RFC = :rfc\n" +
            "ORDER BY nombre,\n" +
            "         cp,\n" +
            "         domicilio\n" +
            "OPTION (RECOMPILE)")
    List<AutofacturaListadoDatosFacturacionProjection> findAutoFacturaDatosFacturacionByRFC(@Param("rfc") String rfc);

    DatosFacturacionEditarProjection findDatosFacturacionEditarProjectedById(Integer id);
}