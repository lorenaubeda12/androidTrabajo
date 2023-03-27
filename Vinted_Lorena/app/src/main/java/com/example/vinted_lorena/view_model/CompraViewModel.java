package com.example.vinted_lorena.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Compra;
import com.example.vinted_lorena.Repository.Compra_Repository;

import java.util.List;

public class CompraViewModel extends AndroidViewModel {
    private final Compra_Repository repository;


    public CompraViewModel(@NonNull Application application) {
        super(application);
        this.repository = Compra_Repository.getInstance();
    }

    public LiveData<GenericResponse<List<Compra>>> listarMisCompras(int id) {
        return this.repository.listarComprasCliente(id);
    }

    public LiveData<GenericResponse<Compra>>guardarCompra(Compra compra) {
        return this.repository.save(compra);
    }
}