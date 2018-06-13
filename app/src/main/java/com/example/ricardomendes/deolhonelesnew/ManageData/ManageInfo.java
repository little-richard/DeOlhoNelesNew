package com.example.ricardomendes.deolhonelesnew.ManageData;

import android.content.Context;

import com.example.ricardomendes.deolhonelesnew.Interfaces.GetDeputadoDetailsCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetDeputadoListCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetEnderecoCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetAllDeputadosDetailsCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetPartidoDetailCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetPartidosListCallback;
import com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks.GetDeputadoDetails;
import com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks.GetDeputadoList;
import com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks.GetEndereco;
import com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks.GetPartidoDetail;
import com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks.GetPartidosList;
import com.example.ricardomendes.deolhonelesnew.ManageData.Network.ManageFirebase;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;

import java.util.ArrayList;

public class ManageInfo{
    private Context context;
    public ManageInfo(Context context) {
        this.context = context;
    }


    public void getDeputadoDetails(final String id, final GetDeputadoDetailsCallback callback) {

        GetDeputadoDetails getDetails = new GetDeputadoDetails(id, new GetDeputadoDetailsCallback() {
            @Override
            public void onDeputadoDetails(final Deputado deputado) {
                new ManageFirebase(context).getDespesasDetail(deputado, callback);
            }
        });
        getDetails.execute();
    }
    public void getDeputadosList(final String url, final GetDeputadoListCallback callback){
        GetDeputadoList getDeputadoList = new GetDeputadoList(url, callback);
        getDeputadoList.execute();
    }
    public void getAllDeputadosDetails(final ArrayList<Deputado> listDeputados, final GetAllDeputadosDetailsCallback callback){
        new ManageFirebase(context).getDespesasDetailAll(listDeputados, new GetAllDeputadosDetailsCallback() {
            @Override
            public void onAllDeputadosDetails(ArrayList<Deputado> listDeputados) {
                new ManageFirebase(context).getDespesasDetailAll(listDeputados, callback);
            }
        });
    }
    public void getEndereco(Double[] coordenadas, GetEnderecoCallback getEnderecoCallback){
        GetEndereco getEndereco = new GetEndereco(coordenadas, getEnderecoCallback);
        getEndereco.execute();
    }
    public void getPartidosList(final String url, final GetPartidosListCallback callback){
        GetPartidosList getPartidosList = new GetPartidosList(url, callback);
        getPartidosList.execute();
    }

    public void getPartidoDetail(final String url, final GetPartidoDetailCallback callback){
        GetPartidoDetail getPartidoDetail = new GetPartidoDetail(url, callback);
        getPartidoDetail.execute();
    }
}
