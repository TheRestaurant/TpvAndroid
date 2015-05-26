package com.example.sadarik.tpv;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Comandas extends ActionBarActivity {

    private LinearLayout lFamilias, lDetalle, lCuenta, lBotones;
    private GridView gridView, gridView2;
    private ListView lvPedidos;
    private AdaptadorFamilias adaptadorFamilias;
    private AdaptadorProductos adaptadorProductos;
    private AdaptadorPedido adaptadorPedido;
    private int mesaActual;
    private JSONArray jArray;
    private ArrayList<Familia> fm;
    private ArrayList<Producto> pr;
    private ArrayList<Producto> auxiliar;
    private ArrayList<Pedido> listaPedidos;
    private int contador;
    private Button btOcultar;
    private ImageView separador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_comandas);
        contador=0;
        Intent i = getIntent();
        mesaActual = i.getIntExtra("mesa", -1);
        Log.v("mesa", mesaActual + "");
        lDetalle = (LinearLayout) findViewById(R.id.layoutProductos);
        lFamilias = (LinearLayout)findViewById(R.id.lFamilias);
        lCuenta = (LinearLayout) findViewById(R.id.layoutCuenta);
        lBotones = (LinearLayout) findViewById(R.id.layoutBotones);
        gridView = (GridView) findViewById(R.id.gridView2);
        gridView2 = (GridView) findViewById(R.id.gridView3);
        lvPedidos = (ListView) findViewById(R.id.lvPedidos);
        btOcultar = (Button)findViewById(R.id.btPedido);
        separador = (ImageView)findViewById(R.id.ivSeparador);
        fm = Principal.familias;
        pr = Principal.productos;
        listaPedidos = new ArrayList<>();
        setTitle(i.getStringExtra("nombreMesa") +" - "+ i.getStringExtra("zona"));
        adaptadorFamilias = new AdaptadorFamilias(this, R.layout.item_gridlayout, fm);
        gridView.setAdapter(adaptadorFamilias);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lDetalle.setVisibility(View.VISIBLE);
                separador.setVisibility(View.VISIBLE);
                Familia f = (Familia) parent.getAdapter().getItem(position);
                auxiliar = new ArrayList<Producto>();
                for (int j = 0; j < pr.size(); j++) {
                    if (pr.get(j).getIdFamilia() == f.getIdFamilia()) {
                        auxiliar.add(pr.get(j));
                    }
                }
                rellenarGridProductos(auxiliar);
            }
        });

        /*Gridview para los productos*/

        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                lCuenta.setVisibility(View.VISIBLE);
                lBotones.setVisibility(View.VISIBLE);
                Producto p = (Producto) parent.getAdapter().getItem(position);
                Pedido pedido = new Pedido(p);
                if (listaPedidos.contains(pedido)) {
                    int pos = listaPedidos.indexOf(pedido);
                    listaPedidos.get(pos).sumaCantidad();
                } else {
                    listaPedidos.add(new Pedido(p));
                }
                rellenarListViewPedidos(listaPedidos);
            }
        });

    }


    public void rellenarGridProductos(ArrayList<Producto> lista) {
        adaptadorProductos = new AdaptadorProductos(Comandas.this, R.layout.item_gridlayout, lista);
        gridView2.setAdapter(adaptadorProductos);
    }

    public void rellenarListViewPedidos(ArrayList<Pedido> lista) {
        adaptadorPedido = new AdaptadorPedido(Comandas.this, R.layout.detalle_pedido, lista);
        lvPedidos.setAdapter(adaptadorPedido);
    }


    class RealizarPedido extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = "http://192.168.5.24:8080/ServletRestaurante/peticiones?target=" + params[0];
            String r = mandarPedido(url);
            return r;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.v("respuesta", s.toString());
            JSONTokener tokenact = new JSONTokener(s.substring(4, s.length()));
            try {
                JSONObject obj = new JSONObject(tokenact);
                Toast.makeText(Comandas.this, obj.getString("r"), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {

            }
            finish();
        }

        public String mandarPedido(String urlPeticion) {
            URL url = null;
            try {
                url = new URL(urlPeticion);
            } catch (MalformedURLException e) {
                Log.v("error", e.toString());
            }
            URLConnection conexion = null;
            OutputStreamWriter out = null;
            try {
                conexion = url.openConnection();
                conexion.setDoOutput(true);
                out = new OutputStreamWriter(conexion.getOutputStream());
                out.write("&datos=" + jArray.toString());
                out.flush();
                out.close();
            } catch (IOException e) {
                Log.v("error", e.toString());
            }
            BufferedReader in = null;
            String resultado = null;
            try {
                in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String decodedString;
                while ((decodedString = in.readLine()) != null) {
                    resultado += decodedString + "\n";
                }
                in.close();
            } catch (IOException e) {
                Log.v("error", e.toString());
            }
            return resultado;
        }
    }

    public void mandarPedido(View v) {
        RealizarPedido pide = new RealizarPedido();
        JSONObject pedir;
        jArray = new JSONArray();
        int cont = 0;
        try {
            for (int i = 0; i < listaPedidos.size(); i++) {
                while (listaPedidos.get(i).getCantidad() > cont) {
                    pedir = new JSONObject();
                    pedir.put("idMesa", mesaActual);
                    pedir.put("idProducto", listaPedidos.get(i).getProducto().getIdProducto());
                    jArray.put(pedir);
                    cont++;
                }
                cont = 0;
            }
            Log.v("arraypedido", jArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pide.execute("pedido");
    }

    public void esImpar() {
        contador++;
        if (contador%2!=0){
            lFamilias.setVisibility(View.GONE);
            btOcultar.setBackgroundDrawable(getResources().getDrawable(R.drawable.comida));
        }

        else{
            lFamilias.setVisibility(View.VISIBLE);
            btOcultar.setBackgroundDrawable(getResources().getDrawable(R.drawable.pedido2));
        }

    }

    public void mostrar(View view){
        esImpar();
    }
}

