package com.pixvs.main.models;

import java.util.List;

public class JsonRespuestaDOF {

    private Integer messageCode;
    private String response;
    private List<IndicadorDOF> ListaIndicadores;
    private Integer TotalIndicadores;

    public Integer getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(Integer messageCode) {
        this.messageCode = messageCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<IndicadorDOF> getListaIndicadores() {
        return ListaIndicadores;
    }

    public void setListaIndicadores(List<IndicadorDOF> listaIndicadores) {
        ListaIndicadores = listaIndicadores;
    }

    public Integer getTotalIndicadores() {
        return TotalIndicadores;
    }

    public void setTotalIndicadores(Integer totalIndicadores) {
        TotalIndicadores = totalIndicadores;
    }
}
