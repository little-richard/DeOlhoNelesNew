package com.example.ricardomendes.deolhonelesnew.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardomendes.deolhonelesnew.Interfaces.GetDeputadoListCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetPartidoDetailCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetPartidosListCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.Helper.Constants;
import com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks.DownloadImageGif;
import com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks.GetDeputadoList;
import com.example.ricardomendes.deolhonelesnew.ManageData.ManageInfo;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.Model.Partido;
import com.example.ricardomendes.deolhonelesnew.R;
import com.example.ricardomendes.deolhonelesnew.View.Adapter.PartidoAdapterSingle;

import java.util.ArrayList;

public class PartidoActivity extends AppCompatActivity implements GetPartidoDetailCallback, GetDeputadoListCallback{
    private String uri;
    private Partido partido;
    private RecyclerView recyclerViewPartido;
    private PartidoAdapterSingle adapter;
    private final String[] tipos = new String[]{"Infos", "Lider", "Membros"};
    private ImageView imageLogo;
    private TextView txtNome;
    private TextView txtSigla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido);
        Bundle bundle = getIntent().getExtras();
        uri = bundle.getString("uri");
        accessViews();
        new ManageInfo(this).getPartidoDetail(uri, this);
    }
    private void accessViews(){
        recyclerViewPartido = (RecyclerView) findViewById(R.id.recycler_item_partido);
        imageLogo = (ImageView) findViewById(R.id.img_partido_act);
        txtNome = (TextView) findViewById(R.id.txtNomePartidoAct);
        txtSigla = (TextView) findViewById(R.id.txtSiglaPartidoAct);
    }
    @Override
    public void onPartidoDetail(Partido partido) {
        this.partido = partido;
        new ManageInfo(this).getDeputadosList(Constants.URL_DEPUTADO_PARTIDO + partido.getSigla() + Constants.ORDENAR_NOME, this);
    }
    @Override
    public void onDeputadoList(ArrayList<Deputado> deputados) {
        partido.setMembros(deputados);
        updateUI();
    }


    private void updateUI(){
        new DownloadImageGif(this, imageLogo).execute(partido.getUrlLogo());
        txtNome.setText(partido.getNome());
        txtSigla.setText(partido.getSigla());
        adapter = new PartidoAdapterSingle(this, tipos, partido);
        recyclerViewPartido.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPartido.setAdapter(adapter);
    }

}
