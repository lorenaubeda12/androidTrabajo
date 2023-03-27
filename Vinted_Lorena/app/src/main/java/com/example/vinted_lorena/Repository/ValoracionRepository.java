package com.example.vinted_lorena.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Compra;
import com.example.vinted_lorena.Entity.service.Valoracion;
import com.example.vinted_lorena.api.ComprasApi;
import com.example.vinted_lorena.api.ConfigApi;
import com.example.vinted_lorena.api.ValoracionApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValoracionRepository {
    private final ValoracionApi valoracionApi;
    private static ValoracionRepository repository;

    public ValoracionRepository() {
        this.valoracionApi = ConfigApi.getValoracionApi();
    }


    public static ValoracionRepository getInstance() {
        if (repository == null) {
            repository = new ValoracionRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Valoracion>> save(Valoracion valoracion) {
        final MutableLiveData<GenericResponse<Valoracion>> mutableLiveData = new MutableLiveData<>();
        this.valoracionApi.save(valoracion).enqueue(new Callback<GenericResponse<Valoracion>>() {

            @Override
            public void onResponse(Call<GenericResponse<Valoracion>> call, Response<GenericResponse<Valoracion>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Valoracion>> call, Throwable t) {
                mutableLiveData.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error :" + t.getMessage());
                t.printStackTrace();
            }
        });

        return mutableLiveData;
    }
}