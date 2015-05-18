package com.example.sadarik.tpv;

import java.io.Serializable;

/**
 * Created by aescribano on 18/05/15.
 */
public class Pedido implements Serializable {

    private int cantidad;
    private Producto producto;

    public Pedido() {
    }

    public Pedido(Producto producto) {
        this.cantidad = 1;
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void sumaCantidad(){
        this.cantidad++;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "cantidad=" + cantidad +
                ", producto=" + producto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pedido pedido = (Pedido) o;

        return !(producto != null ? !producto.equals(pedido.producto) : pedido.producto != null);

    }

    @Override
    public int hashCode() {
        return producto != null ? producto.hashCode() : 0;
    }
}
