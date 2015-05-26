package com.example.sadarik.tpv;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aescribano on 18/05/15.
 */
public class AdaptadorProductos extends ArrayAdapter<Producto> {

    private Context contexto;
    private int recurso;
    private ArrayList<Producto> lista;

    public AdaptadorProductos(Context context, int resource, ArrayList<Producto> objects) {
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


        Producto item = lista.get(position);
        holder.tituloImagen.setText(item.getNombreProducto());

        int id = getContext().getResources().getIdentifier(item.getImagen(), "drawable", getContext().getPackageName());
        if(id!=0 && id!=1925){
            Drawable drawable = getContext().getResources().getDrawable(id);
            holder.imagen.setImageDrawable(drawable);
        }else{
            Drawable drawable = getContext().getResources().getDrawable(R.drawable.nofoto);
            holder.imagen.setImageDrawable(drawable);
        }

        return row;
    }

    static class ViewHolder {
        TextView tituloImagen;
        ImageView imagen;
    }
}
