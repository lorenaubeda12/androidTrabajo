package com.example.vinted_lorena.api;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Compra;
import com.example.vinted_lorena.Entity.service.Valoracion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ValoracionApi {
    String base = "api/valoracion";

    @POST(base)
    Call<GenericResponse<Valoracion>> save(@Body Valoracion valoracion);
}
