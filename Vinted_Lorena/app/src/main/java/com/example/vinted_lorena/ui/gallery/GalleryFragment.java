package com.example.vinted_lorena.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinted_lorena.Activity.DetalleProductoActivity;
import com.example.vinted_lorena.Adapter.misProductosAdapter;
import com.example.vinted_lorena.AniadirProductoActivity;
import com.example.vinted_lorena.Entity.service.Producto;
import com.example.vinted_lorena.Entity.service.Usuario;
import com.example.vinted_lorena.R;
import com.example.vinted_lorena.databinding.FragmentGalleryBinding;
import com.example.vinted_lorena.utilis.DateSerializer;
import com.example.vinted_lorena.utilis.TimeSerializer;
import com.example.vinted_lorena.view_model.ProductoViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;


import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private ProductoViewModel productoViewModel;
    private RecyclerView rcvProductos;
    private misProductosAdapter adapter;
    private List<Producto> listaProductos = new ArrayList<>();
    private Button btnAniadirProducto;
    private TextView textoProductosAniadir;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
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

        //Productos
        rcvProductos = v.findViewById(R.id.rcvProductos2);
        rcvProductos.setLayoutManager(new GridLayoutManager(getContext(), 1));
        productoViewModel = vmp.get(ProductoViewModel.class);

        //AniadirProducto
        btnAniadirProducto = v.findViewById(R.id.btnAniadirProductoVenta);
        textoProductosAniadir = v.findViewById(R.id.textoAniadir);
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        String usuario = sp.getString("usuarioJson", null);
        if (usuario != null) {
            final Usuario user = gson.fromJson(usuario, Usuario.class);
            if (user.getTipo_usuario().toLowerCase().contains("vendedor")) {
                textoProductosAniadir.setVisibility(View.VISIBLE);
                btnAniadirProducto.setVisibility(View.VISIBLE);

            } else {
                textoProductosAniadir.setVisibility(View.GONE);
                btnAniadirProducto.setVisibility(View.GONE);
            }

        }


    }

    private void initAdapter() {
        //Productos
        adapter = new misProductosAdapter(listaProductos, this, this);
        rcvProductos.setAdapter(adapter);
    }

    private void loadData() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        String usuarioJson = sp.getString("usuarioJson", null);
        if (usuarioJson != null) {
            final Usuario u = g.fromJson(usuarioJson, Usuario.class);
            //Productos
            this.productoViewModel.listarMisProductos(u.getId()).observe(getViewLifecycleOwner(), response -> {
                adapter.updateItem(response.getBody());
            });

            btnAniadirProducto.setOnClickListener(v->{
                final Intent i = new Intent(v.getContext(), AniadirProductoActivity.class);
              startActivity(i);

            });

        }
    }


    @SuppressLint("UnsafeExperimentalUsageError")

    public void successMessage(String message) {
        new SweetAlertDialog(this.getContext(),
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}