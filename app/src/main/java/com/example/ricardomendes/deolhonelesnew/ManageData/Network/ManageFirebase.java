package com.example.ricardomendes.deolhonelesnew.ManageData.Network;

import android.content.Context;

import com.example.ricardomendes.deolhonelesnew.Interfaces.GetDeputadoDetailsCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetAllDeputadosDetailsCallback;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageFirebase {
    private DatabaseReference databaseReference;
    private HashMap<String, String> tipoDespesas;
    private HashMap<String, String> porAnoDespesas;
    public ManageFirebase(Context context) {
        tipoDespesas = new HashMap<>();
        porAnoDespesas = new HashMap<>();
        initFirebase(context);
    }

    private void initFirebase(Context context) {
        FirebaseApp.initializeApp(context);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void getDespesasDetail(final Deputado deputado, final GetDeputadoDetailsCallback callback) {
        tipoDespesas.clear();
        porAnoDespesas.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventoDataBase(dataSnapshot, deputado.getId());
                deputado.setTotalTipoDespesas(tipoDespesas);
                deputado.setTotalDespesasAno(porAnoDespesas);
                callback.onDeputadoDetails(deputado);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onDeputadoDetails(null);
            }
        });
    }
    private void eventoDataBase(DataSnapshot dataSnapshot, int id) {
        final String refTotalAno = "total_despesas_por_ano";
        final String refTotalTipo = "total_despesas_tipo";
        Object mapTipo = dataSnapshot.child(refTotalTipo).child("" + id).getValue();
        Object mapAno = dataSnapshot.child(refTotalAno).child("" + id).getValue();
        if (mapTipo != null && mapAno != null) {
            for (Object object : ((HashMap) mapTipo).entrySet()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) object;
                tipoDespesas.put(entry.getKey(), entry.getValue());
            }

            for (Object object : ((HashMap) mapAno).entrySet()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) object;
                porAnoDespesas.put(entry.getKey(), entry.getValue());
            }
        }
    }
    public void getDespesasDetailAll(final ArrayList<Deputado> listDeputados, final GetAllDeputadosDetailsCallback getAllDeputadosDetailsCallback){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int tam = listDeputados.size();
                for(int i=0; i<tam; i++){
                    tipoDespesas.clear();
                    porAnoDespesas.clear();
                    eventoDataBase(dataSnapshot, listDeputados.get(i).getId());
                    listDeputados.get(i).setTotalTipoDespesas(tipoDespesas);
                    listDeputados.get(i).setTotalDespesasAno(porAnoDespesas);
                }
                getAllDeputadosDetailsCallback.onAllDeputadosDetails(listDeputados);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getAllDeputadosDetailsCallback.onAllDeputadosDetails(null);
            }
        });
    }
}
