package com.example.ricardomendes.deolhonelesnew.View.Fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ricardomendes.deolhonelesnew.Interfaces.GetAllDeputadosDetailsCallback;
import com.example.ricardomendes.deolhonelesnew.Interfaces.Helper.Constants;
import com.example.ricardomendes.deolhonelesnew.Utils.Utils;
import com.example.ricardomendes.deolhonelesnew.View.Adapter.ParlamentaresAdapter;
import com.example.ricardomendes.deolhonelesnew.Interfaces.GetDeputadoListCallback;
import com.example.ricardomendes.deolhonelesnew.ManageData.ManageInfo;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.View.Activity.DeputadoActivity;
import com.example.ricardomendes.deolhonelesnew.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */

public class ParlamentaresFragment extends Fragment implements GetDeputadoListCallback, GetAllDeputadosDetailsCallback{
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<Deputado> listDeputadosAll;
    ArrayList<Deputado> listAux;
    private ManageInfo manageInfo;
    private Context context;
    private View ChildView;
    public ParlamentaresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Parlamentares");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parlamentares, container, false);
        listAux = new ArrayList<>();
        accessViews(view);
        return view;
    }
    public void accessViews(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_deputados);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_cyclic_dep);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar.setVisibility(View.VISIBLE);
        manageInfo = new ManageInfo(context);
        manageInfo.getDeputadosList(Constants.URL_DEPUTADOS_ALL + Constants.ORDENAR_NOME, this);
    }

    @Override
    public void onDeputadoList(ArrayList<Deputado> deputados) {
        listDeputadosAll = deputados;
        new ManageInfo(context).getAllDeputadosDetails(listDeputadosAll, this);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.search, menu);
        SearchView searchView;
        MenuItem svMenuItem = menu.findItem(R.id.search_view);
        searchView = (SearchView) MenuItemCompat.getActionView(svMenuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listAux = new Utils().filterDeputados(query, listDeputadosAll);
                updateAdapter(listAux);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                listAux = new Utils().filterDeputados(newText, listDeputadosAll);
                updateAdapter(listAux);
                return true;
            }
        });
        return;
    }
    private void updateAdapter( ArrayList<Deputado> list){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        ParlamentaresAdapter adapter = new ParlamentaresAdapter(list);
        recyclerView.setAdapter(adapter);
    }
    private void onClickRecycler(){
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent){
                    return true;
                }
            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                ChildView = rv.findChildViewUnder(e.getX(), e.getY());
                if(ChildView != null && gestureDetector.onTouchEvent(e)){
                    int recyclerViewPosition = rv.getChildAdapterPosition(ChildView);
                    Bundle params = new Bundle();
                    params.putString("id", "" + listAux.get(recyclerViewPosition).getId());
                    Intent intent = new Intent(getContext(), DeputadoActivity.class);
                    intent.putExtras(params);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    @Override
    public void onAllDeputadosDetails(ArrayList<Deputado> listDeputados) {
        this.listDeputadosAll = listDeputados;
        listAux = listDeputados;
        progressBar.setVisibility(View.INVISIBLE);
        updateAdapter(listAux);
        onClickRecycler();
    }
}

