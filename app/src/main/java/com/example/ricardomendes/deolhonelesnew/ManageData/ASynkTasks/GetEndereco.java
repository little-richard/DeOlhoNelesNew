package com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.ricardomendes.deolhonelesnew.Interfaces.GetEnderecoCallback;
import com.example.ricardomendes.deolhonelesnew.ManageData.Network.HttpHandler;
import com.example.ricardomendes.deolhonelesnew.View.Fragments.InicioFragment;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetEndereco extends AsyncTask<Void, Void, Void> {
    private  GetEnderecoCallback getEnderecoCallback;
    private Double[] coordenadas;
    private String endereco;
    public GetEndereco(Double[] coordenadas, @NonNull GetEnderecoCallback getEnderecoCallback){
        this.getEnderecoCallback = getEnderecoCallback;
        this.coordenadas = coordenadas;
        this.endereco = "";
    }
    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler sh = new HttpHandler();
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + coordenadas[0] +"," + coordenadas[1] +"&key=AIzaSyDXhXNPJkYeveTl-cMZ8OB4-8r6xRNWhe0";
        String jsonStr = sh.makeServiceCall(url);
        if(jsonStr != null){
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonObj.getJSONArray("results");
                JSONObject result = jsonArray.getJSONObject(0);
                endereco = result.getString("formatted_address");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        getEnderecoCallback.onEnderecoCallback(endereco);
    }
}