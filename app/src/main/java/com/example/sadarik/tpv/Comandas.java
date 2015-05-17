package com.example.sadarik.tpv;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
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

    private LinearLayout lDetalle, lCuenta, lBotones;
    private GridView gridView, gridView2;
    private AdaptadorGridView adaptadorGrid;
    private int mesaActual;
    private JSONArray jArray;
    private int productos = R.array.familias;;
    private String[] nombres;
    private TextView tvCuenta;
    private ArrayList<Familia> fm;
    private ArrayList<Producto> pr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_comandas);
        Intent i = getIntent();
        mesaActual = i.getIntExtra("mesa",-1);
        Log.v("mesa", mesaActual + "");
        lDetalle = (LinearLayout)findViewById(R.id.layoutProductos);
        lCuenta = (LinearLayout)findViewById(R.id.layoutCuenta);
        lBotones = (LinearLayout)findViewById(R.id.layoutBotones);
        gridView = (GridView) findViewById(R.id.gridView2);
        gridView2 = (GridView) findViewById(R.id.gridView3);
        tvCuenta = (TextView)findViewById(R.id.tvCuenta);
        fm = Principal.familias;
        pr = Principal.productos;

       final String[] familias = new String[fm.size()];
        for (int j = 0; j < fm.size(); j++) {
            familias[j] = fm.get(j).getNombreFamilia();

        }

        final String[] productosBucle = new String[pr.size()];
        for (int j = 0; j < pr.size(); j++) {
            productosBucle[j] = pr.get(j).getNombreProducto();
        }
        Log.v("productos", ""+productosBucle.length);


        /*Gridview para las familias*/
        adaptadorGrid = new AdaptadorGridView(this, R.layout.item_gridlayout, getFamilias(productos, familias));
        gridView.setAdapter(adaptadorGrid);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                lDetalle.setVisibility(View.VISIBLE);
                switch (position) {

                    case 0:
                        productos = R.array.cafes;
                        nombres = cafes;
                        rellenarGrid();
                        break;
                    case 1:
                        productos = R.array.familias;
                        nombres = familias;
                        rellenarGrid();
                        break;
                    case 2:
                        productos = R.array.refrescos;
                        nombres = refrescos;
                        rellenarGrid();
                        break;
                    case 3:
                        Toast.makeText(Comandas.this, "cervezas", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:

                        Toast.makeText(Comandas.this, "vinos", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(Comandas.this, "licores", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(Comandas.this, "whisky", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(Comandas.this, "cocktails", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(Comandas.this, "tapas", Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        Toast.makeText(Comandas.this, "comida rapida", Toast.LENGTH_SHORT).show();
                        break;
                    case 10:
                        Toast.makeText(Comandas.this, "bocadillos", Toast.LENGTH_SHORT).show();
                        break;
                    case 11:
                        Toast.makeText(Comandas.this, "ensaladas", Toast.LENGTH_SHORT).show();
                        break;
                    case 12:
                        Toast.makeText(Comandas.this, "carnes", Toast.LENGTH_SHORT).show();
                        break;
                    case 13:
                        Toast.makeText(Comandas.this, "pescados", Toast.LENGTH_SHORT).show();
                        break;
                    case 14:
                        Toast.makeText(Comandas.this, "especiales", Toast.LENGTH_SHORT).show();
                        break;
                    case 15:
                        Toast.makeText(Comandas.this, "varios", Toast.LENGTH_SHORT).show();
                        break;
                    case 16:
                        Toast.makeText(Comandas.this, "helados", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }

            }
        });

        /*Gridview para los productos*/

        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                lCuenta.setVisibility(View.VISIBLE);
                lBotones.setVisibility(View.VISIBLE);

                Toast.makeText(Comandas.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void rellenarGrid(){
        adaptadorGrid = new AdaptadorGridView(Comandas.this, R.layout.item_gridlayout, getFamilias(productos, nombres));
        gridView2.setAdapter(adaptadorGrid);
    }


    private ArrayList<ItemImagen> getFamilias(int productos, String[] nombres) {
        final ArrayList<ItemImagen> imagenes = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(productos);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imagenes.add(new ItemImagen(bitmap,  nombres[i]));
        }
        return imagenes;
    }


    /*Array*/

 /*   private String[] familias = {
            "Cafes", "Batidos","Refrescos",
            "Cervezas","Vinos", "Licores",
            "Whisky", "Cocktails", "Tapas",
            "Comida rapida", "Bocadillos", "Ensaladas",
            "Carnes", "Pescados", "Especiales",
            "Varios", "Helados",
    }; */

    private String[] cafes = {
            "Solo", "Solo Descafeinado","Cortado",
            "Cortado Descafeinado","Con Leche", "Con Leche Descafeinado",
            "Leche", "Bombon", "Bombon Descafeinado",
            "Carajillo", "Carajillo Descafeinado", "Te",
            "Te con Leche", "Menta Poleo", "Manzanilla",
            "Tila", "Cola Cao","Belmonte",
            "Tewhis", "Americano", "Americano Descafeinado",
            "Capuchino",
    };

    private String[] refrescos = {
            "Coca Cola", "Light",
            "Zero", "Naranja",
            "Limon", "7up",
            "Swecheps",
    };

    class Pedido extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            String url="http://192.168.1.7:8080/ServletRestaurante/peticiones?target="+params[0];
            String r = mandarPedido(url);
            return r;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.v("respuesta", s.toString());
            JSONTokener tokenact = new JSONTokener(s.substring(4,s.length()));
            try {
                JSONObject obj = new JSONObject(tokenact);
                Log.v("json", obj.toString());
            } catch (JSONException e) {

            }

        }

        public String mandarPedido(String urlPeticion) {
            URL url = null;
            try {
                url = new URL(urlPeticion);
            } catch (MalformedURLException e) {
                Log.v("error", e.toString());
            }
            URLConnection conexion=null;
            OutputStreamWriter out = null;
            try {
                conexion = url.openConnection();
                conexion.setDoOutput(true);
                out = new OutputStreamWriter(conexion.getOutputStream());
                out.write("&datos="+jArray.toString());
                out.flush();
                out.close();
            } catch (IOException e) {
                Log.v("error", e.toString());
            }
            BufferedReader in = null;
            String resultado=null;
            try {
                in = new BufferedReader( new InputStreamReader(conexion.getInputStream()));
                String decodedString;
                while ((decodedString = in.readLine()) != null) {
                    resultado+=decodedString+"\n";
                }
                in.close();
            } catch (IOException e) {
                Log.v("error", e.toString());
            }
            return resultado;
        }
    }

}

