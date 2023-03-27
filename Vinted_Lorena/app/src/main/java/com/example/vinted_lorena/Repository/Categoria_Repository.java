package com.example.vinted_lorena.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Categoria;

import com.example.vinted_lorena.api.ConfigApi;
import com.example.vinted_lorena.api.CategoriaApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Categoria_Repository {
    private final CategoriaApi api;
    private static Categoria_Repository repository;

    public Categoria_Repository() {
        this.api = ConfigApi.getCategoriaApi();
    }
    public static Categoria_Repository getInstance(){
        if(repository == null){
            repository = new Categoria_Repository();
        }
        return repository;
    }
    public LiveData<GenericResponse<List<Categoria>>> listarCategoriasActivas(){
        final MutableLiveData<GenericResponse<List<Categoria>>> mld = new MutableLiveData<>();
        this.api.listarCategoriasActivas().enqueue(new Callback<GenericResponse<List<Categoria>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Categoria>>> call, Response<GenericResponse<List<Categoria>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Categoria>>> call, Throwable t) {
                System.out.println("Error al obtener las categorías: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
