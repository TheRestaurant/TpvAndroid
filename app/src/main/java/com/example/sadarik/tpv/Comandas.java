package com.example.sadarik.tpv;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Comandas extends ActionBarActivity {

    private LinearLayout lDetalle, lCuenta, lBotones;
    private GridView gridView, gridView2;
    private AdaptadorGridView adaptadorGrid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_comandas);

        lDetalle = (LinearLayout)findViewById(R.id.layoutProductos);
        lCuenta = (LinearLayout)findViewById(R.id.layoutCuenta);
        lBotones = (LinearLayout)findViewById(R.id.layoutBotones);
        gridView = (GridView) findViewById(R.id.gridView2);
        gridView2 = (GridView) findViewById(R.id.gridView3);

        /*Gridview para las familias*/
        adaptadorGrid = new AdaptadorGridView(this, R.layout.item_gridlayout, getFamilias());
        gridView.setAdapter(adaptadorGrid);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                lDetalle.setVisibility(View.VISIBLE);

                Toast.makeText(Comandas.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        /*Gridview para los productos*/
        adaptadorGrid = new AdaptadorGridView(this, R.layout.item_gridlayout, getProductos());
        gridView2.setAdapter(adaptadorGrid);

        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                lCuenta.setVisibility(View.VISIBLE);
                lBotones.setVisibility(View.VISIBLE);

                Toast.makeText(Comandas.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private ArrayList<ItemImagen> getProductos() {
        final ArrayList<ItemImagen> imagenes = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.refrescos);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imagenes.add(new ItemImagen(bitmap,  refrescos[i]));
        }
        return imagenes;
    }

    private ArrayList<ItemImagen> getFamilias() {
        final ArrayList<ItemImagen> imagenes = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.familias);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imagenes.add(new ItemImagen(bitmap,  familias[i]));
        }
        return imagenes;
    }


    /*Array*/

    private String[] familias = {
            "Cafes", "Batidos","Refrescos",
            "Cervezas","Vinos", "Licores",
            "Whisky", "Cocktails", "Tapas",
            "Comida rapida", "Bocadillos", "Ensaladas",
            "Carnes", "Pescados", "Especiales",
            "Varios", "Helados",
    };

    private String[] refrescos = {
            "Coca Cola", "Light",
            "Zero", "Naranja",
            "Limon", "7up",
            "Swecheps",
    };

}

