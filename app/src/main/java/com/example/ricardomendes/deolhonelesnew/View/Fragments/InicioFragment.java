package com.example.ricardomendes.deolhonelesnew.View.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.ricardomendes.deolhonelesnew.Interfaces.GetDeputadoListCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetEnderecoCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.Helper.Constants;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetAllDeputadosDetailsCallback;
import com.example.ricardomendes.deolhonelesnew.ManageData.ManageInfo;
import com.example.ricardomendes.deolhonelesnew.ManageData.Network.HttpHandler;
import com.example.ricardomendes.deolhonelesnew.Utils.Utils;
import com.example.ricardomendes.deolhonelesnew.View.Adapter.InicioAdapter;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;


public class InicioFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GetEnderecoCallback, GetDeputadoListCallback, GetAllDeputadosDetailsCallback{
    private double longitude, latitude;
    private GoogleApiClient mGoogleApiClient;
    private Context context;
    private View view;
    private ProgressBar progressBar;
    private RecyclerView recyclerInicio;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    public ArrayList<Deputado> listDeputado;
    public HashMap<Integer, String> totalDespesas;
    private ArrayList<String> tiposTela;
    private String estado;
    public InicioFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Log.e("Script: Fragment", "onAtach()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Script: Fragment", "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Script: Fragment", "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Script: Fragment", "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("Script: Fragment", "onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Script: Fragment", "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("Script: Fragment", "onDetach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.e("Script Fragment", "onCreate()");
        getActivity().setTitle("Inicio");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inicio, container, false);
        listDeputado = new ArrayList<Deputado>();
        totalDespesas = new HashMap<>();
        recyclerInicio = (RecyclerView) view.findViewById(R.id.recycler_inicio);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_cyclic);
        tiposTela = new ArrayList<>();
        tiposTela.add("Ranking");
        tiposTela.add("ChartTipoDespesa");
        callConnection();
        Log.i("Script: Fragment", "onCreateView()");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("Script: Fragment", "onViewCreated()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Script: Fragment", "onActivityCreated()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("lon", longitude);
        outState.putDouble("lat", latitude);
        Log.i("Script: Fragment", "onSaveInstanceState()");
    }

    // Conecta com o GoogleAPI para pegar as cordenadas de latitude e longitude
    private synchronized void callConnection() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Log.i("LOG", "falhaaaa na permissão: " + bundle);
            return;
        } else{
            Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(l!=null){
                longitude = l.getLongitude();
                latitude = l.getLatitude();
                Double[] loc = new Double[2];
                loc[0] = latitude;
                loc[1] = longitude;
                Log.e("LOG", "onConnected- pegar loc");
                new ManageInfo(context).getEndereco(loc, this);
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    //Infla o menu spinner com todos os estados e faz ação de acordo com a opção selecionada
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        MenuItem spMenuItem = menu.findItem(R.id.spinner);
        spinner = (Spinner) MenuItemCompat.getActionView(spMenuItem);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Constants.ESTADOS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //Recebe o endereço da thread que é passado pela interface
    @Override
    public void onEnderecoCallback(String endereco) {
        Log.i("endereco", endereco);
        if(endereco!=null){
            String[] allEndereco = endereco.split(",");
            String[] cidadeEstado = allEndereco[2].split("-");
            String estado = cidadeEstado[1];
            estado = estado.replace(" ", "");
            if(!endereco.contains("Brazil")){
                this.estado = "BR";
                getDeputadoList("BR");
            }else {
                this.estado = estado;
                getDeputadoList(estado);
            }
        }
    }

    private void getDeputadoList(String estado){
        if(estado.equals("BR")){
            updateSpinnerItem(estado);
            progressBar.setVisibility(View.VISIBLE);
            new ManageInfo(context).getDeputadosList(Constants.URL_DEPUTADOS_ALL, this);
        }else{
            updateSpinnerItem(estado);
            new ManageInfo(context).getDeputadosList(Constants.URL_DEPUTADOS_ESTADO + estado + Constants.ORDENAR_NOME, this);
        }
    }
    @Override
    public void onDeputadoList(ArrayList<Deputado> deputados) {
        new ManageInfo(context).getAllDeputadosDetails(deputados, this);
    }

    @Override
    public void onAllDeputadosDetails(ArrayList<Deputado> listDeputados) {
        this.listDeputado = new Utils().getListDeputadoOrdenado(listDeputados);
        updateUI();
    }

    private void updateSpinnerItem(String estado){
        this.estado = estado;
        spinner.setSelection(adapter.getPosition(estado));
    }

    private void updateUI(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progressBar.setVisibility(View.VISIBLE);
                getDeputadoList(spinner.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        InicioAdapter adapterInicio = new InicioAdapter(estado,listDeputado,tiposTela,context);
        recyclerInicio.setLayoutManager(new LinearLayoutManager(context));
        recyclerInicio.setAdapter(adapterInicio);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
