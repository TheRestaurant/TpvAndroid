package com.example.sadarik.tpv;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class ContenedorTab extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

    	TabHost host = getTabHost();

    	host.addTab(host.newTabSpec("Interior").setIndicator("Interior").setContent(new Intent(this, MesaInterior.class)));
    	host.addTab(host.newTabSpec("Terraza").setIndicator("Terraza").setContent(new Intent(this, MesaTerraza.class)));
    }
}