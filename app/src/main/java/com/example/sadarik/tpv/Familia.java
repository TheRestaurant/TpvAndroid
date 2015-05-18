package com.example.sadarik.tpv;

import java.io.Serializable;


public class Familia implements Serializable {

    private int idFamilia;
    private String nombreFamilia;
    private String imagen;

    public Familia() {
    }

    public Familia(int idFamilia, String nombreFamilia, String imagen) {
        this.idFamilia = idFamilia;
        this.nombreFamilia = nombreFamilia;
        this.imagen = imagen;
    }

    public int getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Familia{" +
                "idFamilia=" + idFamilia +
                ", nombreFamilia='" + nombreFamilia + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
