package com.example.vinted_lorena.Entity.service;


import java.sql.Date;


public class Compra {
    private int id_compra;

    private Producto id_producto;

    private Usuario id_usuario;

    private Date fecha_compra;
    private  double precio_compra;

    private Tipo_envio tipo_Envio;


    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }



    public Usuario getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario = id_usuario;
    }


    public Date getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public double getPrecio_compra() {
        return precio_compra;
    }

    public Producto getId_producto() {
        return id_producto;
    }

    public void setId_producto(Producto id_producto) {
        this.id_producto = id_producto;
    }

    public Tipo_envio getTipo_Envio() {
        return tipo_Envio;
    }

    public void setTipo_Envio(Tipo_envio tipo_Envio) {
        this.tipo_Envio = tipo_Envio;
    }

    public void setPrecio_compra(double precio_compra) {
        this.precio_compra = precio_compra;
    }


}
