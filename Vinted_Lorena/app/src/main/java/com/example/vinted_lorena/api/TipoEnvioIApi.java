package com.example.vinted_lorena.api;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Compra;
import com.example.vinted_lorena.Entity.service.Tipo_envio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TipoEnvioIApi {
    String base = "api/tipoEnvio";

    @GET(base + "/tipo/{id}")
    Call<GenericResponse<Tipo_envio>> listarTipoEnvioElegido(@Path("id") int id);

}
