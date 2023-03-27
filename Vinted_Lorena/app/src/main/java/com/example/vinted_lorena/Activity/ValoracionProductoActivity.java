package com.example.vinted_lorena.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinted_lorena.Entity.service.Compra;
import com.example.vinted_lorena.Entity.service.Producto;
import com.example.vinted_lorena.Entity.service.Usuario;
import com.example.vinted_lorena.Entity.service.Valoracion;
import com.example.vinted_lorena.R;
import com.example.vinted_lorena.api.ConfigApi;
import com.example.vinted_lorena.home;
import com.example.vinted_lorena.ui.valoracion.ValoracionViewModel;
import com.example.vinted_lorena.utilis.DateSerializer;
import com.example.vinted_lorena.utilis.TimeSerializer;
import com.example.vinted_lorena.view_model.CompraViewModel;
import com.example.vinted_lorena.view_model.ProductoViewModel;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.sql.Time;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ValoracionProductoActivity extends AppCompatActivity {
    private ImageView imgProductoValorar;
    private TextView tvNombreProductoValorado, tvPrecioProductoValorado, tvDescripcionProductoValorado;
    private Spinner spinnerProductoValorar;
    private ValoracionViewModel valoracionViewModel;
    private Button btnGuardarValoracion;
    private String valoracion;
    private int valoracionNum;
    static Usuario usuario;

    final Gson g = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateSerializer())
            .registerTypeAdapter(Time.class, new TimeSerializer())
            .create();

    Compra compra;
    Valoracion valoracionNueva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        valoracionViewModel = new ViewModelProvider(this).get(ValoracionViewModel.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valoracion_producto);
        init();
        loadData();
    }


    private void init() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        toolbar.setNavigationOnClickListener(v -> {//Reemplazo con lamba
            this.finish();
            this.overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
        });
        valoracionNueva = new Valoracion();
        this.imgProductoValorar = findViewById(R.id.imgValorarProducto);
        this.tvNombreProductoValorado = findViewById(R.id.nombreProductoValoracion);
        this.tvPrecioProductoValorado = findViewById(R.id.precioValoracion);
        this.tvDescripcionProductoValorado = findViewById(R.id.descripValoracion);
        this.spinnerProductoValorar = findViewById(R.id.spValoracion);
        this.btnGuardarValoracion=findViewById(R.id.btnGuardarValoracion);


        spinnerProductoValorar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(getApplicationContext(), "you selected" + parent.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
                valoracion = parent.getItemAtPosition(pos).toString();

                if (valoracion.contains("5")) {
                    valoracionNum = 5;
                    valoracionNueva.setValoracion(valoracionNum);


                } else if (valoracion.contains("4")) {
                    valoracionNum = 4;
                    valoracionNueva.setValoracion(valoracionNum);



                } else if (valoracion.contains("3")) {
                    valoracionNum = 3;
                    valoracionNueva.setValoracion(valoracionNum);


                } else if (valoracion.contains("2")) {
                    valoracionNum = 2;
                    valoracionNueva.setValoracion(valoracionNum);


                } else if (valoracion.contains("1")) {
                    valoracionNum = 1;
                    valoracionNueva.setValoracion(valoracionNum);



                } else if (valoracion.contains("0")) {
                    valoracionNum = 0;
                    valoracionNueva.setValoracion(valoracionNum);


                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public String generateUrl(String s) {
        String[] p = s.split("/");
        String link = "https://drive.google.com/uc?export=download&id=" + p[5];
        return link;
    }


    private void loadData() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        String usuarioJson = sp.getString("usuarioJson", null);
        if (usuarioJson != null) {
            usuario = g.fromJson(usuarioJson, Usuario.class);
            final String detalleString = this.getIntent().getStringExtra("compraValorar");
            if (detalleString != null ) {
            compra = g.fromJson(detalleString, Compra.class);
            this.tvNombreProductoValorado.setText(compra.getId_producto().getNombre_producto());
            this.tvNombreProductoValorado.setText(compra.getId_producto().getDescripcion());
            this.tvPrecioProductoValorado.setText(String.valueOf(compra.getPrecio_compra())+"€");

                String ulrImage = generateUrl(compra.getId_producto().getImagen());
                Uri uri = Uri.parse(ulrImage);
                SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.imgValorarProducto);
                draweeView.setImageURI(uri);


                this.valoracionNueva.setId_producto(compra.getId_producto());
          this.valoracionNueva.setId_usuario(usuario);
          this.valoracionNueva.setId_compra(compra);

            } else {
                System.out.println("Error al obtener los detalles del producto");
            }

        }

        btnGuardarValoracion.setOnClickListener(v -> {
            try {
                guardarDatos(valoracionNueva);
            } catch (Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();
                Toast.makeText(this, "Se ha producido un error : " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarDatos(Valoracion valoracion) {
        try {
            valoracionViewModel.guardarValoracion(valoracion).observe(this, response -> {

                successMessage("Gracias por valorar el producto'");

/*            Intent intent = new Intent(getApplicationContext(), home.class);
            startActivity(intent);*/
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Se ha producido un error : " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    public void successMessage(String message) {

        Handler handler = new Handler();
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("¡Valoración realizada!")
                .setContentText(message).show();
        int tiempoTranscurrir = 3000; //1 segundo, 1000 millisegundos.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //***Aquí agregamos el proceso a ejecutar.

                Intent intent = new Intent(getApplicationContext(), home.class);
                startActivity(intent);


                handler.removeCallbacks(null);
            }
        }, tiempoTranscurrir);//define el tiemp
    }


}
