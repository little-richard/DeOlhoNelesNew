package com.example.ricardomendes.deolhonelesnew.Model;

import com.google.gson.annotations.SerializedName;

public class UltimoStatus {
    @SerializedName("condicaoEleitoral")
    private String condicaoEleitoral;
    @SerializedName("situacao")
    private String situacao;
    @SerializedName("gabinete")
    private Gabinete gabinete;

    public String getCondicaoEleitoral() {
        return condicaoEleitoral;
    }

    public void setCondicaoEleitoral(String condicaoEleitoral) {
        this.condicaoEleitoral = condicaoEleitoral;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Gabinete getGabinete() {
        return gabinete;
    }

    public void setGabinete(Gabinete gabinete) {
        this.gabinete = gabinete;
    }

}
