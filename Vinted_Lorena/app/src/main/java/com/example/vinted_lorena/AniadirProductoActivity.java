package com.example.vinted_lorena;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vinted_lorena.Activity.ComprarActivity;
import com.example.vinted_lorena.Entity.service.Producto;
import com.example.vinted_lorena.Entity.service.Tipo_envio;
import com.example.vinted_lorena.Entity.service.Usuario;
import com.example.vinted_lorena.Repository.TipoEnvio_Repository;
import com.example.vinted_lorena.ui.gallery.GalleryFragment;
import com.example.vinted_lorena.ui.slideshow.SlideshowFragment;
import com.example.vinted_lorena.utilis.DateSerializer;
import com.example.vinted_lorena.utilis.TimeSerializer;
import com.example.vinted_lorena.view_model.ProductoViewModel;
import com.example.vinted_lorena.view_model.UsuarioViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AniadirProductoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnFinalizar;
    private EditText tvNombreProducto, tvPrecioProducto, tvDescripcionProducto, tvURL;
    private Spinner categoriaSpinner;
    static Usuario usuario;
    private ProductoViewModel productoViewModel;
    Producto productoNuevo = new Producto();
    private static final String CHANNEL_ID = "canal";
    private PendingIntent pendingIntent;



    static String categoriaElegida;
    static int tipoEnvioElegido;

    final Gson g = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateSerializer())
            .registerTypeAdapter(Time.class, new TimeSerializer())
            .create();

    TipoEnvio_Repository tipoEnvioRepository = TipoEnvio_Repository.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_producto);
            productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        try {
            init();
            btnFinalizar.setOnClickListener(v -> {
                    loadData();
                    guardarDatos(productoNuevo);
            });
        } catch (MessagingException e) {
            e.printStackTrace();
        }



    }

    private boolean validar() {
        boolean retorno = true;
        String nombre;
        String url;
        String precio;
        String descripcion;

        nombre = tvNombreProducto.getText().toString();
        url = tvURL.getText().toString();
        precio = tvPrecioProducto.getText().toString();
        descripcion = tvPrecioProducto.getText().toString();


        if (nombre.length() == 0) {
            Toast.makeText(this, "Ingrese el nombre", Toast.LENGTH_LONG).show();
            retorno = false;
        }
        if (descripcion.length() == 0) {
            Toast.makeText(this, "Ingrese la descripcion del producto", Toast.LENGTH_LONG).show();
            retorno = false;
        }
        if (url.length() == 0) {
            Toast.makeText(this, "Ingrese una URL ", Toast.LENGTH_LONG).show();
            retorno = false;
        }
        if (precio.length() == 0 || precio.equals("0")) {
            Toast.makeText(this, "Ingrese un precio", Toast.LENGTH_LONG).show();
            retorno = false;
        }

        return retorno;
    }

    private void init() throws MessagingException {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        toolbar.setNavigationOnClickListener(v -> {//Reemplazo con lamba
            this.finish();
            this.overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
        });
        this.categoriaSpinner = (Spinner) findViewById(R.id.categoriaElegir);
        this.tvURL = findViewById(R.id.urlImagenProducto);
        this.btnFinalizar = findViewById(R.id.btnSubir);
        this.tvNombreProducto = findViewById(R.id.aniadirProductoNombre);
        this.tvPrecioProducto = findViewById(R.id.edPrecioProductoNuevo);
        this.tvDescripcionProducto = findViewById(R.id.edDescrioProductoNuevo);

        btnFinalizar = findViewById(R.id.btnSubir);

        categoriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(getApplicationContext(),"you selected"+parent.getItemAtPosition(pos),Toast.LENGTH_SHORT).show();
                categoriaElegida= parent.getItemAtPosition(pos).toString();

                if (categoriaElegida.contains("Zapatillas")) {
                    tipoEnvioElegido = 1;
                    productoNuevo.setCategoria(tipoEnvioElegido);

                } else if (categoriaElegida.contains("Blusa")) {
                    tipoEnvioElegido = 2;
                    productoNuevo.setCategoria(tipoEnvioElegido);

                } else if (categoriaElegida.contains("Bota")) {
                    tipoEnvioElegido = 3;
                    productoNuevo.setCategoria(tipoEnvioElegido);
                } else if (categoriaElegida.contains("Gorro")) {
                    tipoEnvioElegido = 4;
                    productoNuevo.setCategoria(tipoEnvioElegido);
                } else if (categoriaElegida.contains("Camisa")) {
                    tipoEnvioElegido = 5;
                    productoNuevo.setCategoria(tipoEnvioElegido);
                } else if (categoriaElegida.contains("Pantalon")) {
                    tipoEnvioElegido = 6;
                    productoNuevo.setCategoria(tipoEnvioElegido);
                } else if (categoriaElegida.contains("Vestido")) {
                    tipoEnvioElegido = 7;
                    productoNuevo.setCategoria(tipoEnvioElegido);
                } else if (categoriaElegida.contains("Bolso")) {
                    tipoEnvioElegido = 8;
                    productoNuevo.setCategoria(tipoEnvioElegido);
                } else if (categoriaElegida.contains("Chaqueta")) {
                    tipoEnvioElegido = 10;
                    productoNuevo.setCategoria(tipoEnvioElegido);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
     /*   btnAtras.setOnClickListener(v -> {
            onBackPressed();
        });*/

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
            if (validar()) {
                String nombre=tvNombreProducto.getText().toString();
                String descripcion = tvDescripcionProducto.getText().toString();
                Double precio = Double.parseDouble(tvPrecioProducto.getText().toString());
                String url = tvURL.getText().toString();

                productoNuevo.setNombre_producto(nombre);
                productoNuevo.setDescripcion(descripcion);
                productoNuevo.setPrecio(precio);
                productoNuevo.setId_usuario(usuario);
                productoNuevo.setId(0);
                productoNuevo.setImagen(url);

                String[] myarray =getResources().getStringArray(R.array.categoria);


            }

        }

    }

    private void guardarDatos(Producto productoNuevo) {
        try {
            productoViewModel.guardarProducto(productoNuevo).observe(this, response -> {

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        showNotification();
                    } else {
                        showNewNotification();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Se ha producido un error al intentar registrarse: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                successMessage("¡ Tu producto ya esta subido ! Revisa la sección Mis productos'");



            /*Intent intent = new Intent(getApplicationContext(), home.class);
            startActivity(intent);*/
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Se ha producido un error : " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    private void showNotification() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "NEW", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        showNewNotification();
    }

    private void showNewNotification() {
        setPedingIntent(ComprarActivity.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(android.R.drawable.btn_star_big_on)
                .setContentTitle("¡Tu producto se ha subido con éxito!")
                .setContentText("Revisa tus productos a la venta en Mis Productos'")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(1, builder.build());

    }

    private void setPedingIntent(Class<ComprarActivity> comprarActivityClass) {
        Intent intent = new Intent(this, GalleryFragment.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(comprarActivityClass);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void successMessage(String message) {

        Handler handler = new Handler();
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("¡Has subido tu producto!")
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}