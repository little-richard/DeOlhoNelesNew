package com.example.ricardomendes.deolhonelesnew.Model;

import com.google.gson.annotations.SerializedName;

public class Gabinete {
    @SerializedName("andar")
    private String andar;
    @SerializedName("email")
    private String email;
    @SerializedName("nome")
    private String nome;
    @SerializedName("predio")
    private String predio;
    @SerializedName("sala")
    private String sala;
    @SerializedName("telefone")
    private String telefone;

    public String getAndar() {
        return andar;
    }

    public void setAndar(String andar) {
        this.andar = andar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
