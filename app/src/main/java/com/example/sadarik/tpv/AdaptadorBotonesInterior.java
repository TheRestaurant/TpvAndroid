package com.example.sadarik.tpv;

import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class AdaptadorBotonesInterior extends BaseAdapter {
	
	private ArrayList<Button> arrayBotones;
	 
    public AdaptadorBotonesInterior(ArrayList<Button> bt)
    {
		arrayBotones = bt;
    }

	@Override
	public int getCount() {
		return arrayBotones.size();
	}

	@Override
	public Object getItem(int position) {
		return (Object) arrayBotones.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Button button;
        if (convertView == null) {
            button = arrayBotones.get(position);
        } else {
            button = (Button) convertView;
        }
        return button;
	}

}
