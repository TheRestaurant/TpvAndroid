package com.example.sadarik.tpv;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class AdaptadorGridView extends ArrayAdapter<ItemImagen> {

    private Context contexto;
    private int recurso;
    private ArrayList<ItemImagen> lista = new ArrayList<ItemImagen>();

    public AdaptadorGridView(Context context, int resource, ArrayList<ItemImagen> objects) {
        super(context, resource, objects);
        this.recurso = resource;
        this.contexto = context;
        this.lista = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) contexto).getLayoutInflater();
            row = inflater.inflate(recurso, parent, false);
            holder = new ViewHolder();
            holder.tituloImagen = (TextView) row.findViewById(R.id.text);
            holder.imagen = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        ItemImagen item = lista.get(position);
        holder.tituloImagen.setText(item.getTitulo());
        holder.imagen.setImageBitmap(item.getImagen());
        return row;
    }

    static class ViewHolder {
        TextView tituloImagen;
        ImageView imagen;
    }
}