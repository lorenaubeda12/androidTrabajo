package com.example.vinted_lorena.api;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Compra;
import com.example.vinted_lorena.Entity.service.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductoApi {
    //Ruta del controller del usuario
    String baseUrl = "api/producto";

    @GET(baseUrl + "/todos")
    Call<GenericResponse<List<Producto>>> listarProductos();


    @GET(baseUrl + "/misProductos/{id}")
    Call<GenericResponse<List<Producto>>> listarMisProductos(@Path("id") int id);

    @GET(baseUrl + "/productoCategoria/{id}")
    Call<GenericResponse<List<Producto>>> listarProductosCategoria(@Path("id") int idCategoria);


    @GET(baseUrl + "/{nombre}")
    Call<GenericResponse<List<Producto>>> listarProductosNombre(@Path("nombre") String nombre);


    @GET(baseUrl + "/productoValoraciones")
    Call<GenericResponse<List<Producto>>> listar10ProductoMejorValorados();


    @GET(baseUrl + "/producto/{id}")
    Call<GenericResponse<List<Producto>>> producto(@Path("nombre") String nombre);

    @POST(baseUrl)
    Call<GenericResponse<Producto>> save(@Body Producto p);


}
