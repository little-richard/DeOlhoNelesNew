package com.example.ricardomendes.deolhonelesnew.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks.DownloadImage;
import com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks.DownloadImageGif;
import com.example.ricardomendes.deolhonelesnew.Model.Partido;
import com.example.ricardomendes.deolhonelesnew.R;

import java.util.ArrayList;

public class PartidosAdapter extends RecyclerView.Adapter<PartidosAdapter.PartidosViewHold>{
    private ArrayList<Partido> listPartidos;
    private static Context context;
    public PartidosAdapter(Context context, ArrayList<Partido> list){
        this.listPartidos = list;
        this.context = context;
    }
    @NonNull
    @Override
    public PartidosViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partidos_list, null, false);
        return new PartidosViewHold(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PartidosViewHold holder, int position) {
        holder.colocarDados(listPartidos.get(position));
    }
    @Override
    public int getItemCount() {
        return listPartidos.size();
    }

    static class PartidosViewHold extends RecyclerView.ViewHolder{
        private ImageView imgLogo;
        private TextView txtNomePartido;
        private TextView txtSiglaPartido;
        public PartidosViewHold(View itemView) {
            super(itemView);
            imgLogo = (ImageView) itemView.findViewById(R.id.imgLogoPartido);
            txtNomePartido = (TextView) itemView.findViewById(R.id.txtNomePartido);
            txtSiglaPartido = (TextView) itemView.findViewById(R.id.txtSiglaPartido);
        }

        public void colocarDados(Partido partido){
            new DownloadImageGif(context,imgLogo).execute(partido.getUrlLogo());
            txtNomePartido.setText(partido.getNome());
            txtSiglaPartido.setText(partido.getSigla());
        }
    }
}
