package com.example.sadarik.tpv;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class Principal extends FragmentActivity {


    private EditText usuario, password;
    private ProgressDialog pDialog;
    private String baseUrl="http://192.168.1.7:8080/ServletRestaurante/peticiones?target=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        usuario = (EditText) findViewById(R.id.etUsuario);
        password =(EditText) findViewById(R.id.etPassword);


    }

    public void login(View view){
        Peticion pet = new Peticion();
        String[] datos = new String []{baseUrl+"login","&login="+usuario.getText()+"&password="+password.getText()};
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

    class Peticion extends AsyncTask<String[],Integer,String> {

        protected void onPreExecute() {
            //Progress dialog por si tardara en cargar
            pDialog = new ProgressDialog(Principal.this);
            pDialog.setMessage("Autenticando....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String[]... params) {
            SystemClock.sleep(1950);
            String r="";
            for(String[] p:params){
                r=pedirInfo(p[0],p[1]);
            }
            return r;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();// Se oculta progress dialog
            try {
                JSONTokener token = new JSONTokener(s.substring(4,s.length()));
                JSONObject objeto = new JSONObject(token);
                Log.v("jsonresp", objeto.toString());
                String respuesta = objeto.getString("r");
                if(respuesta.equals("1")){
                    Intent login = new Intent(getBaseContext(), ContenedorTab.class); //Cambiar a mesas cuando logre meterlo en tab
                    startActivity(login);
                }else{
                    Toast.makeText(getBaseContext(), "Usuario "+usuario.getText()+password.getText(), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.v("error",e.toString());
            }

        }

        public String pedirInfo(String urlPeticion, String datos) {
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
                out.write(datos);
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
