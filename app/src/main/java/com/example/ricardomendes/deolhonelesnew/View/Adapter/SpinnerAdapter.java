package com.example.ricardomendes.deolhonelesnew.View.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ricardomendes.deolhonelesnew.Interfaces.Helper.Constants;
import com.example.ricardomendes.deolhonelesnew.R;

public class SpinnerAdapter extends ArrayAdapter<String>{
    private LayoutInflater mInflater;
    private final Context mContext;
    private final int mResource;
    private final String[] estados;
    public SpinnerAdapter(@NonNull Context context, int resource, String[] estados) {
        super(context, resource);
        this.mContext = context;
        this.mResource = resource;
        mInflater = null;
        this.estados = estados;
    }

    @Override
    public int getCount() {
        return estados.length;
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mResource, parent, false);
        TextView estado = convertView.findViewById(R.id.txt_estado);
        estado.setText(estados[position]);
        return convertView;
    }
}
