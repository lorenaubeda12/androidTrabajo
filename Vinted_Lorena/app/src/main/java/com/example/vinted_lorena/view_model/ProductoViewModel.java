package com.example.vinted_lorena.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Compra;
import com.example.vinted_lorena.Entity.service.Producto;
import com.example.vinted_lorena.Repository.Producto_Repository;

import java.util.List;


public class ProductoViewModel extends AndroidViewModel {
    private final Producto_Repository repository;


    public ProductoViewModel(@NonNull Application application) {
        super(application);
        repository = Producto_Repository.getInstance();
    }

    public LiveData<GenericResponse<List<Producto>>>listarProductos(){
        return this.repository.listarProductos();
    }
    public LiveData<GenericResponse<List<Producto>>>listarMisProductos(int idUsuario){
        return this.repository.listarMisProductos(idUsuario);
    }
    public LiveData<GenericResponse<List<Producto>>>listarProductosCategoria(int idCategoria){
        return this.repository.listarProductoCategoria(idCategoria);
    }
    public LiveData<GenericResponse<List<Producto>>>diezProductosMasValorados(){
        return this.repository.productosValorados();
    }

    public LiveData<GenericResponse<Producto>>guardarProducto(Producto producto) {
        return this.repository.save(producto);
    }

}
