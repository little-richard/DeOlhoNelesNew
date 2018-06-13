package com.example.ricardomendes.deolhonelesnew.View.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ricardomendes.deolhonelesnew.Interfaces.GetPartidosListCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.Helper.Constants;
import com.example.ricardomendes.deolhonelesnew.ManageData.ManageInfo;
import com.example.ricardomendes.deolhonelesnew.Model.Partido;
import com.example.ricardomendes.deolhonelesnew.R;
import com.example.ricardomendes.deolhonelesnew.Utils.Utils;
import com.example.ricardomendes.deolhonelesnew.View.Activity.DeputadoActivity;
import com.example.ricardomendes.deolhonelesnew.View.Activity.PartidoActivity;
import com.example.ricardomendes.deolhonelesnew.View.Adapter.PartidosAdapter;
import com.example.ricardomendes.deolhonelesnew.View.ClickListener.RankingClickListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartidoFragment extends Fragment implements GetPartidosListCallback{
    private RecyclerView recyclerViewPartidos;
    private ArrayList<Partido> listPartidos;
    private Context context;
    private ProgressBar progressBar;
    private PartidosAdapter adapter;
    public PartidoFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.e("Script Fragment", "onCreate()");
        getActivity().setTitle("Partidos");

    }
    private void accessViews(View view){
        recyclerViewPartidos = (RecyclerView) view.findViewById(R.id.recycler_partidos);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_cyclic_part);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partidos, container, false);
        accessViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.animate();
        new ManageInfo(context).getPartidosList(Constants.URL_PARTIDOS_ALL, this);
    }

    @Override
    public void onPartidosList(ArrayList<Partido> partidos) {
        listPartidos = partidos;
        progressBar.setVisibility(View.INVISIBLE);
        updateUI();
    }

    public void updateUI(){
        recyclerViewPartidos.setLayoutManager(new GridLayoutManager(context, 2));
        adapter = new PartidosAdapter(context, listPartidos);
        recyclerViewPartidos.setAdapter(adapter);
        recyclerViewPartidos.addOnItemTouchListener(
                new RankingClickListener(context, new RankingClickListener.onRankingClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Bundle params = new Bundle();
                        params.putString("uri", "" + listPartidos.get(position).getUri());
                        Intent intent = new Intent(context, PartidoActivity.class);
                        intent.putExtras(params);
                        context.startActivity(intent);
                    }
                }));
    }
}
