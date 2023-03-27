package com.example.vinted_lorena.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Compra;
import com.example.vinted_lorena.Entity.service.Producto;
import com.example.vinted_lorena.api.ConfigApi;
import com.example.vinted_lorena.api.ProductoApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Producto_Repository {

    private final ProductoApi api;
    private static Producto_Repository repository;


    public Producto_Repository() {
        this.api = ConfigApi.getProductoApi();
    }

    public static Producto_Repository getInstance() {
        if (repository == null) {
            repository = new Producto_Repository();
        }
        return repository;
    }


    public LiveData<GenericResponse<List<Producto>>> listarProductos() {
        final MutableLiveData<GenericResponse<List<Producto>>> mutableLiveData = new MutableLiveData<>();
        this.api.listarProductos().enqueue(new Callback<GenericResponse<List<Producto>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Producto>>> call, Response<GenericResponse<List<Producto>>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Producto>>> call, Throwable t) {
                mutableLiveData.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mutableLiveData;
    }
    public LiveData<GenericResponse<List<Producto>>> listarMisProductos(int idUsuario) {
        final MutableLiveData<GenericResponse<List<Producto>>> mutableLiveData = new MutableLiveData<>();
        this.api.listarMisProductos(idUsuario).enqueue(new Callback<GenericResponse<List<Producto>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Producto>>> call, Response<GenericResponse<List<Producto>>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Producto>>> call, Throwable t) {
                mutableLiveData.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mutableLiveData;
    }
    public LiveData<GenericResponse<List<Producto>>> listarProductoCategoria(int idCategoria) {
        final MutableLiveData<GenericResponse<List<Producto>>> mutableLiveData = new MutableLiveData<>();
        this.api.listarProductosCategoria(idCategoria).enqueue(new Callback<GenericResponse<List<Producto>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Producto>>> call, Response<GenericResponse<List<Producto>>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Producto>>> call, Throwable t) {
                mutableLiveData.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mutableLiveData;
    }




    public LiveData<GenericResponse<List<Producto>>> productosValorados() {
        final MutableLiveData<GenericResponse<List<Producto>>> mutableLiveData = new MutableLiveData<>();
        this.api.listar10ProductoMejorValorados().enqueue(new Callback<GenericResponse<List<Producto>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Producto>>> call, Response<GenericResponse<List<Producto>>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Producto>>> call, Throwable t) {
                mutableLiveData.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mutableLiveData;
    }

    public LiveData<GenericResponse<Producto>> save(Producto producto) {
        final MutableLiveData<GenericResponse<Producto>> mutableLiveData = new MutableLiveData<>();
        this.api.save(producto).enqueue(new Callback<GenericResponse<Producto>>() {
            @Override
            public void onResponse(Call<GenericResponse<Producto>> call, Response<GenericResponse<Producto>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Producto>> call, Throwable t) {
                mutableLiveData.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error :" + t.getMessage());
                t.printStackTrace();
            }
        });

        return mutableLiveData;
    }


}
