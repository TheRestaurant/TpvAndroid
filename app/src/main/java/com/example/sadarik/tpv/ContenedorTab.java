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

    	host.addTab(host.newTabSpec(getString(R.string.tab_interior)).setIndicator(getString(R.string.tab_interior)).setContent(new Intent(this, MesaInterior.class)));
    	host.addTab(host.newTabSpec(getString(R.string.tab_terraza)).setIndicator(getString(R.string.tab_terraza)).setContent(new Intent(this, MesaTerraza.class)));
    }
}