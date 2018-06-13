package com.example.ricardomendes.deolhonelesnew.Model;

public class StatusPartido {
    String data;
    String idlegislatura;
    String situacao;
    String totalMembros;
    String totalPosse;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIdlegislatura() {
        return idlegislatura;
    }

    public void setIdlegislatura(String idlegislatura) {
        this.idlegislatura = idlegislatura;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getTotalMembros() {
        return totalMembros;
    }

    public void setTotalMembros(String totalMembros) {
        this.totalMembros = totalMembros;
    }

    public String getTotalPosse() {
        return totalPosse;
    }

    public void setTotalPosse(String totalPosse) {
        this.totalPosse = totalPosse;
    }

    public String getUriMembros() {
        return uriMembros;
    }

    public void setUriMembros(String uriMembros) {
        this.uriMembros = uriMembros;
    }

    public Deputado getLider() {
        return lider;
    }

    public void setLider(Deputado lider) {
        this.lider = lider;
    }

    String uriMembros;
    Deputado lider;
}
