package com.example.sadarik.tpv;

import java.io.Serializable;

/**
 * Created by aescribano on 14/05/15.
 */
public class Mesa implements Serializable {

    private int idMesa;
    private String nombreMesa;
    private String zona;

    public Mesa() {
    }

    public Mesa(int idMesa, String nombreMesa, String zona) {
        this.idMesa = idMesa;
        this.nombreMesa = nombreMesa;
        this.zona = zona;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getNombreMesa() {
        return nombreMesa;
    }

    public void setNombreMesa(String nombreMesa) {
        this.nombreMesa = nombreMesa;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "idMesa='" + idMesa + '\'' +
                ", nombreMesa='" + nombreMesa + '\'' +
                ", zona='" + zona + '\'' +
                '}';
    }
}
