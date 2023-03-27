package com.example.vinted_lorena.api;

import com.example.vinted_lorena.utilis.DateSerializer;
import com.example.vinted_lorena.utilis.TimeSerializer;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigApi {
    public static final String BASE_URL = "http://10.0.2.2:9090/";
    private static Retrofit retrofit;
    private static String token = "";


    private static ProductoApi productoApi;
    private static UsuarioApi usuarioApi;
    private static CategoriaApi categoriaApi;
    private static ComprasApi comprasApi;
    private static TipoEnvioIApi tipoEnvioIApi;
    private static ValoracionApi valoracionApi;


    static {
        initClient();
    }

    private static void initClient() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient()).build();
    }

    public static OkHttpClient getClient() {
        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
        StethoInterceptor stetho = new StethoInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggin).connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(stetho);
        return builder.build();
    }


    public static void setToken(String value) {
        token = value;
        initClient();

    }

    public static UsuarioApi getUsuarioAPI() {
        if (usuarioApi == null) {
            usuarioApi = retrofit.create(UsuarioApi.class);
        }
        return usuarioApi;
    }

    public static ProductoApi getProductoApi() {
        if (productoApi == null) {
            productoApi = retrofit.create(ProductoApi.class);
        }
        return productoApi;
    }

    public static CategoriaApi getCategoriaApi() {
        if (categoriaApi == null) {
            categoriaApi = retrofit.create(CategoriaApi.class);
        }
        return categoriaApi;
    }

    public static ComprasApi getComprasApi() {
        if (comprasApi == null) {
            comprasApi = retrofit.create(ComprasApi.class);
        }
        return comprasApi;
    }


    public static TipoEnvioIApi getTipoEnvioApi() {
        if (tipoEnvioIApi == null) {
            tipoEnvioIApi = retrofit.create(TipoEnvioIApi.class);
        }
        return tipoEnvioIApi;
    }

    public static ValoracionApi getValoracionApi() {
        if (valoracionApi == null) {
            valoracionApi = retrofit.create(ValoracionApi.class);
        }
        return valoracionApi;
    }


}

