package com.example.sadarik.tpv;

import java.io.Serializable;


public class Familia implements Serializable {

    private int idFamilia;
    private String nombreFamilia;

    public Familia() {
    }

    public Familia(int idFamilia, String nombreFamilia) {
        this.idFamilia = idFamilia;
        this.nombreFamilia = nombreFamilia;
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

    @Override
    public String toString() {
        return "Familia{" +
                "idFamilia=" + idFamilia +
                ", nombreFamilia='" + nombreFamilia + '\'' +
                '}';
    }
}
