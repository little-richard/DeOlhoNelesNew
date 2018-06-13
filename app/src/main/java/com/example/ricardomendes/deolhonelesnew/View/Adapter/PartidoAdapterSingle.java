package com.example.ricardomendes.deolhonelesnew.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks.DownloadImage;
import com.example.ricardomendes.deolhonelesnew.ManageData.ManageInfo;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.Model.Partido;
import com.example.ricardomendes.deolhonelesnew.R;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

public class PartidoAdapterSingle extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static Context context;
    private String[] tipos;
    private static Partido partido;
    public PartidoAdapterSingle(Context context, String[] tipos, Partido partido){
        this.partido = partido;
        this.context = context;
        this.tipos = tipos;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_infos_partido, parent, false);
            return new ViewHoldInfos(view);
        }else if(viewType==1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deputado, parent, false);
            return new ViewHoldLider(view);
        }else if(viewType==2){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_membros_partido, parent, false);
            return new ViewHoldMembros(view);
        }else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType()==0){
            ((ViewHoldInfos) holder).updateUI();
        }else if(holder.getItemViewType()==1){
            ((ViewHoldLider) holder).updateUI();
        }else if(holder.getItemViewType()==2){
            ((ViewHoldMembros) holder).updateUI();
        }
    }

    @Override
    public int getItemCount() {
        return tipos.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    static class ViewHoldInfos extends RecyclerView.ViewHolder{
        private TextView situacao;
        private TextView qtdeMembros;
        public ViewHoldInfos(View itemView) {
            super(itemView);
            situacao = (TextView) itemView.findViewById(R.id.txtsituacaopartido);
            qtdeMembros = (TextView) itemView.findViewById(R.id.txtqtdemembros);
        }
        public void updateUI(){
            situacao.setText(partido.getStatusPartido().getSituacao());
            qtdeMembros.setText(partido.getStatusPartido().getTotalMembros());
        }
    }

    static class ViewHoldLider extends RecyclerView.ViewHolder{
        private TextView txtNome;
        private TextView txtDesc;
        private TextView txtValor;
        private ImageView imglider;
        public ViewHoldLider(View itemView) {
            super(itemView);
            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtDesc = (TextView) itemView.findViewById(R.id.txtDesc);
            txtValor = (TextView) itemView.findViewById(R.id.txtTotalDep);
            imglider = (ImageView) itemView.findViewById(R.id.img_deputado);
        }

        public void updateUI(){
            txtNome.setText(partido.getStatusPartido().getLider().getNome());
            txtDesc.setText(partido.getStatusPartido().getLider().getSiglaPartido() + "-" + partido.getStatusPartido().getLider().getSiglaUf());
            txtValor.setText("");
            new DownloadImage(imglider).execute(partido.getStatusPartido().getLider().getUrlFoto());
        }
    }
    static class ViewHoldMembros extends RecyclerView.ViewHolder{
        private RecyclerView recyclerViewMembros;
        public ViewHoldMembros(View itemView) {
            super(itemView);
            recyclerViewMembros = (RecyclerView) itemView.findViewById(R.id.recycler_membros);
        }
        public void updateUI(){
            AdapterMembros adapter = new AdapterMembros(partido.getMembros());
            LinearLayoutManager lin = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewMembros.setLayoutManager(lin);
            recyclerViewMembros.setAdapter(adapter);
        }
    }
}
