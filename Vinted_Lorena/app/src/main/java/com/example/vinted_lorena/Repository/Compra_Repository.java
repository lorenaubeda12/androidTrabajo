package com.example.vinted_lorena.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Compra;
import com.example.vinted_lorena.Entity.service.Usuario;
import com.example.vinted_lorena.api.ComprasApi;
import com.example.vinted_lorena.api.ConfigApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Compra_Repository {
    private final ComprasApi comprasApi;
    private static Compra_Repository repository;

    public Compra_Repository() {
        this.comprasApi = ConfigApi.getComprasApi();
    }


    public static Compra_Repository getInstance() {
        if (repository == null) {
            repository = new Compra_Repository();
        }
        return repository;
    }


    public LiveData<GenericResponse<List<Compra>>> listarComprasCliente(int id) {
        final MutableLiveData<GenericResponse<List<Compra>>> mld = new MutableLiveData<>();
        this.comprasApi.listarMisCompras(id).enqueue(new Callback<GenericResponse<List<Compra>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Compra>>> call, Response<GenericResponse<List<Compra>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Compra>>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Error al obtener los pedidos: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Compra>> save(Compra compra) {
        final MutableLiveData<GenericResponse<Compra>> mutableLiveData = new MutableLiveData<>();
        this.comprasApi.save(compra).enqueue(new Callback<GenericResponse<Compra>>() {

            @Override
            public void onResponse(Call<GenericResponse<Compra>> call, Response<GenericResponse<Compra>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Compra>> call, Throwable t) {
                mutableLiveData.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error :" + t.getMessage());
                t.printStackTrace();
            }
        });

        return mutableLiveData;
    }
}