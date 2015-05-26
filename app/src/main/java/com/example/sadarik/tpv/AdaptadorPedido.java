package com.example.sadarik.tpv;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorPedido extends ArrayAdapter<Pedido> {

    private Context contexto;
    private int recurso;
    private ArrayList<Pedido> lista;

    public AdaptadorPedido(Context context, int resource, ArrayList<Pedido> objects) {
        super(context, resource, objects);
        this.recurso = resource;
        this.contexto = context;
        this.lista = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) contexto).getLayoutInflater();
            convertView = inflater.inflate(recurso, parent, false);
            holder = new ViewHolder();
            holder.tvNombreProducto = (TextView) convertView.findViewById(R.id.tvProducto);
            holder.tvCantidad = (TextView) convertView.findViewById(R.id.tvCantidad);
            holder.tvPrecio = (TextView) convertView.findViewById(R.id.tvPrecio);
            holder.btBorrar = (ImageButton) convertView.findViewById(R.id.btCancelPedido);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Pedido item = lista.get(position);
        holder.tvNombreProducto.setText(item.getProducto().getNombreProducto());
        holder.tvCantidad.setText(item.getCantidad()+"");
        holder.tvPrecio.setText(item.getProducto().getPrecio()*item.getCantidad()+"");
        holder.btBorrar.setTag(position);
        holder.btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista.remove(item);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tvNombreProducto, tvCantidad, tvPrecio;
        ImageButton btBorrar;
    }
}
