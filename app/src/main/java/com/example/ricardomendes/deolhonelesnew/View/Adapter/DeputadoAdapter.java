package com.example.ricardomendes.deolhonelesnew.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ricardomendes.deolhonelesnew.FormatChart.PercentFormatter;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DeputadoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Deputado itemDeputado;
    private ArrayList<String> types;
    private Context context;
    public DeputadoAdapter(Context context, ArrayList<String> types, Deputado deputado){
        this.itemDeputado = deputado;
        this.types = types;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
            return new ViewHolderInfos(view);
        }else if(viewType == 1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_graph_bar_ano, parent, false);
            return new ViewHolderGraphBar(view);
        }else if(viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tipo_despesas, parent, false);
            return new ViewHolderPorCategoria(view);
        }else if(viewType == 3){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contato, parent, false);
            return new ViewHolderContato(view);
        }
        else{
            throw new RuntimeException("TIPO ERRADO");
        }
    }

    private void initInfos(ViewHolderInfos holder, int pos){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        holder.totalDespesa.setText(nf.format(itemDeputado.getTotalDespesas()));
        holder.qtdeProjetos.setText("" + itemDeputado.getQtdeProjetos() + " projetos");
        String legislaturaStart[] = itemDeputado.getLegislatura().getDataInicio().split("-");
        String legislaturaEnd[] = itemDeputado.getLegislatura().getDataFim().split("-");
        holder.anoParlamentar.setText(legislaturaStart[0] + " - " + legislaturaEnd[0]);
        holder.situacao.setText(itemDeputado.getUltimoStatus().getSituacao());
    }
    private void initContato(ViewHolderContato holder, int pos){
        holder.email.setText(itemDeputado.getUltimoStatus().getGabinete().getEmail().toLowerCase());
        holder.telefone.setText("(61) " + itemDeputado.getUltimoStatus().getGabinete().getTelefone());
    }

    private void initPorCategoria(ViewHolderPorCategoria holder, int pos){
        List<PieEntry> entries = new ArrayList<>();
        Set<String> chaves = itemDeputado.getTotalTipoDespesas().keySet();
        for(String chave : chaves){
            entries.add(new PieEntry(itemDeputado.getTotalTipoDespesas().get(chave).floatValue(), chave));
        }
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
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        holder.txtValorConsultoria.setText(nf.format(itemDeputado.getTotalTipoDespesas().get("Consultoria e pesquisas")));
        holder.txtValorLocomocao.setText(nf.format(itemDeputado.getTotalTipoDespesas().get("Locomoção, hospedagem e alimentação")));
        holder.txtValorDivulgacao.setText(nf.format(itemDeputado.getTotalTipoDespesas().get("Divulgação do mandato")));
        holder.txtValorPassagens.setText(nf.format(itemDeputado.getTotalTipoDespesas().get("Passagens")));
        holder.txtValorSegurancaPrivada.setText(nf.format(itemDeputado.getTotalTipoDespesas().get("Segurança privada")));
        holder.txtValorEscritorio.setText(nf.format(itemDeputado.getTotalTipoDespesas().get("Correios e material para escritório político")));
        holder.txtTotal.setText(nf.format(itemDeputado.getTotalDespesas()));
    }

    private void initGraphBar(ViewHolderGraphBar holder, int pos){
        List<BarEntry> entries = new ArrayList<BarEntry>();
        Set<String> chaves = itemDeputado.getTotalDespesasAno().keySet();
        entries.add(new BarEntry(2015f, itemDeputado.getTotalDespesasAno().get("2015").floatValue()));
        entries.add(new BarEntry(2016f, itemDeputado.getTotalDespesasAno().get("2016").floatValue()));
        entries.add(new BarEntry(2017f, itemDeputado.getTotalDespesasAno().get("2017").floatValue()));
        entries.add(new BarEntry(2018f, itemDeputado.getTotalDespesasAno().get("2018").floatValue()));
        BarDataSet dataSet = new BarDataSet(entries, "");
        int[] colors = new int[] {R.color.graph1, R.color.graph2, R.color.graph3, R.color.graph4};
        dataSet.setColors(colors, context);
        String[] labels = new String[4];
        BarData barData = new BarData(dataSet);
        XAxis xAxis = holder.chart.getXAxis();
        Description d = new Description();
        d.setText("");
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        barData.setBarWidth(0.4f);
        holder.chart.setData(barData);
        holder.chart.setDescription(d);
        holder.chart.setFitBars(true);
        holder.chart.invalidate();

        Legend legend = holder.chart.getLegend();
        legend.setEnabled(true);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        legend.setTextSize(12f);
        legend.setXEntrySpace(10f);
        legend.setYEntrySpace(5f);
        List<LegendEntry> legendEntryList = new ArrayList<LegendEntry>();
        for(int i=2015; i<=2018; i++){
            LegendEntry legendEntry = new LegendEntry();
            legendEntry.label = "" + i;
            legendEntry.form = Legend.LegendForm.SQUARE;
            legendEntryList.add(legendEntry);
        }
        legendEntryList.get(0).formColor = Color.parseColor("#4caf50");
        legendEntryList.get(1).formColor = Color.parseColor("#0288d1");
        legendEntryList.get(2).formColor = Color.parseColor("#512da8");
        legendEntryList.get(3).formColor = Color.parseColor("#00897b");
        legend.setCustom(legendEntryList);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                if(holder.getItemViewType() == 0){
                    initInfos((ViewHolderInfos)holder, position);
                }else if(holder.getItemViewType() == 1){
                    initGraphBar((ViewHolderGraphBar) holder, position);
                }else if(holder.getItemViewType() == 2){
                    initPorCategoria((ViewHolderPorCategoria) holder, position);
                }else if(holder.getItemViewType() == 3){
                    initContato((ViewHolderContato) holder, position);
                }
    }

    @Override
    public int getItemCount() {
        return this.types.size();
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }
    static class ViewHolderPorCategoria extends  RecyclerView.ViewHolder{
        PieChart chart;
        public TextView txtTotal;
        public TextView txtValorConsultoria;
        public TextView txtValorLocomocao;
        public TextView txtValorDivulgacao;
        public TextView txtValorPassagens;
        public TextView txtValorSegurancaPrivada;
        public TextView txtValorEscritorio;
        public ViewHolderPorCategoria(View itemView){
            super(itemView);
            chart = (PieChart) itemView.findViewById(R.id.pie_chart);
            txtTotal = (TextView) itemView.findViewById(R.id.txtValorTotal);
            txtValorConsultoria = (TextView) itemView.findViewById(R.id.txtValorConsultoria);
            txtValorLocomocao = (TextView) itemView.findViewById(R.id.txtValorLocomocao);
            txtValorDivulgacao = (TextView) itemView.findViewById(R.id.txtValorDivulgacao);
            txtValorPassagens = (TextView) itemView.findViewById(R.id.txtValorPassagens);
            txtValorSegurancaPrivada = (TextView) itemView.findViewById(R.id.txtValorSeguranca);
            txtValorEscritorio = (TextView) itemView.findViewById(R.id.txtValorEscritorio);
        }
    }

    static class ViewHolderInfos extends RecyclerView.ViewHolder{
        TextView totalDespesa;
        TextView situacao;
        TextView anoParlamentar;
        TextView qtdeProjetos;
        public ViewHolderInfos(View itemView){
            super(itemView);
            totalDespesa = (TextView) itemView.findViewById(R.id.txtTotalDespesa);
            situacao = (TextView) itemView.findViewById(R.id.txtSituacao);
            anoParlamentar = (TextView) itemView.findViewById(R.id.txtAnoLegislatura);
            qtdeProjetos = (TextView) itemView.findViewById(R.id.txtQtdeProjetos);

        }
    }

    static class ViewHolderContato extends RecyclerView.ViewHolder{
        TextView telefone;
        TextView email;

        public ViewHolderContato(View itemView){
            super(itemView);
            telefone = (TextView) itemView.findViewById(R.id.txtTelefone);
            email = (TextView) itemView.findViewById(R.id.txtEmail);
        }
    }

    static class ViewHolderGraphBar extends RecyclerView.ViewHolder{
        BarChart chart;
        public ViewHolderGraphBar(View itemView){
            super(itemView);
            chart  = (BarChart) itemView.findViewById(R.id.graphView);
        }
    }

}
