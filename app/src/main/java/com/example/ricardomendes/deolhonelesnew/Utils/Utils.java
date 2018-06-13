package com.example.ricardomendes.deolhonelesnew.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;

import com.example.ricardomendes.deolhonelesnew.Interfaces.Helper.Constants;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Utils {
    private ArrayList<Deputado> listDeputado;
    public Utils(){
    }
    private void ordenar(){
        int tam = listDeputado.size();
        for (int indice = 0; indice < tam; ++indice) {
            int indiceMenor = indice;
            for (int indiceSeguinte = indice+1; indiceSeguinte < tam; ++indiceSeguinte) {
                if (listDeputado.get(indiceSeguinte).getTotalDespesas() < listDeputado.get(indiceMenor).getTotalDespesas()) {
                    indiceMenor = indiceSeguinte;
                }
            }
            Deputado aux = listDeputado.get(indice);
            listDeputado.set(indice, listDeputado.get(indiceMenor));
            listDeputado.set(indiceMenor, aux);
        }
    }
    public ArrayList<Deputado> getListDeputadoOrdenado(ArrayList<Deputado> listDeputado){
        this.listDeputado = listDeputado;
        ordenar();
        return this.listDeputado;
    }
    public HashMap<String, Double> getTotalPorEstado(ArrayList<Deputado> listDeputado){
        double valor;
        HashMap<String, Double> totalPorEstado = new HashMap<String, Double>(){{
            put("Consultoria e pesquisas", 0.0);
            put("Locomoção, hospedagem e alimentação", 0.0);
            put("Divulgação do mandato", 0.0);
            put("Passagens", 0.0);
            put("Segurança privada", 0.0);
            put("Correios e material para escritório político", 0.0);
        }};
        for(int i=0; i<listDeputado.size(); i++){
            Set<String> chaves = totalPorEstado.keySet();
            for(String chave : chaves){
                valor = totalPorEstado.get(chave);
                if(listDeputado.get(i).getTotalTipoDespesas().get(chave) != null) {
                    valor += listDeputado.get(i).getTotalTipoDespesas().get(chave);
                    totalPorEstado.put(chave, valor);
                }else{
                    valor += 0;
                    totalPorEstado.put(chave, valor);
                }
            }
        }
        Set<String> chaves = totalPorEstado.keySet();
        double total = 0;
        for(String chave : chaves){
            total += totalPorEstado.get(chave);
        }
        totalPorEstado.put("Total", total);
        return totalPorEstado;
    }

    public String formatMoney(double value){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String format = nf.format(value);
        return format;
    }

    public ArrayList<Deputado> filterDeputados(String q, ArrayList<Deputado> listDeputados){
        ArrayList<Deputado> listDeputadosAux = new ArrayList<>();
        int tam = listDeputados.size();
        for(int i=0, tamI=tam; i<tamI; i++){
            if(listDeputados.get(i).getNome().toLowerCase().startsWith(q.toLowerCase())){
                listDeputadosAux.add(listDeputados.get(i));
            }
        }
        return listDeputadosAux;
    }

}
