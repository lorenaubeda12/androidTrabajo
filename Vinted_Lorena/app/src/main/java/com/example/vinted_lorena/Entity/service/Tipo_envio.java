package com.example.vinted_lorena.Entity.service;


public class Tipo_envio {

    public static final Tipo_envio URGENTE = new Tipo_envio(3,"Envio Urgente",10);
    public static final Tipo_envio CERTIFICADO = new Tipo_envio(4,"Envio Certificado",6);
    public static final Tipo_envio ESTANDAR = new Tipo_envio(1,"Envio Estándar",5);
    public static final Tipo_envio NORMAL = new Tipo_envio(2,"Envio normal",2);
    private int id_tipo_envio;

    private String nombre_tipo_envio;

    private int precio;

    public Tipo_envio(int id_tipo_envio, String nombre_tipo_envio, int precio) {
        this.id_tipo_envio = id_tipo_envio;
        this.nombre_tipo_envio = nombre_tipo_envio;
        this.precio = precio;
    }

    public int getId_tipo_envio() {
        return id_tipo_envio;
    }

    public void setId_tipo_envio(int id_tipo_envio) {
        this.id_tipo_envio = id_tipo_envio;
    }

    public String getNombre_tipo_envio() {
        return nombre_tipo_envio;
    }

    public void setNombre_tipo_envio(String nombre_tipo_envio) {
        this.nombre_tipo_envio = nombre_tipo_envio;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public static Tipo_envio datosTipoEnvio(int tipoEnvio) {
        switch(tipoEnvio) {
            case 1:
                return ESTANDAR;
            case 2:
                return NORMAL;
            case 3:
                return URGENTE;
            case 4:
                return CERTIFICADO;
            default:
                return new Tipo_envio(0,"",0);

        }
    }
}
