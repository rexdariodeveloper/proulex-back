package com.pixvs.main.dao;

import com.pixvs.main.models.Articulo;
import com.pixvs.main.models.mapeos.ArticulosTipos;
import com.pixvs.main.models.projections.Articulo.*;
import com.pixvs.main.models.projections.MontosCalculados.MontosCalculadosProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 */
public interface ArticuloDao extends CrudRepository<Articulo, String> {

    // Modelo
    Articulo findById(Integer id);
    Articulo findByIdNotAndCodigoArticulo(Integer id, String codigoArticulo);
    List<Articulo> findAllByProgramaIdiomaIdAndModalidadIdNotIn(Integer idPrograma,List<Integer> ids);
    List<ArticuloComboSimpleProjection> findAllByProgramaIdiomaIdAndModalidadId(Integer idPrograma,Integer idModalidad);
    Articulo findFirstByProgramaIdiomaIdAndModalidadId(Integer programaIdiomaId, Integer modalidadId);
    List<Articulo> findAllByProgramaIdiomaId(Integer id);
    Articulo findByProgramaIdiomaId(Integer id);
    List<ArticuloComboProjection> findAllByCategoriaId(Integer id);
    Articulo findByCodigoArticulo(String codigoArticulo);
    @Query("" +
            "SELECT art \n" +
            "FROM Articulo art \n" +
            "INNER JOIN ProgramaIdiomaLibroMaterial progilm ON progilm.articuloId = art.id \n" +
            "INNER JOIN ProgramaIdioma progi ON progi.id = progilm.programaIdiomaId \n" +
            "LEFT JOIN ProgramaIdiomaLibroMaterialRegla progilmr ON progilmr.programaLibroMateriallId = progilm.id \n" +
            "WHERE \n" +
            "   art.activo = true \n" +
            "   AND progilm.borrado = false \n" +
            "   AND progi.programaId = :programaId \n" +
            "   AND progi.idiomaId = :idiomaId \n" +
            "   AND progilm.nivel = :nivel \n" +
            "   AND ( \n" +
            "       progilmr IS NULL \n" +
            "       OR ( \n" +
            "           :carreraId IS NOT NULL \n" +
            "           AND progilmr.carreraId = :carreraId \n" +
            "       ) \n" +
            "   ) \n" +
            "")
    List<Articulo> findLibrosByCurso(@Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId, @Param("nivel") Integer nivel, @Param("carreraId") Integer carreraId);
    @Query("" +
            "SELECT art \n" +
            "FROM Articulo art \n" +
            "INNER JOIN ProgramaIdiomaCertificacion progic ON progic.certificacionId = art.id \n" +
            "INNER JOIN ProgramaIdioma progi ON progi.id = progic.programaIdiomaId \n" +
            "WHERE \n" +
            "   art.activo = true \n" +
            "   AND progic.borrado = false \n" +
            "   AND progi.programaId = :programaId \n" +
            "   AND progi.idiomaId = :idiomaId \n" +
            "")
    List<Articulo> findCertificacionesByCurso(@Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId);

    List<ArticuloListadoProjection> findProjectedListadoAllByOrderByCodigoArticulo();

    @Query("\n" +
            "SELECT a \n" +
            "FROM Articulo a \n" +
            "WHERE a.tipoArticulo.tipoId <> 2000033 \n" +
            "   AND a.activo = true \n")
    List<ArticuloComboProjection> findProjectedComboAllByActivoTrueAndTipoArticuloNoSistema();

    List<ArticuloComboProjection> findProjectedComboAllByActivoTrueAndTipoArticuloId(Integer tipoArticuloId);
    List<ArticuloComboProjection> findProjectedComboAllByActivoTrueAndId(Integer Id);

    List<ArticuloComboSimpleProjection> findProjectedComboAllByActivoTrueAndFamiliaIdAndTipoArticuloIdNot(Integer familiaId, Integer tipoArticuloId);

    List<ArticuloComboProjection> findProjectedComboAllByActivoTrueAndTipoArticuloIdNot(Integer tipoArticuloId);

    List<ArticuloComboProjection> findProjectedComboAllByActivoTrueAndInventariable(Boolean inventariable);

    List<ArticuloComboProjection> findProjectedComboAllByActivoTrueAndInventariableAndArticuloSubtipoIdNotIn(Boolean inventariable, List<Integer> articuloSubtipoIds);

    List<ArticuloComboProjection> findProjectedComboAllByActivoTrueAndArticuloSubtipoIdIn(List<Integer> subtipoArticuloIds);

    List<ArticuloComboProjection> findProjectedComboAllByActivoTrueAndArticuloSubtipoIdInAndInventariable(List<Integer> subtipoArticuloIds, Boolean inventariable);

    @Query("SELECT a FROM Articulo a WHERE a.activo = true AND a.tipoArticuloId = :tipoArticuloId AND (a.articuloSubtipoId is null OR a.articuloSubtipoId <> :articuloSubtipoId)")
    List<ArticuloComboProjection> findProjectedComboAllByActivoTrueAndTipoArticuloIdAndArticuloSubtipoIdNot(@Param("tipoArticuloId") Integer tipoArticuloId, @Param("articuloSubtipoId") Integer articuloSubtipoId);

    @Query("\n" +
            "SELECT a \n" +
            "FROM Articulo a \n" +
            "WHERE \n" +
            "   a.activo = true \n" +
            "   AND (a.tipoArticuloId is null OR a.tipoArticuloId = :tipoArticuloId) \n" +
            "   AND (a.articuloSubtipoId is null OR a.articuloSubtipoId <> :articuloSubtipoId)")
    List<ArticuloComboProjection> findProjectedComboAllByActivoTrueAndTipoArticuloIdNotAndArticuloSubtipoIdNot(@Param("tipoArticuloId") Integer tipoArticuloId, @Param("articuloSubtipoId") Integer articuloSubtipoId);

    @Query("Select Art from Articulo Art\n" +
            "left join Art.tipoArticulo Artt on Artt.id = Art.tipoArticuloId\n" +
            "left join Art.articuloSubtipo Arts on Arts.id = Art.articuloSubtipoId\n" +
            "left join Artt.tipo Cmm on Artt.tipoId = Cmm.id\n" +
            "where Cmm.id=2000031 and Arts.descripcion is null and Art.activo=true")
    List<ArticuloComboProjection> getArticulosCompradosNoLibros();

    @Query("Select Art from Articulo Art\n" +
            "inner join ArticuloTipo at ON Art.tipoArticuloId = at.id\n" +
            "inner join ControlMaestroMultipleDatosAdicionales cm ON at.tipoId = cm.id\n" +
            "where Art.inventariable=false and cm.id = 2000031 AND Art.activo=true")
    List<ArticuloComboProjection> getArticulosCompradosNoInventariables();

    @Query("Select Art from Articulo Art\n" +
            "inner join ControlMaestroMultipleDatosAdicionales cm ON Art.tipoArticuloId = cm.id AND cm.id = 2000031")
    List<ArticuloComboProjection> getArticulosComprados();

    @Query("\n" +
            "SELECT a \n" +
            "FROM Articulo a \n" +
            "INNER JOIN a.tipoArticulo t \n" +
            "WHERE \n" +
            "   a.activo = true \n" +
            "   AND t.tipoId = :tipoArticuloTipoId \n" +
            "   AND t.id = :tipoArticuloId")
    List<ArticuloComboProjection> findProjectedComboAllByActivoTrueAndTipoArticuloTipoIdAndTipoArticuloId(@Param("tipoArticuloTipoId") Integer tipoArticuloTipoId, @Param("tipoArticuloId") Integer tipoArticuloId);

    @Query("\n" +
            "SELECT a \n" +
            "FROM Articulo a \n" +
            "INNER JOIN a.tipoArticulo t \n" +
            "WHERE \n" +
            "   a.activo = true \n" +
            "   AND t.tipoId = :tipoArticuloTipoId \n" +
            "   AND t.id <> :tipoArticuloId")
    List<ArticuloComboProjection> findProjectedComboAllByActivoTrueAndTipoArticuloTipoIdAndTipoArticuloIdNot(@Param("tipoArticuloTipoId") Integer tipoArticuloTipoId, @Param("tipoArticuloId") Integer tipoArticuloId);

    ArticuloComboProjection findProjectedComboById(Integer id);

    ArticuloComboSimpleProjection findComboProjectedComboById(Integer id);

    ArticuloEditarProjection findProjectedEditarById(Integer id);

    ArticuloPrecargarProjection findProjectedPrecargarById(Integer id);

    List<Articulo> findByProgramaIdiomaIdAndActivoIsTrue(Integer programaIdiomaId);

    @Query("" +
            "SELECT \n" +
            "   a.id AS id, \n" +
            "   d.ordenCompra.fechaOC AS fecha, \n" +
            "   d.ordenCompra.codigo AS codigoOC, \n" +
            "   d.precio AS precio, \n" +
            "   d.ordenCompra.proveedorId AS proveedorId \n" +
            "FROM Articulo a \n" +
            "INNER JOIN a.detallesOC d \n" +
            "WHERE a.id = :id \n" +
            "ORDER BY d.ordenCompra.fechaOC DESC \n" +
            "")
    List<ArticuloUltimasComprasProjection> findProjectedUltimasComprasAllById(@Param("id") Integer id);

    ArticuloArbolComponentesProjection findProjectedArbolComponentesById(Integer id);

    @Query(nativeQuery = true, value = "\n" +
            "SELECT TOP 1 SIG_NUMERO \n" +
            "FROM(\n" +
            "    SELECT  CONCAT(:prefijo, RIGHT('0000000000' + Ltrim(Rtrim(CONVERT( int, SUBSTRING(CODIGO, len(PREFIJO)+1, len(CODIGO) ) ) +1)),:ceros) ) SIG_NUMERO\n" +
            "    FROM(\n" +
            "    select ART_CodigoArticulo as CODIGO, case patindex('%[0-9]%', ART_CodigoArticulo)\n" +
            "        when 0 then ART_CodigoArticulo\n" +
            "        else left(ART_CodigoArticulo, patindex('%[0-9]%', ART_CodigoArticulo) -1 ) end as PREFIJO\n" +
            "        from Articulos\n" +
            "    ) cod\n" +
            "    WHERE PREFIJO = :prefijo\n" +
            "    union all select CONCAT(:prefijo, RIGHT('0000000000' + Ltrim(Rtrim(1 )),:ceros) )\n" +
            ")cod\n" +
            "ORDER BY 1 desc\n" +
            "")
    String findSiguienteCodigo(@Param("prefijo") String prefijo, @Param("ceros") Integer ceros);

    @Query(nativeQuery = true, value = "\n" +
            "Select ART_ArticuloId as id, ART_CodigoArticulo as codigoArticulo, ART_NombreArticulo as nombreArticulo, ART_CMM_IdiomaId as idiomaId \n" +
            "from Articulos\n" +
            "WHERE ART_ARTT_TipoArticuloId = (Select ARTT_ArticuloTipoId from ArticulosTipos WHERE ARTT_Descripcion='Misceláneo') \n" +
            "AND ART_ARTST_ArticuloSubtipoId = (Select ARTST_ArticuloSubtipoId from ArticulosSubtipos WHERE ARTST_Descripcion='Certificación')" +
            "ORDER BY 1 desc\n" +
            "")
    List<ArticuloComboSimpleProjection> findCertificaciones();

    @Query(nativeQuery = true, value = "\n" +
            "Select ART_ArticuloId as id, ART_CodigoArticulo as codigoArticulo, ART_NombreArticulo as nombreArticulo, ART_CMM_IdiomaId as idiomaId \n" +
            "from Articulos\n" +
            "WHERE ART_ARTT_TipoArticuloId = (Select ARTT_ArticuloTipoId from ArticulosTipos WHERE ARTT_Descripcion='Misceláneo') \n" +
            "AND ART_ARTST_ArticuloSubtipoId = (Select ARTST_ArticuloSubtipoId from ArticulosSubtipos WHERE ARTST_Descripcion='Examen')" +
            "ORDER BY 1 desc\n" +
            "")
    List<ArticuloComboSimpleProjection> findExamenes();

    List<ArticuloListadoPrecioProjection> findAllByArticuloParaVentaIsTrueOrderByNombreArticulo();
    List<ArticuloListadoPrecioProjection> findAllByArticuloParaVentaIsTrueAndActivoIsTrueOrderByNombreArticulo();
    @Query("" +
            "SELECT \n" +
            "   art.id AS id, \n" +
            "   art.codigoArticulo AS codigoArticulo, \n" +
            "   art.nombreArticulo AS nombreArticulo, \n" +
            "   art.unidadMedidaInventario AS unidadMedidaInventario, \n" +
            "   art.iva AS iva, \n" +
            "   art.ivaExento AS ivaExento, \n" +
            "   art.ieps AS ieps, \n" +
            "   art.iepsCuotaFija AS iepsCuotaFija, \n" +
            "   art.imagenId AS imagenId, \n" +
            "   art.activo AS activo, \n" +
            "   art.tipoArticuloId AS tipoArticuloId, \n" +
            "   art.programaIdiomaId AS programaIdiomaId, \n" +
            "   art.modalidadId AS modalidadId \n" +
            "FROM Articulo AS art \n" +
            "WHERE \n" +
            "   art.articuloParaVenta = true \n" +
            "   AND art.activo = true \n" +
            "   AND art.tipoArticuloId <> 5 \n" + // Ignorar cursos
            "")
    List<ArticuloListadoPrecioProjection> findProjectedListadoPrecioAllBySinCursos();
    @Query("" +
            "SELECT \n" +
            "   art.id AS id, \n" +
            "   art.codigoArticulo AS codigoArticulo, \n" +
            "   art.nombreArticulo AS nombreArticulo, \n" +
            "   art.unidadMedidaInventario AS unidadMedidaInventario, \n" +
            "   art.iva AS iva, \n" +
            "   art.ivaExento AS ivaExento, \n" +
            "   art.ieps AS ieps, \n" +
            "   art.iepsCuotaFija AS iepsCuotaFija, \n" +
            "   art.imagenId AS imagenId, \n" +
            "   art.activo AS activo, \n" +
            "   art.tipoArticuloId AS tipoArticuloId, \n" +
            "   art.programaIdiomaId AS programaIdiomaId, \n" +
            "   art.modalidadId AS modalidadId \n" +
            "FROM Articulo AS art \n" +
            "WHERE \n" +
            "   art.articuloParaVenta = true \n" +
            "   AND art.activo = true \n" +
            "   AND art.tipoArticuloId = 5 \n" + // Solo cursos
            "   AND ( \n" +
            "       (art.programaIdioma.agruparListadosPreciosPorTipoGrupo = true AND art.tipoGrupoId IS NOT NULL) \n" +
            "       OR (art.programaIdioma.agruparListadosPreciosPorTipoGrupo = false AND art.tipoGrupoId IS NULL) \n" +
            "   ) \n" +
            "")
    List<ArticuloListadoPrecioProjection> findProjectedListadoPrecioAllBySoloCursos();

    @Query("\n" +
            "SELECT \n" +
            "   a.id AS id, \n" +
            "   a.codigoArticulo AS codigoArticulo, \n" +
            "   a.nombreArticulo AS nombreArticulo, \n" +
            "   a.unidadMedidaInventario AS unidadMedidaInventario, \n" +
            "   a.iva AS iva, \n" +
            "   a.ieps AS ieps, \n" +
            "   a.iepsCuotaFija AS iepsCuotaFija, \n" +
            "   lpd.precio AS precio \n" +
            "FROM Articulo a \n" +
            "INNER JOIN ListadoPrecioDetalle lpd ON lpd.articuloId = a.id \n" +
            "WHERE \n" +
            "   a.activo = true \n" +
            "   AND lpd.listadoPrecioId = :listadoPrecioId")
    List<ArticuloComboListaPreciosProjection> findProjectedComboListaPreciosAllByListadoPrecioId(@Param("listadoPrecioId") Integer listadoPrecioId);


    @Modifying
    @Query(value = "UPDATE Articulos SET ART_Activo = :activo WHERE ART_ArticuloId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    @Modifying
    @Query(value = "UPDATE Articulos SET ART_CostoUltimo = :costo WHERE ART_ArticuloId = :id",
            nativeQuery = true)
    int actualizarCostoUltimo(@Param("id") Integer id, @Param("costo") BigDecimal costo);

    @Modifying
    @Query(value = "UPDATE Articulos SET ART_CostoPromedio = :costo WHERE ART_ArticuloId = :id",
            nativeQuery = true)
    int actualizarCostoPromedio(@Param("id") Integer id, @Param("costo") BigDecimal costo);

    //findProjectedComboAllByActivoTrueAndArticuloSubtipoIdInAndInventariable
    @Query("" +
            "SELECT a " +
            "FROM Articulo a " +
            "INNER JOIN LocalidadArticulo la on la.articuloId = a.id "+
            "INNER JOIN Localidad l on la.localidadId = l.id " +
            "WHERE " +
            "l.almacenId = :almacenId and "+
            "a.activo = true and a.inventariable = :inventariable and " +
            "a.articuloSubtipoId in (:subtipos)"
            )
    List<ArticuloComboProjection> getArticulosInventariablesByAlmacenAndSubtipo(@Param("almacenId") Integer almacenId, @Param("subtipos") List<Integer> subtipos, @Param("inventariable") Boolean inventariable);

    @Query("" +
            "SELECT DISTINCT \n" +
            "   a.id AS id, \n" +
            "   a.nombreArticulo AS nombre, \n" +
            "   a.imagenId AS imagenId, \n" +
            "   a.articuloSubtipoId AS articuloSubtipoId, \n" +
            "   a.pedirCantidadPV AS pedirCantidadPV \n" +
            "FROM Articulo a \n" +
            "WHERE a.categoriaId = :categoriaId AND a.activo = true AND a.articuloParaVenta = true AND a.programaIdiomaId IS NULL \n" +
            "")
    List<ArticuloCardProjection> findProjectedCardAllByCategoriaIdAndVentaTrue(@Param("categoriaId") Integer categoriaId);

    @Query("" +
            "SELECT DISTINCT \n" +
            "   a.id AS id, \n" +
            "   a.nombreArticulo AS nombre, \n" +
            "   a.imagenId AS imagenId, \n" +
            "   a.articuloSubtipoId AS articuloSubtipoId, \n" +
            "   a.pedirCantidadPV AS pedirCantidadPV \n" +
            "FROM Articulo a \n" +
            "WHERE a.subcategoriaId = :subcategoriaId AND a.activo = true AND a.articuloParaVenta = true AND a.tipoArticuloId <> " + ArticulosTipos.SISTEMA + " \n" +
            "")
    List<ArticuloCardProjection> findProjectedCardAllBySubcategoriaIdAndVentaTrue(@Param("subcategoriaId") Integer subcategoriaId);

    @Query(nativeQuery = true, value = "" +
            "SELECT \n" +
            "   Subtotal AS subtotal, \n" +
            "   IVA AS iva, \n" +
            "   IEPS AS ieps, \n" +
            "   Descuento AS descuento, \n" +
            "   Total AS total \n" +
            "FROM [dbo].[fn_getImpuestosArticulo]( \n" +
            "   :cantidad, \n" +
            "   :precioUnitario, \n" +
            "   :descuento, \n" +
            "   :iva, \n" +
            "   :ieps, \n" +
            "   :iepsCuotaFija \n" +
            ") \n" +
            "")
    MontosCalculadosProjection getMontosCalculados(@Param("cantidad") BigDecimal cantidad, @Param("precioUnitario") BigDecimal precioUnitario, @Param("descuento") BigDecimal descuento, @Param("iva") BigDecimal iva, @Param("ieps") BigDecimal ieps, @Param("iepsCuotaFija") BigDecimal iepsCuotaFija);

    @Query("" +
            "SELECT art.id \n" +
            "FROM Articulo art \n" +
            "WHERE \n" +
            "   art.programaIdioma.programaId = :programaId \n" +
            "   AND art.programaIdioma.idiomaId = :idiomaId \n" +
            "   AND art.modalidadId = :paModalidadId \n" +
            "   AND art.tipoGrupoId IS NULL \n" +
            "   AND art.activo = true \n" +
            "")
    Integer findIdByProgramaIdAndIdiomaIdAndPaModalidadId(@Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId, @Param("paModalidadId") Integer paModalidadId);
    @Query("" +
            "SELECT art.id \n" +
            "FROM Articulo art \n" +
            "WHERE \n" +
            "   art.programaIdioma.programaId = :programaId \n" +
            "   AND art.programaIdioma.idiomaId = :idiomaId \n" +
            "   AND art.modalidadId = :paModalidadId \n" +
            "   AND art.tipoGrupoId = :tipoGrupoId \n" +
            "   AND art.activo = true \n" +
            "")
    Integer findIdByProgramaIdAndIdiomaIdAndPaModalidadIdAndTipoGrupoId(@Param("programaId") Integer programaId, @Param("idiomaId") Integer idiomaId, @Param("paModalidadId") Integer paModalidadId, @Param("tipoGrupoId") Integer tipoGrupoId);

    @Query("" +
            "SELECT art \n" +
            "FROM Articulo art \n" +
            "WHERE \n" +
            "   art.programaIdiomaId = :programaIdiomaId \n" +
            "   AND art.modalidadId = :paModalidadId \n" +
            "   AND ( \n" +
            "       (art.programaIdioma.agruparListadosPreciosPorTipoGrupo = true AND art.tipoGrupoId = :tipoGrupoId) \n" +
            "       OR (art.programaIdioma.agruparListadosPreciosPorTipoGrupo = false AND art.tipoGrupoId IS NULL) \n" +
            "   ) \n" +
            "")
    ArticuloEditarProjection findProjectedByProgramaIdiomaIdAndPaModalidadIdAndTipoGrupoId(@Param("programaIdiomaId") Integer programaIdiomaId, @Param("paModalidadId") Integer paModalidadId, @Param("tipoGrupoId") Integer tipoGrupoId);

    @Query(nativeQuery = true, value = "SELECT [dbo].[getPrecioVenta](:articuloId,:articuloRaizId,:listaPreciosId)")
    BigDecimal findPrecioVenta(@Param("articuloId") Integer articuloId, @Param("articuloRaizId") Integer articuloRaizId, @Param("listaPreciosId") Integer listaPreciosId);
    @Query("" +
            "SELECT lipred.precio " +
            "FROM ListadoPrecioDetalle lipred " +
            "WHERE " +
            "   lipred.articuloId = :articuloId " +
            "   AND lipred.padreId IS NULL " +
            "   AND lipred.listadoPrecioId = :listaPreciosId " +
            "")
    BigDecimal findPrecioVenta(@Param("articuloId") Integer articuloId, @Param("listaPreciosId") Integer listaPreciosId);


    @Query("" +
            "SELECT a " +
            "FROM Articulo a " +
            "INNER JOIN LocalidadArticulo la on la.articuloId = a.id "+
            "INNER JOIN Localidad l on la.localidadId = l.id " +
            "INNER JOIN Almacen al on al.id = l.almacenId " +
            "WHERE " +
            "   l.localidadGeneral = true and "+
            "   al.esCedi = true and " +
            "   a.activo = true and " +
            "   a.inventariable = :inventariable and " +
            "   a.articuloSubtipoId in (:subtipos)"
    )
    List<ArticuloComboProjection> getArticulosInventariablesLocalidadGeneralCedisByInventariableAndSubtipo(@Param("inventariable") Boolean inventariable, @Param("subtipos") List<Integer> subtipos);

    @Query("" +
            "SELECT DISTINCT \n" +
            "   art.id AS id, \n" +
            "   art.codigoArticulo AS codigoArticulo, \n" +
            "   art.nombreArticulo AS nombreArticulo, \n" +
            "   art.unidadMedidaInventario AS unidadMedidaInventario, \n" +
            "   art.iva AS iva, \n" +
            "   art.ivaExento AS ivaExento, \n" +
            "   art.ieps AS ieps, \n" +
            "   art.iepsCuotaFija AS iepsCuotaFija, \n" +
            "   COALESCE(lipredc.precio,0) AS precio, \n" +
            "   true AS esLibro, \n" +
            "   false AS esCertificacion \n" +
            "FROM Articulo art \n" +
            "INNER JOIN ProgramaIdiomaLibroMaterial progilm ON progilm.articuloId = art.id \n" +
            "INNER JOIN Articulo artPadre ON artPadre.id = :articuloId \n" +
            "LEFT JOIN ListadoPrecioDetalle lipred ON lipred.articuloId = artPadre.id AND lipred.listadoPrecioId = :listadoPreciosId \n" +
            "LEFT JOIN ListadoPrecioDetalleCurso lipredc ON lipredc.listadoPrecioDetalleId = lipred.id AND lipredc.articuloId = art.id \n" +
            "WHERE \n" +
            "   art.activo = true \n" +
            "   AND progilm.borrado = false \n" +
            "   AND progilm.programaIdiomaId = artPadre.programaIdiomaId \n" +
            "ORDER BY art.nombreArticulo \n" +
            "")
    List<ArticuloListadoPrecioMaterialProjection> findLibrosMaterialesCurso(@Param("articuloId") Integer articuloId, @Param("listadoPreciosId") Integer listadoPreciosId);
    @Query("" +
            "SELECT \n" +
            "   art.id AS id, \n" +
            "   art.codigoArticulo AS codigoArticulo, \n" +
            "   art.nombreArticulo AS nombreArticulo, \n" +
            "   art.unidadMedidaInventario AS unidadMedidaInventario, \n" +
            "   art.iva AS iva, \n" +
            "   art.ivaExento AS ivaExento, \n" +
            "   art.ieps AS ieps, \n" +
            "   art.iepsCuotaFija AS iepsCuotaFija, \n" +
            "   COALESCE(lipredc.precio,0) AS precio, \n" +
            "   false AS esLibro, \n" +
            "   true AS esCertificacion \n" +
            "FROM Articulo art \n" +
            "INNER JOIN ProgramaIdiomaCertificacion progic ON progic.certificacionId = art.id \n" +
            "INNER JOIN Articulo artPadre ON artPadre.id = :articuloId \n" +
            "LEFT JOIN ListadoPrecioDetalle lipred ON lipred.articuloId = artPadre.id AND lipred.listadoPrecioId = :listadoPreciosId \n" +
            "LEFT JOIN ListadoPrecioDetalleCurso lipredc ON lipredc.listadoPrecioDetalleId = lipred.id AND lipredc.articuloId = art.id \n" +
            "WHERE \n" +
            "   art.activo = true \n" +
            "   AND progic.borrado = false \n" +
            "   AND progic.programaIdiomaId = artPadre.programaIdiomaId \n" +
            "ORDER BY art.nombreArticulo \n" +
            "")
    List<ArticuloListadoPrecioMaterialProjection> findCertificacionesMaterialesCurso(@Param("articuloId") Integer articuloId, @Param("listadoPreciosId") Integer listadoPreciosId);

    @Query(nativeQuery = true, value = "SELECT [dbo].[getArticuloDescuento] (:articuloId,:sucursalId)")
    Integer getArticuloDescuento(@Param("articuloId") Integer articuloId, @Param("sucursalId") Integer sucursalId);

    @Query("" +
            "SELECT art.id \n" +
            "FROM Articulo art \n" +
            "WHERE \n" +
            "   art.articuloParaVenta = true \n" +
            "   AND art.activo = true \n" +
            "   AND art.tipoArticuloId = 5 \n" + // Solo artículos de curso
            "   AND art.tipoGrupoId IS NOT NULL \n" +
            "")
    List<Integer> getIdAllByCursoConTipoGrupo();
    @Query("" +
            "SELECT art.id \n" +
            "FROM Articulo art \n" +
            "WHERE \n" +
            "   art.articuloParaVenta = true \n" +
            "   AND art.activo = true \n" +
            "   AND art.tipoArticuloId = 5 \n" + // Solo artículos de curso
            "   AND art.tipoGrupoId IS NULL \n" +
            "")
    List<Integer> getIdAllByCursoSinTipoGrupo();

}