package com.example.vinted_lorena.api;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Categoria;
import com.example.vinted_lorena.Entity.service.Valoracion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaApi {
    String base = "api/categoria";

    @GET(base+"/todas")
    Call<GenericResponse<List<Categoria>>> listarCategoriasActivas();
}
