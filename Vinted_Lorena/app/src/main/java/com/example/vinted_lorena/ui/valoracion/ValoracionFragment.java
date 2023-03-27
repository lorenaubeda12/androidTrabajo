package com.example.vinted_lorena.ui.valoracion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinted_lorena.Adapter.CompraAdapter;
import com.example.vinted_lorena.Adapter.ProductoValoradosAdapter;
import com.example.vinted_lorena.Adapter.UsuariosMasProductosdapter;
import com.example.vinted_lorena.Communication.Communication;
import com.example.vinted_lorena.Entity.service.Usuario;
import com.example.vinted_lorena.R;
import com.example.vinted_lorena.databinding.FragmentSlideshowBinding;
import com.example.vinted_lorena.ui.slideshow.SlideshowViewModel;
import com.example.vinted_lorena.utilis.DateSerializer;
import com.example.vinted_lorena.utilis.TimeSerializer;
import com.example.vinted_lorena.view_model.CompraViewModel;
import com.example.vinted_lorena.view_model.ProductoViewModel;
import com.example.vinted_lorena.view_model.UsuarioViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class ValoracionFragment extends Fragment implements Communication {
    private ProductoViewModel productoViewModel;
    private UsuarioViewModel usuarioViewModel;
    private RecyclerView rcvProductos;
    private RecyclerView rcvUsuarios;
    private TextView textoProctosInicio,
            textoUsuariosInicio;
    private ProductoValoradosAdapter adapter;
    private UsuariosMasProductosdapter adapterUsuario;

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_valoracion, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initViewModel();
        initAdapter();
        loadData();
    }

    private void init(View v) {
        rcvProductos = v.findViewById(R.id.rcvProductosValorados);
        rcvUsuarios = v.findViewById(R.id.rcvVendedores);
        textoProctosInicio = v.findViewById(R.id.textoProductoInicio);
        textoUsuariosInicio = v.findViewById(R.id.textoVendedorInicio);
        textoUsuariosInicio.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icono1user, 0, 0, 0);
        textoProctosInicio.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icono1product, 0, 0, 0);

    }

    private void initAdapter() {
        //Productos con mas valoraciones
        adapter = new ProductoValoradosAdapter(new ArrayList<>(), this, this, this);
        rcvProductos.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rcvProductos.setAdapter(adapter);

        //Vendedores con mas productos
        adapterUsuario = new UsuariosMasProductosdapter(new ArrayList<>(), this, this, this);
        rcvUsuarios.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rcvUsuarios.setAdapter(adapterUsuario);

    }

    private void initViewModel() {
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }

    private void loadData() {
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();

        //Usuarios
        this.usuarioViewModel.diezVendedoresConMasProductos().observe(getViewLifecycleOwner(), response -> {
            adapterUsuario.updateItem(response.getBody());
        });
        //Productos
        this.productoViewModel.diezProductosMasValorados().observe(getViewLifecycleOwner(), response -> {
            adapter.updateItem(response.getBody());
        });

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