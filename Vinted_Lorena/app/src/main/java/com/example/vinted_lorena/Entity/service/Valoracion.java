package com.example.vinted_lorena.Entity.service;

public class Valoracion {
    private int id_Valoracion;

    private Producto id_producto;

    private Usuario id_usuario;

    private Compra id_compra;

    private int valoracion;

    public int getId_Valoracion() {
        return id_Valoracion;
    }

    public void setId_Valoracion(int id_Valoracion) {
        this.id_Valoracion = id_Valoracion;
    }

    public Producto getId_producto() {
        return id_producto;
    }

    public void setId_producto(Producto id_producto) {
        this.id_producto = id_producto;
    }

    public Usuario getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Compra getId_compra() {
        return id_compra;
    }

    public void setId_compra(Compra id_compra) {
        this.id_compra = id_compra;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }
}
