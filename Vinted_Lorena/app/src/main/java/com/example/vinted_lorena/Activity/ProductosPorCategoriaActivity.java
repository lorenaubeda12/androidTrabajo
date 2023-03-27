package com.example.vinted_lorena.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.vinted_lorena.Adapter.ProductosPorCategoriaAdapter;
import com.example.vinted_lorena.Communication.Communication;
import com.example.vinted_lorena.Entity.service.Producto;
import com.example.vinted_lorena.R;
import com.example.vinted_lorena.view_model.ProductoViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductosPorCategoriaActivity extends AppCompatActivity implements Communication {
    private ProductosPorCategoriaAdapter adapter;
    private ProductoViewModel productoViewModel;
    private List<Producto> lstproducto = new ArrayList<>();
    private RecyclerView rcvProductosCategorias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_por_categoria);
        init();
        initViewModel();
        initAdapter();
        loadData();
    }

    private void init() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        toolbar.setNavigationOnClickListener(v -> {//Reemplazo con lamba
            this.finish();
            this.overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
        });

    }

    private void initAdapter() {
        adapter = new ProductosPorCategoriaAdapter(lstproducto, this);
        rcvProductosCategorias = findViewById(R.id.rcvProductosPorCategoria);
        rcvProductosCategorias.setAdapter(adapter);
        rcvProductosCategorias.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initViewModel() {
        final ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        this.productoViewModel = viewModelProvider.get(ProductoViewModel.class);
    }

    private void loadData() {
        final String idCategoria = this.getIntent().getStringExtra("idCategoria");
        int idCategoriaFiltrada = Integer.parseInt(idCategoria);
        productoViewModel.listarProductosCategoria(idCategoriaFiltrada).observe(this, response -> {
            adapter.updateItems(response.getBody());
        });


    }

    @Override
    public void showDetails(Intent i) {
        this.startActivity(i);
        this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @Override
    public void exportInvoice(int idCli, int idOrden, String fileName) {

    }
}