package com.example.vinted_lorena.ui.valoracion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Valoracion;
import com.example.vinted_lorena.Repository.Compra_Repository;
import com.example.vinted_lorena.Repository.ValoracionRepository;

public class ValoracionViewModel extends AndroidViewModel {

    private final ValoracionRepository repository;

    public ValoracionViewModel(@NonNull Application application) {
        super(application);
        this.repository = ValoracionRepository.getInstance();
    }


    public LiveData<GenericResponse<Valoracion>>guardarValoracion(Valoracion valoracion) {
        return this.repository.save(valoracion);
    }
}