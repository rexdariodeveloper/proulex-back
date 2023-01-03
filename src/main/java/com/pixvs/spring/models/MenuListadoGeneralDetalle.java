package com.pixvs.spring.models;

import javax.persistence.*;

/**
 * Created by Angel Daniel Hernández Silva on 22/06/2020.
 */
@Entity
@Table(name = "MenuListadosGeneralesDetalles")
public class MenuListadoGeneralDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MLGD_ListadoGeneralDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "MLGD_MLG_ListadoGeneralNodoId", nullable = false, insertable = false, updatable = false)
    private Integer listadoGeneralMenuId;

    @Column(name = "MLGD_JsonConfig", nullable = false, insertable = false, updatable = false)
    private String jsonConfig;

    @Column(name = "MLGD_JsonListado", nullable = false, insertable = false, updatable = false)
    private String jsonListado;

    @Column(name = "MLGD_CampoTabla", length = 255, nullable = false, insertable = false, updatable = false)
    private String campoTabla;

    @Column(name = "MLGD_CampoModelo", length = 255, nullable = false, insertable = false, updatable = false)
    private String campoModelo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getListadoGeneralMenuId() {
        return listadoGeneralMenuId;
    }

    public void setListadoGeneralMenuId(Integer listadoGeneralMenuId) {
        this.listadoGeneralMenuId = listadoGeneralMenuId;
    }

    public String getJsonConfig() {
        return jsonConfig;
    }

    public void setJsonConfig(String jsonConfig) {
        this.jsonConfig = jsonConfig;
    }

    public String getJsonListado() {
        return jsonListado;
    }

    public void setJsonListado(String jsonListado) {
        this.jsonListado = jsonListado;
    }

    public String getCampoTabla() {
        return campoTabla;
    }

    public void setCampoTabla(String campoTabla) {
        this.campoTabla = campoTabla;
    }

    public String getCampoModelo() {
        return campoModelo;
    }

    public void setCampoModelo(String campoModelo) {
        this.campoModelo = campoModelo;
    }

}
