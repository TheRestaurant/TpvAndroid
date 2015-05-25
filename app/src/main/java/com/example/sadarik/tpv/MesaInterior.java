package com.example.sadarik.tpv;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;


public class MesaInterior extends Activity implements OnClickListener {

    private ArrayList<Button> arrayBotones;
    GridView gridView;
    Button bt;
    private ArrayList<Mesa> mesas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_mesa_terraza);
        mesas = Principal.mesas;
        arrayBotones = new ArrayList<Button>();

        for (int i = 0; i < mesas.size(); i++) {
            if (mesas.get(i).getZona().contains("Comedor")) {
                bt = new Button(this);
                bt.setText(mesas.get(i).getNombreMesa());
                bt.setHeight(200);
                bt.setTextSize(25);
                bt.setBackgroundResource(R.drawable.mesas);
                bt.setOnClickListener(this);
                bt.setId(mesas.get(i).getIdMesa());
                registerForContextMenu(bt);
                arrayBotones.add(bt);
            }
        }
        gridView = (GridView) findViewById(R.id.gridviewTerraza);
        gridView.setAdapter(new AdaptadorBotonesInterior(arrayBotones));
    }

    @Override
    public void onClick(View v) {
        Button botonSeleccionado = (Button) v;
        Intent comanda = new Intent(this, Comandas.class);
        comanda.putExtra("mesa",botonSeleccionado.getId());
        comanda.putExtra("nombreMesa",botonSeleccionado.getText());
        comanda.putExtra("zona", "Interior");
        startActivity(comanda);
    }
}


