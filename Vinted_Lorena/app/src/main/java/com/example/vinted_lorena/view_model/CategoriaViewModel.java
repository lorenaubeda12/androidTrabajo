package com.example.vinted_lorena.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Categoria;
import com.example.vinted_lorena.Repository.Categoria_Repository;

import java.util.List;

public class CategoriaViewModel extends AndroidViewModel {
    private final Categoria_Repository repository;


    public CategoriaViewModel(@NonNull Application application) {
        super(application);
        this.repository = Categoria_Repository.getInstance();
    }

    public LiveData<GenericResponse<List<Categoria>>> listarCategoriasActivas() {
        return this.repository.listarCategoriasActivas();
    }
}