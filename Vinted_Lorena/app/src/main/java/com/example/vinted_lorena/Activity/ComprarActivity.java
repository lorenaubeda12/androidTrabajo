package com.example.vinted_lorena.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinted_lorena.Entity.GenericResponse;
import com.example.vinted_lorena.Entity.service.Compra;
import com.example.vinted_lorena.Entity.service.Producto;
import com.example.vinted_lorena.Entity.service.Tipo_envio;
import com.example.vinted_lorena.Entity.service.Usuario;
import com.example.vinted_lorena.R;
import com.example.vinted_lorena.Repository.TipoEnvio_Repository;
import com.example.vinted_lorena.api.ConfigApi;
import com.example.vinted_lorena.api.TipoEnvioIApi;
import com.example.vinted_lorena.home;
import com.example.vinted_lorena.loginActivity;
import com.example.vinted_lorena.ui.home.HomeFragment;
import com.example.vinted_lorena.utilis.DateSerializer;
import com.example.vinted_lorena.utilis.TimeSerializer;
import com.example.vinted_lorena.view_model.CompraViewModel;
import com.example.vinted_lorena.view_model.UsuarioViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ComprarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageView imgProductoDetalle;
    private Button btnFinalizarCompra;
    private TextView tvNombreProducto, tvPrecioProducto, tvDescripcionProducto, tvPrecioFinal, tvVendedor;
    private Spinner tipoEnvio;
    static Usuario usuario;
    private CompraViewModel compraViewModel;

    private static final String CHANNEL_ID = "canal";
    private PendingIntent pendingIntent;


    // Propiedades del cliente de correo
    private static Session session;         // Sesion de correo
    private static Properties properties;   // Propiedades de la sesion
    private static Transport transport;     // Envio del correo
    private static MimeMessage mensaje;     // Mensaje que enviaremos

    // Credenciales de usuario
    private static String direccionCorreo = "a26013@svalero.com";   // Dirección de correo
    private static String contrasenyaCorreo = "!675y8$s";                 // Contraseña


    static String elegidoEnvio;
    static int tipoEnvioElegido;
    static double precioProducto;
    static double precioFinal;
    static Tipo_envio envioElegido;
    final Gson g = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateSerializer())
            .registerTypeAdapter(Time.class, new TimeSerializer())
            .create();

    Producto producto;

    TipoEnvio_Repository tipoEnvioRepository = TipoEnvio_Repository.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);
        init();

        // Ajustamos primero las properties
        properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        loadData();


    }


    private void init() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_volver_atras);
        toolbar.setNavigationOnClickListener(v -> {//Reemplazo con lamba
            this.finish();
            this.overridePendingTransition(R.anim.rigth_in, R.anim.rigth_out);
        });
        this.tipoEnvio = findViewById(R.id.tipoEnvio);
        this.tvVendedor = findViewById(R.id.vendedorProductoComprar);
        this.imgProductoDetalle = findViewById(R.id.productoComprar);
        this.btnFinalizarCompra = findViewById(R.id.btnPagar);
        this.tvNombreProducto = findViewById(R.id.nombreProductoComprar);
        this.tvPrecioProducto = findViewById(R.id.precioProductoComprar);
        this.tvDescripcionProducto = findViewById(R.id.descripcionProductoComprar);
        this.tvPrecioFinal = findViewById(R.id.PrecioFinalTotal);


    }


    public String generateUrl(String s) {
        String[] p = s.split("/");
        String link = "https://drive.google.com/uc?export=download&id=" + p[5];
        return link;
    }

    public static void enviarMensaje(String subject, String content) throws MessagingException {

        // Configuramos los valores de nuestro mensaje
        mensaje = new MimeMessage(session);
        mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getEmail()));
        mensaje.setSubject(subject);
        mensaje.setContent(content, "text/html");

        // Configuramos como sera el envio del correo
        transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", direccionCorreo, contrasenyaCorreo);
        transport.sendMessage(mensaje, mensaje.getAllRecipients());
        transport.close();

        // Mostramos que el mensaje se ha enviado correctamente
        System.out.println("--------------------------");
        System.out.println("Mensaje enviado");
        System.out.println("---------------------------");
    }

    private void loadData() {
        final String productoElegido = this.getIntent().getStringExtra("producto");
        if (productoElegido != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            final Gson g = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateSerializer())
                    .registerTypeAdapter(Time.class, new TimeSerializer())
                    .create();
            String usuarioJson = sp.getString("usuarioJson", null);
            if (usuarioJson != null) {

                usuario = g.fromJson(usuarioJson, Usuario.class);
                this.producto = g.fromJson(productoElegido, Producto.class);
                this.tvNombreProducto.setText(this.producto.getNombre_producto());
                this.tvPrecioProducto.setText(String.valueOf(this.producto.getPrecio()) + "€");
                this.tvDescripcionProducto.setText(this.producto.getDescripcion());


                if (productoElegido != null) {
                    this.producto = g.fromJson(productoElegido, Producto.class);
                    this.tvNombreProducto.setText(this.producto.getNombre_producto());
                    this.tvPrecioProducto.setText(String.valueOf(this.producto.getPrecio()) + "€");
                    this.tvDescripcionProducto.setText(this.producto.getDescripcion());
                    this.tvVendedor.setText(this.producto.getId_usuario().getnombreCompleto());

                    this.tipoEnvio.setOnItemSelectedListener(this);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tiposDeEnvio, android.R.layout.simple_spinner_item);
                    this.tipoEnvio.setAdapter(adapter);

                    precioProducto = this.producto.getPrecio();
                    this.tipoEnvio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            elegidoEnvio = parent.getItemAtPosition(position).toString();
                            if (elegidoEnvio.contains("Envio Estándar")) {
                                tipoEnvioElegido = 1;
                                precioFinal = precioProducto + 5;
                                tvPrecioFinal.setText(String.valueOf(precioFinal) + "€");

                            } else if (elegidoEnvio.contains("Envio Urgente")) {
                                precioFinal = precioProducto + 10;
                                tipoEnvioElegido = 3;
                                tvPrecioFinal.setText(String.valueOf(precioFinal) + "€");

                            } else if (elegidoEnvio.contains("Envio Certificado")) {
                                precioFinal = precioProducto + 6;
                                tipoEnvioElegido = 4;
                                tvPrecioFinal.setText(String.valueOf(precioFinal) + "€");
                            } else {
                                Toast.makeText(ComprarActivity.this, "Se aplicara el envio basico que tiene un costo de 2€", Toast.LENGTH_SHORT).show();
                                precioFinal = precioProducto + 2;
                                tipoEnvioElegido = 2;
                                tvPrecioFinal.setText(String.valueOf(precioFinal) + "€");

                            }

                            /*switch (elegidoEnvio) {
                                case "Envio estándar":
                                    precioFinal+=5;
                                    tipoEnvioElegido = 1;

                                case "Envio Urgente":
                                    precioFinal+=10;
                                    tipoEnvioElegido = 3;

                                case "Envio Certificado":
                                    precioFinal+=6;
                                    tipoEnvioElegido = 4;

                            }*/

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }

                    });
                    /* this.tvPrecioFinal.setText(String.valueOf(precioFinal)+"€");*/

                    String ulrImage = generateUrl(this.producto.getImagen());
                    Picasso picasso = new Picasso.Builder(this)
                            .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                            .build();
                    picasso.load(ulrImage)
                            .error(cn.pedant.SweetAlert.R.drawable.error_center_x)
                            .into(this.imgProductoDetalle);
                } else {
                    System.out.println("Error al obtener los detalles del producto");
                }
            }

        }

        btnFinalizarCompra.setOnClickListener(v -> {
            try {
                guardarDatos();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    showNotification();
                } else {
                    showNewNotification();
                }
            } catch (Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();
                Toast.makeText(this, "Se ha producido un error : " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void guardarDatos() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        /*   final ViewModelProvider vmp = new ViewModelProvider(this);
         *//* this.compraViewModel=vmp.get(CompraViewModel.class);*/
        Compra compraNueva = new Compra();
        compraNueva.setId_producto(producto);
        compraNueva.setId_usuario(usuario);
        envioElegido = Tipo_envio.datosTipoEnvio(tipoEnvioElegido);
        compraNueva.setTipo_Envio(envioElegido);
        compraNueva.setPrecio_compra(precioFinal);

        LocalDateTime now = LocalDateTime.now();
        ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(now);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        compraNueva.setFecha_compra(new Date(now.toInstant(offset).toEpochMilli()));


        CompraViewModel compraViewModel = new ViewModelProvider(this).get(CompraViewModel.class);
        compraViewModel.guardarCompra(compraNueva).observe(this, response -> {
            //Configuramos la sesión
            session = Session.getDefaultInstance(properties, null);

            try {
                enviarMensaje("Compra Vinted", "¡Tu compra se ha realizado con éxito!\n Estos son los datos de tu compra: \n" +
                        "Producto: " + producto.getNombre_producto() +
                        "Descripción: " + producto.getDescripcion() +
                        "Precio: " + compraNueva.getPrecio_compra() +
                        "Fecha: " + compraNueva.getFecha_compra());

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            successMessage("No olvides revisar tus compras en 'Mis compras'");

            /*Intent intent = new Intent(getApplicationContext(), home.class);
            startActivity(intent);*/
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showNotification();
        } else {
            showNewNotification();
        }

    }

    private void showNotification() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "NEW", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        showNewNotification();
    }

    private void showNewNotification() {
        setPedingIntent(ComprarActivity.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_done_outline)
                .setContentTitle("¡Tu compra ha sido procesada!")
                .setContentText("Tu compra se ha realizado correctamente, recuerda mirar sus datos en 'Mis compras'")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(1, builder.build());

    }

    private void setPedingIntent(Class<ComprarActivity> comprarActivityClass) {
        Intent intent = new Intent(this, home.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(comprarActivityClass);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void successMessage(String message) {

        Handler handler = new Handler();
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("¡Compra realizada!")
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
