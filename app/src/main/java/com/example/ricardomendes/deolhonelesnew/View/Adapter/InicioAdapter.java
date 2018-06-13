package com.example.ricardomendes.deolhonelesnew.View.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.FormatChart.PercentFormatter;
import com.example.ricardomendes.deolhonelesnew.R;
import com.example.ricardomendes.deolhonelesnew.Utils.Utils;
import com.example.ricardomendes.deolhonelesnew.View.Activity.DeputadoActivity;
import com.example.ricardomendes.deolhonelesnew.View.ClickListener.RankingClickListener;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class InicioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<String> tipos;
    ArrayList<Deputado> listDeputados;
    private HashMap<String, String> tipoDespesas;
    Context context;
    private String uf;
    public InicioAdapter(String uf, ArrayList<Deputado> listDeputados, ArrayList<String> tipos, Context context) {
        this.tipos = tipos;
        this.listDeputados = listDeputados;
        this.context = context;
        tipoDespesas = new HashMap<>();
        this.uf = uf;
    }
    @SuppressLint("ClickableViewAccessibility")
    private void initRanking(final ViewHolderRanking holder, final int pos){
        final int tam = listDeputados.size();
        final ArrayList<Deputado> ranking = new ArrayList<>();
        for(int i=1; i<=5; i++){
            ranking.add(listDeputados.get(tam-i));
        }
        final RankingAdapter adapter = new RankingAdapter(context, ranking);
        holder.listViewRanking.setLayoutManager(new LinearLayoutManager(context));
        holder.listViewRanking.setAdapter(adapter);
        holder.listViewRanking.addOnItemTouchListener(
                new RankingClickListener(context, new RankingClickListener.onRankingClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Bundle params = new Bundle();
                        params.putString("id", "" + ranking.get(position).getId());
                        Intent intent = new Intent(context, DeputadoActivity.class);
                        intent.putExtras(params);
                        context.startActivity(intent);
                    }
                }));
        holder.buttonShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tamRanking = ranking.size();
                int dif = tam - tamRanking;
                if (dif>5) {
                    for (int i = tamRanking; i <tamRanking + 5; i++) {
                        ranking.add(listDeputados.get((tam - i)-1));
                    }
                    final RankingAdapter adapter = new RankingAdapter(context, ranking);
                    holder.listViewRanking.setAdapter(adapter);
                }else if(tamRanking == tam){
                    final Toast toast = Toast.makeText(context, "Todos os deputados já foram listados!", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    for (int i = tamRanking; i <tamRanking + dif; i++) {
                        ranking.add(listDeputados.get((tam - i) - 1));
                    }
                    final RankingAdapter adapter = new RankingAdapter(context, ranking);
                    holder.listViewRanking.setAdapter(adapter);
                }
            }
        });
        holder.rankingTitle.setText("Ranking de gastos: " + uf);
    }
    private void initTipoDespesas(ViewHolderTipoDespesas holder, int pos){
        HashMap<String, Double> totalPorEstado = new Utils().getTotalPorEstado(listDeputados);
        Log.i("HASH:", totalPorEstado.toString());
        holder.txtTitleTipo.setText("Total por categoria: " + uf);
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        holder.txtValorConsultoria.setText(nf.format(totalPorEstado.get("Consultoria e pesquisas")));
        holder.txtValorLocomocao.setText(nf.format(totalPorEstado.get("Locomoção, hospedagem e alimentação")));
        holder.txtValorDivulgacao.setText(nf.format(totalPorEstado.get("Divulgação do mandato")));
        holder.txtValorPassagens.setText(nf.format(totalPorEstado.get("Passagens")));
        holder.txtValorSegurancaPrivada.setText(nf.format(totalPorEstado.get("Segurança privada")));
        holder.txtValorEscritorio.setText(nf.format(totalPorEstado.get("Correios e material para escritório político")));
        holder.txtValorTotal.setText(nf.format(totalPorEstado.get("Total")));
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(totalPorEstado.get("Consultoria e pesquisas").floatValue(), "Consultoria e pesquisas"));
        entries.add(new PieEntry(totalPorEstado.get("Locomoção, hospedagem e alimentação").floatValue(), "Locomoção, hospedagem e alimentação"));
        entries.add(new PieEntry(totalPorEstado.get("Divulgação do mandato").floatValue(), "Divulgação do mandato"));
        entries.add(new PieEntry(totalPorEstado.get("Passagens").floatValue(), "Passagens"));
        entries.add(new PieEntry(totalPorEstado.get("Segurança privada").floatValue(), "Segurança privada"));
        entries.add(new PieEntry(totalPorEstado.get("Correios e material para escritório político").floatValue(), "Correios e material para escritório político"));
        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(new int[] { R.color.graph1, R.color.graph2, R.color.graph3, R.color.graph4, R.color.graph5, R.color.graph6}, context);
        Legend l = holder.chart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setWordWrapEnabled(true);
        PieData data = new PieData(set);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextColor(Color.WHITE);
        holder.chart.setData(data);
        holder.chart.setDrawSliceText(false);
        holder.chart.setUsePercentValues(true);
        Description d = new Description();
        d.setText("");
        holder.chart.setDescription(d);
        holder.chart.invalidate();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
            return new ViewHolderRanking(view);
        }else if(viewType==1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tipo_despesas, parent, false);
            return new ViewHolderTipoDespesas(view);
        }else
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == 0) {
            initRanking((ViewHolderRanking) holder, position);
        }else if(holder.getItemViewType() == 1 ){
            initTipoDespesas((ViewHolderTipoDespesas) holder, position);
        }
    }

    @Override
    public int getItemViewType(int pos){
        if(pos==0){
            return 0;
        }else if(pos==1){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return tipos.size();
    }

    static class ViewHolderTipoDespesas extends RecyclerView.ViewHolder{
        public PieChart chart;
        public TextView txtValorConsultoria;
        public TextView txtValorLocomocao;
        public TextView txtValorDivulgacao;
        public TextView txtValorPassagens;
        public TextView txtValorSegurancaPrivada;
        public TextView txtValorEscritorio;
        public TextView txtValorTotal;
        public TextView txtTitleTipo;
        public ViewHolderTipoDespesas(View itemView){
            super(itemView);
            chart = (PieChart) itemView.findViewById(R.id.pie_chart);
            txtValorConsultoria = (TextView) itemView.findViewById(R.id.txtValorConsultoria);
            txtValorLocomocao = (TextView) itemView.findViewById(R.id.txtValorLocomocao);
            txtValorDivulgacao = (TextView) itemView.findViewById(R.id.txtValorDivulgacao);
            txtValorPassagens = (TextView) itemView.findViewById(R.id.txtValorPassagens);
            txtValorSegurancaPrivada = (TextView) itemView.findViewById(R.id.txtValorSeguranca);
            txtValorEscritorio = (TextView) itemView.findViewById(R.id.txtValorEscritorio);
            txtTitleTipo = (TextView) itemView.findViewById(R.id.txtTitleTipo);
            txtValorTotal = (TextView) itemView.findViewById(R.id.txtValorTotal);
        }
    }

    static class ViewHolderRanking extends RecyclerView.ViewHolder{
        public ImageButton buttonShowMore;
        public RecyclerView listViewRanking;
        public TextView rankingTitle;
        public ViewHolderRanking(View itemView){
            super(itemView);
            rankingTitle = (TextView) itemView.findViewById(R.id.ranking_title);
            listViewRanking = (RecyclerView)  itemView.findViewById(R.id.recyclerViewRanking);
            buttonShowMore = (ImageButton) itemView.findViewById(R.id.imgButtonVerMais);
        }
    }
}
