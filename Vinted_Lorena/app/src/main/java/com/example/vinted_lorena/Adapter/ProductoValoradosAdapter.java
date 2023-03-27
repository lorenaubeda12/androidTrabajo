package com.example.vinted_lorena.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinted_lorena.Activity.DetalleProductoActivity;
import com.example.vinted_lorena.Communication.Communication;
import com.example.vinted_lorena.Entity.service.Producto;
import com.example.vinted_lorena.R;
import com.example.vinted_lorena.ui.valoracion.ValoracionFragment;
import com.example.vinted_lorena.utilis.DateSerializer;
import com.example.vinted_lorena.utilis.TimeSerializer;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ProductoValoradosAdapter extends RecyclerView.Adapter<ProductoValoradosAdapter.ViewHolder> {

    private final List<Producto> ProductosMejorValorados;
    private final Communication communication;

    public ProductoValoradosAdapter(List<Producto> productoList, ValoracionFragment homeFragment, ValoracionFragment fragment, Communication communication) {
        this.ProductosMejorValorados = new ArrayList<>();
        ProductosMejorValorados.addAll(productoList);
        this.communication = communication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.ProductosMejorValorados.get(position));
    }

    public void updateItem(List<Producto> productos) {
        this.ProductosMejorValorados.clear();
        this.ProductosMejorValorados.addAll(productos);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.ProductosMejorValorados.size();
    }

    public String generateUrl(String s) {
        String[] p = s.split("/");
        String link = "https://drive.google.com/uc?export=download&id=" + p[5];
        return link;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(final Producto producto) {
            String url= producto.getImagen();
            if(url != null){
                String ulrImage = generateUrl(url);
                Uri uri = Uri.parse(ulrImage);
                SimpleDraweeView draweeView = (SimpleDraweeView) itemView.findViewById(R.id.my_image_view);
                draweeView.setImageURI(uri);
            }

            
            TextView nombreProducto = itemView.findViewById(R.id.nombreProducto);
            TextView descipcionProducto = itemView.findViewById(R.id.descrip);
            TextView precioProducto = itemView.findViewById(R.id.precio);
            Button btnver = itemView.findViewById(R.id.btnVer);

            nombreProducto.setText(producto.getNombre_producto());
            descipcionProducto.setText(producto.getDescripcion());
            precioProducto.setText(String.valueOf(producto.getPrecio()) + "€");


            btnver.setOnClickListener(v -> {
                final Intent i = new Intent(itemView.getContext(), DetalleProductoActivity.class);
                final Gson g = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateSerializer())
                        .registerTypeAdapter(Time.class, new TimeSerializer())
                        .create();

                i.putExtra("detalleProducto", g.toJson(producto));
                communication.showDetails(i);


            });


        }
    }


}