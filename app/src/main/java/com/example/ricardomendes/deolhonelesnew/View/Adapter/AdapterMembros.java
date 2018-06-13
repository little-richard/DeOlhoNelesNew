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
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.Model.Partido;
import com.example.ricardomendes.deolhonelesnew.R;

import java.util.ArrayList;

public class AdapterMembros extends RecyclerView.Adapter<AdapterMembros.MembrosViewHold>{
    private ArrayList<Deputado> listMembros;
    public AdapterMembros(ArrayList<Deputado> list){
        this.listMembros = list;
    }

    @NonNull
    @Override
    public MembrosViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_membro_single, null, false);
        return new MembrosViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MembrosViewHold holder, int position) {
        holder.colocarDados(listMembros.get(position));
    }

    @Override
    public int getItemCount() {
        return listMembros.size();
    }

    static class MembrosViewHold extends RecyclerView.ViewHolder{
        private TextView txtNome;
        private TextView txtDesc;
        private ImageView imgMembro;
        public MembrosViewHold(View itemView) {
            super(itemView);
            txtNome = (TextView) itemView.findViewById(R.id.txtNomeMembro);
            txtDesc = (TextView) itemView.findViewById(R.id.txtSiglaMembro);
            imgMembro = (ImageView) itemView.findViewById(R.id.imgMembro);
        }

        public void colocarDados(Deputado dep){
            txtNome.setText(dep.getNome());
            txtDesc.setText(dep.getSiglaUf());
            new DownloadImage(imgMembro).execute(dep.getUrlFoto());
        }
    }
}
