package com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ricardomendes.deolhonelesnew.Interfaces.GetDeputadoListCallback;
import com.example.ricardomendes.deolhonelesnew.ManageData.Network.HttpHandler;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class GetDeputadoList extends AsyncTask<Void, Void, ArrayList<Deputado>>{
    private static final String TAG = GetDeputadoList.class.getSimpleName();
    private GetDeputadoListCallback getDeputadoListCallback;
    private String url;
    public GetDeputadoList(String url, @NonNull GetDeputadoListCallback getDeputadoListCallback){
        this.url = url;
        this.getDeputadoListCallback = getDeputadoListCallback;
    }
    @Override
    protected ArrayList<Deputado> doInBackground(Void... voids) {
        ArrayList<Deputado> listDeputado = new ArrayList<>();
        HttpHandler sh = new HttpHandler();
        String jsonStr = sh.makeServiceCall(url);
        Map<String,String> header = sh.getHeader();
        int total = Integer.parseInt(header.get("x-total-count"));
        String link = header.get("link");
        if(jsonStr!=null){
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray deputados = jsonObj.getJSONArray("dados");
                for(int i=0; i<deputados.length(); i++){
                    JSONObject d = deputados.getJSONObject(i);
                    Deputado dep = new Deputado();
                    dep.setId(d.getInt("id"));
                    dep.setNome(d.getString("nome"));
                    dep.setSiglaPartido(d.getString("siglaPartido"));
                    dep.setSiglaUf(d.getString("siglaUf"));
                    dep.setUri(d.getString("uri"));
                    dep.setUrlFoto(d.getString("urlFoto"));
                    dep.setUriPartido(d.getString("uriPartido"));
                    listDeputado.add(dep);
                }

                Log.e(TAG, "tam" + listDeputado.size());
            }catch (final JSONException e){
                Log.e(TAG, "Error: " + e.getMessage());
            }
        }else{
            Log.e(TAG, "Nao pegou nenhum Json do servidor");
        }

        while(link.contains("next")){
            String[] links = link.split("(\\;(/?[^\\,]+)\\,)");
            String next = links[1].replace("<", "");
            next = next.replace(">", "");
            jsonStr = sh.makeServiceCall(next);
            header = sh.getHeader();
            link = header.get("link");
            if(jsonStr!=null){
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray deputados = jsonObj.getJSONArray("dados");
                    Log.e(TAG, "Response: " + deputados.length());
                    for(int i=0; i<deputados.length(); i++){
                        JSONObject d = deputados.getJSONObject(i);
                        Deputado dep = new Deputado();
                        dep.setId(d.getInt("id"));
                        dep.setNome(d.getString("nome"));
                        dep.setSiglaPartido(d.getString("siglaPartido"));
                        dep.setSiglaUf(d.getString("siglaUf"));
                        dep.setUri(d.getString("uri"));
                        dep.setUrlFoto(d.getString("urlFoto"));
                        dep.setUriPartido(d.getString("uriPartido"));
                        listDeputado.add(dep);
                    }

                }catch (final JSONException e){
                    Log.e(TAG, "Error: " + e.getMessage());
                }
            }else{
                Log.e(TAG, "Nao pegou nenhum Json do servidor");
            }

        }
        return listDeputado;
    }

    @Override
    protected void onPostExecute(ArrayList<Deputado> result){
        super.onPostExecute(result);
        Log.i("tamanhoEnd:", "" + result.size());
        getDeputadoListCallback.onDeputadoList(result);
    }
}
