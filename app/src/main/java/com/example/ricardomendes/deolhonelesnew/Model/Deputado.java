package com.example.ricardomendes.deolhonelesnew.Model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Set;

public class Deputado {
    @SerializedName("id")
    private int id;
    @SerializedName("idLegislatura")
    private Legislatura legislatura;
    @SerializedName("siglaPartido")
    private String siglaPartido;
    @SerializedName("nome")
    private String nome;
    @SerializedName("siglaUf")
    private String siglaUf;
    @SerializedName("uri")
    private String uri;
    @SerializedName("uriPartido")
    private String uriPartido;
    @SerializedName("urlFoto")
    private String urlFoto;
    @SerializedName("dataNascimento")
    private String dataNascimento;
    @SerializedName("ufNascimento")
    private String ufNascimento;
    @SerializedName("municipioNascimento")
    private String municipioNascimento;
    @SerializedName("escolaridade")
    private String escolaridade;
    @SerializedName("qtdeProjetos")
    private int qtdeProjetos;
    @SerializedName("totalDespesas")
    private double totalDespesas;
    @SerializedName("totalTipoDespesas")
    private HashMap<String, Double> totalTipoDespesas;
    @SerializedName("ultimoStatus")
    private UltimoStatus ultimoStatus;
    private HashMap<String, Double> totalDespesasAno;

    public HashMap<String, Double> getTotalDespesasAno() {
        return totalDespesasAno;
    }

    public void setTotalDespesasAno(HashMap<String, String> totalDespesasAno) {
        if(!totalDespesasAno.isEmpty()){
            Set<String> chaves = totalDespesasAno.keySet();
            for(String chave : chaves){
                double value = Double.parseDouble(totalDespesasAno.get(chave));
                this.totalDespesasAno.put(chave,value);
            }
        }
    }

    public Deputado(){
        totalTipoDespesas = new HashMap<>();
        totalDespesasAno = new HashMap<>();
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getUfNascimento() {
        return ufNascimento;
    }

    public void setUfNascimento(String ufNascimento) {
        this.ufNascimento = ufNascimento;
    }

    public String getMunicipioNascimento() {
        return municipioNascimento;
    }

    public void setMunicipioNascimento(String municipioNascimento) {
        this.municipioNascimento = municipioNascimento;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public UltimoStatus getUltimoStatus() {
        return ultimoStatus;
    }

    public void setUltimoStatus(UltimoStatus ultimoStatus) {
        this.ultimoStatus = ultimoStatus;
    }

    public HashMap<String, Double> getTotalTipoDespesas() {
        return totalTipoDespesas;
    }

    public void setTotalTipoDespesas(HashMap<String, String> totalTipoDespesas) {
        if(!totalTipoDespesas.isEmpty()) {
            Set<String> chaves = totalTipoDespesas.keySet();
            for(String chave : chaves){
                if(totalTipoDespesas.get(chave)!=null) {
                    double value = Double.parseDouble(totalTipoDespesas.get(chave));
                    this.totalTipoDespesas.put(chave, value);
                }else{
                    double value = 0.0;
                    this.totalTipoDespesas.put(chave, value);
                }
            }
        }
        setTotalDespesas();
    }

    public int getQtdeProjetos() {
        return qtdeProjetos;
    }

    public void setQtdeProjetos(int qtdeProjetos) {
        this.qtdeProjetos = qtdeProjetos;
    }


    public double getTotalDespesas() {
        return totalDespesas;
    }

    private void setTotalDespesas() {
        totalDespesas = 0;
        Set<String> chaves = totalTipoDespesas.keySet();
        for(String chave : chaves){
            totalDespesas += totalTipoDespesas.get(chave);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Legislatura getLegislatura() {
        return legislatura;
    }

    public void setLegislatura(Legislatura legislatura) {
        this.legislatura = legislatura;
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public void setSiglaPartido(String siglaPartido) {
        this.siglaPartido = siglaPartido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getSiglaUf() {
        return siglaUf;
    }

    public void setSiglaUf(String siglaUf) {
        this.siglaUf = siglaUf;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUriPartido() {
        return uriPartido;
    }

    public void setUriPartido(String iriPartido) {
        this.uriPartido = iriPartido;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String uriFoto) {
        this.urlFoto = uriFoto;
    }

}
