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
import com.example.vinted_lorena.utilis.DateSerializer;
import com.example.vinted_lorena.utilis.TimeSerializer;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ProductosPorCategoriaAdapter extends RecyclerView.Adapter<ProductosPorCategoriaAdapter.ViewHolder> {

    private List<Producto> productoList;
    private final Communication communication;

    public ProductosPorCategoriaAdapter(List<Producto> productoList, Communication communication) {
        this.productoList = productoList;

        this.communication = communication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.productoList.get(position));
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setItem(final Producto producto) {
            /* ImageView imgProducto = (ImageView)  itemView.findViewById(R.id.imgProducto);*/

            String ulrImage = generateUrl(producto.getImagen());
            Uri uri = Uri.parse(ulrImage);
            SimpleDraweeView draweeView = (SimpleDraweeView) itemView.findViewById(R.id.my_image_view);
            draweeView.setImageURI(uri);


            /*SearchView buscador = itemView.findViewById(R.id.buscar);*/
            TextView nombreProducto = itemView.findViewById(R.id.nombreProducto);
            TextView descipcionProducto = itemView.findViewById(R.id.descrip);
            Button btnVerProducto = itemView.findViewById(R.id.btnVer);
            TextView precioProducto = itemView.findViewById(R.id.precio);
            Button btnComprarProducto = itemView.findViewById(R.id.btnComprarProducto);

            /*  Picasso.get().load(producto.getImagen()).resize(50,50).centerCrop().into(imgProducto);*/
            nombreProducto.setText(producto.getNombre_producto());
            descipcionProducto.setText(producto.getDescripcion());
            precioProducto.setText(String.valueOf(producto.getPrecio()) + "€");


            btnVerProducto.setOnClickListener(v -> {
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
    public void updateItems(List<Producto> producto) {
        this.productoList.clear();
        this.productoList.addAll(producto);
        this.notifyDataSetChanged();
    }

    public String generateUrl(String s) {
        String[] p = s.split("/");
        String link = "https://drive.google.com/uc?export=download&id=" + p[5];
        return link;
    }
}