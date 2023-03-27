package com.example.vinted_lorena.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Producto;
import com.example.vinted_lorena.Entity.service.Usuario;
import com.example.vinted_lorena.api.ConfigApi;
import com.example.vinted_lorena.api.UsuarioApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Usuario_Repository {
    private static Usuario_Repository repository;
    private final UsuarioApi usuarioApi;

    private Usuario_Repository() {
        usuarioApi = ConfigApi.getUsuarioAPI();
    }

    public static Usuario_Repository getInstance() {
        if (repository == null) {
            repository = new Usuario_Repository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Usuario>> login(String email, String password) {
        final MutableLiveData<GenericResponse<Usuario>> mdl = new MutableLiveData<>();
        this.usuarioApi.login(email, password).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, retrofit2.Response<GenericResponse<Usuario>> response) {
                if (response.isSuccessful()) {
                    mdl.setValue(response.body());
                } else {
                    mdl.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mdl.setValue(new GenericResponse());
                System.out.print("Se ha producido un error al iniciar la sesión: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mdl;
    }

    public LiveData<GenericResponse<Usuario>> save(Usuario usuario) {
         MutableLiveData<GenericResponse<Usuario>> mutableLiveData = new MutableLiveData<>();
        this.usuarioApi.save(usuario).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                mutableLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mutableLiveData.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error :" + t.getMessage());
                t.printStackTrace();
            }
        });

        return mutableLiveData;
    }

    public LiveData<GenericResponse<List<Usuario>>> listarVendedoresConMasProductos() {
        final MutableLiveData<GenericResponse<List<Usuario>>> mutableLiveData = new MutableLiveData<>();
        this.usuarioApi.listarVendedoresConMasProductos().enqueue(new Callback<GenericResponse<List<Usuario>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Usuario>>> call, Response<GenericResponse<List<Usuario>>> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Usuario>>> call, Throwable t) {
                mutableLiveData.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error :" + t.getMessage());
                t.printStackTrace();
            }
        });

        return mutableLiveData;
    }



}
