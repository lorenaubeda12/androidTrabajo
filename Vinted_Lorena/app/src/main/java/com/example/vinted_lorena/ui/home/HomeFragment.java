package com.example.vinted_lorena.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinted_lorena.Adapter.CategoriaAdapter;
import com.example.vinted_lorena.Adapter.ProductoAdapter;
import com.example.vinted_lorena.Communication.Communication;
import com.example.vinted_lorena.Entity.service.Categoria;
import com.example.vinted_lorena.Entity.service.Producto;
import com.example.vinted_lorena.R;
import com.example.vinted_lorena.databinding.FragmentHomeBinding;
import com.example.vinted_lorena.view_model.CategoriaViewModel;
import com.example.vinted_lorena.view_model.ProductoViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, Communication {

    private FragmentHomeBinding binding;
    private ProductoViewModel productoViewModel;
    private RecyclerView rcvProductos;
    private ProductoAdapter adapterProductos;
    private List<Producto> listaProductos = new ArrayList<>();
    private SearchView buscador;


    //Categorias
    private CategoriaViewModel categoriaViewModel;
    private CategoriaAdapter adapterCategoria;
    private List<Categoria> lstCategoria = new ArrayList<>();
    private RecyclerView rcCategorias;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initAdapter();
        loadData();
    }

    private void init(View v) {
        ViewModelProvider vmp = new ViewModelProvider(this);

        //Categorías
        this.categoriaViewModel = vmp.get(CategoriaViewModel.class);
        rcCategorias = v.findViewById(R.id.rcvCategorias);
        rcCategorias.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        //Productos
        rcvProductos = v.findViewById(R.id.rcvProductos2);
        rcvProductos.setLayoutManager(new GridLayoutManager(getContext(), 1));
        productoViewModel = vmp.get(ProductoViewModel.class);
        buscador = v.findViewById(R.id.buscar);

    }

    private void initAdapter() {

        //Categorías
        adapterCategoria = new CategoriaAdapter(lstCategoria, this);
        rcCategorias.setAdapter(adapterCategoria);

        //Productos
        adapterProductos = new ProductoAdapter(listaProductos, this, this, this);
        rcvProductos.setAdapter(adapterProductos);
    }

    private void loadData() {
        categoriaViewModel.listarCategoriasActivas().observe(getViewLifecycleOwner(), response -> {
            adapterCategoria.updateItems(response.getBody());
        });

        //Llamar datos
        productoViewModel.listarProductos().observe(getViewLifecycleOwner(), response -> {
            adapterProductos.updateItems(response.getBody());
            buscador.setOnQueryTextListener(this);
        });





    }


    @SuppressLint("UnsafeExperimentalUsageError")

    public void successMessage(String message) {
        new SweetAlertDialog(this.getContext(),
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapterProductos.filtrado(newText);
        return false;
    }

    @Override
    public void showDetails(Intent i) {
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);

    }

    @Override
    public void exportInvoice(int idCli, int idOrden, String fileName) {

    }
}