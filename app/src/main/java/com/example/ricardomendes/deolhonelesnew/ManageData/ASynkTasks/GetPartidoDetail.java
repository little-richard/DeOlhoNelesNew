package com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks;

import android.os.AsyncTask;

import com.example.ricardomendes.deolhonelesnew.Interfaces.GetPartidoDetailCallback;
import com.example.ricardomendes.deolhonelesnew.ManageData.Network.HttpHandler;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.Model.Partido;
import com.example.ricardomendes.deolhonelesnew.Model.StatusPartido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetPartidoDetail extends AsyncTask<Void, Void, Partido> {
    private GetPartidoDetailCallback callback;
    private String url;
    public GetPartidoDetail(String url, GetPartidoDetailCallback getPartidoDetail) {
        callback = getPartidoDetail;
        this.url = url;
    }

    @Override
    protected Partido doInBackground(Void... voids) {
        Partido partido = new Partido();
        HttpHandler sh = new HttpHandler();
        String jsonStr = sh.makeServiceCall(url);
        if(jsonStr!=null){
            try {
                JSONObject dados = new JSONObject(jsonStr);
                JSONObject partidoJson = dados.getJSONObject("dados");
                    partido.setId(partidoJson.getString("id"));
                    partido.setNome(partidoJson.getString("nome"));
                    partido.setSigla(partidoJson.getString("sigla"));
                    partido.setUri(partidoJson.getString("uri"));
                    partido.setUrlLogo(partidoJson.getString("urlLogo"));
                    JSONObject status = partidoJson.getJSONObject("status");
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
                    statusPartido.setLider(lider);
                    partido.setStatusPartido(statusPartido);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return partido;
    }

    @Override
    protected void onPostExecute(Partido p){
        super.onPostExecute(p);
        callback.onPartidoDetail(p);
    }
}
