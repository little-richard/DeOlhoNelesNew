package com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks;

import android.os.AsyncTask;

import com.example.ricardomendes.deolhonelesnew.Interfaces.GetPartidosListCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.Helper.Constants;
import com.example.ricardomendes.deolhonelesnew.ManageData.Network.HttpHandler;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.Model.Partido;
import com.example.ricardomendes.deolhonelesnew.Model.StatusPartido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetPartidosList extends AsyncTask<Void, Void, ArrayList<Partido>> {
    private GetPartidosListCallback getPartidosListCallback;
    private String url;
    public GetPartidosList(String url, GetPartidosListCallback callback){
        this.url = url;
        this.getPartidosListCallback = callback;
    }
    @Override
    protected ArrayList<Partido> doInBackground(Void... voids) {
        ArrayList<Partido> listPartidos = new ArrayList<>();
        HttpHandler sh = new HttpHandler();
        String jsonStr = sh.makeServiceCall(url);
        if(jsonStr!=null){
            try {
                JSONObject dados = new JSONObject(jsonStr);
                JSONArray partidos = dados.getJSONArray("dados");
                for(int i=0; i<partidos.length(); i++){
                    JSONObject part = partidos.getJSONObject(i);
                    Partido partido = new Partido();
                    partido.setId(part.getString("id"));
                    partido.setNome(part.getString("nome"));
                    partido.setSigla(part.getString("sigla"));
                    partido.setUri(part.getString("uri"));
                    jsonStr = sh.makeServiceCall(partido.getUri());
                    JSONObject partidoDetailAll = new JSONObject(jsonStr);
                    JSONObject partidoDetail = partidoDetailAll.getJSONObject("dados");
                    partido.setUrlLogo(partidoDetail.getString("urlLogo"));
                    JSONObject status = partidoDetail.getJSONObject("status");
                    StatusPartido statusPartido = new StatusPartido();
                    statusPartido.setData(status.getString("data"));
                    statusPartido.setIdlegislatura(status.getString("idLegislatura"));
                    statusPartido.setSituacao(status.getString("situacao"));
                    statusPartido.setTotalMembros(status.getString("totalMembros"));
                    statusPartido.setTotalPosse(status.getString("totalPosse"));
                    statusPartido.setUriMembros(status.getString("uriMembros"));
                    JSONObject liderJson = status.getJSONObject("lider");
                    Deputado lider = new Deputado();
                    lider.setNome(liderJson.getString("nome"));
                    lider.setUri(liderJson.getString("uri"));
                    lider.setSiglaPartido(liderJson.getString("siglaPartido"));
                    lider.setSiglaUf(liderJson.getString("uf"));
                    lider.setUrlFoto(liderJson.getString("urlFoto"));
                    listPartidos.add(partido);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return listPartidos;
    }

    @Override
    protected void onPostExecute(ArrayList<Partido> result){
        super.onPostExecute(result);
        getPartidosListCallback.onPartidosList(result);
    }
}
