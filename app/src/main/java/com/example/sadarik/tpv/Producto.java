package com.example.sadarik.tpv;

import java.io.Serializable;

/**
 * Created by aescribano on 14/05/15.
 */
public class Producto implements Serializable {

    private int idProducto;
    private String nombreProducto;
    private Double precio;
    private int idFamilia;
    private String imagen;

    public Producto() {
    }

    public Producto(int idProducto, String nombreProducto, Double precio, int idFamilia, String imagen) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.idFamilia = idFamilia;
        this.imagen = imagen;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(int idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", precio=" + precio +
                ", idFamilia=" + idFamilia +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
