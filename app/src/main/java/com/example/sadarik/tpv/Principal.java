package com.example.sadarik.tpv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import java.text.Normalizer;
import java.util.ArrayList;

public class Principal extends FragmentActivity {


    private EditText usuario, password;
    private String baseUrl = "http://192.168.5.24:8080/ServletRestaurante/peticiones?target=";
    public static ArrayList<Mesa> mesas;
    public static ArrayList<Familia> familias;
    public static ArrayList<Producto> productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        usuario = (EditText) findViewById(R.id.etUsuario);
        password = (EditText) findViewById(R.id.etPassword);


    }

    public void login(View view) {
        Peticion pet = new Peticion();
        String[] datos = new String[]{baseUrl + "login", "&login=" + usuario.getText() + "&password=" + password.getText()};
        pet.execute(datos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class Peticion extends AsyncTask<String[], Integer, String> {


        @Override
        protected String doInBackground(String[]... params) {

            String r = "";
            for (String[] p : params) {
                r = pedirInfo(p[0], p[1]);
            }
            return r;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONTokener token = new JSONTokener(s.substring(4, s.length()));
                JSONObject objeto = new JSONObject(token);
                String respuesta = objeto.getString("r");
                if (respuesta.equals("1")) {
                    CargarDatos carga = new CargarDatos();
                    String[] peticiones = new String[3];
                    peticiones[0] = "mesas";
                    peticiones[1] = "familias";
                    peticiones[2] = "productos";
                    carga.execute(peticiones);
                } else {
                    Toast.makeText(getBaseContext(), "Usuario " + usuario.getText() + password.getText(), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.v("error", e.toString());
            }

        }

        public String pedirInfo(String urlPeticion, String datos) {
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
                out.write(datos);
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

    class CargarDatos extends AsyncTask<String, Void, String[]> {

        private ProgressDialog dialog;

        @Override
        protected String[] doInBackground(String... params) {
            String[] r = new String[params.length];
            int contador = 0;
            for (String s : params) {
                SystemClock.sleep(500);
                r[contador] = pedirDatos("http://192.168.5.24:8080/ServletRestaurante/peticiones?target=" + s);
                contador++;
            }
            return r;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Principal.this);
            dialog.setMessage(getString(R.string.cargando));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);

            JSONTokener tokenmesas = new JSONTokener(strings[0].substring(4, strings[0].length()));
            mesas = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(tokenmesas);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    Mesa m = new Mesa(objeto.getInt("idMesa"), objeto.getString("nombreMesa"), objeto.getString("nombreZona"));
                    mesas.add(m);
                }
                Log.v("total mesas", mesas.size() + "");
            } catch (JSONException e) {

            }

            TypedArray imgs = getResources().obtainTypedArray(R.array.familias);

            JSONTokener tokenfamilias = new JSONTokener(strings[1].substring(4, strings[1].length()));
            familias = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(tokenfamilias);
                String imagen = null;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    String busca = objeto.getString("nombreFamilia").replaceAll(" ", "").toLowerCase();
                    busca = Normalizer.normalize(busca, Normalizer.Form.NFD);
                    busca = busca.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                    for (int j = 0; j < imgs.length(); j++) {
                        if (imgs.getString(j).contains(busca)) {
                            imagen = imgs.getString(j);
                            break;
                        } else {
                            imagen = "res/drawable/nofoto";
                        }
                    }
                    String name = imagen.replace(".png", "");
                    name = name.substring(name.lastIndexOf("/") + 1, name.length());
                    Familia f = new Familia(objeto.getInt("idFamilia"), objeto.getString("nombreFamilia"), name);
                    familias.add(f);
                }
                Log.v("total familia", familias.size() + "");
            } catch (JSONException e) {

            }


            JSONTokener tokenproductos = new JSONTokener(strings[2].substring(4, strings[2].length()));
            productos = new ArrayList<>();
            try {
                String imagen = null;
                JSONArray array = new JSONArray(tokenproductos);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objeto = array.getJSONObject(i);
                    try {
                        imagen = objeto.getString("fotoProducto").toLowerCase();
                    } catch (JSONException ex) {
                        imagen = "vacio";
                    }
                    String name;
                    name = imagen.replace(".png", "").replace(".jpg", "");
                    name = name.substring(name.lastIndexOf("/") + 1, name.length());

                    Producto p = new Producto(objeto.getInt("idProducto"), objeto.getString("nombreProducto"), objeto.getDouble("precioProducto"), objeto.getInt("Familias_idFamilias"), name);
                    productos.add(p);
                }
                Log.v("total productos", productos.size() + "");
            } catch (JSONException e) {
                Log.v("Jsonerror", e.toString());
            }
            dialog.dismiss();
            Intent login = new Intent(getBaseContext(), ContenedorTab.class);
            startActivity(login);
        }

        public String pedirDatos(String urlPeticion) {
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
}
