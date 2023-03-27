package com.example.vinted_lorena.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Compra;
import com.example.vinted_lorena.Entity.service.Producto;
import com.example.vinted_lorena.Entity.service.Tipo_envio;
import com.example.vinted_lorena.api.ComprasApi;
import com.example.vinted_lorena.api.ConfigApi;
import com.example.vinted_lorena.api.TipoEnvioIApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipoEnvio_Repository {
    private final TipoEnvioIApi tipoEnvioIApi;
    private static TipoEnvio_Repository repository;

    public TipoEnvio_Repository() {
        this.tipoEnvioIApi = ConfigApi.getTipoEnvioApi();
    }


    public static TipoEnvio_Repository getInstance() {
        if (repository == null) {
            repository = new TipoEnvio_Repository();
        }
        return repository;
    }


    public LiveData<GenericResponse<Tipo_envio>> tipoEnvioElegido(int idEnvio) {
        final MutableLiveData<GenericResponse<Tipo_envio>> mutableLiveData = new MutableLiveData<>();
        this.tipoEnvioIApi.listarTipoEnvioElegido(idEnvio).enqueue(new Callback<GenericResponse<Tipo_envio>>() {
            @Override
            public void onResponse(Call<GenericResponse<Tipo_envio>> call, Response<GenericResponse<Tipo_envio>> response) {
                mutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Tipo_envio>> call, Throwable t) {
                mutableLiveData.postValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mutableLiveData;
    }




}