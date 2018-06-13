package com.example.ricardomendes.deolhonelesnew.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.R;
import com.example.ricardomendes.deolhonelesnew.Utils.Utils;
import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<Deputado> listDeputados;
    Context context;
    public RankingAdapter(Context context, ArrayList<Deputado> listDeputados){
        this.listDeputados = listDeputados;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_ranking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        init((ViewHolder) holder, position);
    }
    @Override
    public int getItemCount() {
        return listDeputados.size();
    }
    private void init(ViewHolder holder, int pos){
        holder.txtPos.setText((pos+1) + "º");
        holder.txtNome.setText(listDeputados.get(pos).getNome());
        holder.txtValor.setText(new Utils().formatMoney(listDeputados.get(pos).getTotalDespesas()));
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtPos;
        TextView txtNome;
        TextView txtValor;
        public ViewHolder(View itemView) {
            super(itemView);
            txtPos = (TextView) itemView.findViewById(R.id.txtPos);
            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtValor = (TextView) itemView.findViewById(R.id.txtValor);
        }
    }
}
