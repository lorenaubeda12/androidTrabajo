package com.example.vinted_lorena.api;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Compra;
import com.example.vinted_lorena.Entity.service.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ComprasApi {
    String base = "api/compras";

    @GET(base + "/todas/{id}")
    Call<GenericResponse<List<Compra>>> listarMisCompras(@Path("id") int id);


    @POST(base)
    Call<GenericResponse<Compra>> save(@Body Compra compra);
}
