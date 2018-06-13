package com.example.ricardomendes.deolhonelesnew.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ricardomendes.deolhonelesnew.View.Adapter.DeputadoAdapter;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetDeputadoDetailsCallback;
import com.example.ricardomendes.deolhonelesnew.ManageData.ManageInfo;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks.DownloadImage;
import com.example.ricardomendes.deolhonelesnew.R;

import java.util.ArrayList;

public class DeputadoActivity extends AppCompatActivity implements GetDeputadoDetailsCallback{
    private RecyclerView recyclerView;
    private TextView nomeDep;
    private TextView descDep;
    private ImageView fotoDep;
    ArrayList<String> types;
    String id;
    Deputado dep;
    ManageInfo manageDepDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deputado);
        Bundle params = getIntent().getExtras();
        id = params.getString("id");
        types = new ArrayList<>();
        types.add("Infos");
        types.add("Por Ano");
        types.add("Por Categoria");
        types.add("Contato");
        accessViews();
        manageDepDetail = new ManageInfo(this);
        manageDepDetail.getDeputadoDetails(id, this);
        Log.e("info-> ", "onCreate()");
    }
    private void accessViews(){
        nomeDep = (TextView) findViewById(R.id.txt_nome_parlamentar);
        descDep = (TextView) findViewById(R.id.txtDescParlamentar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerDeputado);
        fotoDep = (ImageView) findViewById(R.id.img_dep);
    }
    @Override
    public void onDeputadoDetails(Deputado deputado) {
        dep = deputado;
        updateUI();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void updateUI(){
        nomeDep.setText(dep.getNome());
        descDep.setText(dep.getSiglaPartido() + " - " + dep.getSiglaUf());
        new DownloadImage(fotoDep).execute(dep.getUrlFoto());
        DeputadoAdapter adapter = new DeputadoAdapter(this, types, dep);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}

