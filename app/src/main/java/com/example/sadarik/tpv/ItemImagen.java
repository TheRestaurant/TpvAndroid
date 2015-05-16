package com.example.sadarik.tpv;

import android.graphics.Bitmap;

public class ItemImagen {

    private Bitmap imagen;
    private String titulo;

    public ItemImagen(Bitmap image, String title) {
        super();
        this.imagen = image;
        this.titulo = title;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitle(String titulo) {
        this.titulo = titulo;
    }
}
