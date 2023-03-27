package com.example.vinted_lorena.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinted_lorena.Communication.Communication;

import com.example.vinted_lorena.Entity.service.Usuario;
import com.example.vinted_lorena.R;
import com.example.vinted_lorena.ui.valoracion.ValoracionFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class UsuariosMasProductosdapter extends RecyclerView.Adapter<UsuariosMasProductosdapter.ViewHolder> {

    private final List<Usuario> usuariosConMasProductosAlaVenta;
    private final Communication communication;

    public UsuariosMasProductosdapter(List<Usuario> usuarioList, ValoracionFragment homeFragment, ValoracionFragment fragment, Communication communication) {
        this.usuariosConMasProductosAlaVenta = new ArrayList<>();
        usuariosConMasProductosAlaVenta.addAll(usuarioList);
        this.communication = communication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado_vendedor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.usuariosConMasProductosAlaVenta.get(position));
    }

    public void updateItem(List<Usuario> productos) {
        this.usuariosConMasProductosAlaVenta.clear();
        this.usuariosConMasProductosAlaVenta.addAll(productos);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.usuariosConMasProductosAlaVenta.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(final Usuario usuario) {


            TextView nombreUsuario = itemView.findViewById(R.id.nombreVendedor);
            TextView emailUsuario = itemView.findViewById(R.id.emailVendedor);
            TextView paisUsuario = itemView.findViewById(R.id.paisVendedor);

            nombreUsuario.setText(usuario.getnombreCompleto());
            emailUsuario.setText(usuario.getEmail());
            paisUsuario.setText(usuario.getPais());


        }
    }


}