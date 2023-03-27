package com.example.vinted_lorena.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinted_lorena.Activity.ProductosPorCategoriaActivity;
import com.example.vinted_lorena.Communication.Communication;
import com.example.vinted_lorena.Entity.service.Categoria;
import com.example.vinted_lorena.R;
import com.example.vinted_lorena.utilis.DateSerializer;
import com.example.vinted_lorena.utilis.TimeSerializer;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    private final List<Categoria> listadoCategorias;
    private final Communication communication;
    public CategoriaAdapter(List<Categoria> listadoCategorias, Communication communication) {
        this.listadoCategorias = listadoCategorias;
        this.communication = communication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.varios_item, parent, false);
        return new ViewHolder(v);
    }

    public String generateUrl(String s) {
        String[] p = s.split("/");
        String link = "https://drive.google.com/uc?export=download&id=" + p[5];
        return link;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.listadoCategorias.get(position));
    }

    @Override
    public int getItemCount() {
        return this.listadoCategorias.size();
    }

    public void updateItems(List<Categoria> lstCategoria) {
        this.listadoCategorias.clear();
        this.listadoCategorias.addAll(lstCategoria);
        this.notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img;
        private final TextView nombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.imgOpcion);
            this.nombre = itemView.findViewById(R.id.txtNombreAccion);
        }

        public void setItem(final Categoria categoria) {
            this.nombre.setText(categoria.getNombre_categoria());
            String url = generateUrl(categoria.getImg_categoria());
            /*Picasso picasso = new Picasso.Builder(itemView.getContext())
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    .resize(50,50)
                    .error(android.R.drawable.stat_notify_error)
                    .into(img);*/

            Uri uri = Uri.parse(generateUrl(categoria.getImg_categoria()));
            SimpleDraweeView draweeView = (SimpleDraweeView) itemView.findViewById(R.id.imgOpcion);
            draweeView.setImageURI(uri);

            itemView.setOnClickListener(v -> {
                Intent i = new Intent(itemView.getContext(), ProductosPorCategoriaActivity.class);
                final Gson g = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateSerializer())
                        .registerTypeAdapter(Time.class, new TimeSerializer())
                        .create();
                i.putExtra("idCategoria",g.toJson(categoria.getId_categoria()));
                communication.showDetails(i);


            });

        }
    }
}