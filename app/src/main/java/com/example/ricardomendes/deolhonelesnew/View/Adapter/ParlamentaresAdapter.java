package com.example.ricardomendes.deolhonelesnew.View.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.ricardomendes.deolhonelesnew.ManageData.ASynkTasks.DownloadImage;
import com.example.ricardomendes.deolhonelesnew.Model.Deputado;
import com.example.ricardomendes.deolhonelesnew.R;
import com.example.ricardomendes.deolhonelesnew.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ParlamentaresAdapter extends RecyclerView.Adapter<ParlamentaresAdapter.MyViewHold> implements SectionIndexer{
    private ArrayList<Deputado> listdeputados;
    private ArrayList<Integer> mSectionPositions;
    public ParlamentaresAdapter(ArrayList<Deputado> l){
        listdeputados = l;
    }
    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = listdeputados.size(); i < size; i++) {
            String section = String.valueOf(listdeputados.get(i).getNome().charAt(0)).toUpperCase();
            if (!sections.contains(section) && !section.equals("Á")) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }
    @Override
    public ParlamentaresAdapter.MyViewHold onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deputado, null, false);
        final MyViewHold myVHold = new MyViewHold(v);

        return myVHold;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHold holder, int position) {
        holder.colocarDados(listdeputados.get(position));
    }

    @Override
    public int getItemCount() {
        return listdeputados.size();
    }


    public static class MyViewHold extends RecyclerView.ViewHolder{
        public TextView txtNome;
        public TextView txtDesc;
        public ImageView imgView;
        public View itemView;
        public TextView txtValorTotal;
        public MyViewHold(View itemView){
            super(itemView);
            this.itemView = itemView;
            txtNome = (TextView) itemView.findViewById(R.id.txtNome);
            txtDesc = (TextView) itemView.findViewById(R.id.txtDesc);
            txtValorTotal = (TextView) itemView.findViewById(R.id.txtTotalDep);
            imgView =(ImageView) itemView.findViewById(R.id.img_deputado);
        }
        public void colocarDados(Deputado dep) {
            txtNome.setText(dep.getNome());
            txtDesc.setText(dep.getSiglaPartido() + " - " + dep.getSiglaUf());
            txtValorTotal.setText(new Utils().formatMoney(dep.getTotalDespesas()));
            String urlFoto = dep.getUrlFoto();
            new DownloadImage((ImageView) imgView).execute(urlFoto);
        }

    }

}
