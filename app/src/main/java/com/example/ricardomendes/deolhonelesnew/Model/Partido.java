package com.example.ricardomendes.deolhonelesnew.Model;

import java.util.ArrayList;

public class Partido {
    private String id, nome, sigla, uri, urlLogo;

    public ArrayList<Deputado> getMembros() {
        return membros;
    }

    public void setMembros(ArrayList<Deputado> membros) {
        this.membros = membros;
    }

    private ArrayList<Deputado> membros;
    private StatusPartido statusPartido;

    public StatusPartido getStatusPartido() {
        return statusPartido;
    }

    public void setStatusPartido(StatusPartido statusPartido) {
        this.statusPartido = statusPartido;
    }
    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
